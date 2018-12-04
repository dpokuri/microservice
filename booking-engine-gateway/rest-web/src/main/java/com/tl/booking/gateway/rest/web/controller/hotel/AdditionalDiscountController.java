package com.tl.booking.gateway.rest.web.controller.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.common.rest.web.model.request.MandatoryRequestBuilder;
import com.tl.booking.gateway.service.api.hotel.AdditionalDiscountService;
import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.constant.DefaultValues;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.Hotel.AdditionalDiscountRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.AdditionalDiscountResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@Api(value = "Additional Discount")
public class AdditionalDiscountController {

  private static final String MDC_PRIVILEGES = "privileges";

  @Autowired
  public AdditionalDiscountService additionalDiscountService;

  @ApiOperation(value = "Create Additional Discount", notes = "Put all mandatory parameter")
  @PostMapping(value = ApiPath.URL_ADDITIONAL_DISCOUNT)
  public DeferredResult<GatewayBaseResponse> createAdditionalDiscount(
      @Valid @RequestBody AdditionalDiscountRequest additionalDiscountRequest
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

    this.additionalDiscountService.createAdditionalDiscount(
        mandatoryRequest,
        additionalDiscountRequest,
        (String) MDC.get(MDC_PRIVILEGES), sessionData).subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @ApiOperation(value = "Update Additional Discount", notes = "Put all mandatory parameter")
  @PutMapping(value = ApiPath.URL_ADDITIONAL_DISCOUNT + ApiPath.ID)
  public DeferredResult<GatewayBaseResponse> updateAdditionalDiscount(
      @Valid @RequestBody AdditionalDiscountRequest additionalDiscountRequest,
      @PathVariable String id
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

    this.additionalDiscountService
        .updateAdditionalDiscount(mandatoryRequest, additionalDiscountRequest,
            id, (String) MDC.get(MDC_PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @ApiOperation(value = "Delete Additional Discount", notes = "Put all mandatory parameter")
  @DeleteMapping(value = ApiPath.URL_ADDITIONAL_DISCOUNT + ApiPath.ID)
  public DeferredResult<GatewayBaseResponse> deleteAdditionalDiscount(
      @PathVariable String id
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

    this.additionalDiscountService
        .deleteAdditionalDiscount(mandatoryRequest, id, (String) MDC.get(MDC_PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @ApiOperation(value = "Find Additional Discount by Hotel and Type", notes = "Put all mandatory parameter")
  @GetMapping(value = ApiPath.URL_ADDITIONAL_DISCOUNT + "/hotel")
  public DeferredResult<GatewayBaseResponse> findAdditionalDiscountByHotel(
      @RequestParam(value = "hotelId") Integer hotelId,
      @RequestParam(value = "type") String type
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

    this.additionalDiscountService
        .findAdditionalDiscountByHotel(mandatoryRequest, hotelId, type,
            (String) MDC.get(MDC_PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @ApiOperation(value = "Find Additional Discount by id", notes = "Put all mandatory parameter")
  @GetMapping(value = ApiPath.URL_ADDITIONAL_DISCOUNT + ApiPath.ID)
  public DeferredResult<GatewayBaseResponse> findAdditionalDiscountById(
      @PathVariable String id
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

    this.additionalDiscountService
        .findAdditionalDiscountById(mandatoryRequest, id, (String) MDC.get(MDC_PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @ApiOperation(value = "Find Additional Discount", notes = "Put all mandatory parameter")
  @GetMapping(value = ApiPath.URL_ADDITIONAL_DISCOUNT)
  public DeferredResult<GatewayBaseResponse<RestResponsePage<AdditionalDiscountResponse>>>
  findAdditionalDiscount(
      @RequestParam(value = "type", defaultValue = "") String type,
      @RequestParam(value = "hotelId", required = false, defaultValue = "") String hotelId,
      @RequestParam(value = "page", defaultValue = DefaultValues.PAGE) Integer page,
      @RequestParam(value = "limit", defaultValue = DefaultValues.PAGE_LIMIT) Integer limit
  ) {

    DeferredResult<GatewayBaseResponse<RestResponsePage<AdditionalDiscountResponse>>> deferred
        = new DeferredResult<>();

    SessionData sessionData = new SessionData();
    sessionData.setUsername((String)MDC.get("username"));

    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String) MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String) MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String) MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String) MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String) MDC.get(BaseMongoFields.USERNAME))
        .build();

    this.additionalDiscountService
        .findAdditionalDiscount(mandatoryRequest, page, limit, type,
            hotelId, (String) MDC.get(MDC_PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;

  }
}
