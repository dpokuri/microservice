package com.tl.booking.gateway.entity.constant.unit.test.hotel;

import com.tl.booking.gateway.entity.constant.enums.ResponseCode;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponseBuilder;
import com.tl.booking.gateway.entity.outbound.Hotel.Address;
import com.tl.booking.gateway.entity.outbound.Hotel.AddressBuilder;

import java.util.Collections;
import java.util.List;

public class AddressTestVariable {

  protected static final String COUNTRY_ID = "id";
  protected static final String PROVINCE_ID = "13";
  protected static final String CITY_ID = "176";
  protected static final String COUNTRY_NAME = "Indonesia";

  protected static final GatewayBaseResponse<List<Address>> addressResponseDummy = new GatewayBaseResponseBuilder<List<Address>>()
      .withCode(ResponseCode.SUCCESS.getCode())
      .withMessage(ResponseCode.SUCCESS.getMessage())
      .withData(Collections
          .singletonList(new AddressBuilder().withId(COUNTRY_ID).withName(COUNTRY_NAME).build()))
      .withServerTime(null)
      .build();
}
