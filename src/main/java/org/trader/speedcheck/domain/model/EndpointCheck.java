package org.trader.speedcheck.domain.model;

public record EndpointCheck
(
        String   name,
        String   fullUrl,
        AuthType authType,
        String   method,   // "GET" или "POST"
        String   body      // JSON-тело (для POST)
) {}
