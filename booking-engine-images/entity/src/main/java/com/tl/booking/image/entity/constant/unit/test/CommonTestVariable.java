package com.tl.booking.image.entity.constant.unit.test;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.common.rest.web.model.request.MandatoryRequestBuilder;

public interface CommonTestVariable {

  String STORE_ID = "TIKETCOM";
  String REQUEST_ID = "REQUEST_ID";
  String SERVICE_ID = "LOGIN";
  String CHANNEL_ID = "iOS";
  String USERNAME = "testuser";
  String CONTENT_TYPE = "application/json";
  public static MandatoryRequest MANDATORY_REQUEST = new MandatoryRequestBuilder()
      .withStoreId(STORE_ID).withChannelId(CHANNEL_ID).withRequestId(REQUEST_ID)
      .withServiceId(SERVICE_ID).withUsername(USERNAME).build();
}
