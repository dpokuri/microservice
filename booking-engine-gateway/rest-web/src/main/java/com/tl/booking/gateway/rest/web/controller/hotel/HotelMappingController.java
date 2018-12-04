package com.tl.booking.gateway.rest.web.controller.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.common.rest.web.model.request.MandatoryRequestBuilder;
import com.tl.booking.gateway.service.api.hotel.HotelMappingService;
import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.constant.DefaultValues;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelMappingRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelMappingResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping(ApiPath.HOTEL_MAPPING)
@Api(value = "Hotel - Hotel Mapping")
public class HotelMappingController {

  @Autowired
  private HotelMappingService hotelMappingService;

  private static final String MDC_PRIVILEGES = "privileges";

  @ApiOperation(value="Get.HotelMapping", notes="Put all mandatory parameters")
  @GetMapping
  public DeferredResult<GatewayBaseResponse<RestResponsePage<HotelMappingResponse>>> findHotelMappingsByStoreId(
      @RequestParam(value="page", defaultValue = DefaultValues.PAGE) Integer page,
      @RequestParam(value="size", defaultValue = DefaultValues.PAGE_LIMIT) Integer limit,
      @RequestParam(value="q", defaultValue = "", required = false) String q,
      @RequestParam(value="regionId", defaultValue = "", required = false) String regionId,
      @RequestParam(value="sort", defaultValue = DefaultValues.SORT_BY) String sort,
      @RequestParam(value="method", defaultValue = DefaultValues.SORT_METHOD) String method
  ){
    DeferredResult<GatewayBaseResponse<RestResponsePage<HotelMappingResponse>>> deferred =
        new DeferredResult<>();

    SessionData sessionData = new SessionData();
    sessionData.setUsername((String)MDC.get("username"));

    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String)MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String)MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String)MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String)MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String)MDC.get(BaseMongoFields.USERNAME))
        .build();

    this.hotelMappingService
        .findHotelMappingsByStoreId(mandatoryRequest, page,
            limit, q, regionId, sort, method, (String)MDC.get(MDC_PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @GetMapping(value="/{id}")
  public DeferredResult<GatewayBaseResponse<HotelMappingResponse>> findHotelMappingByStoreIdAndId(
      @PathVariable String id
  ){
    DeferredResult<GatewayBaseResponse<HotelMappingResponse>> deferred = new DeferredResult<>();

    SessionData sessionData = new SessionData();
    sessionData.setUsername((String)MDC.get("username"));

    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String)MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String)MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String)MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String)MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String)MDC.get(BaseMongoFields.USERNAME))
        .build();

    this.hotelMappingService
        .findHotelMappingByStoreIdAndId(mandatoryRequest, id, (String)MDC.get(MDC_PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @PostMapping
  public DeferredResult<GatewayBaseResponse> createHotelMapping(
      @RequestBody HotelMappingRequest hotelMappingRequest) {

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

    this.hotelMappingService
        .createHotelMapping(mandatoryRequest, hotelMappingRequest, (String)MDC.get(MDC_PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @PutMapping(value = "/{id}")
  public DeferredResult<GatewayBaseResponse> updateHotelMapping(
      @PathVariable String id,
      @RequestBody HotelMappingRequest hotelMappingRequest) {

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

    this.hotelMappingService
        .updateHotelMapping(mandatoryRequest, id, hotelMappingRequest, (String)MDC.get(MDC_PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @DeleteMapping(value = "/{id}")
  public DeferredResult<GatewayBaseResponse> deleteHotelMapping(
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

    this.hotelMappingService
        .deleteHotelMapping(mandatoryRequest, id, (String)MDC.get(MDC_PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

}
