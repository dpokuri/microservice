package com.tl.booking.image.entity;

import com.tl.booking.common.entity.CommonModel;
import com.tl.booking.image.entity.constant.enums.CriteriaType;
import java.io.Serializable;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class FilterParam extends CommonModel implements Serializable {

  private String paramName;

  private Object paramValue;
  
  private CriteriaType criteriaType;

  public String getParamName() {
    return paramName;
  }

  public void setParamName(String paramName) {
    this.paramName = paramName;
  }

  public Object getParamValue() {
    return paramValue;
  }

  public void setParamValue(Object paramValue) {
    this.paramValue = paramValue;
  }

  public CriteriaType getCriteriaType() {
    return criteriaType;
  }

  public void setCriteriaType(CriteriaType criteriaType) {
    this.criteriaType = criteriaType;
  }

  @Override
  public String toString() {
    return "FilterParam{" +
        "paramName='" + paramName + '\'' +
        ", paramValue=" + paramValue +
        ", criteriaType=" + criteriaType +
        '}';
  }
}
