package com.tl.booking.gateway.service.api.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.Hotel.FindHotelByAddressParam;
import com.tl.booking.gateway.entity.outbound.Hotel.FindHotelByHotelIdParam;

import io.reactivex.Single;
import java.util.List;
import java.util.Map;

public interface HotelService {

  Single<GatewayBaseResponse<List<Map<String, String>>>> findHotelByAddress(
      MandatoryRequest mandatoryRequest, FindHotelByAddressParam findHotelByAddressParam,
      String privilegeToCheck, SessionData sessionData);

  Single<GatewayBaseResponse<List<Map<String, String>>>> findHotelNameByHotelIds(
      MandatoryRequest mandatoryRequest, FindHotelByHotelIdParam findHotelByHotelIdParam,
      String privilegeToCheck, SessionData sessionData);
}
