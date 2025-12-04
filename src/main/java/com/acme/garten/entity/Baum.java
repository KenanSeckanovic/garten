package com.acme.garten.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.Objects;
import java.util.UUID;

/// Bäume eines Gartens
@Entity
public class Baum {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private int aeste;

    /// Standardkonstruktor für Jakarta Persistence
    public Baum() {
    }

    /// Konstruktor
    /// @param id ID des Baumes
    /// @param name Name des Baumes
    /// @param aeste Anzahl der Äste
    public Baum(final UUID id, final String name, final int aeste) {
        this.id = id;
        this.name = name;
        this.aeste = aeste;
    }

    /// ID holen
    /// @return ID
    public UUID getId() {
        return id; }

    /// ID setzen
    /// @param id ID
    public void setId(final UUID id) {
        this.id = id; }

    /// Name holen
    /// @return Name
    public String getName() {
        return name; }

    /// Name setzen
    /// @param name Name
    public void setName(final String name) {
        this.name = name; }

    /// Äste holen
    /// @return Äste
    public int getAeste() {
        return aeste; }

    /// Äste setzen
    /// @param aeste Äste
    public void setAeste(final int aeste) {
        this.aeste = aeste; }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof final Baum baum && Objects.equals(id, baum.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Baum: " +
            "name: " + name +
            "aeste: " + aeste;
    }
}
