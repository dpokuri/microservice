package com.tl.booking.gateway.outbound.api.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;

import io.reactivex.Single;
import java.util.List;
import java.util.Map;

public interface HotelDetailOutboundService {

  Single<GatewayBaseResponse<List<Map<String, Object>>>> getHotelRoomList(MandatoryRequest mandatoryRequest, String hotelId);

  Single<GatewayBaseResponse<List<Map<String, Object>>>> getHotelPolicy(MandatoryRequest mandatoryRequest);

}
