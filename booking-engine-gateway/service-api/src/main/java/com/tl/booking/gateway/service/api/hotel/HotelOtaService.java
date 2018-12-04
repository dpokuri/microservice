package com.tl.booking.gateway.service.api.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.Hotel.OtaRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.OtaResponse;

import io.reactivex.Single;
import java.util.List;

public interface HotelOtaService {

  Single<GatewayBaseResponse<List<OtaResponse>>> findHotelOta(MandatoryRequest mandatoryRequest, String privilegeToCheck, SessionData sessionData);
  Single<GatewayBaseResponse<OtaResponse>> findHotelOtaById(MandatoryRequest mandatoryRequest, String id, String privilegeToCheck, SessionData sessionData);
  Single<GatewayBaseResponse<OtaResponse>> createHotelOta(MandatoryRequest mandatoryRequest, OtaRequest otaRequest, String privilegeToCheck, SessionData sessionData);
  Single<GatewayBaseResponse<OtaResponse>> updateHotelOta(MandatoryRequest mandatoryRequest, String id, OtaRequest otaRequest, String privilegeToCheck, SessionData sessionData);
  Single<GatewayBaseResponse> deleteHotelOta(MandatoryRequest mandatoryRequest, String id, String privilegeToCheck, SessionData sessionData);

}
