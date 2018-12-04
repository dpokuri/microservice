package com.tl.booking.gateway.service.api.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.Hotel.FindAllPromoParam;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoAggregateRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoAggregateResponse;

import io.reactivex.Single;

public interface HotelPromoAggregateService {

  Single<GatewayBaseResponse<RestResponsePage<HotelPromoAggregateResponse>>>
  findAllHotelPromoByHotelIdAndRoomIdAndDate(
      MandatoryRequest mandatoryRequest, FindAllPromoParam findAllPromoParam,
      String privilegeToCheck, SessionData sessionData);

  Single<GatewayBaseResponse<HotelPromoAggregateResponse>> findHotelPromoById(
      MandatoryRequest mandatoryRequest, String id, String privilegeToCheck, SessionData sessionData);

  Single<GatewayBaseResponse<HotelPromoAggregateResponse>> createHotelPromoAggregate(
      MandatoryRequest mandatoryRequest, HotelPromoAggregateRequest hotelPromoAggregateRequest,
      String privilegeToCheck, SessionData sessionData);

  Single<GatewayBaseResponse<HotelPromoAggregateResponse>> updateHotelPromoAggregate(
      MandatoryRequest mandatoryRequest, String id,
      HotelPromoAggregateRequest hotelPromoAggregateRequest,
      String privilegeToCheck, SessionData sessionData);

  Single<GatewayBaseResponse> deleteHotelPromoAggregate(
      MandatoryRequest mandatoryRequest, String id, String privilegeToCheck, SessionData sessionData);

}
