package soes.api;
import java.util.List;

public interface StockExchangeService {

    Order getSellOrder(String companyName, List<Order> orders);

    Order getPurchaseOrder(String companyName, List<Order> orders);

    void processSaleAndPurchaseOrders(Order saleOrder, Order purchaseOrder);

    Integer getTransactionQuantityForAnOrder(Order order);

    Integer updatePurchaseOrder(Integer quantityToBeSold, Integer quantityToBePurchased,  Order purchaseOrder);

    void updateSaleOrder(Integer amountPurchased, Integer quantityToBeSold, Order saleOrder);

}
