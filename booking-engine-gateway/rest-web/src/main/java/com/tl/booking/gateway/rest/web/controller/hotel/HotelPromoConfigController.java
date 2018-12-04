package com.tl.booking.gateway.rest.web.controller.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.service.api.hotel.HotelPromoConfigService;
import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.constant.fields.MandatoryFields;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoConfigFindAllParams;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoConfigRequest;

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
@RequestMapping(ApiPath.HOTEL_PROMO_CONFIG)
@Api(value = "Hotel - Promo Config")
public class HotelPromoConfigController {

  private final HotelPromoConfigService hotelPromoConfigService;

  private MandatoryRequest mandatoryRequest;

  @Autowired
  public HotelPromoConfigController(HotelPromoConfigService hotelPromoConfigService) {
    this.hotelPromoConfigService = hotelPromoConfigService;
  }

  DeferredResult<GatewayBaseResponse<Object>> deferred = new DeferredResult<>();

  @PostMapping
  public DeferredResult<GatewayBaseResponse<Object>> create(
      HttpServletRequest request,
      @RequestBody HotelPromoConfigRequest hotelPromoConfigRequest) {

    mandatoryRequest = (MandatoryRequest) request.getAttribute(MandatoryFields.MANDATORY_REQUEST);

    SessionData sessionData = new SessionData();
    sessionData.setUsername(mandatoryRequest.getUsername());

    this.hotelPromoConfigService
        .create(mandatoryRequest, hotelPromoConfigRequest,
            (String) MDC.get(BaseMongoFields.PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @PutMapping(ApiPath.ID)
  public DeferredResult<GatewayBaseResponse<Object>> update(
      HttpServletRequest request, @PathVariable("id") String id,
      @RequestBody HotelPromoConfigRequest hotelPromoConfigRequest) {

    mandatoryRequest = (MandatoryRequest) request.getAttribute(MandatoryFields.MANDATORY_REQUEST);

    SessionData sessionData = new SessionData();
    sessionData.setUsername(mandatoryRequest.getUsername());

    this.hotelPromoConfigService
        .update(mandatoryRequest, id, hotelPromoConfigRequest,
            (String) MDC.get(BaseMongoFields.PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @DeleteMapping(ApiPath.ID)
  public DeferredResult<GatewayBaseResponse<Object>> delete(
      HttpServletRequest request, @PathVariable("id") String id) {

    mandatoryRequest = (MandatoryRequest) request.getAttribute(MandatoryFields.MANDATORY_REQUEST);

    SessionData sessionData = new SessionData();
    sessionData.setUsername(mandatoryRequest.getUsername());

    this.hotelPromoConfigService
        .delete(mandatoryRequest, id, (String) MDC.get(BaseMongoFields.PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @GetMapping(ApiPath.ID)
  public DeferredResult<GatewayBaseResponse<Object>> getOne(
      HttpServletRequest request, @PathVariable("id") Integer id) {

    mandatoryRequest = (MandatoryRequest) request.getAttribute(MandatoryFields.MANDATORY_REQUEST);

    SessionData sessionData = new SessionData();
    sessionData.setUsername(mandatoryRequest.getUsername());

    this.hotelPromoConfigService
        .getOne(mandatoryRequest, id, (String) MDC.get(BaseMongoFields.PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @GetMapping
  public DeferredResult<GatewayBaseResponse<Object>> update(
      HttpServletRequest request,
      @ModelAttribute @Valid HotelPromoConfigFindAllParams hotelPromoConfigFindAllParams) {

    mandatoryRequest = (MandatoryRequest) request.getAttribute(MandatoryFields.MANDATORY_REQUEST);

    SessionData sessionData = new SessionData();
    sessionData.setUsername(mandatoryRequest.getUsername());

    this.hotelPromoConfigService
        .getAll(mandatoryRequest, hotelPromoConfigFindAllParams,
            (String) MDC.get(BaseMongoFields.PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }
}
