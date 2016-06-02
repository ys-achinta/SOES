package soes;
import org.junit.Test;
import soes.api.Order;
import soes.api.impl.StockOrderProcessorImpl;
import soes.api.StockOrdersProcessor;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class StockOrderProcessorImplIntegrationTest {

    StockOrdersProcessor stockOrdersProcessor = new StockOrderProcessorImpl();

    @Test
    public void shouldProcessGivenOrdersList1_AndGetTheStatus_AndQuantityRemaining(){

        List<Order> orders = new ArrayList<>();
        Order order1 = new Order(1,"Buy","ABC", 10);
        Order order2 = new Order(2,"Sell","XYZ", 15);
        Order order3 = new Order(3,"Sell","ABC", 13);
        Order order4 = new Order(4,"Buy","XYZ", 10);
        Order order5 = new Order(5,"Buy","XYZ", 8);

        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
        orders.add(order4);
        orders.add(order5);

        stockOrdersProcessor.processOrders(orders);

        assertEquals("Buy order for ABC status", orders.get(0).getStatus(), "Closed" );
        assertEquals("Buy order for ABC QuantityRemaining", orders.get(0).getStockRemaining(), new Integer(0) );

        assertEquals("Sell order for XYZ status", orders.get(1).getStatus(), "Closed" );
        assertEquals("Sell order for XYZ quantityRemaining", orders.get(1).getStockRemaining(), new Integer(0));

        assertEquals("Sell order for ABC status", orders.get(2).getStatus(), "Open" );
        assertEquals("Sell order for ABC quantityRemaining", orders.get(2).getStockRemaining(), new Integer(3) );

        assertEquals("Buy order for XYZ status", orders.get(3).getStatus(), "Closed" );
        assertEquals("Buy order for XYZ quantityRemaining", orders.get(3).getStockRemaining(), new Integer(0) );

        assertEquals("Buy order for XYZ status", orders.get(4).getStatus(), "Open" );
        assertEquals("Buy order for XYZ quantityRemaining", orders.get(4).getStockRemaining(), new Integer(3) );
      }

    @Test
    public void shouldProcessGivenOrderList2_AndGetTheCorrectStatus_AndQuantityRemaining(){

        List<Order> orders = new ArrayList<>();
        Order order1 = new Order(1,"Sell","XYZ", 10);
        Order order2 = new Order(2,"Sell","ABC", 20);
        Order order3 = new Order(3,"Buy","XYZ", 20);
        Order order4 = new Order(4,"Sell","XYZ", 20);
        Order order5 = new Order(5,"Buy","ABC", 15);
        Order order6 = new Order(6,"Buy","ABC", 5);

        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
        orders.add(order4);
        orders.add(order5);
        orders.add(order6);

        stockOrdersProcessor.processOrders(orders);

        assertEquals("Sell order for XYZ status", orders.get(0).getStatus(), "Closed" );
        assertEquals("Sell order for XYZ QuantityRemaining", orders.get(0).getStockRemaining(), new Integer(0) );

        assertEquals("Sell order for ABC status", orders.get(1).getStatus(), "Closed" );
        assertEquals("Sell order for ABC quantityRemaining", orders.get(1).getStockRemaining(), new Integer(0));

        assertEquals("Buy order for XYZ status", orders.get(2).getStatus(), "Closed" );
        assertEquals("Buy order for XYZ quantityRemaining", orders.get(2).getStockRemaining(), new Integer(0) );

        assertEquals("Sell order for XYZ status", orders.get(3).getStatus(), "Open" );
        assertEquals("Sell order for XYZ quantityRemaining", orders.get(3).getStockRemaining(), new Integer(10) );

        assertEquals("Buy order for ABC status", orders.get(4).getStatus(), "Closed" );
        assertEquals("Buy order for ABC quantityRemaining", orders.get(4).getStockRemaining(), new Integer(0) );

        assertEquals("Buy order for ABC status", orders.get(5).getStatus(), "Closed" );
        assertEquals("Buy order for ABC quantityRemaining", orders.get(5).getStockRemaining(), new Integer(0) );
    }
}
