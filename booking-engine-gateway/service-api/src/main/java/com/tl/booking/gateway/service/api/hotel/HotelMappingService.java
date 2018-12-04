package com.tl.booking.gateway.service.api.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelMappingRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelMappingResponse;

import io.reactivex.Single;

public interface HotelMappingService {

  Single<GatewayBaseResponse<RestResponsePage<HotelMappingResponse>>> findHotelMappingsByStoreId(MandatoryRequest mandatoryRequest, Integer page,
      Integer limit,
      String q,
      String regionId,
      String sort,
      String method,
      String privilegeToCheck, SessionData sessionData);

  Single<GatewayBaseResponse<HotelMappingResponse>> findHotelMappingByStoreIdAndId(MandatoryRequest mandatoryRequest, String id, String privilegeToCheck, SessionData sessionData);

  Single<GatewayBaseResponse> createHotelMapping(MandatoryRequest mandatoryRequest, HotelMappingRequest hotelMappingRequest, String privilegeToCheck, SessionData sessionData);

  Single<GatewayBaseResponse> updateHotelMapping(MandatoryRequest mandatoryRequest, String id, HotelMappingRequest hotelMappingRequest, String privilegeToCheck, SessionData sessionData);

  Single<GatewayBaseResponse> deleteHotelMapping(MandatoryRequest mandatoryRequest, String id, String privilegeToCheck, SessionData sessionData);

}
