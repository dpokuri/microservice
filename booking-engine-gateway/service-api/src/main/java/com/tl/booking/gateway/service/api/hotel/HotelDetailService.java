package com.tl.booking.gateway.service.api.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.SessionData;

import io.reactivex.Single;
import java.util.List;
import java.util.Map;

public interface HotelDetailService {

  Single<GatewayBaseResponse<List<Map<String, Object>>>> getHotelRoomList(
      MandatoryRequest mandatoryRequest, String hotelId, String privilegeToCheck, SessionData sessionData);

  Single<GatewayBaseResponse<List<Map<String, Object>>>> getHotelPolicy(
      MandatoryRequest mandatoryRequest, String privilegeToCheck, SessionData sessionData);

}
