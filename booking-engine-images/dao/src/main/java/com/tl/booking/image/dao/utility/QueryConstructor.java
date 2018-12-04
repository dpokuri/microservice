package com.tl.booking.image.dao.utility;

import com.tl.booking.image.entity.FilterParam;
import com.tl.booking.image.entity.constant.fields.BaseMongoFields;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class QueryConstructor {

  private static final int ZERO = 0;
  private static final int OUTER_PATH_INDEX = 0;
  private static final int INNER_PATH_INDEX = 1;
  private static final String ELEM_MATCH_SEPARATOR = "#";
  private static final String ELEM_MATCH_OPERATOR_SEPARATOR = ",";

  public static Query constructBy(String storeId, List<FilterParam> filterParams, Pageable pageable, Boolean statusPageable
  ) {
    Criteria criteria = new Criteria();

    Query query = new Query();

    query.addCriteria(Criteria.where(BaseMongoFields.STORE_ID).is(storeId));
    query.addCriteria(criteria.and(BaseMongoFields.IS_DELETED).is(0));

    for (FilterParam filterParam : filterParams) {
      if (filterParam.getParamValue() != null) {

        String fieldPath = filterParam.getParamName();
        Object fieldValue = filterParam.getParamValue();
        switch (filterParam.getCriteriaType()) {
          case REGEX:
            query.addCriteria(criteria.and(fieldPath).regex((String) fieldValue));
            break;
          case EXACT:
            query.addCriteria(criteria.and(fieldPath).is(fieldValue));
            break;
          case ELEM_MATCH:
            String[] elemMatchField = fieldPath.split(ELEM_MATCH_SEPARATOR);

            String fieldValueString = (String) fieldValue;
            String[] elemMatchValue = fieldValueString.split(ELEM_MATCH_OPERATOR_SEPARATOR);
            Criteria criteriaElem = new Criteria();
            for (Integer number = elemMatchField.length; number > 0; number--) {
              String elemenMatchFieldParam = elemMatchField[number - 1];
              if(number == elemMatchField.length)
              {
                String[] elemMatchOperator = elemenMatchFieldParam.split(ELEM_MATCH_OPERATOR_SEPARATOR);
                if(elemMatchOperator.length > 1)
                {
                  Integer getLengthElemMatchOperatorWithoutNull = getLengthElemMatchOperatorWithoutNull(elemMatchValue);
                  Criteria[] criteriaElemOperator = new Criteria[getLengthElemMatchOperatorWithoutNull];
                  Integer countNumberOperator = 0;
                  for (Integer numberOperator = 0; numberOperator < elemMatchValue.length;
                      numberOperator++) {
                    if(!elemMatchValue[numberOperator].equals("null"))
                    {
                      criteriaElemOperator[countNumberOperator] = Criteria.where(elemMatchOperator[numberOperator]).regex((String) elemMatchValue[numberOperator]);
                      countNumberOperator++;
                    }
                  }

                  if(criteriaElemOperator.length > 0)
                  {
                    criteriaElem = criteriaElem.andOperator(criteriaElemOperator);
                  }

                }
                else
                {
                  criteriaElem = Criteria.where(elemMatchOperator[0]).regex(elemMatchValue[0]);
                }

              }
              else
              {
                criteriaElem = Criteria.where(elemenMatchFieldParam).elemMatch(criteriaElem);
              }
            }
            query.addCriteria(criteria.andOperator(criteriaElem));
            break;
          case GTE:
            query.addCriteria(criteria.and(fieldPath).gte(fieldValue));
            break;
          case LTE:
            query.addCriteria(criteria.and(fieldPath).lte(fieldValue));
            break;
        }

      }
    }

    if(statusPageable)
    {
      query.with(pageable);
    }

    return query;
  }

  private static Integer getLengthElemMatchOperatorWithoutNull(String[] elemMatchValue) {
    Integer number = 0;
    for (Integer numberOperator = 0; numberOperator < elemMatchValue.length;
        numberOperator++) {
      if(!elemMatchValue[numberOperator].equals("null"))
      {
        number++;
      }
    }

    return number;
  }

}
