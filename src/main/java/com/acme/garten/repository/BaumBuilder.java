package com.acme.garten.repository;

import com.acme.garten.entity.Baum;
import java.util.UUID;

///Builder Klasse für Entity Baum
@SuppressWarnings({"NullAway.Init", "NotNullFieldNotInitialized", "PMD.AtLeastOneConstructor"})
class BaumBuilder {
    private UUID id;
    private String name;
    private int aeste;

    /// Ein Builder-Objekt für die Klasse [Baum] bauen
    ///
    /// @return Builder-Objekt
    static BaumBuilder getBuilder() {
        return new BaumBuilder();
    }

    /// ID setzen
    /// @param id ID
    /// @return Builder-Objekt
    BaumBuilder setId(final UUID id) {
        this.id = id;
        return this;
    }

    /// Name setzen
    /// @param name Name
    /// @return Builder-Objekt
    BaumBuilder setName(final String name) {
        this.name = name;
        return this;
    }

    /// Äste setzen
    /// @param aeste Äste
    /// @return Builder-Objekt
    BaumBuilder setAeste(final int aeste) {
        this.aeste = aeste;
        return this;
    }

    /// Baum bauen
    /// @return Baum-Objekt
    Baum build() {
        return new Baum(id, name, aeste);
    }
}
