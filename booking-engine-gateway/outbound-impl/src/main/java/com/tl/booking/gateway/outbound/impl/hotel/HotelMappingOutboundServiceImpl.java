package com.tl.booking.gateway.outbound.impl.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.libraries.utility.MandatoryRequestHelper;
import com.tl.booking.gateway.outbound.api.hotel.HotelMappingOutboundService;
import com.tl.booking.gateway.outbound.impl.configuration.HotelScrappingEndPointService;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelMappingRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelMappingResponse;

import io.reactivex.Single;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Response;

@Service
public class HotelMappingOutboundServiceImpl implements HotelMappingOutboundService {

  @Autowired
  private HotelScrappingEndPointService hotelScrappingEndPointService;

  @Override
  public Single<GatewayBaseResponse<RestResponsePage<HotelMappingResponse>>> findHotelMappingsByStoreId(
      MandatoryRequest mandatoryRequest, Integer page, Integer limit, String q, String regionId,
      String sort, String method) {

    Map<String, String> queryParam = new HashMap<>();
    queryParam.put("page", page.toString());
    queryParam.put("limit", limit.toString());
    queryParam.put("q", q);
    queryParam.put("regionId", regionId);
    queryParam.put("sort", sort);
    queryParam.put("method", method);

    return Single.create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<RestResponsePage<HotelMappingResponse>>> hotelMappingResponse = this
            .hotelScrappingEndPointService
            .findHotelMappingsByStoreId
                (
                    MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                    queryParam)
            .execute();
        GatewayBaseResponse<RestResponsePage<HotelMappingResponse>> success = hotelMappingResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e){
        singleEmitter.onError(e);
      }
    });
  }

  @Override
  public Single<GatewayBaseResponse<HotelMappingResponse>> findHotelMappingByStoreIdAndId(
      MandatoryRequest mandatoryRequest, String id) {
    return Single.create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<HotelMappingResponse>> hotelMappingResponse = this.hotelScrappingEndPointService
            .findHotelMappingByStoreIdAndId
                (MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id).execute();
        GatewayBaseResponse<HotelMappingResponse> success = hotelMappingResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e){
        singleEmitter.onError(e);
      }
    });
  }

  @Override
  public Single<GatewayBaseResponse> createHotelMapping(MandatoryRequest mandatoryRequest,
      HotelMappingRequest hotelMappingRequest) {
    return Single.create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse> hotelMappingResponse = this.hotelScrappingEndPointService
            .createHotelMapping
                (MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), hotelMappingRequest).execute();
        GatewayBaseResponse success = hotelMappingResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e){
        singleEmitter.onError(e);
      }
    });
  }

  @Override
  public Single<GatewayBaseResponse> updateHotelMapping(MandatoryRequest mandatoryRequest, String id,
      HotelMappingRequest hotelMappingRequest) {
    return Single.create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse> hotelMappingResponse = this.hotelScrappingEndPointService
            .updateHotelMapping
                (MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id, hotelMappingRequest).execute();
        GatewayBaseResponse success = hotelMappingResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e){
        singleEmitter.onError(e);
      }
    });
  }

  @Override
  public Single<GatewayBaseResponse> deleteHotelMapping(MandatoryRequest mandatoryRequest, String id) {
    return Single.create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse> hotelMappingResponse = this.hotelScrappingEndPointService
            .deleteHotelMapping
                (MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id).execute();
        GatewayBaseResponse success = hotelMappingResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e){
        singleEmitter.onError(e);
      }
    });
  }
}
