package com.tl.booking.gateway.service.api.promocode;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.enums.VariableColumn;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.PromoCode.VariableFindAllResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.VariableRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.VariableResponse;

import io.reactivex.Single;
import java.util.List;

public interface VariableService {

  Single<GatewayBaseResponse<VariableResponse>> findVariableById(
      MandatoryRequest mandatoryRequest, String id, String privilegeToCheck,
      SessionData sessionData);


  Single<GatewayBaseResponse<RestResponsePage<VariableResponse>>> findVariableFilterPaginated(
      MandatoryRequest mandatoryRequest,
      String param,
      String inputType,
      Integer page,
      Integer size,
      VariableColumn columnSort,
      SortDirection sortDirection,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<VariableResponse>> createVariable(
      MandatoryRequest mandatoryRequest,
      VariableRequest variableRequest,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<VariableResponse>> updateVariable(
      MandatoryRequest mandatoryRequest,
      VariableRequest variableRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<Boolean>> deleteVariable(
      MandatoryRequest mandatoryRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<List<VariableFindAllResponse>>> findAllVariable(
      MandatoryRequest mandatoryRequest,
      String privilegeToCheck,
      SessionData sessionData
  );

}
