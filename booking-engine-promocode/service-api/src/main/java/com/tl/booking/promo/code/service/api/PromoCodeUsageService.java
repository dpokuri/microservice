package com.tl.booking.promo.code.service.api;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.entity.PromoCodeObject;
import com.tl.booking.promo.code.entity.dao.PromoCodeUsage;
import io.reactivex.Single;
import java.util.Date;

public interface PromoCodeUsageService {

  Single<PromoCodeUsage> findPromoCodeUsageByPromoCodeObjectAndUsername(
      MandatoryRequest mandatoryRequest, PromoCodeObject promoCodeObject);

  Single<PromoCodeUsage>
  findPromoCodeUsageByPromoCodeObjectAndUsernameAndTodayDate(
      MandatoryRequest mandatoryRequest, Date todayDate, PromoCodeObject promoCodeObject);

  Single<PromoCodeUsage>
  findPromoCodeUsageGeneralByPromoCodeObjectAndUsername(
      MandatoryRequest mandatoryRequest, PromoCodeObject promoCodeObject);

  Single<PromoCodeUsage> findPromoCodeUsageByPromoCodeObjectAndCardNumber(
      MandatoryRequest mandatoryRequest, PromoCodeObject promoCodeObject, String cardNumber);

  Single<PromoCodeUsage>
  findPromoCodeUsageByPromoCodeObjectAndCardNameAndTodayDate(
      MandatoryRequest mandatoryRequest, Date todayDate, PromoCodeObject promoCodeObject, String
      cardNumber);

  Single<Integer> findCurrentQuotaByPromoCode(MandatoryRequest mandatoryRequest, String code);

  Single<PromoCodeObject> createPromoCodeUsageGeneral(MandatoryRequest mandatoryRequest, String code);
}
