import java.util.ArrayList;
import java.util.List;

public class StockExchangeServiceImpl implements StockExchangeService {

    @Override
    public Order getSaleOrderByCompanyName(String companyName, List<Order> orders) {
        for (Order order : orders) {
            if(order.getCompany().equals(companyName) && order.getSide().equals("Sell")){
                return order;
            }
        }
        return null;
    }

    @Override
    public List<Order> getAllPurchaseOrders(List<Order> orders) {
        List<Order> allPurchaseOrders = new ArrayList<>();
        for (Order order : orders) {
            if(order.getSide().equals("Buy")){
                allPurchaseOrders.add(order);
            }
        }
        return allPurchaseOrders;
    }

    @Override
    public void processAnOrder(Order saleOrder, Order purchaseOrder) {
        if(saleOrder.getStockQuantity() > purchaseOrder.getStockQuantity()){
            Integer quantity = saleOrder.getStockQuantity() - purchaseOrder.getStockQuantity();

            saleOrder.setStockRemaining(quantity);
            saleOrder.setStockQuantity(quantity);
            saleOrder.setOrderStatus("Open");

            purchaseOrder.setOrderStatus("Closed");
            purchaseOrder.setStockRemaining(0);
        }
        else if(purchaseOrder.getStockQuantity() > saleOrder.getStockQuantity()){
            Integer quantity = purchaseOrder.getStockQuantity() - saleOrder.getStockQuantity();

            purchaseOrder.setStockRemaining(quantity);
            purchaseOrder.setStockQuantity(quantity);
            purchaseOrder.setOrderStatus("Open");

            saleOrder.setOrderStatus("Closed");
            saleOrder.setStockRemaining(0);

        }
        else{
            saleOrder.setStockRemaining(0);
            saleOrder.setOrderStatus("Closed");

            purchaseOrder.setStockRemaining(0);
            purchaseOrder.setOrderStatus("Closed");
        }


    }
}
