package com.tl.booking.gateway.outbound.api.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;

import io.reactivex.Single;
import java.util.List;
import java.util.Map;

@FunctionalInterface
public interface HotelAutoCompleteOutboundService {

  Single<GatewayBaseResponse<List<Map<String, String>>>> getAutoCompleteHotel(MandatoryRequest mandatoryRequest, String otaId, String q);

}
