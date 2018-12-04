package com.tl.booking.promo.code.service.api;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.entity.CampaignDropdown;
import com.tl.booking.promo.code.entity.constant.enums.CampaignColumn;
import com.tl.booking.promo.code.entity.constant.enums.CampaignStatus;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.dao.Campaign;
import io.reactivex.Single;
import java.util.List;
import org.springframework.data.domain.Page;

public interface CampaignService {

  Single<List<CampaignDropdown>> findCampaigns(MandatoryRequest mandatoryRequest);

  Single<Campaign> createCampaign(MandatoryRequest mandatoryRequest, Campaign campaign);

  Single<Campaign> updateCampaign(MandatoryRequest mandatoryRequest, Campaign campaign, String id);

  Single<Campaign> findCampaignById(MandatoryRequest mandatoryRequest, String id);

  Single<Boolean> deleteCampaign(MandatoryRequest mandatoryRequest, String id);

  Single<Page<Campaign>> findCampaignFilterPaginated(MandatoryRequest mandatoryRequest,
      String name, CampaignStatus campaignStatus, Integer page, Integer size,
      CampaignColumn columnSort,
      SortDirection sortDirection);

  Single<Campaign> updateStatusPendingCampaign(MandatoryRequest mandatoryRequest, String id);

  Single<Campaign> updateStatusRejectedCampaign(MandatoryRequest mandatoryRequest, String id);

  Single<Campaign> updateStatusActiveCampaign(MandatoryRequest mandatoryRequest, String id);

  Single<Campaign> updateStatusInactiveCampaign(MandatoryRequest mandatoryRequest, String id);

  Single<Boolean> checkExistAndActiveCampaignByAdjustmentId(MandatoryRequest mandatoryRequest,
      String adjustmentId, Boolean isDeleted);
}
