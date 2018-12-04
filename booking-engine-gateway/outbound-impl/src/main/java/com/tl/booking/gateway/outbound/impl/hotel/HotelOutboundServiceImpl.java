package com.tl.booking.gateway.outbound.impl.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.libraries.utility.MandatoryRequestHelper;
import com.tl.booking.gateway.outbound.api.hotel.HotelOutboundService;
import com.tl.booking.gateway.outbound.impl.configuration.HotelSubsidyEndPointService;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.Hotel.FindHotelByAddressParam;
import com.tl.booking.gateway.entity.outbound.Hotel.FindHotelByHotelIdParam;

import io.reactivex.Single;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelOutboundServiceImpl implements HotelOutboundService {

  private final HotelSubsidyEndPointService hotelSubsidyEndPointService;

  @Autowired
  public HotelOutboundServiceImpl(HotelSubsidyEndPointService hotelSubsidyEndPointService) {
    this.hotelSubsidyEndPointService = hotelSubsidyEndPointService;
  }

  @Override
  public Single<GatewayBaseResponse<List<Map<String, String>>>> findHotelByAddress(
      MandatoryRequest mandatoryRequest, FindHotelByAddressParam findHotelByAddressParam) {

    return Single.create(singleEmitter -> {
      try {
        GatewayBaseResponse<List<Map<String, String>>> success =
            hotelSubsidyEndPointService.findHotelByAddress(
                MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                findHotelByAddressParam.getFindHotelByAddressAsMap()).execute().body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    });
  }

  @Override
  public Single<GatewayBaseResponse<List<Map<String, String>>>> findHotelNameByHotelIds(
      MandatoryRequest mandatoryRequest, FindHotelByHotelIdParam findHotelByHotelIdParam) {

    return Single.create(singleEmitter -> {
      try {
        GatewayBaseResponse<List<Map<String, String>>> success =
            hotelSubsidyEndPointService.findHotelNameByHotelIds(
                MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                findHotelByHotelIdParam).execute().body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    });
  }
}
