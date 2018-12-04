package com.tl.booking.gateway.service.api.promocode;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.constant.enums.PromoCodeColumn;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeResponse;

import io.reactivex.Single;

public interface PromoCodeService {

  Single<GatewayBaseResponse<PromoCodeResponse>> findPromoCodeById(
      MandatoryRequest mandatoryRequest, String id, String privilegeToCheck, SessionData sessionData);


  Single<GatewayBaseResponse<RestResponsePage<PromoCodeResponse>>> findPromoCodeFilterPaginated(
      MandatoryRequest mandatoryRequest,
      String code,
      String campaignId,
      Integer page,
      Integer size,
      PromoCodeColumn columnSort,
      SortDirection sortDirection,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<PromoCodeResponse>> createPromoCode(
      MandatoryRequest mandatoryRequest,
      PromoCodeRequest promoCodeRequest,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<PromoCodeResponse>> updatePromoCode(
      MandatoryRequest mandatoryRequest,
      PromoCodeRequest promoCodeRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<Boolean>> deletePromoCode(
      MandatoryRequest mandatoryRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<PromoCodeResponse>> updateStatusActivatePromoCode(
      MandatoryRequest mandatoryRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<PromoCodeResponse>> updateStatusUnActivatePromoCode(
      MandatoryRequest mandatoryRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  );

}
