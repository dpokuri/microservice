package com.tl.booking.gateway.service.api.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.Hotel.PicRegionRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.PicRegionResponse;

import io.reactivex.Single;

public interface PicRegionService {

  Single<GatewayBaseResponse<RestResponsePage<PicRegionResponse>>> findPicRegionList(MandatoryRequest mandatoryRequest, Integer page,
      Integer limit,
      String q,
      String sort,
      String method,
      String privilegeToCheck, SessionData sessionData);

  Single<GatewayBaseResponse<PicRegionResponse>> findPicRegionById(MandatoryRequest mandatoryRequest, String id, String privilegeToCheck, SessionData sessionData);

  Single<GatewayBaseResponse> createPicRegion(MandatoryRequest mandatoryRequest, PicRegionRequest picRegionRequest, String privilegeToCheck, SessionData sessionData);

  Single<GatewayBaseResponse> updatePicRegion(MandatoryRequest mandatoryRequest, String id, PicRegionRequest picRegionRequest, String privilegeToCheck, SessionData sessionData);

  Single<GatewayBaseResponse> deletePicRegionById(MandatoryRequest mandatoryRequest, String id, String privilegeToCheck, SessionData sessionData);

}
