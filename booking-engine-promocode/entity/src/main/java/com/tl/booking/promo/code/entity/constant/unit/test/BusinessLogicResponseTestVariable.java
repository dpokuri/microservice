package com.tl.booking.promo.code.entity.constant.unit.test;

import com.tl.booking.promo.code.entity.constant.CacheKey;
import com.tl.booking.promo.code.entity.constant.enums.Language;
import com.tl.booking.promo.code.entity.dao.BusinessLogicResponse;
import com.tl.booking.promo.code.entity.dao.BusinessLogicResponseBuilder;
import com.tl.booking.promo.code.entity.dao.ResponseMessageResponse;
import com.tl.booking.promo.code.entity.dao.ResponseMessageResponseBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class BusinessLogicResponseTestVariable {

  public static String RESPONSE_CODE = "Kelvin";
  public static String RESPONSE_CODE_2 = "TEST123";
  public static String ID = "asdasdsd123a";
  public static String MESSAGE = "ggtq";

  public static String LANG = "EN";
  public static Integer IS_DELETED = 0;
  public static Integer PAGE = 0;
  public static Integer SIZE = 10;

  public static ResponseMessageResponse RESPONSE_MESSAGE = new ResponseMessageResponseBuilder()
      .withContent(MESSAGE)
      .withLang(Language.EN)
      .build();
  public static List<ResponseMessageResponse> RESPONSE_MESSAGES = getResponseMessages();
  public static String CACHE_KEY =
      CacheKey.BUSINESS_LOGIC_RESPONSE + "-" + RESPONSE_CODE + "-" + CommonTestVariable.STORE_ID;
  public static String CACHE_KEY2 =
      CacheKey.BUSINESS_LOGIC_RESPONSE + "-" + CommonTestVariable.STORE_ID + "-" + ID;
  public static String CACHE_KEY_FIND_ALL =
      CacheKey.BUSINESS_LOGIC_RESPONSE + "-" + CommonTestVariable.STORE_ID;
  public static String CACHE_KEY_ID =
      CacheKey.BUSINESS_LOGIC_RESPONSE + "-" + RESPONSE_CODE + "-" + CommonTestVariable.STORE_ID
          + "-" + "ID";
  public static String CACHE_KEY_EN =
      CacheKey.BUSINESS_LOGIC_RESPONSE + "-" + RESPONSE_CODE + "-" + CommonTestVariable.STORE_ID+
          "-" + "EN";
  public static BusinessLogicResponse BUSINESS_LOGIC_RESPONSE = new BusinessLogicResponseBuilder()
      .withId(ID)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withCreatedDate(new Date())
      .withUpdatedDate(new Date())
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUsername(CommonTestVariable.USERNAME)
      .withResponseCode(RESPONSE_CODE)
      .withResponseMessage(RESPONSE_MESSAGES)
      .build();
  public static BusinessLogicResponse BUSINESS_LOGIC_RESPONSE_REQUEST = new BusinessLogicResponseBuilder()
      .withStoreId(CommonTestVariable.STORE_ID)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUsername(CommonTestVariable.USERNAME)
      .withResponseCode(RESPONSE_CODE)
      .withResponseMessage(RESPONSE_MESSAGES)
      .build();
  public static BusinessLogicResponse BUSINESS_LOGIC_RESPONSE_CREATE_RESPONSE = new BusinessLogicResponseBuilder()
      .withUsername(CommonTestVariable.USERNAME)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withIsDeleted(IS_DELETED)

      .withResponseCode(RESPONSE_CODE)
      .withResponseMessage(RESPONSE_MESSAGES)
      .build();
  public static List<BusinessLogicResponse> BUSINESS_LOGIC_RESPONSE_LIST = Arrays
      .asList(BUSINESS_LOGIC_RESPONSE, BUSINESS_LOGIC_RESPONSE);
  public static Page<BusinessLogicResponse> BUSINESS_LOGIC_RESPONSE_PAGE = new PageImpl<>(
      BUSINESS_LOGIC_RESPONSE_LIST);
  public static String BUSINESS_LOGIC_RESPONSE_JSON = "{\n"
      + "  \"responseCode\": \"" + RESPONSE_CODE + "\",\n"
      + "  \"responseMessage\":" + "[{\"content\":\"" + MESSAGE + "\",\"lang\":\"" + Language.EN
      + "\"}]"
      + "}";
  public static BusinessLogicResponse BUSINESS_LOGIC_RESPONSE_FIND_IS_DELETED = new BusinessLogicResponseBuilder()
      .withId(ID)
      .withResponseCode(RESPONSE_CODE)
      .withResponseMessage(RESPONSE_MESSAGES)
      .build();
  public static List<BusinessLogicResponse> BUSINESS_LOGIC_RESPONSE_LIST_IS_DELETED = Arrays
      .asList(BUSINESS_LOGIC_RESPONSE_FIND_IS_DELETED);

  private static List<ResponseMessageResponse> getResponseMessages() {
    List<ResponseMessageResponse> responseMessages = new ArrayList<>();
    responseMessages.add(RESPONSE_MESSAGE);

    return responseMessages;
  }

}
