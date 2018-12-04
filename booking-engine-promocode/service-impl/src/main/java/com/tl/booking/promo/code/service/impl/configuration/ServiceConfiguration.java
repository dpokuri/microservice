package com.tl.booking.promo.code.service.impl.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;
import com.tl.booking.promo.code.libraries.configuration.PromoCodeMongoProperties;
import com.tl.booking.promo.code.libraries.configuration.TixDeserializationProblemHandler;
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
@ComponentScan(basePackages = "com.tl.booking.promo.code.dao")
@ComponentScan(basePackages = "com.tl.booking.promo.code.service")
@EnableMongoRepositories(value = "com.tl.booking.promo.code.dao")
@EnableMongoAuditing(auditorAwareRef = "stringAuditorAware")
public class ServiceConfiguration {

  @Bean
  public static TixDeserializationProblemHandler deserializationProblemHandler() {
    return new TixDeserializationProblemHandler();
  }

  private static final String DESCRIPTION = "mongodb";

  @Bean
  public MongoClientOptions mongoClientOptions(PromoCodeMongoProperties promoCodeMongoProperties) {
    return new MongoClientOptions.Builder().writeConcern(WriteConcern.ACKNOWLEDGED)
        .connectionsPerHost(promoCodeMongoProperties.getConnectionPerHost())
        .minConnectionsPerHost(promoCodeMongoProperties.getMinConnectionsPerHost())
        .threadsAllowedToBlockForConnectionMultiplier(
            promoCodeMongoProperties.getThreadsAllowedToBlockForConnectionMultiplier())
        .connectTimeout(promoCodeMongoProperties.getConnectTimeout())
        .maxWaitTime(promoCodeMongoProperties.getMaxWaitTime())
        .socketKeepAlive(promoCodeMongoProperties.getSocketKeepAlive())
        .socketTimeout(promoCodeMongoProperties.getSocketTimeout())
        .heartbeatConnectTimeout(promoCodeMongoProperties.getHeartbeatConnectTimeout())
        .heartbeatFrequency(promoCodeMongoProperties.getHeartbeatFrequency())
        .heartbeatSocketTimeout(promoCodeMongoProperties.getHeartbeatSocketTimeout())
        .maxConnectionIdleTime(promoCodeMongoProperties.getMaxConnectionIdleTime())
        .maxConnectionLifeTime(promoCodeMongoProperties.getMaxConnectionLifeTime())
        .minHeartbeatFrequency(promoCodeMongoProperties.getMinHeartbeatFrequency())
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
