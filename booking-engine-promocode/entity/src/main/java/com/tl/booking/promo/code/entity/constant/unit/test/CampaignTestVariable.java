package com.tl.booking.promo.code.entity.constant.unit.test;

import com.tl.booking.promo.code.entity.CampaignDropdown;
import com.tl.booking.promo.code.entity.CampaignDropdownBuilder;
import com.tl.booking.promo.code.entity.CampaignPeriod;
import com.tl.booking.promo.code.entity.CampaignPeriodBuilder;
import com.tl.booking.promo.code.entity.constant.CacheKey;
import com.tl.booking.promo.code.entity.constant.enums.CampaignStatus;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeAdjustmentStatus;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeStatus;
import com.tl.booking.promo.code.entity.dao.Campaign;
import com.tl.booking.promo.code.entity.dao.CampaignBuilder;
import com.tl.booking.promo.code.entity.dao.PromoCode;
import com.tl.booking.promo.code.entity.dao.PromoCodeAdjustment;
import com.tl.booking.promo.code.entity.dao.PromoCodeAdjustmentBuilder;
import com.tl.booking.promo.code.entity.dao.log.CampaignLog;
import com.tl.booking.promo.code.entity.dao.log.CampaignLogBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class CampaignTestVariable {

  public static String ID = "12345";
  public static String NAME = "yonathan";
  public static String NAME_2 = "anji";
  public static String CODE = "1234567";
  public static String ADJUSTMENT_ID = "1234567abcdfgt";
  public static String CAMPAIGN_ID = "campaign1234567";
  public static Integer IS_DELETED = 0;
  public static String NAME_OTHER = "anji";
  public static Integer PAGE = 0;
  public static Integer SIZE = 10;

  public static String CACHE_KEY = CacheKey.CAMPAIGN + "-" + CommonTestVariable.STORE_ID + "-" + ID;
  public static String CACHE_KEY_FIND_ACTIVE =
      CacheKey.CAMPAIGN + "-" + CommonTestVariable.STORE_ID + "-" + CampaignStatus.ACTIVE;

  public static String CACHE_KEY_FIND_ADJUSTMENT_ID =  CacheKey.CAMPAIGN + "-adjustment-" + CommonTestVariable.STORE_ID + "-" + ADJUSTMENT_ID;



  public static PromoCodeAdjustment PROMO_CODE_ADJUSTMENT = new PromoCodeAdjustmentBuilder()
      .withId(ADJUSTMENT_ID)
      .build();
  public static PromoCodeAdjustment PROMO_CODE_ADJUSTMENT_ACTIVE = new PromoCodeAdjustmentBuilder()
      .withId(ADJUSTMENT_ID)
      .withPromoCodeAdjustmentStatus(PromoCodeAdjustmentStatus.ACTIVE)
      .build();

  public static CampaignPeriod CAMPAIGN_PERIOD = new CampaignPeriodBuilder()
      .withStartDate(new Date())
      .withEndDate(new Date())
      .build();

  public static List<CampaignPeriod> CAMPAIGN_PERIODS = Arrays.asList(CAMPAIGN_PERIOD);

  public static Campaign CAMPAIGN_REQUEST = new CampaignBuilder()

      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withIsDeleted(IS_DELETED)

      .withId(ID)
      .withName(NAME)
      .withCode(CODE)
      .withPromoCodeAdjustmentId(ADJUSTMENT_ID)
      .withCampaignPeriods(CAMPAIGN_PERIODS)
      .withCampaignStatus(CampaignStatus.DRAFT)

      .build();

  public static Campaign CAMPAIGN_REQUEST_ACTIVE = new CampaignBuilder()

      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withIsDeleted(IS_DELETED)

      .withId(ID)
      .withName(NAME)
      .withCode(CODE)
      .withPromoCodeAdjustmentId(ADJUSTMENT_ID)
      .withCampaignPeriods(CAMPAIGN_PERIODS)
      .withCampaignStatus(CampaignStatus.ACTIVE)

      .build();

  public static Campaign CAMPAIGN_FIND = new CampaignBuilder()

      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withIsDeleted(IS_DELETED)

      .withId(ID)
      .withName(NAME)
      .withCode(CODE)
      .withPromoCodeAdjustmentId(ADJUSTMENT_ID)
      .withCampaignPeriods(CAMPAIGN_PERIODS)
      .withCampaignStatus(CampaignStatus.DRAFT)

      .build();

  public static Campaign CAMPAIGN_REQUEST_CREATE = new CampaignBuilder()
      .withUsername(CommonTestVariable.USERNAME)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withIsDeleted(IS_DELETED)

      .withName(NAME)
      .withCode(CODE)
      .withPromoCodeAdjustmentId(ADJUSTMENT_ID)
      .withCampaignPeriods(CAMPAIGN_PERIODS)

      .build();

  public static Campaign CAMPAIGN_REQUEST_CREATE_2 = new CampaignBuilder()
      .withId(ID)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withIsDeleted(IS_DELETED)

      .withName(NAME)
      .withCode(CODE)
      .withPromoCodeAdjustmentId(ADJUSTMENT_ID)
      .withCampaignPeriods(CAMPAIGN_PERIODS)

      .build();


  public static Campaign CAMPAIGN_REQUEST_NAME_OTHER_NOT_EXIST = new CampaignBuilder()

      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withIsDeleted(IS_DELETED)

      .withId(ID)
      .withName(NAME_OTHER)
      .withCode(CODE)
      .withPromoCodeAdjustmentId(ADJUSTMENT_ID)
      .withCampaignPeriods(CAMPAIGN_PERIODS)
      .withCampaignStatus(CampaignStatus.DRAFT)

      .build();

  public static Campaign CAMPAIGN_REQUEST_UPDATED_NAME = new CampaignBuilder()

      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withIsDeleted(IS_DELETED)

      .withId(ID)
      .withName(NAME_2)
      .withCode(CODE)
      .withPromoCodeAdjustmentId(ADJUSTMENT_ID)
      .withCampaignPeriods(CAMPAIGN_PERIODS)
      .withCampaignStatus(CampaignStatus.DRAFT)

      .build();

  public static Campaign CAMPAIGN_RESPONSE = new CampaignBuilder()
      .withId(ID)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withIsDeleted(IS_DELETED)

      .withName(NAME)
      .withCode(CODE)
      .withPromoCodeAdjustmentId(ADJUSTMENT_ID)
      .withCampaignPeriods(CAMPAIGN_PERIODS)
      .withCampaignStatus(CampaignStatus.DRAFT)

      .build();

  public static Campaign CAMPAIGN_RESPONSE_PENDING = new CampaignBuilder()
      .withId(ID)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withIsDeleted(IS_DELETED)

      .withName(NAME)
      .withCode(CODE)
      .withPromoCodeAdjustmentId(ADJUSTMENT_ID)
      .withCampaignPeriods(CAMPAIGN_PERIODS)
      .withCampaignStatus(CampaignStatus.PENDING)

      .build();

  public static Campaign CAMPAIGN_DELETE = new CampaignBuilder()
      .withId(ID)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withIsDeleted(IS_DELETED)

      .withName(NAME)
      .withCode(CODE)
      .withPromoCodeAdjustmentId(ADJUSTMENT_ID)
      .withCampaignPeriods(CAMPAIGN_PERIODS)
      .withCampaignStatus(CampaignStatus.DRAFT)

      .build();

  public static List<Campaign> CAMPAIGN_LIST = Arrays.asList(CAMPAIGN_REQUEST, CAMPAIGN_RESPONSE);

  public static List<Campaign> CAMPAIGN_LIST_ACTIVE = Arrays.asList(CAMPAIGN_REQUEST_ACTIVE);


  public static Page<Campaign> CAMPAIGN_PAGE = new PageImpl<>(CAMPAIGN_LIST);

  public static List<Campaign> CAMPAIGN_NULLS = Arrays.asList();

  public static Page<Campaign> CAMPAIGN_PAGE_NULL = new PageImpl<>(CAMPAIGN_NULLS);

  public static CampaignDropdown CAMPAIGN_DROPDOWN_1 = new CampaignDropdownBuilder()
      .withId(ID)
      .withName(NAME)

      .build();


  public static List<CampaignDropdown> CAMPAIGN_DROPDOWN_LIST = Arrays
      .asList(CAMPAIGN_DROPDOWN_1, CAMPAIGN_DROPDOWN_1);


  public static String CAMPAIGN_REQUEST_BODY =
      "{\"campaignPeriods\":[{\"endDate\":\"2018-01-11 17:08:32\","
          + "\"startDate\":\"2018-01-11 17:08:32\"}],\"code\":\"" + CODE + "\",\"name\":\"" + NAME
          + "\",\"promoCodeAdjustmentId\":\"" + ADJUSTMENT_ID + "\"}";

  public static Campaign CAMPAIGN = new CampaignBuilder()
      .withId(ID)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withIsDeleted(IS_DELETED)

      .withName(NAME)
      .withCode(CODE)
      .withPromoCodeAdjustmentId(ADJUSTMENT_ID)
      .withCampaignPeriods(CAMPAIGN_PERIODS)
      .withCampaignStatus(CampaignStatus.DRAFT)

      .build();



  public static CampaignLog CAMPAIGN_LOG = new CampaignLogBuilder()
      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withChannelId(CommonTestVariable.CHANNEL_ID)

      .withValue(CAMPAIGN)
      .build();

  public static PromoCode setPromoCodeInactive() {
    PromoCode promoCode = new PromoCode();
    promoCode.setCampaignId(CAMPAIGN_ID);
    promoCode.setPromoCodeStatus(PromoCodeStatus.INACTIVE);
    return promoCode;
  }

  public static List<PromoCode> PROMO_CODE_LIST = Arrays.asList(setPromoCodeInactive());
  public static List<PromoCode> PROMO_CODE_LIST_NULL = new ArrayList<>();

}
