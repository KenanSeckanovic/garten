package com.acme.garten.dev;

import org.springframework.context.annotation.Profile;
import static com.acme.garten.dev.DevConfig.DEV;

/// Konfigurationsklasse für die Anwendung bzw. den Microservice, falls das Profile `dev` aktiviert ist.
///
/// @author [Jürgen Zimmermann](mailto:Juergen.Zimmermann@h-ka.de)
@Profile(DEV)
@SuppressWarnings({"ClassNamePrefixedWithPackageName", "HideUtilityClassConstructor"})
public final class DevConfig implements Flyway, LogRequestHeaders, LogSignatureAlgorithms, K8s {
    /// Konstante für das Spring-Profile `dev`.
    public static final String DEV = "dev";

    /// Konstruktor mit _package private_ für _Spring_.
    DevConfig() {
    }
}
