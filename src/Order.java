public class Order {

    String companyName;
    String orderStatus;
    String side;

    Integer stockRemaining;
    Integer stockQuantity;
    Integer stockId;


    public Order(Integer stockId, String side, String companyName, Integer stockQuantity){
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

    public void setStockRemaining(Integer quantity){
        this.stockRemaining = quantity;
    }

    public void setOrderStatus(String status){
        this.orderStatus = status;
    }
}
