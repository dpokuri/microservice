package com.tl.booking.gateway.entity.outbound.Hotel;

import com.tl.booking.gateway.entity.constant.CollectionName;
import com.tl.booking.gateway.entity.constant.fields.HotelPromoTypeFields;
import com.tl.booking.gateway.entity.dao.common.BaseMongo;

import java.util.Map;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@GeneratePojoBuilder
@Document(collection = CollectionName.HOTEL_PROMO_TYPE)
public class HotelPromoType extends BaseMongo {

  @Field(value = HotelPromoTypeFields.NAME)
  private Map<String, String> name;

  @Field(value = HotelPromoTypeFields.DESCRIPTION)
  private Map<String, String> description;

  @Field(value = HotelPromoTypeFields.FIELD)
  private HotelPromoTypeDetail field;

  @Field(value = HotelPromoTypeFields.SEQUENCE)
  private Integer sequence;

  @Field(value = HotelPromoTypeFields.ACTIVE)
  private Integer active=1;

  public Map<String, String> getName() {
    return name;
  }

  public void setName(Map<String, String> name) {
    this.name = name;
  }

  public Map<String, String> getDescription() {
    return description;
  }

  public void setDescription(Map<String, String> description) {
    this.description = description;
  }

  public HotelPromoTypeDetail getField() {
    return field;
  }

  public void setField(HotelPromoTypeDetail field) {
    this.field = field;
  }

  public Integer getSequence() {
    return sequence;
  }

  public void setSequence(Integer sequence) {
    this.sequence = sequence;
  }

  public Integer getActive() {
    return active;
  }

  public void setActive(Integer active) {
    this.active = active;
  }

  @Override
  public String toString() {
    return "HotelPromoType{" +
        "name=" + name +
        ", description=" + description +
        ", field=" + field +
        ", sequence=" + sequence +
        ", active=" + active +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }

    HotelPromoType that = (HotelPromoType) o;

    if (!name.equals(that.name)) {
      return false;
    }
    if (!description.equals(that.description)) {
      return false;
    }
    if (!field.equals(that.field)) {
      return false;
    }
    if (!sequence.equals(that.sequence)) {
      return false;
    }
    return active.equals(that.active);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + name.hashCode();
    result = 31 * result + description.hashCode();
    result = 31 * result + field.hashCode();
    result = 31 * result + sequence.hashCode();
    result = 31 * result + active.hashCode();
    return result;
  }
}
