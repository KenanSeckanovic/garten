package com.acme.garten.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/// ValueObject für das Neuanlegen und Ändern eines neuen Gartens
///
/// @param plz Postleitzahl
/// @param name Name
record OrtDTO(
    @NotNull
    @Pattern(regexp = PLZ_PATTERN)
    String plz,

    @NotBlank
    String name
) {
    /// Vorschrift für die Form einer Postleitzahl
    static final String PLZ_PATTERN = "^\\d{5}$";
}
