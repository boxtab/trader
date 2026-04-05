package org.trader.speedcheck.domain.model;

public record TokenResult
(
        String token,
        long duration,
        int statusCode
) {}
