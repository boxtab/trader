package org.trader.speedcheck.application.factories;

import org.springframework.stereotype.Component;
import org.trader.speedcheck.domain.model.EndpointDefinition;
import org.trader.speedcheck.domain.model.ServerTarget;
import org.trader.speedcheck.domain.model.ServerEntry;
import org.trader.speedcheck.domain.ports.SecretsProvider;
import org.trader.speedcheck.domain.services.EndpointTaskBuilder;

import java.util.List;

@Component
public class ServerInfoFactory
{
    private final SecretsProvider secretsProvider;
    private final EndpointTaskBuilder taskBuilder;

    public ServerInfoFactory
    (
        SecretsProvider secretsProvider,
        EndpointTaskBuilder taskBuilder
    )
    {
        this.secretsProvider = secretsProvider;
        this.taskBuilder = taskBuilder;
    }

    public ServerTarget create
    (
        ServerEntry serverEntry,
        List<EndpointDefinition> endpointDefinitions
    )
    {
//        System.out.println("Server name: " + serverEntry.getName());
//        System.out.println("Trader password key: " + serverEntry.getName() + ".traderPassword");
//        System.out.println("Manager password key: " + serverEntry.getName() + ".managerPassword");
//        System.out.println("Trader password value: " + secretsProvider.get(serverEntry.getName() + ".traderPassword"));
//        System.out.println("Manager password value: " + secretsProvider.get(serverEntry.getName() + ".managerPassword"));

        return new ServerTarget
                (
                    serverEntry.getName(),
                    serverEntry.getBaseUrl(),

                    serverEntry.getTraderLogin(),
                    secretsProvider.get( serverEntry.getName() + ".traderPassword" ),

                    serverEntry.getManagerLogin(),
                    secretsProvider.get( serverEntry.getName() + ".managerPassword" ),

                    taskBuilder.buildTasks( serverEntry.getBaseUrl(), endpointDefinitions )
                );
    }
}
