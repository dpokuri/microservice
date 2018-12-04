package com.tl.booking.gateway.libraries.configuration;

import org.apache.log4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Configuration
@EnableMongoAuditing(auditorAwareRef = "stringAuditorAware")
public class MongoConfiguration {

  @Bean
  public AuditorAware<String> stringAuditorAware() {
    return () -> MDC.get("username").toString();
  }
}
