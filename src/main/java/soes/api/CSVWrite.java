package soes.api;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVWrite {
    public void writeToFile(List<Stock> stocks) {
        generateCSVFile(Constants.OUTPUT_FILE_PATH, stocks);
    }

    private void generateCSVFile(String csvFileName, List<Stock> stocks) {
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

            for (Stock stock : stocks) {
                writer.append(stock.getStockId().toString());
                writer.append(",");
                writer.append(stock.getSide());
                writer.append(",");
                writer.append(stock.getCompany());
                writer.append(",");
                writer.append(stock.getStockQuantity().toString());
                writer.append(",");
                writer.append(stock.getStockRemaining().toString());
                writer.append(",");
                writer.append(stock.getStatus());
                writer.append("\n");
            }

            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
