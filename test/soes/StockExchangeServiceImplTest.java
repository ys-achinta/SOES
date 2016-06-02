package soes;

import org.junit.Test;
import soes.api.Order;
import soes.api.StockExchangeService;
import soes.api.impl.StockExchangeServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

public class StockExchangeServiceImplTest {

    public StockExchangeService stockExchangeService;

    @Test
    public void shouldGetTransactionQuantityForAnOrder(){
        stockExchangeService = new StockExchangeServiceImpl();
        Integer quantityForTransaction;

        Order order1 = new Order(1,"Sell","XYZ", 10);
        quantityForTransaction = stockExchangeService.getTransactionQuantityForAnOrder(order1);
        assertEquals("Before processing totalQuantity is the transactional quantity",quantityForTransaction, order1.getStockQuantity());

        order1.setStockRemaining(8);
        quantityForTransaction = stockExchangeService.getTransactionQuantityForAnOrder(order1);
        assertNotEquals("After processing totalQuantity is not the transactional quantity ", quantityForTransaction, order1.getStockQuantity());
        assertEquals("After processing remainingQuantity is the transactional quantity",quantityForTransaction, order1.getStockRemaining());
    }

    @Test
    public void shouldUpdateTheStatusAndRemainingQuantityOfASellOrder1(){
        stockExchangeService = new StockExchangeServiceImpl();
        Order order1 = new Order(1,"Sell","XYZ", 10);
        Integer quantityToBeSold = order1.getStockQuantity();
        Integer amountPurchased = 10;

        stockExchangeService.updateSaleOrder(amountPurchased, quantityToBeSold, order1);
        assertEquals("", "Closed", order1.getStatus());
        assertEquals("", new Integer(0), order1.getStockRemaining());
    }

    @Test
    public void shouldUpdateTheStatusAndRemainingQuantityOfASellOrder2(){
        stockExchangeService = new StockExchangeServiceImpl();
        Order order1 = new Order(1,"Sell","XYZ", 10);
        order1.setStockRemaining(8);

        Integer quantityToBeSold = order1.getStockRemaining();
        Integer amountPurchased = 5;

        stockExchangeService.updateSaleOrder(amountPurchased, quantityToBeSold, order1);
        assertEquals("Status is open since purchase is less than to be sold", "Open", order1.getStatus());
        assertEquals("Remaining Value is calculated from the initial remaining amount if present",
                new Integer(quantityToBeSold-amountPurchased), order1.getStockRemaining());
        assertNotEquals("Remaining Value not is calculated from the total amount",
                new Integer(order1.getStockQuantity()-amountPurchased), order1.getStockRemaining());
    }

    @Test
    public void shouldUpdateTheStatusAndRemainingQuantityOfAPurchaseOrder1(){
        stockExchangeService = new StockExchangeServiceImpl();
        Order order1 = new Order(1,"Buy","XYZ", 20);
        order1.setStockRemaining(8);

        Integer quantityToBePurchased = order1.getStockRemaining();
        Integer quantityToBeSold = 5;

        stockExchangeService.updatePurchaseOrder(quantityToBeSold, quantityToBePurchased, order1);
        assertEquals("Purchase soes.api.Order Status Is Open Since Less Quantity Is Sold Than Already Present",
                "Open", order1.getStatus());
        assertEquals("Purchase soes.api.Order Remaining Quantity",
                new Integer(quantityToBePurchased-quantityToBeSold) , order1.getStockRemaining());
    }

    @Test
    public void shouldUpdateTheStatusAndRemainingQuantityOfAPurchaseOrder2(){
        stockExchangeService = new StockExchangeServiceImpl();
        Order order1 = new Order(1,"Buy","XYZ", 20);
        order1.setStockRemaining(2);

        Integer quantityToBePurchased = order1.getStockRemaining();
        Integer quantityToBeSold = 5;

        stockExchangeService.updatePurchaseOrder(quantityToBeSold, quantityToBePurchased, order1);
        assertEquals("Purchase soes.api.Order Status Is Closed Since Less Quantity Is Purchased Than Sold",
                "Closed", order1.getStatus());
        assertEquals("Purchase soes.api.Order Remaining Quantity",
                new Integer(0) , order1.getStockRemaining());
    }

    @Test
    public void shouldGetTheAmountPurchasedFromAPurchaseOrder(){
        stockExchangeService = new StockExchangeServiceImpl();
        Order order1 = new Order(1, "Buy", "XYZ", 10);

        Integer quantityToBePurchased =  order1.getStockQuantity();
        Integer quantityToBeSold = 20;

        Integer amountPurchased = stockExchangeService.updatePurchaseOrder(quantityToBeSold, quantityToBePurchased, order1);
        assertEquals("Get the amount purchased", new Integer(10), amountPurchased );
    }

    @Test
    public void shouldProcessSaleAndPurchaseOrdersWhenSaleIsMoreThanPurchase(){
        stockExchangeService = new StockExchangeServiceImpl();
        Order purchaseOrder = new Order(1, "Buy", "XYZ", 10);
        Order saleOrder = new Order(2, "Sell", "XYZ", 20);

        stockExchangeService.processSaleAndPurchaseOrders(purchaseOrder, saleOrder);

        assertEquals("Sale soes.api.Order is Open", "Open", saleOrder.getStatus());
        assertEquals("Purchase soes.api.Order is Closed", "Closed", purchaseOrder.getStatus());

        assertEquals("Sale soes.api.Order Remaining", new Integer(10), saleOrder.getStockRemaining());
        assertEquals("Purchase soes.api.Order Remaining", new Integer(0), purchaseOrder.getStockRemaining());
    }

    @Test
    public void shouldProcessSaleAndPurchaseOrdersWhenPurchaseIsMoreThanSale(){
        stockExchangeService = new StockExchangeServiceImpl();
        Order purchaseOrder = new Order(1, "Buy", "XYZ", 20);
        Order saleOrder = new Order(2, "Sell", "XYZ", 10);

        stockExchangeService.processSaleAndPurchaseOrders(purchaseOrder, saleOrder);

        assertEquals("Sale soes.api.Order is Closed", "Closed", saleOrder.getStatus());
        assertEquals("Purchase soes.api.Order is Open", "Open", purchaseOrder.getStatus());

        assertEquals("Sale soes.api.Order Remaining", new Integer(0), saleOrder.getStockRemaining());
        assertEquals("Purchase soes.api.Order Remaining", new Integer(10), purchaseOrder.getStockRemaining());
    }

    @Test
    public void shouldProcessSaleAndPurchaseOrdersWhenSomePurchaseStockIsRemaining(){
        stockExchangeService = new StockExchangeServiceImpl();
        Order purchaseOrder = new Order(1, "Buy", "XYZ", 20);
        purchaseOrder.setStockRemaining(5);

        Order saleOrder = new Order(2, "Sell", "XYZ", 10);

        stockExchangeService.processSaleAndPurchaseOrders(purchaseOrder, saleOrder);

        assertEquals("Sale soes.api.Order is Open", "Open", saleOrder.getStatus());
        assertEquals("Purchase soes.api.Order is Closed", "Closed", purchaseOrder.getStatus());

        assertEquals("Sale soes.api.Order Remaining", new Integer(5), saleOrder.getStockRemaining());
        assertEquals("Purchase soes.api.Order Remaining", new Integer(0), purchaseOrder.getStockRemaining());
    }

    @Test
    public void shouldProcessSaleAndPurchaseOrdersWhenSomeSaleStockIsRemaining(){
        stockExchangeService = new StockExchangeServiceImpl();
        Order purchaseOrder = new Order(1, "Buy", "XYZ", 20);

        Order saleOrder = new Order(2, "Sell", "XYZ", 10);
        saleOrder.setStockRemaining(8);

        stockExchangeService.processSaleAndPurchaseOrders(purchaseOrder, saleOrder);

        assertEquals("Sale soes.api.Order is Closed", "Closed", saleOrder.getStatus());
        assertEquals("Purchase soes.api.Order is Open", "Open", purchaseOrder.getStatus());

        assertEquals("Sale soes.api.Order Remaining", new Integer(0), saleOrder.getStockRemaining());
        assertEquals("Purchase soes.api.Order Remaining", new Integer(12), purchaseOrder.getStockRemaining());
    }

    @Test
    public void shouldGetFirstSellOrderByCompanyNameFromAListOfOrders(){
        stockExchangeService = new StockExchangeServiceImpl();

        Order order1 = new Order(1, "Buy", "ABC", 20);
        Order order2 = new Order(2, "Sell", "ABC", 10);
        Order order3 = new Order(3, "Buy", "XYZ", 20);
        Order order4 = new Order(4, "Sell", "ABC", 5);

        order2.setOrderStatus("Closed");

        List<Order> listOfOrders = new ArrayList<>();
        listOfOrders.add(order1);
        listOfOrders.add(order2);
        listOfOrders.add(order3);
        listOfOrders.add(order4);

        String companyName = "ABC";
        Order firstSellOrder = stockExchangeService.getSellOrder(companyName, listOfOrders);

        assertEquals("First Sale soes.api.Order", order4.getStockId(), firstSellOrder.getStockId());
        assertNotEquals("First Sale soes.api.Order Is Not Second soes.api.Order Since soes.api.Order is Closed",
                order2.getStockId(), firstSellOrder.getStockId());
    }

    @Test
    public void shouldGetFirstPurchaseOrderByCompanyNameFromAListOfOrders(){
        stockExchangeService = new StockExchangeServiceImpl();

        Order order1 = new Order(1, "Buy", "ABC", 20);
        Order order2 = new Order(2, "Sell", "XYZ", 10);
        Order order3 = new Order(3, "Buy", "XYZ", 20);
        Order order4 = new Order(4, "Sell", "ABC", 5);

        List<Order> listOfOrders = new ArrayList<>();
        listOfOrders.add(order1);
        listOfOrders.add(order2);
        listOfOrders.add(order3);
        listOfOrders.add(order4);

        String companyName1 = "XYZ";
        Order firstPurchaseOrderByCompany1 = stockExchangeService.getPurchaseOrder(companyName1, listOfOrders);

        assertEquals("First Purchase soes.api.Order Of Company XYZ", order3.getStockId(), firstPurchaseOrderByCompany1.getStockId());

        order1.setOrderStatus("Closed");
        String companyName2 = "ABC";
        Order firstPurchaseOrderByCompany2 = stockExchangeService.getPurchaseOrder(companyName2, listOfOrders);

        assertNull("Should Not show any order because first purchase order of company ABC is closed",
                firstPurchaseOrderByCompany2);
    }
}
