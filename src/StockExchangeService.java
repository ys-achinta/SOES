import java.util.List;

public interface StockExchangeService {

    public Order getSaleOrderByCompanyName(String companyName, List<Order> orders);

    public List<Order> getAllPurchaseOrders(List<Order> orders);

    public void processAnOrder(Order saleOrder, Order purchaseOrder);

}
