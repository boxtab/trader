package org.trader.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@JsonPropertyOrder({
        "order_id",
        "investment_asset_type",
        "symbol",
        "opening_date",
        "closing_date",
        "type",
        "lots",
        "units",
        "volume",
        "opening_price",
        "open_rate",
        "closing_price",
        "close_rate",
        "take_profit_key",
        "take_profit_value",
        "stop_loss_key",
        "stop_loss_value",
        "commission",
        "pl"
})
public class HistoryOrderDto
{
    @JsonProperty("order_id")
    private Long orderId;

    @JsonProperty("investment_asset_type")
    private String investmentAssetType;

    private String symbol;

    @JsonProperty("opening_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime openingDate;

    @JsonProperty("closing_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime closingDate;

    private String type;

    private BigDecimal lots;

    private BigDecimal units;

    private BigDecimal volume;

    @JsonProperty("opening_price")
    private BigDecimal openingPrice;

    @JsonProperty("open_rate")
    private BigDecimal openRate;

    @JsonProperty("closing_price")
    private BigDecimal closingPrice;

    @JsonProperty("close_rate")
    private BigDecimal closeRate;

    @JsonProperty("take_profit_key")
    private Integer takeProfitKey;

    @JsonProperty("take_profit_value")
    private BigDecimal takeProfitValue;

    @JsonProperty("stop_loss_key")
    private Integer stopLossKey;

    @JsonProperty("stop_loss_value")
    private BigDecimal stopLossValue;

    private BigDecimal commission;

    private BigDecimal pl;
}
