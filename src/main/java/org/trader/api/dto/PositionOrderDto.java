package org.trader.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.trader.api.config.BigDecimalNoTrailingZerosSerializer;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
        "order_id",
        "account_id",
        "investment_asset_type",
        "symbol",
        "type",
        "lots",
        "units",
        "volume",
        "opening_date",
        "stop_loss_key",
        "stop_loss_value",
        "take_profit_key",
        "take_profit_value",
        "opening_price",
        "dynamic_profit_loss",
        "commission",
        "swaps",
        "gross_profit",
        "open_rate"
})
public class PositionOrderDto
{
    @JsonProperty("order_id")
    private Long orderId;

    @JsonProperty("account_id")
    private Long accountId;

    @JsonProperty("investment_asset_type")
    private String investmentAssetType;

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("type")
    private String type;

    @JsonProperty("lots")
    @JsonSerialize(using = BigDecimalNoTrailingZerosSerializer.class)
    private BigDecimal lots;

    @JsonProperty("units")
    @JsonSerialize(using = BigDecimalNoTrailingZerosSerializer.class)
    private BigDecimal units;

    @JsonProperty("volume")
    @JsonSerialize(using = BigDecimalNoTrailingZerosSerializer.class)
    private BigDecimal volume;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("opening_date")
    private LocalDateTime openingDate;

    @JsonProperty("stop_loss_key")
    private Integer stopLossKey;

    @JsonProperty("stop_loss_value")
    @JsonSerialize(using = BigDecimalNoTrailingZerosSerializer.class)
    private BigDecimal stopLossValue;

    @JsonProperty("take_profit_key")
    private Integer takeProfitKey;

    @JsonProperty("take_profit_value")
    @JsonSerialize(using = BigDecimalNoTrailingZerosSerializer.class)
    private BigDecimal takeProfitValue;

    @JsonProperty("opening_price")
    @JsonSerialize(using = BigDecimalNoTrailingZerosSerializer.class)
    private BigDecimal openPrice;

    @JsonProperty("dynamic_profit_loss")
    @JsonSerialize(using = BigDecimalNoTrailingZerosSerializer.class)
    private BigDecimal dynamicProfitLoss;

    @JsonProperty("commission")
    @JsonSerialize(using = BigDecimalNoTrailingZerosSerializer.class)
    private BigDecimal commission;

    @JsonProperty("swaps")
    @JsonSerialize(using = BigDecimalNoTrailingZerosSerializer.class)
    private BigDecimal swaps;

    @JsonProperty("gross_profit")
    @JsonSerialize(using = BigDecimalNoTrailingZerosSerializer.class)
    private BigDecimal grossProfit;

    @JsonProperty("open_rate")
    @JsonSerialize(using = BigDecimalNoTrailingZerosSerializer.class)
    private BigDecimal openRate;
}
