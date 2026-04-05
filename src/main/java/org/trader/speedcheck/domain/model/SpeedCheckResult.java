package org.trader.speedcheck.domain.model;

import java.util.List;

public record SpeedCheckResult
(
        String serverName,                          // имя сервера (DEV, PREPROD, PROD 02…)
        List<EndpointCheckResult> endpointChecks    // список результатов проверок конечных точек
) {}
