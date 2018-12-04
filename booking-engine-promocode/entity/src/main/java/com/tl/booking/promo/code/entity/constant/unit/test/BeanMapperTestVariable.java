package com.tl.booking.promo.code.entity.constant.unit.test;

import com.tl.booking.promo.code.entity.dao.SystemParameter;
import com.tl.booking.promo.code.entity.dao.SystemParameterBuilder;
import java.util.Date;

public class BeanMapperTestVariable {

  public static String VARIABLE = "variable";
  public static String VALUE = "value";
  public static String DESCRIPTION = "description";
  public static String USERNAME = "username";
  public static SystemParameter SYSTEM_PARAMETER = new SystemParameterBuilder()
      .withStoreId(CommonTestVariable.STORE_ID)
      .withValue(VALUE).withDescription(DESCRIPTION).withVariable(VARIABLE)
      .withCreatedBy(USERNAME).withCreatedDate(new Date()).withUpdatedBy(USERNAME)
      .withUpdatedDate(new Date())
      .build();
}
