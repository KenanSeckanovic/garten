package com.acme.garten.controller;

import com.acme.garten.controller.GartenDTO.OnCreate;
import com.acme.garten.security.JwtService;
import com.acme.garten.service.GartenWriteService;
import com.acme.garten.service.VersionOutdatedException;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.groups.Default;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import static com.acme.garten.controller.GartenGetController.API_PATH;
import static com.acme.garten.controller.GartenGetController.ID_PATTERN;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.PRECONDITION_FAILED;
import static org.springframework.http.HttpStatus.PRECONDITION_REQUIRED;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.noContent;

/// Eine Controller-Klasse bildet die REST-Schnittstelle
@Controller
@RequestMapping(API_PATH)
@Validated
@SuppressWarnings({"ClassFanOutComplexity", "MethodCount", "java:S1075"})
class GartenWriteController {
    /// Basispfad für `type` innerhalb von [ProblemDetail]
    @SuppressWarnings("TrailingComment")
    static final String PROBLEM_PATH = "/problem/";

    private static final String VERSIONSNUMMER_FEHLT = "Versionsnummer fehlt";

    private static final Logger LOGGER = LoggerFactory.getLogger(GartenWriteController.class);

    private final GartenWriteService service;

    private final GartenMapper mapper;

    private final UriHelper uriHelper;

    private final JwtService jwtService;
    /// Konstruktor mit `package private` für Constructor Injection bei Spring
    ///
    /// @param service Injiziertes Objekt von `GartenWriteService`
    /// @param mapper Injiziertes Objekt für MapStruct
    /// @param uriHelper Injiziertes Helper-Objekt, um URIs zu bauen
    GartenWriteController(final GartenWriteService service, final GartenMapper mapper, final UriHelper uriHelper, final JwtService jwtService) {
        this.service = service;
        this.mapper = mapper;
        this.uriHelper = uriHelper;
        this.jwtService = jwtService;
    }

    /// Einen neuen Garten-Datensatz anlegen
    ///
    /// @param gartenDTO Gartenobjekt mit Benutzerdaten aus eingegangenem Request-Body
    /// @param request Das Request-Objekt, um Location im Response-Header zu erstellen
    /// @return Response
    /// @throws URISyntaxException falls die URI im Request-Objekt nicht korrekt wäre
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @SuppressWarnings({"TrailingComment", "UnusedLocalVariable", "PMD.UnusedLocalVariable"})
    @SuppressFBWarnings("DLS_DEAD_LOCAL_STORE")
    ResponseEntity<Void> post(
        @RequestBody @Validated({Default.class, OnCreate.class}) final GartenDTO gartenDTO,
        final HttpServletRequest request,
        final Authentication authentication
    ) throws URISyntaxException {
        LOGGER.debug("post: gartenDTO={}", gartenDTO);


        final var username = jwtService.getUsername(authentication);
        final var gartenInput = mapper.toGarten(gartenDTO);
        final var garten = service.create(gartenInput);
        final var baseUri = uriHelper.getBaseUri(request);
        final var location = new URI(baseUri.toString() + '/' + garten.getId());
        return created(location).build();
    }

    /// Einen vorhandenen Kunde-Datensatz überschreiben
    ///
    /// @param id ID des zu aktualisierenden Garten
    /// @param gartenDTO Gartenobjekt aus dem eingegangenen Request-Body
    /// @param ifMatch Versionsnummer aus dem Header If-Match
    /// @param request Das Request-Objekt, um ggf. die URL für ProblemDetail zu ermitteln
    /// @return Response
    @PutMapping(path = "{id:" + ID_PATTERN + "}", consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<Void> put(
        @PathVariable final UUID id,
        @RequestBody @Validated final GartenDTO gartenDTO,
        @RequestHeader("If-Match") @Nullable final String ifMatch,
        final HttpServletRequest request
    ) {
        LOGGER.debug("put: id={}, gartenDTO={}, ifMatch={}", id, gartenDTO, ifMatch);
        final int version = getVersion(ifMatch, request);
        final var gartenInput = mapper.toGarten(gartenDTO);
        final var garten = service.update(gartenInput, id, version);
        LOGGER.debug("put: {}", garten);
        return noContent().eTag("\"" + garten.getVersion() + '"').build();
    }

    @SuppressWarnings({"MagicNumber", "RedundantSuppression"})
    private int getVersion(@Nullable final String versionStr, final HttpServletRequest request) {
        LOGGER.trace("getVersion: {}", versionStr);
        if (versionStr == null) {
            throw new VersionInvalidException(
                PRECONDITION_REQUIRED,
                VERSIONSNUMMER_FEHLT,
                URI.create(request.getRequestURL().toString())
            );
        }
        if (versionStr.length() < 3 ||
            versionStr.charAt(0) != '"' ||
            versionStr.charAt(versionStr.length() - 1) != '"') {
            throw new VersionInvalidException(
                PRECONDITION_FAILED,
                "Ungueltiges ETag " + versionStr,
                URI.create(request.getRequestURL().toString())
            );
        }

        final int version;
        try {
            version = Integer.parseInt(versionStr.substring(1, versionStr.length() - 1));
        } catch (final NumberFormatException ex) {
            throw new VersionInvalidException(
                PRECONDITION_FAILED,
                "Ungueltiges ETag " + versionStr,
                URI.create(request.getRequestURL().toString()),
                ex
            );
        }

        LOGGER.trace("getVersion: version={}", version);
        return version;
    }

    /// Einen vorhandenen Garten anhand seiner ID löschen
    ///
    /// @param id ID des zu löschenden Garten
    @DeleteMapping(path = "{id:" + ID_PATTERN + "}")
    @ResponseStatus(NO_CONTENT)
    void deleteById(@PathVariable final UUID id)  {
        LOGGER.debug("deleteById: id={}", id);
        service.deleteById(id);
    }

    /// Exception Handler für Spring WebMvc, falls Constraints bei POST- oder PUT-Requests verletzt sind
    ///
    /// @param ex Exception vom Typ `MethodArgumentNotValidException`
    /// @param request Das injizierte Request-Objekt gemäß Jakarta Servlets
    /// @return ProblemDetails für den Response-Body
    @ExceptionHandler
    @SuppressFBWarnings("NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE")
    ProblemDetail onConstraintViolations(
        final MethodArgumentNotValidException ex,
        final HttpServletRequest request
    ) {
        LOGGER.debug("onConstraintViolations: {}", ex.getMessage());

        final var detailMessages = ex.getDetailMessageArguments();
        final var detail = detailMessages.length == 0 || detailMessages[1] == null
            ? "Constraint Violation"
            : ((String) detailMessages[1]).replace(", and ", ", ");
        final var problemDetail = ProblemDetail.forStatusAndDetail(UNPROCESSABLE_ENTITY, detail);
        problemDetail.setType(URI.create(PROBLEM_PATH + ProblemType.CONSTRAINTS.getValue()));
        problemDetail.setInstance(URI.create(request.getRequestURL().toString()));

        return problemDetail;
    }

    /// ExceptionHandler, falls bei einem PUT-Request die Version nicht aktuell existiert
    ///
    /// @param ex Exception vom Typ `VersionOutdatedException`
    /// @param request  Das injizierte Request-Objekt gemäß Jakarta Servlets
    /// @return ProblemDetails für den Response-Body
    @ExceptionHandler
    ProblemDetail onVersionOutdated(
        final VersionOutdatedException ex,
        final HttpServletRequest request
    ) {
        LOGGER.debug("onVersionOutdated: {}", ex.getMessage());
        final var problemDetail = ProblemDetail.forStatusAndDetail(PRECONDITION_FAILED, ex.getMessage());
        problemDetail.setType(URI.create(PROBLEM_PATH + ProblemType.PRECONDITION.getValue()));
        problemDetail.setInstance(URI.create(request.getRequestURL().toString()));
        return problemDetail;
    }

    /// ExceptionHandler, falls bei einem POST- oder PUT-Request der Request-Body syntaktisch falsch ist
    ///
    /// @param ex Exception vom Typ `HttpMessageNotReadableException`
    /// @param request  Das injizierte Request-Objekt gemäß Jakarta Servlets
    /// @return ProblemDetails für den Response-Body
    @ExceptionHandler
    ProblemDetail onMessageNotReadable(
        final HttpMessageNotReadableException ex,
        final HttpServletRequest request
    ) {
        LOGGER.debug("onMessageNotReadable: {}", ex.getMessage());
        final var problemDetail = ProblemDetail.forStatusAndDetail(BAD_REQUEST, ex.getMessage());
        problemDetail.setType(URI.create(PROBLEM_PATH + ProblemType.BAD_REQUEST.getValue()));
        problemDetail.setInstance(URI.create(request.getRequestURL().toString()));
        return problemDetail;
    }
}
