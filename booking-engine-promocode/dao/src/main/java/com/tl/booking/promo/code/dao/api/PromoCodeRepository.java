package com.tl.booking.promo.code.dao.api;

import com.tl.booking.promo.code.entity.constant.enums.PromoCodeStatus;
import com.tl.booking.promo.code.entity.dao.PromoCode;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PromoCodeRepository extends MongoRepository<PromoCode, String>,
    PromoCodeRepositoryCustom {

  PromoCode findPromoCodeByStoreIdAndCodeAndIsDeleted(String storeId, String code,
      Integer isDeleted);

  PromoCode findPromoCodeByStoreIdAndCodeAndIsDeletedAndPromoCodeStatus(String storeId, String code,
      Integer isDeleted, PromoCodeStatus promoCodeStatus);

  PromoCode findPromoCodeByIdAndIsDeleted(String id, Integer isDeleted);

  List<PromoCode> findAllByStoreIdAndCampaignIdAndIsDeleted(String storeId, String campaignId, Integer isDeleted);
}
