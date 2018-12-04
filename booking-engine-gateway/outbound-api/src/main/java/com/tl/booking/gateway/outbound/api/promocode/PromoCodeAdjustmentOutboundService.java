package com.tl.booking.gateway.outbound.api.promocode;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.constant.enums.PromoCodeAdjustmentColumn;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeAdjustmentDropdownResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeAdjustmentRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeAdjustmentResponse;

import io.reactivex.Single;
import java.util.List;

public interface PromoCodeAdjustmentOutboundService {


  Single<GatewayBaseResponse<PromoCodeAdjustmentResponse>> findPromoCodeAdjustmentById(
      MandatoryRequest mandatoryRequest,
      String id);


  Single<GatewayBaseResponse<RestResponsePage<PromoCodeAdjustmentResponse>>> findPromoCodeAdjustmentFilterPaginated(
      MandatoryRequest mandatoryRequest,
      String name,
      Boolean isPromoCodeCombine,
      Integer page,
      Integer size,
      PromoCodeAdjustmentColumn columnSort,
      SortDirection sortDirection,
      String privilegeToCheck
  );


  Single<GatewayBaseResponse<PromoCodeAdjustmentResponse>> createPromoCodeAdjustment(MandatoryRequest mandatoryRequest,
      PromoCodeAdjustmentRequest promoCodeRequest);

  Single<GatewayBaseResponse<PromoCodeAdjustmentResponse>> updatePromoCodeAdjustment(MandatoryRequest mandatoryRequest,
      PromoCodeAdjustmentRequest promoCodeRequest, String id);

  Single<GatewayBaseResponse<Boolean>> deletePromoCodeAdjustment(MandatoryRequest mandatoryRequest,
      String id);

  Single<GatewayBaseResponse<PromoCodeAdjustmentResponse>> updateStatusActivatePromoCodeAdjustment(
      MandatoryRequest mandatoryRequest,
      String id);

  Single<GatewayBaseResponse<PromoCodeAdjustmentResponse>> updateStatusUnActivatePromoCodeAdjustment(
      MandatoryRequest mandatoryRequest,
      String id);

  Single<GatewayBaseResponse<PromoCodeAdjustmentResponse>> updateStatusPendingPromoCodeAdjustment(
      MandatoryRequest mandatoryRequest,
      String id);

  Single<GatewayBaseResponse<PromoCodeAdjustmentResponse>> updateStatusRejectedPromoCodeAdjustment(
      MandatoryRequest mandatoryRequest,
      String id);

  Single<GatewayBaseResponse<List<PromoCodeAdjustmentDropdownResponse>>> findPromoCodeAdjustmentActivate(
      MandatoryRequest mandatoryRequest);

}
