package com.tl.booking.gateway.entity.constant.unit.test.hotel;

import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.Hotel.AdditionalDiscountRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.AdditionalDiscountRequestBuilder;
import com.tl.booking.gateway.entity.outbound.Hotel.AdditionalDiscountResponse;
import com.tl.booking.gateway.entity.outbound.Hotel.AdditionalDiscountResponseBuilder;

import java.util.Arrays;

public class AdditionalDiscountTestVariable {

  public static String ID = "1";
  public static Integer PAGE = 0;
  public static Integer LIMIT = 20;
  public static Integer HOTEL_ID = 0;
  public static String TYPE = "String";
  public static Integer ACTIVE = 1;
  public static double MIN_DISCOUNT = 0;
  public static double VALUE_DISCOUNT = 0;

  public static AdditionalDiscountRequest REQUEST = new AdditionalDiscountRequestBuilder()
      .withHotelId(HOTEL_ID)
      .withMinDiscount(MIN_DISCOUNT)
      .withValueDiscount(VALUE_DISCOUNT)
      .withType(TYPE)
      .withActive(ACTIVE)
      .build();


  private static AdditionalDiscountResponse DATA = new AdditionalDiscountResponseBuilder()
      .build();
  public static GatewayBaseResponse<AdditionalDiscountResponse> RESULT = getResult();
  public static RestResponsePage<AdditionalDiscountResponse> DATA_PAGE = getDataPage();
  public static GatewayBaseResponse<RestResponsePage<AdditionalDiscountResponse>> RESULT_PAGE = getResultPage();

  private static GatewayBaseResponse<AdditionalDiscountResponse> getResult() {
    GatewayBaseResponse<AdditionalDiscountResponse> result = new GatewayBaseResponse<>();
    result.setCode("SUCCESS");
    result.setData(DATA);

    return result;
  }

  private static RestResponsePage<AdditionalDiscountResponse> getDataPage() {
    RestResponsePage<AdditionalDiscountResponse> data_page = new RestResponsePage<>();
    data_page.setTotalPages(10);
    data_page.setContent(Arrays.asList(DATA));

    return data_page;
  }

  private static GatewayBaseResponse<RestResponsePage<AdditionalDiscountResponse>> getResultPage() {
    GatewayBaseResponse<RestResponsePage<AdditionalDiscountResponse>> result = new GatewayBaseResponse<>();
    result.setCode("SUCCESS");
    result.setData(DATA_PAGE);

    return result;
  }

  public static String REQUEST_JSON = "{" +
      " \"hotelId\" :" + HOTEL_ID +
      ",\"minDiscount\" :" + MIN_DISCOUNT +
      ",\"valueDiscount\" :" + VALUE_DISCOUNT +
      ",\"type\": \"" + TYPE + "\"" +
      ",\"active\":" + ACTIVE +
      '}';


}
