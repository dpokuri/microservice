package com.tl.booking.gateway.outbound.api.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.Hotel.FindHotelByAddressParam;
import com.tl.booking.gateway.entity.outbound.Hotel.FindHotelByHotelIdParam;

import io.reactivex.Single;
import java.util.List;
import java.util.Map;

public interface HotelOutboundService {

  Single<GatewayBaseResponse<List<Map<String, String>>>> findHotelByAddress(
      MandatoryRequest mandatoryRequest, FindHotelByAddressParam findHotelByAddressParam);

  Single<GatewayBaseResponse<List<Map<String, String>>>> findHotelNameByHotelIds(
      MandatoryRequest mandatoryRequest, FindHotelByHotelIdParam findHotelByHotelIdParam);

}
