package com.tl.booking.gateway.outbound.impl.promocode;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.libraries.utility.MandatoryRequestHelper;
import com.tl.booking.gateway.outbound.api.promocode.BusinessLogicResponseOutboundService;
import com.tl.booking.gateway.outbound.impl.configuration.PromoCodeEndPointService;
import com.tl.booking.gateway.entity.constant.enums.BusinessLogicResponseColumn;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.fields.BusinessLogicResponseFields;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoCode.BusinessLogicResponseRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.BusinessLogicResponseResponse;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Response;

@Service
public class BusinessLogicOutboundServiceImpl implements BusinessLogicResponseOutboundService {

  @Autowired
  private PromoCodeEndPointService promoCodeEndPointService;

  @Override
  public Single<GatewayBaseResponse<RestResponsePage<BusinessLogicResponseResponse>>> findBusinessLogicResponseFilterPaginated(
      MandatoryRequest mandatoryRequest,
      String responseCode,
      Integer page,
      Integer size,
      BusinessLogicResponseColumn columnSort,
      SortDirection sortDirection,
      String privilegeToCheck
  ) {

    Map<String, String> queryParam = new HashMap<>();

    if (isNotNullString(responseCode)) {
      queryParam.put(BusinessLogicResponseFields.RESPONSE_CODE, responseCode);
    }

    queryParam.put("page", page.toString());
    queryParam.put("size", size.toString());

    if (isExistColumnSort(columnSort)) {
      queryParam.put("columnSort", columnSort.getName());
    }
    if (isExistSortDirection(sortDirection)) {
      queryParam.put("sortDirection", sortDirection.getName());
    }

    return Single.<GatewayBaseResponse<RestResponsePage<BusinessLogicResponseResponse>>>create(
        singleEmitter -> {
          try {
            Response<GatewayBaseResponse<RestResponsePage<BusinessLogicResponseResponse>>> promoCodeResponse = this
                .promoCodeEndPointService
                .findBusinessLogicResponseFilterPaginated
                    (
                        MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                        queryParam)
                .execute();

            GatewayBaseResponse<RestResponsePage<BusinessLogicResponseResponse>> promoPageCode = promoCodeResponse
                .body();

            if (isExistRestResponsePage(promoPageCode.getData())) {
              List<BusinessLogicResponseResponse> promoCodeResponses = promoPageCode.getData().getContent();

              RestResponsePage<BusinessLogicResponseResponse> restResponsePage = new
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

  private boolean isExistRestResponsePage(RestResponsePage<BusinessLogicResponseResponse> data) {
    return data != null;
  }

  @Override
  public Single<GatewayBaseResponse<BusinessLogicResponseResponse>> createBusinessLogicResponse(MandatoryRequest mandatoryRequest,
      BusinessLogicResponseRequest variableRequest) {
    return Single.<GatewayBaseResponse<BusinessLogicResponseResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<BusinessLogicResponseResponse>> businessLogicResponseResponse = this
            .promoCodeEndPointService
            .createBusinessLogicResponse(MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                variableRequest).execute();
        GatewayBaseResponse<BusinessLogicResponseResponse> promoPage = businessLogicResponseResponse.body();

        singleEmitter.onSuccess(promoPage);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<BusinessLogicResponseResponse>> updateBusinessLogicResponse(
      MandatoryRequest mandatoryRequest,
      BusinessLogicResponseRequest variableRequest, String id) {
    return Single.<GatewayBaseResponse<BusinessLogicResponseResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<BusinessLogicResponseResponse>> promoCodeResponse = this.promoCodeEndPointService
            .updateBusinessLogicResponse(MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                variableRequest, id).execute();
        GatewayBaseResponse<BusinessLogicResponseResponse> promoCodeResponseGateway = promoCodeResponse.body();

        singleEmitter.onSuccess(promoCodeResponseGateway);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<Boolean>> deleteBusinessLogicResponse(MandatoryRequest mandatoryRequest,
      String id) {
    return Single.<GatewayBaseResponse<Boolean>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<Boolean>> promoCodeResponse = this.promoCodeEndPointService
            .deleteBusinessLogic
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
  public Single<GatewayBaseResponse<BusinessLogicResponseResponse>> findBusinessLogicResponseById(
      MandatoryRequest mandatoryRequest,
      String id) {
    return Single.<GatewayBaseResponse<BusinessLogicResponseResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<BusinessLogicResponseResponse>> promoCodeResponse = this.promoCodeEndPointService
            .findBusinessLogicResponseById
                (MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id)
            .execute();
        GatewayBaseResponse<BusinessLogicResponseResponse> success = promoCodeResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  public Single<GatewayBaseResponse<List<BusinessLogicResponseResponse>>> findBusinessLogicResponses(MandatoryRequest mandatoryRequest)
  {
    return Single.<GatewayBaseResponse<List<BusinessLogicResponseResponse>>>create(singleEmitter -> {
      try {

        Response<GatewayBaseResponse<List<BusinessLogicResponseResponse>>> promoCodeResponse = this.promoCodeEndPointService
            .findBusinessLogicResponse
                (MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest))
            .execute();
        GatewayBaseResponse<List<BusinessLogicResponseResponse>> success = promoCodeResponse.body();

        singleEmitter.onSuccess(success);
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

  private boolean isExistColumnSort(BusinessLogicResponseColumn columnSort) {
    return columnSort != null;
  }
}
