package com.tl.booking.gateway.service.api.promocode;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.constant.enums.CampaignColumn;
import com.tl.booking.gateway.entity.constant.enums.CampaignStatus;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.PromoCode.CampaignDropdownResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.CampaignRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.CampaignResponse;

import io.reactivex.Single;
import java.util.List;

public interface CampaignService {

  Single<GatewayBaseResponse<CampaignResponse>> findCampaignById(
      MandatoryRequest mandatoryRequest, String id, String privilegeToCheck,
      SessionData sessionData);


  Single<GatewayBaseResponse<RestResponsePage<CampaignResponse>>> findCampaignFilterPaginated(
      MandatoryRequest mandatoryRequest,
      String name,
      CampaignStatus campaignStatus,
      Integer page,
      Integer size,
      CampaignColumn columnSort,
      SortDirection sortDirection,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<CampaignResponse>> createCampaign(
      MandatoryRequest mandatoryRequest,
      CampaignRequest promoCodeRequest,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<CampaignResponse>> updateCampaign(
      MandatoryRequest mandatoryRequest,
      CampaignRequest promoCodeRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<Boolean>> deleteCampaign(
      MandatoryRequest mandatoryRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<CampaignResponse>> updateStatusActivateCampaign(
      MandatoryRequest mandatoryRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<CampaignResponse>> updateStatusUnActivateCampaign(
      MandatoryRequest mandatoryRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<CampaignResponse>> updateStatusPendingCampaign(
      MandatoryRequest mandatoryRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<CampaignResponse>> updateStatusRejectedCampaign(
      MandatoryRequest mandatoryRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<List<CampaignDropdownResponse>>> findCampaignActivate(
      MandatoryRequest mandatoryRequest,
      String privilegeToCheck,
      SessionData sessionData
  );
}
