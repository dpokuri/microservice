package com.tl.booking.promo.code.rest.web.controller;

import com.tl.booking.common.libraries.BeanMapper;
import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.common.rest.web.model.response.BaseResponse;
import com.tl.booking.common.rest.web.model.response.CommonResponse;
import com.tl.booking.promo.code.entity.constant.ApiPath;
import com.tl.booking.promo.code.entity.constant.enums.BankColumn;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.dao.Bank;
import com.tl.booking.promo.code.rest.web.model.request.BankRequest;
import com.tl.booking.promo.code.rest.web.model.response.BankResponse;
import com.tl.booking.promo.code.service.api.BankService;
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
@RequestMapping(ApiPath.BANK_PATH)
@Api(value = "API Bank")
public class BankController {

  @Autowired
  private BankService bankService;

  @ApiOperation(value = "Find List of Bank")
  @GetMapping(path = "/findAll")
  public DeferredResult<BaseResponse<List<BankResponse>>> bank(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest) {
    DeferredResult<BaseResponse<List<BankResponse>>> deferred = new DeferredResult<>();

    this.bankService.findBanks(mandatoryRequest)
        .map(this::toBankResponse)
        .map(bankResponse -> CommonResponse.constructResponse(ResponseCode.SUCCESS.getCode(),
            ResponseCode.SUCCESS.getMessage(), null, bankResponse))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @ApiOperation(value = "Find List of Bank (Paginated)", notes = "Get Document All of Bank with Pagination By StoreId")
  @GetMapping
  public DeferredResult<BaseResponse<Page<BankResponse>>> findBankFilterPaginated(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @RequestParam(required = false) String name,
      @RequestParam Integer page,
      @RequestParam Integer size,
      @RequestParam(required = false) BankColumn columnSort,
      @RequestParam(required = false) SortDirection sortDirection
  ) {

    DeferredResult<BaseResponse<Page<BankResponse>>> deferred = new DeferredResult<>();

    this.bankService.findBankFilterPaginated(
        mandatoryRequest, name, page, size, columnSort, sortDirection)
        .map(this::toPageBankResponse)
        .map(banks -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, banks))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  private Page<BankResponse> toPageBankResponse(
      Page<Bank> banks) {
    return banks
        .map(bank -> BeanMapper.map(bank, BankResponse.class));
  }

  @ApiOperation(value = "Find Bank by Id", notes = "Get Document of Bank By Id")
  @GetMapping(path = "/{id}")
  public DeferredResult<BaseResponse<BankResponse>> findBankById(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @PathVariable String id) {
    DeferredResult<BaseResponse<BankResponse>> deferred = new DeferredResult<>();

    this.bankService.findBankById(mandatoryRequest, id)
        .map(this::toVariableResponse)
        .map(variableResponse -> CommonResponse.constructResponse(ResponseCode.SUCCESS.getCode(),
            ResponseCode.SUCCESS.getMessage(), null, variableResponse))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @ApiOperation(value = "Create Bank")
  @PostMapping
  public DeferredResult<BaseResponse<BankResponse>> createBank(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @Valid @RequestBody BankRequest bankRequest) {

    Bank bank = this.toBank(mandatoryRequest, bankRequest);

    DeferredResult<BaseResponse<BankResponse>> deferred = new DeferredResult<>();
    this.bankService.createBank(mandatoryRequest, bank)
        .map(bankCreated -> BeanMapper.map(bankCreated, BankResponse.class))
        .map(bankCreated -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, bankCreated))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @ApiOperation(value = "Update Bank")
  @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
  public DeferredResult<BaseResponse<BankResponse>> updateBankById(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @Valid @RequestBody BankRequest bankRequest,
      @PathVariable String id) {

    Bank bank = this.toBank(mandatoryRequest, bankRequest);

    DeferredResult<BaseResponse<BankResponse>> deferred = new DeferredResult<>();

    this.bankService.updateBank(mandatoryRequest, bank, id)
        .map(bankUpdate -> BeanMapper.map(bankUpdate, BankResponse.class))
        .map(bankUpdate -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, bankUpdate))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @ApiOperation(value = "Delete bank by Id")
  @DeleteMapping(path = "/{id}")
  public DeferredResult<BaseResponse> deleteBankById(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @NotBlank(message = "Id must be not blank")
      @NotEmpty(message = "Id not be empty")
      @PathVariable String id) {

    DeferredResult<BaseResponse> deferred = new DeferredResult<>();

    this.bankService.deleteBank(mandatoryRequest, id)
        .map(success -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, null))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }


  private Bank toBank(MandatoryRequest mandatoryRequest,
      BankRequest bankRequest) {

    Bank bank = this.bankMapper(bankRequest);
    bank.setStoreId(mandatoryRequest.getStoreId());
    bank.setChannelId(mandatoryRequest.getChannelId());
    bank.setUsername(mandatoryRequest.getUsername());
    bank.setUpdatedBy(mandatoryRequest.getUsername());
    bank.setCreatedBy(mandatoryRequest.getUsername());

    return bank;
  }

  private Bank bankMapper(BankRequest bankRequest) {
    return BeanMapper.map(bankRequest, Bank.class);
  }

  private List<BankResponse> toBankResponse(List<Bank> banks) {
    return BeanMapper.mapAsList(banks, BankResponse.class);
  }

  private BankResponse toVariableResponse(Bank bank) {
    return BeanMapper.map(bank, BankResponse.class);
  }

  @ModelAttribute
  public MandatoryRequest getMandatoryParameter(HttpServletRequest request) {
    return (MandatoryRequest) request.getAttribute("mandatory");
  }

}
