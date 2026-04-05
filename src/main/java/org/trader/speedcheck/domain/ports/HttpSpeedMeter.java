package org.trader.speedcheck.domain.ports;

import org.trader.speedcheck.domain.model.RequestResult;

public interface HttpSpeedMeter
{
    RequestResult measureRequest
    (
            String url,
            String method,
            String body
    );

    RequestResult measureRequestWithAuth
    (
            String url,
            String method,
            String body,
            String token
    );
}
