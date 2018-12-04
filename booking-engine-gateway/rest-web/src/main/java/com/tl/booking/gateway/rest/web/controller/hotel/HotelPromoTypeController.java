package com.tl.booking.gateway.rest.web.controller.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.service.api.hotel.HotelPromoTypeService;
import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.constant.fields.MandatoryFields;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoTypeRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoTypeResponse;

import io.swagger.annotations.Api;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequestMapping(ApiPath.HOTEL_PROMO_TYPE)
@Api(value = "Hotel - Promo Type")
public class HotelPromoTypeController {

  private final HotelPromoTypeService hotelPromoTypeService;

  private MandatoryRequest mandatoryRequest;

  @Autowired
  public HotelPromoTypeController(HotelPromoTypeService hotelPromoTypeService) {
    this.hotelPromoTypeService = hotelPromoTypeService;
  }

  @GetMapping
  public DeferredResult<GatewayBaseResponse<List<HotelPromoTypeResponse>>> findAllHotelPromoType(
      HttpServletRequest request) {

    mandatoryRequest = (MandatoryRequest) request.getAttribute(MandatoryFields.MANDATORY_REQUEST);

    DeferredResult<GatewayBaseResponse<List<HotelPromoTypeResponse>>> deferred = new DeferredResult<>();

    SessionData sessionData = new SessionData();
    sessionData.setUsername(mandatoryRequest.getUsername());

    this.hotelPromoTypeService.findAllHotelPromoType(
        mandatoryRequest, (String) MDC.get(BaseMongoFields.PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @GetMapping(value = ApiPath.ID)
  public DeferredResult<GatewayBaseResponse<HotelPromoTypeResponse>> findHotelPromoTypeById(
      HttpServletRequest request, @PathVariable("id") String id) {

    mandatoryRequest = (MandatoryRequest) request.getAttribute(MandatoryFields.MANDATORY_REQUEST);

    DeferredResult<GatewayBaseResponse<HotelPromoTypeResponse>> deferred = new DeferredResult<>();

    SessionData sessionData = new SessionData();
    sessionData.setUsername(mandatoryRequest.getUsername());

    this.hotelPromoTypeService
        .findHotelPromoTypeById(mandatoryRequest, id, (String) MDC.get(BaseMongoFields.PRIVILEGES),
            sessionData).subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @PostMapping
  public DeferredResult<GatewayBaseResponse<HotelPromoTypeResponse>> createHotelPromoType(
      HttpServletRequest request, @RequestBody HotelPromoTypeRequest hotelPromoTypeRequest) {

    mandatoryRequest = (MandatoryRequest) request.getAttribute(MandatoryFields.MANDATORY_REQUEST);

    DeferredResult<GatewayBaseResponse<HotelPromoTypeResponse>> deferred = new DeferredResult<>();

    SessionData sessionData = new SessionData();
    sessionData.setUsername(mandatoryRequest.getUsername());

    this.hotelPromoTypeService.createHotelPromoType(mandatoryRequest, hotelPromoTypeRequest,
        (String) MDC.get(BaseMongoFields.PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @PutMapping(value = ApiPath.ID)
  public DeferredResult<GatewayBaseResponse<HotelPromoTypeResponse>> updateHotelPromoType(
      HttpServletRequest request, @PathVariable("id") String id,
      @RequestBody HotelPromoTypeRequest hotelPromoTypeRequest) {

    mandatoryRequest = (MandatoryRequest) request.getAttribute(MandatoryFields.MANDATORY_REQUEST);

    DeferredResult<GatewayBaseResponse<HotelPromoTypeResponse>> deferred = new DeferredResult<>();

    SessionData sessionData = new SessionData();
    sessionData.setUsername(mandatoryRequest.getUsername());

    this.hotelPromoTypeService.updateHotelPromoType(mandatoryRequest, id, hotelPromoTypeRequest,
        (String) MDC.get(BaseMongoFields.PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @DeleteMapping(value = ApiPath.ID)
  public DeferredResult<GatewayBaseResponse<HotelPromoTypeResponse>> deleteHotelPromoType(
      HttpServletRequest request, @PathVariable("id") String id) {

    mandatoryRequest = (MandatoryRequest) request.getAttribute(MandatoryFields.MANDATORY_REQUEST);

    DeferredResult<GatewayBaseResponse<HotelPromoTypeResponse>> deferred = new DeferredResult<>();

    SessionData sessionData = new SessionData();
    sessionData.setUsername(mandatoryRequest.getUsername());

    this.hotelPromoTypeService
        .deleteHotelPromoType(mandatoryRequest, id, (String) MDC.get(BaseMongoFields.PRIVILEGES),
            sessionData).subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }
}
