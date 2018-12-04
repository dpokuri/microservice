package com.tl.booking.promo.code.entity.constant;

public interface CacheKey {
    /* Change {microservice-name} to micro service name ex : payment, promotion, member, login, etc
        String PREFIX = "com.tl.booking.{microservice-name}";
        String SYSTEM_PARAMETER = PREFIX + "system-parameter";
    */

  String PREFIX = "com.tl.booking.promo.code-";
  String SYSTEM_PARAMETER = PREFIX + "system-parameter";
  String PROMO_CODE = PREFIX + "promo-code";
  String PROMO_CODE_ADJUSTMENT = PREFIX + "promo-code-adjustment";
  String CAMPAIGN = PREFIX + "campaign";
  String VARIABLE = PREFIX + "variable";
  String VARIABLE_MAPPED = PREFIX + "variableMapped";
  String PRODUCT_TYPE = PREFIX + "productType";
  String CURRENT_QUOTA = PREFIX + "currentQuota";

  String BANK = PREFIX + "bank";
  String PAYMENT = PREFIX + "payment";
  String CARD_TYPE = PREFIX + "cardType";
  String STORE_ID = PREFIX + "storeId";
  String CHANNEL_ID = PREFIX + "channelId";
  String BUSINESS_LOGIC_RESPONSE = PREFIX + "businessLogicResponse";
  String BIN_NUMBER = PREFIX + "binNumber";
  String VARIABLE_SOURCE = "variableSource";
}
