package com.tl.booking.gateway.outbound.api.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoConfigFindAllParams;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoConfigRequest;

import io.reactivex.Single;

public interface HotelPromoConfigOutboundService {

  Single<GatewayBaseResponse<Object>> create(MandatoryRequest mandatoryRequest,
      HotelPromoConfigRequest hotelPromoConfigRequest);

  Single<GatewayBaseResponse<Object>> update(MandatoryRequest mandatoryRequest, String id,
      HotelPromoConfigRequest hotelPromoConfigRequest);

  Single<GatewayBaseResponse<Object>> delete(MandatoryRequest mandatoryRequest, String id);

  Single<GatewayBaseResponse<Object>> getOne(MandatoryRequest mandatoryRequest, Integer id);

  Single<GatewayBaseResponse<Object>> getAll(MandatoryRequest mandatoryRequest,
      HotelPromoConfigFindAllParams hotelPromoConfigFindAllParams);
}
