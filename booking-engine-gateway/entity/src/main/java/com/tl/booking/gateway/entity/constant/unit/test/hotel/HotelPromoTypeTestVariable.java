package com.tl.booking.gateway.entity.constant.unit.test.hotel;

import com.tl.booking.gateway.entity.constant.enums.ResponseCode;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponseBuilder;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoTypeRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoTypeRequestBuilder;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoTypeResponse;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoTypeResponseBuilder;

import java.util.Collections;
import java.util.List;

public class HotelPromoTypeTestVariable {

  public static final String ID = "abc";
  public static final Integer ACTIVE = 1;

  protected static final GatewayBaseResponse<List<HotelPromoTypeResponse>>
      HOTEL_PROMO_TYPE_RESPONSE_LIST_DUMMY = new GatewayBaseResponseBuilder<List<HotelPromoTypeResponse>>()
      .withCode(ResponseCode.SUCCESS.getCode())
      .withData(
          Collections.singletonList(new HotelPromoTypeResponseBuilder().withId("abc").build()))
      .build();

  protected static final GatewayBaseResponse<HotelPromoTypeResponse> HOTEL_PROMO_TYPE_RESPONSE_DUMMY =
      new GatewayBaseResponseBuilder<HotelPromoTypeResponse>()
          .withCode(ResponseCode.SUCCESS.getCode())
          .withData(new HotelPromoTypeResponseBuilder().withId("abc").build())
          .build();

  protected static final GatewayBaseResponse<HotelPromoTypeResponse>
      HOTEL_PROMO_TYPE_BASE_RESPONSE_DUMMY = new GatewayBaseResponseBuilder<HotelPromoTypeResponse>()
      .withCode(ResponseCode.SUCCESS.getCode())
      .withData(null)
      .build();

  protected static final HotelPromoTypeRequest HOTEL_PROMO_TYPE_REQUEST_DUMMY =
      new HotelPromoTypeRequestBuilder()
          .withActive(ACTIVE)
          .build();

  protected static final String HOTEL_PROMO_TYPE_REQUEST_JSON_DUMMY =
      "{\"active\": " + ACTIVE + "}";
}
