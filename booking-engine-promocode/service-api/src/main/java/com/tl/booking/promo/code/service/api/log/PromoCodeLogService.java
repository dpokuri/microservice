package com.tl.booking.promo.code.service.api.log;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.entity.dao.PromoCode;
import io.reactivex.Single;

public interface PromoCodeLogService {

  Single<PromoCode> createPromoCodeLog(MandatoryRequest mandatoryRequest, PromoCode promoCode);

}
