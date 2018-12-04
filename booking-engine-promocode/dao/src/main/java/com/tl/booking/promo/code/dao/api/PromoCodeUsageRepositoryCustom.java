package com.tl.booking.promo.code.dao.api;

import com.mongodb.WriteResult;

public interface PromoCodeUsageRepositoryCustom {

  WriteResult updatePromoCodeUsageById(String id, Integer increment,
      Integer maxQty);
}
