package com.tl.booking.image.entity.constant;

public interface CacheKey {
    /* Change {microservice-name} to micro service name ex : payment, promotion, member, login, etc
        String PREFIX = "com.tl.booking.promo.list.{microservice-name}";
        String SYSTEM_PARAMETER = PREFIX + "system-parameter";
    */

  String PREFIX = "com.tl.booking.promo.list.archetype-mongodb-";
  String SYSTEM_PARAMETER = PREFIX + "system-parameter";
  String PROMO_PAGE = PREFIX + "promo-page";
  String PROMO_CATEGORY = PREFIX + "promo-category";

}
