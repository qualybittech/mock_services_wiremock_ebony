package ebonywiremock;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;


public class Wiremockoutput {

	public static void main(String[] args) {
		
		 WireMockServer wireMockServer = new WireMockServer(options()
				 											.globalTemplating(true)
				 											.extensions(new DynamicResponseTransformer())); 
		 // default port: 8080
	     wireMockServer.start();
	     configureFor("localhost", wireMockServer.port());

	        // Configure the stub for the GraphQL query
	        wireMockServer.stubFor(post(urlEqualTo("/graphql"))
	                .withRequestBody(matchingJsonPath("$.query", containing("product")))
	                .willReturn(aResponse()
	                        .withStatus(200)
	                        .withHeader("Content-Type", "application/json")
	                        .withTransformers("dynamic-response-transformer")));

	        // Do some testing...
	        System.out.println("started");
	        
	        String classpathStr = System.getProperty("java.class.path");
	        System.out.print(classpathStr);
	        
	        ClassLoader loader = Wiremockoutput.class.getClassLoader();
	        System.out.println(loader.getResource("ebonywiremock/Wiremockoutput.class"));
	}

	public static void wiremokrun() {
		 WireMockServer wireMockServer = new WireMockServer(options()
					.globalTemplating(true)
					.extensions(new DynamicResponseTransformer())); 
		// default port: 8080
		wireMockServer.start();
		configureFor("localhost", wireMockServer.port());
		
		// Configure the stub for the GraphQL query
		wireMockServer.stubFor(post(urlEqualTo("/graphql"))
		.withRequestBody(matchingJsonPath("$.query", containing("product")))
		.willReturn(aResponse()
		.withStatus(200)
		.withHeader("Content-Type", "application/json")
		.withTransformers("dynamic-response-transformer")));
		
		// Do some testing...
		System.out.println("wiremock started");

	}
}
