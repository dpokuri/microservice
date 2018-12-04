package com.tl.booking.promo.code.dao.utility;

import com.tl.booking.promo.code.entity.FilterParam;
import com.tl.booking.promo.code.entity.constant.fields.BaseMongoFields;
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

  public static Query constructBy(String storeId, List<FilterParam> filterParams, Pageable pageable,
      Boolean statusPageable
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
            query.addCriteria(criteria.and(fieldPath).regex((String) fieldValue, "i"));
            break;
          case EXACT:
            query.addCriteria(criteria.and(fieldPath).is(fieldValue));
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

    Integer getElemMatchCount = getElemMatchCount(filterParams);

    Criteria[] arrayElemCriteria = new Criteria[getElemMatchCount];
    Integer countNumber = 0;
    for (FilterParam filterParam : filterParams) {
      if (filterParam.getParamValue() != null) {

        String fieldPath = filterParam.getParamName();
        Object fieldValue = filterParam.getParamValue();
        switch (filterParam.getCriteriaType()) {
          case ELEM_MATCH:
            String[] elemMatchField = fieldPath.split(ELEM_MATCH_SEPARATOR);

            String fieldValueString = (String) fieldValue;
            String[] elemMatchValue = fieldValueString.split(ELEM_MATCH_OPERATOR_SEPARATOR);
            Criteria criteriaElem = new Criteria();
            for (Integer number = elemMatchField.length; number > 0; number--) {
              String elemenMatchFieldParam = elemMatchField[number - 1];
              if (number == elemMatchField.length) {
                String[] elemMatchOperator = elemenMatchFieldParam
                    .split(ELEM_MATCH_OPERATOR_SEPARATOR);
                if (elemMatchOperator.length > 1) {
                  Integer getLengthElemMatchOperatorWithoutNull = getLengthElemMatchOperatorWithoutNull(
                      elemMatchValue);
                  Criteria[] criteriaElemOperator = new Criteria[getLengthElemMatchOperatorWithoutNull];
                  Integer countNumberOperator = 0;
                  for (Integer numberOperator = 0; numberOperator < elemMatchValue.length;
                      numberOperator++) {
                    if (!elemMatchValue[numberOperator].equals("null")) {
                      criteriaElemOperator[countNumberOperator] = Criteria
                          .where(elemMatchOperator[numberOperator])
                          .regex((String) elemMatchValue[numberOperator]);
                      countNumberOperator++;
                    }
                  }

                  if (criteriaElemOperator.length > 0) {
                    criteriaElem = criteriaElem.andOperator(criteriaElemOperator);
                  }

                } else {
                  criteriaElem = Criteria.where(elemMatchOperator[0]).regex(elemMatchValue[0]);
                }

              } else {
                arrayElemCriteria[countNumber] = Criteria.where(elemenMatchFieldParam)
                    .elemMatch(criteriaElem);
                countNumber++;
              }
            }
            break;
          case ELEM_MATCH_NUMBERING:
            arrayElemCriteria[countNumber] = Criteria.where(fieldPath).is(fieldValue);
            countNumber++;
            break;
          case ELEM_MATCH_NUMBERING_REGEX:
            arrayElemCriteria[countNumber] = Criteria.where(fieldPath).regex((String) fieldValue);
            countNumber++;
            break;
          case BETWEEN_START_DATE_END_DATE:
            if (filterParam.getParamValue() != null) {

              String[] elemMatchFieldBetween = fieldPath.split(ELEM_MATCH_SEPARATOR);
              String[] elemMatchOperator = elemMatchFieldBetween[1]
                  .split(ELEM_MATCH_OPERATOR_SEPARATOR);

              Object fieldValueBetween = filterParam.getParamValue();

              Criteria criteriaElemBetween = new Criteria();
              Criteria[] criteriaElemField = new Criteria[2];

              criteriaElemField[0] = Criteria.where(elemMatchOperator[0])
                  .lte(fieldValueBetween);
              criteriaElemField[1] = Criteria.where(elemMatchOperator[1])
                  .gte(fieldValueBetween);

              criteriaElemBetween = criteriaElemBetween.andOperator(criteriaElemField);

              arrayElemCriteria[countNumber] = Criteria.where(elemMatchFieldBetween[0])
                  .elemMatch(criteriaElemBetween);
              countNumber++;
            }
            break;

        }
      }
    }

    if (countNumber > 0) {
      query.addCriteria(criteria.andOperator(arrayElemCriteria));
    }

    if (statusPageable) {
      query.with(pageable);
    }

    return query;
  }

  private static Integer getLengthElemMatchOperatorWithoutNull(String[] elemMatchValue) {
    Integer number = 0;
    for (Integer numberOperator = 0; numberOperator < elemMatchValue.length;
        numberOperator++) {
      if (!elemMatchValue[numberOperator].equals("null")) {
        number++;
      }
    }

    return number;
  }


  private static Integer getElemMatchCount(List<FilterParam> filterParams) {
    Integer number = 0;
    for (FilterParam filterParam : filterParams) {
      if (filterParam.getParamValue() != null) {
        switch (filterParam.getCriteriaType()) {
          case ELEM_MATCH:
            number++;
            break;
          case ELEM_MATCH_NUMBERING:
            number++;
            break;
          case ELEM_MATCH_NUMBERING_REGEX:
            number++;
            break;
          case BETWEEN_START_DATE_END_DATE:
            number++;
            break;

        }
      }
    }

    return number;
  }

}
