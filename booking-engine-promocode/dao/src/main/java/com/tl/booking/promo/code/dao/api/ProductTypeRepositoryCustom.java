package com.tl.booking.promo.code.dao.api;

import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.dao.ProductType;
import org.springframework.data.domain.Page;

public interface ProductTypeRepositoryCustom {

  Page<ProductType> findProductTypeFilterPaginated(String storeId, String name, Integer page,
      Integer size, String columnSort,
      SortDirection sortDirection);


  Boolean softDeleted(ProductType productType, String id);

}
