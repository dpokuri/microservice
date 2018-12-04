package com.tl.booking.gateway.service.api.flightrme;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.constant.enums.AdjustmentRuleColumn;
import com.tl.booking.gateway.entity.constant.enums.AdjustmentRuleStatus;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.flightrme.AdjustmentRuleRequest;
import com.tl.booking.gateway.entity.outbound.flightrme.AdjustmentRuleResponse;

import io.reactivex.Single;

public interface AdjustmentRuleService {
  Single<GatewayBaseResponse<AdjustmentRuleResponse>> createAdjustmentRule(
      MandatoryRequest mandatoryRequest,
      AdjustmentRuleRequest adjustmentRuleRequest,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<AdjustmentRuleResponse>> updateAdjustmentRule(
      MandatoryRequest mandatoryRequest,
      AdjustmentRuleRequest adjustmentRuleRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<AdjustmentRuleResponse>> updateStatusPendingAdjustmentRule(
      MandatoryRequest mandatoryRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<AdjustmentRuleResponse>> updateStatusActivatedAdjustmentRule(
      MandatoryRequest mandatoryRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<AdjustmentRuleResponse>> updateStatusUnActivatedAdjustmentRule(
      MandatoryRequest mandatoryRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<AdjustmentRuleResponse>> updateStatusRejectedAdjustmentRule(
      MandatoryRequest mandatoryRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<Boolean>> deleteAdjustmentRule(
      MandatoryRequest mandatoryRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<AdjustmentRuleResponse>> findAdjustmentRuleById(
      MandatoryRequest mandatoryRequest, String id, String privilegeToCheck,
      SessionData sessionData);

  Single<GatewayBaseResponse<RestResponsePage<AdjustmentRuleResponse>>> findAdjustmentRuleFilterPaginated(
    MandatoryRequest mandatoryRequest,
    String airline,
    String origin,
    String destination,
    AdjustmentRuleStatus adjustmentRuleStatus,
    Integer page,
    Integer size,
    AdjustmentRuleColumn columnSort,
    SortDirection sortDirection,
    String privilegeToCheck,
    SessionData sessionData
  );
}
