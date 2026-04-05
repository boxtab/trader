package org.trader.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.trader.api.dto.HistoryOrderDto;
import org.trader.api.dto.PendingOrderDto;
import org.trader.api.dto.PositionOrderDto;
import org.trader.api.entity.Order;
import org.trader.api.entity.OrderState;
import org.trader.api.entity.OrderType;
import org.trader.api.entity.Quote;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface OrderMapper
{
    @Mapping(target = "orderId", source = "id")
    @Mapping(target = "accountId", source = "account.id")
    @Mapping(target = "investmentAssetType", source = "quote.investmentAssetType.name")
    @Mapping(target = "symbol", source = "quote.symbol")
    @Mapping(target = "type", source = "type", qualifiedByName = "mapOrderType")
    @Mapping(target = "volume", source = ".", qualifiedByName = "calculateVolume")
    @Mapping(target = "openingDate", source = "createdAt")
    @Mapping(target = "dynamicProfitLoss", expression = "java(calculateDynamicProfitLoss(order))")
    @Mapping(target = "grossProfit", expression = "java(java.math.BigDecimal.ZERO)")
    PositionOrderDto orderToPositionDto(Order order);

    @Mapping(target = "orderId", source = "id")
    @Mapping(target = "accountLogin", source = "account.login")
    @Mapping(target = "status", source = "state", qualifiedByName = "mapOrderState")
    @Mapping(target = "deleted", source = "deletedAt")
    @Mapping(target = "investmentAssetType", source = "quote.investmentAssetType.name")
    @Mapping(target = "symbol", source = "quote.symbol")
    @Mapping(target = "openingDate", source = "createdAt")
    @Mapping(target = "expired", source = "expired")
    @Mapping(target = "type", source = "type", qualifiedByName = "mapOrderType")
    @Mapping(target = "lots", source = "lots")
    @Mapping(target = "units", source = "units")
    @Mapping(target = "volume", source = ".", qualifiedByName = "calculateVolume")
    @Mapping(target = "activationPrice", source = "activationPrice")
    @Mapping(target = "stopLossKey", source = "stopLossKey")
    @Mapping(target = "stopLossValue", source = "stopLossValue")
    @Mapping(target = "takeProfitKey", source = "takeProfitKey")
    @Mapping(target = "takeProfitValue", source = "takeProfitValue")
    @Mapping(target = "openingPrice", source = "openPrice")
    PendingOrderDto orderToPendingDto(Order order);

    @Mapping(target = "orderId", source = "id")
    @Mapping(target = "investmentAssetType", source = "quote.investmentAssetType.name")
    @Mapping(target = "symbol", source = "quote.symbol")
    @Mapping(target = "openingDate", source = "createdAt")
    @Mapping(target = "closingDate", source = "closingDate")
    @Mapping(target = "type", source = "type", qualifiedByName = "mapOrderType")
    @Mapping(target = "lots", source = "lots")
    @Mapping(target = "units", source = "units")
    @Mapping(target = "volume", source = ".", qualifiedByName = "calculateVolume")
    @Mapping(target = "openingPrice", source = "openPrice")
    @Mapping(target = "openRate", source = "openRate")
    @Mapping(target = "closingPrice", source = "closePrice")
    @Mapping(target = "closeRate", source = "closeRate")
    @Mapping(target = "takeProfitKey", source = "takeProfitKey")
    @Mapping(target = "takeProfitValue", source = "takeProfitValue")
    @Mapping(target = "stopLossKey", source = "stopLossKey")
    @Mapping(target = "stopLossValue", source = "stopLossValue")
    @Mapping(target = "commission", source = "commission")
    @Mapping(target = "pl", source = "profitLoss")
    HistoryOrderDto orderToHistoryDto(Order order);

    @Named("mapOrderType")
    default String mapOrderType(OrderType type)
    {
        return type != null ? type.toString() : null;
    }

    default BigDecimal calculateDynamicProfitLoss(Order order)
    {
        if (order == null || order.getQuote() == null) {
            return BigDecimal.ZERO;
        }

        Quote quote = order.getQuote();

        BigDecimal openRate = order.getOpenRate() != null ? order.getOpenRate() : BigDecimal.ONE;
        BigDecimal lots = order.getLots() != null ? order.getLots() : BigDecimal.ZERO;
        BigDecimal units = order.getUnits() != null ? order.getUnits() : BigDecimal.ZERO;
        BigDecimal commission = order.getCommission() != null ? order.getCommission() : BigDecimal.ZERO;
        BigDecimal openPrice = order.getOpenPrice() != null ? order.getOpenPrice() : BigDecimal.ZERO;

        BigDecimal result;

        if (order.getType() == OrderType.BUY) {
            result = quote.getBidPrice()
                    .multiply(openRate)
                    .subtract(openPrice.multiply(openRate))
                    .multiply(lots)
                    .multiply(units)
                    .subtract(commission);
        } else {
            result = openPrice.multiply(openRate)
                    .subtract(quote.getAskPrice().multiply(openRate))
                    .multiply(lots)
                    .multiply(units)
                    .subtract(commission);
        }

        return result;
    }

    @Named("mapOrderState")
    default Integer mapOrderState(OrderState state)
    {
        return state != null ? state.getCode() : null;
    }

    @Named("calculateVolume")
    default BigDecimal calculateVolume(Order order)
    {
        if (order == null || order.getLots() == null || order.getUnits() == null) {
            return BigDecimal.ZERO;
        }
        return order.getLots().multiply(order.getUnits());
    }
}
