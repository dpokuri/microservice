package com.tl.booking.image.entity.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "cloudinary")
public class CloudinaryProperties {

  private String cloud_name;
  private String api_key;
  private String api_secret;
  private String resource_type;
  private String overwrite;
  private String cname;
  private String private_cdn;

  public String getCloud_name() {
    return cloud_name;
  }

  public void setCloud_name(String cloud_name) {
    this.cloud_name = cloud_name;
  }

  public String getApi_key() {
    return api_key;
  }

  public void setApi_key(String api_key) {
    this.api_key = api_key;
  }

  public String getApi_secret() {
    return api_secret;
  }

  public void setApi_secret(String api_secret) {
    this.api_secret = api_secret;
  }

  public String getResource_type() {
    return resource_type;
  }

  public void setResource_type(String resource_type) {
    this.resource_type = resource_type;
  }

  public String getOverwrite() {
    return overwrite;
  }

  public void setOverwrite(String overwrite) {
    this.overwrite = overwrite;
  }

  public String getCname() {
    return cname;
  }

  public void setCname(String cname) {
    this.cname = cname;
  }

  public String getPrivate_cdn() {
    return private_cdn;
  }

  public void setPrivate_cdn(String private_cdn) {
    this.private_cdn = private_cdn;
  }

  @Override
  public String toString() {
    return "CloudinaryProperties{" +
        "cloud_name='" + cloud_name + '\'' +
        ", api_key='" + api_key + '\'' +
        ", api_secret='" + api_secret + '\'' +
        ", resource_type='" + resource_type + '\'' +
        ", overwrite='" + overwrite + '\'' +
        ", cname='" + cname + '\'' +
        ", private_cdn='" + private_cdn + '\'' +
        '}';
  }
}
