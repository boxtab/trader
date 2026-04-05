package org.trader.speedcheck.application.services;

import org.springframework.stereotype.Component;
import org.trader.speedcheck.application.services.strategies.FallbackStrategy;
import org.trader.speedcheck.domain.model.*;
import org.trader.speedcheck.domain.ports.EndpointCheckStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ServerChecker
{
    private final Map<AuthType, EndpointCheckStrategy> strategies;

    public ServerChecker( List<EndpointCheckStrategy> strategyList )
    {
        this.strategies = strategyList
                .stream()
                .collect(
                        Collectors.toMap( EndpointCheckStrategy::getType, Function.identity() )
                );
    }

    public SpeedCheckResult check( ServerTarget serverTarget )
    {
//        System.out.println("Trader password: " + serverTarget.traderPassword());
//        System.out.println("Manager password: " + serverTarget.managerPassword());

        List<EndpointCheckResult> endpointCheckResults = new ArrayList<>();
        AuthContext authContext = new AuthContext();

        for ( EndpointCheck task : serverTarget.endpointChecks() )
        {
            EndpointCheckStrategy strategy = strategies.getOrDefault( task.authType(), new FallbackStrategy() );

            RequestResult exec = strategy.execute( task, serverTarget, authContext );
            endpointCheckResults.add( new EndpointCheckResult( task.name(), exec.durationMs(), exec.statusCode() ) );

//            System.out.println("CHECK: " + task.fullUrl() + " [" + task.method() + "]");
        }

        return new SpeedCheckResult( serverTarget.name(), endpointCheckResults );
    }
}
