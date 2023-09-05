package com.apssouza.mytrade.trading.domain.forex.orderbook;

import com.apssouza.mytrade.trading.domain.forex.common.events.Event;
import com.apssouza.mytrade.trading.domain.forex.common.observerinfra.Observer;
import com.apssouza.mytrade.trading.domain.forex.portfolio.FilledOrderDto;
import com.apssouza.mytrade.trading.domain.forex.risk.stopordercreation.StopOrderDto;
import com.apssouza.mytrade.trading.domain.forex.risk.stopordercreation.StopOrderFilledEvent;

import java.time.LocalDateTime;

class HistoryStopOrderFilledListener implements Observer {

    private final OrderBookService historyHandler;

    public HistoryStopOrderFilledListener(OrderBookService historyHandler) {
        this.historyHandler = historyHandler;
    }

    @Override
    public void update(final Event e) {
        if (!(e instanceof StopOrderFilledEvent event)) {
            return;
        }

        StopOrderDto stopOrder = event.getStopOrder();
        LocalDateTime time = event.getTimestamp();

        this.historyHandler.addOrderFilled(new FilledOrderDto(
                time,
                stopOrder.symbol(),
                stopOrder.action(),
                stopOrder.quantity(),
                stopOrder.filledPrice(),
                "",
                stopOrder.id()
        ));
    }
}
