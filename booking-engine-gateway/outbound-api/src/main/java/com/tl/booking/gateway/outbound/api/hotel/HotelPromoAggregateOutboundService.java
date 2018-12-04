package com.tl.booking.gateway.outbound.api.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.Hotel.FindAllPromoParam;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoAggregateRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoAggregateResponse;

import io.reactivex.Single;

public interface HotelPromoAggregateOutboundService {

  Single<GatewayBaseResponse<RestResponsePage<HotelPromoAggregateResponse>>>
  findAllHotelPromoByHotelIdAndRoomIdAndDate(
      MandatoryRequest mandatoryRequest, FindAllPromoParam findAllPromoParam);

  Single<GatewayBaseResponse<HotelPromoAggregateResponse>> findHotelPromoById(
      MandatoryRequest mandatoryRequest, String id);

  Single<GatewayBaseResponse<HotelPromoAggregateResponse>> createHotelPromoAggregate(
      MandatoryRequest mandatoryRequest, HotelPromoAggregateRequest hotelPromoAggregateRequest);

  Single<GatewayBaseResponse<HotelPromoAggregateResponse>> updateHotelPromoAggregate(
      MandatoryRequest mandatoryRequest, String id,
      HotelPromoAggregateRequest hotelPromoAggregateRequest);

  Single<GatewayBaseResponse> deleteHotelPromoAggregate(MandatoryRequest mandatoryRequest, String id);

}
