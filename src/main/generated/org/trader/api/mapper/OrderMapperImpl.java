package org.trader.api.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import org.trader.api.dto.HistoryOrderDto;
import org.trader.api.dto.PendingOrderDto;
import org.trader.api.dto.PositionOrderDto;
import org.trader.api.entity.Account;
import org.trader.api.entity.InvestmentAssetType;
import org.trader.api.entity.Order;
import org.trader.api.entity.Quote;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-03T14:37:11+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Microsoft)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public PositionOrderDto orderToPositionDto(Order order) {
        if ( order == null ) {
            return null;
        }

        PositionOrderDto.PositionOrderDtoBuilder positionOrderDto = PositionOrderDto.builder();

        positionOrderDto.orderId( order.getId() );
        positionOrderDto.investmentAssetType( orderQuoteInvestmentAssetTypeName( order ) );
        positionOrderDto.symbol( orderQuoteSymbol( order ) );
        positionOrderDto.type( mapOrderType( order.getType() ) );
        positionOrderDto.volume( calculateVolume( order ) );
        positionOrderDto.openingDate( order.getCreatedAt() );
        positionOrderDto.lots( order.getLots() );
        positionOrderDto.units( order.getUnits() );
        positionOrderDto.stopLossKey( order.getStopLossKey() );
        positionOrderDto.stopLossValue( order.getStopLossValue() );
        positionOrderDto.takeProfitKey( order.getTakeProfitKey() );
        positionOrderDto.takeProfitValue( order.getTakeProfitValue() );
        positionOrderDto.openPrice( order.getOpenPrice() );
        positionOrderDto.commission( order.getCommission() );
        positionOrderDto.swaps( order.getSwaps() );
        positionOrderDto.openRate( order.getOpenRate() );

        positionOrderDto.dynamicProfitLoss( calculateDynamicProfitLoss(order) );
        positionOrderDto.grossProfit( java.math.BigDecimal.ZERO );

        return positionOrderDto.build();
    }

    @Override
    public PendingOrderDto orderToPendingDto(Order order) {
        if ( order == null ) {
            return null;
        }

        PendingOrderDto pendingOrderDto = new PendingOrderDto();

        pendingOrderDto.setOrderId( order.getId() );
        pendingOrderDto.setAccountLogin( orderAccountLogin( order ) );
        pendingOrderDto.setStatus( mapOrderState( order.getState() ) );
        pendingOrderDto.setDeleted( order.getDeletedAt() );
        pendingOrderDto.setInvestmentAssetType( orderQuoteInvestmentAssetTypeName( order ) );
        pendingOrderDto.setSymbol( orderQuoteSymbol( order ) );
        pendingOrderDto.setOpeningDate( order.getCreatedAt() );
        pendingOrderDto.setExpired( order.getExpired() );
        pendingOrderDto.setType( mapOrderType( order.getType() ) );
        pendingOrderDto.setLots( order.getLots() );
        pendingOrderDto.setUnits( order.getUnits() );
        pendingOrderDto.setVolume( calculateVolume( order ) );
        pendingOrderDto.setActivationPrice( order.getActivationPrice() );
        pendingOrderDto.setStopLossKey( order.getStopLossKey() );
        pendingOrderDto.setStopLossValue( order.getStopLossValue() );
        pendingOrderDto.setTakeProfitKey( order.getTakeProfitKey() );
        pendingOrderDto.setTakeProfitValue( order.getTakeProfitValue() );
        pendingOrderDto.setOpeningPrice( order.getOpenPrice() );

        return pendingOrderDto;
    }

    @Override
    public HistoryOrderDto orderToHistoryDto(Order order) {
        if ( order == null ) {
            return null;
        }

        HistoryOrderDto historyOrderDto = new HistoryOrderDto();

        historyOrderDto.setOrderId( order.getId() );
        historyOrderDto.setInvestmentAssetType( orderQuoteInvestmentAssetTypeName( order ) );
        historyOrderDto.setSymbol( orderQuoteSymbol( order ) );
        historyOrderDto.setOpeningDate( order.getCreatedAt() );
        historyOrderDto.setClosingDate( order.getClosingDate() );
        historyOrderDto.setType( mapOrderType( order.getType() ) );
        historyOrderDto.setLots( order.getLots() );
        historyOrderDto.setUnits( order.getUnits() );
        historyOrderDto.setVolume( calculateVolume( order ) );
        historyOrderDto.setOpeningPrice( order.getOpenPrice() );
        historyOrderDto.setOpenRate( order.getOpenRate() );
        historyOrderDto.setClosingPrice( order.getClosePrice() );
        historyOrderDto.setCloseRate( order.getCloseRate() );
        historyOrderDto.setTakeProfitKey( order.getTakeProfitKey() );
        historyOrderDto.setTakeProfitValue( order.getTakeProfitValue() );
        historyOrderDto.setStopLossKey( order.getStopLossKey() );
        historyOrderDto.setStopLossValue( order.getStopLossValue() );
        historyOrderDto.setCommission( order.getCommission() );
        historyOrderDto.setPl( order.getProfitLoss() );

        return historyOrderDto;
    }

    private String orderQuoteInvestmentAssetTypeName(Order order) {
        if ( order == null ) {
            return null;
        }
        Quote quote = order.getQuote();
        if ( quote == null ) {
            return null;
        }
        InvestmentAssetType investmentAssetType = quote.getInvestmentAssetType();
        if ( investmentAssetType == null ) {
            return null;
        }
        String name = investmentAssetType.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String orderQuoteSymbol(Order order) {
        if ( order == null ) {
            return null;
        }
        Quote quote = order.getQuote();
        if ( quote == null ) {
            return null;
        }
        String symbol = quote.getSymbol();
        if ( symbol == null ) {
            return null;
        }
        return symbol;
    }

    private String orderAccountLogin(Order order) {
        if ( order == null ) {
            return null;
        }
        Account account = order.getAccount();
        if ( account == null ) {
            return null;
        }
        String login = account.getLogin();
        if ( login == null ) {
            return null;
        }
        return login;
    }
}
