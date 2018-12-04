package com.tl.booking.promo.code.dao.mongodb;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import com.mongodb.WriteResult;
import com.tl.booking.promo.code.dao.api.CardTypeRepositoryCustom;
import com.tl.booking.promo.code.dao.utility.QueryConstructor;
import com.tl.booking.promo.code.entity.FilterParam;
import com.tl.booking.promo.code.entity.constant.enums.CriteriaType;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.fields.BankFields;
import com.tl.booking.promo.code.entity.constant.fields.BaseMongoFields;
import com.tl.booking.promo.code.entity.constant.fields.CardTypeFields;
import com.tl.booking.promo.code.entity.dao.CardType;
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
public class CardTypeRepositoryImpl implements CardTypeRepositoryCustom {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Override
  public Boolean softDeleted(CardType cardType, String id) {
    Query query = new Query(where(CardTypeFields.ID).is(id));
    Update update = new Update();
    update.inc(BaseMongoFields.IS_DELETED, cardType.getIsDeleted());
    update.set(CardTypeFields.UPDATED_BY, cardType.getUpdatedBy());
    update.set(CardTypeFields.UPDATED_DATE, cardType.getUpdatedDate());
    WriteResult writeResult = mongoTemplate.updateFirst(query, update, CardType.class);

    Boolean result = true;

    if (!writeResult.isUpdateOfExisting()) {
      result = false;
    }

    return result;
  }

  @Override
  public Page<CardType> findCardTypeFilterPaginated(String storeId, String name, Integer page,
      Integer size,
      String columnSort, SortDirection sortDirection) {

    List<FilterParam> filterParams = setFilterParamList(name);

    Pageable pageable = RepositoryImplHelper.setPageable(page, size, sortDirection, columnSort);

    List<CardType> cardTypes = mongoTemplate.find(QueryConstructor
            .constructBy(storeId, filterParams, pageable, true),
        CardType.class);

    List<CardType> cardTypeAll = mongoTemplate.find(QueryConstructor
            .constructBy(storeId, filterParams, pageable, false),
        CardType.class);

    Page<CardType> cardTypePage = new PageImpl<>(cardTypes, pageable,
        cardTypeAll.size());

    return cardTypePage;
  }

  @Override
  public List<CardType> findCardTypes(Integer isDeleted, String storeId, String bankId) {

    Criteria criteria = new Criteria();
    Query query = new Query();

    query.addCriteria(criteria.and(CardTypeFields.STORE_ID).is(storeId));
    query.addCriteria(criteria.and(CardTypeFields.IS_DELETED).is(isDeleted));

    if (bankId != null) {
      query.addCriteria(criteria.and(CardTypeFields.BANK_ID).is(bankId));
    }

    List<CardType> cardTypes = mongoTemplate.find(query, CardType.class);

    return cardTypes;
  }

  private List<FilterParam> setFilterParamList(String name) {
    List<FilterParam> filterParams = new ArrayList<>();

    FilterParam filteapplicationrParam = new FilterParam();

    filteapplicationrParam.setParamName(BankFields.NAME);
    filteapplicationrParam.setParamValue(name);
    filteapplicationrParam.setCriteriaType(CriteriaType.REGEX);
    filterParams.add(filteapplicationrParam);

    return filterParams;
  }
}
