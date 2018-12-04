package com.tl.booking.promo.code.dao.mongodb;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import com.mongodb.WriteResult;
import com.tl.booking.promo.code.dao.api.VariableRepositoryCustom;
import com.tl.booking.promo.code.dao.utility.QueryConstructor;
import com.tl.booking.promo.code.entity.FilterParam;
import com.tl.booking.promo.code.entity.constant.enums.CriteriaType;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.fields.BaseMongoFields;
import com.tl.booking.promo.code.entity.constant.fields.VariableFields;
import com.tl.booking.promo.code.entity.dao.Variable;
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
public class VariableRepositoryImpl implements VariableRepositoryCustom {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Override
  public Page<Variable> findVariableFilterPaginated(String storeId, String param,
      String inputType, Integer page, Integer size, String columnSort,
      SortDirection sortDirection) {

    List<FilterParam> filterParams = setFilterParamList(param, inputType);

    Pageable pageable = RepositoryImplHelper.setPageable(page, size, sortDirection, columnSort);

    List<Variable> variables = mongoTemplate.find(QueryConstructor
            .constructBy(storeId, filterParams, pageable, true),
        Variable.class);

    List<Variable> variablesAll = mongoTemplate.find(QueryConstructor
            .constructBy(storeId, filterParams, pageable, false),
        Variable.class);

    Page<Variable> variablePage = new PageImpl<>(variables, pageable, variablesAll.size());

    return variablePage;
  }

  @Override
  public Boolean softDeleted(Variable variable, String id) {
    Query query = new Query(where(VariableFields.ID).is(id));
    Update update = new Update();
    update.inc(BaseMongoFields.IS_DELETED, variable.getIsDeleted());
    update.set(VariableFields.UPDATED_BY, variable.getUpdatedBy());
    update.set(VariableFields.UPDATED_DATE, variable.getUpdatedDate());
    WriteResult writeResult = mongoTemplate.updateFirst(query, update, Variable.class);

    Boolean result = true;

    if (!writeResult.isUpdateOfExisting()) {
      result = false;
    }

    return result;
  }


  private List<FilterParam> setFilterParamList(String param, String inputType) {
    List<FilterParam> filterParams = new ArrayList<>();

    FilterParam filterParam = new FilterParam();

    filterParam.setParamName(VariableFields.PARAM);
    filterParam.setParamValue(param);
    filterParam.setCriteriaType(CriteriaType.REGEX);
    filterParams.add(filterParam);

    filterParam = new FilterParam();
    filterParam.setParamName(VariableFields.INPUT_TYPE);
    filterParam.setParamValue(inputType);
    filterParam.setCriteriaType(CriteriaType.EXACT);
    filterParams.add(filterParam);

    return filterParams;
  }


}
