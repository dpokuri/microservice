package com.tl.booking.gateway.outbound.impl.configuration;

import java.util.Map;

import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.HolidayRequest;
import com.tl.booking.gateway.entity.outbound.HolidayResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface CalendarEndPointService {
  @GET("/tix-calendar/holiday")
  Call<GatewayBaseResponse<RestResponsePage<HolidayResponse>>> findHolidayFilterPaginated(@HeaderMap Map<String, String> header, @QueryMap
      Map<String, String> query);

  @POST("/tix-calendar/holiday")
  Call<GatewayBaseResponse<HolidayResponse>> createHoliday(@HeaderMap Map<String, String> header, @Body HolidayRequest holidayRequest);

  @PUT("/tix-calendar/holiday/{id}")
  Call<GatewayBaseResponse<HolidayResponse>> updateHoliday(@HeaderMap Map<String, String> header, @Body
      HolidayRequest holidayRequest, @Path("id") String id);

  @DELETE("/tix-calendar/holiday/{id}")
  Call<GatewayBaseResponse<Boolean>> deleteHoliday(@HeaderMap Map<String, String> header, @Path("id")
      String id);

  @GET("/tix-calendar/holiday/{id}")
  Call<GatewayBaseResponse<HolidayResponse>> findHolidayById(@HeaderMap Map<String, String> header, @Path("id")
      String id);
}
