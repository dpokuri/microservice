package com.tl.booking.promo.code.service.api;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.entity.CalculationResult;
import io.reactivex.Single;

public interface IncrementPromoCodeUsageService {

  Single<Boolean> incrementPromoCodeUsage(MandatoryRequest mandatoryRequest,
      CalculationResult calculationResult, String cardNumber, String referenceId);
}
