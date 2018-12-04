package com.tl.booking.promo.code.libraries.configuration;

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
        .apis(RequestHandlerSelectors.basePackage("com.tl.booking.promo.code.rest.web.controller"))
        .paths(regex("/.*"))
        .build()
        .globalOperationParameters(Arrays.asList(new ParameterBuilder().name("lang").parameterType
                (HEADER)
                .modelRef(new ModelRef(STRING)).required(true).defaultValue("ID")
                .description("client's language").build(),
            new ParameterBuilder().name("storeId").parameterType(HEADER)
                .modelRef(new ModelRef(STRING)).required(true).defaultValue("TIKETCOM")
                .description("client's store id").build(),
            new ParameterBuilder().name("channelId").parameterType(HEADER)
                .modelRef(new ModelRef(STRING)).required(true).defaultValue("MOBILE")
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
