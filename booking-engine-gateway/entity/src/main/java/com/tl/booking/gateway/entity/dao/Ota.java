package com.tl.booking.gateway.entity.dao;

import com.tl.booking.gateway.entity.constant.CollectionName;
import com.tl.booking.gateway.entity.constant.fields.OtaFields;
import com.tl.booking.gateway.entity.dao.common.BaseMongo;

import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@GeneratePojoBuilder
@Document(collection = CollectionName.OTA)
public class Ota extends BaseMongo {
  @Indexed
  @Field(value = OtaFields.NAME)
  private String name;

  @Field(value = OtaFields.ENDPOINT)
  private String endpoint;

  @Field(value = OtaFields.ACTIVE)
  private Integer active;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEndpoint() {
    return endpoint;
  }

  public void setEndpoint(String endpoint) {
    this.endpoint = endpoint;
  }

  public Integer getActive() {
    return active;
  }

  public void setActive(Integer active) {
    this.active = active;
  }

  @Override
  public String toString() {
    return "Ota{" +
        "name='" + name + '\'' +
        ", endpoint='" + endpoint + '\'' +
        ", active=" + active +
        '}';
  }
}
