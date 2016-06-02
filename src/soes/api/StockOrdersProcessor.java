package soes.api;
import java.util.List;

public interface StockOrdersProcessor {
    void processOrders(List<Order> orders);
}
