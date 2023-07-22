package com.sa.tawuniya.assingment.account.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class JwtServiceTest {

    @InjectMocks
    JwtService jwtService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.jwtService = new JwtService("A31E8C6D5BF7E9A08C6D7E528A4F01B5E26C9D0F387E4A1B5C76D4E7A9081B5A3E28C76D7A08C6D5B7E9A0B4C76D8A4F012E5C6D9A");

    }
    @Test
    void generateToken() {
       var token = jwtService.generateToken(Map.of("role","ADMIN"), "admin");
       var username = jwtService.extractUsername(token);
       var isValid = jwtService.isTokenValid(token, org.springframework.security.core.userdetails.User
               .withUsername("admin")
               .password("admin")
               .roles("ADMIN")
               .build());
        assertEquals("admin", username);
        assertEquals(true, isValid);

    }

}