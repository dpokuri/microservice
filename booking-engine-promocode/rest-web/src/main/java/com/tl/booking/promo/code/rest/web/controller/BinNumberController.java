package com.tl.booking.promo.code.rest.web.controller;

import com.tl.booking.common.libraries.BeanMapper;
import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.common.rest.web.model.response.BaseResponse;
import com.tl.booking.common.rest.web.model.response.CommonResponse;
import com.tl.booking.promo.code.entity.constant.ApiPath;
import com.tl.booking.promo.code.entity.constant.enums.BinNumberColumn;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.dao.BinNumber;
import com.tl.booking.promo.code.rest.web.model.request.BinNumberRequest;
import com.tl.booking.promo.code.rest.web.model.response.BinNumberResponse;
import com.tl.booking.promo.code.service.api.BinNumberService;
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
@RequestMapping(ApiPath.BIN_NUMBER_PATH)
@Api(value = "API Bin Number")
public class BinNumberController {

  @Autowired
  private BinNumberService binNumberService;

  @ApiOperation(value = "Find List of Bin Number")
  @GetMapping(path = "/findAll")
  public DeferredResult<BaseResponse<List<String>>> binNumber(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @RequestParam(required = false) String binNumber,
      @RequestParam(required = false) String bankId,
      @RequestParam(required = false) String cardTypeId) {

    DeferredResult<BaseResponse<List<String>>> deferred = new DeferredResult<>();

    this.binNumberService.findBinNumbers(mandatoryRequest, binNumber, bankId, cardTypeId)
        .map(this::toBinNumberResponseAll)
        .map(binNumberResponse -> CommonResponse.constructResponse(ResponseCode.SUCCESS.getCode(),
            ResponseCode.SUCCESS.getMessage(), null, binNumberResponse))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @ApiOperation(value = "Find List of Bin Number (Paginated)",
      notes = "Get Document All of Bin Number with Pagination By StoreId")
  @GetMapping
  public DeferredResult<BaseResponse<Page<BinNumberResponse>>> findBinNumberFilterPaginated(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @RequestParam(required = false) String binNumber,
      @RequestParam(required = false) String bankId,
      @RequestParam(required = false) String cardTypeId,
      @RequestParam Integer page,
      @RequestParam Integer size,
      @RequestParam(required = false) BinNumberColumn columnSort,
      @RequestParam(required = false) SortDirection sortDirection
  ) {

    DeferredResult<BaseResponse<Page<BinNumberResponse>>> deferred = new DeferredResult<>();

    this.binNumberService.findBinNumberFilterPaginated(
        mandatoryRequest, binNumber, bankId, cardTypeId, page, size, columnSort, sortDirection)
        .map(this::toPageBinNumberResponse)
        .map(binNumbers -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, binNumbers))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  private Page<BinNumberResponse> toPageBinNumberResponse(
      Page<BinNumber> binNumbers) {
    return binNumbers
        .map(binNumber -> BeanMapper.map(binNumber, BinNumberResponse.class));
  }

  @ApiOperation(value = "Create Bin Number")
  @PostMapping
  public DeferredResult<BaseResponse<BinNumberResponse>> createBinNumber(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @Valid @RequestBody BinNumberRequest binNumberRequest) {

    BinNumber binNumber = this.toBinNumber(mandatoryRequest, binNumberRequest);

    DeferredResult<BaseResponse<BinNumberResponse>> deferred = new DeferredResult<>();

    this.binNumberService.createBinNumber(mandatoryRequest, binNumber)
        .map(binNumberCreated -> BeanMapper.map(binNumberCreated, BinNumberResponse.class))
        .map(binNumberCreated -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, binNumberCreated))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @ApiOperation(value = "Update Bin Number")
  @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
  public DeferredResult<BaseResponse<BinNumberResponse>> updateBinNumberById(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @Valid @RequestBody BinNumberRequest binNumberRequest,
      @PathVariable String id) {

    BinNumber binNumber = this.toBinNumber(mandatoryRequest, binNumberRequest);

    DeferredResult<BaseResponse<BinNumberResponse>> deferred = new DeferredResult<>();

    this.binNumberService.updateBinNumber(mandatoryRequest, binNumber, id)
        .map(binNumberUpdate -> BeanMapper.map(binNumberUpdate, BinNumberResponse.class))
        .map(binNumberUpdate -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, binNumberUpdate))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @ApiOperation(value = "Find BinNumber by Id", notes = "Get Document of BinNumber By Id")
  @GetMapping(path = "/{id}")
  public DeferredResult<BaseResponse<BinNumberResponse>> findBinNumberById(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @PathVariable String id) {
    DeferredResult<BaseResponse<BinNumberResponse>> deferred = new DeferredResult<>();

    this.binNumberService.findBinNumberById(mandatoryRequest, id)
        .map(this::toBinNumberResponse)
        .map(variableResponse -> CommonResponse.constructResponse(ResponseCode.SUCCESS.getCode(),
            ResponseCode.SUCCESS.getMessage(), null, variableResponse))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @ApiOperation(value = "Delete BinNumber by Id")
  @DeleteMapping(path = "/{id}")
  public DeferredResult<BaseResponse> deleteBinNumberById(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @NotBlank(message = "Id must be not blank")
      @NotEmpty(message = "Id not be empty")
      @PathVariable String id) {

    DeferredResult<BaseResponse> deferred = new DeferredResult<>();

    this.binNumberService.deleteBinNumber(mandatoryRequest, id)
        .map(success -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, null))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  private BinNumberResponse toBinNumberResponse(BinNumber binNumber) {
    return BeanMapper.map(binNumber, BinNumberResponse.class);
  }

  private List<String> toBinNumberResponseAll(List<BinNumber> binNumbers) {
    List<String> binNumberStrings = new ArrayList<>();
    for (BinNumber binNumber : binNumbers) {
      binNumberStrings.add(binNumber.getBinNumber());
    }
    return binNumberStrings;
  }

  private BinNumber toBinNumber(MandatoryRequest mandatoryRequest,
      BinNumberRequest binNumberRequest) {

    BinNumber binNumber = this.binNumberMapper(binNumberRequest);
    binNumber.setStoreId(mandatoryRequest.getStoreId());
    binNumber.setChannelId(mandatoryRequest.getChannelId());
    binNumber.setUsername(mandatoryRequest.getUsername());
    binNumber.setUpdatedBy(mandatoryRequest.getUsername());
    binNumber.setCreatedBy(mandatoryRequest.getUsername());

    return binNumber;
  }

  private BinNumber binNumberMapper(BinNumberRequest binNumberRequest) {
    return BeanMapper.map(binNumberRequest, BinNumber.class);
  }

  @ModelAttribute
  public MandatoryRequest getMandatoryParameter(HttpServletRequest request) {
    return (MandatoryRequest) request.getAttribute("mandatory");
  }

}
