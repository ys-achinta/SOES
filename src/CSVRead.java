import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CSVRead {

    public void run() {
        String csvFile = "resources/SOES_Input.csv";
        BufferedReader bufferedReader = null;
        String line = "";
        String csvSplitBy = ",";

        List<Order> orders = new ArrayList<>();

        try{
            bufferedReader = new BufferedReader(new FileReader(csvFile));
            String[] header = (bufferedReader.readLine()).split(csvSplitBy);

            while((line = bufferedReader.readLine()) != null && !line.isEmpty()){
                String[] fields = line.split(csvSplitBy);
                Integer stockId = Integer.parseInt(fields[0]);
                String side = fields[1];
                String companyName = fields[2];
                Integer quantity = Integer.parseInt(fields[3]);
                Order order = new Order(stockId, side, companyName, quantity);
                orders.add(order);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        processOrders(orders);
    }

    private void processOrders(List<Order> orders) {
        StockOrdersProcessor stockOrderProcessor = new StockOrderProcessorImpl();
        stockOrderProcessor.processPurchaseOrders(orders);
    }
}

