package com.tl.booking.gateway.service.api.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoConfigFindAllParams;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoConfigRequest;

import io.reactivex.Single;

public interface HotelPromoConfigService {

  Single<GatewayBaseResponse<Object>> create(
      MandatoryRequest mandatoryRequest, HotelPromoConfigRequest hotelPromoConfigRequest,
      String privilegeToCheck, SessionData sessionData);

  Single<GatewayBaseResponse<Object>> update(
      MandatoryRequest mandatoryRequest, String id, HotelPromoConfigRequest hotelPromoConfigRequest,
      String privilegeToCheck, SessionData sessionData);

  Single<GatewayBaseResponse<Object>> delete(
      MandatoryRequest mandatoryRequest, String id, String privilegeToCheck,
      SessionData sessionData);

  Single<GatewayBaseResponse<Object>> getOne(
      MandatoryRequest mandatoryRequest, Integer id, String privilegeToCheck,
      SessionData sessionData);

  Single<GatewayBaseResponse<Object>> getAll(
      MandatoryRequest mandatoryRequest,
      HotelPromoConfigFindAllParams hotelPromoConfigFindAllParams, String privilegeToCheck,
      SessionData sessionData);
}
