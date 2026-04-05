package org.trader.speedcheck.domain.ports;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface AuthClient
{
    ResponseEntity<Map> sendLoginRequest
    (
            String url,
            String login,
            String password
    );
}
