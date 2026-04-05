package org.trader.speedcheck.domain.model;

import java.util.List;

public record ServerTarget
(
    String name,
    String baseUrl,

    String traderLogin,
    String traderPassword,

    String managerLogin,
    String managerPassword,

    List<EndpointCheck> endpointChecks
) {}
