package com.tl.booking.promo.code.dao.mongodb;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import com.mongodb.WriteResult;
import com.tl.booking.promo.code.dao.api.BankRepositoryCustom;
import com.tl.booking.promo.code.dao.utility.QueryConstructor;
import com.tl.booking.promo.code.entity.FilterParam;
import com.tl.booking.promo.code.entity.constant.enums.CriteriaType;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.fields.BankFields;
import com.tl.booking.promo.code.entity.constant.fields.BaseMongoFields;
import com.tl.booking.promo.code.entity.dao.Bank;
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
public class BankRepositoryImpl implements BankRepositoryCustom {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Override
  public Boolean softDeleted(Bank bank, String id) {
    Query query = new Query(where(BankFields.ID).is(id));
    Update update = new Update();
    update.inc(BaseMongoFields.IS_DELETED, bank.getIsDeleted());
    update.set(BankFields.UPDATED_BY, bank.getUpdatedBy());
    update.set(BankFields.UPDATED_DATE, bank.getUpdatedDate());
    WriteResult writeResult = mongoTemplate.updateFirst(query, update, Bank.class);

    Boolean result = true;

    if (!writeResult.isUpdateOfExisting()) {
      result = false;
    }

    return result;
  }

  @Override
  public Page<Bank> findBankFilterPaginated(String storeId, String name, Integer page, Integer size,
      String columnSort, SortDirection sortDirection) {

    List<FilterParam> filterParams = setFilterParamList(name);

    Pageable pageable = RepositoryImplHelper.setPageable(page, size, sortDirection, columnSort);

    List<Bank> banks = mongoTemplate.find(QueryConstructor
            .constructBy(storeId, filterParams, pageable, true),
        Bank.class);

    List<Bank> banksAll = mongoTemplate.find(QueryConstructor
            .constructBy(storeId, filterParams, pageable, false),
        Bank.class);

    Page<Bank> bankPage = new PageImpl<>(banks, pageable,
        banksAll.size());

    return bankPage;
  }

  private List<FilterParam> setFilterParamList(String name) {
    List<FilterParam> filterParams = new ArrayList<>();

    FilterParam filterParam = new FilterParam();

    filterParam.setParamName(BankFields.NAME);
    filterParam.setParamValue(name);
    filterParam.setCriteriaType(CriteriaType.REGEX);
    filterParams.add(filterParam);

    return filterParams;
  }
}
