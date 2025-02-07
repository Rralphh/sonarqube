/*
 * SonarQube
 * Copyright (C) 2009-2025 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.sonar.core.util;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class SystemPingIntegrationTest {

    @Test
    void testSonarQubePing() throws IOException {
        String sonarUrl = "http://localhost:9000/api/system/ping";

        URL url = new URL(sonarUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        String credentials = "admin:admin";
        byte[] encodedCreds = Base64.encodeBase64(credentials.getBytes(StandardCharsets.UTF_8));
        conn.setRequestProperty("Authorization", "Basic " + new String(encodedCreds, StandardCharsets.UTF_8));

        int statusCode = conn.getResponseCode();
        assertThat(statusCode)
            .as("Le code de statut HTTP doit être 200")
            .isEqualTo(HttpURLConnection.HTTP_OK);

        String body = IOUtils.toString(conn.getInputStream(), StandardCharsets.UTF_8);
        assertThat(body)
            .as("Le corps de la réponse doit être 'pong'")
            .isEqualTo("pong");
    }
}
