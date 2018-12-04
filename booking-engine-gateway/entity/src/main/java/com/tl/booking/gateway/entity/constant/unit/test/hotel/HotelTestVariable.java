package com.tl.booking.gateway.entity.constant.unit.test.hotel;

import com.tl.booking.gateway.entity.constant.enums.ResponseCode;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponseBuilder;
import com.tl.booking.gateway.entity.outbound.Hotel.FindHotelByAddressParam;
import com.tl.booking.gateway.entity.outbound.Hotel.FindHotelByAddressParamBuilder;
import com.tl.booking.gateway.entity.outbound.Hotel.FindHotelByHotelIdParam;
import com.tl.booking.gateway.entity.outbound.Hotel.FindHotelByHotelIdParamBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HotelTestVariable {

  protected static final String COUNTRY_ID = "id";
  protected static final String PROVINCE_ID = "13";
  protected static final String CITY_ID = "176";
  protected static final String AREA_ID = "0";
  protected static final String HOTEL_NAME = "Ibis";
  protected static final List<Integer> HOTEL_ID = Arrays.asList(71, 366);

  protected static final String HOTEL_REQUEST_JSON_DUMMY = "{\"hotelId\": " + HOTEL_ID + "}";

  private static Map<String, String> responseMap = new HashMap<>();

  protected static final FindHotelByAddressParam FIND_HOTEL_BY_ADDRESS_PARAM =
      new FindHotelByAddressParamBuilder()
          .withCountryId(COUNTRY_ID).withProvinceId(PROVINCE_ID).withCityId(CITY_ID)
          .withAreaId(AREA_ID)
          .build();

  protected static final FindHotelByHotelIdParam FIND_HOTEL_BY_HOTEL_ID_PARAM =
      new FindHotelByHotelIdParamBuilder()
          .withHotelId(HOTEL_ID)
          .build();

  static {
    responseMap.put("name", HOTEL_NAME);
  }

  protected static final GatewayBaseResponse<List<Map<String, String>>> HOTEL_RESPONSE_DUMMY =
      new GatewayBaseResponseBuilder<List<Map<String, String>>>()
          .withCode(ResponseCode.SUCCESS.getCode())
          .withData(Collections.singletonList(responseMap))
          .build();
}
