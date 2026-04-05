package org.trader.speedcheck.domain.model;

public record RequestResult
(
        long durationMs,
        int statusCode
) {}
