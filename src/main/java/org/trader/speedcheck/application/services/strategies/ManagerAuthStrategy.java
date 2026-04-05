package org.trader.speedcheck.application.services.strategies;

import org.springframework.stereotype.Component;
import org.trader.speedcheck.domain.model.*;
import org.trader.speedcheck.domain.ports.EndpointCheckStrategy;
import org.trader.speedcheck.application.services.AuthService;
import org.trader.speedcheck.infrastructure.adapters.HttpSpeedMeterAdapter;

@Component
public class ManagerAuthStrategy implements EndpointCheckStrategy
{
    private final AuthService authService;
    private final HttpSpeedMeterAdapter httpClientAdapter;

    public ManagerAuthStrategy( AuthService authService, HttpSpeedMeterAdapter httpClientAdapter )
    {
        this.authService        = authService;
        this.httpClientAdapter  = httpClientAdapter;
    }

    @Override
    public AuthType getType()
    {
        return AuthType.MANAGER;
    }

    @Override
    public RequestResult execute( EndpointCheck task, ServerTarget server, AuthContext authContext )
    {
        if ( task.fullUrl().endsWith("/login") ) {
            TokenResult token = authService.login(
                    task.fullUrl(),
                    server.managerLogin(),
                    server.managerPassword(),
                    AuthType.MANAGER
            );

            authContext.setManagerToken( token.token() );
            int statusCode = token.token() != null ? 200 : 401;

            return new RequestResult( token.duration(), statusCode );
        }

        return httpClientAdapter.measureRequestWithAuth(
                task.fullUrl(),
                task.method(),
                task.body(),
                authContext.getManagerToken()
        );
    }
}
