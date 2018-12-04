package com.tl.booking.gateway.entity.constant.unit.test.hotel;

import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponseBuilder;
import com.tl.booking.gateway.entity.outbound.Hotel.OtaRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.OtaRequestBuilder;
import com.tl.booking.gateway.entity.outbound.Hotel.OtaResponse;
import com.tl.booking.gateway.entity.outbound.Hotel.OtaResponseBuilder;

import java.util.Arrays;
import java.util.List;

public class HotelOtaTestVariable {

  public static String ID = "1";
  public static String NAME = "Tiket.com";
  public static String ENDPOINT = "tiket";

  public static OtaResponse DATA = new OtaResponseBuilder()
      .withId(ID)
      .withName(NAME)
      .withEndpoint(ENDPOINT)
      .withActive(1)
      .build();

  public static GatewayBaseResponse<List<OtaResponse>> RESPONSE_LIST = new GatewayBaseResponseBuilder<List<OtaResponse>>()
      .withCode("SUCCESS")
      .withMessage("")
      .withData(Arrays.asList(DATA))
      .build();

  public static GatewayBaseResponse<OtaResponse> RESPONSE = new GatewayBaseResponseBuilder<OtaResponse>()
      .withCode("SUCCESS")
      .withMessage("")
      .withData(DATA)
      .build();

  public static OtaRequest REQUEST = new OtaRequestBuilder()
      .withName(NAME)
      .withEndpoint(ENDPOINT)
      .withActive(1)
      .build();

  public static GatewayBaseResponse<Boolean> BASE_RESPONSE_BOOLEAN = getGatewayBaseResponseBoolean();

  private static GatewayBaseResponse<Boolean> getGatewayBaseResponseBoolean(){
    GatewayBaseResponse<Boolean> booleanGatewayBaseResponse = new GatewayBaseResponse<>();
    booleanGatewayBaseResponse.setCode("SUCCESS");
    booleanGatewayBaseResponse.setData(true);

    return booleanGatewayBaseResponse;
  }


  public static String OTA_REQUEST = "{"
      + "\"active\":1,"
      +"\"endpoint\":\""+ENDPOINT+"\","
      + "\"name\":\"" + NAME + "\""
      + "}";

}
