package com.tl.booking.gateway.entity.constant.unit.test.hotel;

import com.tl.booking.gateway.entity.constant.enums.ResponseCode;
import com.tl.booking.gateway.entity.constant.fields.HotelPromoAggregateFields;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponseBuilder;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.Hotel.FindAllPromoParam;
import com.tl.booking.gateway.entity.outbound.Hotel.FindAllPromoParamBuilder;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoAggregateRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoAggregateRequestBuilder;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoAggregateResponse;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoAggregateResponseBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class HotelAggregateTestVariable {

  public static final String ID = "abc";
  public static final Integer PAGE = 0;
  protected static final Integer LIMIT = 20;
  protected static final String HOTEL_ID = "13";
  protected static final String ROOM_ID = "12";
  protected static final String START_DATE = "2018-01-01";
  protected static final String END_DATE = "2018-10-10";

  protected static Map<String, String> params = new HashMap<>();

  private static RestResponsePage restResponsePage = new RestResponsePage();
  protected static final GatewayBaseResponse<RestResponsePage<HotelPromoAggregateResponse>>
      hotelPromoAggregateResponsePageDummy =
      new GatewayBaseResponseBuilder<RestResponsePage<HotelPromoAggregateResponse>>()
          .withCode(ResponseCode.SUCCESS.getCode())
          .withMessage(ResponseCode.SUCCESS.getMessage())
          .withData(restResponsePage)
          .withServerTime(null)
          .build();

  protected static final GatewayBaseResponse<HotelPromoAggregateResponse>
      hotelPromoAggregateResponseDummy =
      new GatewayBaseResponseBuilder<RestResponsePage<HotelPromoAggregateResponse>>()
          .withCode(ResponseCode.SUCCESS.getCode())
          .withMessage(ResponseCode.SUCCESS.getMessage())
          .withData(restResponsePage)
          .withServerTime(null)
          .build();

  protected static final HotelPromoAggregateRequest hotelPromoAggregateRequest =
      new HotelPromoAggregateRequestBuilder()
          .withHotelId(Integer.parseInt(HOTEL_ID))
          .build();

  protected static final FindAllPromoParam findAllPromoParam = new FindAllPromoParamBuilder()
      .withPage(PAGE)
      .withLimit(LIMIT)
      .withHotelId(HOTEL_ID)
      .withRoomId(ROOM_ID)
      .withStartDate(START_DATE)
      .withEndDate(END_DATE)
      .build();

  protected static final String HOTEL_PROMO_REQUEST_DUMMY = "{ \"hotelId\": " + HOTEL_ID + "}";

  static {
    params.put(HotelPromoAggregateFields.PAGE, PAGE.toString());
    params.put(HotelPromoAggregateFields.LIMIT, LIMIT.toString());
    params.put(HotelPromoAggregateFields.HOTEL_ID, HOTEL_ID);
    params.put(HotelPromoAggregateFields.ROOM_ID, ROOM_ID);
    params.put(HotelPromoAggregateFields.START_DATE, START_DATE);
    params.put(HotelPromoAggregateFields.END_DATE, END_DATE);
    params.put(HotelPromoAggregateFields.HOTEL_ID, HOTEL_ID);

    restResponsePage.setContent(Collections.singletonList(new HotelPromoAggregateResponseBuilder()
        .withHotelId(Integer.parseInt(HOTEL_ID)).build()));
  }
}
