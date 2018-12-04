package com.tl.booking.gateway.entity.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "hotelSubsidy")
public class HotelSubsidyConfiguration {

  private Integer baseHttpClientTotalMax;
  private Integer baseHttpClientTotalPerRoute;
  private Integer connectTimeout;
  private Integer readTimeout;
  private String host;
  private String app;
  private String logLevel;

  public Integer getBaseHttpClientTotalMax() {
    return baseHttpClientTotalMax;
  }

  public void setBaseHttpClientTotalMax(Integer baseHttpClientTotalMax) {
    this.baseHttpClientTotalMax = baseHttpClientTotalMax;
  }

  public Integer getBaseHttpClientTotalPerRoute() {
    return baseHttpClientTotalPerRoute;
  }

  public void setBaseHttpClientTotalPerRoute(Integer baseHttpClientTotalPerRoute) {
    this.baseHttpClientTotalPerRoute = baseHttpClientTotalPerRoute;
  }

  public Integer getConnectTimeout() {
    return connectTimeout;
  }

  public void setConnectTimeout(Integer connectTimeout) {
    this.connectTimeout = connectTimeout;
  }

  public Integer getReadTimeout() {
    return readTimeout;
  }

  public void setReadTimeout(Integer readTimeout) {
    this.readTimeout = readTimeout;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getApp() {
    return app;
  }

  public void setApp(String app) {
    this.app = app;
  }

  public String getLogLevel() {
    return logLevel;
  }

  public void setLogLevel(String logLevel) {
    this.logLevel = logLevel;
  }
}
