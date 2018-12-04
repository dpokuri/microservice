package com.tl.booking.promo.code.dao.mongodb;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import com.mongodb.WriteResult;
import com.tl.booking.promo.code.dao.api.PromoCodeAdjustmentRepositoryCustom;
import com.tl.booking.promo.code.dao.utility.QueryConstructor;
import com.tl.booking.promo.code.entity.FilterParam;
import com.tl.booking.promo.code.entity.constant.enums.CriteriaType;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.fields.BaseMongoFields;
import com.tl.booking.promo.code.entity.constant.fields.PromoCodeAdjustmentFields;
import com.tl.booking.promo.code.entity.dao.PromoCodeAdjustment;
import com.tl.booking.promo.code.libraries.utility.RepositoryImplHelper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class PromoCodeAdjustmentRepositoryImpl implements PromoCodeAdjustmentRepositoryCustom {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Override
  public Page<PromoCodeAdjustment> findPromoCodeAdjustmentFilterPaginated(String storeId,
      String name,
      Boolean isPromoCodeCombine, Double maxDiscount, String promoCodeAdjustmentStatus,
      Integer page, Integer size, String columnSort,
      SortDirection sortDirection) {

    List<FilterParam> filterParams = this
        .setFilterParamList(name, isPromoCodeCombine, maxDiscount, promoCodeAdjustmentStatus);

    Pageable pageable = RepositoryImplHelper.setPageable(page, size, sortDirection, columnSort);
    List<PromoCodeAdjustment> promoCodeAdjustments = mongoTemplate.find(QueryConstructor
            .constructBy(storeId, filterParams, pageable, true),
        PromoCodeAdjustment.class);

    List<PromoCodeAdjustment> promoCodeAdjustmentsAll = mongoTemplate.find(QueryConstructor
            .constructBy(storeId, filterParams, pageable, false),

        PromoCodeAdjustment.class);

    Page<PromoCodeAdjustment> promoCodeAdjustmentPage = new PageImpl<>(promoCodeAdjustments,
        pageable, promoCodeAdjustmentsAll.size());

    return promoCodeAdjustmentPage;
  }


  private List<FilterParam> setFilterParamList(String name, Boolean isPromoCodeCombine, Double
      maxDiscount, String promoCodeAdjustmentStatus) {
    List<FilterParam> filterParams = new ArrayList<>();

    FilterParam filterParam = new FilterParam();

    filterParam.setParamName(PromoCodeAdjustmentFields.NAME);
    filterParam.setParamValue(name);
    filterParam.setCriteriaType(CriteriaType.REGEX);
    filterParams.add(filterParam);

    filterParam = new FilterParam();
    filterParam.setParamName(PromoCodeAdjustmentFields.PROMO_CODE_COMBINE);
    filterParam.setParamValue(isPromoCodeCombine);
    filterParam.setCriteriaType(CriteriaType.EXACT);
    filterParams.add(filterParam);

    filterParam = new FilterParam();
    filterParam.setParamName(PromoCodeAdjustmentFields.MAX_DISCOUNT);
    filterParam.setParamValue(maxDiscount);
    filterParam.setCriteriaType(CriteriaType.EXACT);
    filterParams.add(filterParam);

    filterParam = new FilterParam();
    filterParam.setParamName(PromoCodeAdjustmentFields.PROMO_CODE_ADJUSTMENT_STATUS);
    filterParam.setParamValue(promoCodeAdjustmentStatus);
    filterParam.setCriteriaType(CriteriaType.EXACT);
    filterParams.add(filterParam);

    return filterParams;
  }

  @Override
  public Boolean softDeleted(PromoCodeAdjustment promoCodeAdjustment, String id) {
    Query query = new Query(where(PromoCodeAdjustmentFields.ID).is(id));
    Update update = new Update();
    update.inc(BaseMongoFields.IS_DELETED, promoCodeAdjustment.getIsDeleted());
    update.set(PromoCodeAdjustmentFields.UPDATED_BY, promoCodeAdjustment.getUpdatedBy());
    update.set(PromoCodeAdjustmentFields.UPDATED_DATE, promoCodeAdjustment.getUpdatedDate());
    WriteResult writeResult = mongoTemplate.updateFirst(query, update, PromoCodeAdjustment.class);

    Boolean result = true;

    if (!writeResult.isUpdateOfExisting()) {
      result = false;
    }

    return result;
  }

  @Override
  public Boolean updateStatus(PromoCodeAdjustment promoCodeAdjustment) {
    Query query = new Query(where(PromoCodeAdjustmentFields.ID).is(promoCodeAdjustment.getId()));
    Update update = new Update();
    update.set(PromoCodeAdjustmentFields.PROMO_CODE_ADJUSTMENT_STATUS,
        promoCodeAdjustment.getPromoCodeAdjustmentStatus());
    update.set(PromoCodeAdjustmentFields.UPDATED_BY, promoCodeAdjustment.getUpdatedBy());
    update.set(PromoCodeAdjustmentFields.UPDATED_DATE, promoCodeAdjustment.getUpdatedDate());
    WriteResult writeResult = mongoTemplate.updateFirst(query, update, PromoCodeAdjustment.class);

    Boolean result = true;

    if (!writeResult.isUpdateOfExisting()) {
      result = false;
    }

    return result;
  }


}
