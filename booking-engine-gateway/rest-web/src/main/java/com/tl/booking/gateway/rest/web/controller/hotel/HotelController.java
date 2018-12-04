package com.tl.booking.gateway.rest.web.controller.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.service.api.hotel.HotelService;
import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.constant.fields.MandatoryFields;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.Hotel.FindHotelByAddressParam;
import com.tl.booking.gateway.entity.outbound.Hotel.FindHotelByHotelIdParam;

import io.swagger.annotations.Api;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@Api(value = "Hotel")
public class HotelController {

  private final HotelService hotelService;

  private MandatoryRequest mandatoryRequest;

  @Autowired
  public HotelController(HotelService hotelService) {
    this.hotelService = hotelService;
  }

  @GetMapping(ApiPath.HOTEL_BY_ADDRESS)
  public DeferredResult<GatewayBaseResponse<List<Map<String, String>>>> findHotelByAddress(
      HttpServletRequest request,
      @Valid @ModelAttribute FindHotelByAddressParam findHotelByAddressParam) {

    mandatoryRequest = (MandatoryRequest) request.getAttribute(MandatoryFields.MANDATORY_REQUEST);

    DeferredResult<GatewayBaseResponse<List<Map<String, String>>>> deferred = new DeferredResult<>();

    SessionData sessionData = new SessionData();
    sessionData.setUsername(mandatoryRequest.getUsername());

    this.hotelService.findHotelByAddress(
        mandatoryRequest, findHotelByAddressParam, (String) MDC.get(BaseMongoFields.PRIVILEGES),
        sessionData).subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @PostMapping(ApiPath.HOTEL)
  public DeferredResult<GatewayBaseResponse<List<Map<String, String>>>> findHotelNameByHotelIds(
      HttpServletRequest request,
        @Valid @RequestBody FindHotelByHotelIdParam findHotelByHotelIdParam) {

    mandatoryRequest = (MandatoryRequest) request.getAttribute(MandatoryFields.MANDATORY_REQUEST);

    DeferredResult<GatewayBaseResponse<List<Map<String, String>>>> deferred = new DeferredResult<>();

    SessionData sessionData = new SessionData();
    sessionData.setUsername(mandatoryRequest.getUsername());

    this.hotelService.findHotelNameByHotelIds(
        mandatoryRequest, findHotelByHotelIdParam, (String) MDC.get(BaseMongoFields.PRIVILEGES),
        sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }
}
