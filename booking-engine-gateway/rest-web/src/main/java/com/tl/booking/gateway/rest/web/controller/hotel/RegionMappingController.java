package com.tl.booking.gateway.rest.web.controller.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.common.rest.web.model.request.MandatoryRequestBuilder;
import com.tl.booking.gateway.service.api.hotel.RegionMappingService;
import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.constant.DefaultValues;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.Hotel.RegionMappingRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.RegionMappingResponse;

import io.swagger.annotations.Api;
import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequestMapping(ApiPath.REGION_MAPPING)
@Api(value = "Hotel - Region Mapping")
public class RegionMappingController {

  @Autowired
  private RegionMappingService regionMappingService;

  private static final String MDC_PRIVILEGES = "privileges";

  @GetMapping
  public DeferredResult<GatewayBaseResponse<RestResponsePage<RegionMappingResponse>>> findRegionMappingsByStoreId(
      @RequestParam(value = "page", defaultValue = DefaultValues.PAGE) Integer page,
      @RequestParam(value = "limit", defaultValue = DefaultValues.PAGE_LIMIT) Integer limit,
      @RequestParam(value = "q", defaultValue = "", required = false) String q,
      @RequestParam(value = "sort", defaultValue = DefaultValues.SORT_BY) String sort,
      @RequestParam(value = "method", defaultValue = DefaultValues.SORT_METHOD) String method) {

    DeferredResult<GatewayBaseResponse<RestResponsePage<RegionMappingResponse>>> deferred = new DeferredResult<>();

    SessionData sessionData = new SessionData();
    sessionData.setUsername((String)MDC.get("username"));

    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String)MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String)MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String)MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String)MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String)MDC.get(BaseMongoFields.USERNAME))
        .build();

    this.regionMappingService
        .findRegionMappingsByStoreId(mandatoryRequest, page,
            limit,
            q,
            sort,
            method, (String)MDC.get(MDC_PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @GetMapping(value = {"/{id}"})
  public DeferredResult<GatewayBaseResponse<RegionMappingResponse>> findRegionMappingByStoreIdAndId(
      @PathVariable(value = "id") String id) {

    DeferredResult<GatewayBaseResponse<RegionMappingResponse>> deferred = new DeferredResult<>();

    SessionData sessionData = new SessionData();
    sessionData.setUsername((String)MDC.get("username"));

    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String)MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String)MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String)MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String)MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String)MDC.get(BaseMongoFields.USERNAME))
        .build();

    this.regionMappingService
        .findRegionMappingByStoreIdAndId(mandatoryRequest, id, (String)MDC.get(MDC_PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }


  @PostMapping
  public DeferredResult<GatewayBaseResponse> createRegionMapping(
      @RequestBody RegionMappingRequest regionMappingRequest) {

    DeferredResult<GatewayBaseResponse> deferred = new DeferredResult<>();

    SessionData sessionData = new SessionData();
    sessionData.setUsername((String)MDC.get("username"));

    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String)MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String)MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String)MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String)MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String)MDC.get(BaseMongoFields.USERNAME))
        .build();

    this.regionMappingService
        .createRegionMapping(mandatoryRequest, regionMappingRequest, (String)MDC.get(MDC_PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @PutMapping(value = "/{id}")
  public DeferredResult<GatewayBaseResponse> updateRegionMapping(
      @PathVariable String id,
      @RequestBody RegionMappingRequest regionMappingRequest) {

    DeferredResult<GatewayBaseResponse> deferred = new DeferredResult<>();

    SessionData sessionData = new SessionData();
    sessionData.setUsername((String)MDC.get("username"));

    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String)MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String)MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String)MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String)MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String)MDC.get(BaseMongoFields.USERNAME))
        .build();

    this.regionMappingService
        .updateRegionMapping(mandatoryRequest, id, regionMappingRequest, (String)MDC.get(MDC_PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @DeleteMapping(value = "/{id}")
  public DeferredResult<GatewayBaseResponse> deleteRegionMapping(
      @PathVariable String id) {

    DeferredResult<GatewayBaseResponse> deferred = new DeferredResult<>();

    SessionData sessionData = new SessionData();
    sessionData.setUsername((String)MDC.get("username"));

    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String)MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String)MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String)MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String)MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String)MDC.get(BaseMongoFields.USERNAME))
        .build();

    this.regionMappingService
        .deleteRegionMapping(mandatoryRequest, id, (String)MDC.get(MDC_PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

}
