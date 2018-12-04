package com.tl.booking.gateway.outbound.impl.configuration;

import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.Hotel.Address;
import com.tl.booking.gateway.entity.outbound.Hotel.FindHotelByHotelIdParam;
import com.tl.booking.gateway.entity.outbound.Hotel.InternalSubsidyActivateRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.InternalSubsidyRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.InternalSubsidyResponse;

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

public interface HotelSubsidyEndPointService {

  @GET(ApiPath.URL_COUNTRY)
  Call<GatewayBaseResponse<List<Address>>> callGetCountry(@HeaderMap Map<String, String> header);

  @POST(ApiPath.END_POINT_INTERNAL_SUBSIDY)
  Call<GatewayBaseResponse> createInternalSubsidy(@HeaderMap Map<String, String> header, @Body
      InternalSubsidyRequest internalSubsidyRequest);

  @PUT(ApiPath.END_POINT_INTERNAL_SUBSIDY + "/{id}")
  Call<GatewayBaseResponse> updateInternalSubsidy(@HeaderMap Map<String, String> header, @Body
      InternalSubsidyRequest internalSubsidyRequest, @Path("id") String id);

  @PUT(ApiPath.END_POINT_INTERNAL_SUBSIDY + "/activate/{id}")
  Call<GatewayBaseResponse> setActiveInternalSubsidy(@HeaderMap Map<String, String> header, @Body
      InternalSubsidyActivateRequest internalSubsidyActivateRequest, @Path("id") String id);

  @DELETE(ApiPath.END_POINT_INTERNAL_SUBSIDY + "/{id}")
  Call<GatewayBaseResponse> deleteInternalSubsidy(@HeaderMap Map<String, String> header,
      @Path("id") String id);

  @GET(ApiPath.END_POINT_INTERNAL_SUBSIDY + "/{id}")
  Call<GatewayBaseResponse> findInternalSubsidyByStoreIdAndId(@HeaderMap Map<String, String> header,
      @Path("id") String id);

  @GET(ApiPath.END_POINT_INTERNAL_SUBSIDY)
  Call<GatewayBaseResponse<RestResponsePage<InternalSubsidyResponse>>> findInternalSubsidyByStoreId(
      @HeaderMap Map<String, String> header, @QueryMap Map<String, String> query);

  @GET(ApiPath.URL_PROVINCE)
  Call<GatewayBaseResponse<List<Address>>> callGetProvince(@HeaderMap Map<String, String> header,
      @Query(value = "countryId") String countryId);

  @GET(ApiPath.URL_CITY)
  Call<GatewayBaseResponse<List<Address>>> callGetCity(@HeaderMap Map<String, String> header,
      @Query(value = "provinceId") String provinceId);

  @GET(ApiPath.URL_AREA)
  Call<GatewayBaseResponse<List<Address>>> callGetArea(@HeaderMap Map<String, String> header,
      @Query(value = "cityId") String cityId);

  @GET(ApiPath.URL_HOTEL_BY_ADDRESS)
  Call<GatewayBaseResponse<List<Map<String, String>>>> findHotelByAddress(
      @HeaderMap Map<String, String> header, @QueryMap Map<String, String> query);

  @POST(ApiPath.URL_HOTEL_NAME_BY_HOTEL_IDS)
  Call<GatewayBaseResponse<List<Map<String, String>>>> findHotelNameByHotelIds(
      @HeaderMap Map<String, String> header, @Body FindHotelByHotelIdParam findHotelByHotelIdParam);
}
