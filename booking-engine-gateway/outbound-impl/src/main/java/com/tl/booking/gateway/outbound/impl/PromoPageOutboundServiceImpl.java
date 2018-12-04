package com.tl.booking.gateway.outbound.impl;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.libraries.utility.MandatoryRequestHelper;
import com.tl.booking.gateway.outbound.api.PromoPageOutboundService;
import com.tl.booking.gateway.outbound.impl.configuration.PromoListEndPointService;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.constant.enums.PromoPageColumn;
import com.tl.booking.gateway.entity.constant.enums.PromoPageStatus;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.fields.PromoPageFields;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoList.PromoPageRequest;
import com.tl.booking.gateway.entity.outbound.PromoList.PromoPageResponse;

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
public class PromoPageOutboundServiceImpl implements PromoPageOutboundService {

  @Autowired
  private PromoListEndPointService promoListEndPointService;

  @Override
  public Single<GatewayBaseResponse<RestResponsePage<PromoPageResponse>>> findPromoPageFilterPaginated(
      MandatoryRequest mandatoryRequest,
      String title,
      String categories,
      PromoPageStatus status,
      Integer precedence,
      Integer page,
      Integer size,
      PromoPageColumn columnSort,
      SortDirection sortDirection,
      String privilegeToCheck) {

    Map<String, String> queryParam = new HashMap<>();

    if (isNotNullString(categories)) {
      queryParam.put(PromoPageFields.CATEGORIES, categories);
    }

    if (isNotNullString(title)) {
      queryParam.put(PromoPageFields.TITLE, title);
    }

    if (isNotNullStatus(status)) {
      queryParam.put(PromoPageFields.STATUS, status.getCode());
    }

    if (isNotNullInteger(precedence)) {
      queryParam.put(PromoPageFields.PRECEDENCE, precedence.toString());
    }

    queryParam.put("page", page.toString());
    queryParam.put("size", size.toString());

    if (isExistColumnSort(columnSort)) {
      queryParam.put("columnSort", columnSort.getName());
    }
    if (isExistSortDirection(sortDirection)) {
      queryParam.put("sortDirection", sortDirection.getName());
    }

    return Single.<GatewayBaseResponse<RestResponsePage<PromoPageResponse>>>create(
        singleEmitter -> {
          try {
            Response<GatewayBaseResponse<RestResponsePage<PromoPageResponse>>> promoPageResponse = this
                .promoListEndPointService
                .findPromoPageFilterPaginated
                    (
                        MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                        queryParam)
                .execute();

            GatewayBaseResponse<RestResponsePage<PromoPageResponse>> promoPagePage = promoPageResponse
                .body();

            if (isExistRestResponsePage(promoPagePage.getData())) {
              List<PromoPageResponse> promoPageResponses = promoPagePage.getData().getContent();
              for (PromoPageResponse response : promoPageResponses) {
                response.setAllowedActions(new ArrayList<>());
                this.generateAllowedActions(response, privilegeToCheck);
              }

              RestResponsePage<PromoPageResponse> pageResponseRestResponsePage = new
                  RestResponsePage<>();
              pageResponseRestResponsePage.setContent(promoPageResponses);
              pageResponseRestResponsePage.setFirstPage(promoPagePage.getData().isFirstPage());
              pageResponseRestResponsePage.setNumber(promoPagePage.getData().getNumber());
              pageResponseRestResponsePage.setSize(promoPagePage.getData().getSize());
              pageResponseRestResponsePage.setTotalPages(promoPagePage.getData().getTotalPages());
              pageResponseRestResponsePage
                  .setNumberOfElements(promoPagePage.getData().getNumberOfElements());
              pageResponseRestResponsePage
                  .setTotalElements(promoPagePage.getData().getTotalElements());
              pageResponseRestResponsePage
                  .setPreviousPage(promoPagePage.getData().isPreviousPage());
              pageResponseRestResponsePage.setNextPage(promoPagePage.getData().isNextPage());
              pageResponseRestResponsePage.setLastPage(promoPagePage.getData().isLast());
              pageResponseRestResponsePage.setSort(promoPagePage.getData().getSort());
              promoPagePage.setData(pageResponseRestResponsePage);
            }

            singleEmitter.onSuccess(promoPagePage);
          } catch (Exception e) {
            singleEmitter.onError(e);
          }
        }).subscribeOn(Schedulers.io());
  }

  private boolean isExistRestResponsePage(RestResponsePage<PromoPageResponse> data) {
    return data != null;
  }

  private void generateAllowedActions(PromoPageResponse response, String privilegeToCheck) {
    if (response.getStatus().equals(PromoPageStatus.DRAFT) && privilegeToCheck.contains
        (PrivilegeId.REQUEST_APPROVAL_PROMO_LIST)) {
      response.setAllowedActions(Arrays.asList("request_approval_promo_list"));
    } else if (response.getStatus().equals(PromoPageStatus.PENDING) && privilegeToCheck.contains
        (PrivilegeId.ACTIVATE_PROMO_LIST)) {
      response.setAllowedActions(Arrays.asList("activate_promo_list"));
    } else if (response.getStatus().equals(PromoPageStatus.ACTIVE) && privilegeToCheck.contains
        (PrivilegeId.UNACTIVATE_PROMO_LIST)) {
      response.setAllowedActions(Arrays.asList("unactivate_promo_list"));
    }
  }


  @Override
  public Single<GatewayBaseResponse<PromoPageResponse>> createPromoPage(
      MandatoryRequest mandatoryRequest,
      PromoPageRequest promoPageRequest) {
    return Single.<GatewayBaseResponse<PromoPageResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<PromoPageResponse>> promoPageResponse = this
            .promoListEndPointService
            .createPromoPage(MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                promoPageRequest).execute();
        GatewayBaseResponse<PromoPageResponse> promoPage = promoPageResponse.body();

        singleEmitter.onSuccess(promoPage);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<PromoPageResponse>> updatePromoPage(
      MandatoryRequest mandatoryRequest,
      PromoPageRequest promoPageRequest, String id) {
    return Single.<GatewayBaseResponse<PromoPageResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<PromoPageResponse>> promoPageResponse = this.promoListEndPointService
            .updatePromoPage(MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                promoPageRequest, id).execute();
        GatewayBaseResponse<PromoPageResponse> promoCategoryPage = promoPageResponse.body();

        singleEmitter.onSuccess(promoCategoryPage);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<Boolean>> deletePromoPage(MandatoryRequest mandatoryRequest,
      String id) {
    return Single.<GatewayBaseResponse<Boolean>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<Boolean>> promoPageResponse = this.promoListEndPointService
            .deletePromoPage
                (MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id)
            .execute();
        GatewayBaseResponse<Boolean> success = promoPageResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<PromoPageResponse>> findPromoPageBySlug(
      MandatoryRequest mandatoryRequest,
      String id) {
    return Single.<GatewayBaseResponse<PromoPageResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<PromoPageResponse>> promoPageResponse = this.promoListEndPointService
            .findPromoPageBySlug
                (MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id)
            .execute();
        GatewayBaseResponse<PromoPageResponse> success = promoPageResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }


  @Override
  public Single<GatewayBaseResponse<PromoPageResponse>> updateStatusPendingPromoPage(
      MandatoryRequest mandatoryRequest,
      String id) {
    return Single.<GatewayBaseResponse<PromoPageResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<PromoPageResponse>> promoPageResponse = this.promoListEndPointService
            .updateStatusPendingPromoPage(
                MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id)
            .execute();
        GatewayBaseResponse<PromoPageResponse> promoCategoryPage = promoPageResponse.body();

        singleEmitter.onSuccess(promoCategoryPage);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<PromoPageResponse>> updateStatusActivatedPromoPage(
      MandatoryRequest mandatoryRequest,
      String id) {
    return Single.<GatewayBaseResponse<PromoPageResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<PromoPageResponse>> promoPageResponse = this.promoListEndPointService
            .updateStatusActivatedPromoPage(
                MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id)
            .execute();
        GatewayBaseResponse<PromoPageResponse> promoCategoryPage = promoPageResponse.body();

        singleEmitter.onSuccess(promoCategoryPage);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<PromoPageResponse>> updateStatusInActivatedPromoPage(
      MandatoryRequest mandatoryRequest,
      String id) {
    return Single.<GatewayBaseResponse<PromoPageResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<PromoPageResponse>> promoPageResponse = this.promoListEndPointService
            .updateStatusInActivatedPromoPage(
                MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id)
            .execute();
        GatewayBaseResponse<PromoPageResponse> promoCategoryPage = promoPageResponse.body();

        singleEmitter.onSuccess(promoCategoryPage);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<PromoPageResponse>> updateStatusRejectedPromoPage(
      MandatoryRequest mandatoryRequest,
      String id) {
    return Single.<GatewayBaseResponse<PromoPageResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<PromoPageResponse>> promoPageResponse = this.promoListEndPointService
            .updateStatusRejectedPromoPage(
                MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id)
            .execute();
        GatewayBaseResponse<PromoPageResponse> promoCategoryPage = promoPageResponse.body();

        singleEmitter.onSuccess(promoCategoryPage);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  private Boolean isNotNullStatus(PromoPageStatus status) {
    return status != null;
  }

  private Boolean isNotNullString(String string) {
    return string != null;
  }

  private Boolean isNotNullInteger(Integer integer) {
    return integer != null;
  }

  private boolean isExistSortDirection(SortDirection sortDirection) {
    return sortDirection != null;
  }

  private boolean isExistColumnSort(PromoPageColumn columnSort) {
    return columnSort != null;
  }
}
