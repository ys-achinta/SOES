package soes.api;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVRead {
    public List<Stock> run() {
        String csvFile = Constants.INPUT_FILE_PATH;
        BufferedReader bufferedReader = null;
        String line = "";
        String csvSplitBy = ",";

        List<Stock> orders = new ArrayList<Stock>();

        try{
            bufferedReader = new BufferedReader(new FileReader(csvFile));
            String[] header = (bufferedReader.readLine()).split(csvSplitBy);

            while((line = bufferedReader.readLine()) != null && !line.isEmpty()){
                String[] fields = line.split(csvSplitBy);
                Integer stockId = Integer.parseInt(fields[0]);
                String side = fields[1];
                String companyName = fields[2];
                Integer quantity = Integer.parseInt(fields[3]);
                Stock order = new Stock(stockId, side, companyName, quantity);
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
        return orders;
    }
}
