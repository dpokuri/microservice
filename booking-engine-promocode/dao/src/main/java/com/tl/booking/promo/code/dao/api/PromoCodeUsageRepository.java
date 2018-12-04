package com.tl.booking.promo.code.dao.api;

import com.tl.booking.promo.code.entity.dao.PromoCodeUsage;
import java.util.Date;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PromoCodeUsageRepository extends MongoRepository<PromoCodeUsage, String>,
    PromoCodeUsageRepositoryCustom {
  PromoCodeUsage
  findPromoCodeUsageByPromoCodeIdAndStartDateAndEndDateAndCardNumberAndUsername(
      String promoCodeId, Date startDate, Date endDate, String cardNumber, String username);
}
