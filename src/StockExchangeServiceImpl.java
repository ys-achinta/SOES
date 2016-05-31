import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StockExchangeServiceImpl implements StockExchangeService {

    @Override
    public Order getSaleOrderByCompanyName(String companyName, List<Order> orders) {
        for (Order order : orders) {
            if(order.getCompany().equals(companyName) && order.getSide().equals("Sell") && !Objects.equals(order.getStatus(), "Closed")){
                return order;
            }
        }
        return null;
    }

    @Override
    public List<Order> getAllPurchaseOrders(List<Order> orders) {
        List<Order> allPurchaseOrders = new ArrayList<>();
        for (Order order : orders) {
            if(order.getSide().equals("Buy") && !Objects.equals(order.getStatus(), "Closed")){
                allPurchaseOrders.add(order);
            }
        }
        return allPurchaseOrders;
    }

    @Override
    public void processAnOrder(Order saleOrder, Order purchaseOrder) {
        updatePurchaseOrder(saleOrder, purchaseOrder);
        updateSaleOrder(saleOrder, purchaseOrder);
    }

    private void updateSaleOrder(Order saleOrder, Order purchaseOrder){
        Integer sellingQuantity = saleOrder.getStockQuantity();
        if(saleOrder.getStockRemaining() != null){
            sellingQuantity = saleOrder.getStockRemaining();
        }

        Integer purchasingQuantity = purchaseOrder.getStockQuantity();

        if(sellingQuantity > purchasingQuantity){
            saleOrder.setStockRemaining(sellingQuantity - purchasingQuantity);
            saleOrder.setOrderStatus("Open");
        }
        else {
            saleOrder.setStockRemaining(0);
            saleOrder.setOrderStatus("Closed");
        }
    }

    private void updatePurchaseOrder(Order saleOrder, Order purchaseOrder){
        Integer sellingQuantity = saleOrder.getStockQuantity();
        if(saleOrder.getStockRemaining() != null){
            sellingQuantity = saleOrder.getStockRemaining();
        }

        Integer purchasingQuantity = purchaseOrder.getStockQuantity();

        if(purchasingQuantity > sellingQuantity){
            purchaseOrder.setStockRemaining(purchasingQuantity-sellingQuantity);
            purchaseOrder.setOrderStatus("Open");
        }
        else{
            purchaseOrder.setStockRemaining(0);
            purchaseOrder.setOrderStatus("Closed");
        }
    }
}
