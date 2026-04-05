package org.trader.speedcheck.infrastructure.adapters;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.trader.speedcheck.domain.ports.AuthClient;

import java.util.Map;

@Component
public class AuthClientAdapter implements AuthClient
{
    private final RestTemplate restTemplate;

    public AuthClientAdapter( RestTemplateBuilder builder )
    {
        this.restTemplate = builder.build();
    }

    @Override
    public ResponseEntity<Map> sendLoginRequest( String url, String login, String password )
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON );

        Map<String, String> payload = Map.of(  "login", login, "password", password );
        HttpEntity<Map<String, String>> request = new HttpEntity<>( payload, headers );

        return restTemplate.postForEntity( url, request, Map.class );
    }
}
