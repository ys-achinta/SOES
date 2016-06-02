package soes.api;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVWrite {
    public void writeToFile(List<Order> orders) {
        generateCSVFile(Constants.OUTPUT_FILE_PATH, orders);
    }

    private void generateCSVFile(String csvFileName, List<Order> orders) {
        try{
            FileWriter writer = new FileWriter(csvFileName);
            writer.append(Constants.HEADER_STOCK_ID);
            writer.append(",");
            writer.append(Constants.HEADER_SIDE);
            writer.append(",");
            writer.append(Constants.HEADER_COMPANY);
            writer.append(",");
            writer.append(Constants.HEADER_QUANTITY);
            writer.append("\n");

            for (Order order : orders) {
                writer.append(order.getStockId().toString());
                writer.append(",");
                writer.append(order.getSide());
                writer.append(",");
                writer.append(order.getCompany());
                writer.append(",");
                writer.append(order.getStockQuantity().toString());
                writer.append(",");
                writer.append(order.getStockRemaining().toString());
                writer.append(",");
                writer.append(order.getStatus());
                writer.append("\n");
            }

            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
