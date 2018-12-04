package com.tl.booking.promo.code.libraries.configuration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TixDeserializationProblemHandler extends DeserializationProblemHandler {

  private static final Logger LOG = LoggerFactory.getLogger(TixDeserializationProblemHandler.class);

  @Override
  public boolean handleUnknownProperty(DeserializationContext ctxt, JsonParser jp,
      JsonDeserializer<?> deserializer, Object beanOrClass, String propertyName)
      throws IOException {
    TixDeserializationProblemHandler.LOG.warn("unknown field : {}", propertyName);
    return true;
  }
}
