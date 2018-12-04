package com.tl.booking.gateway.outbound.impl.configuration;

import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoCode.BankResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.BinNumberRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.BinNumberResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.BusinessLogicResponseRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.BusinessLogicResponseResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.CampaignDropdownResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.CampaignRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.CampaignResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.CardTypeResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.ChannelIdResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.PaymentResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.ProductTypeRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.ProductTypeResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeAdjustmentDropdownResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeAdjustmentRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeAdjustmentResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.StoreIdResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.VariableFindAllResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.VariableRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.VariableResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.VariableSourceResponse;

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


public interface PromoCodeEndPointService {


  @GET(ApiPath.END_POINT_PROMO_CODE)
  Call<GatewayBaseResponse<RestResponsePage<PromoCodeResponse>>> findPromoCodeFilterPaginated(
      @HeaderMap Map<String, String> header, @QueryMap
      Map<String, String> query);

  @POST(ApiPath.END_POINT_PROMO_CODE)
  Call<GatewayBaseResponse<PromoCodeResponse>> createPromoCode(
      @HeaderMap Map<String, String> header, @Body PromoCodeRequest promoCodeRequest);


  @PUT(ApiPath.END_POINT_PROMO_CODE + "/{id}")
  Call<GatewayBaseResponse<PromoCodeResponse>> updatePromoCode(
      @HeaderMap Map<String, String> header, @Body
      PromoCodeRequest promoCodeRequest, @Path("id") String id);

  @DELETE(ApiPath.END_POINT_PROMO_CODE + "/{id}")
  Call<GatewayBaseResponse<Boolean>> deletePromoCode(@HeaderMap Map<String, String> header,
      @Path("id")
          String id);

  @GET(ApiPath.END_POINT_PROMO_CODE + "/{id}")
  Call<GatewayBaseResponse<PromoCodeResponse>> findPromoCodeById(
      @HeaderMap Map<String, String> header, @Path("id")
      String id);

  @PUT(ApiPath.END_POINT_PROMO_CODE + "/{id}/activate")
  Call<GatewayBaseResponse<PromoCodeResponse>> updateStatusActivatePromoCode(
      @HeaderMap Map<String, String> header, @Path("id") String id);

  @PUT(ApiPath.END_POINT_PROMO_CODE + "/{id}/unActivate")
  Call<GatewayBaseResponse<PromoCodeResponse>> updateStatusUnActivatePromoCode(
      @HeaderMap Map<String, String> header, @Path("id") String id);





  @GET(ApiPath.END_POINT_CAMPAIGN)
  Call<GatewayBaseResponse<RestResponsePage<CampaignResponse>>> findCampaignFilterPaginated(
      @HeaderMap Map<String, String> header, @QueryMap
      Map<String, String> query);

  @POST(ApiPath.END_POINT_CAMPAIGN)
  Call<GatewayBaseResponse<CampaignResponse>> createCampaign(
      @HeaderMap Map<String, String> header, @Body CampaignRequest campaignRequest);


  @PUT(ApiPath.END_POINT_CAMPAIGN + "/{id}")
  Call<GatewayBaseResponse<CampaignResponse>> updateCampaign(
      @HeaderMap Map<String, String> header, @Body
      CampaignRequest campaignRequest, @Path("id") String id);

  @DELETE(ApiPath.END_POINT_CAMPAIGN + "/{id}")
  Call<GatewayBaseResponse<Boolean>> deleteCampaign(@HeaderMap Map<String, String> header,
      @Path("id")
          String id);

  @GET(ApiPath.END_POINT_CAMPAIGN + "/{id}")
  Call<GatewayBaseResponse<CampaignResponse>> findCampaignById(
      @HeaderMap Map<String, String> header, @Path("id")
      String id);

  @PUT(ApiPath.END_POINT_CAMPAIGN + "/{id}/activate")
  Call<GatewayBaseResponse<CampaignResponse>> updateStatusActivateCampaign(
      @HeaderMap Map<String, String> header, @Path("id") String id);

  @PUT(ApiPath.END_POINT_CAMPAIGN + "/{id}/unActivate")
  Call<GatewayBaseResponse<CampaignResponse>> updateStatusUnActivateCampaign(
      @HeaderMap Map<String, String> header, @Path("id") String id);

  @PUT(ApiPath.END_POINT_CAMPAIGN + "/{id}/pending")
  Call<GatewayBaseResponse<CampaignResponse>> updateStatusPendingCampaign(
      @HeaderMap Map<String, String> header, @Path("id") String id);

  @PUT(ApiPath.END_POINT_CAMPAIGN + "/{id}/rejected")
  Call<GatewayBaseResponse<CampaignResponse>> updateStatusRejectedCampaign(
      @HeaderMap Map<String, String> header, @Path("id") String id);

  @GET(ApiPath.END_POINT_CAMPAIGN + ApiPath.FIND_ACTIVATE)
  Call<GatewayBaseResponse<List<CampaignDropdownResponse>>> findCampaignActivate(
      @HeaderMap Map<String, String> header);



  @GET(ApiPath.END_POINT_PROMO_ADJUSTMENT)
  Call<GatewayBaseResponse<RestResponsePage<PromoCodeAdjustmentResponse>>> findPromoCodeAdjustmentFilterPaginated(
      @HeaderMap Map<String, String> header, @QueryMap
      Map<String, String> query);

  @POST(ApiPath.END_POINT_PROMO_ADJUSTMENT)
  Call<GatewayBaseResponse<PromoCodeAdjustmentResponse>> createPromoCodeAdjustment(
      @HeaderMap Map<String, String> header, @Body PromoCodeAdjustmentRequest promoCodeRequest);


  @PUT(ApiPath.END_POINT_PROMO_ADJUSTMENT + "/{id}")
  Call<GatewayBaseResponse<PromoCodeAdjustmentResponse>> updatePromoCodeAdjustment(
      @HeaderMap Map<String, String> header, @Body
      PromoCodeAdjustmentRequest promoCodeRequest, @Path("id") String id);

  @DELETE(ApiPath.END_POINT_PROMO_ADJUSTMENT + "/{id}")
  Call<GatewayBaseResponse<Boolean>> deletePromoCodeAdjustment(@HeaderMap Map<String, String> header,
      @Path("id")
          String id);

  @GET(ApiPath.END_POINT_PROMO_ADJUSTMENT + "/{id}")
  Call<GatewayBaseResponse<PromoCodeAdjustmentResponse>> findPromoCodeAdjustmentById(
      @HeaderMap Map<String, String> header, @Path("id")
      String id);

  @PUT(ApiPath.END_POINT_PROMO_ADJUSTMENT + "/{id}/activate")
  Call<GatewayBaseResponse<PromoCodeAdjustmentResponse>> updateStatusActivatePromoCodeAdjustment(
      @HeaderMap Map<String, String> header, @Path("id") String id);

  @PUT(ApiPath.END_POINT_PROMO_ADJUSTMENT + "/{id}/unActivate")
  Call<GatewayBaseResponse<PromoCodeAdjustmentResponse>> updateStatusUnActivatePromoCodeAdjustment(
      @HeaderMap Map<String, String> header, @Path("id") String id);

  @PUT(ApiPath.END_POINT_PROMO_ADJUSTMENT + "/{id}/pending")
  Call<GatewayBaseResponse<PromoCodeAdjustmentResponse>> updateStatusPendingPromoCodeAdjustment(
      @HeaderMap Map<String, String> header, @Path("id") String id);

  @PUT(ApiPath.END_POINT_PROMO_ADJUSTMENT + "/{id}/rejected")
  Call<GatewayBaseResponse<PromoCodeAdjustmentResponse>> updateStatusRejectedPromoCodeAdjustment(
      @HeaderMap Map<String, String> header, @Path("id") String id);

  @GET(ApiPath.END_POINT_PROMO_ADJUSTMENT + ApiPath.FIND_ACTIVATE)
  Call<GatewayBaseResponse<List<PromoCodeAdjustmentDropdownResponse>>> findPromoCodeAdjustmentActivate(
      @HeaderMap Map<String, String> header);




  @GET(ApiPath.END_POINT_VARIABLE)
  Call<GatewayBaseResponse<RestResponsePage<VariableResponse>>> findVariableFilterPaginated(
      @HeaderMap Map<String, String> header, @QueryMap
      Map<String, String> query);

  @POST(ApiPath.END_POINT_VARIABLE)
  Call<GatewayBaseResponse<VariableResponse>> createVariable(
      @HeaderMap Map<String, String> header, @Body VariableRequest variableRequest);


  @PUT(ApiPath.END_POINT_VARIABLE + "/{id}")
  Call<GatewayBaseResponse<VariableResponse>> updateVariable(
      @HeaderMap Map<String, String> header, @Body
      VariableRequest variableRequest, @Path("id") String id);

  @DELETE(ApiPath.END_POINT_VARIABLE + "/{id}")
  Call<GatewayBaseResponse<Boolean>> deleteVariable(@HeaderMap Map<String, String> header,
      @Path("id")
          String id);

  @GET(ApiPath.END_POINT_VARIABLE + "/{id}")
  Call<GatewayBaseResponse<VariableResponse>> findVariableById(
      @HeaderMap Map<String, String> header, @Path("id")
      String id);

  @GET(ApiPath.END_POINT_VARIABLE + "/findAll")
  Call<GatewayBaseResponse<List<VariableFindAllResponse>>> findAllVariable(
      @HeaderMap Map<String, String> header);



  @GET(ApiPath.END_POINT_PRODUCT_TYPE)
  Call<GatewayBaseResponse<RestResponsePage<ProductTypeResponse>>> findProductTypeFilterPaginated(
      @HeaderMap Map<String, String> header, @QueryMap
      Map<String, String> query);

  @POST(ApiPath.END_POINT_PRODUCT_TYPE)
  Call<GatewayBaseResponse<ProductTypeResponse>> createProductType(
      @HeaderMap Map<String, String> header, @Body ProductTypeRequest productTypeRequest);


  @PUT(ApiPath.END_POINT_PRODUCT_TYPE + "/{id}")
  Call<GatewayBaseResponse<ProductTypeResponse>> updateProductType(
      @HeaderMap Map<String, String> header, @Body
      ProductTypeRequest productTypeRequest, @Path("id") String id);

  @DELETE(ApiPath.END_POINT_PRODUCT_TYPE + "/{id}")
  Call<GatewayBaseResponse<Boolean>> deleteProductType(@HeaderMap Map<String, String> header,
      @Path("id")
          String id);

  @GET(ApiPath.END_POINT_PRODUCT_TYPE + "/{id}")
  Call<GatewayBaseResponse<ProductTypeResponse>> findProductTypeById(
      @HeaderMap Map<String, String> header, @Path("id")
      String id);

  @GET(ApiPath.END_POINT_PRODUCT_TYPE + "/findAll")
  Call<GatewayBaseResponse<List<ProductTypeResponse>>> findProductTypes(
      @HeaderMap Map<String, String> header);



  @GET(ApiPath.END_POINT_BIN_NUMBER)
  Call<GatewayBaseResponse<RestResponsePage<BinNumberResponse>>> findBinNumberFilterPaginated(
      @HeaderMap Map<String, String> header, @QueryMap
      Map<String, String> query);

  @POST(ApiPath.END_POINT_BIN_NUMBER)
  Call<GatewayBaseResponse<BinNumberResponse>> createBinNumber(
      @HeaderMap Map<String, String> header, @Body BinNumberRequest binNumberRequest);


  @PUT(ApiPath.END_POINT_BIN_NUMBER + "/{id}")
  Call<GatewayBaseResponse<BinNumberResponse>> updateBinNumber(
      @HeaderMap Map<String, String> header, @Body
      BinNumberRequest binNumberRequest, @Path("id") String id);

  @DELETE(ApiPath.END_POINT_BIN_NUMBER + "/{id}")
  Call<GatewayBaseResponse<Boolean>> deleteBinNumber(@HeaderMap Map<String, String> header,
      @Path("id")
          String id);

  @GET(ApiPath.END_POINT_BIN_NUMBER + "/{id}")
  Call<GatewayBaseResponse<BinNumberResponse>> findBinNumberById(
      @HeaderMap Map<String, String> header, @Path("id")
      String id);

  @GET(ApiPath.END_POINT_BIN_NUMBER + "/findAll")
  Call<GatewayBaseResponse<List<String>>> binNumber(
      @HeaderMap Map<String, String> header, @QueryMap
      Map<String, String> query);

  @GET(ApiPath.END_POINT_BANK + "/findAll")
  Call<GatewayBaseResponse<List<BankResponse>>> bank(
      @HeaderMap Map<String, String> header);

  @GET(ApiPath.END_POINT_PAYMENT + "/findAll")
  Call<GatewayBaseResponse<List<PaymentResponse>>> findAllPayment(
      @HeaderMap Map<String, String> header);

  @GET(ApiPath.END_POINT_STORE_ID + "/findAll")
  Call<GatewayBaseResponse<List<StoreIdResponse>>> findAllStoreId(
      @HeaderMap Map<String, String> header);

  @GET(ApiPath.END_POINT_CARD_TYPE + "/findAll")
  Call<GatewayBaseResponse<List<CardTypeResponse>>> findAllCardType(
      @HeaderMap Map<String, String> header);

  @GET(ApiPath.END_POINT_CHANNEL_ID + "/findAll")
  Call<GatewayBaseResponse<List<ChannelIdResponse>>> findAllChannelId(
      @HeaderMap Map<String, String> header);




  @GET(ApiPath.END_POINT_BUSINESS_LOGIC)
  Call<GatewayBaseResponse<RestResponsePage<BusinessLogicResponseResponse>>> findBusinessLogicResponseFilterPaginated(
      @HeaderMap Map<String, String> header, @QueryMap
      Map<String, String> query);

  @POST(ApiPath.END_POINT_BUSINESS_LOGIC)
  Call<GatewayBaseResponse<BusinessLogicResponseResponse>> createBusinessLogicResponse(
      @HeaderMap Map<String, String> header, @Body BusinessLogicResponseRequest businessLogicResponseRequest);


  @PUT(ApiPath.END_POINT_BUSINESS_LOGIC + "/{id}")
  Call<GatewayBaseResponse<BusinessLogicResponseResponse>> updateBusinessLogicResponse(
      @HeaderMap Map<String, String> header, @Body
      BusinessLogicResponseRequest businessLogicResponseRequest, @Path("id") String id);

  @DELETE(ApiPath.END_POINT_BUSINESS_LOGIC + "/{id}")
  Call<GatewayBaseResponse<Boolean>> deleteBusinessLogic(@HeaderMap Map<String, String> header,
      @Path("id")
          String id);

  @GET(ApiPath.END_POINT_BUSINESS_LOGIC + "/{id}")
  Call<GatewayBaseResponse<BusinessLogicResponseResponse>> findBusinessLogicResponseById(
      @HeaderMap Map<String, String> header, @Path("id")
      String id);

  @GET(ApiPath.END_POINT_BUSINESS_LOGIC + "/findAll")
  Call<GatewayBaseResponse<List<BusinessLogicResponseResponse>>> findBusinessLogicResponse(
      @HeaderMap Map<String, String> header);

  @GET(ApiPath.END_POINT_VARIABLE_SOURCE + "/{sourceType}")
  Call<GatewayBaseResponse<List<VariableSourceResponse>>> findVariableSource(
      @HeaderMap Map<String, String> header, @Path("sourceType")
      String sourceType, @QueryMap Map<String, String> query);

}
