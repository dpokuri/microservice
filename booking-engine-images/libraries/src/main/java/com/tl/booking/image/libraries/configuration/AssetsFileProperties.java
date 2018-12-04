package com.tl.booking.image.libraries.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "assetsFiles")
public class AssetsFileProperties {
  private String photo;
  private String hostPhoto;
  private String pathPhotoUrl;
  private Integer maxFileSize;
  private String eTagVersion;

  public String getPhoto() {
    return photo;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }

  public String getHostPhoto() {
    return hostPhoto;
  }

  public void setHostPhoto(String hostPhoto) {
    this.hostPhoto = hostPhoto;
  }

  public String getPathPhotoUrl() {
    return pathPhotoUrl;
  }

  public void setPathPhotoUrl(String pathPhotoUrl) {
    this.pathPhotoUrl = pathPhotoUrl;
  }

  public Integer getMaxFileSize() {
    return maxFileSize;
  }

  public void setMaxFileSize(Integer maxFileSize) {
    this.maxFileSize = maxFileSize;
  }

  public String geteTagVersion() {
    return eTagVersion;
  }

  public void seteTagVersion(String eTagVersion) {
    this.eTagVersion = eTagVersion;
  }

  @Override
  public String toString() {
    return "AssetsFileProperties{" +
        "photo='" + photo + '\'' +
        ", hostPhoto='" + hostPhoto + '\'' +
        ", pathPhotoUrl='" + pathPhotoUrl + '\'' +
        ", maxFileSize=" + maxFileSize +
        ", eTagVersion='" + eTagVersion + '\'' +
        '}' + super.toString();
  }

}
