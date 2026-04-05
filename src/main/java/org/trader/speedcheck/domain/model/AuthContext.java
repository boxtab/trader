package org.trader.speedcheck.domain.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthContext
{
    private String traderToken;
    private String managerToken;
}
