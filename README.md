# mock_services_wiremock_ebony

#Build the project using command
mvn clean compile assembly:single 


#Run the jar file
java -jar ebonywiremock-0.0.1-SNAPSHOT-jar-with-dependencies.jar

#To build the image
docker build -t ebonymockservices .

#To run the docker image
docker run -p 8080:8080 ebonymockservices