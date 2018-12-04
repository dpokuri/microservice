package com.tl.booking.promo.code.entity.constant.unit.test;

import com.tl.booking.promo.code.entity.constant.CacheKey;
import com.tl.booking.promo.code.entity.dao.Bank;
import com.tl.booking.promo.code.entity.dao.BankBuilder;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class BankTestVariable {

  public static String NAME = "Bank Asqo";
  public static String NAME_2 = "1234567";
  public static String NAME_3 = "1234568";
  public static String ID = "asdfg123123122a";

  public static Integer PAGE = 0;
  public static Integer SIZE = 10;

  public static String CACHE_KEY = CacheKey.BANK + "-" + CommonTestVariable.STORE_ID + "-" + ID;
  public static String CACHE_KEY_FIND_ALL = CacheKey.BANK + "-" + CommonTestVariable.STORE_ID;


  public static Bank BANK = new BankBuilder()
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
      .build();

  public static Bank BANK_FIND_IS_DELETED = new BankBuilder()
      .withId(ID)
      .withName(NAME)
      .build();

  public static Bank BANK_REQUEST = new BankBuilder()
      .withStoreId(CommonTestVariable.STORE_ID)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUsername(CommonTestVariable.USERNAME)
      .withName(NAME)
      .build();

  public static Bank BANK_2 = new BankBuilder()
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
      .build();


  public static List<Bank> BANK_LIST = Arrays.asList(BANK, BANK);

  public static List<Bank> BANK_LIST_IS_DELETED = Arrays.asList(BANK_FIND_IS_DELETED);

  public static Page<Bank> BANK_PAGE = new PageImpl<>(BANK_LIST);

  public static List<Bank> BANK_NULL = Arrays.asList();

  public static Page<Bank> BANK_PAGE_NULL = new PageImpl<>(BANK_NULL);

  public static String BANK_JSON = "{\n"
      + "  \"name\": \"" + NAME + "\"\n"
      + "}";
}
