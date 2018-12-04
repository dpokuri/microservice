package com.tl.booking.promo.code.entity.constant;

public interface ApiPath {

  String BASE_PATH = "/tix-promocode/";
  String SYSTEM_PARAMETER = BASE_PATH + "/systemParameter";
  String APPLY_PROMO_CODE = "/apply";
  String UNAPPLY_PROMO_CODE = "/unapply";
  String CALCULATE_DISCOUNT = "/calculateDiscount";
  String CALCULATE_DISCOUNT_PAYMENT_VALIDATION = "/calculateDiscountWithoutPaymentValidation";
  String CHECK_VALID_PAYMENT = "/checkValidPayments";
  String FIND_ACTIVATE = "/findActivate";
  String PROMO_CODE_PATH = BASE_PATH + "/promoCodes";
  String VARIABLE_PATH = BASE_PATH + "/variables";
  String PROMO_CODE_ADJUSTMENT_PATH = BASE_PATH + "/promoCodeAdjustments";
  String CAMPAIGN_PATH = BASE_PATH + "/campaigns";
  String PRODUCT_TYPE_PATH = BASE_PATH + "/productTypes";
  String APPLY_PROMO_CODE_WITHOUT_PAYMENT_VALIDATION = "/applyWithoutPaymentValidation";
  String PROMO_CODE_USAGE_LOG = "/promoCodeUsageLog";
  String STORE_ID_PATH = BASE_PATH + "/storeIds";
  String CHANNEL_ID_PATH = BASE_PATH + "/channelIds";

  String BANK_PATH = BASE_PATH + "/banks";
  String PAYMENT_PATH = BASE_PATH + "/payments";
  String CARD_TYPE_PATH = BASE_PATH + "/cardTypes";
  String BIN_NUMBER = BASE_PATH + "/binNumbers";
  String BUSINESS_LOGIC_RESPONSE = BASE_PATH + "/businessLogicResponses";
  String BIN_NUMBER_PATH = BASE_PATH + "/binNumbers";
  String VARIABLE_SOURCE_PATH = BASE_PATH + "/variableSources";
  String PROMO_CODE_QUOTA = "/promoCodes/quota";
}
