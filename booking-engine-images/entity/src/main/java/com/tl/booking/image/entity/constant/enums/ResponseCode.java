package com.tl.booking.image.entity.constant.enums;

public enum ResponseCode {
  SUCCESS("SUCCESS", "SUCCESS"),
  SYSTEM_ERROR("SYSTEM_ERROR", "Contact our team"),
  DUPLICATE_DATA("DUPLICATE_DATA", "Duplicate data"),
  DATA_NOT_EXIST("DATA_NOT_EXIST", "No data exist"),
  DATE_NOT_VALID("DATE_NOT_VALID", "Date not valid"),
  TIME_NOT_GREATER_THAN_NOW("TIME_NOT_GREATER_THAN_NOW", "Time Not Greater Than Now"),
  END_DATE_NOT_GREATER_THAN_NOW("END_DATE_NOT_GREATER_THAN_NOW", "End Date Not Greater Than Now"),

  RUNTIME_ERROR("RUNTIME_ERROR", "Runtime Error"),
  PROMO_PAGE_STATUS_NOT_DRAFT("PROMO_PAGE_STATUS_NOT_DRAFT","Promo Page Status is Not Draft"),
  PROMO_PAGE_STATUS_NOT_PENDING("PROMO_PAGE_STATUS_NOT_PENDING","Promo Page Status is Not Pending"),
  PROMO_PAGE_STATUS_NOT_ACTIVE("PROMO_PAGE_STATUS_NOT_ACTIVE","Promo Page Status is Not Active"),
  PROMO_PAGE_STATUS_NOT_IN_ACTIVE("PROMO_PAGE_STATUS_NOT_IN_ACTIVE","Promo Page Status is Not InActive"),
  PARENT_CATEGORY_NOT_EXIST("PARENT_CATEGORY_NOT_EXIST","Parent Category Not Exist"),
  CATEGORY_USED_AS_PARENT("CATEGORY_USED_AS_PARENT","Category is used as Parent Category"),
  PHOTO_EXTENSION_NOT_MATCH("PHOTO_EXTENSION_NOT_MATCH","Photo Extension Not Match"),
  MERCHANT_PHOTO_NOT_EXIST("MERCHANT_PHOTO_NOT_EXIST","Merchant Photo Not Exist"),
  WIDTH_RATIO_NOT_EXIST("WIDTH_RATIO_NOT_EXIST","Width Ratio Not Exist"),
  WIDTH_NOT_EXIST("WIDTH_NOT_EXIST","Width Not Exist"),
  HEIGHT_NOT_EXIST("HEIGHT_NOT_EXIST","Height Not Exist"),
  WIDTH_OR_HEIGHT_NOT_EXIST("WIDTH_OR_HEIGHT_NOT_EXIST","Width or Height Not Exist"),
  PROPERTY_IMAGE_NOT_EXIST("PROPERTY_IMAGE_NOT_EXIST", "Property image not exist"),
  IMAGE_NOT_EXIST("IMAGE_NOT_EXIST", "No image exist"),
  IMAGE_NOT_FOUND_IN_ASSETS("IMAGE_NOT_FOUND_IN_ASSETS", "No image found in assets"),
  CANNOT_CROP_IMAGE("CANNOT_CROP_IMAGE", "Cannot Crop Image"),
  CANNOT_RATIO_IMAGE("CANNOT_RATIO_IMAGE", "Cannot Ratio Image"),
  CANNOT_RESIZE_IMAGE("CANNOT_RESIZE_IMAGE", "Cannot Resize Image"),
  CANNOT_INPUT_STREAM_RESOURCE("CANNOT_INPUT_STREAM_RESOURCE","Cannot Input Stream Resource"),
  FAILED_READ_FILE("FAILED_READ_FILE", "Failed read file"),
  FILE_SIZE_EXCEEDS_MAXIMUM_LIMIT("FILE_SIZE_EXCEEDS_MAXIMUM_LIMIT", "File size Exceeds maximum limit"),
  FILE_NOT_EXIST("FILE_NOT_EXIST", "File not exist"),
  CANNOT_CREATE_IMAGE_OUTPUT_STREAM("CANNOT_CREATE_IMAGE_OUTPUT_STREAM", "Cannot Create Image Output Stream"),
  CANNOT_WRITE_IMAGE("CANNOT_WRITE_IMAGE", "Cannot Write Image"),
  QUALITY_MUST_BE_NUMERIC("QUALITY_MUST_BE_NUMERIC","Quality Must be Numeric"),
  CANNOT_READ_IMAGE("CANNOT_READ_IMAGE", "Cannot Read Image"),
  METHOD_ARGUMENTS_NOT_VALID("METHOD_ARGUMENTS_NOT_VALID", "Please fill in mandatory parameter"),
  UPLOAD_FILE_FAILED("UPLOAD_FILE_FAILED", "upload file failed"),
  FILE_NAME_IMAGE_INVALID("FILE_NAME_IMAGE_INVALID", "file name image invalid"),
  PROPERTY_INVALID("PROPERTY_INVALID", "property invalid"),
  ARRAY_INDEX_OUT_OF_OUT_BOUNDS_EXCEPTION("ARRAY_INDEX_OUT_OF_OUT_BOUNDS_EXCEPTION","Array Index Out Of Bounds Exception"),
  IMAGE_NOT_FOUND("IMAGE_NOT_FOUND", "Image Not Found"),
  CANNOT_GET_IMAGE_FROM_URL("CANNOT_GET_IMAGE_FROM_URL", "Cannot get image from URL"),


  BIND_ERROR("BIND_ERROR", "Please fill in mandatory parameter");

  private String code;
  private String message;

  ResponseCode(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
