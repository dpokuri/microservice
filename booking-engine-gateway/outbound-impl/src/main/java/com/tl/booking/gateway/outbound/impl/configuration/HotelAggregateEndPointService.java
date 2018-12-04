package com.tl.booking.gateway.outbound.impl.configuration;

import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.Hotel.AdditionalDiscountRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.AdditionalDiscountResponse;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoAggregateRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoAggregateResponse;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoConfigRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoTypeRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoTypeResponse;

import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface HotelAggregateEndPointService {

  @GET(ApiPath.END_POINT_HOTEL_PROMO_AGGREGATE)
  Call<GatewayBaseResponse<RestResponsePage<HotelPromoAggregateResponse>>>
  findAllHotelPromoByHotelIdAndRoomIdAndDate(@HeaderMap Map<String, String> headers,
      @QueryMap Map<String, String> queries
  );

  @GET(ApiPath.END_POINT_HOTEL_PROMO_AGGREGATE + ApiPath.ID)
  Call<GatewayBaseResponse<HotelPromoAggregateResponse>> findHotelPromoById(
      @HeaderMap Map<String, String> headers, @Path("id") String id
  );

  @POST(ApiPath.END_POINT_HOTEL_PROMO_AGGREGATE)
  Call<GatewayBaseResponse<HotelPromoAggregateResponse>> createHotelPromoAggregate(
      @HeaderMap Map<String, String> headers,
      @Body HotelPromoAggregateRequest hotelPromoAggregateRequest
  );

  @PUT(ApiPath.END_POINT_HOTEL_PROMO_AGGREGATE + ApiPath.ID)
  Call<GatewayBaseResponse<HotelPromoAggregateResponse>> updateHotelPromoAggregate(
      @HeaderMap Map<String, String> headers, @Path("id") String id,
      @Body HotelPromoAggregateRequest hotelPromoAggregateRequest
  );

  @DELETE(ApiPath.END_POINT_HOTEL_PROMO_AGGREGATE + ApiPath.ID)
  Call<GatewayBaseResponse> deleteHotelPromoAggregate(
      @HeaderMap Map<String, String> headers, @Path("id") String id
  );

  @POST(ApiPath.END_POINT_ADDITIONAL_DISCOUNT)
  Call<GatewayBaseResponse<AdditionalDiscountResponse>> createAdditionalDiscount(
      @HeaderMap Map<String, String> headers,
      @Body AdditionalDiscountRequest additionalDiscountRequest
  );

  @PUT(ApiPath.END_POINT_ADDITIONAL_DISCOUNT + ApiPath.ID)
  Call<GatewayBaseResponse<AdditionalDiscountResponse>> updateAdditionalDiscount(
      @HeaderMap Map<String, String> headers,
      @Body AdditionalDiscountRequest additionalDiscountRequest,
      @Path("id") String id
  );

  @DELETE(ApiPath.END_POINT_ADDITIONAL_DISCOUNT + ApiPath.ID)
  Call<GatewayBaseResponse<AdditionalDiscountResponse>> deleteAdditionalDiscount(
      @HeaderMap Map<String, String> headers,
      @Path("id") String id
  );

  @GET(ApiPath.END_POINT_ADDITIONAL_DISCOUNT + "/hotel")
  Call<GatewayBaseResponse<AdditionalDiscountResponse>> findAdditionalDiscountByHotel(
      @HeaderMap Map<String, String> headers,
      @Query(value = "hotelId") Integer hotelId,
      @Query(value = "type") String type
  );

  @GET(ApiPath.END_POINT_ADDITIONAL_DISCOUNT + ApiPath.ID)
  Call<GatewayBaseResponse<AdditionalDiscountResponse>> findAdditionalDiscountById(
      @HeaderMap Map<String, String> headers,
      @Path("id") String id
  );

  @GET(ApiPath.END_POINT_ADDITIONAL_DISCOUNT)
  Call<GatewayBaseResponse<RestResponsePage<AdditionalDiscountResponse>>> findAdditionalDiscount(
      @HeaderMap Map<String, String> header,
      @QueryMap Map<String, String> query
  );

  @GET(ApiPath.END_POINT_HOTEL_PROMO_TYPE)
  Call<GatewayBaseResponse<List<HotelPromoTypeResponse>>> findAllHotelPromoType(
      @HeaderMap Map<String, String> headers);

  @GET(ApiPath.END_POINT_HOTEL_PROMO_TYPE + ApiPath.ID)
  Call<GatewayBaseResponse<HotelPromoTypeResponse>> findHotelPromoTypeById(
      @HeaderMap Map<String, String> headers, @Path("id") String id);

  @POST(ApiPath.END_POINT_HOTEL_PROMO_TYPE)
  Call<GatewayBaseResponse<HotelPromoTypeResponse>> createHotelPromoType(
      @HeaderMap Map<String, String> headers, @Body HotelPromoTypeRequest hotelPromoTypeRequest);

  @PUT(ApiPath.END_POINT_HOTEL_PROMO_TYPE + ApiPath.ID)
  Call<GatewayBaseResponse<HotelPromoTypeResponse>> updateHotelPromoType(
      @HeaderMap Map<String, String> headers, @Path("id") String id,
      @Body HotelPromoTypeRequest hotelPromoTypeRequest);

  @DELETE(ApiPath.END_POINT_HOTEL_PROMO_TYPE + ApiPath.ID)
  Call<GatewayBaseResponse> deleteHotelPromoType(@HeaderMap Map<String, String> headers,
      @Path("id") String id);

  @POST(ApiPath.SLUG_HOTEL_PROMO_CONFIG)
  Call<GatewayBaseResponse<Object>> createHotelPromoConfig(@HeaderMap Map<String, String> headers,
      @Body HotelPromoConfigRequest hotelPromoConfigRequest);

  @PUT(ApiPath.SLUG_HOTEL_PROMO_CONFIG + ApiPath.ID)
  Call<GatewayBaseResponse<Object>> updateHotelPromoConfig(
      @HeaderMap Map<String, String> headers, @Path("id") String id,
      @Body HotelPromoConfigRequest hotelPromoConfigRequest);

  @DELETE(ApiPath.SLUG_HOTEL_PROMO_CONFIG + ApiPath.ID)
  Call<GatewayBaseResponse<Object>> deleteHotelPromoConfig(
      @HeaderMap Map<String, String> headers, @Path("id") String id);

  @GET(ApiPath.SLUG_HOTEL_PROMO_CONFIG + ApiPath.ID)
  Call<GatewayBaseResponse<Object>> getOneHotelPromoConfig(
      @HeaderMap Map<String, String> headers, @Path("id") Integer id);

  @GET(ApiPath.SLUG_HOTEL_PROMO_CONFIG)
  Call<GatewayBaseResponse<Object>> getAllHotelPromoConfig(
      @HeaderMap Map<String, String> headers, @QueryMap Map<String, String> queries);

  @GET(ApiPath.END_POINT_HOTEL_ROOM)
  Call<GatewayBaseResponse<List<Map<String, Object>>>> getHotelRoomList(
      @HeaderMap Map<String, String> headers, @QueryMap Map<String, String> query);

  @GET(ApiPath.END_POINT_HOTEL_POLICY)
  Call<GatewayBaseResponse<List<Map<String, Object>>>> getHotelPolicy(
      @HeaderMap Map<String, String> headers);
}
