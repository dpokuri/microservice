FROM openjdk:8
WORKDIR /app
ADD ./rest-web/target/rest-web-1.0.5-2-SNAPSHOT.jar rest-web-1.0.5-2-SNAPSHOT.jar
RUN mkdir /app/config
EXPOSE 8470
CMD ["java", "-jar", "rest-web-1.0.5-2-SNAPSHOT.jar", "--spring.config.location=/app/config/application.properties"]
