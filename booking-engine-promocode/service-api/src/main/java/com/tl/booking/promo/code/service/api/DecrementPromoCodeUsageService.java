package com.tl.booking.promo.code.service.api;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import io.reactivex.Single;

public interface DecrementPromoCodeUsageService {

  Single<Boolean> decrementPromoCodeUsage(MandatoryRequest mandatoryRequest, String
      code, String cardNumber, String referenceId);
}
