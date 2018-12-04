package com.tl.booking.gateway.entity.constant.enums;

public enum ResponseCode {
  SUCCESS("SUCCESS", "SUCCESS"),
  SYSTEM_ERROR("SYSTEM_ERROR", "Contact our team"),
  DUPLICATE_DATA("DUPLICATE_DATA", "Duplicate data"),
  DATA_NOT_EXIST("DATA_NOT_EXIST", "No data exist"),
  TOKEN_NOT_MATCH("TOKEN_NOT_MATCH", "Token not match"),
  BIND_ERROR("BIND_ERROR", "Please fill in mandatory parameter"),
  REQUEST_NOT_VALID("REQUEST_NOT_VALID", "Request Not Valid"),
  NOT_AUTHORIZED("NOT_AUTHORIZED", "You are not authorized to access this page"),
  METHOD_ARGUMENTS_NOT_VALID("METHOD_ARGUMENTS_NOT_VALID","Method Arguments Not Valid"),
  RUNTIME_ERROR("RUNTIME_ERROR", "Runtime Error");

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
