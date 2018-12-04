package com.tl.booking.gateway.outbound.impl.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.libraries.utility.MandatoryRequestHelper;
import com.tl.booking.gateway.outbound.api.hotel.HotelOtaOutboundService;
import com.tl.booking.gateway.outbound.impl.configuration.HotelScrappingEndPointService;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.Hotel.OtaRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.OtaResponse;

import io.reactivex.Single;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Response;

@Service
public class HotelOtaOutboundServiceImpl implements HotelOtaOutboundService {

  @Autowired
  private HotelScrappingEndPointService hotelScrappingEndPointService;

  @Override
  public Single<GatewayBaseResponse<List<OtaResponse>>> findHotelOta(MandatoryRequest mandatoryRequest) {
    return Single.create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<List<OtaResponse>>> otaResponse = this.hotelScrappingEndPointService
            .findHotelOta
                (MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest)).execute();
        GatewayBaseResponse<List<OtaResponse>> success = otaResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e){
        singleEmitter.onError(e);
      }
    });
  }

  @Override
  public Single<GatewayBaseResponse<OtaResponse>> findHotelOtaById(MandatoryRequest mandatoryRequest,
      String id) {
    return Single.create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<OtaResponse>> otaResponse = this.hotelScrappingEndPointService
            .findHotelOtaById
                (MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id).execute();
        GatewayBaseResponse<OtaResponse> success = otaResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e){
        singleEmitter.onError(e);
      }
    });
  }

  @Override
  public Single<GatewayBaseResponse<OtaResponse>> createHotelOta(MandatoryRequest mandatoryRequest,
      OtaRequest otaRequest) {
    return Single.create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<OtaResponse>> otaResponse = this.hotelScrappingEndPointService
            .createHotelOta(MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), otaRequest).execute();
        GatewayBaseResponse<OtaResponse> success = otaResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e){
        singleEmitter.onError(e);
      }
    });
  }

  @Override
  public Single<GatewayBaseResponse<OtaResponse>> updateHotelOta(MandatoryRequest mandatoryRequest,
      String id, OtaRequest otaRequest) {
    return Single.create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<OtaResponse>> otaResponse = this.hotelScrappingEndPointService
            .updateHotelOta(MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id, otaRequest).execute();
        GatewayBaseResponse<OtaResponse> success = otaResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e){
        singleEmitter.onError(e);
      }
    });
  }

  @Override
  public Single<GatewayBaseResponse> deleteHotelOta(MandatoryRequest mandatoryRequest, String id) {
    return Single.create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse> otaResponse = this.hotelScrappingEndPointService
            .deleteHotelOta(MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id).execute();
        GatewayBaseResponse success = otaResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e){
        singleEmitter.onError(e);
      }
    });
  }
}
