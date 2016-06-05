package soes.api;

import java.util.List;
import java.util.Objects;

public class StockList {
    private List<Stock> stocks;

    public StockList(List<Stock> stocks){
        this.stocks = stocks;
    }

    public Stock getSellStockByCompanyName(String companyName){
        String orderType = Constants.SELL_ORDER;
        return getStockByCompanyNameAndOrderType(orderType, companyName);
    }

    public Stock getBuyStockByCompanyName(String companyName){
        String orderType = Constants.BUY_ORDER;
        return getStockByCompanyNameAndOrderType(orderType, companyName);
    }

    private Stock getStockByCompanyNameAndOrderType(String orderType, String companyName) {
        for (Stock stock : stocks) {
            if(stock.getCompany().equals(companyName) && stock.getSide().equals(orderType)
                    && !Objects.equals(stock.getStatus(), Constants.ORDER_STATUS_CLOSED)){
                return stock;
            }
        }
        return null;
    }

    public List<Stock> getStocks() {
        return stocks;
    }
}
