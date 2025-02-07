package org.sonar.core.util;

import org.junit.Test; // JUnit 4
import static org.junit.Assert.assertTrue; // JUnit 4 assertions

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class SonarQubeHealthCheckTest {

    @Test
    public void testSonarQubeHealth() throws IOException {
        String sonarUrl = "http://localhost:9000/api/system/health";

        URL url = new URL(sonarUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        String credentials = "admin:admin";
        byte[] encodedCreds = Base64.encodeBase64(credentials.getBytes(StandardCharsets.UTF_8));
        conn.setRequestProperty("Authorization", "Basic " + new String(encodedCreds, StandardCharsets.UTF_8));

        int statusCode = conn.getResponseCode();

        String responseBody = IOUtils.toString(conn.getInputStream(), StandardCharsets.UTF_8);

        System.out.println("Received HTTP Code: " + statusCode);
        System.out.println("Response Body: " + responseBody);

        assertTrue("SonarQube response should contain 'health', check response format!",
                responseBody.contains("\"health\":"));

        assertTrue("SonarQube should be running (GREEN health status), but response was: " + responseBody,
                responseBody.contains("\"health\":\"GREEN\""));
    }
}
