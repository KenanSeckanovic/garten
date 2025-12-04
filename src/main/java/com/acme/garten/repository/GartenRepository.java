package com.acme.garten.repository;

import com.acme.garten.entity.Garten;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/// Repository für DB-Zugriff bei Gaerten
public interface GartenRepository extends JpaRepository<Garten, UUID>, JpaSpecificationExecutor<Garten> {
    @EntityGraph(attributePaths = {"baeume", "ort"})
    @Override
    Page<Garten> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"baeume", "ort"})
    @Override
    Optional<Garten> findById(UUID id);

    /// Suche anhand des Namen
    ///
    /// @param name Name des Gartens
    /// @return gefundener Garten
    @EntityGraph(attributePaths = {"baeume", "ort"})
    Optional<Garten> findByName(String name);

}
