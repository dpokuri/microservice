package com.tl.booking.gateway.outbound.impl.promocode;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.libraries.utility.MandatoryRequestHelper;
import com.tl.booking.gateway.outbound.api.promocode.PromoCodeOutboundService;
import com.tl.booking.gateway.outbound.impl.configuration.PromoCodeEndPointService;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.constant.enums.PromoCodeColumn;
import com.tl.booking.gateway.entity.constant.enums.PromoCodeStatus;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.fields.PromoCodeFields;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeResponse;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.SpanName;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import retrofit2.Response;

@Service
public class PromoCodeOutboundServiceImpl implements PromoCodeOutboundService {

  @Autowired
  private PromoCodeEndPointService promoCodeEndPointService;

  @Autowired
  private Tracer tracer;


  /* Code Added line: 47 - 48 and 37-38
   *
   * Here we added @Async and spanName annotations to capture traceId using Sleuth.
   *
   * <This change is temporary as we need to finalize the best way to integrate sleuth traces with stack driver >
   */
  @Async
  @SpanName("promocode2")
  @Override
  public Single<GatewayBaseResponse<RestResponsePage<PromoCodeResponse>>> findPromoCodeFilterPaginated(
      MandatoryRequest mandatoryRequest,
      String code,
      String campaignId,
      Integer page,
      Integer size,
      PromoCodeColumn columnSort,
      SortDirection sortDirection,
      String privilegeToCheck
  ) {

    Map<String, String> queryParam = new HashMap<>();

    if (isNotNullString(code)) {
      queryParam.put(PromoCodeFields.CODE, code);
    }

    if (isNotNullString(campaignId)) {
      queryParam.put(PromoCodeFields.CAMPAIGN_ID, campaignId);
    }

    queryParam.put("page", page.toString());
    queryParam.put("size", size.toString());

    if (isExistColumnSort(columnSort)) {
      queryParam.put("columnSort", columnSort.getName());
    }
    if (isExistSortDirection(sortDirection)) {
      queryParam.put("sortDirection", sortDirection.getName());
    }


    /* Code Added line: 87, 88, 97 and 133 - 135
     *
     * Here we are creating Span and retrieving current traceId and adding to RequestHeader and passing to Promo code Service.
     *
     * <This change is temporary as we need to finalize the best way to integrate sleuth traces with stack driver >
     */
    Span span = this.tracer.createSpan("promocode-span");
    String traceId = span.traceIdString();

    return Single.<GatewayBaseResponse<RestResponsePage<PromoCodeResponse>>>create(
        singleEmitter -> {
          try {
            Response<GatewayBaseResponse<RestResponsePage<PromoCodeResponse>>> promoCodeResponse = this
                .promoCodeEndPointService
                .findPromoCodeFilterPaginated
                    (
                        MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest, traceId),
                        queryParam)
                .execute();

            GatewayBaseResponse<RestResponsePage<PromoCodeResponse>> promoPageCode = promoCodeResponse
                .body();

            if (isExistRestResponsePage(promoPageCode.getData())) {
              List<PromoCodeResponse> promoCodeResponses = promoPageCode.getData().getContent();
              for (PromoCodeResponse response : promoCodeResponses) {
                response.setAllowedActions(new ArrayList<>());
                this.generateAllowedActions(response, privilegeToCheck);
              }

              RestResponsePage<PromoCodeResponse> restResponsePage = new
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
          }finally {
            this.tracer.close(span);
          }
        }).subscribeOn(Schedulers.io());
  }

  private boolean isExistRestResponsePage(RestResponsePage<PromoCodeResponse> data) {
    return data != null;
  }

  private void generateAllowedActions(PromoCodeResponse response, String privilegeToCheck) {
    if (response.getPromoCodeStatus().equals(PromoCodeStatus.INACTIVE) && privilegeToCheck.contains
        (PrivilegeId.ACTIVATE_PROMO_CODE)) {
      response.setAllowedActions(Arrays.asList("activate_promo_code"));
    } else if (response.getPromoCodeStatus().equals(PromoCodeStatus.ACTIVE) && privilegeToCheck.contains
        (PrivilegeId.UNACTIVATE_PROMO_CODE)) {
      response.setAllowedActions(Arrays.asList("unActivate_promo_code"));
    }
  }


  @Override
  public Single<GatewayBaseResponse<PromoCodeResponse>> createPromoCode(MandatoryRequest mandatoryRequest,
      PromoCodeRequest promoCodeRequest) {
    return Single.<GatewayBaseResponse<PromoCodeResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<PromoCodeResponse>> promoCodeResponse = this
            .promoCodeEndPointService
            .createPromoCode(MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                promoCodeRequest).execute();
        GatewayBaseResponse<PromoCodeResponse> promoPage = promoCodeResponse.body();

        singleEmitter.onSuccess(promoPage);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<PromoCodeResponse>> updatePromoCode(
      MandatoryRequest mandatoryRequest,
      PromoCodeRequest promoCodeRequest, String id) {
    return Single.<GatewayBaseResponse<PromoCodeResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<PromoCodeResponse>> promoCodeResponse = this.promoCodeEndPointService
            .updatePromoCode(MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                promoCodeRequest, id).execute();
        GatewayBaseResponse<PromoCodeResponse> promoCodeResponseGateway = promoCodeResponse.body();

        singleEmitter.onSuccess(promoCodeResponseGateway);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<Boolean>> deletePromoCode(MandatoryRequest mandatoryRequest,
      String id) {
    return Single.<GatewayBaseResponse<Boolean>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<Boolean>> promoCodeResponse = this.promoCodeEndPointService
            .deletePromoCode
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
  public Single<GatewayBaseResponse<PromoCodeResponse>> findPromoCodeById(
      MandatoryRequest mandatoryRequest,
      String id) {
    return Single.<GatewayBaseResponse<PromoCodeResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<PromoCodeResponse>> promoCodeResponse = this.promoCodeEndPointService
            .findPromoCodeById
                (MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id)
            .execute();
        GatewayBaseResponse<PromoCodeResponse> success = promoCodeResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }



    @Override
  public Single<GatewayBaseResponse<PromoCodeResponse>> updateStatusActivatePromoCode(
      MandatoryRequest mandatoryRequest,
      String id) {
    return Single.<GatewayBaseResponse<PromoCodeResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<PromoCodeResponse>> promoCodeResponse = this.promoCodeEndPointService
            .updateStatusActivatePromoCode(
                MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id)
            .execute();
        GatewayBaseResponse<PromoCodeResponse> promoCategoryPage = promoCodeResponse.body();

        singleEmitter.onSuccess(promoCategoryPage);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<PromoCodeResponse>> updateStatusUnActivatePromoCode(
      MandatoryRequest mandatoryRequest,
      String id) {
    return Single.<GatewayBaseResponse<PromoCodeResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<PromoCodeResponse>> promoCodeResponse = this.promoCodeEndPointService
            .updateStatusUnActivatePromoCode(
                MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id)
            .execute();
        GatewayBaseResponse<PromoCodeResponse> promoCategoryPage = promoCodeResponse.body();

        singleEmitter.onSuccess(promoCategoryPage);
      } catch (Exception e) {
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

  private boolean isExistColumnSort(PromoCodeColumn columnSort) {
    return columnSort != null;
  }
}
