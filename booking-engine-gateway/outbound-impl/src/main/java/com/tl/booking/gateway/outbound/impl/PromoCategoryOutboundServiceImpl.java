package com.tl.booking.gateway.outbound.impl;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.libraries.utility.MandatoryRequestHelper;
import com.tl.booking.gateway.outbound.api.PromoCategoryOutboundService;
import com.tl.booking.gateway.outbound.impl.configuration.PromoListEndPointService;
import com.tl.booking.gateway.entity.constant.enums.PromoCategoryColumn;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.fields.PromoCategoryFields;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoList.PromoCategoryRequest;
import com.tl.booking.gateway.entity.outbound.PromoList.PromoCategoryResponse;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Response;

@Service
public class PromoCategoryOutboundServiceImpl implements PromoCategoryOutboundService {

  @Autowired
  private PromoListEndPointService promoListEndPointService;

  @Override
  public Single<GatewayBaseResponse<RestResponsePage<PromoCategoryResponse>>> findPromoCategoryFilterPaginated(MandatoryRequest mandatoryRequest,
      String category,
      Integer page,
      Integer size,
      PromoCategoryColumn columnSort,
      SortDirection sortDirection) {

    Map<String, String> queryParam = new HashMap<>();

    if(isNotNullString(category)) {
      queryParam.put(PromoCategoryFields.CATEGORY, category);
    }

    queryParam.put("page", page.toString());
    queryParam.put("size", size.toString());

    if(isExistColumnSort(columnSort)){
      queryParam.put("columnSort", columnSort.getName());
    }
    if(isExistSortDirection(sortDirection)){
      queryParam.put("sortDirection", sortDirection.getName());
    }


    return Single.<GatewayBaseResponse<RestResponsePage<PromoCategoryResponse>>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<RestResponsePage<PromoCategoryResponse>>> promoCategoryResponse = this
            .promoListEndPointService
            .findPromoCategoryFilterPaginated
                (
                    MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                    queryParam)
            .execute();

        GatewayBaseResponse<RestResponsePage<PromoCategoryResponse>> promoCategoryPage = promoCategoryResponse.body();

        singleEmitter.onSuccess(promoCategoryPage);
        singleEmitter.onSuccess(null);
      } catch (Exception e){
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<PromoCategoryResponse>> createPromoCategory(MandatoryRequest mandatoryRequest,
      PromoCategoryRequest promoCategoryRequest) {
    return Single.<GatewayBaseResponse<PromoCategoryResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<PromoCategoryResponse>> promoCategoryResponse = this.promoListEndPointService.createPromoCategory(MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), promoCategoryRequest).execute();
        GatewayBaseResponse<PromoCategoryResponse> promoCategoryPage = promoCategoryResponse.body();

        singleEmitter.onSuccess(promoCategoryPage);
      } catch (Exception e){
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<PromoCategoryResponse>> updatePromoCategory(MandatoryRequest mandatoryRequest,
      PromoCategoryRequest promoCategoryRequest, String id)
  {
    return Single.<GatewayBaseResponse<PromoCategoryResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<PromoCategoryResponse>> promoCategoryResponse = this.promoListEndPointService.updatePromoCategory(MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), promoCategoryRequest, id).execute();
        GatewayBaseResponse<PromoCategoryResponse> promoCategoryPage = promoCategoryResponse.body();

        singleEmitter.onSuccess(promoCategoryPage);
      } catch (Exception e){
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<Boolean>> deletePromoCategory(MandatoryRequest mandatoryRequest,
      String id) {
    return Single.<GatewayBaseResponse<Boolean>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<Boolean>> promoCategoryResponse = this.promoListEndPointService
            .deletePromoCategory
                (MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id).execute();
        GatewayBaseResponse<Boolean> success = promoCategoryResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e){
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<PromoCategoryResponse>> findPromoCategoryById(MandatoryRequest mandatoryRequest,
      String id) {
    return Single.<GatewayBaseResponse<PromoCategoryResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<PromoCategoryResponse>> promoCategoryResponse = this.promoListEndPointService
            .findPromoCategoryById
                (MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id).execute();
        GatewayBaseResponse<PromoCategoryResponse> success = promoCategoryResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e){
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<List<Map<String, Object>>>> getCategories
      (MandatoryRequest
      mandatoryRequest, String lang) {

    Map<String, String> queryParam = new HashMap<>();
    queryParam.put("lang", lang);

    return Single.<GatewayBaseResponse<List<Map<String, Object>>>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<List<Map<String,Object>>>> promoCategoryResponse = this
            .promoListEndPointService
            .getCategories
                (MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                    queryParam)
            .execute();

        GatewayBaseResponse<List<Map<String,Object>>> promoCategoryPage = promoCategoryResponse.body();

        singleEmitter.onSuccess(promoCategoryPage);
      } catch (Exception e){
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<List<Map<String, Object>>>> getListCategories
      (MandatoryRequest
          mandatoryRequest, String lang) {

    Map<String, String> queryParam = new HashMap<>();
    queryParam.put("lang", lang);

    return Single.<GatewayBaseResponse<List<Map<String, Object>>>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<List<Map<String,Object>>>> promoCategoryResponse = this
            .promoListEndPointService
            .getListCategories
                (MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                    queryParam)
            .execute();

        GatewayBaseResponse<List<Map<String,Object>>> promoCategoryPage = promoCategoryResponse.body();

        singleEmitter.onSuccess(promoCategoryPage);
      } catch (Exception e){
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }
  
  private Boolean isNotNullString(String string) {
    return string != null;
  }


  private boolean isExistSortDirection(SortDirection sortDirection) {
    return sortDirection != null;
  }

  private boolean isExistColumnSort(PromoCategoryColumn columnSort) {
    return columnSort != null;
  }

}
