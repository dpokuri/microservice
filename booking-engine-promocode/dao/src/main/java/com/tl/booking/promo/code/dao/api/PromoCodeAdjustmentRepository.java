package com.tl.booking.promo.code.dao.api;

import com.tl.booking.promo.code.entity.constant.enums.PromoCodeAdjustmentStatus;
import com.tl.booking.promo.code.entity.dao.PromoCodeAdjustment;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PromoCodeAdjustmentRepository extends
    MongoRepository<PromoCodeAdjustment, String>, PromoCodeAdjustmentRepositoryCustom {

  PromoCodeAdjustment findPromoCodeAdjustmentByStoreIdAndCodeAndIsDeleted(String storeId,
      String code, Integer isDeleted);

  List<PromoCodeAdjustment> findAll();

  Page<PromoCodeAdjustment> findAllByStoreIdAndIsDeleted(String storeId, Integer isDeleted,
      Pageable pageable);

  PromoCodeAdjustment findPromoCodeAdjustmentById(String id);

  PromoCodeAdjustment findPromoCodeAdjustmentByIdAndIsDeleted(String id, Integer isDeleted);

  PromoCodeAdjustment findPromoCodeAdjustmentByIdAndPromoCodeAdjustmentStatusAndIsDeleted(String Id,
      PromoCodeAdjustmentStatus promoCodeAdjustmentStatus, Integer isDeleted);

  List<PromoCodeAdjustment> findAllByStoreIdAndPromoCodeAdjustmentStatusAndIsDeleted(String storeId,
      PromoCodeAdjustmentStatus promoCodeAdjustmentStatus, Integer isDeleted);

}
