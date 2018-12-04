package com.tl.booking.promo.code.dao.mongodb;


import static org.springframework.data.mongodb.core.query.Criteria.where;

import com.mongodb.WriteResult;
import com.tl.booking.promo.code.dao.api.PromoCodeRepositoryCustom;
import com.tl.booking.promo.code.dao.utility.QueryConstructor;
import com.tl.booking.promo.code.entity.FilterParam;
import com.tl.booking.promo.code.entity.constant.enums.CriteriaType;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.fields.BaseMongoFields;
import com.tl.booking.promo.code.entity.constant.fields.PromoCodeFields;
import com.tl.booking.promo.code.entity.dao.PromoCode;
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
public class PromoCodeRepositoryImpl implements PromoCodeRepositoryCustom {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Override
  public Page<PromoCode> findPromoCodeFilterPaginated(String storeId, String code,
      String campaignId, Integer page, Integer size, String columnSort,
      SortDirection sortDirection) {

    List<FilterParam> filterParams = this.filterParam(code, campaignId);

    Pageable pageable = RepositoryImplHelper.setPageable(page, size, sortDirection, columnSort);

    List<PromoCode> promoCodes = mongoTemplate.find(QueryConstructor
            .constructBy(storeId, filterParams, pageable, true),
        PromoCode.class);

    List<PromoCode> promoCodesAll = mongoTemplate.find(QueryConstructor
            .constructBy(storeId, filterParams, pageable, true),
        PromoCode.class);

    Page<PromoCode> promoCodePage = new PageImpl<>(promoCodes, pageable, promoCodesAll.size());

    return promoCodePage;
  }

 private List<FilterParam> filterParam(String code, String campaignId) {
    List<FilterParam> filterParams = new ArrayList<>();

    FilterParam filterParam = new FilterParam();

    filterParam.setParamName(PromoCodeFields.CODE);
    filterParam.setParamValue(code);
    filterParam.setCriteriaType(CriteriaType.REGEX);
    filterParams.add(filterParam);

    if(campaignId != null){
      filterParam = new FilterParam();
      filterParam.setParamName(PromoCodeFields.CAMPAIGN_ID);
      filterParam.setParamValue(campaignId);
      filterParam.setCriteriaType(CriteriaType.EXACT);
      filterParams.add(filterParam);
    }

    return filterParams;
  }


  @Override
  public Boolean softDeleted(PromoCode promoCode, String id) {
    Query query = new Query(where(PromoCodeFields.ID).is(id));
    Update update = new Update();
    update.inc(BaseMongoFields.IS_DELETED, promoCode.getIsDeleted());
    update.set(PromoCodeFields.UPDATED_BY, promoCode.getUpdatedBy());
    update.set(PromoCodeFields.UPDATED_DATE, promoCode.getUpdatedDate());
    WriteResult writeResult = mongoTemplate.updateFirst(query, update, PromoCode.class);

    Boolean result = true;

    if (!writeResult.isUpdateOfExisting()) {
      result = false;
    }

    return result;
  }

  @Override
  public Boolean updateStatus(PromoCode promoCode) {
    Query query = new Query(where(PromoCodeFields.ID).is(promoCode.getId()));
    Update update = new Update();
    update.set(PromoCodeFields.PROMO_CODE_STATUS, promoCode.getPromoCodeStatus());
    update.set(PromoCodeFields.UPDATED_BY, promoCode.getUpdatedBy());
    update.set(PromoCodeFields.UPDATED_DATE, promoCode.getUpdatedDate());
    WriteResult writeResult = mongoTemplate.updateFirst(query, update, PromoCode.class);

    Boolean result = true;

    if (!writeResult.isUpdateOfExisting()) {
      result = false;
    }

    return result;
  }


}
