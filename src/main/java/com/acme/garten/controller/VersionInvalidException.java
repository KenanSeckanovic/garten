package com.acme.garten.controller;

import java.io.Serial;
import java.net.URI;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;
import static com.acme.garten.controller.GartenWriteController.PROBLEM_PATH;
import static com.acme.garten.controller.ProblemType.PRECONDITION;

/// Exception, falls die Versionsnummer im Request-Header bei If-Match fehlt oder syntaktisch ungültig ist
class VersionInvalidException extends ErrorResponseException {
    @Serial
    private static final long serialVersionUID = 5121126361674362315L;

    /// Konstruktor für die Verwendung in KundeWriteController
    ///
    /// @param status HTTP-Statuscode
    /// @param message Die eigentliche Meldung
    /// @param uri des _PUT_- oder _PATCH_-Requests
    VersionInvalidException(final HttpStatusCode status, final String message, final URI uri) {
        this(status, message, uri, null);
    }

    /// Konstruktor für die Verwendung in KundeWriteController
    ///
    /// @param status HTTP-Statuscode
    /// @param message Die eigentliche Meldung
    /// @param uri des _PUT_- oder _PATCH_-Requests
    /// @param cause Ursache als innere Exception
    VersionInvalidException(
        final HttpStatusCode status,
        final String message,
        final URI uri,
        final @Nullable Throwable cause
    ) {
        super(status, asProblemDetail(status, message, uri), cause);
    }

    private static ProblemDetail asProblemDetail(final HttpStatusCode status, final String detail, final URI uri) {
        final var problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
        problemDetail.setType(URI.create(PROBLEM_PATH + PRECONDITION.getValue()));
        problemDetail.setInstance(uri);
        return problemDetail;
    }
}
