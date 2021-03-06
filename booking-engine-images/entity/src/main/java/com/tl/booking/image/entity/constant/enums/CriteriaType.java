package com.tl.booking.image.entity.constant.enums;

public enum CriteriaType {
  GTE("GTE", "GTE"),
  LTE("LTE", "LTE"),
  BETWEEN("BETWEEN", "BETWEEN"),
  BETWEEN_DATE("BETWEEN_DATE", "BETWEEN_DATE"),
  BETWEEN_START_DATE_END_DATE("BETWEEN_START_DATE_END_DATE", "BETWEEN_START_DATE_END_DATE"),
  REGEX("REGEX", "REGEX"),
  EXACT("EXACT", "EXACT"),
  ELEM_MATCH("ELEM_MATCH", "ELEM_MATCH");

  private String name;
  private String value;

  CriteriaType(String name, String value) {
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public String getValue() {
    return value;
  }

}

