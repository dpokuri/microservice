package com.tl.booking.gateway.rest.web.controller.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.common.rest.web.model.request.MandatoryRequestBuilder;
import com.tl.booking.gateway.service.api.hotel.HotelAutoCompleteService;
import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.SessionData;

import io.swagger.annotations.Api;
import java.util.List;
import java.util.Map;
import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequestMapping(ApiPath.AUTO_COMPLETE_HOTEL)
@Api(value = "Hotel - Autocomplete")
public class HotelAutoCompleteController {

  @Autowired
  private HotelAutoCompleteService hotelAutoCompleteService;

  private static final String MDC_PRIVILEGES = "privileges";

  @GetMapping(value="/search/hotel")
  public DeferredResult<GatewayBaseResponse<List<Map<String, String>>>> getAutoCompleteHotel(
      @RequestParam(value="otaId") String otaId,
      @RequestParam(value="q") String q){

    DeferredResult<GatewayBaseResponse<List<Map<String, String>>>> deferred = new DeferredResult<>();

    SessionData sessionData = new SessionData();
    sessionData.setUsername((String)MDC.get("username"));

    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String)MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String)MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String)MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String)MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String)MDC.get(BaseMongoFields.USERNAME))
        .build();

    this.hotelAutoCompleteService.getAutoCompleteHotel(mandatoryRequest, otaId, q, (String)MDC.get(MDC_PRIVILEGES), sessionData)
        .subscribe(
            deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

}
