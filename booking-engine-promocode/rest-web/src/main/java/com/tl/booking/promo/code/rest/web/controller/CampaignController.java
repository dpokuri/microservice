package com.tl.booking.promo.code.rest.web.controller;

import com.tl.booking.common.libraries.BeanMapper;
import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.common.rest.web.model.response.BaseResponse;
import com.tl.booking.common.rest.web.model.response.CommonResponse;
import com.tl.booking.promo.code.entity.CampaignPeriod;
import com.tl.booking.promo.code.entity.constant.ApiPath;
import com.tl.booking.promo.code.entity.constant.enums.CampaignColumn;
import com.tl.booking.promo.code.entity.constant.enums.CampaignStatus;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.dao.Campaign;
import com.tl.booking.promo.code.libraries.configuration.TimeZoneProperties;
import com.tl.booking.promo.code.libraries.utility.DateFormatterHelper;
import com.tl.booking.promo.code.rest.web.model.request.CampaignPeriodRequest;
import com.tl.booking.promo.code.rest.web.model.request.CampaignPeriodRequestConvertDate;
import com.tl.booking.promo.code.rest.web.model.request.CampaignRequest;
import com.tl.booking.promo.code.rest.web.model.request.CampaignRequestConvertDate;
import com.tl.booking.promo.code.rest.web.model.response.CampaignDropdownResponse;
import com.tl.booking.promo.code.rest.web.model.response.CampaignResponse;
import com.tl.booking.promo.code.service.api.CampaignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(ApiPath.CAMPAIGN_PATH)
@Api(value = "API Campaign")
public class CampaignController {

  @Autowired
  CampaignService campaignService;

  @Autowired
  TimeZoneProperties timeZoneProperties;

  @ApiOperation(value = "Find List of Campaign by StoreId", notes = "Get List of Campaign By "
      + "StoreId")
  @GetMapping(path = ApiPath.FIND_ACTIVATE)
  public DeferredResult<BaseResponse<List<CampaignDropdownResponse>>> findCampaignByStoreIdAndPublished(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest) {
    DeferredResult<BaseResponse<List<CampaignDropdownResponse>>> deferred = new DeferredResult<>();

    this.campaignService.findCampaigns(mandatoryRequest)
        .map(campaigns -> BeanMapper.mapAsList(campaigns, CampaignDropdownResponse.class))
        .map(campaignResponses -> CommonResponse.constructResponse(ResponseCode.SUCCESS.getCode(),
            ResponseCode.SUCCESS.getMessage(), null, campaignResponses))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @ApiOperation(value = "Find campaign by Id", notes = "Get Document of Campaign By Id")
  @GetMapping(path = "/{id}")
  public DeferredResult<BaseResponse<CampaignResponse>> findCampaignById(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @PathVariable String id) {
    DeferredResult<BaseResponse<CampaignResponse>> deferred = new DeferredResult<>();

    this.campaignService.findCampaignById(mandatoryRequest, id)
        .map(campaign -> BeanMapper.map(campaign, CampaignResponse.class))
        .map(campaignResponse -> CommonResponse.constructResponse(ResponseCode.SUCCESS.getCode(),
            ResponseCode.SUCCESS.getMessage(), null, campaignResponse))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @ApiOperation(value = "Get.Campaign (Paginated)", notes = "Get Document All of Campaign with Pagination By StoreId")
  @GetMapping
  public DeferredResult<BaseResponse<Page<CampaignResponse>>> findCampaignFilterPaginated(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) CampaignStatus campaignStatus,
      @RequestParam Integer page,
      @RequestParam Integer size,
      @RequestParam(required = false) CampaignColumn columnSort,
      @RequestParam(required = false) SortDirection sortDirection
  ) {

    DeferredResult<BaseResponse<Page<CampaignResponse>>> deferred = new DeferredResult<>();

    this.campaignService.findCampaignFilterPaginated(
        mandatoryRequest, name, campaignStatus, page, size, columnSort, sortDirection)
        .map(this::toPageCampaignResponse)
        .map(campaignResponses -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, campaignResponses))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @ApiOperation(value = "Create campaign")
  @PostMapping
  public DeferredResult<BaseResponse<CampaignResponse>> createCampaign(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @Valid @RequestBody CampaignRequest campaignRequest) {

    Campaign campaign = this.toCampaign(mandatoryRequest, campaignRequest);

    DeferredResult<BaseResponse<CampaignResponse>> deferred = new DeferredResult<>();
    this.campaignService.createCampaign(mandatoryRequest, campaign)
        .map(createdCampaign -> BeanMapper.map(createdCampaign, CampaignResponse.class))
        .map(createdCampaign -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, createdCampaign))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;

  }

  @ApiOperation(value = "Update campaign")
  @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
  public DeferredResult<BaseResponse<CampaignResponse>> updateCampaignById(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @Valid @RequestBody CampaignRequest campaignRequest,
      @PathVariable String id) {

    Campaign campaign = this.toCampaign(mandatoryRequest, campaignRequest);

    DeferredResult<BaseResponse<CampaignResponse>> deferred = new DeferredResult<>();

    this.campaignService.updateCampaign(mandatoryRequest, campaign, id)
        .map(updatedCampaign -> BeanMapper.map(updatedCampaign, CampaignResponse.class))
        .map(updatedCampaign -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, updatedCampaign))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;

  }

  @ApiOperation(value = "Delete campaign by Id")
  @DeleteMapping(path = "/{id}")
  public DeferredResult<BaseResponse> deleteCampaignById(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @NotBlank(message = "Id must be not blank")
      @NotEmpty(message = "Id not be empty")
      @PathVariable String id) {

    DeferredResult<BaseResponse> deferred = new DeferredResult<>();

    this.campaignService.deleteCampaign(mandatoryRequest, id)
        .map(success -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, null))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }


  @ApiOperation(value = "Update campaignStatus Pending")
  @RequestMapping(path = "/{id}/pending", method = RequestMethod.PUT)
  public DeferredResult<BaseResponse<CampaignResponse>> updateStatusActivedPromoCodeById(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @PathVariable String id) {

    DeferredResult<BaseResponse<CampaignResponse>> deferred = new DeferredResult<>();

    this.campaignService.updateStatusPendingCampaign(mandatoryRequest, id)
        .map(campaignUpdated -> BeanMapper.map(campaignUpdated, CampaignResponse.class))
        .map(campaignUpdated -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, campaignUpdated))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;

  }


  @ApiOperation(value = "Update campaignStatus Activate")
  @RequestMapping(path = "/{id}/activate", method = RequestMethod.PUT)
  public DeferredResult<BaseResponse<CampaignResponse>> updateStatusActiveCampaignById(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @PathVariable String id) {

    DeferredResult<BaseResponse<CampaignResponse>> deferred = new DeferredResult<>();

    this.campaignService.updateStatusActiveCampaign(mandatoryRequest, id)
        .map(campaignUpdated -> BeanMapper.map(campaignUpdated, CampaignResponse.class))
        .map(campaignUpdated -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, campaignUpdated))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;

  }

  @ApiOperation(value = "Update campaignStatus unActivate")
  @RequestMapping(path = "/{id}/unActivate", method = RequestMethod.PUT)
  public DeferredResult<BaseResponse<CampaignResponse>> updateStatusUnactiveCampaignById(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @PathVariable String id) {

    DeferredResult<BaseResponse<CampaignResponse>> deferred = new DeferredResult<>();

    this.campaignService.updateStatusInactiveCampaign(mandatoryRequest, id)
        .map(campaignUpdated -> BeanMapper.map(campaignUpdated, CampaignResponse.class))
        .map(campaignUpdated -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, campaignUpdated))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;

  }

  @ApiOperation(value = "Update campaignStatus Rejected")
  @RequestMapping(path = "/{id}/rejected", method = RequestMethod.PUT)
  public DeferredResult<BaseResponse<CampaignResponse>> updateStatusRejectedCampaignById(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @PathVariable String id) {

    DeferredResult<BaseResponse<CampaignResponse>> deferred = new DeferredResult<>();

    this.campaignService.updateStatusRejectedCampaign(mandatoryRequest, id)
        .map(campaignUpdated -> BeanMapper.map(campaignUpdated, CampaignResponse.class))
        .map(campaignUpdated -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, campaignUpdated))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;

  }


  private Campaign toCampaign(MandatoryRequest mandatoryRequest,
      CampaignRequest campaignRequest) {

    List<CampaignPeriodRequestConvertDate> campaignPeriods = this
        .setCampaignPeriod(campaignRequest);

    CampaignRequestConvertDate campaignRequestConvertDate = new CampaignRequestConvertDate();
    campaignRequestConvertDate.setCampaignPeriods(campaignPeriods);
    campaignRequestConvertDate.setName(campaignRequest.getName());
    campaignRequestConvertDate.setCode(campaignRequest.getCode());
    campaignRequestConvertDate.setPromoCodeAdjustmentId(campaignRequest.getPromoCodeAdjustmentId());

    Campaign campaign = this.campaignMapper(campaignRequestConvertDate);

    this.setMandatory(campaign, mandatoryRequest);

    return campaign;
  }


  private List<CampaignPeriodRequestConvertDate> setcampaignPeriodRequestConvertDates(
      List<CampaignPeriod> campaignPeriods) {
    List<CampaignPeriodRequestConvertDate> campaignRequestConvertDate = new ArrayList<>();

    for (CampaignPeriod getCampaignPeriods : campaignPeriods) {
      CampaignPeriodRequestConvertDate campaignPeriod = new CampaignPeriodRequestConvertDate();
      campaignPeriod.setStartDate(getCampaignPeriods.getStartDate());
      campaignPeriod.setEndDate(getCampaignPeriods.getEndDate());
      campaignRequestConvertDate.add(campaignPeriod);
    }

    return campaignRequestConvertDate;
  }

  private List<CampaignPeriodRequestConvertDate> setCampaignPeriod(
      CampaignRequest campaignRequest) {
    List<CampaignPeriod> campaignPeriods = new ArrayList<>();

    Integer offset = 0;

    for (CampaignPeriodRequest getCampaignPeriods : campaignRequest.getCampaignPeriods()) {
      CampaignPeriod campaignPeriod = new CampaignPeriod();
      campaignPeriod.setStartDate(
          DateFormatterHelper.stringToDate(getCampaignPeriods.getStartDate(), offset));
      campaignPeriod
          .setEndDate(DateFormatterHelper.stringToDate(getCampaignPeriods.getEndDate(), offset));
      campaignPeriods.add(campaignPeriod);
    }

    return this.setcampaignPeriodRequestConvertDates(campaignPeriods);
  }

  private Campaign campaignMapper(CampaignRequestConvertDate campaignRequestConvertDate) {
    return BeanMapper.map(campaignRequestConvertDate, Campaign.class);
  }

  private void setMandatory(Campaign campaign, MandatoryRequest mandatoryRequest) {
    campaign.setStoreId(mandatoryRequest.getStoreId());
    campaign.setChannelId(mandatoryRequest.getChannelId());
    campaign.setUsername(mandatoryRequest.getUsername());
    campaign.setUpdatedBy(mandatoryRequest.getUsername());
    campaign.setCreatedBy(mandatoryRequest.getUsername());
  }

  private Page<CampaignResponse> toPageCampaignResponse(
      Page<Campaign> campaigns) {
    return campaigns
        .map(campaign -> BeanMapper.map(campaign, CampaignResponse.class));
  }


  @ModelAttribute
  public MandatoryRequest getMandatoryParameter(HttpServletRequest request) {
    return (MandatoryRequest) request.getAttribute("mandatory");
  }


}
