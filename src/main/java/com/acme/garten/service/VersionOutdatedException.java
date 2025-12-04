package com.acme.garten.service;

import java.io.Serial;

/// Exception, falls Versionsnummer nicht aktuell
public class VersionOutdatedException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -6446398552079178556L;

    /// verwaltete Version
    private final int version;

    /// Konstruktor für Verwendung in KundeWriteService
    ///
    /// @param version veraltete Version
    VersionOutdatedException(final int version) {
        super("Die Versionsnummer " + version + " ist veraltet.");
        this.version = version;
    }

    /// Veraltete Version ermitteln
    ///
    /// @return veraltete Version.
    public int getVersion() {
        return version;
    }
}
