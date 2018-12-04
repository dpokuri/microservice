package com.tl.booking.promo.code.entity.constant.unit.test;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.common.rest.web.model.request.MandatoryRequestBuilder;
import java.util.Date;
import org.joda.time.DateTime;

public class CommonTestVariable {

  public static String BUSINESS_LOGIC_RESPONSE = "DATA_NOT_EXIST";
  public static String LANG = "ID";
  public static String STORE_ID = "123";
  public static String REQUEST_ID = "REQUEST_ID";
  public static String SERVICE_ID = "LOGIN";
  public static String CHANNEL_ID = "iOS";
  public static String USERNAME = "testuser";
  public static String CONTENT_TYPE = "application/json";
  public static MandatoryRequest MANDATORY_REQUEST = new MandatoryRequestBuilder()
      .withStoreId(STORE_ID).withChannelId(CHANNEL_ID).withRequestId(REQUEST_ID)
      .withServiceId(SERVICE_ID).withUsername(USERNAME).build();
  public static Date TODAY_DATE = new DateTime().plusDays(1).toDate();
}
