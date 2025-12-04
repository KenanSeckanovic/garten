package com.acme.garten.controller;

import com.acme.garten.entity.Garten;
import com.acme.garten.security.JwtService;
import com.acme.garten.service.GartenReadService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import static com.acme.garten.controller.GartenGetController.API_PATH;
import static org.springframework.http.HttpStatus.NOT_MODIFIED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

/// Eine Controller-Klasse bildet die REST-Schnittstelle
@RestController
@RequestMapping(API_PATH)
@SuppressWarnings({"ClassFanOutComplexity", "java:S1075", "UnusedLocalVariable", "PMD.UnusedLocalVariable"})
class GartenGetController {
    /// Basispfad für REST-Schnittstelle
    static final String API_PATH = "/api";

    /// Muster für UUID
    static final String ID_PATTERN = "[\\da-f]{8}-[\\da-f]{4}-[\\da-f]{4}-[\\da-f]{4}-[\\da-f]{12}";

    private static final String NAME_PATH = "/name";

    private static final String DEFAULT_PAGE = "0";

    private static final String DEFAULT_PAGE_SIZE = "5";

    private static final Logger LOGGER = LoggerFactory.getLogger(GartenGetController.class);

    private final JwtService jwtService;

    private final GartenReadService service;

    /// Konstruktor mit `package private` für Constructor Injection bei Spring
    ///
    /// @param service Injiziertes Objekt von `KundeService`
    GartenGetController(final GartenReadService service,  final JwtService jwtService) {
        this.service = service;
        this.jwtService = jwtService;
    }

    /// Suche anhand Garten-ID als Pfad-Parameter
    ///
    /// @param id ID des zu suchenden Garten
    /// @param ifNonMatch Versionsnummer aus dem Header If-None-Match
    /// @return den gefundenen Garten
    @GetMapping(path = "{id:" + ID_PATTERN + "}", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @SuppressWarnings("ReturnCount")
    @SuppressFBWarnings("DLS_DEAD_LOCAL_STORE")
    ResponseEntity<Garten> getById(
        @PathVariable final UUID id,
        @RequestHeader("If-Non-Match") @Nullable final String ifNonMatch,
        final Authentication authentication
    ) {
        LOGGER.debug("getById: id={}, ifNonMatch={}", id, ifNonMatch);

        final var username = jwtService.getUsername(authentication);
        final var garten = service.findById(id);
        LOGGER.trace("getById: {}", garten);

        final var versionStr = "\"" + garten.getVersion() + '"';
        if (versionStr.equals(ifNonMatch)) {
            return status(NOT_MODIFIED).build();
        }

        LOGGER.debug("getById: garten={}", garten);
        return ok().eTag(versionStr).body(garten);
    }

    /// Suche mit Suchkriterien als Query-Parameter
    ///
    /// @param suchkriterien Query-Parameter als Map
    /// @return gefundenen Garten als Page
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    Garten get(
        @RequestParam final MultiValueMap<String, String> suchkriterien) {
        LOGGER.debug("get: suchkriterien={}", suchkriterien);
        LOGGER.trace("get: suchkriterien={}", suchkriterien);
        final Optional<Garten> garten = service.find(suchkriterien);
        return garten.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kein Garten gefunden"));
    }

}
