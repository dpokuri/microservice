package com.tl.booking.image.entity.constant.unit.test;

import com.tl.booking.image.entity.constant.CacheKey;
import com.tl.booking.image.entity.dao.SystemParameter;
import com.tl.booking.image.entity.dao.SystemParameterBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class SystemParameterTestVariable {

  public static final String ID = "id";
  public static final String VARIABLE = "variable";
  public static final String VALUE = "value";
  public static final String DESCRIPTION = "description";
  public static final String USERNAME = "username";
  public static final Integer PAGE = 0;
  public static final int SIZE = 10;
  public static final String SIZE_STRING = "10";
  public static final Pageable PAGEABLE = new PageRequest(PAGE, SIZE);
  public static final String KEY = CacheKey.SYSTEM_PARAMETER + "-" + ID;
  public static final SystemParameter SYSTEM_PARAMETER = new SystemParameterBuilder()
      .withStoreId(CommonTestVariable.STORE_ID)
      .withValue(VALUE).withDescription(DESCRIPTION).withVariable(VARIABLE)
      .withId(ID)
      .build();

  public static final SystemParameter CREATE_SYSTEM_PARAMETER = new SystemParameterBuilder()
      .withStoreId(CommonTestVariable.STORE_ID)
      .withValue(VALUE).withDescription(DESCRIPTION).withVariable(VARIABLE)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withIsDeleted(0)
      .build();

  public static final SystemParameter UPDATE_SYSTEM_PARAMETER = new SystemParameterBuilder()
      .withStoreId(CommonTestVariable.STORE_ID)
      .withValue(VALUE).withDescription(DESCRIPTION).withVariable(VARIABLE)
      .withId(ID)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withIsDeleted(0)
      .build();

  public static final SystemParameter SYSTEM_PARAMETER_REQUEST = new SystemParameterBuilder()
      .withValue(VALUE).withDescription(DESCRIPTION)
      .withVariable(VARIABLE)
      .build();

  public static final String SYSTEM_PARAMETER_REQUEST_BODY = "{\"description\":\"description\",\"value\":\"value\",\"variable\":\"variable\"}";
  public static String SYSTEM_PARAMETER_REQUEST_JSON = "{\"description\":\"description\",\"value\":\"value\",\"variable\":\"variable\"}";
  public static String SYSTEM_PARAMETER_REQUEST_JSON_METHOD_ARGUMENT = "{\"description\":\"description\",\"value\":\"value\",\"\":\"variable\"}";

}
