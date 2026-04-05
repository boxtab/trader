package org.trader.api.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "quote")
@Data
public class Quote
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "symbol", unique = true, nullable = false)
    private String symbol;

    @Column(name = "currency_id")
    private Long currencyId;

    @Column(name = "data_source")
    private String dataSource;

    @Column(name = "exchange_id")
    private Long exchangeId;

    @Column(name = "quoteinformer_investment_asset_type_id")
    private Long quoteinformerInvestmentAssetTypeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "investment_asset_type_id", nullable = false)
    private InvestmentAssetType investmentAssetType;

    @Column(name = "category")
    private String category;

    @Column(name = "country")
    private String country;

    @Column(name = "candle_type_id")
    private Long candleTypeId;

    @Column(name = "bid_price", precision = 20, scale = 8, nullable = false)
    private BigDecimal bidPrice;

    @Column(name = "ask_price", precision = 20, scale = 8, nullable = false)
    private BigDecimal askPrice;

    @Column(name = "price_snapshot", columnDefinition = "json")
    private String priceSnapshot;

    @Column(name = "lots", precision = 20, scale = 8, nullable = false)
    private BigDecimal lots;

    @Column(name = "is_trading", nullable = false)
    private Boolean isTrading;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "description", length = 4096)
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
