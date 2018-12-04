package com.tl.booking.gateway.outbound.impl.promocode;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.libraries.utility.MandatoryRequestHelper;
import com.tl.booking.gateway.outbound.api.promocode.VariableOutboundService;
import com.tl.booking.gateway.outbound.impl.configuration.PromoCodeEndPointService;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.enums.VariableColumn;
import com.tl.booking.gateway.entity.constant.fields.VariableFields;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoCode.VariableFindAllResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.VariableRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.VariableResponse;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Response;

@Service
public class VariableOutboundServiceImpl implements VariableOutboundService {

  @Autowired
  private PromoCodeEndPointService promoCodeEndPointService;

  @Override
  public Single<GatewayBaseResponse<RestResponsePage<VariableResponse>>> findVariableFilterPaginated(
      MandatoryRequest mandatoryRequest,
      String param,
      String inputType,
      Integer page,
      Integer size,
      VariableColumn columnSort,
      SortDirection sortDirection,
      String privilegeToCheck
  ) {

    Map<String, String> queryParam = new HashMap<>();

    if (isNotNullString(param)) {
      queryParam.put(VariableFields.PARAM, param);
    }

    if (isNotNullString(inputType)) {
      queryParam.put(VariableFields.INPUT_TYPE, inputType);
    }


    queryParam.put("page", page.toString());
    queryParam.put("size", size.toString());

    if (isExistColumnSort(columnSort)) {
      queryParam.put("columnSort", columnSort.getName());
    }
    if (isExistSortDirection(sortDirection)) {
      queryParam.put("sortDirection", sortDirection.getName());
    }

    return Single.<GatewayBaseResponse<RestResponsePage<VariableResponse>>>create(
        singleEmitter -> {
          try {
            Response<GatewayBaseResponse<RestResponsePage<VariableResponse>>> promoCodeResponse = this
                .promoCodeEndPointService
                .findVariableFilterPaginated
                    (
                        MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                        queryParam)
                .execute();

            GatewayBaseResponse<RestResponsePage<VariableResponse>> promoPageCode = promoCodeResponse
                .body();

            if (isExistRestResponsePage(promoPageCode.getData())) {
              List<VariableResponse> promoCodeResponses = promoPageCode.getData().getContent();

              RestResponsePage<VariableResponse> restResponsePage = new
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

  private boolean isExistRestResponsePage(RestResponsePage<VariableResponse> data) {
    return data != null;
  }

  @Override
  public Single<GatewayBaseResponse<VariableResponse>> createVariable(MandatoryRequest mandatoryRequest,
      VariableRequest variableRequest) {
    return Single.<GatewayBaseResponse<VariableResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<VariableResponse>> variableResponse = this
            .promoCodeEndPointService
            .createVariable(MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                variableRequest).execute();
        GatewayBaseResponse<VariableResponse> promoPage = variableResponse.body();

        singleEmitter.onSuccess(promoPage);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<VariableResponse>> updateVariable(
      MandatoryRequest mandatoryRequest,
      VariableRequest variableRequest, String id) {
    return Single.<GatewayBaseResponse<VariableResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<VariableResponse>> promoCodeResponse = this.promoCodeEndPointService
            .updateVariable(MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                variableRequest, id).execute();
        GatewayBaseResponse<VariableResponse> promoCodeResponseGateway = promoCodeResponse.body();

        singleEmitter.onSuccess(promoCodeResponseGateway);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<Boolean>> deleteVariable(MandatoryRequest mandatoryRequest,
      String id) {
    return Single.<GatewayBaseResponse<Boolean>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<Boolean>> promoCodeResponse = this.promoCodeEndPointService
            .deleteVariable
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
  public Single<GatewayBaseResponse<VariableResponse>> findVariableById(
      MandatoryRequest mandatoryRequest,
      String id) {
    return Single.<GatewayBaseResponse<VariableResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<VariableResponse>> promoCodeResponse = this.promoCodeEndPointService
            .findVariableById
                (MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id)
            .execute();
        GatewayBaseResponse<VariableResponse> success = promoCodeResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<List<VariableFindAllResponse>>> findAllVariable(
      MandatoryRequest mandatoryRequest) {
    return Single.<GatewayBaseResponse<List<VariableFindAllResponse>>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<List<VariableFindAllResponse>>> promoCodeResponse = this.promoCodeEndPointService
            .findAllVariable
                (MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest))
            .execute();
        GatewayBaseResponse<List<VariableFindAllResponse>> success = promoCodeResponse.body();

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

  private boolean isExistColumnSort(VariableColumn columnSort) {
    return columnSort != null;
  }
}
