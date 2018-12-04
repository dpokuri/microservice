package com.tl.booking.gateway.entity.constant;

public interface ApiPath {

  String BASE_PATH = "/tix-gateway";
  String SYSTEM_PARAMETER = BASE_PATH + "/systemParameters";
  String ID = "/{id}";
  String HOLIDAY = BASE_PATH + "/holiday";
  String HOTEL_SCRAPPING = BASE_PATH + "/hotel-scrapping";
  String OTA = HOTEL_SCRAPPING + "/ota";
  String AUTO_COMPLETE_HOTEL = HOTEL_SCRAPPING + "/autocomplete";
  String SUBSIDY = BASE_PATH + "/hotel-subsidy";
  String ADDRESS_COUNTRY = SUBSIDY + "/country";
  String ADDRESS_CITY = SUBSIDY + "/city";
  String ADDRESS_AREA = SUBSIDY + "/area";
  String HOTEL = SUBSIDY + "/hotel";
  String HOTEL_BY_ADDRESS = SUBSIDY + "/hotel-by-address";
  String PIC_REGION = HOTEL_SCRAPPING + "/pic";
  String REGION_MAPPING = HOTEL_SCRAPPING + "/region";
  String REPORT = HOTEL_SCRAPPING + "/report";
  String HOTEL_MAPPING = HOTEL_SCRAPPING + "/hotel";
  String PREFIX_PROMO_CATEGORY = "/tix-promolist";
  String SUFFIX_PROMO_CATEGORY = "/promoCategories";
  String END_POINT_PROMO_CATEGORY = PREFIX_PROMO_CATEGORY + SUFFIX_PROMO_CATEGORY;
  String PREFIX_PROMO_PAGE = "/tix-promolist";
  String SUFFIX_PROMO_PAGE = "/promoPages";
  String END_POINT_PROMO_PAGE = PREFIX_PROMO_PAGE + SUFFIX_PROMO_PAGE;
  String PROMO_CATEGORY = BASE_PATH + SUFFIX_PROMO_CATEGORY;
  String PROMO_PAGE = BASE_PATH + SUFFIX_PROMO_PAGE;
  String URL_COUNTRY = "/hotel-subsidy/country";
  String ADDRESS_PROVINCE = SUBSIDY + "/province";
  String URL_PROVINCE = "/hotel-subsidy/province";
  String IMAGES = BASE_PATH + "/uploadImages";
  String URL_CITY = "/hotel-subsidy/city";
  String URL_AREA = "/hotel-subsidy/area";
  String URL_HOTEL_BY_ADDRESS =  "/hotel-subsidy/hotel-by-address";
  String URL_HOTEL_NAME_BY_HOTEL_IDS =  "/hotel-subsidy/hotel";
  String END_POINT_INTERNAL_SUBSIDY = "/hotel-subsidy/subsidy";
  String INTERNAL_SUBSIDY = BASE_PATH + "/hotel-subsidy/subsidy";

  String HOTEL_AGGREGATE = "/hotel-promo";
  String HOTEL_PROMO_AGGREGATE = BASE_PATH + HOTEL_AGGREGATE + "/promo";
  String HOTEL_PROMO_TYPE = BASE_PATH + HOTEL_AGGREGATE + "/promo/type";
  String URL_ADDITIONAL_DISCOUNT = BASE_PATH + HOTEL_AGGREGATE + "/promo/additional";
  String HOTEL_DETAIL = BASE_PATH + HOTEL_AGGREGATE;
  String HOTEL_POLICY = "/promo/policy";
  String HOTEL_ROOM_LIST = "/promo/room";
  String END_POINT_ADDITIONAL_DISCOUNT = HOTEL_AGGREGATE + "/promo/additional";
  String END_POINT_HOTEL_PROMO_AGGREGATE = HOTEL_AGGREGATE + "/promo";
  String END_POINT_HOTEL_PROMO_TYPE = HOTEL_AGGREGATE + "/promo/type";
  String END_POINT_HOTEL_POLICY = HOTEL_AGGREGATE + "/promo/policy";
  String END_POINT_HOTEL_ROOM = HOTEL_AGGREGATE + "/promo/room";

  String FIND_ACTIVATE = "/findActivate";

  String SUFFIX_PROMO_CODE = "/promoCodes";
  String PROMO_CODE = BASE_PATH + SUFFIX_PROMO_CODE;

  String PREFIX_PROMO_CODE = "/tix-promocode";
  String END_POINT_PROMO_CODE = PREFIX_PROMO_CODE + SUFFIX_PROMO_CODE;


  String SUFFIX_CAMPAIGN = "/campaigns";
  String CAMPAIGN = BASE_PATH + SUFFIX_CAMPAIGN;

  String END_POINT_CAMPAIGN = PREFIX_PROMO_CODE + SUFFIX_CAMPAIGN;

  String SUFFIX_PROMO_ADJUSTMENT = "/promoCodeAdjustments";
  String PROMO_ADJUSTMENT = BASE_PATH + SUFFIX_PROMO_ADJUSTMENT;

  String END_POINT_PROMO_ADJUSTMENT = PREFIX_PROMO_CODE + SUFFIX_PROMO_ADJUSTMENT;

  String SUFFIX_VARIABLE = "/variables";
  String VARIABLE = BASE_PATH + SUFFIX_VARIABLE;

  String END_POINT_VARIABLE = PREFIX_PROMO_CODE + SUFFIX_VARIABLE;


  String SUFFIX_PRODUCT_TYPE = "/productTypes";
  String PRODUCT_TYPE = BASE_PATH + SUFFIX_PRODUCT_TYPE;

  String END_POINT_PRODUCT_TYPE = PREFIX_PROMO_CODE + SUFFIX_PRODUCT_TYPE;


  String SUFFIX_BIN_NUMBER = "/binNumbers";
  String BIN_NUMBER = BASE_PATH + SUFFIX_BIN_NUMBER;

  String END_POINT_BIN_NUMBER = PREFIX_PROMO_CODE + SUFFIX_BIN_NUMBER;

  String SUFFIX_BANK = "/banks";
  String BANK = BASE_PATH + SUFFIX_BANK;

  String END_POINT_BANK = PREFIX_PROMO_CODE + SUFFIX_BANK;

  String SUFFIX_PAYMENT = "/payments";
  String PAYMENT = BASE_PATH + SUFFIX_PAYMENT;

  String END_POINT_PAYMENT = PREFIX_PROMO_CODE + SUFFIX_PAYMENT;

  String SUFFIX_STORE_ID = "/storeIds";
  String STORE_ID = BASE_PATH + SUFFIX_STORE_ID;

  String END_POINT_STORE_ID = PREFIX_PROMO_CODE + SUFFIX_STORE_ID;


  String SUFFIX_CARD_TYPE = "/cardTypes";
  String CARD_TYPE = BASE_PATH + SUFFIX_CARD_TYPE;

  String END_POINT_CARD_TYPE = PREFIX_PROMO_CODE + SUFFIX_CARD_TYPE;


  String SUFFIX_CHANNEL_ID = "/channelIds";
  String CHANNEL_ID = BASE_PATH + SUFFIX_CHANNEL_ID;

  String END_POINT_CHANNEL_ID = PREFIX_PROMO_CODE + SUFFIX_CHANNEL_ID;


  String SUFFIX_BUSINESS_LOGIC = "/businessLogicResponses";
  String BUSINESS_LOGIC = BASE_PATH + SUFFIX_BUSINESS_LOGIC;

  String END_POINT_BUSINESS_LOGIC = PREFIX_PROMO_CODE + SUFFIX_BUSINESS_LOGIC;

  String SUFFIX_VARIABLE_SOURCE = "/variableSources";
  String VARIABLE_SOURCE = BASE_PATH + SUFFIX_VARIABLE_SOURCE;

  String END_POINT_VARIABLE_SOURCE = PREFIX_PROMO_CODE + SUFFIX_VARIABLE_SOURCE;

  //API PATH Flight RME
  String PREFIX_FLIGHT_RME = "tix-flight-rme";

  String SUFFIX_ADJUSTMENT_RULE = "/adjustmentRules";
  String ADJUSTMENT_RULE = BASE_PATH + SUFFIX_ADJUSTMENT_RULE;
  String END_POINT_ADJUSTMENT_RULE = PREFIX_FLIGHT_RME + ADJUSTMENT_RULE;

  String SUFFIX_ADJUSTMENT_DETAIL = "/adjustmentDetails";
  String ADJUSTMENT_DETAIL = BASE_PATH + SUFFIX_ADJUSTMENT_DETAIL;
  String END_POINT_ADJUSTMENT_DETAIL = PREFIX_FLIGHT_RME + ADJUSTMENT_DETAIL;

  String HOTEL_PROMO_CONFIG = BASE_PATH + "/promo/config";
  String SLUG_HOTEL_PROMO_CONFIG = "/hotel-aggregate/promo/config";
  String SESSION = BASE_PATH + "/sessions";

}
