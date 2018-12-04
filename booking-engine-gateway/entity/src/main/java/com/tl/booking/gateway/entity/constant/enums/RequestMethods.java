package com.tl.booking.gateway.entity.constant.enums;

public enum RequestMethods {
  GET("GET", "GET"),
  POST("POST", "POST"),
  PUT("PUT", "PUT"),
  DELETE("DELETE", "DELETE"),
  PATCH("PATCH", "PATCH");

  private String param;
  private String value;

  RequestMethods(String param, String value) {
    this.param = param;
    this.value = value;
  }

  public String getParam() {
    return param;
  }

  public String getValue() {
    return value;
  }
}
