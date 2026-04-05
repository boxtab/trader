package org.trader.speedcheck.application.services;

import org.springframework.stereotype.Component;
import org.trader.speedcheck.domain.model.EndpointDefinition;
import org.trader.speedcheck.domain.model.ServerJsonList;
import org.trader.speedcheck.infrastructure.adapters.EndpointJsonLoader;
import org.trader.speedcheck.infrastructure.adapters.ServerJsonLoader;

import java.util.List;

@Component
public class ServerDataLoader
{
    private final ServerJsonLoader serverJsonLoader;

    private final EndpointJsonLoader endpointJsonLoader;

    public ServerDataLoader
    (
        ServerJsonLoader serverJsonLoader,
        EndpointJsonLoader endpointJsonLoader
    )
    {
        this.serverJsonLoader = serverJsonLoader;
        this.endpointJsonLoader = endpointJsonLoader;
    }

    public ServerJsonList loadServerData()
    {
        return serverJsonLoader.load( "servers.json" );
    }

    public List<EndpointDefinition> loadEndpointDefinitions()
    {
        return endpointJsonLoader.load( "endpoints.json" );
    }
}
