package com.tl.booking.gateway.outbound.impl.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.libraries.utility.MandatoryRequestHelper;
import com.tl.booking.gateway.outbound.api.hotel.HotelDetailOutboundService;
import com.tl.booking.gateway.outbound.impl.configuration.HotelAggregateEndPointService;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;

import io.reactivex.Single;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelDetailOutboundServiceImpl implements HotelDetailOutboundService {

  private final HotelAggregateEndPointService hotelAggregateEndPointService;

  @Autowired
  public HotelDetailOutboundServiceImpl(
      HotelAggregateEndPointService hotelAggregateEndPointService) {
    this.hotelAggregateEndPointService = hotelAggregateEndPointService;
  }

  @Override
  public Single<GatewayBaseResponse<List<Map<String, Object>>>> getHotelRoomList(
      MandatoryRequest mandatoryRequest, String hotelId) {

    Map<String, String> queryParam = new HashMap<>();
    queryParam.put("hotelId", hotelId);
    return Single.create(singleEmitter -> {
      try {
        GatewayBaseResponse<List<Map<String, Object>>> success =
            hotelAggregateEndPointService.getHotelRoomList(
                MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), queryParam).execute()
                .body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    });
  }

  @Override
  public Single<GatewayBaseResponse<List<Map<String, Object>>>> getHotelPolicy(
      MandatoryRequest mandatoryRequest) {
    return Single.create(singleEmitter -> {
      try {
        GatewayBaseResponse<List<Map<String, Object>>> success =
            hotelAggregateEndPointService.getHotelPolicy(
                MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest)).execute()
                .body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    });
  }
}
