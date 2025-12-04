package com.acme.garten.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Version;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;

/// Daten eines Gartens, in DDD ist Garten ein Aggregate Root
@Entity
public class Garten {
    @Id
    @GeneratedValue
    private UUID id;

    @Version
    private int version;

    private String name;

    private int flaeche;

    @OneToOne(optional = false, cascade = {PERSIST, REMOVE}, orphanRemoval = true)
    private Ort ort;

    @OneToMany(cascade = {PERSIST, REMOVE}, orphanRemoval = true)
    @JoinColumn(name = "garten_id")
    @OrderColumn(name = "idx", nullable = false)
    private List<Baum> baeume;

    @CreationTimestamp
    private LocalDateTime erzeugt;

    @UpdateTimestamp
    private LocalDateTime aktualisiert;

    /// Standardkonstruktor für Jakarta Persistence
    public Garten() {
    }

    /// Konstruktor
    /// @param id ID
    /// @param version Version
    /// @param name Name
    /// @param flaeche Größe
    /// @param ort Ort
    /// @param baeume Baeume
    /// @param erzeugt Erstelldatum
    /// @param aktualisiert Aktualisierungsdatum
    public Garten(final UUID id, final int version, final String name,
                  final int flaeche, final Ort ort, final List<Baum> baeume,
                  final LocalDateTime erzeugt, final LocalDateTime aktualisiert) {
        this.id = id;
        this.version = version;
        this.name = name;
        this.flaeche = flaeche;
        this.ort = ort;
        this.baeume = baeume;
        this.erzeugt = erzeugt;
        this.aktualisiert = aktualisiert;
    }

    /// Gartendaten überschreiben
    ///
    /// @param garten Neue Gartendaten
    public void set(final Garten garten) {
        name = garten.name;
        flaeche = garten.flaeche;
    }

    /// ID holen
    /// @return ID
    public UUID getId() {
        return id; }

    /// ID setzen
    /// @param id ID
    public void setId(final UUID id) {
        this.id = id; }

    /// Version holen
    ///
    /// @return Die Version
    public int getVersion() {
        return version;
    }

    /// Version setzen
    ///
    /// @param version Version
    public void setVersion(final int version) {
        this.version = version;
    }

    /// Name holen
    /// @return Name
    public String getName() {
        return name; }

    /// Name setzen
    /// @param name Name
    public void setName(final String name) {
        this.name = name; }

    /// Fläche holen
    /// @return Fläche
    public int getFlaeche() {
        return flaeche; }

    /// Fläche setzen
    /// @param flaeche Fläche
    public void setFlaeche(final int flaeche) {
        this.flaeche = flaeche; }

    /// Ort holen
    /// @return Ort
    public Ort getOrt() {
        return ort; }

    /// Ort setzen
    /// @param ort Ort
    public void setOrt(final Ort ort) {
        this.ort = ort; }

    /// Baum holen
    /// @return Baum
    public List<Baum> getBaeume() {
        return baeume; }

    /// Baeume setzen
    /// @param baeume Baeume
    public void setBaeume(final List<Baum> baeume) {
        this.baeume = baeume; }

    /// Erstellungsdatum holen
    ///
    /// @return Erstellungsdatum
    public LocalDateTime getErzeugt() {
        return erzeugt;
    }

    /// Erstellungsdatum setzen
    ///
    /// @param erzeugt Erstellungsdatum
    public void setErzeugt(final LocalDateTime erzeugt) {
        this.erzeugt = erzeugt;
    }

    /// Datum der letzten Änderung holen
    ///
    /// @return Datum der letzten Änderung
    public LocalDateTime getAktualisiert() {
        return aktualisiert;
    }

    /// Datum der letzen Änderung setzen
    ///
    /// @param aktualisiert Datum der letzen Änderung
    public void setAktualisiert(final LocalDateTime aktualisiert) {
        this.aktualisiert = aktualisiert;
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof final Garten garten && Objects.equals(id, garten.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Garten: " +
            "id: " + id +
            " version: " + version +
            " name: " + name +
            " flaeche: " + flaeche + " qm " +
            " ort: " + ort +
            " Bäume: " + baeume +
            " erzeugt: " + erzeugt +
            " aktualisiert: " + aktualisiert;
    }
}
