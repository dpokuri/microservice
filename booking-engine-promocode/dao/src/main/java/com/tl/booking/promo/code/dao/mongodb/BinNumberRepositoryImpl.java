package com.tl.booking.promo.code.dao.mongodb;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import com.mongodb.WriteResult;
import com.tl.booking.promo.code.dao.api.BinNumberRepositoryCustom;
import com.tl.booking.promo.code.dao.utility.QueryConstructor;
import com.tl.booking.promo.code.entity.FilterParam;
import com.tl.booking.promo.code.entity.constant.enums.CriteriaType;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.fields.BaseMongoFields;
import com.tl.booking.promo.code.entity.constant.fields.BinNumberFields;
import com.tl.booking.promo.code.entity.dao.BinNumber;
import com.tl.booking.promo.code.libraries.utility.RepositoryImplHelper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class BinNumberRepositoryImpl implements BinNumberRepositoryCustom {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Override
  public Boolean softDeleted(BinNumber binNumber, String id) {
    Query query = new Query(where(BinNumberFields.ID).is(id));
    Update update = new Update();
    update.inc(BaseMongoFields.IS_DELETED, binNumber.getIsDeleted());
    update.set(BinNumberFields.UPDATED_BY, binNumber.getUpdatedBy());
    update.set(BinNumberFields.UPDATED_DATE, binNumber.getUpdatedDate());
    WriteResult writeResult = mongoTemplate.updateFirst(query, update, BinNumber.class);

    Boolean result = true;

    if (!writeResult.isUpdateOfExisting()) {
      result = false;
    }

    return result;
  }

  @Override
  public Page<BinNumber> findBinNumberFilterPaginated(String storeId, String binNumber,
      String bankId, String cardTypeId, Integer page, Integer size, String columnSort,
      SortDirection sortDirection) {

    List<FilterParam> filterParams = setFilterParamList(binNumber, bankId, cardTypeId);

    Pageable pageable = RepositoryImplHelper.setPageable(page, size, sortDirection, columnSort);

    List<BinNumber> binNumbers = mongoTemplate.find(QueryConstructor
            .constructBy(storeId, filterParams, pageable, true),
        BinNumber.class);

    List<BinNumber> binNumberAll = mongoTemplate.find(QueryConstructor
            .constructBy(storeId, filterParams, pageable, false),
        BinNumber.class);

    Page<BinNumber> binNumberPage = new PageImpl<>(binNumbers, pageable,
        binNumberAll.size());

    return binNumberPage;
  }

  @Override
  public List<BinNumber> findBinNumbers(String storeId, String binNumber,
      String bankId, String cardTypeid) {

    Criteria criteria = new Criteria();
    Query query = new Query();

    query.addCriteria(criteria.and(BinNumberFields.STORE_ID).is(storeId));
    query.addCriteria(criteria.and(BinNumberFields.IS_DELETED).is(0));

    if (binNumber != null) {
      query.addCriteria(criteria.and(BinNumberFields.BIN_NUMBER).is(binNumber));
    }

    if (bankId != null) {
      query.addCriteria(criteria.and(BinNumberFields.BANK_ID).is(bankId));
    }

    if (cardTypeid != null) {
      query.addCriteria(criteria.and(BinNumberFields.CARD_TYPE_ID).is(cardTypeid));
    }

    List<BinNumber> binNumbers = mongoTemplate.find(query, BinNumber.class);

    return binNumbers;
  }

  private List<FilterParam> setFilterParamList(String binNumber, String bankId, String cardTypeId) {
    List<FilterParam> filterParams = new ArrayList<>();

    FilterParam filterParam = new FilterParam();

    filterParam.setParamName(BinNumberFields.BIN_NUMBER);
    filterParam.setParamValue(binNumber);
    filterParam.setCriteriaType(CriteriaType.REGEX);
    filterParams.add(filterParam);

    filterParam = new FilterParam();
    filterParam.setParamName(BinNumberFields.BANK_ID);
    filterParam.setParamValue(bankId);
    filterParam.setCriteriaType(CriteriaType.REGEX);
    filterParams.add(filterParam);

    filterParam = new FilterParam();
    filterParam.setParamName(BinNumberFields.CARD_TYPE_ID);
    filterParam.setParamValue(cardTypeId);
    filterParam.setCriteriaType(CriteriaType.REGEX);
    filterParams.add(filterParam);

    return filterParams;
  }
}
