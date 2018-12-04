package com.tl.booking.gateway.outbound.impl.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.libraries.utility.MandatoryRequestHelper;
import com.tl.booking.gateway.outbound.api.hotel.HotelPromoTypeOutboundService;
import com.tl.booking.gateway.outbound.impl.configuration.HotelAggregateEndPointService;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoTypeRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoTypeResponse;

import io.reactivex.Single;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelPromoTypeOutboundServiceImpl implements HotelPromoTypeOutboundService {

  private final HotelAggregateEndPointService hotelAggregateEndPointService;

  @Autowired
  public HotelPromoTypeOutboundServiceImpl(
      HotelAggregateEndPointService hotelAggregateEndPointService) {
    this.hotelAggregateEndPointService = hotelAggregateEndPointService;
  }

  @Override
  public Single<GatewayBaseResponse<List<HotelPromoTypeResponse>>> findAllHotelPromoType(
      MandatoryRequest mandatoryRequest) {

    return Single.create(singleEmitter -> {
      try {
        GatewayBaseResponse<List<HotelPromoTypeResponse>> success = hotelAggregateEndPointService
            .findAllHotelPromoType(MandatoryRequestHelper.
                buildMandatoryRequestHeader(mandatoryRequest)).execute().body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        e.printStackTrace();
        singleEmitter.onError(e);
      }
    });
  }

  @Override
  public Single<GatewayBaseResponse<HotelPromoTypeResponse>> findHotelPromoTypeById(
      MandatoryRequest mandatoryRequest, String id) {

    return Single.create(singleEmitter -> {
      try {
        GatewayBaseResponse<HotelPromoTypeResponse> success = hotelAggregateEndPointService
            .findHotelPromoTypeById(MandatoryRequestHelper.
                buildMandatoryRequestHeader(mandatoryRequest), id).execute().body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    });
  }

  @Override
  public Single<GatewayBaseResponse<HotelPromoTypeResponse>> createHotelPromoType(
      MandatoryRequest mandatoryRequest, HotelPromoTypeRequest hotelPromoTypeRequest) {

    return Single.create(singleEmitter -> {
      try {
        GatewayBaseResponse<HotelPromoTypeResponse> success = hotelAggregateEndPointService
            .createHotelPromoType(MandatoryRequestHelper.
                buildMandatoryRequestHeader(mandatoryRequest), hotelPromoTypeRequest).execute()
            .body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    });
  }

  @Override
  public Single<GatewayBaseResponse<HotelPromoTypeResponse>> updateHotelPromoType(
      MandatoryRequest mandatoryRequest, String id, HotelPromoTypeRequest hotelPromoTypeRequest) {

    return Single.create(singleEmitter -> {
      try {
        GatewayBaseResponse<HotelPromoTypeResponse> success = hotelAggregateEndPointService
            .updateHotelPromoType(MandatoryRequestHelper.
                buildMandatoryRequestHeader(mandatoryRequest), id, hotelPromoTypeRequest).execute()
            .body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    });
  }

  @Override
  public Single<GatewayBaseResponse> deleteHotelPromoType(MandatoryRequest mandatoryRequest, String id) {

    return Single.create(singleEmitter -> {
      try {
        GatewayBaseResponse success = hotelAggregateEndPointService
            .deleteHotelPromoType(
                MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id).execute()
            .body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    });
  }
}
