FROM openjdk:8
WORKDIR /app
ADD ./rest-web/target/rest-web-1.1.5-SNAPSHOT.jar rest-web-1.1.5-SNAPSHOT.jar
ADD ./lib/collector-0.6.0.jar collector-0.6.0.jar
RUN mkdir /app/config
EXPOSE 8534
EXPOSE 9411
CMD ["java", "-jar", "rest-web-1.1.5-SNAPSHOT.jar", "--spring.config.location=/app/config/application.properties", ";", "-jar", "collector-0.6.0.jar", "PROJECT_ID=prismatic-fact-201508", "GOOGLE_APPLICATION_CREDENTIALS=./lib/adc.json"]
