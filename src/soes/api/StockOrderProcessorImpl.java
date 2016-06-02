package soes.api;

import java.util.List;
import java.util.Objects;

public class StockOrderProcessorImpl implements StockOrdersProcessor {

    StockExchangeService stockExchangeService;
    @Override
    public void processOrders(List<Order> orders) {
        stockExchangeService = new StockExchangeServiceImpl();

        for (Order order : orders) {
            if(!Objects.equals(order.getStatus(), Constants.ORDER_STATUS_CLOSED)){
                Order oppositeTypeOrder = null;
                if(order.getSide().equals(Constants.BUY_ORDER)){
                    oppositeTypeOrder = stockExchangeService.getSellOrder(order.getCompany(), orders);
                }
                else
                if(order.getSide().equals(Constants.SELL_ORDER)){
                    oppositeTypeOrder = stockExchangeService.getPurchaseOrder(order.getCompany(), orders);
                }

                if(oppositeTypeOrder != null){
                    stockExchangeService.processSaleAndPurchaseOrders(order, oppositeTypeOrder);
                }
            }
        }
    }
}
