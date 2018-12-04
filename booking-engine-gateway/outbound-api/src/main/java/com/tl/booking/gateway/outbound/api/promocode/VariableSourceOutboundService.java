package com.tl.booking.gateway.outbound.api.promocode;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.VariableSourceResponse;

import io.reactivex.Single;
import java.util.List;

public interface VariableSourceOutboundService {

  Single<GatewayBaseResponse<List<VariableSourceResponse>>> findAll(MandatoryRequest mandatoryRequest,
      String sourceType, String key);

}
