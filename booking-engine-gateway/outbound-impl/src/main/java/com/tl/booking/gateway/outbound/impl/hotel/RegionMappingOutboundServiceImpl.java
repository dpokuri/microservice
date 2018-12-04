package com.tl.booking.gateway.outbound.impl.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.libraries.utility.MandatoryRequestHelper;
import com.tl.booking.gateway.outbound.api.hotel.RegionMappingOutboundService;
import com.tl.booking.gateway.outbound.impl.configuration.HotelScrappingEndPointService;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.Hotel.RegionMappingRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.RegionMappingResponse;

import io.reactivex.Single;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Response;

@Service
public class RegionMappingOutboundServiceImpl implements RegionMappingOutboundService {

  @Autowired
  private HotelScrappingEndPointService hotelScrappingEndPointService;

  @Override
  public Single<GatewayBaseResponse<RestResponsePage<RegionMappingResponse>>> findRegionMappingsByStoreId(
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
        Response<GatewayBaseResponse<RestResponsePage<RegionMappingResponse>>> regionMappingResponse = this
            .hotelScrappingEndPointService
            .findRegionMappingsByStoreId
                (
                    MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                    queryParam)
            .execute();
        GatewayBaseResponse<RestResponsePage<RegionMappingResponse>> success = regionMappingResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e){
        singleEmitter.onError(e);
      }
    });
  }

  @Override
  public Single<GatewayBaseResponse<RegionMappingResponse>> findRegionMappingByStoreIdAndId(
      MandatoryRequest mandatoryRequest, String id) {
    return Single.create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<RegionMappingResponse>> regionMappingResponse = this.hotelScrappingEndPointService
            .findRegionMappingByStoreIdAndId
                (MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id).execute();
        GatewayBaseResponse<RegionMappingResponse> success = regionMappingResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e){
        singleEmitter.onError(e);
      }
    });
  }

  @Override
  public Single<GatewayBaseResponse> createRegionMapping(MandatoryRequest mandatoryRequest,
      RegionMappingRequest regionMappingRequest) {
    return Single.create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse> regionMappingResponse = this.hotelScrappingEndPointService
            .createRegionMapping
                (MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), regionMappingRequest).execute();
        GatewayBaseResponse success = regionMappingResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e){
        singleEmitter.onError(e);
      }
    });
  }

  @Override
  public Single<GatewayBaseResponse> updateRegionMapping(MandatoryRequest mandatoryRequest, String id,
      RegionMappingRequest regionMappingRequest) {
    return Single.create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse> regionMappingResponse = this.hotelScrappingEndPointService
            .updateRegionMapping
                (MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id, regionMappingRequest).execute();
        GatewayBaseResponse success = regionMappingResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e){
        singleEmitter.onError(e);
      }
    });
  }

  @Override
  public Single<GatewayBaseResponse> deleteRegionMapping(MandatoryRequest mandatoryRequest, String id) {
    return Single.create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse> regionMappingResponse = this.hotelScrappingEndPointService
            .deleteRegionMapping
                (MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id).execute();
        GatewayBaseResponse success = regionMappingResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e){
        singleEmitter.onError(e);
      }
    });
  }
}
