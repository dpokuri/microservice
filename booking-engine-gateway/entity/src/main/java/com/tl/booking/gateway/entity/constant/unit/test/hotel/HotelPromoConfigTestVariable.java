package com.tl.booking.gateway.entity.constant.unit.test.hotel;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tl.booking.gateway.entity.constant.enums.ResponseCode;
import com.tl.booking.gateway.entity.constant.fields.HotelPromoAggregateFields;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponseBuilder;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.Hotel.Area;
import com.tl.booking.gateway.entity.outbound.Hotel.AreaBuilder;
import com.tl.booking.gateway.entity.outbound.Hotel.City;
import com.tl.booking.gateway.entity.outbound.Hotel.CityBuilder;
import com.tl.booking.gateway.entity.outbound.Hotel.Country;
import com.tl.booking.gateway.entity.outbound.Hotel.CountryBuilder;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoAggregateResponseBuilder;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoConfig;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoConfigBuilder;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoConfigFindAllParams;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoConfigFindAllParamsBuilder;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoConfigRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoConfigRequestBuilder;
import com.tl.booking.gateway.entity.outbound.Hotel.Province;
import com.tl.booking.gateway.entity.outbound.Hotel.ProvinceBuilder;

import java.util.Collections;
import java.util.Map;
import org.springframework.data.domain.PageImpl;

@SuppressWarnings("unchecked")
public class HotelPromoConfigTestVariable {

  protected final static String ID = "abc";
  protected final static Integer HOTEL_ID = 1;
  protected final static Integer MIN_DISCOUNT_HOTEL_TONIGHT = 30;
  private final static String HOTEL_NAME = "Ibis";
  private final static String COUNTRY_ID = "id";
  private final static String COUNTRY_NAME = "Indonesia";
  private final static Integer PROVINCE_ID = 1;
  private final static String PROVINCE_NAME = "Jakarta";
  private final static Integer CITY_ID = 10;
  private final static String CITY_NAME = "Jakarta Selatan";
  private final static Integer AREA_ID = 100;
  private final static String AREA_NAME = "Pasar Minggu";

  private final static Country COUNTRY = new CountryBuilder().withId(COUNTRY_ID)
      .withName(COUNTRY_NAME).build();
  private final static Province PROVINCE = new ProvinceBuilder().withId(PROVINCE_ID)
      .withName(PROVINCE_NAME).build();
  private final static City CITY = new CityBuilder().withId(CITY_ID).withName(CITY_NAME).build();
  private final static Area AREA = new AreaBuilder().withId(AREA_ID).withName(AREA_NAME).build();

  private static RestResponsePage restResponsePage = new RestResponsePage();

  protected final static HotelPromoConfigRequest HOTEL_PROMO_CONFIG_REQUEST
      = new HotelPromoConfigRequestBuilder()
      .withHotelId(HOTEL_ID)
      .withMinDiscountHotelTonight(MIN_DISCOUNT_HOTEL_TONIGHT)
      .withHotelName(HOTEL_NAME)
      .withCountry(COUNTRY)
      .withProvince(PROVINCE)
      .withCity(CITY)
      .withArea(AREA)
      .build();

  protected final static HotelPromoConfig HOTEL_PROMO_CONFIG
      = new HotelPromoConfigBuilder()
      .withHotelId(HOTEL_ID)
      .withMinDiscountHotelTonight(MIN_DISCOUNT_HOTEL_TONIGHT)
      .withHotelName(HOTEL_NAME)
      .withCountry(COUNTRY)
      .withProvince(PROVINCE)
      .withCity(CITY)
      .withArea(AREA)
      .build();

  protected final static HotelPromoConfigFindAllParams HOTEL_PROMO_CONFIG_FIND_ALL_PARAMS = new
      HotelPromoConfigFindAllParamsBuilder().build();

  protected final static GatewayBaseResponse<Object>
      HOTEL_PROMO_CONFIG_RESPONSE_PAGE =
      new GatewayBaseResponseBuilder<RestResponsePage<Object>>()
          .withCode(ResponseCode.SUCCESS.getCode())
          .withMessage(ResponseCode.SUCCESS.getMessage())
          .withData(restResponsePage)
          .build();

  protected final static GatewayBaseResponse<Object>
      HOTEL_PROMO_CONFIG_RESPONSE =
      new GatewayBaseResponseBuilder<>()
          .withCode(ResponseCode.SUCCESS.getCode())
          .withMessage(ResponseCode.SUCCESS.getMessage())
          .withData(HOTEL_PROMO_CONFIG)
          .build();


  protected static String getHotelPromoConfigRequestJson() throws Exception {
    return new ObjectMapper().setSerializationInclusion(Include.NON_NULL)
        .writeValueAsString(HOTEL_PROMO_CONFIG_REQUEST);
  }

  protected static Map<String, String> getHotelHotelPromoConfigFindAllParamAsMap()
      throws Exception {
    return new ObjectMapper().setSerializationInclusion(Include.NON_NULL)
        .convertValue(HOTEL_PROMO_CONFIG_FIND_ALL_PARAMS, Map.class);
  }

  static {
    restResponsePage.setContent(Collections.singletonList(HOTEL_PROMO_CONFIG));
  }

}
