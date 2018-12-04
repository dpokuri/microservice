package com.tl.booking.promo.code.service.api.log;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.entity.dao.PromoCodeAdjustment;
import io.reactivex.Single;

public interface PromoCodeAdjustmentLogService {

  Single<PromoCodeAdjustment> createPromoCodeAdjustmentLog(MandatoryRequest mandatoryRequest,
      PromoCodeAdjustment promoCodeAdjustment);

}
