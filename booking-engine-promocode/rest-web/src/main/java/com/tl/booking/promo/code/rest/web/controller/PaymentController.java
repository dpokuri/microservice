package com.tl.booking.promo.code.rest.web.controller;

import com.tl.booking.common.libraries.BeanMapper;
import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.common.rest.web.model.response.BaseResponse;
import com.tl.booking.common.rest.web.model.response.CommonResponse;
import com.tl.booking.promo.code.entity.constant.ApiPath;
import com.tl.booking.promo.code.entity.constant.enums.PaymentColumn;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.dao.Payment;
import com.tl.booking.promo.code.rest.web.model.request.PaymentRequest;
import com.tl.booking.promo.code.rest.web.model.response.PaymentResponse;
import com.tl.booking.promo.code.service.api.PaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping(ApiPath.PAYMENT_PATH)
@Api(value = "API Payment")
public class PaymentController {

  @Autowired
  private PaymentService paymentService;

  @ApiOperation(value = "Find List of Payment")
  @GetMapping(path = "/findAll")
  public DeferredResult<BaseResponse<List<PaymentResponse>>> payments(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest) {
    DeferredResult<BaseResponse<List<PaymentResponse>>> deferred = new DeferredResult<>();

    this.paymentService.findPayments(mandatoryRequest)
        .map(this::toPaymentResponse)
        .map(paymentResponse -> CommonResponse.constructResponse(ResponseCode.SUCCESS.getCode(),
            ResponseCode.SUCCESS.getMessage(), null, paymentResponse))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @ApiOperation(value = "Find List of Payment (Paginated)", notes = "Get Document All of Payment with Pagination By StoreId")
  @GetMapping
  public DeferredResult<BaseResponse<Page<PaymentResponse>>> findPaymentFilterPaginated(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String paymentId,
      @RequestParam Integer page,
      @RequestParam Integer size,
      @RequestParam(required = false) PaymentColumn columnSort,
      @RequestParam(required = false) SortDirection sortDirection
  ) {

    DeferredResult<BaseResponse<Page<PaymentResponse>>> deferred = new DeferredResult<>();

    this.paymentService.findPaymentFilterPaginated(
        mandatoryRequest, name, paymentId, page, size, columnSort, sortDirection)
        .map(this::toPagePaymentResponse)
        .map(payments -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, payments))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  private Page<PaymentResponse> toPagePaymentResponse(
      Page<Payment> payments) {
    return payments
        .map(payment -> BeanMapper.map(payment, PaymentResponse.class));
  }

  @ApiOperation(value = "Find Payment by Id", notes = "Get Document of Payment By Id")
  @GetMapping(path = "/{id}")
  public DeferredResult<BaseResponse<PaymentResponse>> findPaymentById(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @PathVariable String id) {
    DeferredResult<BaseResponse<PaymentResponse>> deferred = new DeferredResult<>();

    this.paymentService.findPaymentById(mandatoryRequest, id)
        .map(this::toVariableResponse)
        .map(variableResponse -> CommonResponse.constructResponse(ResponseCode.SUCCESS.getCode(),
            ResponseCode.SUCCESS.getMessage(), null, variableResponse))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @ApiOperation(value = "Create Payment")
  @PostMapping
  public DeferredResult<BaseResponse<PaymentResponse>> createPayment(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @Valid @RequestBody PaymentRequest paymentRequest) {

    Payment payment = this.toPayment(mandatoryRequest, paymentRequest);

    DeferredResult<BaseResponse<PaymentResponse>> deferred = new DeferredResult<>();

    this.paymentService.createPayment(mandatoryRequest, payment)
        .map(paymentCreated -> BeanMapper.map(paymentCreated, PaymentResponse.class))
        .map(paymentCreated -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, paymentCreated))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @ApiOperation(value = "Update Payment")
  @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
  public DeferredResult<BaseResponse<PaymentResponse>> updatePaymentById(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @Valid @RequestBody PaymentRequest paymentRequest,
      @PathVariable String id) {

    Payment payment = this.toPayment(mandatoryRequest, paymentRequest);

    DeferredResult<BaseResponse<PaymentResponse>> deferred = new DeferredResult<>();

    this.paymentService.updatePayment(mandatoryRequest, payment, id)
        .map(paymentUpdate -> BeanMapper.map(paymentUpdate, PaymentResponse.class))
        .map(paymentUpdate -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, paymentUpdate))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @ApiOperation(value = "Delete payment by Id")
  @DeleteMapping(path = "/{id}")
  public DeferredResult<BaseResponse> deletePaymentById(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @NotBlank(message = "Id must be not blank")
      @NotEmpty(message = "Id not be empty")
      @PathVariable String id) {

    DeferredResult<BaseResponse> deferred = new DeferredResult<>();

    this.paymentService.deletePayment(mandatoryRequest, id)
        .map(success -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, null))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  private Payment toPayment(MandatoryRequest mandatoryRequest,
      PaymentRequest paymentRequest) {

    Payment payment = this.paymentMapper(paymentRequest);
    payment.setStoreId(mandatoryRequest.getStoreId());
    payment.setChannelId(mandatoryRequest.getChannelId());
    payment.setUsername(mandatoryRequest.getUsername());
    payment.setUpdatedBy(mandatoryRequest.getUsername());
    payment.setCreatedBy(mandatoryRequest.getUsername());

    return payment;
  }

  private Payment paymentMapper(PaymentRequest paymentRequest) {
    return BeanMapper.map(paymentRequest, Payment.class);
  }

  private PaymentResponse toVariableResponse(Payment payment) {
    return BeanMapper.map(payment, PaymentResponse.class);
  }

  private List<PaymentResponse> toPaymentResponse(List<Payment> payments) {
    return BeanMapper.mapAsList(payments, PaymentResponse.class);
  }

  @ModelAttribute
  public MandatoryRequest getMandatoryParameter(HttpServletRequest request) {
    return (MandatoryRequest) request.getAttribute("mandatory");
  }

}
