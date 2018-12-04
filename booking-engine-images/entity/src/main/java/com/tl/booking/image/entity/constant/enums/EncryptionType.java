package com.tl.booking.image.entity.constant.enums;

public enum EncryptionType {
  SHA1("SHA1", "SHA-1"),
  MD5("MD5", "MD5");

  private String name;
  private String value;

  EncryptionType(String name, String value) {
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
