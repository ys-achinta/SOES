import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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
        assertEquals("", order1.getStatus(), "Closed");
        assertEquals("", order1.getStockRemaining(), new Integer(0));
    }

    @Test
    public void shouldUpdateTheStatusAndRemainingQuantityOfASellOrder2(){
        stockExchangeService = new StockExchangeServiceImpl();
        Order order1 = new Order(1,"Sell","XYZ", 10);
        order1.setStockRemaining(8);

        Integer quantityToBeSold = order1.getStockRemaining();
        Integer amountPurchased = 5;

        stockExchangeService.updateSaleOrder(amountPurchased, quantityToBeSold, order1);
        assertEquals("Status is open since purchase is less than to be sold", order1.getStatus(), "Open");
        assertEquals("Remaining Value is calculated from the initial remaining amount if present",
                order1.getStockRemaining(), new Integer(quantityToBeSold-amountPurchased));
        assertNotEquals("Remaining Value not is calculated from the total amount",
                order1.getStockRemaining(), new Integer(order1.getStockQuantity()-amountPurchased));
    }

    @Test
    public void shouldUpdateTheStatusAndRemainingQuantityOfAPurchaseOrder1(){
        stockExchangeService = new StockExchangeServiceImpl();


    }
}
