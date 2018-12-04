package com.tl.booking.gateway.rest.web.controller.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.common.rest.web.model.request.MandatoryRequestBuilder;
import com.tl.booking.gateway.service.api.hotel.HotelOtaService;
import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.Hotel.OtaRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.OtaResponse;

import io.swagger.annotations.Api;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequestMapping(ApiPath.OTA)
@Api(value = "Hotel - OTA")
public class HotelOtaController {

  @Autowired
  private HotelOtaService hotelOtaService;

  private static final String MDC_PRIVILEGES = "privileges";

  @GetMapping(path = "")
  public DeferredResult<GatewayBaseResponse<List<OtaResponse>>> findHotelOta(){

    DeferredResult<GatewayBaseResponse<List<OtaResponse>>> deferred = new DeferredResult<>();

    SessionData sessionData = new SessionData();
    sessionData.setUsername((String)MDC.get("username"));

    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String)MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String)MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String)MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String)MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String)MDC.get(BaseMongoFields.USERNAME))
        .build();

    this.hotelOtaService.findHotelOta(mandatoryRequest, (String)MDC.get(MDC_PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @GetMapping(path = "/{id}")
  public DeferredResult<GatewayBaseResponse<OtaResponse>> findHotelOtaById(
      @PathVariable("id") String id){

    DeferredResult<GatewayBaseResponse<OtaResponse>> deferred = new DeferredResult<>();

    SessionData sessionData = new SessionData();
    sessionData.setUsername((String)MDC.get("username"));

    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String)MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String)MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String)MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String)MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String)MDC.get(BaseMongoFields.USERNAME))
        .build();

    this.hotelOtaService.findHotelOtaById(mandatoryRequest, id, (String)MDC.get(MDC_PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;

  }

  @PostMapping(path = "")
  public DeferredResult<GatewayBaseResponse<OtaResponse>> createHotelOta(
      @RequestBody OtaRequest otaRequest) {

    DeferredResult<GatewayBaseResponse<OtaResponse>> deferred = new DeferredResult<>();

    SessionData sessionData = new SessionData();
    sessionData.setUsername((String)MDC.get("username"));

    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String)MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String)MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String)MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String)MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String)MDC.get(BaseMongoFields.USERNAME))
        .build();
    this.hotelOtaService.createHotelOta(mandatoryRequest, otaRequest, (String)MDC.get(MDC_PRIVILEGES), sessionData)
        .subscribe(
            deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @PutMapping(value = "/{id}")
  public DeferredResult<GatewayBaseResponse<OtaResponse>> updateHotelOta(
      @RequestBody OtaRequest otaRequest,
      @PathVariable String id) {

    DeferredResult<GatewayBaseResponse<OtaResponse>> deferred = new DeferredResult<>();

    SessionData sessionData = new SessionData();
    sessionData.setUsername((String)MDC.get("username"));

    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String)MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String)MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String)MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String)MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String)MDC.get(BaseMongoFields.USERNAME))
        .build();
    this.hotelOtaService.updateHotelOta(mandatoryRequest, id, otaRequest, (String)MDC.get(MDC_PRIVILEGES), sessionData)
        .subscribe(
            deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @DeleteMapping(value = "/{id}")
  public DeferredResult<GatewayBaseResponse> deleteByStoreIdAndId(
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
    this.hotelOtaService.deleteHotelOta(mandatoryRequest, id, (String)MDC.get(MDC_PRIVILEGES), sessionData)
        .subscribe(
            deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @ModelAttribute
  public MandatoryRequest getMandatoryParameter(HttpServletRequest request) {
    return (MandatoryRequest) request.getAttribute("mandatory");
  }
}
