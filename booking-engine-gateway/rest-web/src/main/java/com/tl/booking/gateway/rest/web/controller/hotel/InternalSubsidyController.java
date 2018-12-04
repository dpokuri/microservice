package com.tl.booking.gateway.rest.web.controller.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.common.rest.web.model.request.MandatoryRequestBuilder;
import com.tl.booking.gateway.libraries.utility.CommonHelper;
import com.tl.booking.gateway.service.api.hotel.InternalSubsidyService;
import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.constant.DefaultValues;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.dao.common.BaseMongo;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.Hotel.InternalSubsidyActivateRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.InternalSubsidyRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.InternalSubsidyResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@Api(value = "Internal Subsidy")
public class InternalSubsidyController {

  private static final String MDC_PRIVILEGES = "privileges";

  @Autowired
  public InternalSubsidyService internalSubsidyService;

  @ApiOperation(value = "Create Internal Subsidy", notes = "Put all mandatory parameter")
  @PostMapping(value = ApiPath.INTERNAL_SUBSIDY)
  public DeferredResult<GatewayBaseResponse>
  createSubsidy(
      @Valid @RequestBody InternalSubsidyRequest subsidyRequest
  ) {
    DeferredResult<GatewayBaseResponse> deferred = new DeferredResult<>();

    SessionData sessionData = CommonHelper.getSessionData();

    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String) MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String) MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String) MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String) MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String) MDC.get(BaseMongoFields.USERNAME))
        .build();

    this.internalSubsidyService
        .createInternalSubsidy(mandatoryRequest, subsidyRequest, (String) MDC.get(MDC_PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @ApiOperation(value = "Update Internal Subsidy", notes = "Put all mandatory parameter")
  @PutMapping(value = ApiPath.INTERNAL_SUBSIDY + "/{id}")
  public DeferredResult<GatewayBaseResponse> updateSubsidy(
      @PathVariable String id,
      @Valid @RequestBody InternalSubsidyRequest subsidyRequest
  ) {
    DeferredResult<GatewayBaseResponse> deferred = new DeferredResult<>();

    SessionData sessionData = new SessionData();
    sessionData.setUsername((String)MDC.get("username"));

    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String) MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String) MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String) MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String) MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String) MDC.get(BaseMongoFields.USERNAME))
        .build();

    this.internalSubsidyService.updateInternalSubsidy(mandatoryRequest, subsidyRequest, id,
        (String) MDC.get(MDC_PRIVILEGES), sessionData).subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @ApiOperation(value = "Set Active / Inactive Internal Subsidy", notes = "Put all mandatory parameter")
  @PutMapping(value = ApiPath.INTERNAL_SUBSIDY + "/activate/{id}")
  public DeferredResult<GatewayBaseResponse> setActiveSubsidy(
      @PathVariable String id,
      @Valid @RequestBody InternalSubsidyActivateRequest subsidyRequest
  ) {
    DeferredResult<GatewayBaseResponse> deferred = new DeferredResult<>();

    SessionData sessionData = CommonHelper.getSessionData();

    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String) MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String) MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String) MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String) MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String) MDC.get(BaseMongoFields.USERNAME))
        .build();

    this.internalSubsidyService.setActiveInternalSubsidy(mandatoryRequest, subsidyRequest,
        id, (String) MDC.get(MDC_PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;

  }

  @ApiOperation(value = "Delete Internal Subsidy", notes = "Put all mandatory parameter")
  @DeleteMapping(value = ApiPath.INTERNAL_SUBSIDY + "/{id}")
  public DeferredResult<GatewayBaseResponse> deleteInternalSubsidy(
      @PathVariable String id
  ) {
    DeferredResult<GatewayBaseResponse> deferred = new DeferredResult<>();

    SessionData sessionData = CommonHelper.getSessionData();

    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String) MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String) MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String) MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String) MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String) MDC.get(BaseMongoFields.USERNAME))
        .build();

    this.internalSubsidyService.deleteInternalSubsidy(mandatoryRequest, id,
        (String) MDC.get(MDC_PRIVILEGES), sessionData).subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @ApiOperation(value = "Get Detail Internal Subsidy", notes = "Put all mandatory parameter")
  @GetMapping(value = ApiPath.INTERNAL_SUBSIDY + "/{id}")
  public DeferredResult<GatewayBaseResponse> findInternalSubsidyByStoreIdAndId(
      @PathVariable String id
  ) {
    DeferredResult<GatewayBaseResponse> deferred = new DeferredResult<>();

    SessionData sessionData = CommonHelper.getSessionData();

    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String) MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String) MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String) MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String) MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String) MDC.get(BaseMongoFields.USERNAME))
        .build();

    this.internalSubsidyService.findInternalSubsidyByStoreIdAndId(mandatoryRequest, id,
        (String) MDC.get(MDC_PRIVILEGES), sessionData).subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @ApiOperation(value = "Get Hotel Internal Subsidy", notes = "Put all mandatory parameters")
  @RequestMapping(value = ApiPath.INTERNAL_SUBSIDY, method = RequestMethod.GET)
  public DeferredResult<GatewayBaseResponse<RestResponsePage<InternalSubsidyResponse>>> findInternalSubsidyByStoreId(
      @RequestParam(value = "page", defaultValue = DefaultValues.PAGE) Integer page,
      @RequestParam(value = "limit", defaultValue = DefaultValues.PAGE_LIMIT) Integer limit,
      @RequestParam(value = "q", defaultValue = "", required = false) String q,
      @RequestParam(value = "countryId", defaultValue = "", required = false) String countryId,
      @RequestParam(value = "provinceId", defaultValue = "", required = false) String provinceId,
      @RequestParam(value = "cityId", defaultValue = "", required = false) String cityId,
      @RequestParam(value = "areaId", defaultValue = "", required = false) String areaId,
      @RequestParam(value = "sort", defaultValue = DefaultValues.SORT_BY_ACTIVE) String sort,
      @RequestParam(value = "method", defaultValue = DefaultValues.SORT_METHOD) String method
  ) {
    DeferredResult<GatewayBaseResponse<RestResponsePage<InternalSubsidyResponse>>> deferred = new DeferredResult<>();

    SessionData sessionData = CommonHelper.getSessionData();

    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String) MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String) MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String) MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String) MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String) MDC.get(BaseMongoFields.USERNAME))
        .build();

    Map<String, String> queryParam = new HashMap<>();
    queryParam.put("page", page.toString());
    queryParam.put("limit", limit.toString());
    queryParam.put("q", q);
    queryParam.put("countryId", countryId);
    queryParam.put("provinceId", provinceId);
    queryParam.put("cityId", cityId);
    queryParam.put("areaId", areaId);
    queryParam.put("sort", sort);
    queryParam.put("method", method);

    Map<String, String> queryParam1 = new HashMap<>();

    for (Map.Entry<String, String> entry : queryParam.entrySet()) {
      if (!entry.getValue().isEmpty()) {
        queryParam1.put(entry.getKey(), entry.getValue());
      }
    }

    this.internalSubsidyService.findInternalSubsidyByStoreId(mandatoryRequest, queryParam1,
        (String) MDC.get(MDC_PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;

  }


}
