package com.tl.booking.promo.code.rest.web.controller;

import com.tl.booking.common.libraries.BeanMapper;
import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.common.rest.web.model.response.BaseResponse;
import com.tl.booking.common.rest.web.model.response.CommonResponse;
import com.tl.booking.promo.code.entity.constant.ApiPath;
import com.tl.booking.promo.code.entity.constant.enums.ProductTypeColumn;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.dao.ProductType;
import com.tl.booking.promo.code.rest.web.model.request.ProductTypeRequest;
import com.tl.booking.promo.code.rest.web.model.response.ProductTypeFindAllResponse;
import com.tl.booking.promo.code.rest.web.model.response.ProductTypeResponse;
import com.tl.booking.promo.code.service.api.ProductTypeService;
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
@RequestMapping(ApiPath.PRODUCT_TYPE_PATH)
@Api(value = "API ProductType")
public class ProductTypeController {

  @Autowired
  private ProductTypeService productTypeService;


  @ApiOperation(value = "Find ProductType by Id", notes = "Get Document of ProductType By Id")
  @GetMapping(path = "/{id}")
  public DeferredResult<BaseResponse<ProductTypeResponse>> findPromoCodeById(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @PathVariable String id) {
    DeferredResult<BaseResponse<ProductTypeResponse>> deferred = new DeferredResult<>();

    this.productTypeService.findProductTypeById(mandatoryRequest, id)
        .map(this::toVariableResponse)
        .map(variableResponse -> CommonResponse.constructResponse(ResponseCode.SUCCESS.getCode(),
            ResponseCode.SUCCESS.getMessage(), null, variableResponse))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }


  @ApiOperation(value = "Get.ProductType (Paginated)", notes = "Get Document All of ProductType with Pagination By StoreId")
  @GetMapping
  public DeferredResult<BaseResponse<Page<ProductTypeResponse>>> findVariableFilterPaginated(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @RequestParam(required = false) String name,
      @RequestParam Integer page,
      @RequestParam Integer size,
      @RequestParam(required = false) ProductTypeColumn columnSort,
      @RequestParam(required = false) SortDirection sortDirection
  ) {

    DeferredResult<BaseResponse<Page<ProductTypeResponse>>> deferred = new DeferredResult<>();

    this.productTypeService.findProductTypeFilterPaginated(
        mandatoryRequest, name, page, size, columnSort, sortDirection)
        .map(this::toPageProductTypeResponse)
        .map(productTypes -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, productTypes))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @ApiOperation(value = "Create ProductType")
  @PostMapping
  public DeferredResult<BaseResponse<ProductTypeResponse>> createProductType(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @Valid @RequestBody ProductTypeRequest productTypeRequest) {

    ProductType productType = this.toProductType(mandatoryRequest, productTypeRequest);

    DeferredResult<BaseResponse<ProductTypeResponse>> deferred = new DeferredResult<>();
    this.productTypeService.createProductType(mandatoryRequest, productType)
        .map(productTypeCreated -> BeanMapper.map(productTypeCreated, ProductTypeResponse.class))
        .map(productTypeCreated -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, productTypeCreated))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;

  }


  @ApiOperation(value = "Update productType")
  @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
  public DeferredResult<BaseResponse<ProductTypeResponse>> updatePromoCodeById(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @Valid @RequestBody ProductTypeRequest productTypeRequest,
      @PathVariable String id) {

    ProductType productType = this.toProductType(mandatoryRequest, productTypeRequest);

    DeferredResult<BaseResponse<ProductTypeResponse>> deferred = new DeferredResult<>();

    this.productTypeService.updateProductType(mandatoryRequest, productType, id)
        .map(productTypeUpdated -> BeanMapper.map(productTypeUpdated, ProductTypeResponse.class))
        .map(productTypeUpdated -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, productTypeUpdated))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;

  }

  @ApiOperation(value = "Delete productType by Id")
  @DeleteMapping(path = "/{id}")
  public DeferredResult<BaseResponse> deleteProductTypeById(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @NotBlank(message = "Id must be not blank")
      @NotEmpty(message = "Id not be empty")
      @PathVariable String id) {

    DeferredResult<BaseResponse> deferred = new DeferredResult<>();

    this.productTypeService.deleteProductType(mandatoryRequest, id)
        .map(success -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, null))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @ApiOperation(value = "Find All ProductType")
  @GetMapping(path = "/findAll")
  public DeferredResult<BaseResponse<List<ProductTypeFindAllResponse>>> findProductTypes(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest) {

    DeferredResult<BaseResponse<List<ProductTypeFindAllResponse>>> deferred = new DeferredResult<>();

    this.productTypeService.findProductTypes(
        mandatoryRequest)
        .map(this::toListProductTypeResponse)
        .map(channelIds -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, channelIds))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  private List<ProductTypeFindAllResponse> toListProductTypeResponse(
      List<ProductType> productTypes) {
    List<ProductTypeFindAllResponse> productTypeFindAllResponses = new ArrayList<>();
    for (ProductType productType : productTypes) {
      ProductTypeFindAllResponse productTypeFindAllResponse = BeanMapper.map(productType,
          ProductTypeFindAllResponse
              .class);
      productTypeFindAllResponse.setLabel(productType.getName());
      productTypeFindAllResponse.setValue(productType.getId());
      productTypeFindAllResponses.add(productTypeFindAllResponse);
    }

    return productTypeFindAllResponses;
  }


  private ProductType toProductType(MandatoryRequest mandatoryRequest,
      ProductTypeRequest productTypeRequest) {

    ProductType productType = this.productTypeMapper(productTypeRequest);
    productType.setStoreId(mandatoryRequest.getStoreId());
    productType.setChannelId(mandatoryRequest.getChannelId());
    productType.setUsername(mandatoryRequest.getUsername());
    productType.setUpdatedBy(mandatoryRequest.getUsername());
    productType.setCreatedBy(mandatoryRequest.getUsername());

    return productType;
  }

  private ProductTypeResponse toVariableResponse(ProductType productType) {
    return BeanMapper.map(productType, ProductTypeResponse.class);
  }

  private ProductType productTypeMapper(ProductTypeRequest productTypeRequest) {
    return BeanMapper.map(productTypeRequest, ProductType.class);
  }

  private Page<ProductTypeResponse> toPageProductTypeResponse(
      Page<ProductType> productTypes) {
    return productTypes
        .map(productType -> BeanMapper.map(productType, ProductTypeResponse.class));
  }

  @ModelAttribute
  public MandatoryRequest getMandatoryParameter(HttpServletRequest request) {
    return (MandatoryRequest) request.getAttribute("mandatory");
  }

}
