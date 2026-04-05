package org.trader.speedcheck.domain.ports;

import org.trader.speedcheck.domain.model.*;

public interface EndpointCheckStrategy
{
    AuthType getType();

    RequestResult execute
    (
        EndpointCheck   task,
        ServerTarget    server,
        AuthContext     authContext
    );
}
