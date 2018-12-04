package com.tl.booking.promo.code.dao.mongodb;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import com.mongodb.WriteResult;
import com.tl.booking.promo.code.dao.api.BusinessLogicResponseRepositoryCustom;
import com.tl.booking.promo.code.dao.utility.QueryConstructor;
import com.tl.booking.promo.code.entity.FilterParam;
import com.tl.booking.promo.code.entity.constant.enums.CriteriaType;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.fields.BaseMongoFields;
import com.tl.booking.promo.code.entity.constant.fields.BusinessLogicResponseFields;
import com.tl.booking.promo.code.entity.dao.BusinessLogicResponse;
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
public class BusinessLogicResponseRepositoryImpl implements BusinessLogicResponseRepositoryCustom {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Override
  public Boolean softDeleted(BusinessLogicResponse businessLogicResponse, String id) {
    Query query = new Query(where(BusinessLogicResponseFields.ID).is(id));
    Update update = new Update();
    update.inc(BaseMongoFields.IS_DELETED, businessLogicResponse.getIsDeleted());
    update.set(BusinessLogicResponseFields.UPDATED_BY, businessLogicResponse.getUpdatedBy());
    update.set(BusinessLogicResponseFields.UPDATED_DATE, businessLogicResponse.getUpdatedDate());
    WriteResult writeResult = mongoTemplate.updateFirst(query, update, BusinessLogicResponse.class);

    Boolean result = true;

    if (!writeResult.isUpdateOfExisting()) {
      result = false;
    }

    return result;
  }

  //  @Override
  public Page<BusinessLogicResponse> findBusinessLogicResponseFilterPaginated(String storeId,
      String responseCode, Integer page, Integer size,
      String columnSort, SortDirection sortDirection) {

    List<FilterParam> filterParams = setFilterParamList(responseCode);

    Pageable pageable = RepositoryImplHelper.setPageable(page, size, sortDirection, columnSort);

    List<BusinessLogicResponse> businessLogicResponses = mongoTemplate.find(QueryConstructor
            .constructBy(storeId, filterParams, pageable, true),
        BusinessLogicResponse.class);

    List<BusinessLogicResponse> businessLogicResponsesAll = mongoTemplate.find(QueryConstructor
            .constructBy(storeId, filterParams, pageable, false),
        BusinessLogicResponse.class);

    Page<BusinessLogicResponse> businessLogicResponsePage = new PageImpl<>(businessLogicResponses,
        pageable,
        businessLogicResponsesAll.size());

    return businessLogicResponsePage;
  }

  private List<FilterParam> setFilterParamList(String name) {
    List<FilterParam> filterParams = new ArrayList<>();

    FilterParam filterParam = new FilterParam();

    filterParam.setParamName(BusinessLogicResponseFields.RESPONSE_CODE);
    filterParam.setParamValue(name);
    filterParam.setCriteriaType(CriteriaType.REGEX);
    filterParams.add(filterParam);

    return filterParams;
  }
}
