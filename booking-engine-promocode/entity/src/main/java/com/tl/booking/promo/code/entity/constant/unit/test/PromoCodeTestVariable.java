package com.tl.booking.promo.code.entity.constant.unit.test;

import com.tl.booking.promo.code.entity.CampaignPeriod;
import com.tl.booking.promo.code.entity.CampaignPeriodBuilder;
import com.tl.booking.promo.code.entity.PromoCodeObject;
import com.tl.booking.promo.code.entity.PromoCodeObjectBuilder;
import com.tl.booking.promo.code.entity.constant.CacheKey;
import com.tl.booking.promo.code.entity.constant.enums.CampaignStatus;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeStatus;
import com.tl.booking.promo.code.entity.dao.Campaign;
import com.tl.booking.promo.code.entity.dao.CampaignBuilder;
import com.tl.booking.promo.code.entity.dao.PromoCode;
import com.tl.booking.promo.code.entity.dao.PromoCodeAdjustment;
import com.tl.booking.promo.code.entity.dao.PromoCodeAdjustmentBuilder;
import com.tl.booking.promo.code.entity.dao.PromoCodeBuilder;
import com.tl.booking.promo.code.entity.dao.log.PromoCodeLog;
import com.tl.booking.promo.code.entity.dao.log.PromoCodeLogBuilder;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class PromoCodeTestVariable {

  public static final String PROMO_CODE_JSON_BODY = "{\"campaignId\": \"CAMPID\",\"code\": \"PROMOCODE\",\"maxQty\": 100}";

  public static Date START_DATE = new DateTime().plusDays(1).toDate();
  public static Date END_DATE = new DateTime().plusDays(2).toDate();
  public static Date START_DATE_1 = new DateTime().plusDays(10).toDate();
  public static Date END_DATE_1 = new DateTime().plusDays(11).toDate();
  public static Date START_DATE_EXPIRED = new DateTime().minusDays(2).toDate();
  public static Date END_DATE_EXPIRED = new DateTime().minusDays(1).toDate();
  public static String ID = "5a4cb92b184bf51b2d0e4b30";
  public static String CODE = "PROMOCODE";
  public static String CODE_SPACE = "PROMOCODE ";
  public static String VALUE = "value";
  public static String USERNAME = "development";
  public static Integer MAX_QTY = 100;
  public static Integer PAGE = 0;
  public static Integer SIZE = 10;
  public static String CAMPAIGN_ID = "CAMPID";
  public static String CAMPAIGN_ID_EXIST = "CAMPIDEXIST";
  public static String CAMPAIGN_NAME = "CAMPNAME";
  public static String ADJUSTMENT_ID = "ADJID";
  public static String SORT = "CODE";
  public static String SORT_DIRECTION = "ASC";
  public static String CACHE_KEY = CacheKey.PROMO_CODE + "-" + ID;
  public static String CACHE_KEY_BY_STORE_ID_AND_CODE =
      CacheKey.PROMO_CODE + "-" + CommonTestVariable.STORE_ID + "-" + CODE;


  public static String CACHE_KEY_BY_CAMPAIGN_ID = CacheKey.PROMO_CODE + "-campaign-" + CommonTestVariable.STORE_ID + "-" + CAMPAIGN_ID;


  public static Integer IS_DELETED = 1;
  public static CampaignPeriod CAMPAIGN_PERIOD = new CampaignPeriodBuilder()
      .withStartDate(START_DATE).withEndDate(END_DATE).build();
  public static CampaignPeriod CAMPAIGN_PERIOD_1 = new CampaignPeriodBuilder()
      .withStartDate(START_DATE_1).withEndDate(END_DATE_1).build();
  public static CampaignPeriod CAMPAIGN_PERIOD_EXPIRED = new CampaignPeriodBuilder()
      .withStartDate(START_DATE_EXPIRED).withEndDate(END_DATE_EXPIRED).build();
  public static List<CampaignPeriod> CAMPAIGN_PERIODS = Arrays
      .asList(CAMPAIGN_PERIOD_EXPIRED, CAMPAIGN_PERIOD, CAMPAIGN_PERIOD_1);
  public static List<CampaignPeriod> CAMPAIGN_PERIODS_EXPIRED = Arrays
      .asList(CAMPAIGN_PERIOD_EXPIRED);
  public static PromoCodeAdjustment PROMO_CODE_ADJUSTMENT = new PromoCodeAdjustmentBuilder()
      .withId(ADJUSTMENT_ID).build();
  public static Campaign CAMPAIGN = new CampaignBuilder()
      .withId(CAMPAIGN_ID)
      .withPromoCodeAdjustmentId(ADJUSTMENT_ID)
      .withName(CAMPAIGN_NAME)
      .withCampaignPeriods(CAMPAIGN_PERIODS)
      .withCampaignStatus(CampaignStatus.ACTIVE)
      .build();

  public static  List<Campaign> CAMPAIGN_LIST = Arrays.asList(CAMPAIGN);

  public static PromoCode PROMO_CODE_CHECK = new PromoCodeBuilder()
      .withId(ID)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(PromoCodeTestVariable.USERNAME)
      .withUpdatedBy(PromoCodeTestVariable.USERNAME)
      .withCode(PromoCodeTestVariable.CODE)
      .withMaxQty(PromoCodeTestVariable.MAX_QTY)
      .withPromoCodeStatus(PromoCodeStatus.INACTIVE)
      .withCampaignId(CAMPAIGN_ID)
      .build();

  public static List<PromoCode> PROMO_CODE_LIST = Arrays.asList(PROMO_CODE_CHECK);

  public static PromoCode PROMO_CODE_CHECK_ACTIVE = new PromoCodeBuilder()
      .withId(ID)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(PromoCodeTestVariable.USERNAME)
      .withUpdatedBy(PromoCodeTestVariable.USERNAME)
      .withCode(PromoCodeTestVariable.CODE)
      .withMaxQty(PromoCodeTestVariable.MAX_QTY)
      .withPromoCodeStatus(PromoCodeStatus.ACTIVE)
      .withCampaignId(CAMPAIGN_ID)
      .build();

  public static List<PromoCode> PROMO_CODE_LIST_ACTIVE = Arrays.asList(PROMO_CODE_CHECK_ACTIVE);


  public static Campaign CAMPAIGN_EXPIRED = new CampaignBuilder()
      .withId(CAMPAIGN_ID)
      .withPromoCodeAdjustmentId(ADJUSTMENT_ID)
      .withName(CAMPAIGN_NAME)
      .withCampaignPeriods(CAMPAIGN_PERIODS_EXPIRED)
      .build();
  public static PromoCode PROMO_CODE = new PromoCodeBuilder()
      .withId(ID)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(PromoCodeTestVariable.USERNAME)
      .withUpdatedBy(PromoCodeTestVariable.USERNAME)
      .withCode(PromoCodeTestVariable.CODE)
      .withMaxQty(PromoCodeTestVariable.MAX_QTY)
      .withPromoCodeStatus(PromoCodeStatus.ACTIVE)
      .withCampaignId(CAMPAIGN_ID)
      .build();
  public static PromoCode PROMO_CODE_SPACE = new PromoCodeBuilder()
      .withId(ID)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(PromoCodeTestVariable.USERNAME)
      .withUpdatedBy(PromoCodeTestVariable.USERNAME)
      .withCode(PromoCodeTestVariable.CODE_SPACE)
      .withMaxQty(PromoCodeTestVariable.MAX_QTY)
      .withPromoCodeStatus(PromoCodeStatus.ACTIVE)
      .withCampaignId(CAMPAIGN_ID)
      .build();
  public static PromoCode PROMO_CODE_CREATE = new PromoCodeBuilder()
      .withStoreId(CommonTestVariable.STORE_ID)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUsername(CommonTestVariable.USERNAME)
//      .withCreatedBy(PromoCodeTestVariable.USERNAME)
//      .withUpdatedBy(PromoCodeTestVariable.USERNAME)
      .withCode(PromoCodeTestVariable.CODE)
      .withMaxQty(PromoCodeTestVariable.MAX_QTY)
      .withPromoCodeStatus(PromoCodeStatus.ACTIVE)
      .withCampaignId(CAMPAIGN_ID)
      .build();
  public static PromoCode PROMO_CODE_CREATE_SAVE_RESPONSE = new PromoCodeBuilder()
      .withId(ID)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUsername(CommonTestVariable.USERNAME)
      .withCreatedBy(PromoCodeTestVariable.USERNAME)
      .withUpdatedBy(PromoCodeTestVariable.USERNAME)
      .withCode(PromoCodeTestVariable.CODE)
      .withMaxQty(PromoCodeTestVariable.MAX_QTY)
      .withPromoCodeStatus(PromoCodeStatus.ACTIVE)
      .withCampaignId(CAMPAIGN_ID)
      .build();
  public static PromoCode PROMO_CODE_EXIST = new PromoCodeBuilder()
      .withId(ID)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(PromoCodeTestVariable.USERNAME)
      .withUpdatedBy(PromoCodeTestVariable.USERNAME)
      .withCode(PromoCodeTestVariable.CODE)
      .withMaxQty(PromoCodeTestVariable.MAX_QTY)
      .withPromoCodeStatus(PromoCodeStatus.ACTIVE)
      .withCampaignId(CAMPAIGN_ID_EXIST)
      .build();
  public static List<PromoCode> PROMO_CODES = Arrays.asList(PROMO_CODE, PROMO_CODE_EXIST);
  public static Page<PromoCode> PROMO_CODE_PAGE = new PageImpl<>(PROMO_CODES);
  public static PromoCode PROMO_CODE_REQUEST = new PromoCodeBuilder()
      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withCode(PromoCodeTestVariable.CODE)
      .withMaxQty(PromoCodeTestVariable.MAX_QTY)
      .withCampaignId(CAMPAIGN_ID)
      .build();
  public static PromoCode PROMO_CODE_CREATE_RESULT = new PromoCodeBuilder()
      .withId(ID)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(PromoCodeTestVariable.USERNAME)
      .withUpdatedBy(PromoCodeTestVariable.USERNAME)
      .withCode(PromoCodeTestVariable.CODE)
      .withMaxQty(PromoCodeTestVariable.MAX_QTY)
      .withPromoCodeStatus(PromoCodeStatus.ACTIVE)
      .withCampaignId(CAMPAIGN_ID)
      .build();
  public static List<PromoCode> PROMO_CODE_NULLS = Arrays.asList();
  public static Page<PromoCode> PROMO_CODE_PAGE_NULL = new PageImpl<>(PROMO_CODE_NULLS);


  public static PromoCodeLog PROMO_CODE_LOG = new PromoCodeLogBuilder()
      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withChannelId(CommonTestVariable.CHANNEL_ID)

      .withValue(PROMO_CODE)
      .build();

  public static final PromoCodeObject PROMO_CODE_OBJECT = new PromoCodeObjectBuilder()
      .withPromoCode(PROMO_CODE)
      .build();
}
