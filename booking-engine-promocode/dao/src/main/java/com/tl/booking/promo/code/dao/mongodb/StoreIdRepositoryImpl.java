package com.tl.booking.promo.code.dao.mongodb;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import com.mongodb.WriteResult;
import com.tl.booking.promo.code.dao.api.StoreIdRepositoryCustom;
import com.tl.booking.promo.code.dao.utility.QueryConstructor;
import com.tl.booking.promo.code.entity.FilterParam;
import com.tl.booking.promo.code.entity.constant.enums.CriteriaType;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.fields.BaseMongoFields;
import com.tl.booking.promo.code.entity.constant.fields.StoreIdFields;
import com.tl.booking.promo.code.entity.dao.StoreId;
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
public class StoreIdRepositoryImpl implements StoreIdRepositoryCustom {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Override
  public Page<StoreId> findStoreIdFilterPaginated(String storeId, String name, Integer page,
      Integer size, String columnSort,
      SortDirection sortDirection) {

    List<FilterParam> filterParams = setFilterParamList(name);

    Pageable pageable = RepositoryImplHelper.setPageable(page, size, sortDirection, columnSort);

    List<StoreId> storeIds = mongoTemplate.find(QueryConstructor
            .constructBy(storeId, filterParams, pageable, true),
        StoreId.class);

    List<StoreId> storeIdsAll = mongoTemplate.find(QueryConstructor
            .constructBy(storeId, filterParams, pageable, false),
        StoreId.class);

    Page<StoreId> storeIdPage = new PageImpl<>(storeIds, pageable,
        storeIdsAll.size());

    return storeIdPage;
  }

  @Override
  public Boolean softDeleted(StoreId storeId, String id) {
    Query query = new Query(where(StoreIdFields.ID).is(id));
    Update update = new Update();
    update.inc(BaseMongoFields.IS_DELETED, storeId.getIsDeleted());
    update.set(StoreIdFields.UPDATED_BY, storeId.getUpdatedBy());
    update.set(StoreIdFields.UPDATED_DATE, storeId.getUpdatedDate());
    WriteResult writeResult = mongoTemplate.updateFirst(query, update, StoreId.class);

    Boolean result = true;

    if (!writeResult.isUpdateOfExisting()) {
      result = false;
    }

    return result;
  }


  private List<FilterParam> setFilterParamList(String name) {
    List<FilterParam> filterParams = new ArrayList<>();

    FilterParam filterParam = new FilterParam();

    filterParam.setParamName(StoreIdFields.VALUE);
    filterParam.setParamValue(name);
    filterParam.setCriteriaType(CriteriaType.REGEX);
    filterParams.add(filterParam);

    return filterParams;
  }


}
