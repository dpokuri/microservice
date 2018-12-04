package com.tl.booking.image.libraries.exception;

public class ArrayIndexOutOfBoundsExceptionHandler extends ArrayIndexOutOfBoundsException {
  private static final long serialVersionUID = 1L;

  private String code;
  private String message;

  public ArrayIndexOutOfBoundsExceptionHandler(String code, String message) {
    super();
    this.setCode(code);
    this.setMessage(message);
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return "ArrayIndexOutOfBoundsExceptionHandler{" +
        "code='" + code + '\'' +
        ", message='" + message + '\'' +
        '}' + super.toString();
  }
}
