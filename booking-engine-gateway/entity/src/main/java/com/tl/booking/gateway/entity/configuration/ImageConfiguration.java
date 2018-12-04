package com.tl.booking.gateway.entity.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "image")
public class ImageConfiguration {
  private Integer baseHttpClientTotalMax;
  private Integer baseHttpClientTotalPerRoute;
  private Integer connectTimeout;
  private Integer readTimeout;
  private String host;
  private String app;
  private String logLevel;

  public String getLogLevel() {
    return logLevel;
  }

  public void setLogLevel(String logLevel) {
    this.logLevel = logLevel;
  }

  public Integer getBaseHttpClientTotalMax() {
    return this.baseHttpClientTotalMax;
  }

  public void setBaseHttpClientTotalMax(Integer baseHttpClientTotalMax) {
    this.baseHttpClientTotalMax = baseHttpClientTotalMax;
  }

  public Integer getBaseHttpClientTotalPerRoute() {
    return this.baseHttpClientTotalPerRoute;
  }

  public void setBaseHttpClientTotalPerRoute(Integer baseHttpClientTotalPerRoute) {
    this.baseHttpClientTotalPerRoute = baseHttpClientTotalPerRoute;
  }

  public Integer getConnectTimeout() {
    return this.connectTimeout;
  }

  public void setConnectTimeout(Integer connectTimeout) {
    this.connectTimeout = connectTimeout;
  }

  public Integer getReadTimeout() {
    return this.readTimeout;
  }

  public void setReadTimeout(Integer readTimeout) {
    this.readTimeout = readTimeout;
  }

  public String getHost() {
    return this.host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getApp() {
    return this.app;
  }

  public void setApp(String app) {
    this.app = app;
  }
}
