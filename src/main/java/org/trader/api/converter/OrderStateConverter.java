package org.trader.api.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.trader.api.entity.OrderState;

@Converter(autoApply = true)
public class OrderStateConverter implements AttributeConverter<OrderState, Integer>
{
    @Override
    public Integer convertToDatabaseColumn(OrderState orderState)
    {
        if (orderState == null) {
            return null;
        }
        return orderState.getCode();
    }

    @Override
    public OrderState convertToEntityAttribute(Integer code)
    {
        if (code == null) {
            return null;
        }
        for (OrderState state : OrderState.values()) {
            if (state.getCode() == code) {
                return state;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }
}
