package com.tl.booking.gateway.outbound.impl.promocode;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.libraries.utility.MandatoryRequestHelper;
import com.tl.booking.gateway.outbound.api.promocode.PromoCodeAdjustmentOutboundService;
import com.tl.booking.gateway.outbound.impl.configuration.PromoCodeEndPointService;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.constant.enums.PromoCodeAdjustmentColumn;
import com.tl.booking.gateway.entity.constant.enums.PromoCodeAdjustmentStatus;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.fields.PromoCodeAdjustmentFields;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeAdjustmentDropdownResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeAdjustmentRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeAdjustmentResponse;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Response;

@Service
public class PromoCodeAdjustmentOutboundServiceImpl implements PromoCodeAdjustmentOutboundService {

  @Autowired
  private PromoCodeEndPointService promoCodeEndPointService;

  @Override
  public Single<GatewayBaseResponse<RestResponsePage<PromoCodeAdjustmentResponse>>> findPromoCodeAdjustmentFilterPaginated(
      MandatoryRequest mandatoryRequest,
      String name,
      Boolean isPromoCodeCombine,
      Integer page,
      Integer size,
      PromoCodeAdjustmentColumn columnSort,
      SortDirection sortDirection,
      String privilegeToCheck
  ) {

    Map<String, String> queryParam = new HashMap<>();

    if (isNotNullString(name)) {
      queryParam.put(PromoCodeAdjustmentFields.NAME, name);
    }
    if (isNotNullBoolean(isPromoCodeCombine)) {
      queryParam.put(PromoCodeAdjustmentFields.PROMO_CODE_COMBINE,
          String.valueOf(isPromoCodeCombine));
    }

    queryParam.put("page", page.toString());
    queryParam.put("size", size.toString());

    if (isExistColumnSort(columnSort)) {
      queryParam.put("columnSort", columnSort.getName());
    }
    if (isExistSortDirection(sortDirection)) {
      queryParam.put("sortDirection", sortDirection.getName());
    }

    return Single.<GatewayBaseResponse<RestResponsePage<PromoCodeAdjustmentResponse>>>create(
        singleEmitter -> {
          try {
            Response<GatewayBaseResponse<RestResponsePage<PromoCodeAdjustmentResponse>>> promoCodeResponse = this
                .promoCodeEndPointService
                .findPromoCodeAdjustmentFilterPaginated
                    (
                        MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                        queryParam)
                .execute();

            GatewayBaseResponse<RestResponsePage<PromoCodeAdjustmentResponse>> promoPageCode = promoCodeResponse
                .body();

            if (isExistRestResponsePage(promoPageCode.getData())) {
              List<PromoCodeAdjustmentResponse> promoCodeResponses = promoPageCode.getData().getContent();
              for (PromoCodeAdjustmentResponse response : promoCodeResponses) {
                response.setAllowedActions(new ArrayList<>());
                this.generateAllowedActions(response, privilegeToCheck);
              }

              RestResponsePage<PromoCodeAdjustmentResponse> restResponsePage = new
                  RestResponsePage<>();
              restResponsePage.setContent(promoCodeResponses);
              restResponsePage.setFirstPage(promoPageCode.getData().isFirstPage());
              restResponsePage.setNumber(promoPageCode.getData().getNumber());
              restResponsePage.setSize(promoPageCode.getData().getSize());
              restResponsePage.setTotalPages(promoPageCode.getData().getTotalPages());
              restResponsePage
                  .setNumberOfElements(promoPageCode.getData().getNumberOfElements());
              restResponsePage
                  .setTotalElements(promoPageCode.getData().getTotalElements());
              restResponsePage
                  .setPreviousPage(promoPageCode.getData().isPreviousPage());
              restResponsePage.setNextPage(promoPageCode.getData().isNextPage());
              restResponsePage.setLastPage(promoPageCode.getData().isLast());
              restResponsePage.setSort(promoPageCode.getData().getSort());
              promoPageCode.setData(restResponsePage);
            }

            singleEmitter.onSuccess(promoPageCode);
          } catch (Exception e) {
            singleEmitter.onError(e);
          }
        }).subscribeOn(Schedulers.io());
  }

  private boolean isExistRestResponsePage(RestResponsePage<PromoCodeAdjustmentResponse> data) {
    return data != null;
  }

  private void generateAllowedActions(PromoCodeAdjustmentResponse response, String privilegeToCheck) {
    if (response.getPromoCodeAdjustmentStatus().equals(PromoCodeAdjustmentStatus.DRAFT) && privilegeToCheck.contains
        (PrivilegeId.PENDING_PROMO_ADJUSTMENT)) {
      response.setAllowedActions(Arrays.asList("pending"));
    } else if (response.getPromoCodeAdjustmentStatus().equals(PromoCodeAdjustmentStatus.PENDING) && privilegeToCheck
        .contains(PrivilegeId.ACTIVATE_PROMO_ADJUSTMENT)) {
      response.setAllowedActions(Arrays.asList("activate"));
    } else if (response.getPromoCodeAdjustmentStatus().equals(PromoCodeAdjustmentStatus.ACTIVE) && privilegeToCheck
        .contains(PrivilegeId.UNACTIVATE_PROMO_ADJUSTMENT)) {
      response.setAllowedActions(Arrays.asList("unActivate"));
    }
  }


  @Override
  public Single<GatewayBaseResponse<PromoCodeAdjustmentResponse>> createPromoCodeAdjustment(MandatoryRequest mandatoryRequest,
      PromoCodeAdjustmentRequest promoCodeRequest) {
    return Single.<GatewayBaseResponse<PromoCodeAdjustmentResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<PromoCodeAdjustmentResponse>> promoCodeAdjustmentResponse = this
            .promoCodeEndPointService
            .createPromoCodeAdjustment(MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                promoCodeRequest).execute();
        GatewayBaseResponse<PromoCodeAdjustmentResponse> promoPage = promoCodeAdjustmentResponse.body();

        singleEmitter.onSuccess(promoPage);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<PromoCodeAdjustmentResponse>> updatePromoCodeAdjustment(
      MandatoryRequest mandatoryRequest,
      PromoCodeAdjustmentRequest promoCodeRequest, String id) {
    return Single.<GatewayBaseResponse<PromoCodeAdjustmentResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<PromoCodeAdjustmentResponse>> promoCodeResponse = this.promoCodeEndPointService
            .updatePromoCodeAdjustment(MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                promoCodeRequest, id).execute();
        GatewayBaseResponse<PromoCodeAdjustmentResponse> promoCodeResponseGateway = promoCodeResponse.body();

        singleEmitter.onSuccess(promoCodeResponseGateway);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<Boolean>> deletePromoCodeAdjustment(MandatoryRequest mandatoryRequest,
      String id) {
    return Single.<GatewayBaseResponse<Boolean>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<Boolean>> promoCodeResponse = this.promoCodeEndPointService
            .deletePromoCodeAdjustment
                (MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id)
            .execute();
        GatewayBaseResponse<Boolean> success = promoCodeResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<PromoCodeAdjustmentResponse>> findPromoCodeAdjustmentById(
      MandatoryRequest mandatoryRequest,
      String id) {
    return Single.<GatewayBaseResponse<PromoCodeAdjustmentResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<PromoCodeAdjustmentResponse>> promoCodeResponse = this.promoCodeEndPointService
            .findPromoCodeAdjustmentById
                (MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id)
            .execute();
        GatewayBaseResponse<PromoCodeAdjustmentResponse> success = promoCodeResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }



    @Override
  public Single<GatewayBaseResponse<PromoCodeAdjustmentResponse>> updateStatusActivatePromoCodeAdjustment(
      MandatoryRequest mandatoryRequest,
      String id) {
    return Single.<GatewayBaseResponse<PromoCodeAdjustmentResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<PromoCodeAdjustmentResponse>> promoCodeAdjustmentResponse = this.promoCodeEndPointService
            .updateStatusActivatePromoCodeAdjustment(
                MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id)
            .execute();
        GatewayBaseResponse<PromoCodeAdjustmentResponse> promoCategoryPage = promoCodeAdjustmentResponse.body();

        singleEmitter.onSuccess(promoCategoryPage);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<PromoCodeAdjustmentResponse>> updateStatusUnActivatePromoCodeAdjustment(
      MandatoryRequest mandatoryRequest,
      String id) {
    return Single.<GatewayBaseResponse<PromoCodeAdjustmentResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<PromoCodeAdjustmentResponse>> promoCodeAdjustmentResponse = this.promoCodeEndPointService
            .updateStatusUnActivatePromoCodeAdjustment(
                MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id)
            .execute();
        GatewayBaseResponse<PromoCodeAdjustmentResponse> promoCategoryPage = promoCodeAdjustmentResponse.body();

        singleEmitter.onSuccess(promoCategoryPage);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<PromoCodeAdjustmentResponse>> updateStatusPendingPromoCodeAdjustment(
      MandatoryRequest mandatoryRequest,
      String id) {
    return Single.<GatewayBaseResponse<PromoCodeAdjustmentResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<PromoCodeAdjustmentResponse>> promoCodeAdjustmentResponse = this.promoCodeEndPointService
            .updateStatusPendingPromoCodeAdjustment(
                MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id)
            .execute();
        GatewayBaseResponse<PromoCodeAdjustmentResponse> promoCategoryPage = promoCodeAdjustmentResponse.body();

        singleEmitter.onSuccess(promoCategoryPage);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<PromoCodeAdjustmentResponse>> updateStatusRejectedPromoCodeAdjustment(
      MandatoryRequest mandatoryRequest,
      String id) {
    return Single.<GatewayBaseResponse<PromoCodeAdjustmentResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<PromoCodeAdjustmentResponse>> promoCodeAdjustmentResponse = this.promoCodeEndPointService
            .updateStatusRejectedPromoCodeAdjustment(
                MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id)
            .execute();
        GatewayBaseResponse<PromoCodeAdjustmentResponse> promoCategoryPage = promoCodeAdjustmentResponse.body();

        singleEmitter.onSuccess(promoCategoryPage);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<List<PromoCodeAdjustmentDropdownResponse>>> findPromoCodeAdjustmentActivate(
      MandatoryRequest mandatoryRequest) {
    return Single.<GatewayBaseResponse<List<PromoCodeAdjustmentDropdownResponse>>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<List<PromoCodeAdjustmentDropdownResponse>>> promoCodeAdjustmentResponse = this.promoCodeEndPointService
            .findPromoCodeAdjustmentActivate(
                MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest))
            .execute();
        GatewayBaseResponse<List<PromoCodeAdjustmentDropdownResponse>> promoCategoryPage = promoCodeAdjustmentResponse.body();

        singleEmitter.onSuccess(promoCategoryPage);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  private Boolean isNotNullString(String string) {
    return string != null;
  }

  private Boolean isNotNullBoolean(Boolean isBoolean) {
    return isBoolean != null;
  }

  private boolean isExistSortDirection(SortDirection sortDirection) {
    return sortDirection != null;
  }

  private boolean isExistColumnSort(PromoCodeAdjustmentColumn columnSort) {
    return columnSort != null;
  }
}
