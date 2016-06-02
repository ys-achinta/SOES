package soes.api;

import java.util.List;
import java.util.Objects;

public class StockExchangeServiceImpl implements StockExchangeService {

    @Override
    public Order getPurchaseOrder(String companyName, List<Order> orders) {
        for (Order order : orders) {
            if(order.getCompany().equals(companyName) && order.getSide().equals(Constants.BUY_ORDER)
                    && !Objects.equals(order.getStatus(), Constants.ORDER_STATUS_CLOSED)){
                return order;
            }
        }
        return null;
    }

    @Override
    public Order getSellOrder(String companyName, List<Order> orders) {
        for (Order order : orders) {
            if(order.getCompany().equals(companyName) && order.getSide().equals(Constants.SELL_ORDER)
                    && !Objects.equals(order.getStatus(), Constants.ORDER_STATUS_CLOSED)){
                return order;
            }
        }
        return null;
    }

    @Override
    public void processSaleAndPurchaseOrders(Order order, Order oppositeTypeOrder) {
        Order saleOrder = order.getSide().equals(Constants.SELL_ORDER) ? order : oppositeTypeOrder;
        Order purchaseOrder = order.getSide().equals(Constants.BUY_ORDER) ? order : oppositeTypeOrder;

        Integer quantityToBeSold = getTransactionQuantityForAnOrder(saleOrder);
        Integer quantityToBePurchased = getTransactionQuantityForAnOrder(purchaseOrder);

        Integer amountPurchased = updatePurchaseOrder(quantityToBeSold, quantityToBePurchased, purchaseOrder);
        updateSaleOrder(amountPurchased, quantityToBeSold, saleOrder);
    }

    @Override
    public Integer getTransactionQuantityForAnOrder(Order order){
        Integer transactionQuantity = order.getStockQuantity();
        if(order.getStockRemaining() != null){
            transactionQuantity = order.getStockRemaining();
        }
        return transactionQuantity;
    }

    @Override
    public Integer updatePurchaseOrder(Integer quantityToBeSold, Integer quantityToBePurchased,  Order purchaseOrder){
        Integer amountPurchased = null;
        if(quantityToBePurchased == 0) {
            amountPurchased = 0;
            purchaseOrder.setStockRemaining(0);
            purchaseOrder.setOrderStatus(Constants.ORDER_STATUS_CLOSED);
        }

        if(quantityToBePurchased > quantityToBeSold){
            amountPurchased = quantityToBeSold;
            purchaseOrder.setStockRemaining(quantityToBePurchased-amountPurchased);
            purchaseOrder.setOrderStatus(Constants.ORDER_STATUS_OPEN);
        } else{
            amountPurchased = quantityToBePurchased;
            purchaseOrder.setStockRemaining(0);
            purchaseOrder.setOrderStatus(Constants.ORDER_STATUS_CLOSED);
        }
        return amountPurchased;
    }

    @Override
    public void updateSaleOrder(Integer amountPurchased, Integer quantityToBeSold, Order saleOrder){
        Integer quantityRemaining = quantityToBeSold - amountPurchased;
        if(quantityRemaining == 0){
            saleOrder.setOrderStatus(Constants.ORDER_STATUS_CLOSED);
        }else{
            saleOrder.setOrderStatus(Constants.ORDER_STATUS_OPEN);
        }
        saleOrder.setStockRemaining(quantityRemaining);
    }
}
