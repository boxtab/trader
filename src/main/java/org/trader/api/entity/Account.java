package org.trader.api.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "accounts")
@Data
public class Account
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "currency_id", nullable = false)
    private Long currencyId;

    @Column(name = "balance", precision = 18, scale = 2, nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(name = "credit", precision = 18, scale = 2, nullable = false)
    private BigDecimal credit = BigDecimal.ZERO;

    @Column(name = "leverage", nullable = false)
    private Long leverage;

    @Column(name = "type", nullable = false)
    private Short type = 0;

    @Column(name = "equity", precision = 20, scale = 8, nullable = false)
    private BigDecimal equity;

    @Column(name = "margin", precision = 20, scale = 8)
    private BigDecimal margin;

    @Column(name = "free_margin", precision = 20, scale = 8, nullable = false)
    private BigDecimal freeMargin;

    @Column(name = "margin_indicator", precision = 20, scale = 8, nullable = false)
    private BigDecimal marginIndicator;

    @Column(name = "margin_level", precision = 20, scale = 8, nullable = false)
    private BigDecimal marginLevel;

    @Column(name = "profit_loss", precision = 20, scale = 8, nullable = false)
    private BigDecimal profitLoss;

    @Column(name = "profit_loss_opened_orders", precision = 20, scale = 8)
    private BigDecimal profitLossOpenedOrders;

    @Column(name = "status", nullable = false)
    private Integer status = 0;

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "uuid", nullable = false, columnDefinition = "char(36)")
    private UUID uuid;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "remember_token", length = 100)
    private String rememberToken;

    @Column(name = "last_login_time")
    private LocalDateTime lastLoginTime;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
