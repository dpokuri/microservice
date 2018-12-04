package com.tl.booking.gateway.service.api.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoTypeRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoTypeResponse;

import io.reactivex.Single;
import java.util.List;

public interface HotelPromoTypeService {

  Single<GatewayBaseResponse<List<HotelPromoTypeResponse>>> findAllHotelPromoType(
      MandatoryRequest mandatoryRequest, String privilegeToCheck, SessionData sessionData);

  Single<GatewayBaseResponse<HotelPromoTypeResponse>> findHotelPromoTypeById(
      MandatoryRequest mandatoryRequest, String id, String privilegeToCheck, SessionData sessionData);

  Single<GatewayBaseResponse<HotelPromoTypeResponse>> createHotelPromoType(
      MandatoryRequest mandatoryRequest, HotelPromoTypeRequest hotelPromoTypeRequest,
      String privilegeToCheck, SessionData sessionData);

  Single<GatewayBaseResponse<HotelPromoTypeResponse>> updateHotelPromoType(
      MandatoryRequest mandatoryRequest, String id, HotelPromoTypeRequest hotelPromoTypeRequest,
      String privilegeToCheck, SessionData sessionData);

  Single<GatewayBaseResponse> deleteHotelPromoType(MandatoryRequest mandatoryRequest, String id,
      String privilegeToCheck, SessionData sessionData);
}
