package ebonywiremock;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonExtractor {
	
	public static String extractJsonValue(String json,String prefix, String suffix) {
        ObjectMapper objectMapper = new ObjectMapper();
        String value = null;
        
		try {
            // Parse the JSON string into a JsonNode object
            JsonNode rootNode = objectMapper.readTree(json);
            
            // Extract the "query" node value as text
            String queryValue = rootNode.path("query").textValue();
            value = extractValue(queryValue, prefix, suffix);

        } catch (IOException e) {
            e.printStackTrace();
        }
		return value;
	}

    private static String extractValue(String text, String prefix, String suffix) {
        String cutFront = text.split(prefix, 2)[1];
        String value = cutFront.split(suffix, 2)[0];
        return value;
    }
}
