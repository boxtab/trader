package org.trader.api.entity;

import lombok.Getter;

@Getter
public enum OrderState
{
    POSITION(1),
    HISTORY(2),
    PENDING(3);

    private final int code;

    OrderState(int code)
    {
        this.code = code;
    }
}
