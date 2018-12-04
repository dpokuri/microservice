package com.tl.booking.gateway.entity.constant.unit.test;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.tl.booking.gateway.entity.dao.SystemParameter;
import com.tl.booking.gateway.entity.dao.SystemParameterBuilder;

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
  public static final SystemParameter SYSTEM_PARAMETER = new SystemParameterBuilder()
      .withStoreId(CommonTestVariable.STORE_ID)
      .withValue(VALUE).withDescription(DESCRIPTION).withVariable(VARIABLE)
      .withId(ID)
      .build();

  public static final SystemParameter SYSTEM_PARAMETER_REQUEST = new SystemParameterBuilder()
      .withStoreId(CommonTestVariable.STORE_ID).withValue(VALUE).withDescription(DESCRIPTION)
      .withVariable(VARIABLE)
      .build();

  public static final String SYSTEM_PARAMETER_REQUEST_BODY = "{\"description\":\"description\",\"value\":\"value\",\"variable\":\"variable\"}";
  public static final String SYSTEM_PARAMETER_REQUEST_BODY_METHOD = "{\"description\":\"\",\"value\":\"\",\"variable\":\"variable\"}";
}
