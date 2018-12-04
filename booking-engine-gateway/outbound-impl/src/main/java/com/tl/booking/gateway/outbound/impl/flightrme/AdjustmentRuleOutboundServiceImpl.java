package com.tl.booking.gateway.outbound.impl.flightrme;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.libraries.utility.MandatoryRequestHelper;
import com.tl.booking.gateway.outbound.api.flightrme.AdjustmentRuleOutboundService;
import com.tl.booking.gateway.outbound.impl.configuration.FlightRMEEndPointService;
import com.tl.booking.gateway.entity.constant.enums.AdjustmentRuleColumn;
import com.tl.booking.gateway.entity.constant.enums.AdjustmentRuleStatus;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.fields.AdjustmentRuleFields;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.flightrme.AdjustmentRuleRequest;
import com.tl.booking.gateway.entity.outbound.flightrme.AdjustmentRuleResponse;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import retrofit2.Response;

public class AdjustmentRuleOutboundServiceImpl implements AdjustmentRuleOutboundService {

  @Autowired
  private FlightRMEEndPointService flightRMEEndPointService;

  @Override
  public Single<GatewayBaseResponse<RestResponsePage<AdjustmentRuleResponse>>> findAdjustmentRuleFilterPaginated(
      MandatoryRequest mandatoryRequest,
      String airline,
      String origin,
      String destination,
      AdjustmentRuleStatus adjustmentRuleStatus,
      Integer page,
      Integer size,
      AdjustmentRuleColumn columnSort,
      SortDirection sortDirection,
      String privilegeToCheck
  ) {

    Map<String, String> queryParam = new HashMap<>();

    if(isNotNullString(airline)){
      queryParam.put(AdjustmentRuleFields.AIRLINE, airline);
    }

    if (isNotNullString(origin)) {
      queryParam.put(AdjustmentRuleFields.ORIGIN, origin);
    }

    if (isNotNullString(destination)) {
      queryParam.put(AdjustmentRuleFields.DESTINATION, destination);
    }

    if (isExistAdjustmentRuleStatus(adjustmentRuleStatus)){
      queryParam.put(AdjustmentRuleFields.STATUS, adjustmentRuleStatus.getCode());
    }

    queryParam.put("page", page.toString());
    queryParam.put("size", size.toString());

    if (isExistColumnSort(columnSort)) {
      queryParam.put("columnSort", columnSort.getName());
    }
    if (isExistSortDirection(sortDirection)) {
      queryParam.put("sortDirection", sortDirection.getName());
    }

    return Single.<GatewayBaseResponse<RestResponsePage<AdjustmentRuleResponse>>>create(
        singleEmitter -> {
          try {
            Response<GatewayBaseResponse<RestResponsePage<AdjustmentRuleResponse>>> adjustmentRuleResponse = this
                .flightRMEEndPointService
                .findAdjustmentRuleFilterPaginated
                    (
                        MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                        queryParam)
                .execute();

            GatewayBaseResponse<RestResponsePage<AdjustmentRuleResponse>> adjustmentRulePageCode = adjustmentRuleResponse
                .body();

            if (isExistRestResponsePage(adjustmentRulePageCode.getData())) {
              List<AdjustmentRuleResponse> adjustmentRuleResponses = adjustmentRulePageCode.getData().getContent();

              RestResponsePage<AdjustmentRuleResponse> restResponsePage = new
                  RestResponsePage<>();
              restResponsePage.setContent(adjustmentRuleResponses);
              restResponsePage.setFirstPage(adjustmentRulePageCode.getData().isFirstPage());
              restResponsePage.setNumber(adjustmentRulePageCode.getData().getNumber());
              restResponsePage.setSize(adjustmentRulePageCode.getData().getSize());
              restResponsePage.setTotalPages(adjustmentRulePageCode.getData().getTotalPages());
              restResponsePage
                  .setNumberOfElements(adjustmentRulePageCode.getData().getNumberOfElements());
              restResponsePage
                  .setTotalElements(adjustmentRulePageCode.getData().getTotalElements());
              restResponsePage
                  .setPreviousPage(adjustmentRulePageCode.getData().isPreviousPage());
              restResponsePage.setNextPage(adjustmentRulePageCode.getData().isNextPage());
              restResponsePage.setLastPage(adjustmentRulePageCode.getData().isLast());
              restResponsePage.setSort(adjustmentRulePageCode.getData().getSort());
              adjustmentRulePageCode.setData(restResponsePage);
            }

            singleEmitter.onSuccess(adjustmentRulePageCode);
          } catch (Exception e) {
            singleEmitter.onError(e);
          }
        }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<AdjustmentRuleResponse>> createAdjustmentRule(MandatoryRequest mandatoryRequest,
      AdjustmentRuleRequest adjustmentRuleRequest) {
    return Single.<GatewayBaseResponse<AdjustmentRuleResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<AdjustmentRuleResponse>> adjustmentRuleResponse = this
            .flightRMEEndPointService
            .createAdjustmentRule(MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                adjustmentRuleRequest).execute();
        GatewayBaseResponse<AdjustmentRuleResponse> adjustmentRule = adjustmentRuleResponse.body();

        singleEmitter.onSuccess(adjustmentRule);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<AdjustmentRuleResponse>> updateAdjustmentRule(
      MandatoryRequest mandatoryRequest,
      AdjustmentRuleRequest adjustmentRuleRequest, String id) {
    return Single.<GatewayBaseResponse<AdjustmentRuleResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<AdjustmentRuleResponse>> adjustmentRuleResponse = this
            .flightRMEEndPointService
            .updateAdjustmentRule(MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                adjustmentRuleRequest, id).execute();
        GatewayBaseResponse<AdjustmentRuleResponse> adjustmentRule = adjustmentRuleResponse.body();

        singleEmitter.onSuccess(adjustmentRule);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<AdjustmentRuleResponse>> updateStatusPendingAdjustmentRule(
      MandatoryRequest mandatoryRequest,
      String id) {
    return Single.<GatewayBaseResponse<AdjustmentRuleResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<AdjustmentRuleResponse>> adjustmentRuleResponse = this.flightRMEEndPointService
            .updateStatusPendingAdjustmentRule(
                MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id)
            .execute();
        GatewayBaseResponse<AdjustmentRuleResponse> adjustmentRule = adjustmentRuleResponse.body();

        singleEmitter.onSuccess(adjustmentRule);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<AdjustmentRuleResponse>> updateStatusActivateAdjustmentRule(
      MandatoryRequest mandatoryRequest,
      String id) {
    return Single.<GatewayBaseResponse<AdjustmentRuleResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<AdjustmentRuleResponse>> adjustmentRuleResponse = this.flightRMEEndPointService
            .updateStatusActivateAdjustmentRule(
                MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id)
            .execute();
        GatewayBaseResponse<AdjustmentRuleResponse> adjustmentRule = adjustmentRuleResponse.body();

        singleEmitter.onSuccess(adjustmentRule);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<AdjustmentRuleResponse>> updateStatusUnActivateAdjustmentRule(
      MandatoryRequest mandatoryRequest,
      String id) {
    return Single.<GatewayBaseResponse<AdjustmentRuleResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<AdjustmentRuleResponse>> adjustmentRuleResponse = this.flightRMEEndPointService
            .updateStatusUnActivateAdjustmentRule(
                MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id)
            .execute();
        GatewayBaseResponse<AdjustmentRuleResponse> adjustmentRule = adjustmentRuleResponse.body();

        singleEmitter.onSuccess(adjustmentRule);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<AdjustmentRuleResponse>> updateStatusRejectedAdjustmentRule(
      MandatoryRequest mandatoryRequest,
      String id) {
    return Single.<GatewayBaseResponse<AdjustmentRuleResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<AdjustmentRuleResponse>> adjustmentRuleResponse = this.flightRMEEndPointService
            .updateStatusRejectedAdjustmentRule(
                MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id)
            .execute();
        GatewayBaseResponse<AdjustmentRuleResponse> adjustmentRule = adjustmentRuleResponse.body();

        singleEmitter.onSuccess(adjustmentRule);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<Boolean>> deleteAdjustmentRule(MandatoryRequest mandatoryRequest,
      String id) {
    return Single.<GatewayBaseResponse<Boolean>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<Boolean>> adjustmentRuleResponse = this.flightRMEEndPointService
            .deleteAdjustmentRule
                (MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id)
            .execute();
        GatewayBaseResponse<Boolean> success = adjustmentRuleResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<AdjustmentRuleResponse>> findAdjustmentRuleById(
      MandatoryRequest mandatoryRequest,
      String id) {
    return Single.<GatewayBaseResponse<AdjustmentRuleResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<AdjustmentRuleResponse>> adjustmentRuleResponse = this.flightRMEEndPointService
            .findAdjustmentById
                (MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id)
            .execute();
        GatewayBaseResponse<AdjustmentRuleResponse> success = adjustmentRuleResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  private boolean isExistRestResponsePage(RestResponsePage<AdjustmentRuleResponse> data) {
    return data != null;
  }

  private Boolean isNotNullString(String string) {
    return string != null;
  }

  private boolean isExistAdjustmentRuleStatus(AdjustmentRuleStatus adjustmentRuleStatus) {
    return adjustmentRuleStatus != null;
  }

  private boolean isExistSortDirection(SortDirection sortDirection) {
    return sortDirection != null;
  }

  private boolean isExistColumnSort(AdjustmentRuleColumn columnSort) {
    return columnSort != null;
  }
}
