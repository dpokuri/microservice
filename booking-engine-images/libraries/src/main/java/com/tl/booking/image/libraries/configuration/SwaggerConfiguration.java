package com.tl.booking.image.libraries.configuration;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

  private static final String STRING = "string";
  private static final String HEADER = "header";
  private static final String USERNAME = "username";

  @Bean
  public Docket init() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.tl.booking.image.rest.web.controller"))
        .paths(regex("/.*"))
        .build()
        .globalOperationParameters(Arrays.asList(
            new ParameterBuilder().name("storeId").parameterType(HEADER)
                .modelRef(new ModelRef(STRING)).required(true).defaultValue("tiket")
                .description("client's store id").build(),
            new ParameterBuilder().name("channelId").parameterType(HEADER)
                .modelRef(new ModelRef(STRING)).required(true).defaultValue("web")
                .description("client's channel id").build(),
            new ParameterBuilder().name("requestId").parameterType(HEADER)
                .modelRef(new ModelRef(STRING)).required(true).defaultValue("1")
                .description("client's request Id").build(),
            new ParameterBuilder().name("serviceId").parameterType(HEADER)
                .modelRef(new ModelRef(STRING)).required(true).defaultValue("1")
                .description("client's serviceId").build(),
            new ParameterBuilder().name(USERNAME).parameterType(HEADER)
                .modelRef(new ModelRef(STRING)).required(true).defaultValue(USERNAME)
                .description("username").build()));
  }
}
