package com.tl.booking.gateway.service.api.promocode;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.constant.enums.BusinessLogicResponseColumn;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.PromoCode.BusinessLogicResponseRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.BusinessLogicResponseResponse;

import io.reactivex.Single;
import java.util.List;

public interface BusinessLogicResponseService {

  Single<GatewayBaseResponse<BusinessLogicResponseResponse>> findBusinessLogicResponseById(
      MandatoryRequest mandatoryRequest, String id, String privilegeToCheck,
      SessionData sessionData);


  Single<GatewayBaseResponse<RestResponsePage<BusinessLogicResponseResponse>>> findBusinessLogicResponseFilterPaginated(
      MandatoryRequest mandatoryRequest,
      String param,
      Integer page,
      Integer size,
      BusinessLogicResponseColumn columnSort,
      SortDirection sortDirection,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<BusinessLogicResponseResponse>> createBusinessLogicResponse(
      MandatoryRequest mandatoryRequest,
      BusinessLogicResponseRequest variableRequest,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<BusinessLogicResponseResponse>> updateBusinessLogicResponse(
      MandatoryRequest mandatoryRequest,
      BusinessLogicResponseRequest variableRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<Boolean>> deleteBusinessLogicResponse(
      MandatoryRequest mandatoryRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<List<BusinessLogicResponseResponse>>> findBusinessLogicResponses(
      MandatoryRequest mandatoryRequest,
      String privilegeToCheck,
      SessionData sessionData
  );
}
