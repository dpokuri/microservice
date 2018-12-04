package com.tl.booking.gateway.entity.outbound.Hotel;

import com.tl.booking.common.entity.CommonModel;
import java.util.Map;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class ValueAdded extends CommonModel{
  private Map<String, String> name;

  private Integer minStay;

  private Integer extraNight;

  private Map<String, String> description;

  public Map<String, String> getName() {
    return name;
  }

  public void setName(Map<String, String> name) {
    this.name = name;
  }

  public Integer getMinStay() {
    return minStay;
  }

  public void setMinStay(Integer minStay) {
    this.minStay = minStay;
  }

  public Integer getExtraNight() {
    return extraNight;
  }

  public void setExtraNight(Integer extraNight) {
    this.extraNight = extraNight;
  }

  public Map<String, String> getDescription() {
    return description;
  }

  public void setDescription(Map<String, String> description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return "ValueAdded{" +
        "name=" + name +
        ", minStay=" + minStay +
        ", extraNight=" + extraNight +
        ", description=" + description +
        '}';
  }


}
