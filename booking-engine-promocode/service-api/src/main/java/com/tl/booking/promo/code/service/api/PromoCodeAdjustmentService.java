package com.tl.booking.promo.code.service.api;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.entity.PromoCodeAdjustmentDropdown;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeAdjustmentColumn;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeAdjustmentStatus;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.dao.PromoCodeAdjustment;
import io.reactivex.Single;
import java.util.List;
import org.springframework.data.domain.Page;

public interface PromoCodeAdjustmentService {

  Single<PromoCodeAdjustment> createPromoCodeAdjustment(MandatoryRequest mandatoryRequest,
      PromoCodeAdjustment promoCodeAdjustment);

  Single<PromoCodeAdjustment> updatePromoCodeAdjustment(MandatoryRequest mandatoryRequest,
      PromoCodeAdjustment promoCodeAdjustment, String id);

  Single<PromoCodeAdjustment> updateStatusPendingPromoCodeAdjustment(
      MandatoryRequest mandatoryRequest, String id);

  Single<PromoCodeAdjustment> updateStatusActivedPromoCodeAdjustment(
      MandatoryRequest mandatoryRequest, String id);

  Single<PromoCodeAdjustment> updateStatusInActivedPromoCodeAdjustment(
      MandatoryRequest mandatoryRequest, String id);

  Single<PromoCodeAdjustment> updateStatusRejectedPromoCodeAdjustment(
      MandatoryRequest mandatoryRequest, String id);

  Single<Boolean> deletePromoCodeAdjustment(MandatoryRequest mandatoryRequest, String id);

  Single<PromoCodeAdjustment> findPromoCodeAdjustmentById(MandatoryRequest mandatoryRequest,
      String id);

  Single<Page<PromoCodeAdjustment>> findPromoCodeAdjustmentFilterPaginated(
      MandatoryRequest mandatoryRequest, String name,
      Boolean isPromoCodeCombine, Double maxDiscount, PromoCodeAdjustmentStatus
      promoCodeAdjustmentStatus,
      Integer page, Integer size, PromoCodeAdjustmentColumn columnSort,
      SortDirection sortDirection);

  Single<List<PromoCodeAdjustmentDropdown>> findPromoCodeAdjustments(
      MandatoryRequest mandatoryRequest);
}
