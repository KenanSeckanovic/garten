package com.acme.garten.repository;

import com.acme.garten.entity.Ort;
import java.util.UUID;

/// Builder Klasse für Entity Ort
@SuppressWarnings({"NullAway.Init", "NotNullFieldNotInitialized", "PMD.AtLeastOneConstructor"})
class OrtBuilder {
    private UUID id;
    private String plz;
    private String name;

    /// Ein Builder-Objekt für die Klasse [Ort] bauen.
    ///
    /// @return Builder-Objekt
    static OrtBuilder getBuilder() {
        return new OrtBuilder();
    }

    /// ID setzen
    /// @param id ID
    /// @return Builder-Objekt
    OrtBuilder setId(final UUID id) {
        this.id = id;
        return this;
    }

    /// Postleitzahl setzen
    /// @param plz Postleitzahl
    /// @return Builder-Objekt
    OrtBuilder setPlz(final String plz) {
        this.plz = plz;
        return this;
    }

    /// Name setzen
    /// @param name Name
    /// @return Builder-Objekt
    OrtBuilder setName(final String name) {
        this.name = name;
        return this;
    }

    /// Ort bauen
    /// @return Ort-Objekt
    Ort build() {
        return new Ort(id, plz, name);
    }
}
