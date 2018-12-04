package com.tl.booking.promo.code.dao.api;

import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.dao.Variable;
import org.springframework.data.domain.Page;

public interface VariableRepositoryCustom {

  Page<Variable> findVariableFilterPaginated(String storeId, String columnFilterParam,
      String columnFilterInputType, Integer page, Integer size, String columnSort,
      SortDirection sortDirection);

  Boolean softDeleted(Variable variable, String id);

}
