package com.tl.booking.gateway.outbound.api.promocode;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.constant.enums.BusinessLogicResponseColumn;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoCode.BusinessLogicResponseRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.BusinessLogicResponseResponse;

import io.reactivex.Single;
import java.util.List;

public interface BusinessLogicResponseOutboundService {


  Single<GatewayBaseResponse<BusinessLogicResponseResponse>> findBusinessLogicResponseById(
      MandatoryRequest mandatoryRequest,
      String id);


  Single<GatewayBaseResponse<RestResponsePage<BusinessLogicResponseResponse>>> findBusinessLogicResponseFilterPaginated(
      MandatoryRequest mandatoryRequest,
      String responseCode,
      Integer page,
      Integer size,
      BusinessLogicResponseColumn columnSort,
      SortDirection sortDirection,
      String privilegeToCheck
  );


  Single<GatewayBaseResponse<BusinessLogicResponseResponse>> createBusinessLogicResponse(
      MandatoryRequest mandatoryRequest,
      BusinessLogicResponseRequest promoCodeRequest);

  Single<GatewayBaseResponse<BusinessLogicResponseResponse>> updateBusinessLogicResponse(
      MandatoryRequest mandatoryRequest,
      BusinessLogicResponseRequest promoCodeRequest, String id);

  Single<GatewayBaseResponse<Boolean>> deleteBusinessLogicResponse(MandatoryRequest mandatoryRequest,
      String id);

  Single<GatewayBaseResponse<List<BusinessLogicResponseResponse>>> findBusinessLogicResponses(
      MandatoryRequest mandatoryRequest);

}
