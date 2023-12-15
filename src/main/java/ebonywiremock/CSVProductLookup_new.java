package ebonywiremock;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CSVProductLookup_new {

    public static void main(String[] args) {
        String filePath = "./src/main/resources/CF4977.csv"; // Update the path to your CSV file
        String productCode = "101085"; // Specify the product code to filter by
        String contractId = "SY05"; // Specify the contract ID to filter by
        
        System.out.println(getDataFromCSVFile(filePath, productCode, contractId, 3));
    }
    
    public static String getDataFromCSVFile(String filePath, String productCode, String contractId,int columnNo) {
    	String outputValue = null;
        try {
            FileReader fileReader = new FileReader(filePath);
            CSVReader csvReader = new CSVReaderBuilder(fileReader).withSkipLines(1).build(); // Skip the header
            
            List<String[]> rows = csvReader.readAll();
            for (String[] row : rows) {
            	//System.out.println("Email: " + row[10] + ", Customer Code: " + row[11] + ", Product Code: " + row[2] + ", CONTRACTID: " + row[12]);
                if (row[2].equals(productCode) && row[12].equals(contractId)) { // Assuming row[2] is 'Product Code' and row[11] is 'CONTRACTID'
                    System.out.println("CSA Price: " + row[10] + ", Sy05 Price: " + row[11] + ", Product Code: " + row[2] + ", CONTRACTID: " + row[12]);
                	outputValue = row[columnNo];
                	break;
                }
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        System.out.println("outputValue: "+outputValue);
        return outputValue;
    }
}
