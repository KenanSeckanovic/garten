package com.acme.garten.repository;

import com.acme.garten.entity.Baum;
import com.acme.garten.entity.Garten;
import com.acme.garten.entity.Ort;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/// Builde Klasse für Entity Garten
@SuppressWarnings({"NullAway.Init", "NotNullFieldNotInitialized", "PMD.AtLeastOneConstructor"})
class GartenBuilder {
    private UUID id;
    private int version;
    private String name;
    private int flaeche;
    private Ort ort;
    private List<Baum> baeume;
    private LocalDateTime erzeugt;
    private LocalDateTime aktualisiert;

    /// Ein Builder-Objekt für die Klasse [Garten] bauen
    ///
    /// @return Builder-Objekt
    static GartenBuilder getBuilder() {
        return new GartenBuilder();
    }

    /// ID setzen
    /// @param id ID
    /// @return Builder-Objekt
    GartenBuilder setId(final UUID id) {
        this.id = id;
        return this;
    }

    /// Version setzen
    /// @param version Version
    /// @return Builder-Objekt
    GartenBuilder setVersion(final int version) {
        this.version = version;
        return this;
    }

    /// Name setzen
    /// @param name Name
    /// @return Builder-Objekt
    GartenBuilder setName(final String name) {
        this.name = name;
        return this;
    }

    /// Fläche setzen
    /// @param flaeche Fläche
    /// @return Builder-Objekt
    GartenBuilder setFlaeche(final int flaeche) {
        this.flaeche = flaeche;
        return this;
    }

    /// Ort setzen
    /// @param ort Ort
    /// @return Builder-Objekt
    GartenBuilder setOrt(final Ort ort) {
        this.ort = ort;
        return this;
    }

    /// Bäume setzen
    /// @param baeume Bäume
    /// @return Builder-Objekt
    GartenBuilder setBaeume(final List<Baum> baeume) {
        this.baeume = baeume;
        return this;
    }

    /// Erzeugungsdatum setzen
    /// @param erzeugt Erzeugungsdatum
    /// @return Builder-Objekt
    GartenBuilder setErzeugt(final LocalDateTime erzeugt) {
        this.erzeugt = erzeugt;
        return this;
    }

    /// Aktualisierungsdatum setzen
    /// @param aktualisiert Aktualisierungsdatum
    /// @return Builder-Objekt
    GartenBuilder setAktualisiert(final LocalDateTime aktualisiert) {
        this.aktualisiert = aktualisiert;
        return this;
    }

    /// Garten bauen
    /// @return Gaten-Objekt
    Garten build() {
        return new Garten(id, version, name, flaeche, ort, baeume, erzeugt, aktualisiert);
    }
}
