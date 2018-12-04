package com.tl.booking.gateway.rest.web.controller;

import com.tl.booking.common.libraries.BeanMapper;
import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.common.rest.web.model.response.BaseResponse;
import com.tl.booking.common.rest.web.model.response.CommonResponse;
import com.tl.booking.gateway.rest.web.model.request.SystemParameterRequest;
import com.tl.booking.gateway.rest.web.model.response.SystemParameterResponse;
import com.tl.booking.gateway.service.api.SystemParameterService;
import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.constant.enums.ResponseCode;
import com.tl.booking.gateway.entity.dao.SystemParameter;
import com.tl.booking.gateway.entity.dao.SystemParameterBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.apache.log4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPath.SYSTEM_PARAMETER)
@Api(value = "System Parameter")
public class SystemParameterController {

  private static final Logger LOGGER = LoggerFactory.getLogger(SystemParameterController.class);

  @Autowired
  private SystemParameterService systemParameterService;

  @ApiOperation(value = "Get.systemParameter", notes = "Put all mandatory parameter")
  @RequestMapping(path = ApiPath.ID, method = RequestMethod.GET)
  public BaseResponse<SystemParameterResponse> findSystemParameterById(
      @PathVariable("id") String id,
      @Valid @ModelAttribute MandatoryRequest mandatoryRequest) {
    LOGGER.info("findSystemParameterById Request mandatoryRequest = {}, id = {}",
        mandatoryRequest, id);

    SystemParameter systemParameter = this.systemParameterService.findSystemParameterById(id);

    SystemParameterResponse systemParameterResponse = this
        .toSystemParameterResponse(systemParameter);

    return CommonResponse
        .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
            null, systemParameterResponse);
  }

  @ApiOperation(value = "Get.systemParameters (Paginated)", notes = "Put all mandatory parameter")
  @RequestMapping(method = RequestMethod.GET)
  public BaseResponse<Page<SystemParameterResponse>> findAllSystemParametersByStoreId(
      @Valid @ModelAttribute MandatoryRequest mandatoryRequest, @RequestParam Integer page,
      @RequestParam Integer limit) {
    LOGGER.info(
        "findAllSystemParametersByStoreId Request mandatoryRequest = {}, page = {}, limit = {}",
        mandatoryRequest, page, limit);

    Page<SystemParameter> systemParameters = this.systemParameterService
        .findAllSystemParametersByStoreId(mandatoryRequest
            .getStoreId(), page, limit);

    Page<SystemParameterResponse> systemParameterResponses = this
        .toPageSystemParameterResponse(systemParameters);

    return CommonResponse
        .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
            null, systemParameterResponses);
  }

  @RequestMapping(method = RequestMethod.POST)
  public BaseResponse<SystemParameterResponse> createSystemParameter(
      @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @Valid @RequestBody SystemParameterRequest systemParameterRequest) {
    LOGGER.info(
        "createSystemParameter Request mandatoryRequest = {}, systemParameterRequest = {}",
        mandatoryRequest, systemParameterRequest);

    SystemParameter systemParameter = this.toSystemParameter(systemParameterRequest);

    SystemParameter createdSystemParameter = this.systemParameterService
        .createSystemParameter(systemParameter);

    SystemParameterResponse systemParameterResponse = this
        .toSystemParameterResponse(createdSystemParameter);

    return CommonResponse
        .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
            null, systemParameterResponse);
  }

  @RequestMapping(path = ApiPath.ID, method = RequestMethod.PUT)
  public BaseResponse<SystemParameterResponse> updateSystemParameter(
      @PathVariable("id") String id,
      @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @RequestBody SystemParameterRequest systemParameterRequest) {
    LOGGER.info(
        "updateSystemParameter Request mandatoryRequest = {}, systemParameterRequest = {}, id = {}",
        mandatoryRequest, systemParameterRequest, id);

    SystemParameter systemParameter = this.toSystemParameter(systemParameterRequest);

    SystemParameter updatedSystemParameter = this.systemParameterService
        .updateSystemParameter(systemParameter, id);

    SystemParameterResponse systemParameterResponse = this
        .toSystemParameterResponse(updatedSystemParameter);

    return CommonResponse
        .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
            null, systemParameterResponse);
  }

  @RequestMapping(path = ApiPath.ID, method = RequestMethod.DELETE)
  public BaseResponse<Boolean> deleteSystemParameterById(
      @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @PathVariable("id") String id) {
    LOGGER.info(
        "deleteSystemParameterById Request mandatoryRequest = {}, id = {}",
        mandatoryRequest, id);

    Boolean status = this.systemParameterService.deleteSystemParameterById(id);

    return CommonResponse
        .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
            null, status);
  }

  private SystemParameter toSystemParameter(SystemParameterRequest systemParameterRequest) {
    return new SystemParameterBuilder()
        .withStoreId((String)MDC.get("storeId"))
        .withValue(systemParameterRequest.getValue())
        .withVariable(systemParameterRequest.getVariable())
        .withDescription(systemParameterRequest.getDescription()).build();
  }

  private SystemParameterResponse toSystemParameterResponse(SystemParameter systemParameter) {
    return BeanMapper.map(systemParameter, SystemParameterResponse.class);
  }

  private Page<SystemParameterResponse> toPageSystemParameterResponse(
      Page<SystemParameter> systemParameters) {
    return systemParameters
        .map(systemParameter -> BeanMapper.map(systemParameter, SystemParameterResponse.class));
  }
}
