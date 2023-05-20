FROM openjdk:17
COPY target/scrapper-1.0-SNAPSHOT.jar scrapper.jar
EXPOSE 8080
EXPOSE 8082
ENV DB_URL=jdbc:postgresql://postgresql:5432/scrapper
ENTRYPOINT ["java", "-jar", "scrapper.jar"]
