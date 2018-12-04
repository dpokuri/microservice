package com.tl.booking.gateway.entity.constant;

public interface CacheKey {
    /* Change {microservice-name} to micro service name ex : payment, promotion, member, login, etc
        String PREFIX = "com.tl.booking.{microservice-name}";
        String SYSTEM_PARAMETER = PREFIX + "system-parameter";
    */

  String PREFIX = "com.tl.booking.gateway-";
  String SYSTEM_PARAMETER = PREFIX + "system-parameter";
}
