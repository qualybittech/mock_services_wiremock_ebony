package ebonywiremock;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class CSVProductLookup {

	public static CSVRecord productsByCode(String filePath, String productCodeToFind ) {
        Map<String, CSVRecord> productsByCode = readCsvIntoMap(filePath, "Product Code");

        // Now retrieve the values based on Product Code
        CSVRecord productDetails = productsByCode.get(productCodeToFind);
        if (productDetails != null) {
            System.out.println("Details for Product Code " + productCodeToFind + ":");
            //System.out.println(productDetails.toMap()); // Convert the record to a map and print it
            
        } else {
            System.out.println("Product Code " + productCodeToFind + " not found in CSV.");
        }
		return productDetails;
	}
	
    private static Map<String, CSVRecord> readCsvIntoMap(String filePath, String keyHeader) {
        Map<String, CSVRecord> map = new HashMap<>();
        try (Reader reader = new FileReader(filePath);
             @SuppressWarnings("deprecation")
			CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            for (CSVRecord csvRecord : csvParser) {
                map.put(csvRecord.get(keyHeader), csvRecord);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
}
