package com.tl.booking.gateway.outbound.impl.configuration;

import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelMappingRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelMappingResponse;
import com.tl.booking.gateway.entity.outbound.Hotel.OtaRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.OtaResponse;
import com.tl.booking.gateway.entity.outbound.Hotel.PicRegionRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.PicRegionResponse;
import com.tl.booking.gateway.entity.outbound.Hotel.RegionMappingRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.RegionMappingResponse;
import com.tl.booking.gateway.entity.outbound.Hotel.ScrappingReportResponse;

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
import retrofit2.http.QueryMap;

public interface HotelScrappingEndPointService {

  @GET("/hotel-scrapping/ota")
  Call<GatewayBaseResponse<List<OtaResponse>>> findHotelOta(@HeaderMap Map<String, String> header);

  @GET("/hotel-scrapping/ota/{id}")
  Call<GatewayBaseResponse<OtaResponse>> findHotelOtaById(@HeaderMap Map<String, String> header, @Path("id") String id);

  @POST("/hotel-scrapping/ota")
  Call<GatewayBaseResponse<OtaResponse>> createHotelOta(@HeaderMap Map<String, String> header, @Body
      OtaRequest otaRequest);

  @PUT("/hotel-scrapping/ota/{id}")
  Call<GatewayBaseResponse<OtaResponse>> updateHotelOta(@HeaderMap Map<String, String> header, @Path("id") String id,
      @Body OtaRequest otaRequest);

  @DELETE("/hotel-scrapping/ota/{id}")
  Call<GatewayBaseResponse> deleteHotelOta(@HeaderMap Map<String, String> header, @Path("id") String id);

  @GET("/hotel-scrapping/autocomplete/search/hotel")
  Call<GatewayBaseResponse<List<Map<String, String>>>> getAutoCompleteHotel(@HeaderMap Map<String, String> header, @QueryMap
      Map<String, String> query);

  @GET("/hotel-scrapping/hotel")
  Call<GatewayBaseResponse<RestResponsePage<HotelMappingResponse>>> findHotelMappingsByStoreId(@HeaderMap Map<String, String> header, @QueryMap
      Map<String, String> query);

  @GET("/hotel-scrapping/hotel/{id}")
  Call<GatewayBaseResponse<HotelMappingResponse>> findHotelMappingByStoreIdAndId(@HeaderMap Map<String, String> header,
      @Path("id") String id);

  @POST("/hotel-scrapping/hotel")
  Call<GatewayBaseResponse> createHotelMapping(@HeaderMap Map<String, String> header, @Body
      HotelMappingRequest hotelMappingRequest);

  @PUT("/hotel-scrapping/hotel/{id}")
  Call<GatewayBaseResponse> updateHotelMapping(@HeaderMap Map<String, String> header, @Path("id") String id, @Body
      HotelMappingRequest hotelMappingRequest);

  @DELETE("/hotel-scrapping/hotel/{id}")
  Call<GatewayBaseResponse> deleteHotelMapping(@HeaderMap Map<String, String> header, @Path("id") String id);

  @GET("/hotel-scrapping/pic")
  Call<GatewayBaseResponse<RestResponsePage<PicRegionResponse>>> findPicRegionList(@HeaderMap Map<String, String> header, @QueryMap
      Map<String, String> query);

  @GET("/hotel-scrapping/pic/{id}")
  Call<GatewayBaseResponse<PicRegionResponse>> findPicRegionById(@HeaderMap Map<String, String> header,
      @Path("id") String id);

  @POST("/hotel-scrapping/pic")
  Call<GatewayBaseResponse> createPicRegion(@HeaderMap Map<String, String> header, @Body
      PicRegionRequest picRegionRequest);

  @PUT("/hotel-scrapping/pic/{id}")
  Call<GatewayBaseResponse> updatePicRegion(@HeaderMap Map<String, String> header, @Path("id") String id, @Body
      PicRegionRequest picRegionRequest);

  @DELETE("/hotel-scrapping/pic/{id}")
  Call<GatewayBaseResponse> deletePicRegionById(@HeaderMap Map<String, String> header, @Path("id") String id);

  @GET("/hotel-scrapping/region")
  Call<GatewayBaseResponse<RestResponsePage<RegionMappingResponse>>> findRegionMappingsByStoreId(@HeaderMap Map<String, String> header, @QueryMap
      Map<String, String> query);

  @GET("/hotel-scrapping/region/{id}")
  Call<GatewayBaseResponse<RegionMappingResponse>> findRegionMappingByStoreIdAndId(@HeaderMap Map<String, String> header,
      @Path("id") String id);

  @POST("/hotel-scrapping/region")
  Call<GatewayBaseResponse> createRegionMapping(@HeaderMap Map<String, String> header, @Body
      RegionMappingRequest regionMappingRequest);

  @PUT("/hotel-scrapping/region/{id}")
  Call<GatewayBaseResponse> updateRegionMapping(@HeaderMap Map<String, String> header, @Path("id") String id, @Body
      RegionMappingRequest regionMappingRequest);

  @DELETE("/hotel-scrapping/region/{id}")
  Call<GatewayBaseResponse> deleteRegionMapping(@HeaderMap Map<String, String> header, @Path("id") String id);

  @GET("/hotel-scrapping/report")
  Call<GatewayBaseResponse<RestResponsePage<ScrappingReportResponse>>> getPriceReportData(@HeaderMap Map<String, String> header, @QueryMap
      Map<String, String> query);

  @GET("/hotel-scrapping/report/send-email")
  Call<GatewayBaseResponse<Boolean>> getEmailReport(@HeaderMap Map<String, String> header, @QueryMap
      Map<String, String> query);

  @GET("/hotel-scrapping/report/export")
  Call<GatewayBaseResponse<String>> exportReport(@HeaderMap Map<String, String> header, @QueryMap
      Map<String, String> query);

}
