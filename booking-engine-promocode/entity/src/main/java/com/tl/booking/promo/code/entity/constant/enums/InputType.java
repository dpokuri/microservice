package com.tl.booking.promo.code.entity.constant.enums;

public enum InputType {
  TEXT("TEXT", "TEXT"),
  DROPDOWN("DROPDOWN", "DROPDOWN"),
  AUTOCOMPLETE("AUTOCOMPLETE", "AUTOCOMPLETE"),
  DATETIMEPICKER("DATETIMEPICKER", "DATETIMEPICKER"),
  DATEPICKER("DATEPICKER", "DATEPICKER"),
  TIMEPICKER("TIMEPICKER", "TIMEPICKER");

  private String name;
  private String value;

  InputType(String name, String value) {
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
