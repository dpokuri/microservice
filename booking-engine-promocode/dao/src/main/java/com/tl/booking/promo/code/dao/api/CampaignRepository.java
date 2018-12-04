package com.tl.booking.promo.code.dao.api;

import com.tl.booking.promo.code.entity.dao.Campaign;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CampaignRepository extends MongoRepository<Campaign, String>,
    CampaignRepositoryCustom {

  List<Campaign> findByStoreIdAndCampaignStatusAndIsDeleted(String storeId, String campaignStatus,
      Integer isDeleted);

  Campaign findCampaignById(String id);

  Campaign findCampaignByStoreIdAndNameAndIsDeleted(String storeId, String name, Integer isDeleted);

  Campaign findCampaignByStoreIdAndIdAndIsDeleted(String storeId, String id, Integer isDeleted);

  Campaign findCampaignByIdAndNameAndIsDeleted(String id, String name, Integer isDeleted);

  List<Campaign> findAllByStoreIdAndPromoCodeAdjustmentIdAndIsDeleted(String storeId, String promoAdjustmentId, Integer isDeleted);
}
