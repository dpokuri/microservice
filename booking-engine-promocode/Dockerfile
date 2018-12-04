FROM openjdk:8
WORKDIR /app
ADD ./rest-web/target/rest-web-1.0.0-10-SNAPSHOT.jar /app/rest-web-1.0.0-10-SNAPSHOT.jar
RUN mkdir /app/config
EXPOSE 8181
CMD ["java", "-jar", "/app/rest-web-1.0.0-10-SNAPSHOT.jar", "--spring.config.location=/app/config/application.properties"]
#CMD ["java", "-jar", "/app/rest-web-1.0.0-10-SNAPSHOT.jar"]
