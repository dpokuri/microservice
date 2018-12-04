package com.tl.booking.gateway.outbound.impl.promocode;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.libraries.utility.MandatoryRequestHelper;
import com.tl.booking.gateway.outbound.api.promocode.CampaignOutboundService;
import com.tl.booking.gateway.outbound.impl.configuration.PromoCodeEndPointService;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.constant.enums.CampaignColumn;
import com.tl.booking.gateway.entity.constant.enums.CampaignStatus;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.fields.CampaignFields;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoCode.CampaignDropdownResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.CampaignRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.CampaignResponse;

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
public class CampaignOutboundServiceImpl implements CampaignOutboundService {

  @Autowired
  private PromoCodeEndPointService promoCodeEndPointService;

  @Override
  public Single<GatewayBaseResponse<RestResponsePage<CampaignResponse>>> findCampaignFilterPaginated(
      MandatoryRequest mandatoryRequest,
      String name,
      CampaignStatus campaignStatus,
      Integer page,
      Integer size,
      CampaignColumn columnSort,
      SortDirection sortDirection,
      String privilegeToCheck
  ) {

    Map<String, String> queryParam = new HashMap<>();

    if (isNotNullString(name)) {
      queryParam.put(CampaignFields.NAME, name);
    }

    if (isNotNullStatus(campaignStatus)) {
      queryParam.put(CampaignFields.CAMPAIGN_STATUS, campaignStatus.getCode());
    }


    queryParam.put("page", page.toString());
    queryParam.put("size", size.toString());

    if (isExistColumnSort(columnSort)) {
      queryParam.put("columnSort", columnSort.getName());
    }
    if (isExistSortDirection(sortDirection)) {
      queryParam.put("sortDirection", sortDirection.getName());
    }

    return Single.<GatewayBaseResponse<RestResponsePage<CampaignResponse>>>create(
        singleEmitter -> {
          try {
            Response<GatewayBaseResponse<RestResponsePage<CampaignResponse>>> campaignResponse = this
                .promoCodeEndPointService
                .findCampaignFilterPaginated
                    (
                        MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                        queryParam)
                .execute();

            GatewayBaseResponse<RestResponsePage<CampaignResponse>> campaignPageRest = campaignResponse.body();

            if (isExistRestResponsePage(campaignPageRest.getData())) {
              List<CampaignResponse> campaignResponses = campaignPageRest.getData().getContent();
              for (CampaignResponse response : campaignResponses) {
                response.setAllowedActions(new ArrayList<>());
                this.generateAllowedActions(response, privilegeToCheck);
              }

              RestResponsePage<CampaignResponse> restResponsePage = new
                  RestResponsePage<>();
              restResponsePage.setContent(campaignResponses);
              restResponsePage.setFirstPage(campaignPageRest.getData().isFirstPage());
              restResponsePage.setNumber(campaignPageRest.getData().getNumber());
              restResponsePage.setSize(campaignPageRest.getData().getSize());
              restResponsePage.setTotalPages(campaignPageRest.getData().getTotalPages());
              restResponsePage
                  .setNumberOfElements(campaignPageRest.getData().getNumberOfElements());
              restResponsePage
                  .setTotalElements(campaignPageRest.getData().getTotalElements());
              restResponsePage
                  .setPreviousPage(campaignPageRest.getData().isPreviousPage());
              restResponsePage.setNextPage(campaignPageRest.getData().isNextPage());
              restResponsePage.setLastPage(campaignPageRest.getData().isLast());
              restResponsePage.setSort(campaignPageRest.getData().getSort());
              campaignPageRest.setData(restResponsePage);
            }

            singleEmitter.onSuccess(campaignPageRest);
          } catch (Exception e) {
            singleEmitter.onError(e);
          }
        }).subscribeOn(Schedulers.io());
  }

  private boolean isExistRestResponsePage(RestResponsePage<CampaignResponse> data) {
    return data != null;
  }

  private void generateAllowedActions(CampaignResponse response, String privilegeToCheck) {
    if (response.getCampaignStatus().equals(CampaignStatus.DRAFT) && privilegeToCheck.contains
        (PrivilegeId.PENDING_CAMPAIGN)) {
      response.setAllowedActions(Arrays.asList("pending"));
    } else if (response.getCampaignStatus().equals(CampaignStatus.PENDING) && privilegeToCheck
        .contains(PrivilegeId.ACTIVATE_CAMPAIGN)) {
      response.setAllowedActions(Arrays.asList("activate"));
    } else if (response.getCampaignStatus().equals(CampaignStatus.ACTIVE) && privilegeToCheck
        .contains(PrivilegeId.UNACTIVATE_CAMPAIGN)) {
      response.setAllowedActions(Arrays.asList("unActivate"));
    }
  }

  @Override
  public Single<GatewayBaseResponse<CampaignResponse>> createCampaign(MandatoryRequest mandatoryRequest,
      CampaignRequest campaignRequest) {
    return Single.<GatewayBaseResponse<CampaignResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<CampaignResponse>> campaignResponse = this
            .promoCodeEndPointService
            .createCampaign(MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                campaignRequest).execute();
        GatewayBaseResponse<CampaignResponse> promoPage = campaignResponse.body();

        singleEmitter.onSuccess(promoPage);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<CampaignResponse>> updateCampaign(
      MandatoryRequest mandatoryRequest,
      CampaignRequest campaignRequest, String id) {
    return Single.<GatewayBaseResponse<CampaignResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<CampaignResponse>> campaignResponse = this.promoCodeEndPointService
            .updateCampaign(MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                campaignRequest, id).execute();
        GatewayBaseResponse<CampaignResponse> responseGateway = campaignResponse.body();

        singleEmitter.onSuccess(responseGateway);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<Boolean>> deleteCampaign(MandatoryRequest mandatoryRequest,
      String id) {
    return Single.<GatewayBaseResponse<Boolean>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<Boolean>> campaignResponse = this.promoCodeEndPointService
            .deleteCampaign
                (MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id)
            .execute();
        GatewayBaseResponse<Boolean> success = campaignResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<CampaignResponse>> findCampaignById(
      MandatoryRequest mandatoryRequest,
      String id) {
    return Single.<GatewayBaseResponse<CampaignResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<CampaignResponse>> campaignResponse = this.promoCodeEndPointService
            .findCampaignById
                (MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id)
            .execute();
        GatewayBaseResponse<CampaignResponse> success = campaignResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }



  @Override
  public Single<GatewayBaseResponse<CampaignResponse>> updateStatusActivateCampaign(
      MandatoryRequest mandatoryRequest,
      String id) {
    return Single.<GatewayBaseResponse<CampaignResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<CampaignResponse>> campaignResponse = this.promoCodeEndPointService
            .updateStatusActivateCampaign(
                MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id)
            .execute();
        GatewayBaseResponse<CampaignResponse> campaignResponsePage = campaignResponse.body();

        singleEmitter.onSuccess(campaignResponsePage);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<CampaignResponse>> updateStatusUnActivateCampaign(
      MandatoryRequest mandatoryRequest,
      String id) {
    return Single.<GatewayBaseResponse<CampaignResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<CampaignResponse>> campaignResponse = this.promoCodeEndPointService
            .updateStatusUnActivateCampaign(
                MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id)
            .execute();
        GatewayBaseResponse<CampaignResponse> promoCategoryPage = campaignResponse.body();

        singleEmitter.onSuccess(promoCategoryPage);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<CampaignResponse>> updateStatusPendingCampaign(
      MandatoryRequest mandatoryRequest,
      String id) {
    return Single.<GatewayBaseResponse<CampaignResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<CampaignResponse>> campaignResponse = this.promoCodeEndPointService
            .updateStatusPendingCampaign(
                MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id)
            .execute();
        GatewayBaseResponse<CampaignResponse> campaignResponsePage = campaignResponse.body();

        singleEmitter.onSuccess(campaignResponsePage);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<CampaignResponse>> updateStatusRejectedCampaign(
      MandatoryRequest mandatoryRequest,
      String id) {
    return Single.<GatewayBaseResponse<CampaignResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<CampaignResponse>> campaignResponse = this.promoCodeEndPointService
            .updateStatusRejectedCampaign(
                MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id)
            .execute();
        GatewayBaseResponse<CampaignResponse> campaignResponsePage = campaignResponse.body();

        singleEmitter.onSuccess(campaignResponsePage);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<List<CampaignDropdownResponse>>> findCampaignActivate(
      MandatoryRequest mandatoryRequest) {
    return Single.<GatewayBaseResponse<List<CampaignDropdownResponse>>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<List<CampaignDropdownResponse>>> campaignResponse = this.promoCodeEndPointService
            .findCampaignActivate(
                MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest))
            .execute();
        GatewayBaseResponse<List<CampaignDropdownResponse>> campaignResponsePage = campaignResponse.body();

        singleEmitter.onSuccess(campaignResponsePage);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  private Boolean isNotNullStatus(CampaignStatus status) {
    return status != null;
  }

  private Boolean isNotNullString(String string) {
    return string != null;
  }

  private boolean isExistSortDirection(SortDirection sortDirection) {
    return sortDirection != null;
  }

  private boolean isExistColumnSort(CampaignColumn columnSort) {
    return columnSort != null;
  }
}
