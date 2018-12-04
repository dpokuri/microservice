package com.tl.booking.image.entity.constant.enums;

public enum PhotoPromoPage {
  PHOTO_EXTENSION("PHOTO_EXTENSION", "photoExtension"),
  PHOTO_DESKTOP_WEB("PHOTO_DESKTOP_WEB", "photoDesktopWeb"),
  PHOTO_MOBILE_WEB("PHOTO_MOBILE_WEB", "photoMobileWeb"),
  PHOTO_IOS("PHOTO_IOS", "photoIos"),
  PHOTO_ANDROID("PHOTO_ANDROID", "photoAndroid"),
  PHOTO_ORIGINAL_IMAGE("PHOTO_ORIGINAL_IMAGE","originalImage");

  private final String code;
  private final String name;

  PhotoPromoPage(String code, String name) {
    this.code = code;
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public String getCode() {
    return code;
  }
}
