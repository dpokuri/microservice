package com.tl.booking.gateway.service.api.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.Hotel.RegionMappingRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.RegionMappingResponse;

import io.reactivex.Single;

public interface RegionMappingService {

  Single<GatewayBaseResponse<RestResponsePage<RegionMappingResponse>>> findRegionMappingsByStoreId(MandatoryRequest mandatoryRequest, Integer page,
      Integer limit,
      String q,
      String sort,
      String method,
      String privilegeToCheck, SessionData sessionData);

  Single<GatewayBaseResponse<RegionMappingResponse>> findRegionMappingByStoreIdAndId(MandatoryRequest mandatoryRequest, String id, String privilegeToCheck, SessionData sessionData);

  Single<GatewayBaseResponse> createRegionMapping(MandatoryRequest mandatoryRequest, RegionMappingRequest regionMappingRequest, String privilegeToCheck, SessionData sessionData);

  Single<GatewayBaseResponse> updateRegionMapping(MandatoryRequest mandatoryRequest, String id, RegionMappingRequest regionMappingRequest, String privilegeToCheck, SessionData sessionData);

  Single<GatewayBaseResponse> deleteRegionMapping(MandatoryRequest mandatoryRequest, String id, String privilegeToCheck, SessionData sessionData);

}
