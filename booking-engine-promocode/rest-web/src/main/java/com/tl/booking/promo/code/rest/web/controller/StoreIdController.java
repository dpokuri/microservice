package com.tl.booking.promo.code.rest.web.controller;

import com.tl.booking.common.libraries.BeanMapper;
import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.common.rest.web.model.response.BaseResponse;
import com.tl.booking.common.rest.web.model.response.CommonResponse;
import com.tl.booking.promo.code.entity.constant.ApiPath;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.enums.StoreIdColumn;
import com.tl.booking.promo.code.entity.dao.StoreId;
import com.tl.booking.promo.code.rest.web.model.request.StoreIdRequest;
import com.tl.booking.promo.code.rest.web.model.response.StoreIdResponse;
import com.tl.booking.promo.code.service.api.StoreIdService;
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
@RequestMapping(ApiPath.STORE_ID_PATH)
@Api(value = "API StoreId")
public class StoreIdController {

  @Autowired
  private StoreIdService storeIdService;


  @ApiOperation(value = "Find StoreId by Id", notes = "Get Document of StoreId By Id")
  @GetMapping(path = "/{id}")
  public DeferredResult<BaseResponse<StoreIdResponse>> findStoreIdById(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @PathVariable String id) {
    DeferredResult<BaseResponse<StoreIdResponse>> deferred = new DeferredResult<>();

    this.storeIdService.findStoreIdById(mandatoryRequest, id)
        .map(this::toVariableResponse)
        .map(variableResponse -> CommonResponse.constructResponse(ResponseCode.SUCCESS.getCode(),
            ResponseCode.SUCCESS.getMessage(), null, variableResponse))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }


  @ApiOperation(value = "Get.StoreId (Paginated)", notes = "Get Document All of StoreId with Pagination By StoreId")
  @GetMapping
  public DeferredResult<BaseResponse<Page<StoreIdResponse>>> findStoreIdFilterPaginated(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @RequestParam(required = false) String name,
      @RequestParam Integer page,
      @RequestParam Integer size,
      @RequestParam(required = false) StoreIdColumn columnSort,
      @RequestParam(required = false) SortDirection sortDirection
  ) {

    DeferredResult<BaseResponse<Page<StoreIdResponse>>> deferred = new DeferredResult<>();

    this.storeIdService.findStoreIdFilterPaginated(
        mandatoryRequest, name, page, size, columnSort, sortDirection)
        .map(this::toPageStoreIdResponse)
        .map(productTypes -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, productTypes))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @ApiOperation(value = "Create StoreId")
  @PostMapping
  public DeferredResult<BaseResponse<StoreIdResponse>> createStoreId(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @Valid @RequestBody StoreIdRequest productTypeRequest) {

    StoreId productType = this.toStoreId(mandatoryRequest, productTypeRequest);

    DeferredResult<BaseResponse<StoreIdResponse>> deferred = new DeferredResult<>();
    this.storeIdService.createStoreId(mandatoryRequest, productType)
        .map(productTypeCreated -> BeanMapper.map(productTypeCreated, StoreIdResponse.class))
        .map(productTypeCreated -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, productTypeCreated))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;

  }


  @ApiOperation(value = "Update productType")
  @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
  public DeferredResult<BaseResponse<StoreIdResponse>> updateStoreIdById(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @Valid @RequestBody StoreIdRequest productTypeRequest,
      @PathVariable String id) {

    StoreId productType = this.toStoreId(mandatoryRequest, productTypeRequest);

    DeferredResult<BaseResponse<StoreIdResponse>> deferred = new DeferredResult<>();

    this.storeIdService.updateStoreId(mandatoryRequest, productType, id)
        .map(productTypeUpdated -> BeanMapper.map(productTypeUpdated, StoreIdResponse.class))
        .map(productTypeUpdated -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, productTypeUpdated))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;

  }

  @ApiOperation(value = "Delete productType by Id")
  @DeleteMapping(path = "/{id}")
  public DeferredResult<BaseResponse> deleteStoreIdById(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @NotBlank(message = "Id must be not blank")
      @NotEmpty(message = "Id not be empty")
      @PathVariable String id) {

    DeferredResult<BaseResponse> deferred = new DeferredResult<>();

    this.storeIdService.deleteStoreId(mandatoryRequest, id)
        .map(success -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, null))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @ApiOperation(value = "Find All StoreId")
  @GetMapping(path = "/findAll")
  public DeferredResult<BaseResponse<List<StoreIdResponse>>> findStoreIds(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest) {

    DeferredResult<BaseResponse<List<StoreIdResponse>>> deferred = new DeferredResult<>();

    this.storeIdService.findStoreIds(
        mandatoryRequest)
        .map(this::toListStoreIdResponse)
        .map(productTypes -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, productTypes))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }


  private StoreId toStoreId(MandatoryRequest mandatoryRequest,
      StoreIdRequest productTypeRequest) {

    StoreId productType = this.productTypeMapper(productTypeRequest);
    productType.setStoreId(mandatoryRequest.getStoreId());
    productType.setChannelId(mandatoryRequest.getChannelId());
    productType.setUpdatedBy(mandatoryRequest.getUsername());
    productType.setCreatedBy(mandatoryRequest.getUsername());

    return productType;
  }

  private StoreIdResponse toVariableResponse(StoreId productType) {
    return BeanMapper.map(productType, StoreIdResponse.class);
  }

  private StoreId productTypeMapper(StoreIdRequest productTypeRequest) {
    return BeanMapper.map(productTypeRequest, StoreId.class);
  }

  private Page<StoreIdResponse> toPageStoreIdResponse(
      Page<StoreId> productTypes) {
    return productTypes
        .map(productType -> BeanMapper.map(productType, StoreIdResponse.class));
  }

  private List<StoreIdResponse> toListStoreIdResponse(
      List<StoreId> storeIds) {
    return BeanMapper.mapAsList(storeIds, StoreIdResponse.class);
  }

  @ModelAttribute
  public MandatoryRequest getMandatoryParameter(HttpServletRequest request) {
    return (MandatoryRequest) request.getAttribute("mandatory");
  }

}
