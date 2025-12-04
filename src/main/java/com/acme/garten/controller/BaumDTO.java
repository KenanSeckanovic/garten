package com.acme.garten.controller;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/// Data Transfer Object für Baum
///
/// @param name Vorgabe für Namen
/// @param aeste Vorgabe für Aeste
record BaumDTO(
    @NotBlank
    String name,

    @Min(MIN)
    @Max(MAX)
    int aeste
) {
    /// Minimaler Wert für Aeste
    static final long MIN = 3;

    /// Maximaler Wert für Aeste
    static final long MAX = 10;
}
