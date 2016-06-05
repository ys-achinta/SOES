package soes.api;

import java.util.List;

public class SOESRunner
{
    public static void main( String[] args ) {
        CSVRead csvRead = new CSVRead();
        List<Stock> stocks = csvRead.run();

        StockList stockList = new StockList(stocks);

        StockExchange stockExchange = new StockExchange();
        stockExchange.takeOrders(stockList);
        StockList processedStockList = stockExchange.getProcessedStocks();

        CSVWrite csvWrite = new CSVWrite();
        csvWrite.writeToFile(processedStockList.getStocks());
    }
}
