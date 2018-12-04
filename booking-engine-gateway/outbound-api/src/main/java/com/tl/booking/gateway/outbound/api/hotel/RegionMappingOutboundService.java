package com.tl.booking.gateway.outbound.api.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.Hotel.RegionMappingRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.RegionMappingResponse;

import io.reactivex.Single;

public interface RegionMappingOutboundService {

  Single<GatewayBaseResponse<RestResponsePage<RegionMappingResponse>>> findRegionMappingsByStoreId(MandatoryRequest mandatoryRequest, Integer page,
      Integer limit,
      String q,
      String sort,
      String method);

  Single<GatewayBaseResponse<RegionMappingResponse>> findRegionMappingByStoreIdAndId(MandatoryRequest mandatoryRequest, String id);

  Single<GatewayBaseResponse> createRegionMapping(MandatoryRequest mandatoryRequest, RegionMappingRequest regionMappingRequest);

  Single<GatewayBaseResponse> updateRegionMapping(MandatoryRequest mandatoryRequest, String id, RegionMappingRequest regionMappingRequest);

  Single<GatewayBaseResponse> deleteRegionMapping(MandatoryRequest mandatoryRequest, String id);

}
