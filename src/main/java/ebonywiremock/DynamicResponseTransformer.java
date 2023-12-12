package ebonywiremock;

import org.apache.commons.csv.CSVRecord;

import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseTransformerV2;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.Response;
import com.github.tomakehurst.wiremock.stubbing.ServeEvent;

public class DynamicResponseTransformer implements ResponseTransformerV2 {
    
    public Response transform(Request request, Response response, Parameters parameters) {
        try {
            
          System.out.println(JsonExtractor.extractJsonValue(request.getBodyAsString(), "productId: \\\"", "\\\""));
            
          String productId = JsonExtractor.extractJsonValue(request.getBodyAsString(), "productId: \\\"", "\\\"");
          String contractId = JsonExtractor.extractJsonValue(request.getBodyAsString(), "contractId: \\\"", "\\\"");
         String filePath = "./CF4977.csv";
          
          
          CSVRecord productDetails  = CSVProductLookup.productsByCode(filePath, productId);
          String CSAPrice = productDetails.get("CSA Price");
          String SYO5 = productDetails.get("SYO5 PERSIMMON HOMES - MASTE");	
          String price = CSAPrice ;
          String name = productDetails.get("Product Description");
          String sellingUom = productDetails.get("UOM");
          String supplierPartNumber = productDetails.get("Supplier Product Code");
          
          if(CSAPrice.isEmpty()) {
        	  price = SYO5;
          }
          
            // Construct dynamic response JSON with extracted values
            String jsonResponse = "{" +
                    "  \"data\": {" +
                    "    \"product\": {" +
                    "      \"catalogType\": \"WEB\"," +
                    "      \"category\": {" +
                    "        \"id\": \"1848002\"," +
                    "        \"name\": \"Bath Shower Mixer Taps\"" +
                    "      }," +
                    "      \"id\": \"" + productId + "\"," +
                    "      \"name\": \""+name+"\"," +
                    "      \"sellingUom\": \""+sellingUom+"\"," +
                    "      \"supplierPartNumber\": \""+supplierPartNumber+"\"," +
                    "      \"supplierType\": \"UNKNOWN\"," +
                    "      \"productPrice\": {" +
                    "        \"sellingPrice\": {" +
                    "          \"price\": {" +
                    "            \"valueExVat\": "+price+"," +
                    "          }," +
                    "          \"priceType\": \"CSA\"" +
                    "        }," +
                    "        \"contractId\": \"" + contractId + "\"" +
                    "      }" +
                    "    }" +
                    "  }" +
                    "}";
            
            // Construct new response with dynamic JSON body
            response = Response.Builder.like(response)
                    .but()
                    .body(jsonResponse)
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            // Optional: handle parsing errors or return a fallback response
        }
        return response;
    }

    @Override
    public String getName() {
        return "dynamic-response-transformer";
    }

    @Override
    public boolean applyGlobally() {
        return false; // Set to true if you want this to apply to all responses
    }

	@Override
	public Response transform(Response response, ServeEvent serveEvent) {
		Parameters parameters = serveEvent.getTransformerParameters();
		
        return response = transform(serveEvent.getRequest(), response, parameters);
	}
}