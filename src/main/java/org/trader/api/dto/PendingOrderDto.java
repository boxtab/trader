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
        "account_login",
        "status",
        "deleted",
        "investment_asset_type",
        "symbol",
        "opening_date",
        "expired",
        "type",
        "lots",
        "units",
        "volume",
        "activation_price",
        "stop_loss_key",
        "stop_loss_value",
        "take_profit_key",
        "take_profit_value",
        "opening_price"
})
public class PendingOrderDto
{
    @JsonProperty("order_id")
    private Long orderId;

    @JsonProperty("account_login")
    private String accountLogin;

    @JsonProperty("status")
    private Integer status;

    private LocalDateTime deleted;

    @JsonProperty("investment_asset_type")
    private String investmentAssetType;

    private String symbol;

    @JsonProperty("opening_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime openingDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expired;

    private String type;

    private BigDecimal lots;

    private BigDecimal units;

    private BigDecimal volume;

    @JsonProperty("activation_price")
    private BigDecimal activationPrice;

    @JsonProperty("stop_loss_key")
    private Integer stopLossKey;

    @JsonProperty("stop_loss_value")
    private BigDecimal stopLossValue;

    @JsonProperty("take_profit_key")
    private Integer takeProfitKey;

    @JsonProperty("take_profit_value")
    private BigDecimal takeProfitValue;

    @JsonProperty("opening_price")
    private BigDecimal openingPrice;
}
