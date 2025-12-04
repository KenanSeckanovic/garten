package com.acme.garten.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.jspecify.annotations.Nullable;

/// Data Transfer Object für Garten
///
/// @param name Vorgabe für Namen
/// @param flaeche Vorgabe für Flaeche
/// @param ort Vorgabe für Ort
/// @param baum Vorgabe für Baum
record GartenDTO(
    @NotBlank
    String name,

    @Min(MIN)
    @Max(MAX)
    int flaeche,

    @Valid
    @NotNull(groups = OnCreate.class)
    OrtDTO ort,

    @Nullable
    List<BaumDTO> baum
) {
    /// Minimale Flaeche für Garten
    static final long MIN = 10;

    /// Maximale Flaeche für Garten
    static final long MAX = 500;

    /// Marker-Interface für _Jakarta Validation_: zusätzliche Validierung beim Neuanlegen
    interface OnCreate { }

}
