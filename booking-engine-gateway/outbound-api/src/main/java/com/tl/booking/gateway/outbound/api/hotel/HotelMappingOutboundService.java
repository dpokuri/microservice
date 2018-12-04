package com.tl.booking.gateway.outbound.api.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelMappingRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelMappingResponse;

import io.reactivex.Single;

public interface HotelMappingOutboundService {

  Single<GatewayBaseResponse<RestResponsePage<HotelMappingResponse>>> findHotelMappingsByStoreId(MandatoryRequest mandatoryRequest, Integer page,
      Integer limit,
      String q,
      String regionId,
      String sort,
      String method);

  Single<GatewayBaseResponse<HotelMappingResponse>> findHotelMappingByStoreIdAndId(MandatoryRequest mandatoryRequest, String id);

  Single<GatewayBaseResponse> createHotelMapping(MandatoryRequest mandatoryRequest, HotelMappingRequest hotelMappingRequest);

  Single<GatewayBaseResponse> updateHotelMapping(MandatoryRequest mandatoryRequest, String id, HotelMappingRequest hotelMappingRequest);

  Single<GatewayBaseResponse> deleteHotelMapping(MandatoryRequest mandatoryRequest, String id);

}
