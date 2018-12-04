package com.tl.booking.gateway.outbound.api;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.constant.enums.PromoPageColumn;
import com.tl.booking.gateway.entity.constant.enums.PromoPageStatus;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoList.PromoPageRequest;
import com.tl.booking.gateway.entity.outbound.PromoList.PromoPageResponse;

import io.reactivex.Single;

public interface PromoPageOutboundService {

  Single<GatewayBaseResponse<RestResponsePage<PromoPageResponse>>> findPromoPageFilterPaginated(
      MandatoryRequest mandatoryRequest,
      String title,
      String categories,
      PromoPageStatus status,
      Integer precedence,
      Integer page,
      Integer size,
      PromoPageColumn columnSort,
      SortDirection sortDirection,
      String privilegeToCheck
  );


  Single<GatewayBaseResponse<PromoPageResponse>> createPromoPage(MandatoryRequest mandatoryRequest,
      PromoPageRequest promoPageRequest);

  Single<GatewayBaseResponse<PromoPageResponse>> updatePromoPage(MandatoryRequest mandatoryRequest,
      PromoPageRequest promoPageRequest, String slug);

  Single<GatewayBaseResponse<Boolean>> deletePromoPage(MandatoryRequest mandatoryRequest, String slug);

  Single<GatewayBaseResponse<PromoPageResponse>> findPromoPageBySlug(
      MandatoryRequest mandatoryRequest,
      String slug);

  Single<GatewayBaseResponse<PromoPageResponse>> updateStatusPendingPromoPage(
      MandatoryRequest mandatoryRequest,
      String slug);

  Single<GatewayBaseResponse<PromoPageResponse>> updateStatusActivatedPromoPage(
      MandatoryRequest mandatoryRequest,
      String slug);

  Single<GatewayBaseResponse<PromoPageResponse>> updateStatusInActivatedPromoPage(
      MandatoryRequest mandatoryRequest,
      String slug);

  Single<GatewayBaseResponse<PromoPageResponse>> updateStatusRejectedPromoPage(
      MandatoryRequest mandatoryRequest,
      String slug);

}
