package com.tl.booking.image.entity;

import javax.validation.constraints.NotNull;
import net.karneim.pojobuilder.GeneratePojoBuilder;


@GeneratePojoBuilder
public class PhotoFile {

  @NotNull
  private String name;

  @NotNull
  private String data;


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

  @Override
  public String toString() {
    return "PhotoFile{" +
        "name='" + name + '\'' +
        ", data='" + data + '\'' +
        '}' + super.toString();
  }

}
