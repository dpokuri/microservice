package com.tl.booking.gateway.outbound.api.promocode;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.constant.enums.CampaignColumn;
import com.tl.booking.gateway.entity.constant.enums.CampaignStatus;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoCode.CampaignDropdownResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.CampaignRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.CampaignResponse;

import io.reactivex.Single;
import java.util.List;

public interface CampaignOutboundService {


  Single<GatewayBaseResponse<CampaignResponse>> findCampaignById(
      MandatoryRequest mandatoryRequest,
      String id);


  Single<GatewayBaseResponse<RestResponsePage<CampaignResponse>>> findCampaignFilterPaginated(
      MandatoryRequest mandatoryRequest,
      String name,
      CampaignStatus campaignStatus,
      Integer page,
      Integer size,
      CampaignColumn columnSort,
      SortDirection sortDirection,
      String privilegeToCheck
  );


  Single<GatewayBaseResponse<CampaignResponse>> createCampaign(MandatoryRequest mandatoryRequest,
      CampaignRequest campaignRequest);

  Single<GatewayBaseResponse<CampaignResponse>> updateCampaign(MandatoryRequest mandatoryRequest,
      CampaignRequest campaignRequest, String id);

  Single<GatewayBaseResponse<Boolean>> deleteCampaign(MandatoryRequest mandatoryRequest,
      String id);

  Single<GatewayBaseResponse<CampaignResponse>> updateStatusActivateCampaign(
      MandatoryRequest mandatoryRequest,
      String id);

  Single<GatewayBaseResponse<CampaignResponse>> updateStatusUnActivateCampaign(
      MandatoryRequest mandatoryRequest,
      String id);

  Single<GatewayBaseResponse<CampaignResponse>> updateStatusPendingCampaign(
      MandatoryRequest mandatoryRequest,
      String id);

  Single<GatewayBaseResponse<CampaignResponse>> updateStatusRejectedCampaign(
      MandatoryRequest mandatoryRequest,
      String id);

  Single<GatewayBaseResponse<List<CampaignDropdownResponse>>> findCampaignActivate(
      MandatoryRequest mandatoryRequest);
}
