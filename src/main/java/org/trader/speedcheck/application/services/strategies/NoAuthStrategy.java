package org.trader.speedcheck.application.services.strategies;

import org.springframework.stereotype.Component;
import org.trader.speedcheck.domain.model.*;
import org.trader.speedcheck.domain.ports.EndpointCheckStrategy;
import org.trader.speedcheck.infrastructure.adapters.HttpSpeedMeterAdapter;

@Component
public class NoAuthStrategy implements EndpointCheckStrategy
{
    private final HttpSpeedMeterAdapter httpClientAdapter;

    public NoAuthStrategy( HttpSpeedMeterAdapter httpClientAdapter )
    {
        this.httpClientAdapter = httpClientAdapter;
    }

    @Override
    public AuthType getType()
    {
        return AuthType.NONE;
    }

    @Override
    public RequestResult execute( EndpointCheck task, ServerTarget server, AuthContext authContext )
    {
        return httpClientAdapter.measureRequest(
                task.fullUrl(),
                task.method(),
                task.body()
        );
    }
}
