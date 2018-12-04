package com.tl.booking.gateway.service.api.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.SessionData;

import io.reactivex.Single;
import java.util.List;
import java.util.Map;

@FunctionalInterface
public interface HotelAutoCompleteService {

  Single<GatewayBaseResponse<List<Map<String, String>>>> getAutoCompleteHotel(MandatoryRequest mandatoryRequest, String otaId, String q, String privilegeToCheck, SessionData sessionData);

}
