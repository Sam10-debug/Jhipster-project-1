package com.emorinken.review.service.client;

import com.emorinken.review.service.dto.BookDTO;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BookServiceClient {

    private final RestTemplate restTemplate;

    public BookServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public BookDTO getBookByIsbn(String isbn){
        String API_URL = "http://localhost:8080/services/bookapp/api/books/isbn/" + isbn;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + getCurrentToken());
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<BookDTO> response = restTemplate.exchange(
            API_URL,
            HttpMethod.GET,
            entity,
            BookDTO.class
        );

        return response.getBody();
    }

    private String getCurrentToken() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof JwtAuthenticationToken jwtAuth) {
            return jwtAuth.getToken().getTokenValue();
        }
        return null;
    }
}
