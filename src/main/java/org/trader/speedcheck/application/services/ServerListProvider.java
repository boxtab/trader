package org.trader.speedcheck.application.services;

import org.springframework.stereotype.Component;
import org.trader.speedcheck.application.factories.ServerInfoFactory;
import org.trader.speedcheck.domain.model.*;

import java.util.List;

@Component
public class ServerListProvider
{
    private final ServerDataLoader dataLoader;
    private final ServerInfoFactory infoFactory;

    public ServerListProvider
    (
        ServerDataLoader dataLoader,
        ServerInfoFactory infoFactory
    )
    {
        this.dataLoader     = dataLoader;
        this.infoFactory    = infoFactory;
    }

    public List<ServerTarget> getServers()
    {
        ServerJsonList jsonList = dataLoader.loadServerData();
        List<EndpointDefinition> definitions = dataLoader.loadEndpointDefinitions();

        return jsonList.getServers().stream()
                .map( entry -> infoFactory.create( entry, definitions ) )
                .toList();
    }
}
