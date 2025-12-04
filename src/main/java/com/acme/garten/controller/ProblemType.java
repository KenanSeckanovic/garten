package com.acme.garten.controller;

/// Enum für ProblemDetail.type
enum ProblemType {
    /// Constraints als Fehlerursache
    CONSTRAINTS("constraints"),

    /// Fehler, wenn z.B. E-Mail-Adresse bereits existiert.
    UNPROCESSABLE("unprocessable"),

    /// Fehler beim Header `If-Match`
    PRECONDITION("precondition"),

    /// Fehler bei z.B. einer Patch-Operation
    BAD_REQUEST("badRequest");

    private final String value;

    ProblemType(final String value) {
        this.value = value;
    }

    /// Den internen Wert eines Enums ermitteln
    ///
    /// @return Der interne Wert
    String getValue() {
        return value;
    }
}
