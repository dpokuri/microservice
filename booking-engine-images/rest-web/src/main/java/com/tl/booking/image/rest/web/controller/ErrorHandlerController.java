package com.tl.booking.image.rest.web.controller;

import com.tl.booking.common.rest.web.model.response.BaseResponse;
import com.tl.booking.common.rest.web.model.response.CommonResponse;
import com.tl.booking.image.entity.constant.enums.ResponseCode;
import com.tl.booking.image.libraries.exception.BusinessLogicException;
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

  @ExceptionHandler(BindException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public BaseResponse bindException(BindException be) {
    LOGGER.info(be.getMessage());

    List<FieldError> bindErrors = be.getFieldErrors();
    List<String> errors = new ArrayList<>();
    for (FieldError fieldError : bindErrors) {
      errors.add(fieldError.getField() + " " + fieldError.getDefaultMessage());
    }

    return CommonResponse
        .constructResponse(ResponseCode.BIND_ERROR.getCode(), ResponseCode.BIND_ERROR.getMessage(),
            errors, null);
  }

  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public BaseResponse runTimeException(RuntimeException re) {
    LOGGER.warn(re.getMessage());
    return CommonResponse.constructResponse(ResponseCode.SYSTEM_ERROR.getCode(),
        ResponseCode.SYSTEM_ERROR.getMessage(),
        null, null);
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public BaseResponse exception(Exception e) {
    LOGGER.warn(e.getMessage());
    return CommonResponse.constructResponse(ResponseCode.SYSTEM_ERROR.getCode(),
        ResponseCode.SYSTEM_ERROR.getMessage(),
        null, null);
  }

  @ExceptionHandler(BusinessLogicException.class)
  public BaseResponse businessLogicException(BusinessLogicException ble) {
    LOGGER.info(ble.getMessage());

    return CommonResponse.constructResponse(ble.getCode(), ble.getMessage(), null, null);
  }

  @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public BaseResponse arrayIndexOutOfBoundsException(ArrayIndexOutOfBoundsException ai) {
    LOGGER.info("Array Index Out Of Bounds Exception = {}", ai.getMessage());

    return CommonResponse
        .constructResponse(ResponseCode.ARRAY_INDEX_OUT_OF_OUT_BOUNDS_EXCEPTION.getCode(),
            ResponseCode.ARRAY_INDEX_OUT_OF_OUT_BOUNDS_EXCEPTION.getMessage(),
            null, null);
  }

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

}
