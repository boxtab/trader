package org.trader.speedcheck.domain.model;

public record EndpointCheckResult
(
        String  endpointPath,           // путь эндпоинта (/api/v1/login)
        long    responseTimeMs,         // время отклика (мс)
        int     httpStatus              // HTTP статус код (200, 500…)
) {}
