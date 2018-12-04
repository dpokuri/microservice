package com.tl.booking.promo.code.dao.api;

import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.dao.BusinessLogicResponse;
import org.springframework.data.domain.Page;

public interface BusinessLogicResponseRepositoryCustom {

  Boolean softDeleted(BusinessLogicResponse businessLogicResponse, String id);

  Page<BusinessLogicResponse> findBusinessLogicResponseFilterPaginated(String storeId,
      String responseCode, Integer page,
      Integer size, String columnSort,
      SortDirection sortDirection);
}
