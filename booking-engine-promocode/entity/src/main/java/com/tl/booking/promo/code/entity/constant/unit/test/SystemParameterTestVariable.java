package com.tl.booking.promo.code.entity.constant.unit.test;

import com.tl.booking.promo.code.entity.dao.SystemParameter;
import com.tl.booking.promo.code.entity.dao.SystemParameterBuilder;
import java.util.Date;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class SystemParameterTestVariable {

  public static String VARIABLE = "variable";
  public static String VALUE = "value";
  public static String DESCRIPTION = "description";
  public static String USERNAME = "username";
  public static Integer PAGE = 0;
  public static int SIZE = 10;
  public static String SIZE_STRING = "10";
  public static Pageable PAGEABLE = new PageRequest(PAGE, SIZE);
  public static SystemParameter SYSTEM_PARAMETER = new SystemParameterBuilder()
      .withStoreId(CommonTestVariable.STORE_ID)
      .withValue(VALUE).withDescription(DESCRIPTION).withVariable(VARIABLE)
      .withCreatedBy(USERNAME).withCreatedDate(new Date()).withUpdatedBy(USERNAME)
      .withUpdatedDate(new Date())
      .build();
  public static String SYSTEM_PARAMETER_REQUEST = "{\"description\":\"description\",\"value\":\"value\",\"variable\":\"variable\"}";
  public static String SYSTEM_PARAMETER_REQUEST_ERROR = "{\"description\":\"description\",\"value\":\"value\",\"\":\"variable\"}";
}
