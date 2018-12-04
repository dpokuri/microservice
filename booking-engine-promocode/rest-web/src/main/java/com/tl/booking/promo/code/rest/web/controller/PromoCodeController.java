package com.tl.booking.promo.code.rest.web.controller;

import com.tl.booking.common.libraries.BeanMapper;
import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.common.rest.web.model.response.BaseResponse;
import com.tl.booking.common.rest.web.model.response.CommonResponse;
import com.tl.booking.promo.code.entity.constant.ApiPath;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeColumn;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.dao.PromoCode;
import com.tl.booking.promo.code.rest.web.model.request.PromoCodeRequest;
import com.tl.booking.promo.code.rest.web.model.response.PromoCodeResponse;
import com.tl.booking.promo.code.service.api.PromoCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
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
@RequestMapping(ApiPath.PROMO_CODE_PATH)
@Api(value = "API PromoCode")
public class PromoCodeController {

  @Autowired
  private PromoCodeService promoCodeService;


  @ApiOperation(value = "Find promo code by Id", notes = "Get Document of PromoCode By Id")
  @GetMapping(path = "/{id}")
  public DeferredResult<BaseResponse<PromoCodeResponse>> findPromoCodeById(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @PathVariable String id) {
    DeferredResult<BaseResponse<PromoCodeResponse>> deferred = new DeferredResult<>();

    this.promoCodeService.findPromoCodeById(mandatoryRequest, id)
        .map(this::toPromoCodeResponse)
        .map(promoCodeResponse -> CommonResponse.constructResponse(ResponseCode.SUCCESS.getCode(),
            ResponseCode.SUCCESS.getMessage(), null, promoCodeResponse))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }


  /* Code Added line: 71
   *
   * Here we added @Async annotation to capture traceId using Sleuth.
   *
   * <This change is temporary as we need to finalize the best way to integrate sleuth traces with stack driver >
   */

  @Async
  @ApiOperation(value = "Get.PromoCode (Paginated)", notes = "Get Document of PromoCode with Pagination By StoreId")
  @GetMapping
  public DeferredResult<BaseResponse<Page<PromoCodeResponse>>> findPromoCodeFilterPaginated(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @RequestParam(required = false) String code,
      @RequestParam(required = false) String campaignId,
      @RequestParam Integer page,
      @RequestParam Integer size,
      @RequestParam(required = false) PromoCodeColumn columnSort,
      @RequestParam(required = false) SortDirection sortDirection
  ) {

    DeferredResult<BaseResponse<Page<PromoCodeResponse>>> deferred = new DeferredResult<>();

    String upperCasedCode = null;
    if(code != null){
      upperCasedCode = code.toUpperCase();
    }

    this.promoCodeService.findPromoCodeFilterPaginated(
        mandatoryRequest, upperCasedCode, campaignId, page, size, columnSort, sortDirection)
        .map(this::toPagePromoCodeResponse)
        .map(promoCodeFindAllResponses -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, promoCodeFindAllResponses))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @ApiOperation(value = "Create promoCode")
  @PostMapping
  public DeferredResult<BaseResponse<PromoCodeResponse>> createPromoCode(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @Valid @RequestBody PromoCodeRequest promoCodeRequest) {

    PromoCode promoCode = this.toPromoCode(mandatoryRequest, promoCodeRequest);

    DeferredResult<BaseResponse<PromoCodeResponse>> deferred = new DeferredResult<>();
    this.promoCodeService.createPromoCode(mandatoryRequest, promoCode)
        .map(promoCodeCreated -> BeanMapper.map(promoCodeCreated, PromoCodeResponse.class))
        .map(promoCodeResponse -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, promoCodeResponse))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;

  }


  @ApiOperation(value = "Update promoCode")
  @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
  public DeferredResult<BaseResponse<PromoCodeResponse>> updatePromoCodeById(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @Valid @RequestBody PromoCodeRequest promoCodeRequest,
      @PathVariable String id) {

    PromoCode promoCode = this.toPromoCode(mandatoryRequest, promoCodeRequest);

    DeferredResult<BaseResponse<PromoCodeResponse>> deferred = new DeferredResult<>();

    this.promoCodeService.updatePromoCode(mandatoryRequest, promoCode, id)
        .map(promoCodeUpdated -> BeanMapper.map(promoCodeUpdated, PromoCodeResponse.class))
        .map(promoCodeUpdated -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, promoCodeUpdated))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;

  }

  @ApiOperation(value = "Update promoCodeStatus activate")
  @RequestMapping(path = "/{id}/activate", method = RequestMethod.PUT)
  public DeferredResult<BaseResponse<PromoCodeResponse>> updateStatusActivedPromoCodeById(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @PathVariable String id) {

    DeferredResult<BaseResponse<PromoCodeResponse>> deferred = new DeferredResult<>();

    this.promoCodeService.updateStatusActivedPromoCode(mandatoryRequest, id)
        .map(promoCodeUpdated -> BeanMapper.map(promoCodeUpdated, PromoCodeResponse.class))
        .map(promoCodeUpdated -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, promoCodeUpdated))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;

  }

  @ApiOperation(value = "Update promoCodeStatus unActivate")
  @RequestMapping(path = "/{id}/unActivate", method = RequestMethod.PUT)
  public DeferredResult<BaseResponse<PromoCodeResponse>> updateStatusInActivedPromoCodeById(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @PathVariable String id) {

    DeferredResult<BaseResponse<PromoCodeResponse>> deferred = new DeferredResult<>();

    this.promoCodeService.updateStatusInActivedPromoCode(mandatoryRequest, id)
        .map(promoCodeUpdated -> BeanMapper.map(promoCodeUpdated, PromoCodeResponse.class))
        .map(promoCodeUpdated -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, promoCodeUpdated))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;

  }


  @ApiOperation(value = "Delete promoCode by Id")
  @DeleteMapping(path = "/{id}")
  public DeferredResult<BaseResponse> deletePromoCodeById(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @NotBlank(message = "Id must be not blank")
      @NotEmpty(message = "Id not be empty")
      @PathVariable String id) {

    DeferredResult<BaseResponse> deferred = new DeferredResult<>();

    this.promoCodeService.deletePromoCode(mandatoryRequest, id)
        .map(success -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, null))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  private PromoCode toPromoCode(MandatoryRequest mandatoryRequest,
      PromoCodeRequest promoCodeRequest) {

    PromoCode promoCode = promoCodeMapper(promoCodeRequest);
    promoCode.setCode(promoCode.getCode().toUpperCase());
    promoCode.setStoreId(mandatoryRequest.getStoreId());
    promoCode.setCreatedBy(mandatoryRequest.getUsername());
    promoCode.setUpdatedBy(mandatoryRequest.getUsername());

    return promoCode;
  }

  private PromoCode promoCodeMapper(PromoCodeRequest promoCodeRequest) {
    return BeanMapper.map(promoCodeRequest, PromoCode.class);
  }

  private PromoCodeResponse toPromoCodeResponse(PromoCode promoCode) {
    return BeanMapper.map(promoCode, PromoCodeResponse.class);
  }


  private Page<PromoCodeResponse> toPagePromoCodeResponse(
      Page<PromoCode> promoCodes) {
    return promoCodes.map(promoCode -> BeanMapper.map(promoCode, PromoCodeResponse.class));
  }


  @ModelAttribute
  public MandatoryRequest getMandatoryParameter(HttpServletRequest request) {
    return (MandatoryRequest) request.getAttribute("mandatory");
  }
}
