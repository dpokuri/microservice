package com.tl.booking.promo.code.entity.constant.unit.test;

import com.tl.booking.promo.code.entity.constant.CacheKey;
import com.tl.booking.promo.code.entity.dao.VariableSource;
import com.tl.booking.promo.code.entity.dao.VariableSourceBuilder;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class VariableSourceTestVariable {

  public static String DESCRIPTION = "123456";
  public static String NAME = "123456";
  public static String NAME_2 = "1234567";
  public static String VALUE_ID = "val123";
  public static String VALUE_NAME = "valName123";
  public static String LABEL = "label123";
  public static String SOURCE_TYPE = "source123";
  public static String KEY = "key123";
  public static String ID = "12345edfewfef";
  public static String INPUT_SOURCE = "input123";
  public static String ALLOW_ARITH_STRING = "test1, test2";

  public static Integer PAGE = 0;
  public static Integer SIZE = 10;

  public static String CACHE_KEY =
      CacheKey.VARIABLE_SOURCE + "-" + CommonTestVariable.STORE_ID + "-" + ID;
  public static String CACHE_KEY_SOURCE =
      CacheKey.VARIABLE_SOURCE + "-" + CommonTestVariable.STORE_ID + "-" + SOURCE_TYPE;
  public static String CACHE_KEY_ID =
      CacheKey.VARIABLE_SOURCE + "-" + CommonTestVariable.STORE_ID + "-" + ID;
  public static String CACHE_KEY_SOURCE_AND_KEY =
      CacheKey.VARIABLE_SOURCE + "-" + CommonTestVariable.STORE_ID + "-" + SOURCE_TYPE + "-" + KEY;

  public static List<String> ALLOWED_ARITH = Arrays.asList("test1", "test2");

  public static VariableSource VARIABLE_SOURCE = new VariableSourceBuilder()
      .withId(ID)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withCreatedDate(new Date())
      .withUpdatedDate(new Date())
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUsername(CommonTestVariable.USERNAME)

      .withSourceType(SOURCE_TYPE)
      .withValueId(VALUE_ID)
      .withValueName(VALUE_NAME)
      .build();

  public static VariableSource VARIABLE_SOURCE_REQUEST = new VariableSourceBuilder()
      .withStoreId(CommonTestVariable.STORE_ID)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUsername(CommonTestVariable.USERNAME)

      .withSourceType(SOURCE_TYPE)
      .withValueId(VALUE_ID)
      .withValueName(VALUE_NAME)

      .build();

  public static List<VariableSource> VARIABLE_SOURCE_LIST = Arrays.asList(VARIABLE_SOURCE);


  public static Page<VariableSource> VARIABLE_SOURCE_PAGE = new PageImpl<>(VARIABLE_SOURCE_LIST);

  public static List<VariableSource> VARIABLE_SOURCE_NULLS = Arrays.asList();

  public static Page<VariableSource> VARIABLE_SOURCE_PAGE_NULL = new PageImpl<>(
      VARIABLE_SOURCE_NULLS);

  public static String VARIABLE_SOURCE_JSON = "{\n"
      + "  \"description\": \"" + DESCRIPTION + "\",\n"
      + "  \"name\": \"" + NAME + "\"\n"
      + "}";
}
