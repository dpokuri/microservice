package com.tl.booking.gateway.service.api.promocode;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.constant.enums.PromoCodeAdjustmentColumn;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeAdjustmentDropdownResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeAdjustmentRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeAdjustmentResponse;

import io.reactivex.Single;
import java.util.List;

public interface PromoCodeAdjustmentService {

  Single<GatewayBaseResponse<PromoCodeAdjustmentResponse>> findPromoCodeAdjustmentById(
      MandatoryRequest mandatoryRequest, String id, String privilegeToCheck,
      SessionData sessionData);


  Single<GatewayBaseResponse<RestResponsePage<PromoCodeAdjustmentResponse>>> findPromoCodeAdjustmentFilterPaginated(
      MandatoryRequest mandatoryRequest,
      String name,
      Boolean isPromoCodeCombine,
      Integer page,
      Integer size,
      PromoCodeAdjustmentColumn columnSort,
      SortDirection sortDirection,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<PromoCodeAdjustmentResponse>> createPromoCodeAdjustment(
      MandatoryRequest mandatoryRequest,
      PromoCodeAdjustmentRequest promoCodeRequest,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<PromoCodeAdjustmentResponse>> updatePromoCodeAdjustment(
      MandatoryRequest mandatoryRequest,
      PromoCodeAdjustmentRequest promoCodeRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<Boolean>> deletePromoCodeAdjustment(
      MandatoryRequest mandatoryRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<PromoCodeAdjustmentResponse>> updateStatusActivatePromoCodeAdjustment(
      MandatoryRequest mandatoryRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<PromoCodeAdjustmentResponse>> updateStatusUnActivatePromoCodeAdjustment(
      MandatoryRequest mandatoryRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<PromoCodeAdjustmentResponse>> updateStatusPendingPromoCodeAdjustment(
      MandatoryRequest mandatoryRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<PromoCodeAdjustmentResponse>> updateStatusRejectedPromoCodeAdjustment(
      MandatoryRequest mandatoryRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<List<PromoCodeAdjustmentDropdownResponse>>> findPromoCodeAdjustmentActivate(
      MandatoryRequest mandatoryRequest,
      String privilegeToCheck,
      SessionData sessionData
  );
}
