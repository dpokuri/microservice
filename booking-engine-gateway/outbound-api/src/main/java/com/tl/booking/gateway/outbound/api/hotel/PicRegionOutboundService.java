package com.tl.booking.gateway.outbound.api.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.Hotel.PicRegionRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.PicRegionResponse;

import io.reactivex.Single;

public interface PicRegionOutboundService {

  Single<GatewayBaseResponse<RestResponsePage<PicRegionResponse>>> findPicRegionList(MandatoryRequest mandatoryRequest, Integer page,
      Integer limit,
      String q,
      String sort,
      String method);

  Single<GatewayBaseResponse<PicRegionResponse>> findPicRegionById(MandatoryRequest mandatoryRequest, String id);

  Single<GatewayBaseResponse> createPicRegion(MandatoryRequest mandatoryRequest, PicRegionRequest picRegionRequest);

  Single<GatewayBaseResponse> updatePicRegion(MandatoryRequest mandatoryRequest, String id, PicRegionRequest picRegionRequest);

  Single<GatewayBaseResponse> deletePicRegionById(MandatoryRequest mandatoryRequest, String id);

}
