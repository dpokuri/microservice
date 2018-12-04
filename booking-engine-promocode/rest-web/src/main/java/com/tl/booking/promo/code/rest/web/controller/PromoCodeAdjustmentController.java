package com.tl.booking.promo.code.rest.web.controller;

import com.tl.booking.common.libraries.BeanMapper;
import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.common.rest.web.model.response.BaseResponse;
import com.tl.booking.common.rest.web.model.response.CommonResponse;
import com.tl.booking.promo.code.entity.constant.ApiPath;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeAdjustmentColumn;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeAdjustmentStatus;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.dao.PromoCodeAdjustment;
import com.tl.booking.promo.code.rest.web.model.request.PromoCodeAdjustmentRequest;
import com.tl.booking.promo.code.rest.web.model.response.PromoCodeAdjustmentDropdownResponse;
import com.tl.booking.promo.code.rest.web.model.response.PromoCodeAdjustmentResponse;
import com.tl.booking.promo.code.service.api.PromoCodeAdjustmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Size;
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
@RequestMapping(ApiPath.PROMO_CODE_ADJUSTMENT_PATH)
@Api(value = "API PromoCodeAdjustment")
public class PromoCodeAdjustmentController {

  @Autowired
  PromoCodeAdjustmentService promoCodeAdjustmentService;

  @ApiOperation(value = "Find List of Campaign by StoreId", notes = "Get List of Campaign By "
      + "StoreId")
  @GetMapping(path = ApiPath.FIND_ACTIVATE)
  public DeferredResult<BaseResponse<List<PromoCodeAdjustmentDropdownResponse>>> findPromoCodeAdjustmentDropDown(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest) {

    DeferredResult<BaseResponse<List<PromoCodeAdjustmentDropdownResponse>>> deferred = new DeferredResult<>();

    this.promoCodeAdjustmentService.findPromoCodeAdjustments(mandatoryRequest)
        .map(promoCodeAdjustments -> BeanMapper
            .mapAsList(promoCodeAdjustments, PromoCodeAdjustmentDropdownResponse.class))
        .map(campaignResponses -> CommonResponse.constructResponse(ResponseCode.SUCCESS.getCode(),
            ResponseCode.SUCCESS.getMessage(), null, campaignResponses))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }


  @ApiOperation(value = "Create promoCodeAdjustment")
  @PostMapping
  public DeferredResult<BaseResponse<PromoCodeAdjustmentResponse>> createPromoCodeAdjustment(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @Valid @RequestBody PromoCodeAdjustmentRequest promoCodeAdjustmentRequest) {

    PromoCodeAdjustment promoCodeAdjustment = this
        .toPromoCodeAdjustment(mandatoryRequest, promoCodeAdjustmentRequest);

    DeferredResult<BaseResponse<PromoCodeAdjustmentResponse>> deferred = new DeferredResult<>();
    this.promoCodeAdjustmentService.createPromoCodeAdjustment(mandatoryRequest, promoCodeAdjustment)
        .map(
            promoCodeCreated -> BeanMapper.map(promoCodeCreated, PromoCodeAdjustmentResponse.class))
        .map(promoCodeCreated -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, promoCodeCreated))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }


  @ApiOperation(value = "Update promoCodeAdjustment")
  @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
  public DeferredResult<BaseResponse<PromoCodeAdjustmentResponse>> updatePromoCodeAdjustment(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @NotBlank(message = "id must be not blank")
      @NotEmpty(message = "id must be not empty")
      @PathVariable String id,
      @Valid @RequestBody PromoCodeAdjustmentRequest promoCodeAdjustmentRequest) {

    PromoCodeAdjustment promoCodeAdjustment = this
        .toPromoCodeAdjustment(mandatoryRequest, promoCodeAdjustmentRequest);
    DeferredResult<BaseResponse<PromoCodeAdjustmentResponse>> deferred = new DeferredResult<>();
    this.promoCodeAdjustmentService
        .updatePromoCodeAdjustment(mandatoryRequest, promoCodeAdjustment, id)
        .map(promoCodeAdjustmentUpdated -> BeanMapper
            .map(promoCodeAdjustmentUpdated, PromoCodeAdjustmentResponse.class))
        .map(promoCodeAdjustmentUpdated -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, promoCodeAdjustmentUpdated))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;

  }

  @ApiOperation(value = "Delete promoCodeAdjustment by Id")
  @DeleteMapping(path = "/{id}")
  public DeferredResult<BaseResponse> deleteById(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @NotBlank(message = "Id must be not blank")
      @NotEmpty(message = "Id not be empty")
      @PathVariable String id) {

    DeferredResult<BaseResponse> output = new DeferredResult<>();

    this.promoCodeAdjustmentService.deletePromoCodeAdjustment(mandatoryRequest, id)
        .map(success -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, null))
        .subscribe(output::setResult, output::setErrorResult);

    return output;
  }

  @ApiOperation(value = "Find PromoCodeAdjustment by Id", notes = "Get Document of PromoCodeAdjustment By Id")
  @RequestMapping(path = "/{id}", method = RequestMethod.GET)
  public DeferredResult<BaseResponse<PromoCodeAdjustmentResponse>> findById(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @PathVariable String id) {

    DeferredResult<BaseResponse<PromoCodeAdjustmentResponse>> output = new DeferredResult<>();
    this.promoCodeAdjustmentService.findPromoCodeAdjustmentById(mandatoryRequest, id)
        .map(this::toPromoCodeAdjustmentResponse)
        .map(promoCodeAdjustmentResponses -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(),
                ResponseCode.SUCCESS.getMessage(), null, promoCodeAdjustmentResponses))
        .subscribe(output::setResult, output::setErrorResult
        );

    return output;
  }


  @ApiOperation(value = "Get.PromoCode (Paginated)", notes = "Get Document All of PromoCode with Pagination By StoreId")
  @GetMapping
  public DeferredResult<BaseResponse<Page<PromoCodeAdjustmentResponse>>> findPromoCodeAdjustmentFilterPaginated(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,

      @RequestParam(required = false) String name,
      @RequestParam(required = false) Boolean isPromoCodeCombine,
      @RequestParam(required = false) Double maxDiscount,
      @RequestParam(required = false) PromoCodeAdjustmentStatus promoCodeAdjustmentStatus,

      @RequestParam Integer page,
      @Size(min = 1, max = 100)
      @RequestParam Integer size,
      @RequestParam(required = false) PromoCodeAdjustmentColumn sort,
      @RequestParam(required = false) SortDirection sortDirection
  ) {

    DeferredResult<BaseResponse<Page<PromoCodeAdjustmentResponse>>> deferred = new DeferredResult<>();

    this.promoCodeAdjustmentService.findPromoCodeAdjustmentFilterPaginated(
        mandatoryRequest, name, isPromoCodeCombine, maxDiscount, promoCodeAdjustmentStatus, page,
        size,
        sort, sortDirection)
        .map(this::toPagePromoCodeAdjustmentResponse)
        .map(promoCodeFindAllResponses -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, promoCodeFindAllResponses))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }


  @ApiOperation(value = "Update promoCodeAdjustmentStatus Pending")
  @RequestMapping(path = "/{id}/pending", method = RequestMethod.PUT)
  public DeferredResult<BaseResponse<PromoCodeAdjustmentResponse>> updateStatusPendingPromoCodeById(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @PathVariable String id) {

    DeferredResult<BaseResponse<PromoCodeAdjustmentResponse>> deferred = new DeferredResult<>();

    this.promoCodeAdjustmentService.updateStatusPendingPromoCodeAdjustment(mandatoryRequest, id)
        .map(
            promoCodeUpdated -> BeanMapper.map(promoCodeUpdated, PromoCodeAdjustmentResponse.class))
        .map(promoCodeUpdated -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, promoCodeUpdated))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;

  }

  @ApiOperation(value = "Update promoCodeAdjustmentStatus Activated")
  @RequestMapping(path = "/{id}/activate", method = RequestMethod.PUT)
  public DeferredResult<BaseResponse<PromoCodeAdjustmentResponse>> updateStatusActivedPromoCodeById(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @PathVariable String id) {

    DeferredResult<BaseResponse<PromoCodeAdjustmentResponse>> deferred = new DeferredResult<>();

    this.promoCodeAdjustmentService.updateStatusActivedPromoCodeAdjustment(mandatoryRequest, id)
        .map(
            promoCodeUpdated -> BeanMapper.map(promoCodeUpdated, PromoCodeAdjustmentResponse.class))
        .map(promoCodeUpdated -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, promoCodeUpdated))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;

  }

  @ApiOperation(value = "Update promoCodeAdjustmentStatus InActivated")
  @RequestMapping(path = "/{id}/unActivate", method = RequestMethod.PUT)
  public DeferredResult<BaseResponse<PromoCodeAdjustmentResponse>> updateStatusInActivedPromoCodeById(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @PathVariable String id) {

    DeferredResult<BaseResponse<PromoCodeAdjustmentResponse>> deferred = new DeferredResult<>();

    this.promoCodeAdjustmentService.updateStatusInActivedPromoCodeAdjustment(mandatoryRequest, id)
        .map(
            promoCodeUpdated -> BeanMapper.map(promoCodeUpdated, PromoCodeAdjustmentResponse.class))
        .map(promoCodeUpdated -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, promoCodeUpdated))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;

  }

  @ApiOperation(value = "Update promoCodeAdjustmentStatus Rejected")
  @RequestMapping(path = "/{id}/rejected", method = RequestMethod.PUT)
  public DeferredResult<BaseResponse<PromoCodeAdjustmentResponse>> updateStatusRejectedPromoCodeAdjustmentById(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @PathVariable String id) {

    DeferredResult<BaseResponse<PromoCodeAdjustmentResponse>> deferred = new DeferredResult<>();

    this.promoCodeAdjustmentService.updateStatusRejectedPromoCodeAdjustment(mandatoryRequest, id)
        .map(
            promoCodeUpdated -> BeanMapper.map(promoCodeUpdated, PromoCodeAdjustmentResponse.class))
        .map(promoCodeUpdated -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, promoCodeUpdated))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;

  }

  private PromoCodeAdjustment toPromoCodeAdjustment(MandatoryRequest mandatoryRequest,
      PromoCodeAdjustmentRequest promoCodeAdjustmentRequest) {

    PromoCodeAdjustment promoCodeAdjustment = this
        .promoCodeAdjustmentMapper(promoCodeAdjustmentRequest);
    promoCodeAdjustment.setStoreId(mandatoryRequest.getStoreId());
    promoCodeAdjustment.setCreatedBy(mandatoryRequest.getUsername());
    promoCodeAdjustment.setUpdatedBy(mandatoryRequest.getUsername());

    return promoCodeAdjustment;
  }


  private PromoCodeAdjustment promoCodeAdjustmentMapper(
      PromoCodeAdjustmentRequest promoCodeAdjustmentRequest) {
    return BeanMapper.map(promoCodeAdjustmentRequest, PromoCodeAdjustment.class);
  }

  private PromoCodeAdjustmentResponse toPromoCodeAdjustmentResponse(
      PromoCodeAdjustment promoCodeAdjustment) {
    return BeanMapper.map(promoCodeAdjustment, PromoCodeAdjustmentResponse.class);
  }

  private Page<PromoCodeAdjustmentResponse> toPagePromoCodeAdjustmentResponse(
      Page<PromoCodeAdjustment> promoCodeAdjustments) {
    return promoCodeAdjustments
        .map(promoCodeAdjustment -> BeanMapper
            .map(promoCodeAdjustment, PromoCodeAdjustmentResponse.class));
  }

  @ModelAttribute
  public MandatoryRequest getMandatoryParameter(HttpServletRequest request) {
    return (MandatoryRequest) request.getAttribute("mandatory");
  }

}
