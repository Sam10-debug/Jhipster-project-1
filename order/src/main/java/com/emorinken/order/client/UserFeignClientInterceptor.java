package com.emorinken.order.client;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class UserFeignClientInterceptor implements RequestInterceptor {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer";

    @Override
    public void apply(RequestTemplate template) {
        // FIRST TRY: Use current user's JWT if available
        Optional<String> currentJwt = getCurrentUserJwt();
        if (currentJwt.isPresent()) {
            template.header(AUTHORIZATION, BEARER + " " + currentJwt.get());
            return;
        }

        // FALLBACK: Generate internal token (your teammate's solution)
        String internalJwt = generateInternalJwt();
        template.header(AUTHORIZATION, BEARER + " " + internalJwt);
    }

    private Optional<String> getCurrentUserJwt() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null
            && authentication.getCredentials() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getCredentials();
            return Optional.of(jwt.getTokenValue());
        }
        return Optional.empty();
    }

    private String generateInternalJwt() {
        // Keep your teammate's implementation exactly as-is
        byte[] keyBytes = Base64.getDecoder().decode("NjdiY2Y4MzZjOGY3ZmQxNTJiNGNlNGZhYjZiYWI1ODY5MGE2NzkwZDNmMDZhYzg0ZTExNWJjN2JkZjhkZjMxOTNlNGIxZTQ2YjRiMjAzNmM4ZGIxYTA5NTI2YzQ3NTNkYmM1Njg4YzkxMmZhMzZkNmJjM2NjMjIxYzQ1MjI2ODg=");
        SecretKey key = new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA512");

        return Jwts.builder()
            .claim("auth", List.of("INTERNAL_ADMIN"))
            .setSubject("order-service") // Changed from review-service
            .setIssuedAt(new Date())
            .setExpiration(Date.from(Instant.now().plus(Duration.ofHours(1))))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();
    }
}
