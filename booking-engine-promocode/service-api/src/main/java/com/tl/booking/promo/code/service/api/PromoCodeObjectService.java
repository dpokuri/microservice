package com.tl.booking.promo.code.service.api;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.entity.PromoCodeObject;
import com.tl.booking.promo.code.entity.dao.PromoCode;
import io.reactivex.Single;

public interface PromoCodeObjectService {

  Single<PromoCodeObject> findPromoCodeObjectByStoreIdAndCode(MandatoryRequest mandatoryRequest,
      String code);

  Single<PromoCodeObject> findPromoCodeObjectByCampaignIdMergeWithPromoCode(
      MandatoryRequest mandatoryRequest, String id,
      PromoCode promoCode);
}
