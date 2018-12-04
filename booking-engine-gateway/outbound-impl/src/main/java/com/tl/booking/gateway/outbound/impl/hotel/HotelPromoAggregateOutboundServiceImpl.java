package com.tl.booking.gateway.outbound.impl.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.libraries.utility.MandatoryRequestHelper;
import com.tl.booking.gateway.outbound.api.hotel.HotelPromoAggregateOutboundService;
import com.tl.booking.gateway.outbound.impl.configuration.HotelAggregateEndPointService;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.Hotel.FindAllPromoParam;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoAggregateRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoAggregateResponse;

import io.reactivex.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelPromoAggregateOutboundServiceImpl implements HotelPromoAggregateOutboundService {

  private final HotelAggregateEndPointService hotelAggregateEndPointService;

  @Autowired
  public HotelPromoAggregateOutboundServiceImpl(
      HotelAggregateEndPointService hotelAggregateEndPointService) {
    this.hotelAggregateEndPointService = hotelAggregateEndPointService;
  }

  @Override
  public Single<GatewayBaseResponse<RestResponsePage<HotelPromoAggregateResponse>>>
  findAllHotelPromoByHotelIdAndRoomIdAndDate(
      MandatoryRequest mandatoryRequest, FindAllPromoParam findAllPromoParam) {

    return Single.create(singleEmitter -> {
      try {
        GatewayBaseResponse<RestResponsePage<HotelPromoAggregateResponse>> success =
            hotelAggregateEndPointService.findAllHotelPromoByHotelIdAndRoomIdAndDate(
                MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                findAllPromoParam.getFindAllPromoParamAsMap()).execute().body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    });
  }

  @Override
  public Single<GatewayBaseResponse<HotelPromoAggregateResponse>> findHotelPromoById(
      MandatoryRequest mandatoryRequest, String id) {

    return Single.create(singleEmitter -> {
      try {
        GatewayBaseResponse<HotelPromoAggregateResponse> success =
            hotelAggregateEndPointService.findHotelPromoById(
                MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id).execute()
                .body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    });
  }

  @Override
  public Single<GatewayBaseResponse<HotelPromoAggregateResponse>> createHotelPromoAggregate(
      MandatoryRequest mandatoryRequest, HotelPromoAggregateRequest hotelPromoAggregateRequest) {

    return Single.create(singleEmitter -> {
      try {
        GatewayBaseResponse<HotelPromoAggregateResponse> success = hotelAggregateEndPointService
            .createHotelPromoAggregate(
                MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                hotelPromoAggregateRequest).execute().body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    });
  }

  @Override
  public Single<GatewayBaseResponse<HotelPromoAggregateResponse>> updateHotelPromoAggregate(
      MandatoryRequest mandatoryRequest, String id,
      HotelPromoAggregateRequest hotelPromoAggregateRequest) {

    return Single.create(singleEmitter -> {
      try {
        GatewayBaseResponse<HotelPromoAggregateResponse> success = hotelAggregateEndPointService
            .updateHotelPromoAggregate(
                MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id,
                hotelPromoAggregateRequest).execute().body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    });
  }

  @Override
  public Single<GatewayBaseResponse> deleteHotelPromoAggregate(
      MandatoryRequest mandatoryRequest, String id) {

    return Single.create(singleEmitter -> {
      try {
        GatewayBaseResponse success = hotelAggregateEndPointService.deleteHotelPromoAggregate(
            MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id).execute()
            .body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    });
  }
}
