package com.tl.booking.promo.code.dao.mongodb;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import com.mongodb.WriteResult;
import com.tl.booking.promo.code.dao.api.PaymentRepositoryCustom;
import com.tl.booking.promo.code.dao.utility.QueryConstructor;
import com.tl.booking.promo.code.entity.FilterParam;
import com.tl.booking.promo.code.entity.constant.enums.CriteriaType;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.fields.BaseMongoFields;
import com.tl.booking.promo.code.entity.constant.fields.PaymentFields;
import com.tl.booking.promo.code.entity.dao.Payment;
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
public class PaymentRepositoryImpl implements PaymentRepositoryCustom {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Override
  public Boolean softDeleted(Payment payment, String id) {
    Query query = new Query(where(PaymentFields.ID).is(id));
    Update update = new Update();
    update.inc(BaseMongoFields.IS_DELETED, payment.getIsDeleted());
    update.set(PaymentFields.UPDATED_BY, payment.getUpdatedBy());
    update.set(PaymentFields.UPDATED_DATE, payment.getUpdatedDate());
    WriteResult writeResult = mongoTemplate.updateFirst(query, update, Payment.class);

    Boolean result = true;

    if (!writeResult.isUpdateOfExisting()) {
      result = false;
    }

    return result;
  }

  @Override
  public Page<Payment> findPaymentFilterPaginated(String storeId, String name, String paymentId,
      Integer page, Integer size, String columnSort, SortDirection sortDirection) {

    List<FilterParam> filterParams = setFilterParamList(name, paymentId);

    Pageable pageable = RepositoryImplHelper.setPageable(page, size, sortDirection, columnSort);

    List<Payment> payments = mongoTemplate.find(QueryConstructor
            .constructBy(storeId, filterParams, pageable, true),
        Payment.class);

    List<Payment> paymentAll = mongoTemplate.find(QueryConstructor
            .constructBy(storeId, filterParams, pageable, false),
        Payment.class);

    Page<Payment> paymentPage = new PageImpl<>(payments, pageable,
        paymentAll.size());

    return paymentPage;
  }

  private List<FilterParam> setFilterParamList(String name, String paymentId) {
    List<FilterParam> filterParams = new ArrayList<>();

    FilterParam filterParam = new FilterParam();

    filterParam.setParamName(PaymentFields.NAME);
    filterParam.setParamValue(name);
    filterParam.setCriteriaType(CriteriaType.REGEX);
    filterParams.add(filterParam);

    filterParam = new FilterParam();
    filterParam.setParamName(PaymentFields.PAYMENT_ID);
    filterParam.setParamValue(paymentId);
    filterParam.setCriteriaType(CriteriaType.REGEX);
    filterParams.add(filterParam);

    return filterParams;
  }
}
