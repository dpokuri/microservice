package com.tl.booking.promo.code.dao.api;

import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.dao.Campaign;
import org.springframework.data.domain.Page;

public interface CampaignRepositoryCustom {

  Boolean softDeleted(Campaign campaign, String id);

  Boolean updateStatus(Campaign campaign);

  Page<Campaign> findCampaignFilterPaginated(String storeId, String name,
      String campaignStatus, Integer page, Integer size, String columnSort,
      SortDirection sortDirection);
}
