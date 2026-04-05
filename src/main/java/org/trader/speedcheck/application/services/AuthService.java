package org.trader.speedcheck.application.services;

import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.trader.speedcheck.domain.model.AuthType;
import org.trader.speedcheck.domain.model.TokenResult;
import org.trader.speedcheck.domain.ports.AuthClient;
import org.trader.speedcheck.domain.ports.Authenticator;
import org.trader.speedcheck.domain.ports.TokenExtractorStrategy;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AuthService implements Authenticator
{
    private final AuthClient authClient;
    private final Map<AuthType, TokenExtractorStrategy> extractors;

    public AuthService( AuthClient authClient, List<TokenExtractorStrategy> extractorList )
    {
        this.authClient = authClient;
        this.extractors = extractorList.stream()
                .collect(
                        Collectors.toMap( TokenExtractorStrategy::getType, Function.identity() )
                );
    }

    @Override
    public TokenResult login( String url, String login, String password, AuthType authType )
    {
        long start = System.currentTimeMillis();

        try {
            ResponseEntity<Map> response = authClient.sendLoginRequest( url, login, password );
            long duration = System.currentTimeMillis() - start;
            int statusCode = response.getStatusCode().value();

            if (response.getStatusCode().is2xxSuccessful()) {
                String token = extractors.get( authType ).extract( response.getBody() );
                return new TokenResult( token, duration, statusCode );
            } else {
                return new TokenResult( null, duration, statusCode );
            }
        } catch (Exception e) {
            return new TokenResult( null, -1, 500 );
        }
    }
}
