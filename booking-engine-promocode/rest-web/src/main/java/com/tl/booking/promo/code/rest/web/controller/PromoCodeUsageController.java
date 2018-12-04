package com.tl.booking.promo.code.rest.web.controller;

import com.tl.booking.common.libraries.BeanMapper;
import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.common.rest.web.model.response.BaseResponse;
import com.tl.booking.common.rest.web.model.response.CommonResponse;
import com.tl.booking.promo.code.entity.OrderDetail;
import com.tl.booking.promo.code.entity.PaymentMethod;
import com.tl.booking.promo.code.entity.constant.ApiPath;
import com.tl.booking.promo.code.entity.constant.enums.IgnoreValidation;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeUsageLogColumn;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.dao.PromoCodeUsageLog;
import com.tl.booking.promo.code.rest.web.model.OrderDetailDTO;
import com.tl.booking.promo.code.rest.web.model.request.ApplyPromoCodeRequest;
import com.tl.booking.promo.code.rest.web.model.request.ApplyPromoCodeWithoutPaymentValidationRequest;
import com.tl.booking.promo.code.rest.web.model.request.UnApplyPromoCodeRequest;
import com.tl.booking.promo.code.rest.web.model.response.ApplyPromoCodeResponse;
import com.tl.booking.promo.code.rest.web.model.response.PromoCodeUsageLogResponse;
import com.tl.booking.promo.code.service.api.ApplyPromoCodeService;
import com.tl.booking.promo.code.service.api.CalculatePromoCodeService;
import com.tl.booking.promo.code.service.api.PromoCodeObjectService;
import com.tl.booking.promo.code.service.api.PromoCodeUsageLogService;
import com.tl.booking.promo.code.service.api.PromoCodeUsageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(ApiPath.BASE_PATH)
@Api(value = "Auth")
public class PromoCodeUsageController {

  @Autowired
  private ApplyPromoCodeService applyPromoCodeService;

  @Autowired
  private CalculatePromoCodeService calculatePromoCodeService;

  @Autowired
  private PromoCodeObjectService promoCodeObjectService;

  @Autowired
  private PromoCodeUsageLogService promoCodeUsageLogService;

  @Autowired
  private PromoCodeUsageService promoCodeUsageService;

  @ApiOperation(value = "Apply.PromoCode", notes = "Put all mandatory parameter")
  @RequestMapping(path = ApiPath.APPLY_PROMO_CODE, method = RequestMethod.POST)
  public DeferredResult<BaseResponse<ApplyPromoCodeResponse>> applyPromoCode(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @Valid @RequestBody ApplyPromoCodeRequest applyPromoCodeRequest) {
    List<OrderDetail> orderDetails = BeanMapper
        .mapAsList(applyPromoCodeRequest.getOrderDetails(), OrderDetail.class);

    DeferredResult<BaseResponse<ApplyPromoCodeResponse>> deffered = new DeferredResult<>();

    applyPromoCodeRequest.setCode(applyPromoCodeRequest.getCode().toUpperCase());

    this.applyPromoCodeService
        .applyPromoCode(mandatoryRequest, applyPromoCodeRequest.getCode(), orderDetails,
            applyPromoCodeRequest.getUsedPromoCodes(), applyPromoCodeRequest.getTotalPrice(),
            applyPromoCodeRequest.getPayment(), applyPromoCodeRequest.getReferenceId(), IgnoreValidation.NONE)
        .map(response -> this
            .toApplyPromoCodeResponse(response.getOrderDetails(), applyPromoCodeRequest
                    .getCode(),
                response
                    .getTotalPrice(), response.getTotalDiscount(),
                applyPromoCodeRequest.getUsedPromoCodes()))
        .map(orderDetailDTOS -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, orderDetailDTOS))
        .subscribe(deffered::setResult, deffered::setErrorResult);

    return deffered;
  }

  @ApiOperation(value = "Apply.PromoCode Without Payment Validation", notes = "Put all mandatory "
      + "parameter")
  @RequestMapping(path = ApiPath.APPLY_PROMO_CODE_WITHOUT_PAYMENT_VALIDATION, method = RequestMethod
      .POST)
  public DeferredResult<BaseResponse<ApplyPromoCodeResponse>>
  applyPromoCodeWithoutPaymentValidation(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @Valid @RequestBody ApplyPromoCodeWithoutPaymentValidationRequest applyPromoCodeRequest) {
    List<OrderDetail> orderDetails = BeanMapper
        .mapAsList(applyPromoCodeRequest.getOrderDetails(), OrderDetail.class);

    DeferredResult<BaseResponse<ApplyPromoCodeResponse>> deffered = new DeferredResult<>();

    applyPromoCodeRequest.setCode(applyPromoCodeRequest.getCode().toUpperCase());

    this.applyPromoCodeService
        .applyPromoCode(mandatoryRequest, applyPromoCodeRequest.getCode(), orderDetails,
            applyPromoCodeRequest.getUsedPromoCodes(), applyPromoCodeRequest.getTotalPrice(),
            applyPromoCodeRequest.getPayment(), applyPromoCodeRequest.getReferenceId(),
            IgnoreValidation.PAYMENT_METHOD)
        .map(response -> this
            .toApplyPromoCodeResponse(response.getOrderDetails(), applyPromoCodeRequest
                    .getCode(),
                response
                    .getTotalPrice(), response.getTotalDiscount(),
                applyPromoCodeRequest.getUsedPromoCodes()))
        .map(orderDetailDTOS -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, orderDetailDTOS))
        .subscribe(deffered::setResult, deffered::setErrorResult);

    return deffered;
  }

  @ApiOperation(value = "Unapply.PromoCode", notes = "Put all mandatory parameter")
  @RequestMapping(path = ApiPath.UNAPPLY_PROMO_CODE, method = RequestMethod.POST)
  public DeferredResult<BaseResponse<Boolean>> unApplyPromoCode(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @Valid @RequestBody UnApplyPromoCodeRequest unApplyPromoCodeRequest) {

    DeferredResult<BaseResponse<Boolean>> deffered = new DeferredResult<>();

    unApplyPromoCodeRequest.setCode(unApplyPromoCodeRequest.getCode().toUpperCase());

    this.applyPromoCodeService.unApplyPromoCode(mandatoryRequest, unApplyPromoCodeRequest.getCode(),
        unApplyPromoCodeRequest.getCardNumber(), unApplyPromoCodeRequest.getReferenceId()).map(
        success -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, success))
        .subscribe(deffered::setResult, deffered::setErrorResult);

    return deffered;
  }

  @ApiOperation(value = "Calculate.PromoCode", notes = "Put all mandatory parameter")
  @RequestMapping(path = ApiPath.CALCULATE_DISCOUNT, method = RequestMethod.POST)
  public DeferredResult<BaseResponse<ApplyPromoCodeResponse>> calculateDiscount(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @Valid @RequestBody ApplyPromoCodeRequest applyPromoCodeRequest) {
    List<OrderDetail> orderDetails = BeanMapper
        .mapAsList(applyPromoCodeRequest.getOrderDetails(), OrderDetail.class);

    DeferredResult<BaseResponse<ApplyPromoCodeResponse>> deffered = new DeferredResult<>();

    applyPromoCodeRequest.setCode(applyPromoCodeRequest.getCode().toUpperCase());

    this.calculatePromoCodeService
        .calculatePromoCode(mandatoryRequest, applyPromoCodeRequest.getCode(), orderDetails,
            applyPromoCodeRequest.getUsedPromoCodes(), applyPromoCodeRequest.getTotalPrice(),
            applyPromoCodeRequest.getPayment(), applyPromoCodeRequest.getReferenceId(), IgnoreValidation
                .QUANTITY)
        .map(response -> this.toApplyPromoCodeResponse(
            response.getOrderDetails(),
            response.getPromoCodeObject().getPromoCode().getCode(),
            response.getTotalPrice(),
            response.getTotalDiscount(),
            response.getUsedPromoCodes()))
        .map(orderDetailDTOS -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, orderDetailDTOS))
        .subscribe(deffered::setResult, deffered::setErrorResult);

    return deffered;
  }

  @ApiOperation(value = "Calculate.PromoCode Without Payment Validation", notes = "Put all "
      + "mandatory "
      + "parameter")
  @RequestMapping(path = ApiPath.CALCULATE_DISCOUNT_PAYMENT_VALIDATION, method = RequestMethod.POST)
  public DeferredResult<BaseResponse<ApplyPromoCodeResponse>>
  calculateDiscountWithoutPaymentValidation(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @Valid @RequestBody ApplyPromoCodeWithoutPaymentValidationRequest applyPromoCodeRequest) {
    List<OrderDetail> orderDetails = BeanMapper
        .mapAsList(applyPromoCodeRequest.getOrderDetails(), OrderDetail.class);

    DeferredResult<BaseResponse<ApplyPromoCodeResponse>> deffered = new DeferredResult<>();

    applyPromoCodeRequest.setCode(applyPromoCodeRequest.getCode().toUpperCase());

    this.calculatePromoCodeService
        .calculatePromoCode(mandatoryRequest, applyPromoCodeRequest.getCode(), orderDetails,
            applyPromoCodeRequest.getUsedPromoCodes(), applyPromoCodeRequest.getTotalPrice(),
            applyPromoCodeRequest.getPayment(), applyPromoCodeRequest.getReferenceId(), IgnoreValidation.PAYMENT_METHOD_QUANTITY)
        .map(response -> this.toApplyPromoCodeResponse(
            response.getOrderDetails(),
            applyPromoCodeRequest.getCode(),
            response.getTotalPrice(),
            response.getTotalDiscount(),
            response.getUsedPromoCodes()))
        .map(orderDetailDTOS -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, orderDetailDTOS))
        .subscribe(deffered::setResult, deffered::setErrorResult);

    return deffered;
  }

  @ApiOperation(value = "Check Payment Method Valid.PromoCode", notes = "Put all mandatory "
      + "parameter")
  @RequestMapping(path = ApiPath.CHECK_VALID_PAYMENT, method = RequestMethod.GET)
  public DeferredResult<BaseResponse<List<PaymentMethod>>> checkValidPayment(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @Valid @RequestParam @NotNull @NotEmpty @NotBlank String code) {

    DeferredResult<BaseResponse<List<PaymentMethod>>> deffered = new DeferredResult<>();
    String upperCasedcode = code.toUpperCase();

    this.promoCodeObjectService.findPromoCodeObjectByStoreIdAndCode(mandatoryRequest, upperCasedcode)
        .map(promoCodeObject -> promoCodeObject.getPromoCodeAdjustment().getPaymentMethods())
        .map(paymentMethods -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, paymentMethods))
        .subscribe(deffered::setResult, deffered::setErrorResult);

    return deffered;
  }

  private ApplyPromoCodeResponse toApplyPromoCodeResponse(List<OrderDetail> orderDetails,
      String code, Double
      totalPrice, Double totalDiscount, Set<String> usedPromoCodes) {
    List<OrderDetailDTO> orderDetailDTOs = BeanMapper.mapAsList(orderDetails,
        OrderDetailDTO.class);
    ApplyPromoCodeResponse applyPromoCodeResponse = new ApplyPromoCodeResponse();
    applyPromoCodeResponse.setOrderDetails(orderDetailDTOs);
    applyPromoCodeResponse.setTotalPrice(totalPrice);
    applyPromoCodeResponse.setUsedPromoCodes(usedPromoCodes);
    applyPromoCodeResponse.setCode(code);
    applyPromoCodeResponse.setTotalDiscount(totalDiscount);

    return applyPromoCodeResponse;
  }

  @ApiOperation(value = "UsageLog.PromoCode", notes = "Put all mandatory parameter")
  @RequestMapping(path = ApiPath.PROMO_CODE_USAGE_LOG, method = RequestMethod.GET)
  public DeferredResult<BaseResponse<Page<PromoCodeUsageLogResponse>>> promoCodeUsageLog(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) String startDate,
      @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) String endDate,
      @RequestParam(required = false) String code,
      @RequestParam() Integer page,
      @RequestParam() Integer size,
      @RequestParam(required = false) PromoCodeUsageLogColumn columnSort,
      @RequestParam(required = false) SortDirection sortDirection) {

    DeferredResult<BaseResponse<Page<PromoCodeUsageLogResponse>>> deferred = new DeferredResult<>();
    String upperCasedcode = code.toUpperCase();

    this.promoCodeUsageLogService.findPromoCodeUsageLogFilterPaginated(
        mandatoryRequest, upperCasedcode, startDate, endDate, page, size, columnSort, sortDirection)
        .map(this::toPromoCodeUsageLogResponse)
        .map(promoCodeUsageLogResponses -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, promoCodeUsageLogResponses))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @ApiOperation(value = "Check PromoCode Quota", notes = "Put all mandatory parameter")
  @RequestMapping(path = ApiPath.PROMO_CODE_QUOTA, method = RequestMethod.GET)
  public DeferredResult<BaseResponse<Integer>> checkQuota(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @RequestParam String code) {

    DeferredResult<BaseResponse<Integer>> deferred = new DeferredResult<>();
    String upperCasedcode = code.toUpperCase();

    this.promoCodeUsageService.findCurrentQuotaByPromoCode(mandatoryRequest, upperCasedcode)
        .map(currentQuotaResponse -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, currentQuotaResponse))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  private Page<PromoCodeUsageLogResponse> toPromoCodeUsageLogResponse(
      Page<PromoCodeUsageLog> promoCodeUsageLogs) {
    return promoCodeUsageLogs
        .map(promoCodeUsageLog -> BeanMapper
            .map(promoCodeUsageLog, PromoCodeUsageLogResponse.class));
  }


  @ModelAttribute
  public MandatoryRequest getMandatoryParameter(HttpServletRequest request) {
    return (MandatoryRequest) request.getAttribute("mandatory");
  }

}

