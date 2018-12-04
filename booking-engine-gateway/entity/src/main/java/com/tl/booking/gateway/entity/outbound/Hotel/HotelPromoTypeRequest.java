package com.tl.booking.gateway.entity.outbound.Hotel;

import com.tl.booking.common.entity.CommonModel;
import java.util.Map;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class HotelPromoTypeRequest extends CommonModel {
  private Map<String, String> name;

  private Map<String, String> description;

  private HotelPromoTypeDetail field;

  private Integer sequence;

  private Integer active;

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
    return "HotelPromoTypeRequest{" +
        "name=" + name +
        ", description=" + description +
        ", field=" + field +
        ", sequence=" + sequence +
        ", active=" + active +
        '}';
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }
}
