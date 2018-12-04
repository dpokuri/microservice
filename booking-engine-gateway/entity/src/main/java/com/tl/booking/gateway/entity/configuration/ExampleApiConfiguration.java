package com.tl.booking.gateway.entity.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "example.api")
public class ExampleApiConfiguration {

  private Integer baseHttpClientTotalMax;
  private Integer baseHttpClientTotalPerRoute;
  private Integer connectTimeout;
  private Integer readTimeout;
  private String host;
  private String proxyHost;
  private int proxyPort;
  private String proxyUsername;
  private String proxyPassword;
  private int proxyUse;

  public String getProxyHost() {
    return this.proxyHost;
  }

  public void setProxyHost(String proxyHost) {
    this.proxyHost = proxyHost;
  }

  public int getProxyPort() {
    return this.proxyPort;
  }

  public void setProxyPort(int proxyPort) {
    this.proxyPort = proxyPort;
  }

  public String getProxyUsername() {
    return this.proxyUsername;
  }

  public void setProxyUsername(String proxyUsername) {
    this.proxyUsername = proxyUsername;
  }

  public String getProxyPassword() {
    return this.proxyPassword;
  }

  public void setProxyPassword(String proxyPassword) {
    this.proxyPassword = proxyPassword;
  }

  public int getProxyUse() {
    return this.proxyUse;
  }

  public void setProxyUse(int proxyUse) {
    this.proxyUse = proxyUse;
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
}
