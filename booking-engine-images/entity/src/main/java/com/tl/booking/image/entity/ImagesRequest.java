package com.tl.booking.image.entity;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.NotEmpty;

@GeneratePojoBuilder
 public class ImagesRequest extends CommonModel implements Serializable {

  @NotNull(message = "name parameter cannot be null")
  @NotEmpty
  private String name;

  @NotNull(message = "data image parameter cannot be null")
  @NotEmpty
  private String data;

  @NotNull(message = "folder parameter cannot be null")
  @NotEmpty
  private String folder;

  private Integer width;

  private Integer height;

  private String crop;

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

  public String getFolder() {
    return folder;
  }

  public void setFolder(String folder) {
    this.folder = folder;
  }

  public Integer getWidth() {
    return width;
  }

  public void setWidth(Integer width) {
    this.width = width;
  }

  public Integer getHeight() {
    return height;
  }

  public void setHeight(Integer height) {
    this.height = height;
  }

  public String getCrop() {
    return crop;
  }

  public void setCrop(String crop) {
    this.crop = crop;
  }

  @Override
  public String toString() {
    return "ImagesRequest{" +
        "name='" + name + '\'' +
        ", data='" + data + '\'' +
        ", folder='" + folder + '\'' +
        ", width=" + width +
        ", height=" + height +
        ", crop='" + crop + '\'' +
        '}';
  }
}
