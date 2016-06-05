package soes.api;

public class Stock {
    private Integer stockId;
    private String companyName;
    private String side;
    private Integer stockQuantity;
    private Integer stockRemaining;

    private String stockStatus = Constants.ORDER_STATUS_OPEN ;

    public Stock(Integer stockId, String side, String companyName, Integer stockQuantity){
        this.stockId = stockId;
        this.side = side;
        this.companyName = companyName;
        this.stockQuantity = stockQuantity;
    }

    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    public String getCompany() {
        return companyName;
    }

    public void setCompany(String companyName) {
        this.companyName = companyName;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public void setSide(String side){
        this.side = side;
    }

    public String getSide(){
        return side;
    }

    public Integer getStockRemaining() {
        return stockRemaining;
    }

    public String getStatus() {
        return stockStatus;
    }

    public void setOrderStatus(){
        if(stockRemaining != 0){
            stockStatus = Constants.ORDER_STATUS_OPEN;
        }else {
            stockStatus = Constants.ORDER_STATUS_CLOSED;
        }
    }

    public void setStockRemaining(Integer amount){
        Integer quantity = getQuantityForTransaction();
        if(amount >= quantity){
            stockRemaining = 0;
        }else{
            stockRemaining = quantity-amount;
        }
    }

    public Integer getQuantityForTransaction() {
        return (null != stockRemaining) ? stockRemaining : stockQuantity;
    }


    public void buy(Integer quantityToBeSold) {
        setStockRemaining(quantityToBeSold);
        setOrderStatus();
    }

    public void sell(Integer quantityToBePurchased) {
        setStockRemaining(quantityToBePurchased);
        setOrderStatus();
    }
}
