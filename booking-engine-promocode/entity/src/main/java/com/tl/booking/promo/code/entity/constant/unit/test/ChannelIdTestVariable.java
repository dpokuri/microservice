package com.tl.booking.promo.code.entity.constant.unit.test;

import com.tl.booking.promo.code.entity.constant.CacheKey;
import com.tl.booking.promo.code.entity.dao.ChannelId;
import com.tl.booking.promo.code.entity.dao.ChannelIdBuilder;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class ChannelIdTestVariable {

  public static String LABEL = "123456";
  public static String NAME = "123456";
  public static String NAME_2 = "1234567";
  public static String NAME_3 = "1234568";
  public static String ID = "12345edfewfef";
  public static String INPUT_SOURCE = "input123";
  public static String ALLOW_ARITH_STRING = "test1, test2";

  public static Integer PAGE = 0;
  public static Integer SIZE = 10;

  public static String CACHE_KEY =
      CacheKey.CHANNEL_ID + "-" + CommonTestVariable.STORE_ID + "-" + ID;
  public static String CACHE_KEY_FIND = CacheKey.CHANNEL_ID + "-" + CommonTestVariable.STORE_ID;

  public static List<String> ALLOWED_ARITH = Arrays.asList("test1", "test2");

  public static ChannelId CHANNEL_ID = new ChannelIdBuilder()
      .withId(ID)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withCreatedDate(new Date())
      .withUpdatedDate(new Date())
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUsername(CommonTestVariable.USERNAME)

      .withValue(NAME)
      .withLabel(LABEL)
      .build();

  public static ChannelId CHANNEL_ID_REQUEST = new ChannelIdBuilder()
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUsername(CommonTestVariable.USERNAME)
      .withValue(NAME)
      .withLabel(LABEL)
      .build();

  public static ChannelId CHANNEL_ID_2 = new ChannelIdBuilder()
      .withId(ID)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withCreatedDate(new Date())
      .withUpdatedDate(new Date())
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUsername(CommonTestVariable.USERNAME)

      .withValue(NAME_2)
      .withLabel(LABEL)
      .build();


  public static List<ChannelId> CHANNEL_ID_LIST = Arrays.asList(CHANNEL_ID, CHANNEL_ID);


  public static Page<ChannelId> CHANNEL_ID_PAGE = new PageImpl<>(CHANNEL_ID_LIST);

  public static List<ChannelId> CHANNEL_ID_NULLS = Arrays.asList();

  public static Page<ChannelId> CHANNEL_ID_PAGE_NULL = new PageImpl<>(CHANNEL_ID_NULLS);

  public static String CHANNEL_ID_JSON = "{\n"
      + "  \"label\": \"" + LABEL + "\",\n"
      + "  \"value\": \"" + NAME + "\"\n"
      + "}";
}
