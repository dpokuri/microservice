package com.tl.booking.gateway.outbound.impl.configuration;

import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.flightrme.AdjustmentRuleRequest;
import com.tl.booking.gateway.entity.outbound.flightrme.AdjustmentRuleResponse;

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

public interface FlightRMEEndPointService {

  @GET(ApiPath.END_POINT_BUSINESS_LOGIC)
  Call<GatewayBaseResponse<RestResponsePage<AdjustmentRuleResponse>>> findAdjustmentRuleFilterPaginated(
      @HeaderMap Map<String, String> header, @QueryMap
      Map<String, String> query);

  @POST(ApiPath.END_POINT_ADJUSTMENT_RULE)
  Call<GatewayBaseResponse<AdjustmentRuleResponse>> createAdjustmentRule(
      @HeaderMap Map<String, String> header, @Body AdjustmentRuleRequest adjustmentRuleRequest);

  @PUT(ApiPath.END_POINT_ADJUSTMENT_RULE + "/{id}")
  Call<GatewayBaseResponse<AdjustmentRuleResponse>> updateAdjustmentRule(
      @HeaderMap Map<String, String> header, @Body
      AdjustmentRuleRequest adjustmentRuleRequest, @Path("id") String id);

  @DELETE(ApiPath.END_POINT_ADJUSTMENT_RULE + "/{id}")
  Call<GatewayBaseResponse<Boolean>> deleteAdjustmentRule(@HeaderMap Map<String, String> header,
      @Path("id")
          String id);

  @GET(ApiPath.END_POINT_BUSINESS_LOGIC + "/{id}")
  Call<GatewayBaseResponse<AdjustmentRuleResponse>> findAdjustmentById(
      @HeaderMap Map<String, String> header, @Path("id")
      String id);

  @PUT(ApiPath.END_POINT_ADJUSTMENT_RULE + "/{id}/activate")
  Call<GatewayBaseResponse<AdjustmentRuleResponse>> updateStatusActivateAdjustmentRule(
      @HeaderMap Map<String, String> header, @Path("id") String id);

  @PUT(ApiPath.END_POINT_ADJUSTMENT_RULE + "/{id}/unActivate")
  Call<GatewayBaseResponse<AdjustmentRuleResponse>> updateStatusUnActivateAdjustmentRule(
      @HeaderMap Map<String, String> header, @Path("id") String id);

  @PUT(ApiPath.END_POINT_ADJUSTMENT_RULE + "/{id}/pending")
  Call<GatewayBaseResponse<AdjustmentRuleResponse>> updateStatusPendingAdjustmentRule(
      @HeaderMap Map<String, String> header, @Path("id") String id);

  @PUT(ApiPath.END_POINT_ADJUSTMENT_RULE + "/{id}/rejected")
  Call<GatewayBaseResponse<AdjustmentRuleResponse>> updateStatusRejectedAdjustmentRule(
      @HeaderMap Map<String, String> header, @Path("id") String id);
}
