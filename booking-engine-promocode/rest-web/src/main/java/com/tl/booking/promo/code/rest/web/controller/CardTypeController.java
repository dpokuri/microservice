package com.tl.booking.promo.code.rest.web.controller;

import com.tl.booking.common.libraries.BeanMapper;
import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.common.rest.web.model.response.BaseResponse;
import com.tl.booking.common.rest.web.model.response.CommonResponse;
import com.tl.booking.promo.code.entity.constant.ApiPath;
import com.tl.booking.promo.code.entity.constant.enums.CardTypeColumn;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.dao.CardType;
import com.tl.booking.promo.code.rest.web.model.request.CardTypeRequest;
import com.tl.booking.promo.code.rest.web.model.response.CardTypeResponse;
import com.tl.booking.promo.code.service.api.CardTypeService;
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
@RequestMapping(ApiPath.CARD_TYPE_PATH)
@Api(value = "API Card Type")
public class CardTypeController {

  @Autowired
  private CardTypeService cardTypeService;

  @ApiOperation(value = "Find List of Card Type")
  @GetMapping(path = "/findAll")
  public DeferredResult<BaseResponse<List<CardTypeResponse>>> cardTypes(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest, String bankId) {
    DeferredResult<BaseResponse<List<CardTypeResponse>>> deferred = new DeferredResult<>();

    this.cardTypeService.findCardTypes(mandatoryRequest, bankId)
        .map(this::toCardTypeResponse)
        .map(cardTypeResponse -> CommonResponse.constructResponse(ResponseCode.SUCCESS.getCode(),
            ResponseCode.SUCCESS.getMessage(), null, cardTypeResponse))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @ApiOperation(value = "Find List of Card Type (Paginated)", notes = "Get Document All of Card Type with Pagination By StoreId")
  @GetMapping
  public DeferredResult<BaseResponse<Page<CardTypeResponse>>> findCardTypeFilterPaginated(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @RequestParam(required = false) String name,
      @RequestParam Integer page,
      @RequestParam Integer size,
      @RequestParam(required = false) CardTypeColumn columnSort,
      @RequestParam(required = false) SortDirection sortDirection
  ) {

    DeferredResult<BaseResponse<Page<CardTypeResponse>>> deferred = new DeferredResult<>();

    this.cardTypeService.findCardTypeFilterPaginated(
        mandatoryRequest, name, page, size, columnSort, sortDirection)
        .map(this::toPageCardTypeResponse)
        .map(cardTypes -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, cardTypes))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  private Page<CardTypeResponse> toPageCardTypeResponse(
      Page<CardType> cardTypes) {
    return cardTypes
        .map(cardType -> BeanMapper.map(cardType, CardTypeResponse.class));
  }

  @ApiOperation(value = "Find Card Type by Id", notes = "Get Document of Card Type By Id")
  @GetMapping(path = "/{id}")
  public DeferredResult<BaseResponse<CardTypeResponse>> findCardTypeById(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @PathVariable String id) {
    DeferredResult<BaseResponse<CardTypeResponse>> deferred = new DeferredResult<>();

    this.cardTypeService.findCardTypeById(mandatoryRequest, id)
        .map(this::toVariableResponse)
        .map(variableResponse -> CommonResponse.constructResponse(ResponseCode.SUCCESS.getCode(),
            ResponseCode.SUCCESS.getMessage(), null, variableResponse))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @ApiOperation(value = "Create Card Type")
  @PostMapping
  public DeferredResult<BaseResponse<CardTypeResponse>> createCardType(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @Valid @RequestBody CardTypeRequest cardTypeRequest) {

    CardType cardType = this.toCardType(mandatoryRequest, cardTypeRequest);

    DeferredResult<BaseResponse<CardTypeResponse>> deferred = new DeferredResult<>();
    this.cardTypeService.createCardType(mandatoryRequest, cardType)
        .map(cardTypeCreated -> BeanMapper.map(cardTypeCreated, CardTypeResponse.class))
        .map(cardTypeCreated -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, cardTypeCreated))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @ApiOperation(value = "Update Card Type")
  @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
  public DeferredResult<BaseResponse<CardTypeResponse>> updateCardTypeById(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @Valid @RequestBody CardTypeRequest cardTypeRequest,
      @PathVariable String id) {

    CardType cardType = this.toCardType(mandatoryRequest, cardTypeRequest);

    DeferredResult<BaseResponse<CardTypeResponse>> deferred = new DeferredResult<>();

    this.cardTypeService.updateCardType(mandatoryRequest, cardType, id)
        .map(cardTypeUpdate -> BeanMapper.map(cardTypeUpdate, CardTypeResponse.class))
        .map(cardTypeUpdate -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, cardTypeUpdate))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @ApiOperation(value = "Delete Card Type by Id")
  @DeleteMapping(path = "/{id}")
  public DeferredResult<BaseResponse> deleteCardTypeById(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @NotBlank(message = "Id must be not blank")
      @NotEmpty(message = "Id not be empty")
      @PathVariable String id) {

    DeferredResult<BaseResponse> deferred = new DeferredResult<>();

    this.cardTypeService.deleteCardType(mandatoryRequest, id)
        .map(success -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, null))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  private CardType toCardType(MandatoryRequest mandatoryRequest,
      CardTypeRequest cardTypeRequest) {

    CardType cardType = this.cardTypeMapper(cardTypeRequest);
    cardType.setStoreId(mandatoryRequest.getStoreId());
    cardType.setChannelId(mandatoryRequest.getChannelId());
    cardType.setUsername(mandatoryRequest.getUsername());
    cardType.setUpdatedBy(mandatoryRequest.getUsername());
    cardType.setCreatedBy(mandatoryRequest.getUsername());

    return cardType;
  }

  private CardType cardTypeMapper(CardTypeRequest cardTypeRequest) {
    return BeanMapper.map(cardTypeRequest, CardType.class);
  }

  private List<CardTypeResponse> toCardTypeResponse(List<CardType> cardTypes) {
    return BeanMapper.mapAsList(cardTypes, CardTypeResponse.class);
  }

  private CardTypeResponse toVariableResponse(CardType cardType) {
    return BeanMapper.map(cardType, CardTypeResponse.class);
  }

  @ModelAttribute
  public MandatoryRequest getMandatoryParameter(HttpServletRequest request) {
    return (MandatoryRequest) request.getAttribute("mandatory");
  }

}
