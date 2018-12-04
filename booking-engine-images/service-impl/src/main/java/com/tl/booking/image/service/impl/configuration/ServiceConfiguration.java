package com.tl.booking.image.service.impl.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;
import com.tl.booking.image.libraries.configuration.ImageMongoProperties;
import com.tl.booking.image.libraries.configuration.TixDeserializationProblemHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ComponentScan(basePackages = "com.tl.booking.image.dao")
@ComponentScan(basePackages = "com.tl.booking.image.service")
@EnableMongoRepositories(value = "com.tl.booking.image.dao")
@EnableMongoAuditing(auditorAwareRef = "stringAuditorAware")
public class ServiceConfiguration {

  @Bean
  public static TixDeserializationProblemHandler deserializationProblemHandler() {
    return new TixDeserializationProblemHandler();
  }

  private static final String DESCRIPTION = "mongodb";

  @Bean
  public MongoClientOptions mongoClientOptions(ImageMongoProperties imageMongoProperties) {
    return new MongoClientOptions.Builder().writeConcern(WriteConcern.ACKNOWLEDGED)
        .connectionsPerHost(imageMongoProperties.getConnectionPerHost())
        .minConnectionsPerHost(imageMongoProperties.getMinConnectionsPerHost())
        .threadsAllowedToBlockForConnectionMultiplier(
            imageMongoProperties.getThreadsAllowedToBlockForConnectionMultiplier())
        .connectTimeout(imageMongoProperties.getConnectTimeout())
        .maxWaitTime(imageMongoProperties.getMaxWaitTime())
        .socketKeepAlive(imageMongoProperties.getSocketKeepAlive())
        .socketTimeout(imageMongoProperties.getSocketTimeout())
        .heartbeatConnectTimeout(imageMongoProperties.getHeartbeatConnectTimeout())
        .heartbeatFrequency(imageMongoProperties.getHeartbeatFrequency())
        .heartbeatSocketTimeout(imageMongoProperties.getHeartbeatSocketTimeout())
        .maxConnectionIdleTime(imageMongoProperties.getMaxConnectionIdleTime())
        .maxConnectionLifeTime(imageMongoProperties.getMaxConnectionLifeTime())
        .minHeartbeatFrequency(imageMongoProperties.getMinHeartbeatFrequency())
        .readPreference(ReadPreference.primary())
        .description(DESCRIPTION)
        .build();
  }

  @Bean
  public ObjectMapper createObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true);
    objectMapper.configure(DeserializationFeature.ACCEPT_FLOAT_AS_INT, false);
    objectMapper.addHandler(ServiceConfiguration.deserializationProblemHandler());
    return objectMapper;
  }

  @Bean
  public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory, MongoConverter converter) {
    return new MongoTemplate(mongoDbFactory, converter);
  }

  @Bean
  public AuditorAware<String> stringAuditorAware() {
    return () -> "system";
  }
}
