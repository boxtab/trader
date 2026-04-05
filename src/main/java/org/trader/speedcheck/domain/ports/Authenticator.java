package org.trader.speedcheck.domain.ports;

import org.trader.speedcheck.domain.model.AuthType;
import org.trader.speedcheck.domain.model.TokenResult;

public interface Authenticator
{
    TokenResult login(
            String url,
            String login,
            String password,
            AuthType authType
    );
}
