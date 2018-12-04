package com.tl.booking.promo.code.entity.constant.unit.test;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.common.rest.web.model.request.MandatoryRequestBuilder;
import com.tl.booking.promo.code.entity.CostAmount;
import com.tl.booking.promo.code.entity.CostAmountBuilder;
import com.tl.booking.promo.code.entity.dao.PromoCodeUsageLog;
import com.tl.booking.promo.code.entity.dao.PromoCodeUsageLogBuilder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class PromoCodeUsageLogTestVariable {

  public static String STORE_ID = "TIKETCOM";
  public static String REQUEST_ID = "1";
  public static String SERVICE_ID = "1";
  public static String CHANNEL_ID = "web";
  public static String USERNAME = "username";
  public static Integer IS_DELETED = 0;

  public static String REFERENCE_ID = "123444123";
  public static Double TOTAL_PRICE = 500000.00;


  public static String CODE = "PROMOCODEAJA";
  public static Double DISCOUNT_AMOUNT = 600000.0;
  public static String ID = "1234abrna";
  public static Integer USED_QTY = 1;
  public static Date DATE_NOW = new Date();
  public static String START_DATE = "2018-01-01";
  public static String END_DATE = "2018-05-01";
  public static Integer PAGE = 1;
  public static Integer SIZE = 5;
  public static String DATE_STRING = "2018-02-10";
  public static Date DATE_TYPE = stringToDate(DATE_STRING);
  public static Date START_DATE_TYPE = stringToDate(START_DATE);
  public static Date END_DATE_TYPE = stringToDate(END_DATE);

  public static String NAME = "BCA Cost";
  public static Double AMOUNT = 160000.00;

  public static CostAmount PARTNER_COST_AMOUNT = new CostAmountBuilder()
      .withName(NAME)
      .withAmount(AMOUNT)
      .build();

  public static CostAmount COMPANY_COST_AMOUNT = new CostAmountBuilder()
      .withName(NAME)
      .withAmount(AMOUNT)
      .build();

  public static List<CostAmount> PARTNER_COST_AMOUNTS = Arrays.asList(PARTNER_COST_AMOUNT);

  public static List<CostAmount> COMPANY_COST_AMOUNTS = Arrays.asList(COMPANY_COST_AMOUNT);

  public static PromoCodeUsageLog PROMO_CODE_USAGE_LOG_LIST_REQUEST = new PromoCodeUsageLogBuilder()

      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withIsDeleted(IS_DELETED)
      .withCode(CODE)
      .withDate(stringToDate(START_DATE))
      .withDate(stringToDate(END_DATE))
      .build();

  public static PromoCodeUsageLog PROMO_CODE_USAGE_LOG = new PromoCodeUsageLogBuilder()
      .withId(ID)
      .withCode(CODE)
      .withDate(DATE_TYPE)
      .withDiscountAmount(DISCOUNT_AMOUNT)
      .withPartnerCostAmount(PARTNER_COST_AMOUNTS)
      .withCompanyCostAmount(COMPANY_COST_AMOUNTS)
      .withUsedQty(USED_QTY)
      .build();

  public static List<PromoCodeUsageLog> PROMO_CODE_USAGE_LOG_LIST = Arrays
      .asList(PROMO_CODE_USAGE_LOG_LIST_REQUEST, PROMO_CODE_USAGE_LOG);

  public static List<PromoCodeUsageLog> PROMO_CODE_USAGE_LOG_LIST_NULL = Arrays.asList();

  public static Page<PromoCodeUsageLog> PROMO_CODE_USAGE_LOG_LIST_PAGE = new PageImpl<>(
      PROMO_CODE_USAGE_LOG_LIST);

  public static Page<PromoCodeUsageLog> PROMO_CODE_USAGE_LOG_PAGE_NULL = new PageImpl<>(
      PROMO_CODE_USAGE_LOG_LIST_NULL);

  public static MandatoryRequest MANDATORY_REQUEST = new MandatoryRequestBuilder()
      .withStoreId(STORE_ID).withChannelId(CHANNEL_ID).withRequestId(REQUEST_ID)
      .withServiceId(SERVICE_ID).withUsername(USERNAME).build();

  static Date stringToDate(String date) {
    String pattern = "";
    if (date.length() == 19) {
      pattern = "yyyy-MM-dd HH:mm:ss";

      DateTimeFormatter formatter = DateTimeFormatter
          .ofPattern(pattern, Locale.ENGLISH);

      LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

      return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());

    } else if (date.length() == 10) {
      pattern = "yyyy-MM-dd";

      DateTimeFormatter formatter = DateTimeFormatter
          .ofPattern(pattern, Locale.ENGLISH);

      LocalDate localDate = LocalDate.parse(date, formatter);

      return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    return null;
  }


}
