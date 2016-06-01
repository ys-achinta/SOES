import java.util.List;

public interface StockExchangeService {

    public Order getSellOrder(String companyName, List<Order> orders);

    public Order getPurchaseOrder(String companyName, List<Order> orders);

    public void processSaleAndPurchaseOrders(Order saleOrder, Order purchaseOrder);

    public Integer getTransactionQuantityForAnOrder(Order order);

    public Integer updatePurchaseOrder(Integer quantityToBeSold, Integer quantityToBePurchased,  Order purchaseOrder);

    public void updateSaleOrder(Integer amountPurchased, Integer quantityToBeSold, Order saleOrder);

}
