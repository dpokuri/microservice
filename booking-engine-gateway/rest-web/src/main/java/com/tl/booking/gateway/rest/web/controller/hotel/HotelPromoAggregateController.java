package com.tl.booking.gateway.rest.web.controller.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.service.api.hotel.HotelPromoAggregateService;
import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.constant.fields.MandatoryFields;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.Hotel.FindAllPromoParam;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoAggregateRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoAggregateResponse;

import io.swagger.annotations.Api;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
@RequestMapping(ApiPath.HOTEL_PROMO_AGGREGATE)
@Api(value = "Hotel - Promo Aggregate")
public class HotelPromoAggregateController {

  private final HotelPromoAggregateService hotelPromoAggregateService;

  private MandatoryRequest mandatoryRequest;

  @Autowired
  public HotelPromoAggregateController(HotelPromoAggregateService hotelPromoAggregateService) {
    this.hotelPromoAggregateService = hotelPromoAggregateService;
  }

  @GetMapping
  public DeferredResult<GatewayBaseResponse<RestResponsePage<HotelPromoAggregateResponse>>>
  findAllHotelPromoByHotelIdAndRoomIdAndDate(HttpServletRequest request,
      @Valid @ModelAttribute FindAllPromoParam findAllPromoParam) {

    mandatoryRequest = (MandatoryRequest) request.getAttribute(MandatoryFields.MANDATORY_REQUEST);

    DeferredResult<GatewayBaseResponse<RestResponsePage<HotelPromoAggregateResponse>>> deferred =
        new DeferredResult<>();

    SessionData sessionData = new SessionData();
    sessionData.setUsername(mandatoryRequest.getUsername());
    sessionData.setBusinessId((String)MDC.get(BaseMongoFields.BUSINESS_ID));

    this.hotelPromoAggregateService
        .findAllHotelPromoByHotelIdAndRoomIdAndDate(
            mandatoryRequest, findAllPromoParam,
            (String) MDC.get(BaseMongoFields.PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @GetMapping(value = ApiPath.ID)
  public DeferredResult<GatewayBaseResponse<HotelPromoAggregateResponse>> findHotelPromoById(
      HttpServletRequest request, @PathVariable("id") String id) {

    mandatoryRequest = (MandatoryRequest) request.getAttribute(MandatoryFields.MANDATORY_REQUEST);

    DeferredResult<GatewayBaseResponse<HotelPromoAggregateResponse>> deferred = new DeferredResult<>();

    SessionData sessionData = new SessionData();
    sessionData.setUsername(mandatoryRequest.getUsername());

    this.hotelPromoAggregateService
        .findHotelPromoById(mandatoryRequest, id, (String) MDC.get(BaseMongoFields.PRIVILEGES),
            sessionData).subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @PostMapping
  public DeferredResult<GatewayBaseResponse<HotelPromoAggregateResponse>> createHotelPromoAggregate(
      HttpServletRequest request,
      @RequestBody HotelPromoAggregateRequest hotelPromoAggregateRequest) {

    mandatoryRequest = (MandatoryRequest) request.getAttribute(MandatoryFields.MANDATORY_REQUEST);

    DeferredResult<GatewayBaseResponse<HotelPromoAggregateResponse>> deferred = new DeferredResult<>();

    SessionData sessionData = new SessionData();
    sessionData.setUsername(mandatoryRequest.getUsername());

    this.hotelPromoAggregateService
        .createHotelPromoAggregate(
            mandatoryRequest, hotelPromoAggregateRequest,
            (String) MDC.get(BaseMongoFields.PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @PutMapping(value = ApiPath.ID)
  public DeferredResult<GatewayBaseResponse<HotelPromoAggregateResponse>> updateHotelPromoAggregate(
      HttpServletRequest request, @PathVariable("id") String id,
      @RequestBody HotelPromoAggregateRequest hotelPromoAggregateRequest) {

    mandatoryRequest = (MandatoryRequest) request.getAttribute(MandatoryFields.MANDATORY_REQUEST);

    DeferredResult<GatewayBaseResponse<HotelPromoAggregateResponse>> deferred = new DeferredResult<>();

    SessionData sessionData = new SessionData();
    sessionData.setUsername(mandatoryRequest.getUsername());

    this.hotelPromoAggregateService
        .updateHotelPromoAggregate(
            mandatoryRequest, id, hotelPromoAggregateRequest,
            (String) MDC.get(BaseMongoFields.PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @DeleteMapping(value = ApiPath.ID)
  public DeferredResult<GatewayBaseResponse> deleteHotelPromoAggregate(HttpServletRequest request,
      @PathVariable("id") String id) {

    mandatoryRequest = (MandatoryRequest) request.getAttribute(MandatoryFields.MANDATORY_REQUEST);

    DeferredResult<GatewayBaseResponse> deferred = new DeferredResult<>();

    SessionData sessionData = new SessionData();
    sessionData.setUsername(mandatoryRequest.getUsername());

    this.hotelPromoAggregateService
        .deleteHotelPromoAggregate(
            mandatoryRequest, id, (String) MDC.get(BaseMongoFields.PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }
}
