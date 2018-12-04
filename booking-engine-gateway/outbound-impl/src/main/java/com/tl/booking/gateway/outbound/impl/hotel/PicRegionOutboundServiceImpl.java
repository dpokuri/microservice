package com.tl.booking.gateway.outbound.impl.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.libraries.utility.MandatoryRequestHelper;
import com.tl.booking.gateway.outbound.api.hotel.PicRegionOutboundService;
import com.tl.booking.gateway.outbound.impl.configuration.HotelScrappingEndPointService;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.Hotel.PicRegionRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.PicRegionResponse;

import io.reactivex.Single;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Response;

@Service
public class PicRegionOutboundServiceImpl implements PicRegionOutboundService {

  @Autowired
  private HotelScrappingEndPointService hotelScrappingEndPointService;

  @Override
  public Single<GatewayBaseResponse<RestResponsePage<PicRegionResponse>>> findPicRegionList(
      MandatoryRequest mandatoryRequest, Integer page, Integer limit, String q, String sort,
      String method) {

    Map<String, String> queryParam = new HashMap<>();
    queryParam.put("page", page.toString());
    queryParam.put("limit", limit.toString());
    queryParam.put("q", q);
    queryParam.put("sort", sort);
    queryParam.put("method", method);

    return Single.create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<RestResponsePage<PicRegionResponse>>> picRegionResponse = this
            .hotelScrappingEndPointService
            .findPicRegionList
                (
                    MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                    queryParam)
            .execute();
        GatewayBaseResponse<RestResponsePage<PicRegionResponse>> success = picRegionResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e){
        singleEmitter.onError(e);
      }
    });
  }

  @Override
  public Single<GatewayBaseResponse<PicRegionResponse>> findPicRegionById(
      MandatoryRequest mandatoryRequest, String id) {
    return Single.create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<PicRegionResponse>> picRegionResponse = this.hotelScrappingEndPointService
            .findPicRegionById
                (MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id).execute();
        GatewayBaseResponse<PicRegionResponse> success = picRegionResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e){
        singleEmitter.onError(e);
      }
    });
  }

  @Override
  public Single<GatewayBaseResponse> createPicRegion(MandatoryRequest mandatoryRequest,
      PicRegionRequest picRegionRequest) {
    return Single.create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse> picRegionResponse = this.hotelScrappingEndPointService
            .createPicRegion
                (MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), picRegionRequest).execute();
        GatewayBaseResponse success = picRegionResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e){
        singleEmitter.onError(e);
      }
    });
  }

  @Override
  public Single<GatewayBaseResponse> updatePicRegion(MandatoryRequest mandatoryRequest, String id,
      PicRegionRequest picRegionRequest) {
    return Single.create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse> picRegionResponse = this.hotelScrappingEndPointService
            .updatePicRegion
                (MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id, picRegionRequest).execute();
        GatewayBaseResponse success = picRegionResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e){
        singleEmitter.onError(e);
      }
    });
  }

  @Override
  public Single<GatewayBaseResponse> deletePicRegionById(MandatoryRequest mandatoryRequest, String id) {
    return Single.create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse> picRegionResponse = this.hotelScrappingEndPointService
            .deletePicRegionById
                (MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id).execute();
        GatewayBaseResponse success = picRegionResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e){
        singleEmitter.onError(e);
      }
    });
  }
}
