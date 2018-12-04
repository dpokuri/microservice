package com.tl.booking.promo.code.dao.api;

import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.dao.PromoCodeAdjustment;
import org.springframework.data.domain.Page;

public interface PromoCodeAdjustmentRepositoryCustom {

  Page<PromoCodeAdjustment> findPromoCodeAdjustmentFilterPaginated(String storeId, String name,
      Boolean isPromoCodeCombine, Double maxDiscount,
      String promoCodeAdjustmentStatus,
      Integer page, Integer size, String columnSort,
      SortDirection sortDirection);

  Boolean softDeleted(PromoCodeAdjustment promoCodeAdjustment, String id);

  Boolean updateStatus(PromoCodeAdjustment promoCodeAdjustment);
}
