package com.github.xasoek.hh_service.service.impl;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JwtServiceTest {

    private static final String SECRET = "test-secret-test-secret-test-secret-test-secret";

    @Test
    void generatedTokenIsValidForSubject() {
        JwtService jwtService = new JwtService(SECRET, 3600000);

        String token = jwtService.generateToken("user@example.com");

        assertThat(jwtService.extractEmail(token)).isEqualTo("user@example.com");
        assertThat(jwtService.isTokenValid(token, "user@example.com")).isTrue();
    }

    @Test
    void expiredTokenIsInvalid() {
        JwtService jwtService = new JwtService(SECRET, -1000);

        String token = jwtService.generateToken("user@example.com");

        assertThat(jwtService.isTokenValid(token, "user@example.com")).isFalse();
    }
}
