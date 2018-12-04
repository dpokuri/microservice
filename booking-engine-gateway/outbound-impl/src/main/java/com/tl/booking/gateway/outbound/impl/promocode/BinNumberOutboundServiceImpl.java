package com.tl.booking.gateway.outbound.impl.promocode;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.libraries.utility.MandatoryRequestHelper;
import com.tl.booking.gateway.outbound.api.promocode.BinNumberOutboundService;
import com.tl.booking.gateway.outbound.impl.configuration.PromoCodeEndPointService;
import com.tl.booking.gateway.entity.constant.enums.BinNumberColumn;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.fields.BinNumberFields;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoCode.BinNumberRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.BinNumberResponse;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Response;

@Service
public class BinNumberOutboundServiceImpl implements BinNumberOutboundService {

  @Autowired
  private PromoCodeEndPointService promoCodeEndPointService;

  @Override
  public Single<GatewayBaseResponse<RestResponsePage<BinNumberResponse>>> findBinNumberFilterPaginated(
      MandatoryRequest mandatoryRequest,
      String binNumber,
      Integer page,
      Integer size,
      BinNumberColumn columnSort,
      SortDirection sortDirection,
      String privilegeToCheck
  ) {

    Map<String, String> queryParam = new HashMap<>();

    if (isNotNullString(binNumber)) {
      queryParam.put(BinNumberFields.BIN_NUMBER, binNumber);
    }

    queryParam.put("page", page.toString());
    queryParam.put("size", size.toString());

    if (isExistColumnSort(columnSort)) {
      queryParam.put("columnSort", columnSort.getName());
    }
    if (isExistSortDirection(sortDirection)) {
      queryParam.put("sortDirection", sortDirection.getName());
    }

    return Single.<GatewayBaseResponse<RestResponsePage<BinNumberResponse>>>create(
        singleEmitter -> {
          try {
            Response<GatewayBaseResponse<RestResponsePage<BinNumberResponse>>> promoCodeResponse = this
                .promoCodeEndPointService
                .findBinNumberFilterPaginated
                    (
                        MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                        queryParam)
                .execute();

            GatewayBaseResponse<RestResponsePage<BinNumberResponse>> promoPageCode = promoCodeResponse
                .body();

            if (isExistRestResponsePage(promoPageCode.getData())) {
              List<BinNumberResponse> promoCodeResponses = promoPageCode.getData().getContent();

              RestResponsePage<BinNumberResponse> restResponsePage = new
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

  private boolean isExistRestResponsePage(RestResponsePage<BinNumberResponse> data) {
    return data != null;
  }

  @Override
  public Single<GatewayBaseResponse<BinNumberResponse>> createBinNumber(MandatoryRequest mandatoryRequest,
      BinNumberRequest variableRequest) {
    return Single.<GatewayBaseResponse<BinNumberResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<BinNumberResponse>> variableResponse = this
            .promoCodeEndPointService
            .createBinNumber(MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                variableRequest).execute();
        GatewayBaseResponse<BinNumberResponse> promoPage = variableResponse.body();

        singleEmitter.onSuccess(promoPage);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<BinNumberResponse>> updateBinNumber(
      MandatoryRequest mandatoryRequest,
      BinNumberRequest variableRequest, String id) {
    return Single.<GatewayBaseResponse<BinNumberResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<BinNumberResponse>> promoCodeResponse = this.promoCodeEndPointService
            .updateBinNumber(MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                variableRequest, id).execute();
        GatewayBaseResponse<BinNumberResponse> promoCodeResponseGateway = promoCodeResponse.body();

        singleEmitter.onSuccess(promoCodeResponseGateway);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<Boolean>> deleteBinNumber(MandatoryRequest mandatoryRequest,
      String id) {
    return Single.<GatewayBaseResponse<Boolean>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<Boolean>> promoCodeResponse = this.promoCodeEndPointService
            .deleteBinNumber
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
  public Single<GatewayBaseResponse<BinNumberResponse>> findBinNumberById(
      MandatoryRequest mandatoryRequest,
      String id) {
    return Single.<GatewayBaseResponse<BinNumberResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<BinNumberResponse>> promoCodeResponse = this.promoCodeEndPointService
            .findBinNumberById
                (MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id)
            .execute();
        GatewayBaseResponse<BinNumberResponse> success = promoCodeResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  public Single<GatewayBaseResponse<List<String>>> binNumber(MandatoryRequest mandatoryRequest,
      String binNumber, String bankId, String cardTypeId)
  {
    return Single.<GatewayBaseResponse<List<String>>>create(singleEmitter -> {
      try {

        Map<String, String> queryParam = new HashMap<>();

        if (isNotNullString(binNumber)) {
          queryParam.put(BinNumberFields.BIN_NUMBER, binNumber);
        }
        if (isNotNullString(bankId)) {
          queryParam.put(BinNumberFields.BANK_ID, bankId);
        }
        if (isNotNullString(cardTypeId)) {
          queryParam.put(BinNumberFields.CARD_TYPE_ID, cardTypeId);
        }

        Response<GatewayBaseResponse<List<String>>> promoCodeResponse = this.promoCodeEndPointService
            .binNumber
                (MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), queryParam)
            .execute();
        GatewayBaseResponse<List<String>> success = promoCodeResponse.body();

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

  private boolean isExistColumnSort(BinNumberColumn columnSort) {
    return columnSort != null;
  }
}
