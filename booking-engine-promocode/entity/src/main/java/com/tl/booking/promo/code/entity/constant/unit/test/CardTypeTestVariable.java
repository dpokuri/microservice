package com.tl.booking.promo.code.entity.constant.unit.test;

import com.tl.booking.promo.code.entity.constant.CacheKey;
import com.tl.booking.promo.code.entity.dao.CardType;
import com.tl.booking.promo.code.entity.dao.CardTypeBuilder;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class CardTypeTestVariable {

  public static String NAME = "Card Type Asqo";
  public static String NAME_2 = "1234567";
  public static String NAME_3 = "1234568";
  public static String ID = "asdfg123123122a";
  public static String BANK_ID = "4536721831";
  public static String BANK_ID_2 = "45367218s1";

  public static Integer PAGE = 0;
  public static Integer SIZE = 10;

  public static String CACHE_KEY =
      CacheKey.CARD_TYPE + "-" + CommonTestVariable.STORE_ID + "-" + ID;

  public static String CACHE_KEY_FIND_ALL = CacheKey.CARD_TYPE + "-" + CommonTestVariable.STORE_ID
      + "-" + BANK_ID;

  public static CardType CARD_TYPE = new CardTypeBuilder()
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
      .withBankId(BANK_ID)
      .build();

  public static CardType CARD_TYPE_FIND_IS_DELETED = new CardTypeBuilder()
      .withId(ID)
      .withName(NAME)
      .withBankId(BANK_ID)
      .build();

  public static CardType CARD_TYPE_REQUEST = new CardTypeBuilder()
      .withStoreId(CommonTestVariable.STORE_ID)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUsername(CommonTestVariable.USERNAME)
      .withName(NAME)
      .withBankId(BANK_ID)
      .build();

  public static CardType CARD_TYPE_2 = new CardTypeBuilder()
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
      .withBankId(BANK_ID)
      .build();


  public static List<CardType> CARD_TYPE_LIST = Arrays.asList(CARD_TYPE, CARD_TYPE);

  public static List<CardType> CARD_TYPE_LIST_IS_DELETED = Arrays.asList(CARD_TYPE_FIND_IS_DELETED);

  public static List<CardType> CARD_TYPE_NULL = Arrays.asList();

  public static Page<CardType> CARD_TYPE_PAGE = new PageImpl<>(CARD_TYPE_LIST);

  public static Page<CardType> CARD_TYPE_PAGE_NULL = new PageImpl<>(CARD_TYPE_NULL);

  public static String CARD_TYPE_JSON = "{\n"
      + "  \"name\": \"" + NAME + "\",\n"
      + "  \"bankId\": \"" + BANK_ID + "\"\n"
      + "}";
}
