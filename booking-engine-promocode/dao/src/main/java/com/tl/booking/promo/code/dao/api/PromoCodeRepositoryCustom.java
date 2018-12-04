package com.tl.booking.promo.code.dao.api;

import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.dao.PromoCode;
import org.springframework.data.domain.Page;

public interface PromoCodeRepositoryCustom {

  Page<PromoCode> findPromoCodeFilterPaginated(String storeId, String code,
      String campaignId, Integer page, Integer size, String columnSort,
      SortDirection sortDirection);

  Boolean softDeleted(PromoCode promoCode, String id);

  Boolean updateStatus(PromoCode promoCode);

}
