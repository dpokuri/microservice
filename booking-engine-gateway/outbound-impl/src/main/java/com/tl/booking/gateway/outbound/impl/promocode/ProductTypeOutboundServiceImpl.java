package com.tl.booking.gateway.outbound.impl.promocode;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.libraries.utility.MandatoryRequestHelper;
import com.tl.booking.gateway.outbound.api.promocode.ProductTypeOutboundService;
import com.tl.booking.gateway.outbound.impl.configuration.PromoCodeEndPointService;
import com.tl.booking.gateway.entity.constant.enums.ProductTypeColumn;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.fields.ProductTypeFields;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoCode.ProductTypeRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.ProductTypeResponse;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Response;

@Service
public class ProductTypeOutboundServiceImpl implements ProductTypeOutboundService {

  @Autowired
  private PromoCodeEndPointService promoCodeEndPointService;

  @Override
  public Single<GatewayBaseResponse<RestResponsePage<ProductTypeResponse>>> findProductTypeFilterPaginated(
      MandatoryRequest mandatoryRequest,
      String name,
      Integer page,
      Integer size,
      ProductTypeColumn columnSort,
      SortDirection sortDirection,
      String privilegeToCheck
  ) {

    Map<String, String> queryParam = new HashMap<>();

    if (isNotNullString(name)) {
      queryParam.put(ProductTypeFields.NAME, name);
    }

    queryParam.put("page", page.toString());
    queryParam.put("size", size.toString());

    if (isExistColumnSort(columnSort)) {
      queryParam.put("columnSort", columnSort.getName());
    }
    if (isExistSortDirection(sortDirection)) {
      queryParam.put("sortDirection", sortDirection.getName());
    }

    return Single.<GatewayBaseResponse<RestResponsePage<ProductTypeResponse>>>create(
        singleEmitter -> {
          try {
            Response<GatewayBaseResponse<RestResponsePage<ProductTypeResponse>>> promoCodeResponse = this
                .promoCodeEndPointService
                .findProductTypeFilterPaginated
                    (
                        MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                        queryParam)
                .execute();

            GatewayBaseResponse<RestResponsePage<ProductTypeResponse>> promoPageCode = promoCodeResponse
                .body();

            if (isExistRestResponsePage(promoPageCode.getData())) {
              List<ProductTypeResponse> promoCodeResponses = promoPageCode.getData().getContent();

              RestResponsePage<ProductTypeResponse> restResponsePage = new
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

  private boolean isExistRestResponsePage(RestResponsePage<ProductTypeResponse> data) {
    return data != null;
  }

  @Override
  public Single<GatewayBaseResponse<ProductTypeResponse>> createProductType(MandatoryRequest mandatoryRequest,
      ProductTypeRequest variableRequest) {
    return Single.<GatewayBaseResponse<ProductTypeResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<ProductTypeResponse>> productTypeResponse = this
            .promoCodeEndPointService
            .createProductType(MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                variableRequest).execute();
        GatewayBaseResponse<ProductTypeResponse> promoPage = productTypeResponse.body();

        singleEmitter.onSuccess(promoPage);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<ProductTypeResponse>> updateProductType(
      MandatoryRequest mandatoryRequest,
      ProductTypeRequest variableRequest, String id) {
    return Single.<GatewayBaseResponse<ProductTypeResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<ProductTypeResponse>> promoCodeResponse = this.promoCodeEndPointService
            .updateProductType(MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                variableRequest, id).execute();
        GatewayBaseResponse<ProductTypeResponse> promoCodeResponseGateway = promoCodeResponse.body();

        singleEmitter.onSuccess(promoCodeResponseGateway);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<Boolean>> deleteProductType(MandatoryRequest mandatoryRequest,
      String id) {
    return Single.<GatewayBaseResponse<Boolean>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<Boolean>> promoCodeResponse = this.promoCodeEndPointService
            .deleteProductType
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
  public Single<GatewayBaseResponse<ProductTypeResponse>> findProductTypeById(
      MandatoryRequest mandatoryRequest,
      String id) {
    return Single.<GatewayBaseResponse<ProductTypeResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<ProductTypeResponse>> promoCodeResponse = this.promoCodeEndPointService
            .findProductTypeById
                (MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id)
            .execute();
        GatewayBaseResponse<ProductTypeResponse> success = promoCodeResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  public Single<GatewayBaseResponse<List<ProductTypeResponse>>> findProductTypes(MandatoryRequest mandatoryRequest)
  {
    return Single.<GatewayBaseResponse<List<ProductTypeResponse>>>create(singleEmitter -> {
      try {

        Response<GatewayBaseResponse<List<ProductTypeResponse>>> promoCodeResponse = this.promoCodeEndPointService
            .findProductTypes
                (MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest))
            .execute();
        GatewayBaseResponse<List<ProductTypeResponse>> success = promoCodeResponse.body();

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

  private boolean isExistColumnSort(ProductTypeColumn columnSort) {
    return columnSort != null;
  }
}
