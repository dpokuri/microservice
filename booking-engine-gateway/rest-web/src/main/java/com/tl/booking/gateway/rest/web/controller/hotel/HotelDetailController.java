package com.tl.booking.gateway.rest.web.controller.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.common.rest.web.model.request.MandatoryRequestBuilder;
import com.tl.booking.gateway.service.api.hotel.HotelDetailService;
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
@RequestMapping(ApiPath.HOTEL_DETAIL)
@Api(value = "Hotel - Hotel Detail")
public class HotelDetailController {

  @Autowired
  private HotelDetailService hotelDetailService;

  private static final String MDC_PRIVILEGES = "privileges";

  @GetMapping(path = ApiPath.HOTEL_ROOM_LIST)
  public DeferredResult<GatewayBaseResponse<List<Map<String, Object>>>> getHotelRoomList(
      @RequestParam(value = "hotelId", defaultValue = "") String hotelId) {

    DeferredResult<GatewayBaseResponse<List<Map<String, Object>>>> deferred = new DeferredResult<>();

    SessionData sessionData = new SessionData();
    sessionData.setUsername((String) MDC.get("username"));

    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String) MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String) MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String) MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String) MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String) MDC.get(BaseMongoFields.USERNAME))
        .build();

    this.hotelDetailService
        .getHotelRoomList(mandatoryRequest, hotelId, (String) MDC.get(MDC_PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @GetMapping(path = ApiPath.HOTEL_POLICY)
  public DeferredResult<GatewayBaseResponse<List<Map<String, Object>>>> getHotelPolicy() {

    DeferredResult<GatewayBaseResponse<List<Map<String, Object>>>> deferred = new DeferredResult<>();

    SessionData sessionData = new SessionData();
    sessionData.setUsername((String) MDC.get("username"));

    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String) MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String) MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String) MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String) MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String) MDC.get(BaseMongoFields.USERNAME))
        .build();

    this.hotelDetailService
        .getHotelPolicy(mandatoryRequest, (String) MDC.get(MDC_PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

}
