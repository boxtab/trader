package org.trader.api.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
public class Order
{
    @Id
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type", nullable = false)
    private OrderType type;

    // Отношение к Quote
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quote_id", insertable = false, updatable = false)
    private Quote quote;

    // Оригинальное поле quote_id для обратной совместимости
    @Column(name = "quote_id")
    private Long quoteId;

    @Column(name = "currency_id")
    private Long currencyId;

    @Column(name = "lots", precision = 20, scale = 8)
    private BigDecimal lots;

    @Column(name = "units", precision = 20, scale = 8)
    private BigDecimal units;

    @Column(name = "margin", precision = 20, scale = 8)
    private BigDecimal margin;

    @Column(name = "stop_loss_key")
    private Integer stopLossKey;

    @Column(name = "stop_loss_value", precision = 20, scale = 8)
    private BigDecimal stopLossValue;

    @Column(name = "take_profit_key")
    private Integer takeProfitKey;

    @Column(name = "take_profit_value", precision = 20, scale = 8)
    private BigDecimal takeProfitValue;

    @Column(name = "expired")
    private LocalDateTime expired;

    @Column(name = "activation_price", precision = 20, scale = 8)
    private BigDecimal activationPrice;

    @Column(name = "open_price", precision = 20, scale = 8)
    private BigDecimal openPrice;

    @Column(name = "open_rate", precision = 20, scale = 8)
    private BigDecimal openRate;

    @Column(name = "close_price", precision = 20, scale = 8)
    private BigDecimal closePrice;

    @Column(name = "close_rate", precision = 20, scale = 8)
    private BigDecimal closeRate;

    @Column(name = "commission", precision = 20, scale = 8)
    private BigDecimal commission;

    @Column(name = "swaps", precision = 20, scale = 8)
    private BigDecimal swaps;

    @Column(name = "profit_loss", precision = 20, scale = 8)
    private BigDecimal profitLoss;

    @Column(name = "state")
    private OrderState state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", insertable = false, updatable = false)
    private Account account;


    @Column(name = "closing_date")
    private LocalDateTime closingDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
