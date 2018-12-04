package com.tl.booking.gateway.service.api.promocode;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.PromoCode.VariableSourceResponse;

import io.reactivex.Single;
import java.util.List;

public interface VariableSourceService {

  Single<GatewayBaseResponse<List<VariableSourceResponse>>> findAllVariableSource(
      MandatoryRequest mandatoryRequest,
      String sourceType,
      String key,
      String privilegeToCheck,
      SessionData sessionData
  );



}
