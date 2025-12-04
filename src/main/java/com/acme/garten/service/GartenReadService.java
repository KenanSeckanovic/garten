package com.acme.garten.service;

import com.acme.garten.entity.Garten;
import com.acme.garten.repository.GartenRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

/// Anwendungslogik für Gaerten
@Service
@Transactional(readOnly = true)
public class GartenReadService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GartenReadService.class);

    private final GartenRepository repo;

    /// Konstruktor mit `package private` für Constructor Injection bei Spring
    ///
    /// @param repo Injiziertes Repository für Spring Data
    GartenReadService(final GartenRepository repo) {
        this.repo = repo;
    }

    /// Garten anhand seiner ID suchen
    ///
    /// @param id Id des gesuchten Garten
    /// @return Der gefundene Garten
    /// @throws NotFoundException Falls kein Garten gefunden wurde
    public Garten findById(final UUID id) {
        final var garten = repo.findById(id);
        if (garten.isEmpty()) {
            throw new NotFoundException(id);
        }
        return  garten.get();
    }

    /// Garten anhand von Suchkriterien als Collection suchen
    ///
    /// @param suchkriterien Suchkriterien
    /// @return gefundene Gaerten oder leere Liste
    /// @throws NotFoundException Falls keine Gaerten gefunden wurden
    @SuppressWarnings({"ReturnCount", "Nullaway"})
    public Optional<Garten> find(final MultiValueMap<String, String> suchkriterien) {
        LOGGER.debug("find: suchkriterien={}", suchkriterien);

        if (suchkriterien.isEmpty()) {
            throw new NotFoundException(suchkriterien);
        }
        final String namen = suchkriterien.getFirst("name");
        if (namen == null) {
            throw new NotFoundException(suchkriterien);
        }
        final var garten = repo.findByName(namen);
        if (garten.isEmpty()) {
            throw new NotFoundException(suchkriterien);
        }
        return garten;
    }


}
