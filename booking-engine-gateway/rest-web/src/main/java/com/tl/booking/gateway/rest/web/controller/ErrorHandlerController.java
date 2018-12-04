package com.tl.booking.gateway.rest.web.controller;

import com.tl.booking.common.rest.web.model.response.BaseResponse;
import com.tl.booking.common.rest.web.model.response.CommonResponse;
import com.tl.booking.gateway.libraries.exception.BusinessLogicException;
import com.tl.booking.gateway.entity.constant.enums.ResponseCode;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestControllerAdvice
public class ErrorHandlerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ErrorHandlerController.class);

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public BaseResponse methodArgumentNotValidException(MethodArgumentNotValidException manve) {
    LOGGER.info("Method Arguments not Valid Exception = {}", manve);
    List<FieldError> methodArgumentNotValidExceptionErrors = manve.getBindingResult().getFieldErrors();
    List<String> errors = new ArrayList<>();
    for (FieldError fieldError : methodArgumentNotValidExceptionErrors) {
      errors.add(fieldError.getField() + " " + fieldError.getDefaultMessage());
    }

    return CommonResponse
        .constructResponse(ResponseCode.METHOD_ARGUMENTS_NOT_VALID.getCode(), ResponseCode.METHOD_ARGUMENTS_NOT_VALID.getMessage(),
            errors, null);
  }

  @ExceptionHandler(BindException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public BaseResponse bindException(BindException be) {
    LOGGER.info("BindException = {}", be);
    List<FieldError> bindErrors = be.getFieldErrors();
    List<String> errors = new ArrayList<>();
    for(FieldError fieldError : bindErrors) {
      errors.add(fieldError.getField() + " " + fieldError.getDefaultMessage());
    }

    return CommonResponse.constructResponse(ResponseCode.BIND_ERROR.getCode(),
        ResponseCode.BIND_ERROR.getMessage(),
        errors, null);
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public BaseResponse exception(Exception e) {
    LOGGER.warn("Exception = {}", e);
    return CommonResponse.constructResponse(ResponseCode.SYSTEM_ERROR.getCode(),
        ResponseCode.SYSTEM_ERROR.getMessage(),
        null, null);
  }

  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public BaseResponse runTimeException(RuntimeException re) {
    LOGGER.info("Runtime Error = {}", re);
    return CommonResponse.constructResponse(ResponseCode.RUNTIME_ERROR.getCode(),
        ResponseCode.RUNTIME_ERROR.getMessage(),
        null, null);
  }

  @ExceptionHandler(BusinessLogicException.class)
  public BaseResponse businessLogicException(BusinessLogicException ble) {
    LOGGER.info("BusinessLogicException = {}", ble);
    return CommonResponse.constructResponse(ble.getCode(), ble.getMessage(), null, null);
  }
}
