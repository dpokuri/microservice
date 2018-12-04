package com.tl.booking.promo.code.dao.mongodb;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.dao.api.PromoCodeUsageLogRepositoryCustom;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.fields.PromoCodeUsageLogFields;
import com.tl.booking.promo.code.entity.dao.PromoCodeUsageLog;
import com.tl.booking.promo.code.libraries.utility.RepositoryImplHelper;
import java.util.Date;
import java.util.List;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class PromoCodeUsageLogRepositoryImpl implements PromoCodeUsageLogRepositoryCustom {

  @Autowired
  private MongoTemplate mongoTemplate;

  public Page<PromoCodeUsageLog> findPromoCodeUsageLogFilterPaginated(
      MandatoryRequest mandatoryRequest, String code,
      String startDate, String endDate, Integer page,
      Integer size, String columnSort, SortDirection sortDirection) {

    Pageable pageable = RepositoryImplHelper.setPageable(page, size, sortDirection, columnSort);

    List<PromoCodeUsageLog> promoCodeUsageLogsAll = mongoTemplate.findAll(PromoCodeUsageLog.class);

    Criteria criteria = new Criteria();
    Query query = new Query();

    if (code != null) {
      query.addCriteria(criteria.and(PromoCodeUsageLogFields.CODE).is(code));
    }

    Date dateFrom = null;
    if (isNotNullStringDate(startDate)) {
      dateFrom = DateTime.parse(startDate).toDate();
    }

    Date dateTo = null;
    if (isNotNullStringDate(endDate)) {
      dateTo = DateTime.parse(endDate).plusHours(23).plusMinutes(59).plusSeconds(59).toDate();
    }

    if (startDate != null && endDate != null) {
      query.addCriteria(criteria.and(PromoCodeUsageLogFields.DATE).gte(dateFrom).lte(dateTo));
    } else {
      if (startDate != null) {
        query.addCriteria(criteria.and(PromoCodeUsageLogFields.DATE).gte(dateFrom));
      }

      if (endDate != null) {
        query.addCriteria(criteria.and(PromoCodeUsageLogFields.DATE).lte(dateTo));
      }
    }

    List<PromoCodeUsageLog> promoCodeUsageLogs = mongoTemplate
        .find(query.with(pageable), PromoCodeUsageLog.class);
    Page<PromoCodeUsageLog> result = new PageImpl<>(promoCodeUsageLogs, pageable,
        promoCodeUsageLogsAll.size());

    return result;
  }

  private boolean isNotNullStringDate(String date) {
    Boolean result = false;
    if (date != null) {
      result = true;
    }

    return result;
  }
}
