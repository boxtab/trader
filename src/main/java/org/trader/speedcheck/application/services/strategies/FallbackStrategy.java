package org.trader.speedcheck.application.services.strategies;

import org.trader.speedcheck.domain.model.*;
import org.trader.speedcheck.domain.ports.EndpointCheckStrategy;

public class FallbackStrategy implements EndpointCheckStrategy
{
    @Override
    public AuthType getType()
    {
        return null;
    }

    @Override
    public RequestResult execute( EndpointCheck task, ServerTarget server, AuthContext authContext )
    {
        return new RequestResult( -1, 500 );
    }
}

