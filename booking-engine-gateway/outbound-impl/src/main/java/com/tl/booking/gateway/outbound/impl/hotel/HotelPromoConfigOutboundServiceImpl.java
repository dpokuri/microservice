package com.tl.booking.gateway.outbound.impl.hotel;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.libraries.utility.MandatoryRequestHelper;
import com.tl.booking.gateway.outbound.api.hotel.HotelPromoConfigOutboundService;
import com.tl.booking.gateway.outbound.impl.configuration.HotelAggregateEndPointService;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoConfigFindAllParams;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoConfigRequest;

import io.reactivex.Single;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Response;

@Service
public class HotelPromoConfigOutboundServiceImpl implements HotelPromoConfigOutboundService {

  private final HotelAggregateEndPointService hotelAggregateEndPointService;

  @Autowired
  public HotelPromoConfigOutboundServiceImpl(
      HotelAggregateEndPointService hotelAggregateEndPointService) {
    this.hotelAggregateEndPointService = hotelAggregateEndPointService;
  }

  @Override
  public Single<GatewayBaseResponse<Object>> create(MandatoryRequest mandatoryRequest,
      HotelPromoConfigRequest hotelPromoConfigRequest) {

    return Single.create(singleEmitter -> {
      try {
        GatewayBaseResponse<Object> success = hotelAggregateEndPointService
            .createHotelPromoConfig(
                MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                hotelPromoConfigRequest).execute().body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        e.printStackTrace();
        singleEmitter.onError(e);
      }
    });
  }

  @Override
  public Single<GatewayBaseResponse<Object>> update(MandatoryRequest mandatoryRequest, String id,
      HotelPromoConfigRequest hotelPromoConfigRequest) {

    return Single.create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<Object>> response = hotelAggregateEndPointService
            .updateHotelPromoConfig(
                MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id,
                hotelPromoConfigRequest).execute();

        GatewayBaseResponse<Object> success = response.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        e.printStackTrace();
        singleEmitter.onError(e);
      }
    });
  }

  @Override
  public Single<GatewayBaseResponse<Object>> delete(MandatoryRequest mandatoryRequest, String id) {

    return Single.create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<Object>> response = hotelAggregateEndPointService
            .deleteHotelPromoConfig(
                MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id).execute();

        GatewayBaseResponse<Object> success = response.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        e.printStackTrace();
        singleEmitter.onError(e);
      }
    });
  }

  @Override
  public Single<GatewayBaseResponse<Object>> getOne(MandatoryRequest mandatoryRequest, Integer id) {

    return Single.create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<Object>> response = hotelAggregateEndPointService
            .getOneHotelPromoConfig(
                MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id).execute();

        GatewayBaseResponse<Object> success = response.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        e.printStackTrace();
        singleEmitter.onError(e);
      }
    });
  }

  @SuppressWarnings("unchecked")
  @Override
  public Single<GatewayBaseResponse<Object>> getAll(MandatoryRequest mandatoryRequest,
      HotelPromoConfigFindAllParams hotelPromoConfigFindAllParams) {

    return Single.create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<Object>> response = hotelAggregateEndPointService
            .getAllHotelPromoConfig(
                MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                new ObjectMapper().setSerializationInclusion(Include.NON_NULL)
                    .convertValue(hotelPromoConfigFindAllParams, Map.class)).execute();

        GatewayBaseResponse<Object> success = response.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        e.printStackTrace();
        singleEmitter.onError(e);
      }
    });
  }
}
