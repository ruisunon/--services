package com.apssouza.mytrade.trading.domain.forex.risk.stopordercreation;

import com.apssouza.mytrade.trading.domain.forex.common.TradingParams;
import com.apssouza.mytrade.trading.domain.forex.portfolio.PortfolioHandlerBuilder;
import com.apssouza.mytrade.trading.domain.forex.risk.RiskManagementFactory;
import com.apssouza.mytrade.trading.domain.forex.risk.RiskManagementService;

public class RiskManagementBuilder {

    public RiskManagementService build() {
        var stopOrder = new StopOrderConfigDto(
                TradingParams.hard_stop_loss_distance,
                TradingParams.take_profit_distance_fixed,
                TradingParams.entry_stop_loss_distance_fixed,
                TradingParams.trailing_stop_loss_distance
        );
        return RiskManagementFactory.create(new StopOrderCreatorFixed(stopOrder));
    }
}
