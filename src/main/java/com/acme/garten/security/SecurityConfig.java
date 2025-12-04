/*
 * Copyright (C) 2022 - present Juergen Zimmermann, Hochschule Karlsruhe
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.acme.garten.security;

import com.c4_soft.springaddons.security.oidc.starter.synchronised.resourceserver.ResourceServerExpressionInterceptUrlRegistryPostProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.util.UriComponentsBuilder;
import static com.acme.garten.security.RolleType.ADMIN;
import static org.springframework.http.HttpMethod.GET;

/// Security-Konfiguration.
///
/// @author [Jürgen Zimmermann](mailto:Juergen.Zimmermann@h-ka.de)
public class SecurityConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

    /// Konstruktor mit `package private` für `@Import()` bei _Spring_.
    SecurityConfig() {
    }

    /// Bean-Methode, um ein Objekt zum Interface KeycloakRepository zu erstellen.
    ///
    /// @param clientBuilder Injiziertes Objekt vom Typ RestClient.Builder
    /// @param keycloakProps Spring-Properties für Keycloak
    /// @return Objekt zum Interface KeycloakRepository
    @Bean
    KeycloakRepository keycloakRepository(
        final RestClient.Builder clientBuilder,
        final KeycloakProps keycloakProps
    ) {
        LOGGER.info("keycloakRepository: keycloakProps={}", keycloakProps);
        final var baseUri = UriComponentsBuilder.newInstance()
            .scheme(keycloakProps.schema())
            .host(keycloakProps.host())
            .port(keycloakProps.port())
            .build();
        LOGGER.info("keycloakRepository: baseUri={}", baseUri);

        final var restClient = clientBuilder.baseUrl(baseUri.toUriString()).build();
        final var clientAdapter = RestClientAdapter.create(restClient);
        final var proxyFactory = HttpServiceProxyFactory.builderFor(clientAdapter).build();
        return proxyFactory.createClient(KeycloakRepository.class);
    }

    /// Bean, um URIs aus Bibliotheken für z.B. Swagger und Actuator einzuschränken
    ///
    /// @return `ResourceServerExpressionInterceptUrlRegistryPostProcessor` aus _spring-addons-starter-oidc_
    // https://stackoverflow.com/questions/77801381/spring-boot-keycloak-rbac#answer-77802128
    @Bean
    ResourceServerExpressionInterceptUrlRegistryPostProcessor expressionInterceptUrlRegistryPostProcessor() {
        return registry -> registry
            .requestMatchers(GET, "/v3/api-docs", "/v3/api-docs.yaml").hasRole(ADMIN.name())
            .requestMatchers(GET, "/actuator/health", "/actuator/health/liveness", "/actuator/health/readiness",
                "/actuator/metrics", "/actuator/metrics/get-by-id", "/actuator/prometheus", "/swagger-ui.html")
            .permitAll()
            .requestMatchers(GET, "/actuator", "/actuator/**").hasRole(ADMIN.name());
    }

    /// Bean-Methode für die Überprüfung, ob ein Passwort ein bekanntes ("gehacktes") Passwort ist.
    ///
    /// @return "Checker-Objekt" für die Überprüfung, ob ein Passwort ein bekanntes ("gehacktes") Passwort ist.
    @Bean
    CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }
}
