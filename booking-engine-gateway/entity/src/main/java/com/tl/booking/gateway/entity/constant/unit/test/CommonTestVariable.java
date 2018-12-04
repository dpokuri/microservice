package com.tl.booking.gateway.entity.constant.unit.test;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.common.rest.web.model.request.MandatoryRequestBuilder;
import com.tl.booking.common.rest.web.model.response.BaseResponse;
import com.tl.booking.common.rest.web.model.response.CommonResponse;
import com.tl.booking.gateway.entity.constant.enums.ResponseCode;
import com.tl.booking.gateway.entity.constant.fields.MandatoryFields;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponseBuilder;
import com.tl.booking.gateway.entity.outbound.PrivilegeResponse;
import com.tl.booking.gateway.entity.outbound.PrivilegeResponseBuilder;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.SessionDataBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CommonTestVariable {

  String STORE_ID = "TIKETCOM";
  String REQUEST_ID = "REQUEST_ID";
  String SERVICE_ID = "LOGIN";
  String CHANNEL_ID = "iOS";
  String USERNAME = "testuser";
  String CONTENT_TYPE = "application/json";
  public static String MM_MODULE = "207";

  MandatoryRequest MANDATORY_REQUEST = new MandatoryRequestBuilder()
      .withStoreId(STORE_ID)
      .withRequestId(REQUEST_ID)
      .withServiceId(SERVICE_ID)
      .withChannelId(CHANNEL_ID)
      .withUsername(USERNAME)
      .build();

  SessionData SESSION_DATA = new SessionDataBuilder()
      .withUsername(USERNAME).build();

  BaseResponse BASE_RESPONSE = CommonResponse.constructResponse(ResponseCode.SUCCESS.getCode(),
      ResponseCode.SUCCESS.getMessage(), null, null);

  @SuppressWarnings("unchecked")
  GatewayBaseResponse GATEWAY_BASE_RESPONSE = new GatewayBaseResponseBuilder()
      .withCode(ResponseCode.SUCCESS.getCode())
      .withMessage(ResponseCode.SUCCESS.getMessage())
      .withErrors(null)
      .withData(null)
      .build();

  static Map<String, String> getMandatoryRequestAsMap() {
    Map<String, String> mandatoryRequestMap = new HashMap<>();
    mandatoryRequestMap.put(MandatoryFields.STORE_ID, STORE_ID);
    mandatoryRequestMap.put(MandatoryFields.CHANNEL_ID, CHANNEL_ID);
    mandatoryRequestMap.put(MandatoryFields.REQUEST_ID, REQUEST_ID);
    mandatoryRequestMap.put(MandatoryFields.SERVICE_ID, SERVICE_ID);
    mandatoryRequestMap.put(MandatoryFields.USERNAME, USERNAME);

    return mandatoryRequestMap;
  }

  static List<PrivilegeResponse> getPrivilegeResponse(){
    List<PrivilegeResponse> privilegeResponses = new ArrayList<>();
    privilegeResponses.add(
        new PrivilegeResponseBuilder()
            .withPrivilegeId("300")
            .withPrivilegeName("coba")
            .build());

    privilegeResponses.add(
        new PrivilegeResponseBuilder()
            .withPrivilegeId("301")
            .withPrivilegeName("coba1")
            .build());
    return privilegeResponses;
  }

  List<PrivilegeResponse> PRIVILEGE_RESPONSE = getPrivilegeResponse();
}
