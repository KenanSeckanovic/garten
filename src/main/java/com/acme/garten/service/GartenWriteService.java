package com.acme.garten.service;

import com.acme.garten.entity.Garten;
import com.acme.garten.mail.MailService;
import com.acme.garten.repository.GartenRepository;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/// Anwendungslogik für Gaerten auch mit Bean Validation
@Service
@Transactional(readOnly = true)
public class GartenWriteService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GartenWriteService.class);

    private final GartenRepository repo;

    private final MailService mailService;

    /// Konstruktor mit `package private` für Constructor Injection bei Spring
    ///
    /// @param repo Injiziertes Repository für Spring Data
    /// @param mailService Injiziertes Objekt für Mailing
    GartenWriteService(final GartenRepository repo, final MailService mailService) {
        this.repo = repo;
        this.mailService = mailService;
    }

    /// Einen neuen Garten anlegen
    ///
    /// @param garten Objekt des neu anzulegenden Garten
    /// @return neu angelegter Garten mit generierter ID
    @Transactional
    @SuppressWarnings("TrailingComment")
    public Garten create(final Garten garten) {
        LOGGER.debug("create: garten={}", garten);
        LOGGER.debug("create: ort={}", garten.getOrt());
        LOGGER.debug("create: baeume={}", garten.getBaeume());

        final var gartenDB = repo.save(garten);

        LOGGER.trace("create: Thread-ID={}", Thread.currentThread().threadId());
        mailService.send(gartenDB);

        LOGGER.debug("create: gartenDB={}", gartenDB);
        return gartenDB;
    }

    /// vorhandenen Garten aktualisieren
    ///
    /// @param garten Objekt mit den neuen Daten (ohne ID)
    /// @param id ID des zu aktualisierenden Garten
    /// @param version Die erforderliche Version
    /// @return Aktualisierter Garten mit erhöhter Versionsnummer
    /// @throws NotFoundException Kein Garten zur ID vorhanden
    /// @throws VersionOutdatedException Versionsnummer ist veraltet und nicht aktuell
    @Transactional
    public Garten update(final Garten garten, final UUID id, final int version) {
        LOGGER.debug("update: garten={}", garten);
        LOGGER.debug("update: id={}, version={}", id, version);

        var gartenDb = repo
            .findById(id)
            .orElseThrow(() -> new NotFoundException(id));
        LOGGER.trace("update: version={}, gartenDb={}", version, gartenDb);
        if (version != gartenDb.getVersion()) {
            throw new VersionOutdatedException(version);
        }

        // Zu ueberschreibende Werte uebernehmen
        gartenDb.set(garten);
        gartenDb = repo.save(gartenDb);

        LOGGER.debug("update: {}", gartenDb);
        return gartenDb;
    }

    /// Einen Garten löschen
    ///
    /// @param id Die ID des zu löschenden Garten
    @Transactional
    public void deleteById(final UUID id) {
        LOGGER.debug("deleteById: id={}", id);
        final var garten = repo.findById(id).orElse(null);
        if (garten == null) {
            LOGGER.debug("deleteById: id={} nicht vorhanden", id);
            return;
        }
        repo.delete(garten);
    }
}
