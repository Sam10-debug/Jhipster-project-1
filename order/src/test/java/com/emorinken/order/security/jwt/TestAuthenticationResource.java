package com.emorinken.order.security.jwt;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing testing authentication token.
 */
@RestController
@RequestMapping("/api")
public class TestAuthenticationResource {

    /**
     * {@code GET  /authenticate} : check if the authentication token correctly validates
     */
    @GetMapping("/authenticate")
    public ResponseEntity<Void> isAuthenticated() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
