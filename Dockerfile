FROM openjdk:11
VOLUME /tmp
ADD target/ebonywiremock-0.0.1-SNAPSHOT-jar-with-dependencies.jar app.jar
ADD src/main/resources/CF4977.csv CF4977.csv
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-jar","/app.jar"]