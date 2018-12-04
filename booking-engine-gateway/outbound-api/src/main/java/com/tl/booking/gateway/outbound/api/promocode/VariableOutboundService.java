package com.tl.booking.gateway.outbound.api.promocode;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.enums.VariableColumn;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoCode.VariableFindAllResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.VariableRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.VariableResponse;

import io.reactivex.Single;
import java.util.List;

public interface VariableOutboundService {


  Single<GatewayBaseResponse<VariableResponse>> findVariableById(
      MandatoryRequest mandatoryRequest,
      String id);


  Single<GatewayBaseResponse<RestResponsePage<VariableResponse>>> findVariableFilterPaginated(
      MandatoryRequest mandatoryRequest,
      String param,
      String inpytType,
      Integer page,
      Integer size,
      VariableColumn columnSort,
      SortDirection sortDirection,
      String privilegeToCheck
  );


  Single<GatewayBaseResponse<VariableResponse>> createVariable(
      MandatoryRequest mandatoryRequest,
      VariableRequest promoCodeRequest);

  Single<GatewayBaseResponse<VariableResponse>> updateVariable(
      MandatoryRequest mandatoryRequest,
      VariableRequest promoCodeRequest, String id);

  Single<GatewayBaseResponse<Boolean>> deleteVariable(MandatoryRequest mandatoryRequest,
      String id);

  Single<GatewayBaseResponse<List<VariableFindAllResponse>>> findAllVariable(
      MandatoryRequest mandatoryRequest);

}
