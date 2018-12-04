package com.tl.booking.promo.code.rest.web.controller;


import com.tl.booking.common.libraries.BeanMapper;
import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.common.rest.web.model.response.BaseResponse;
import com.tl.booking.common.rest.web.model.response.CommonResponse;
import com.tl.booking.promo.code.entity.constant.ApiPath;
import com.tl.booking.promo.code.entity.constant.enums.BusinessLogicResponseColumn;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.dao.BusinessLogicResponse;
import com.tl.booking.promo.code.rest.web.model.request.BusinessLogicResponseRequest;
import com.tl.booking.promo.code.rest.web.model.response.BusinessLogicResponseResponse;
import com.tl.booking.promo.code.service.api.BusinessLogicResponseService;
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
@RequestMapping(ApiPath.BUSINESS_LOGIC_RESPONSE)
@Api(value = "API BusinessLogicResponse")
public class BusinessLogicResponseController {

  @Autowired
  BusinessLogicResponseService businessLogicResponseService;


  @ApiOperation(value = "Find List of Business Logic Exception")
  @GetMapping(path = "/findAll")
  public DeferredResult<BaseResponse<List<BusinessLogicResponseResponse>>> businessLogicResponse(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest) {
    DeferredResult<BaseResponse<List<BusinessLogicResponseResponse>>> deferred = new DeferredResult<>();

    this.businessLogicResponseService.findBusinessLogicResponses(mandatoryRequest)
        .map(this::toBusinessLogicResponseResponse)
        .map(businessLogicResponseResponse -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(),
                ResponseCode.SUCCESS.getMessage(), null, businessLogicResponseResponse))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @ApiOperation(value = "Find List of Business Logic Response (Paginated)")
  @GetMapping
  public DeferredResult<BaseResponse<Page<BusinessLogicResponseResponse>>> findBusinessLogicResponseFilterPaginated(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @RequestParam(required = false) String responseCode,
      @RequestParam Integer page,
      @RequestParam Integer size,
      @RequestParam(required = false) BusinessLogicResponseColumn columnSort,
      @RequestParam(required = false) SortDirection sortDirection
  ) {

    DeferredResult<BaseResponse<Page<BusinessLogicResponseResponse>>> deferred = new DeferredResult<>();

    this.businessLogicResponseService.findBusinessLogicResponseFilterPaginated(
        mandatoryRequest, responseCode, page, size, columnSort, sortDirection)
        .map(this::toPageBusinessLogicResponseResponse)
        .map(businessLogicResponseResponses -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, businessLogicResponseResponses))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  private Page<BusinessLogicResponseResponse> toPageBusinessLogicResponseResponse(
      Page<BusinessLogicResponse> businessLogicResponses) {
    return businessLogicResponses
        .map(bank -> BeanMapper.map(bank, BusinessLogicResponseResponse.class));
  }

  @ApiOperation(value = "Find Business Logic Response by Id")
  @GetMapping(path = "/{id}")
  public DeferredResult<BaseResponse<BusinessLogicResponseResponse>> findBusinessLogicResponseById(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @PathVariable String id) {
    DeferredResult<BaseResponse<BusinessLogicResponseResponse>> deferred = new DeferredResult<>();

    this.businessLogicResponseService.findBusinessLogicResponseById(mandatoryRequest, id)
        .map(this::toVariableResponse)
        .map(variableResponse -> CommonResponse.constructResponse(ResponseCode.SUCCESS.getCode(),
            ResponseCode.SUCCESS.getMessage(), null, variableResponse))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @ApiOperation(value = "Create Business Logic Response")
  @PostMapping
  public DeferredResult<BaseResponse<BusinessLogicResponseResponse>> createBusinessLogicResponse(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @Valid @RequestBody BusinessLogicResponseRequest businessLogicResponseRequest) {

    BusinessLogicResponse businessLogicResponse = this
        .toBusinessLogicResponse(mandatoryRequest, businessLogicResponseRequest);

    DeferredResult<BaseResponse<BusinessLogicResponseResponse>> deferred = new DeferredResult<>();
    this.businessLogicResponseService
        .createBusinessLogicResponse(mandatoryRequest, businessLogicResponse)
        .map(businessLogicResponseCreated -> BeanMapper
            .map(businessLogicResponseCreated, BusinessLogicResponseResponse.class))
        .map(businessLogicResponseCreated -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, businessLogicResponseCreated))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @ApiOperation(value = "Update Business Logic Response")
  @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
  public DeferredResult<BaseResponse<BusinessLogicResponseResponse>> updateBusinessLogicResponseById(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @Valid @RequestBody BusinessLogicResponseRequest businessLogicResponseRequest,
      @PathVariable String id) {

    BusinessLogicResponse businessLogicResponse = this
        .toBusinessLogicResponse(mandatoryRequest, businessLogicResponseRequest);

    DeferredResult<BaseResponse<BusinessLogicResponseResponse>> deferred = new DeferredResult<>();

    this.businessLogicResponseService
        .updateBusinessLogicResponse(mandatoryRequest, businessLogicResponse, id)
        .map(businessLogicResponseUpdate -> BeanMapper
            .map(businessLogicResponseUpdate, BusinessLogicResponseResponse.class))
        .map(businessLogicResponseUpdate -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, businessLogicResponseUpdate))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @ApiOperation(value = "Delete businessLogicResponse by Id")
  @DeleteMapping(path = "/{id}")
  public DeferredResult<BaseResponse> deleteBusinessLogicResponseById(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @NotBlank(message = "Id must be not blank")
      @NotEmpty(message = "Id not be empty")
      @PathVariable String id) {

    DeferredResult<BaseResponse> deferred = new DeferredResult<>();

    this.businessLogicResponseService.deleteBusinessLogicResponse(mandatoryRequest, id)
        .map(success -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, null))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  private BusinessLogicResponse toBusinessLogicResponse(MandatoryRequest mandatoryRequest,
      BusinessLogicResponseRequest businessLogicResponseRequest) {

    BusinessLogicResponse businessLogicResponse = this
        .businessLogicResponseMapper(businessLogicResponseRequest);
    businessLogicResponse.setStoreId(mandatoryRequest.getStoreId());
    businessLogicResponse.setChannelId(mandatoryRequest.getChannelId());
    businessLogicResponse.setUsername(mandatoryRequest.getUsername());
    businessLogicResponse.setUpdatedBy(mandatoryRequest.getUsername());
    businessLogicResponse.setCreatedBy(mandatoryRequest.getUsername());

    return businessLogicResponse;
  }

  private List<BusinessLogicResponseResponse> toBusinessLogicResponseResponse(
      List<BusinessLogicResponse> businessLogicResponses) {
    return BeanMapper.mapAsList(businessLogicResponses, BusinessLogicResponseResponse.class);
  }

  private BusinessLogicResponse businessLogicResponseMapper(
      BusinessLogicResponseRequest businessLogicResponseRequest) {
    return BeanMapper.map(businessLogicResponseRequest, BusinessLogicResponse.class);
  }

  private BusinessLogicResponseResponse toVariableResponse(
      BusinessLogicResponse businessLogicResponse) {
    return BeanMapper.map(businessLogicResponse, BusinessLogicResponseResponse.class);
  }

  @ModelAttribute
  public MandatoryRequest getMandatoryParameter(HttpServletRequest request) {
    return (MandatoryRequest) request.getAttribute("mandatory");
  }
}
