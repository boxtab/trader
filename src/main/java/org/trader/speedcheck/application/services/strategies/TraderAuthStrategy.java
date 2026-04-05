package org.trader.speedcheck.application.services.strategies;

import org.springframework.stereotype.Component;
import org.trader.speedcheck.domain.model.*;
import org.trader.speedcheck.domain.ports.EndpointCheckStrategy;
import org.trader.speedcheck.application.services.AuthService;
import org.trader.speedcheck.infrastructure.adapters.HttpSpeedMeterAdapter;

@Component
public class TraderAuthStrategy implements EndpointCheckStrategy
{
    private final AuthService authService;
    private final HttpSpeedMeterAdapter httpClientAdapter;

    public TraderAuthStrategy( AuthService authService, HttpSpeedMeterAdapter httpClientAdapter )
    {
        this.authService        = authService;
        this.httpClientAdapter  = httpClientAdapter;
    }

    @Override
    public AuthType getType()
    {
        return AuthType.TRADER;
    }

    @Override
    public RequestResult execute( EndpointCheck task, ServerTarget server, AuthContext authContext )
    {
        // логинимся только если эндпоинт оканчивается на /login
        if ( task.fullUrl().endsWith("/login") ) {
            TokenResult token = authService.login(
                    task.fullUrl(),
                    server.traderLogin(),
                    server.traderPassword(),
                    AuthType.TRADER
            );

            authContext.setTraderToken( token.token() );
            int statusCode = token.token() != null ? 200 : 401;

            return new RequestResult( token.duration(), statusCode );
        }

        // обычные запросы с токеном
        return httpClientAdapter.measureRequestWithAuth(
                task.fullUrl(),
                task.method(),
                task.body(),
                authContext.getTraderToken()
        );
    }
}
