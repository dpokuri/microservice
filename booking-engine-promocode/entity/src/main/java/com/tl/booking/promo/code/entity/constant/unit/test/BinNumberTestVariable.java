package com.tl.booking.promo.code.entity.constant.unit.test;

import com.tl.booking.promo.code.entity.constant.CacheKey;
import com.tl.booking.promo.code.entity.dao.BinNumber;
import com.tl.booking.promo.code.entity.dao.BinNumberBuilder;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class BinNumberTestVariable {

  public static String BIN_NUMBER = "BinNumber Asqo";
  public static String BANK_ID = "asdfgh12123";
  public static String CARD_TYPE_ID = "13467hgghy12";
  public static String DESCRIPTION = "ini description";

  public static String BIN_NUMBER_2 = "BinNumber Asqo 2";
  public static String BANK_ID_2 = "asdfgh121232";
  public static String CARD_TYPE_ID_2 = "13467hgghy122";
  public static String DESCRIPTION_2 = "ini description2";

  public static String ID = "asdfg123123122a";

  public static Integer PAGE = 0;
  public static Integer SIZE = 10;

  public static String CACHE_KEY =
      CacheKey.BIN_NUMBER + "-" + CommonTestVariable.STORE_ID + "-" + ID;
  public static String CACHE_KEY_FIND_ALL = CacheKey.BIN_NUMBER + "-" + CommonTestVariable.STORE_ID
      + "-" + BIN_NUMBER + "-" + BANK_ID + "-" + CARD_TYPE_ID;

  public static BinNumber BIN_NUMBER_BUILDER = new BinNumberBuilder()
      .withId(ID)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withCreatedDate(new Date())
      .withUpdatedDate(new Date())
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUsername(CommonTestVariable.USERNAME)
      .withBinNumber(BIN_NUMBER)
      .withBankId(BANK_ID)
      .withCardTypeId(CARD_TYPE_ID)
      .withDescription(DESCRIPTION)
      .build();

  public static String CACHE_KEY1 = generateCacheKeyFindAll(CommonTestVariable.STORE_ID,
      BIN_NUMBER, BANK_ID, CARD_TYPE_ID);
  public static String CACHE_KEY2 = generateCacheKeyFindAll(CommonTestVariable.STORE_ID,
      null, null, null);
  public static String CACHE_KEY3 = generateCacheKeyFindAll(CommonTestVariable.STORE_ID,
      BIN_NUMBER, null, null);
  public static String CACHE_KEY4 = generateCacheKeyFindAll(CommonTestVariable.STORE_ID,
      null, BANK_ID, null);
  public static String CACHE_KEY5 = generateCacheKeyFindAll(CommonTestVariable.STORE_ID,
      null, null, CARD_TYPE_ID);
  public static String CACHE_KEY6 = generateCacheKeyFindAll(CommonTestVariable.STORE_ID,
      BIN_NUMBER, BANK_ID, null);
  public static String CACHE_KEY7 = generateCacheKeyFindAll(CommonTestVariable.STORE_ID,
      BIN_NUMBER, null, CARD_TYPE_ID);
  public static String CACHE_KEY8 = generateCacheKeyFindAll(CommonTestVariable.STORE_ID,
      null, BANK_ID, CARD_TYPE_ID);
  public static BinNumber BIN_NUMBER_FIND_IS_DELETED = new BinNumberBuilder()
      .withId(ID)
      .withBinNumber(BIN_NUMBER)
      .withBankId(BANK_ID)
      .withCardTypeId(CARD_TYPE_ID)
      .build();
  public static BinNumber BIN_NUMBER_REQUEST = new BinNumberBuilder()
      .withStoreId(CommonTestVariable.STORE_ID)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUsername(CommonTestVariable.USERNAME)
      .withBinNumber(BIN_NUMBER)
      .withCardTypeId(CARD_TYPE_ID)
      .withBankId(BANK_ID)
      .withDescription(DESCRIPTION)
      .build();
  public static BinNumber BIN_NUMBER_BUILDER_2 = new BinNumberBuilder()
      .withId(ID)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withCreatedDate(new Date())
      .withUpdatedDate(new Date())
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUsername(CommonTestVariable.USERNAME)
      .withBinNumber(BIN_NUMBER_2)
      .withBankId(BANK_ID_2)
      .withCardTypeId(CARD_TYPE_ID_2)
      .withDescription(DESCRIPTION_2)
      .build();
  public static List<BinNumber> BIN_NUMBER_LIST = Arrays
      .asList(BIN_NUMBER_BUILDER, BIN_NUMBER_BUILDER);
  public static List<BinNumber> BIN_NUMBER_LIST_IS_DELETED = Arrays
      .asList(BIN_NUMBER_FIND_IS_DELETED);
  public static Page<BinNumber> BIN_NUMBER_PAGE = new PageImpl<>(BIN_NUMBER_LIST);
  public static List<BinNumber> BIN_NUMBER_NULL = Arrays.asList();
  public static Page<BinNumber> BIN_NUMBER_PAGE_NULL = new PageImpl<>(BIN_NUMBER_NULL);
  public static String BIN_NUMBER_JSON = "{\n"
      + "  \"binNumber\": \"" + BIN_NUMBER + "\",\n"
      + "  \"bankId\": \"" + BANK_ID + "\",\n"
      + "  \"cardTypeId\": \"" + CARD_TYPE_ID + "\",\n"
      + "  \"description\": \"" + DESCRIPTION + "\"\n"
      + "}";

  private static String generateCacheKeyFindAll(String storeId, String binNumber, String bankId,
      String cardTypeId) {
    return CacheKey.BIN_NUMBER + "-" + storeId + "-" + binNumber + "-" + bankId + "-" + cardTypeId;
  }
}
