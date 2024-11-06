package de.szut.lf8_starter.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
//Author: Tobias
@Service
public abstract class RequestService {

    @Getter
    private final RestTemplate restTemplate;

    private final HttpHeaders headers = new HttpHeaders();

    public RequestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        headers.set("accept", "application/json");
    }

    protected HttpEntity<String> getEntityWithAuthorization() {
        headers.set("Authorization", getJwtToken());
        return new HttpEntity<>(headers);
    }

    protected String getJwtToken() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof JwtAuthenticationToken) {
            JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) auth;
            Jwt jwt = jwtAuthenticationToken.getToken();
            String tokenValue = jwt.getTokenValue(); // Hier bekommst du den reinen JWT-String
            return "Bearer " + tokenValue; // Der pure JWT-Token wird zurückgegeben
        }

        return "No JWT Token found!";
    }

}
