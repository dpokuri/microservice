package com.tl.booking.promo.code.entity.dao;

import com.tl.booking.promo.code.entity.constant.CollectionName;
import com.tl.booking.promo.code.entity.constant.fields.ProductTypeFields;
import com.tl.booking.promo.code.entity.dao.common.BaseMongo;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@GeneratePojoBuilder
@Document(collection = CollectionName.PRODUCT_TYPE)
public class ProductType extends BaseMongo {

  @Field(value = ProductTypeFields.NAME)
  private String name;

  @Field(value = ProductTypeFields.DESCRIPTION)
  private String description;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  @Override
  public String toString() {
    return "ProductType{" +
        "name='" + name + '\'' +
        ", description='" + description + '\'' +
        '}' + super.toString();
  }

}
