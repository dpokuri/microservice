package com.tl.booking.promo.code.dao.api;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.dao.PromoCodeUsageLog;
import org.springframework.data.domain.Page;

public interface PromoCodeUsageLogRepositoryCustom {

  Page<PromoCodeUsageLog> findPromoCodeUsageLogFilterPaginated(MandatoryRequest mandatoryRequest,
      String code, String startDate,
      String endDate, Integer page, Integer size,
      String columnSort, SortDirection sortDirection);
}
