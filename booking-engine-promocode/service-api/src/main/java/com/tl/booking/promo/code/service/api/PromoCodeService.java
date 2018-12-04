package com.tl.booking.promo.code.service.api;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeColumn;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeStatus;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.dao.Campaign;
import com.tl.booking.promo.code.entity.dao.PromoCode;
import io.reactivex.Single;
import java.util.List;
import org.springframework.data.domain.Page;

public interface PromoCodeService {

  Single<PromoCode> createPromoCode(MandatoryRequest mandatoryRequest, PromoCode promoCode);

  Single<PromoCode> updatePromoCode(MandatoryRequest mandatoryRequest, PromoCode promoCode,
      String id);

  Single<PromoCode> updateStatusActivedPromoCode(MandatoryRequest mandatoryRequest, String id);

  Single<PromoCode> updateStatusInActivedPromoCode(MandatoryRequest mandatoryRequest, String id);

  Single<Boolean> deletePromoCode(MandatoryRequest mandatoryRequest, String id);

  Single<PromoCode> findPromoCodeById(MandatoryRequest mandatoryRequest, String id);

  Single<PromoCode> findPromoCodeByCode(MandatoryRequest mandatoryRequest, String code);

  Single<PromoCode> findPromoCodeByCodeAndStatus(MandatoryRequest mandatoryRequest, String id,
      PromoCodeStatus promoCodeStatus);

  Single<Page<PromoCode>> findPromoCodeFilterPaginated(MandatoryRequest mandatoryRequest,
      String code, String campaignId, Integer page, Integer size, PromoCodeColumn columnSort,
      SortDirection sortDirection);

  Single<Boolean> checkExistAndActivePromoCodeByCampaigns(MandatoryRequest mandatoryRequest, List<Campaign> campaigns, Boolean toDeleted);

  List<PromoCode> findPromoCodeByCampaignId(MandatoryRequest mandatoryRequest, String campaignId);
}
