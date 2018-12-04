package com.tl.booking.promo.code.dao.api;

import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.dao.StoreId;
import org.springframework.data.domain.Page;

public interface StoreIdRepositoryCustom {

  Page<StoreId> findStoreIdFilterPaginated(String storeId, String value, Integer page,
      Integer size, String columnSort,
      SortDirection sortDirection);


  Boolean softDeleted(StoreId storeId, String id);

}
