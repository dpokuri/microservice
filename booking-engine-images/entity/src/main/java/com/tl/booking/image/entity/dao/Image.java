package com.tl.booking.image.entity.dao;

import com.tl.booking.image.entity.constant.CollectionName;
import com.tl.booking.image.entity.constant.fields.ImageFields;
import com.tl.booking.image.entity.dao.common.BaseMongo;
import java.io.Serializable;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@GeneratePojoBuilder
@Document(collection = CollectionName.IMAGE)
public class Image extends BaseMongo {

  @Field(ImageFields.NAME)
  private String name;

  @Field(ImageFields.URL_CLOUDINARY)
  private String urlCloudinary;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUrlCloudinary() {
    return urlCloudinary;
  }

  public void setUrlCloudinary(String urlCloudinary) {
    this.urlCloudinary = urlCloudinary;
  }

  @Override
  public String toString() {
    return "Image{" +
        "name='" + name + '\'' +
        ", urlCloudinary='" + urlCloudinary + '\'' +
        '}';
  }
}
