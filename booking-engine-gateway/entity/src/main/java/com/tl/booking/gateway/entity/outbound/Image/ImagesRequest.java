package com.tl.booking.gateway.entity.outbound.Image;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

public class ImagesRequest extends CommonModel implements Serializable {

  @NotNull(message = "name parameter cannot be null")
  @NotEmpty
  private String name;

  @NotNull(message = "data image parameter cannot be null")
  @NotEmpty
  private String data;

  private String crop;
  private String folder;
  private int width;
  private int height;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public String getCrop() {
    return crop;
  }

  public void setCrop(String crop) {
    this.crop = crop;
  }

  public String getFolder() {
    return folder;
  }

  public void setFolder(String folder) {
    this.folder = folder;
  }
  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }


  @Override
  public String toString() {
    return "ImagesRequest1{" +
            "crop='" + crop + '\'' +
            ", data='" + data + '\'' +
            ", folder='" + folder + '\'' +
            ", name='" + name + '\'' +
            ", width=" + width +
            ", height=" + height +
            '}';
  }

}
