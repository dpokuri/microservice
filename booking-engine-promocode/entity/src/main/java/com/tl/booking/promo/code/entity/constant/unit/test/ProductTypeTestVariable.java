package com.tl.booking.promo.code.entity.constant.unit.test;

import com.tl.booking.promo.code.entity.constant.CacheKey;
import com.tl.booking.promo.code.entity.dao.ProductType;
import com.tl.booking.promo.code.entity.dao.ProductTypeBuilder;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class ProductTypeTestVariable {

  public static String DESCRIPTION = "123456";
  public static String NAME = "123456";
  public static String NAME_2 = "1234567";
  public static String NAME_3 = "1234568";
  public static String ID = "12345edfewfef";
  public static String INPUT_SOURCE = "input123";
  public static String ALLOW_ARITH_STRING = "test1, test2";

  public static Integer PAGE = 0;
  public static Integer SIZE = 10;

  public static String CACHE_KEY =
      CacheKey.PRODUCT_TYPE + "-" + CommonTestVariable.STORE_ID + "-" + ID;
  public static String CACHE_KEY_FIND = CacheKey.PRODUCT_TYPE + "-" + CommonTestVariable.STORE_ID;

  public static List<String> ALLOWED_ARITH = Arrays.asList("test1", "test2");

  public static ProductType PRODUCT_TYPE = new ProductTypeBuilder()
      .withId(ID)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withCreatedDate(new Date())
      .withUpdatedDate(new Date())
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUsername(CommonTestVariable.USERNAME)

      .withName(NAME)
      .withDescription(DESCRIPTION)
      .build();

  public static ProductType PRODUCT_TYPE_REQUEST = new ProductTypeBuilder()
      .withStoreId(CommonTestVariable.STORE_ID)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUsername(CommonTestVariable.USERNAME)

      .withName(NAME)
      .withDescription(DESCRIPTION)
      .build();

  public static ProductType PRODUCT_TYPE_2 = new ProductTypeBuilder()
      .withId(ID)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withCreatedDate(new Date())
      .withUpdatedDate(new Date())
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUsername(CommonTestVariable.USERNAME)

      .withName(NAME_2)
      .withDescription(DESCRIPTION)
      .build();


  public static List<ProductType> PRODUCT_TYPE_LIST = Arrays.asList(PRODUCT_TYPE, PRODUCT_TYPE);


  public static Page<ProductType> PRODUCT_TYPE_PAGE = new PageImpl<>(PRODUCT_TYPE_LIST);

  public static List<ProductType> PRODUCT_TYPE_NULLS = Arrays.asList();

  public static Page<ProductType> PRODUCT_TYPE_PAGE_NULL = new PageImpl<>(PRODUCT_TYPE_NULLS);

  public static String PRODUCT_TYPE_JSON = "{\n"
      + "  \"description\": \"" + DESCRIPTION + "\",\n"
      + "  \"name\": \"" + NAME + "\"\n"
      + "}";
}
