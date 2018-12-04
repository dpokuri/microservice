package com.tl.booking.promo.code.rest.web.controller;

import com.tl.booking.common.rest.web.model.response.BaseResponse;
import com.tl.booking.common.rest.web.model.response.CommonResponse;
import com.tl.booking.promo.code.entity.constant.enums.Language;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.fields.BaseMongoFields;
import com.tl.booking.promo.code.libraries.exception.BusinessLogicException;
import com.tl.booking.promo.code.service.api.BusinessLogicResponseService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  private BusinessLogicResponseService businessLogicResponseService;

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public BaseResponse methodArgumentNotValidException(MethodArgumentNotValidException manve) {
    LOGGER.info("Method Arguments not Valid Exception = {}", manve);
    List<FieldError> methodArgumentNotValidExceptionErrors = manve.getBindingResult()
        .getFieldErrors();
    List<String> errors = new ArrayList<>();
    for (FieldError fieldError : methodArgumentNotValidExceptionErrors) {
      errors.add(fieldError.getField() + " " + fieldError.getDefaultMessage());
    }

    String message = this.getErrorMessage(ResponseCode.METHOD_ARGUMENTS_NOT_VALID.getCode());

    return CommonResponse
        .constructResponse(ResponseCode.METHOD_ARGUMENTS_NOT_VALID.getCode(),
            message,
            errors, null);
  }

  @ExceptionHandler(BindException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public BaseResponse bindException(BindException be) {
    LOGGER.info("BindException = {}", be);
    List<FieldError> bindErrors = be.getFieldErrors();
    List<String> errors = new ArrayList<>();
    for (FieldError fieldError : bindErrors) {
      errors.add(fieldError.getField() + " " + fieldError.getDefaultMessage());
    }

    String message = this.getErrorMessage(ResponseCode.BIND_ERROR.getCode());
    return CommonResponse.constructResponse(ResponseCode.BIND_ERROR.getCode(),
        message,
        errors, null);
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public BaseResponse exception(Exception e) {
    LOGGER.warn("Exception = {}", e);

    String message = this.getErrorMessage(ResponseCode.SYSTEM_ERROR.getCode());

    return CommonResponse.constructResponse(ResponseCode.SYSTEM_ERROR.getCode(),
        message,
        null, null);
  }

  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public BaseResponse runTimeException(RuntimeException re) {
    LOGGER.info("Runtime Error = {}", re);

    String message = this.getErrorMessage(ResponseCode.RUNTIME_ERROR.getCode());

    return CommonResponse.constructResponse(ResponseCode.RUNTIME_ERROR.getCode(),
        message,
        null, null);
  }

  @ExceptionHandler(BusinessLogicException.class)
  public BaseResponse businessLogicException(BusinessLogicException ble) {
    LOGGER.info("BusinessLogicException = {}", ble);

    String message = this.getErrorMessage(ble.getCode());

    return CommonResponse.constructResponse(ble.getCode(), message,
        null, null);
  }

  private String getErrorMessage(String code) {
    String message = "";
    if(Arrays.asList(ResponseCode.RUNTIME_ERROR.getCode(), ResponseCode.SYSTEM_ERROR.getCode(),
        ResponseCode.METHOD_ARGUMENTS_NOT_VALID.getCode(), ResponseCode.BIND_ERROR.getCode())
        .contains(code)){
      if(Language.EN.getCode().equalsIgnoreCase(MDC.get(BaseMongoFields.LANG))){
        message = ResponseCode.valueOf(code).getMessageEnglish();
      } else {
        message = ResponseCode.valueOf(code).getMessage();
      }
    } else {
      message = this.businessLogicResponseService
          .findMessageByResponseCodeAndLanguage(MDC.get(BaseMongoFields.STORE_ID),
              code, MDC.get(BaseMongoFields.LANG));
    }
    return message;
  }

}
