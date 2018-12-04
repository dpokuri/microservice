package com.tl.booking.gateway.rest.web.controller.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.service.api.hotel.AddressService;
import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.constant.fields.MandatoryFields;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.Hotel.Address;

import io.swagger.annotations.Api;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@Api(value = "Hotel - Country")
public class AddressController {

  private final AddressService addressService;

  private MandatoryRequest mandatoryRequest;

  @Autowired
  public AddressController(AddressService addressService) {
    this.addressService = addressService;
  }

  @GetMapping(value = ApiPath.ADDRESS_COUNTRY)
  public DeferredResult<GatewayBaseResponse<List<Address>>> getCountry(HttpServletRequest request) {

    mandatoryRequest = (MandatoryRequest) request.getAttribute(MandatoryFields.MANDATORY_REQUEST);

    DeferredResult<GatewayBaseResponse<List<Address>>> deferred =
        new DeferredResult<>();

    SessionData sessionData = new SessionData();
    sessionData.setUsername(mandatoryRequest.getUsername());

    this.addressService
        .getCountry(mandatoryRequest, (String) MDC.get(BaseMongoFields.PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @GetMapping(value = ApiPath.ADDRESS_PROVINCE)
  public DeferredResult<GatewayBaseResponse<List<Address>>> getProvince(HttpServletRequest request,
      @RequestParam String countryId) {

    mandatoryRequest = (MandatoryRequest) request.getAttribute(MandatoryFields.MANDATORY_REQUEST);

    DeferredResult<GatewayBaseResponse<List<Address>>> deferred =
        new DeferredResult<>();

    SessionData sessionData = new SessionData();
    sessionData.setUsername(mandatoryRequest.getUsername());

    this.addressService
        .getProvince(mandatoryRequest, countryId, (String) MDC.get(BaseMongoFields.PRIVILEGES),
            sessionData).subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @GetMapping(value = ApiPath.ADDRESS_CITY)
  public DeferredResult<GatewayBaseResponse<List<Address>>> getCity(HttpServletRequest request,
      @RequestParam String provinceId) {

    mandatoryRequest = (MandatoryRequest) request.getAttribute(MandatoryFields.MANDATORY_REQUEST);

    DeferredResult<GatewayBaseResponse<List<Address>>> deferred =
        new DeferredResult<>();

    SessionData sessionData = new SessionData();
    sessionData.setUsername(mandatoryRequest.getUsername());

    this.addressService
        .getCity(mandatoryRequest, provinceId, (String) MDC.get(BaseMongoFields.PRIVILEGES),
            sessionData).subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @GetMapping(value = ApiPath.ADDRESS_AREA)
  public DeferredResult<GatewayBaseResponse<List<Address>>> getArea(HttpServletRequest request,
      @RequestParam String cityId) {

    mandatoryRequest = (MandatoryRequest) request.getAttribute(MandatoryFields.MANDATORY_REQUEST);

    DeferredResult<GatewayBaseResponse<List<Address>>> deferred =
        new DeferredResult<>();

    SessionData sessionData = new SessionData();
    sessionData.setUsername(mandatoryRequest.getUsername());

    this.addressService
        .getArea(mandatoryRequest, cityId, (String) MDC.get(BaseMongoFields.PRIVILEGES),
            sessionData).subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }
}
