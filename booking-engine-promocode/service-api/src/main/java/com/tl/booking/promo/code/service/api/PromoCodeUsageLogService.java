package com.tl.booking.promo.code.service.api;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.entity.CostAmount;
import com.tl.booking.promo.code.entity.OrderDetail;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeUsageLogColumn;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.dao.PromoCodeUsageLog;
import io.reactivex.Single;
import java.util.List;
import org.springframework.data.domain.Page;

public interface PromoCodeUsageLogService {

  void createPromoCodeUsageLog(MandatoryRequest mandatoryRequest, String code,
      Double discountAmount,
      List<CostAmount> partnerCostAmount, List<CostAmount> companyCostAmount, Double
      totalPrice, String referenceId, Integer usedQty, List<OrderDetail> orderDetails);

  Single<Page<PromoCodeUsageLog>> findPromoCodeUsageLogFilterPaginated(
      MandatoryRequest mandatoryRequest, String code, String startDate,
      String endDate, Integer page, Integer size,
      PromoCodeUsageLogColumn columnSort, SortDirection sortDirection);
}
