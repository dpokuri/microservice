package com.tl.booking.image.entity.dao;

import com.tl.booking.image.entity.constant.CollectionName;
import com.tl.booking.image.entity.constant.fields.ImagePropertiesFields;
import com.tl.booking.image.entity.dao.common.BaseMongo;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@GeneratePojoBuilder
@Document(collection = CollectionName.IMAGE_PROPERTIES)
public class ImageProperties extends BaseMongo {

  @Field(ImagePropertiesFields.PROPERTIES)
  private String properties;

  public String getProperties() {
    return properties;
  }

  public void setProperties(String properties) {
    this.properties = properties;
  }

  @Override
  public String toString() {
    return "ImageProperties{" +
        "properties='" + properties + '\'' +
        '}' + super.toString();
  }
}
