package com.acme.garten.controller;

import com.acme.garten.entity.Baum;
import com.acme.garten.entity.Garten;
import com.acme.garten.entity.Ort;
import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import static org.mapstruct.NullValueMappingStrategy.RETURN_DEFAULT;

/// Mapper zwischen Entity-Klassen.
@Mapper(nullValueIterableMappingStrategy = RETURN_DEFAULT, componentModel = "spring")
@AnnotateWith(ExcludeFromJacocoGeneratedReport.class)
interface GartenMapper {
    /// DTO-Objekt von [GartenDTO] in Objekt für [Garten] konvertieren
    ///
    /// @param dto DTO-Objekt für GartenDTO ohne ID
    /// @return Konvertiertes Garten-Objekt mit null als ID
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "erzeugt", ignore = true)
    @Mapping(target = "aktualisiert", ignore = true)
    @Mapping(source = "baum", target = "baeume")
    Garten toGarten(GartenDTO dto);

    /// DTO-Objekt von [BaumDTO] in Objekt für [Baum] konvertieren
    ///
    /// @param dto DTO-Objekt für BaumDTO
    /// @return Konvertiertes Baum-Objekt mit null als ID
    @Mapping(target = "id", ignore = true)
    Baum toBaum(BaumDTO dto);

    /// DTO-Objekt von [OrtDTO] in Objekt für [Ort] konvertieren
    ///
    /// @param dto DTO-Objekt für OrtDTO
    /// @return Konvertiertes Ort-Objekt
    @Mapping(target = "id", ignore = true)
    Ort toOrt(OrtDTO dto);
}
