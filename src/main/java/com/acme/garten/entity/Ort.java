package com.acme.garten.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.Objects;
import java.util.UUID;

/// Ort des Gartens
@Entity
public class Ort {
    @Id
    @GeneratedValue
    private UUID id;
    private String plz;
    private String name;

    /// Standardkonstruktor für Jakarta Persistence
    public Ort() {
    }

    /// Konstruktor
    /// @param id ID
    /// @param plz Postleitzahl
    /// @param name Name des Ortes
    public Ort(final UUID id, final String plz, final String name) {
        this.id = id;
        this.plz = plz;
        this.name = name;
    }

    /// ID holen
    ///
    /// @return ID
    public UUID getId() {
        return id;
    }

    /// ID setzen
    ///
    /// @param id ID
    public void setId(final UUID id) {
        this.id = id;
    }

    /// Postleitzahl holen
    /// @return Postleitzahl
    public String getPlz() {
        return plz; }

    /// Postleitzahl setzen
    /// @param plz Postleitzahl
    public void setPlz(final String plz) {
        this.plz = plz; }

    /// Namen holen
    /// @return Namen
    public String getName() {
        return name; }

    /// Namen setzen
    /// @param name Name
    public void setName(final String name) {
        this.name = name; }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof final Ort ort && Objects.equals(id, ort.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Ort: " +
            "plz: " + plz +
            "name: " + name;
    }
}
