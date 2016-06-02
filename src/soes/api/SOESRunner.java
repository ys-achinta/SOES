package soes.api;
import soes.api.impl.StockOrderProcessorImpl;
import java.util.List;

public class SOESRunner {

    public static void main(String[] args){
        CSVRead csvRead = new CSVRead();
        List<Order> orders = csvRead.run();

        StockOrdersProcessor stockOrdersProcessor = new StockOrderProcessorImpl();
        stockOrdersProcessor.processOrders(orders);

        CSVWrite csvWrite = new CSVWrite();
        csvWrite.writeToFile(orders);
    }
}
