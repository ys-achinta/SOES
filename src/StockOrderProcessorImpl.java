import java.util.List;

public class StockOrderProcessorImpl implements StockOrdersProcessor {

    StockExchangeService stockExchangeService;
    @Override
    public void processPurchaseOrders(List<Order> orders) {
        stockExchangeService = new StockExchangeServiceImpl();

        List<Order> purchaseOrders = stockExchangeService.getAllPurchaseOrders(orders);

        for (Order purchaseOrder : purchaseOrders) {
            Order saleOrder = stockExchangeService.getSaleOrderByCompanyName(purchaseOrder.getCompany(), orders);
            stockExchangeService.processAnOrder(saleOrder, purchaseOrder);
        }

    }
}
