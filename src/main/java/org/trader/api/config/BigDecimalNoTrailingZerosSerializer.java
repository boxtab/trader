package org.trader.api.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;

public class BigDecimalNoTrailingZerosSerializer extends JsonSerializer<BigDecimal>
{
    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException
    {
        if (value == null) {
            gen.writeNull();
            return;
        }

        // Для целых чисел убираем дробную часть полностью
        if (value.stripTrailingZeros().scale() <= 0) {
            gen.writeNumber(value.longValue());
        } else {
            // Для дробных чисел убираем только незначащие нули
            gen.writeNumber(value.stripTrailingZeros());
        }
    }
}
