package com.tl.booking.gateway.outbound.api.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.Hotel.OtaRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.OtaResponse;

import io.reactivex.Single;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface HotelOtaOutboundService {

  Single<GatewayBaseResponse<List<OtaResponse>>> findHotelOta(MandatoryRequest mandatoryRequest);
  Single<GatewayBaseResponse<OtaResponse>> findHotelOtaById(MandatoryRequest mandatoryRequest, String id);
  Single<GatewayBaseResponse<OtaResponse>> createHotelOta(MandatoryRequest mandatoryRequest, OtaRequest otaRequest);
  Single<GatewayBaseResponse<OtaResponse>> updateHotelOta(MandatoryRequest mandatoryRequest, String id, OtaRequest otaRequest);
  Single<GatewayBaseResponse> deleteHotelOta(MandatoryRequest mandatoryRequest, String id);

}
