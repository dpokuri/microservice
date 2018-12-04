package com.tl.booking.gateway.outbound.api.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoTypeRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoTypeResponse;

import io.reactivex.Single;
import java.util.List;

public interface HotelPromoTypeOutboundService {

  Single<GatewayBaseResponse<List<HotelPromoTypeResponse>>> findAllHotelPromoType(
      MandatoryRequest mandatoryRequest);

  Single<GatewayBaseResponse<HotelPromoTypeResponse>> findHotelPromoTypeById(
      MandatoryRequest mandatoryRequest, String id);

  Single<GatewayBaseResponse<HotelPromoTypeResponse>> createHotelPromoType(
      MandatoryRequest mandatoryRequest, HotelPromoTypeRequest hotel);

  Single<GatewayBaseResponse<HotelPromoTypeResponse>> updateHotelPromoType(
      MandatoryRequest mandatoryRequest, String id, HotelPromoTypeRequest hotelPromoTypeRequest);

  Single<GatewayBaseResponse> deleteHotelPromoType(MandatoryRequest mandatoryRequest, String id);
}
