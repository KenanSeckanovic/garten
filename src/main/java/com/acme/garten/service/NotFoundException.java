package com.acme.garten.service;

import java.io.Serial;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.jspecify.annotations.Nullable;

/// [RuntimeException], falls kein Garten gefunden wurde
public final class NotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1101909572340666200L;

    /// Fehlerhafte ID
    @Nullable
    private final UUID id;

    ///  Fehlerhafte Suchkriterien
    @Nullable
    private final Map<String, List<String>> suchkriterien;

    /// Standardkonstruktor für den [GartenReadService], wenn alle Gärten gesucht werden, aber keine existieren
    NotFoundException() {
        super("Keine Gärten gefunden.");
        id = null;
        suchkriterien = null;
    }

    /// Konstruktor für den [GartenReadService] bei fehlerhafter ID
    ///
    /// @param id Die fehlerhafte ID
    NotFoundException(final UUID id) {
        super("Kein Garten mit der ID " + id + " gefunden.");
        this.id = id;
        suchkriterien = null;
    }

    /// Konstruktor für den [GartenReadService] bei fehlerhaften Suchkriterien
    ///
    /// @param suchkriterien Die fehlerhaften Suchkriterien
    NotFoundException(final Map<String, List<String>> suchkriterien) {
        super("Keine Gärten gefunden.");
        id = null;
        this.suchkriterien = suchkriterien;
    }

    /// id ermitteln
    ///
    /// @return Die fehlerhafte id
    public @Nullable UUID getId() {
        return id;
    }

    /// Suchkriterien ermitteln
    ///
    /// @return Die fehlerhaften Suchkriterien
    public @Nullable Map<String, List<String>> getSuchkriterien() {
        return suchkriterien;
    }
}
