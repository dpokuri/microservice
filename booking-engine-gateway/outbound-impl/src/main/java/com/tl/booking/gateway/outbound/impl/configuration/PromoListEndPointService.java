package com.tl.booking.gateway.outbound.impl.configuration;

import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoList.PromoCategoryRequest;
import com.tl.booking.gateway.entity.outbound.PromoList.PromoCategoryResponse;
import com.tl.booking.gateway.entity.outbound.PromoList.PromoPageRequest;
import com.tl.booking.gateway.entity.outbound.PromoList.PromoPageResponse;

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


public interface PromoListEndPointService {
  @GET(ApiPath.END_POINT_PROMO_CATEGORY)
  Call<GatewayBaseResponse<RestResponsePage<PromoCategoryResponse>>> findPromoCategoryFilterPaginated(
      @HeaderMap Map<String, String> header, @QueryMap
      Map<String, String> query);

  @POST(ApiPath.END_POINT_PROMO_CATEGORY)
  Call<GatewayBaseResponse<PromoCategoryResponse>> createPromoCategory(@HeaderMap Map<String, String> header, @Body PromoCategoryRequest promoCategoryRequest);

  @PUT(ApiPath.END_POINT_PROMO_CATEGORY + "/{id}")
  Call<GatewayBaseResponse<PromoCategoryResponse>> updatePromoCategory(@HeaderMap Map<String, String> header, @Body
      PromoCategoryRequest promoCategoryRequest, @Path("id") String id);

  @DELETE(ApiPath.END_POINT_PROMO_CATEGORY + "/{id}")
  Call<GatewayBaseResponse<Boolean>> deletePromoCategory(@HeaderMap Map<String, String> header, @Path("id")
      String id);

  @GET(ApiPath.END_POINT_PROMO_CATEGORY + "/{id}")
  Call<GatewayBaseResponse<PromoCategoryResponse>> findPromoCategoryById(@HeaderMap Map<String, String> header, @Path("id")
      String id);


  @GET(ApiPath.END_POINT_PROMO_PAGE)
  Call<GatewayBaseResponse<RestResponsePage<PromoPageResponse>>> findPromoPageFilterPaginated(
      @HeaderMap Map<String, String> header, @QueryMap
      Map<String, String> query);

  @POST(ApiPath.END_POINT_PROMO_PAGE)
  Call<GatewayBaseResponse<PromoPageResponse>> createPromoPage(@HeaderMap Map<String, String> header, @Body PromoPageRequest promoPageRequest);


  @PUT(ApiPath.END_POINT_PROMO_PAGE + "/{id}")
  Call<GatewayBaseResponse<PromoPageResponse>> updatePromoPage(@HeaderMap Map<String, String> header, @Body
      PromoPageRequest promoPageRequest, @Path("id") String id);

  @DELETE(ApiPath.END_POINT_PROMO_PAGE + "/{id}")
  Call<GatewayBaseResponse<Boolean>> deletePromoPage(@HeaderMap Map<String, String> header, @Path("id")
      String id);

  @GET(ApiPath.END_POINT_PROMO_PAGE + "/{id}")
  Call<GatewayBaseResponse<PromoPageResponse>> findPromoPageBySlug(@HeaderMap Map<String, String> header, @Path("id")
      String id);


  @PUT(ApiPath.END_POINT_PROMO_PAGE + "/{id}/pending")
  Call<GatewayBaseResponse<PromoPageResponse>> updateStatusPendingPromoPage(@HeaderMap Map<String, String> header, @Path("id") String id);

  @PUT(ApiPath.END_POINT_PROMO_PAGE + "/{id}/activated")
  Call<GatewayBaseResponse<PromoPageResponse>> updateStatusActivatedPromoPage(@HeaderMap Map<String, String> header, @Path("id") String id);

  @PUT(ApiPath.END_POINT_PROMO_PAGE + "/{id}/inActivated")
  Call<GatewayBaseResponse<PromoPageResponse>> updateStatusInActivatedPromoPage(@HeaderMap Map<String, String> header, @Path("id") String id);

  @PUT(ApiPath.END_POINT_PROMO_PAGE + "/{id}/rejected")
  Call<GatewayBaseResponse<PromoPageResponse>> updateStatusRejectedPromoPage(@HeaderMap Map<String,
      String> header, @Path("id") String id);

  @GET(ApiPath.END_POINT_PROMO_CATEGORY + "/getCategories")
  Call<GatewayBaseResponse<List<Map<String, Object>>>> getCategories(@HeaderMap Map<String, String> header, @QueryMap Map<String, String> query);

  @GET(ApiPath.END_POINT_PROMO_CATEGORY + "/getListCategories")
  Call<GatewayBaseResponse<List<Map<String, Object>>>> getListCategories(@HeaderMap Map<String, String> header, @QueryMap Map<String, String> query);
}
