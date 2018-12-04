package com.tl.booking.promo.code.rest.web.controller;

import com.tl.booking.common.libraries.BeanMapper;
import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.common.rest.web.model.response.BaseResponse;
import com.tl.booking.common.rest.web.model.response.CommonResponse;
import com.tl.booking.promo.code.entity.constant.ApiPath;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.dao.SystemParameter;
import com.tl.booking.promo.code.entity.dao.SystemParameterBuilder;
import com.tl.booking.promo.code.libraries.exception.BusinessLogicException;
import com.tl.booking.promo.code.libraries.utility.DateFormatterHelper;
import com.tl.booking.promo.code.rest.web.model.request.SystemParameterRequest;
import com.tl.booking.promo.code.rest.web.model.response.SystemParameterResponse;
import com.tl.booking.promo.code.service.api.SystemParameterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPath.SYSTEM_PARAMETER)
@Api(value = "System Parameter")
public class SystemParameterController {

  @Autowired
  private SystemParameterService systemParameterService;

  @ApiOperation(value = "Get.systemParameter", notes = "Put all mandatory parameter")
  @RequestMapping(method = RequestMethod.GET)
  public BaseResponse<SystemParameterResponse> findByStoreIdAndVariable(
      @Valid @ModelAttribute MandatoryRequest mandatoryRequest, @RequestParam String variable) {
    SystemParameter systemParameter = this.systemParameterService
        .findSystemParameterByStoreId(mandatoryRequest
            .getStoreId(), variable);
    if (systemParameter == null) {
      throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
          ResponseCode.DATA_NOT_EXIST.getMessage());
    }
    SystemParameterResponse systemParameterResponse = BeanMapper
        .map(systemParameter, SystemParameterResponse
            .class);

    return CommonResponse
        .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
            null,
            systemParameterResponse);
  }

  @ApiOperation(value = "Get.systemParameters (Paginated)", notes = "Put all mandatory parameter")
  @RequestMapping(path = "all", method = RequestMethod.GET)
  public BaseResponse<Page<SystemParameter>> findByStoreIdAndVariablePaginated(
      @Valid @ModelAttribute MandatoryRequest mandatoryRequest, @RequestParam Integer page,
      @RequestParam Integer limit) {
    Page<SystemParameter> systemParameters = this.systemParameterService
        .findAll(mandatoryRequest
            .getStoreId(), page, limit);

    return CommonResponse
        .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
            null,
            systemParameters);
  }

  @PostMapping
  public BaseResponse createSystemParameter(
      @Valid @ModelAttribute MandatoryRequest mandatoryRequest, @Valid @RequestBody
      SystemParameterRequest systemParameterRequest) {
    SystemParameter systemParameter = new SystemParameterBuilder()
        .withStoreId(mandatoryRequest.getStoreId())
        .withVariable(systemParameterRequest.getVariable())
        .withValue(systemParameterRequest.getValue())
        .withDescription(systemParameterRequest.getDescription())
        .withCreatedBy(mandatoryRequest
            .getUsername()).withCreatedDate(DateFormatterHelper.getTodayDate())
        .withUpdatedBy(mandatoryRequest.getUsername
            ()).withUpdatedDate(DateFormatterHelper.getTodayDate()).build();

    this.systemParameterService.createSystemParameter(systemParameter);

    return CommonResponse
        .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
            null, null);
  }

  @PutMapping
  public BaseResponse updateSystemParameter(
      @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @RequestBody SystemParameterRequest systemParameterRequest) {
    SystemParameter systemParameter = new SystemParameterBuilder()
        .withStoreId(mandatoryRequest.getStoreId())
        .withVariable(systemParameterRequest.getVariable())
        .withValue(systemParameterRequest.getValue())
        .withDescription(systemParameterRequest.getDescription())
        .withUpdatedBy(mandatoryRequest.getUsername())
        .withUpdatedDate(DateFormatterHelper.getTodayDate())
        .build();

    this.systemParameterService.updateSystemParameter(systemParameter);

    return CommonResponse
        .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
            null, null);
  }

  @DeleteMapping
  public BaseResponse deleteByStoreIdAndVariable(
      @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @RequestParam String variable) {
    this.systemParameterService.deleteByStoreIdAndVariable(mandatoryRequest.getStoreId(), variable);

    return CommonResponse
        .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
            null, null);
  }
}
