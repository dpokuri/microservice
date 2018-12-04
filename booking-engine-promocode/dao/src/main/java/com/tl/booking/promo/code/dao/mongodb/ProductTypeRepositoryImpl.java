package com.tl.booking.promo.code.dao.mongodb;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import com.mongodb.WriteResult;
import com.tl.booking.promo.code.dao.api.ProductTypeRepositoryCustom;
import com.tl.booking.promo.code.dao.utility.QueryConstructor;
import com.tl.booking.promo.code.entity.FilterParam;
import com.tl.booking.promo.code.entity.constant.enums.CriteriaType;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.fields.BaseMongoFields;
import com.tl.booking.promo.code.entity.constant.fields.ProductTypeFields;
import com.tl.booking.promo.code.entity.dao.ProductType;
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
public class ProductTypeRepositoryImpl implements ProductTypeRepositoryCustom {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Override
  public Page<ProductType> findProductTypeFilterPaginated(String storeId, String name, Integer page,
      Integer size, String columnSort,
      SortDirection sortDirection) {

    List<FilterParam> filterParams = setFilterParamList(name);

    Pageable pageable = RepositoryImplHelper.setPageable(page, size, sortDirection, columnSort);

    List<ProductType> productTypes = mongoTemplate.find(QueryConstructor
            .constructBy(storeId, filterParams, pageable, true),
        ProductType.class);

    List<ProductType> productTypesAll = mongoTemplate.find(QueryConstructor
            .constructBy(storeId, filterParams, pageable, false),
        ProductType.class);

    Page<ProductType> productTypePage = new PageImpl<>(productTypes, pageable,
        productTypesAll.size());

    return productTypePage;
  }

  @Override
  public Boolean softDeleted(ProductType productType, String id) {
    Query query = new Query(where(ProductTypeFields.ID).is(id));
    Update update = new Update();
    update.inc(BaseMongoFields.IS_DELETED, productType.getIsDeleted());
    update.set(ProductTypeFields.UPDATED_BY, productType.getUpdatedBy());
    update.set(ProductTypeFields.UPDATED_DATE, productType.getUpdatedDate());
    WriteResult writeResult = mongoTemplate.updateFirst(query, update, ProductType.class);

    Boolean result = true;

    if (!writeResult.isUpdateOfExisting()) {
      result = false;
    }

    return result;
  }


  private List<FilterParam> setFilterParamList(String name) {
    List<FilterParam> filterParams = new ArrayList<>();

    FilterParam filterParam = new FilterParam();

    filterParam.setParamName(ProductTypeFields.NAME);
    filterParam.setParamValue(name);
    filterParam.setCriteriaType(CriteriaType.REGEX);
    filterParams.add(filterParam);

    return filterParams;
  }


}
