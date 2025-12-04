/*
 * Copyright (C) 2024 - present Juergen Zimmermann, Hochschule Karlsruhe
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
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.acme.garten.security;

import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

/// Service-Klasse, um Benutzernamen und Rollen aus einem JWT von Keycloak zu extrahieren.
///
/// @author [Jürgen Zimmermann](mailto:Juergen.Zimmermann@h-ka.de)
@Service
@SuppressWarnings("java:S5852")
public class JwtService {
    private static final int LENGTH_ROLE_PREFIX = "ROLE_".length();
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtService.class);

    /// Konstruktor mit `package private` für _Spring_.
    JwtService() {
    }

    /// Zu einem gegebenen JWT wird der zugehörige Username gesucht.
    ///
    /// @param authentication Objekt vom Typ JwtAuthenticationToken
    /// @return Der gesuchte Username oder null
    public String getUsername(final Authentication authentication) {
        LOGGER.debug("getUsername");
        if (!(authentication instanceof JwtAuthenticationToken token)) {
            throw new UsernameNotFoundException("Kein JwtAuthenticationToken"); // NOSONAR
        }
        // username = Subject des Token
        final var username = token.getName();
        LOGGER.debug("getUsername: username={}", username);
        return username;
    }

    /// Zu einem gegebenen JWT werden die zugehörigen Rollen gesucht.
    ///
    /// @param authentication Objekt vom Typ JwtAuthenticationToken
    /// @return Die gesuchten Rollen oder die leere Liste
    public List<RolleType> getRollen(final Authentication authentication) {
        return authentication
            .getAuthorities()
            .stream()
            // Liste von Strings ohne Praefix "ROLE_"
            .map(authority -> authority.getAuthority().substring(LENGTH_ROLE_PREFIX))
            .map(RolleType::of)
            .filter(Objects::nonNull)
            .toList();
    }
}
