package com.tl.booking.gateway.outbound.impl.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.libraries.utility.MandatoryRequestHelper;
import com.tl.booking.gateway.outbound.api.hotel.AdditionalDiscountOutboundService;
import com.tl.booking.gateway.outbound.impl.configuration.HotelAggregateEndPointService;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.Hotel.AdditionalDiscountRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.AdditionalDiscountResponse;

import io.reactivex.Single;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Response;

@Service
public class AdditionalDiscountOutboundServiceImpl implements AdditionalDiscountOutboundService {

  @Autowired
  private HotelAggregateEndPointService hotelAggregateEndPointService;

  @Override
  public Single<GatewayBaseResponse> createAdditionalDiscount(MandatoryRequest mandatoryRequest,
      AdditionalDiscountRequest additionalDiscountRequest) {

    return Single.create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<AdditionalDiscountResponse>> additionalDiscountResponse =
            this.hotelAggregateEndPointService.createAdditionalDiscount(MandatoryRequestHelper
                .buildMandatoryRequestHeader(mandatoryRequest), additionalDiscountRequest
            ).execute();

        GatewayBaseResponse success = additionalDiscountResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    });
  }

  @Override
  public Single<GatewayBaseResponse> updateAdditionalDiscount(MandatoryRequest mandatoryRequest,
      AdditionalDiscountRequest additionalDiscountRequest, String id) {

    return Single.create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<AdditionalDiscountResponse>> additionalDiscountResponse =
            this.hotelAggregateEndPointService.updateAdditionalDiscount(
                MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                additionalDiscountRequest, id).execute();

        GatewayBaseResponse success = additionalDiscountResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    });
  }

  @Override
  public Single<GatewayBaseResponse> deleteAdditionalDiscount(MandatoryRequest mandatoryRequest,
      String id) {

    return Single.create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<AdditionalDiscountResponse>> additionalDiscountResponse =
            this.hotelAggregateEndPointService.deleteAdditionalDiscount(
                MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                id
            ).execute();

        GatewayBaseResponse success = additionalDiscountResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    });
  }

  @Override
  public Single<GatewayBaseResponse> findAdditionalDiscountByHotel(MandatoryRequest mandatoryRequest,
      Integer hotelId, String type) {

    return Single.create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<AdditionalDiscountResponse>> additionalDiscountResponse =
            this.hotelAggregateEndPointService.findAdditionalDiscountByHotel(
                MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),

                hotelId, type
            ).execute();

        GatewayBaseResponse success = additionalDiscountResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    });
  }

  @Override
  public Single<GatewayBaseResponse> findAdditionalDiscountById(MandatoryRequest mandatoryRequest,
      String id) {

    return Single.create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<AdditionalDiscountResponse>> additionalDiscountResponse =
            this.hotelAggregateEndPointService.findAdditionalDiscountById(
                MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                id
            ).execute();

        GatewayBaseResponse success = additionalDiscountResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    });
  }

  @Override
  public Single<GatewayBaseResponse<RestResponsePage<AdditionalDiscountResponse>>> findAdditionalDiscount(
      MandatoryRequest mandatoryRequest, Integer page, Integer limit, String type, String hotelId) {

    Map<String, String> queryParam = new HashMap<>();
    queryParam.put("page", page.toString());
    queryParam.put("limit", limit.toString());
    queryParam.put("type", type);
    queryParam.put("hotelId", hotelId);

    Map<String, String> queryParam1 = new HashMap<>();

    for (Map.Entry<String, String> entry : queryParam.entrySet()) {
      if (!entry.getValue().isEmpty()) {
        queryParam1.put(entry.getKey(), entry.getValue());
      }
    }

    return Single.create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<RestResponsePage<AdditionalDiscountResponse>>> additionalDiscountResponse =
            this.hotelAggregateEndPointService.findAdditionalDiscount(
                MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                queryParam1).execute();

        GatewayBaseResponse success = additionalDiscountResponse.body();

        singleEmitter.onSuccess(success);

      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    });
  }
}
