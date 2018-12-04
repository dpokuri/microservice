package com.tl.booking.promo.code.rest.web.controller;

import com.tl.booking.common.libraries.BeanMapper;
import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.common.rest.web.model.response.BaseResponse;
import com.tl.booking.common.rest.web.model.response.CommonResponse;
import com.tl.booking.promo.code.entity.constant.ApiPath;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.enums.VariableColumn;
import com.tl.booking.promo.code.entity.dao.Variable;
import com.tl.booking.promo.code.rest.web.model.request.VariableRequest;
import com.tl.booking.promo.code.rest.web.model.response.VariableFindAllResponse;
import com.tl.booking.promo.code.rest.web.model.response.VariableResponse;
import com.tl.booking.promo.code.service.api.VariableService;
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
import org.springframework.kafka.core.KafkaTemplate;
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
@RequestMapping(ApiPath.VARIABLE_PATH)
@Api(value = "API Variable")
public class VariableController {

  @Autowired
  private VariableService variableService;

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  @ApiOperation(value = "Find variable by Id", notes = "Get Document of Variable By Id")
  @GetMapping(path = "/{id}")
  public DeferredResult<BaseResponse<VariableResponse>> findVariableById(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @PathVariable String id) {
    DeferredResult<BaseResponse<VariableResponse>> deferred = new DeferredResult<>();

    this.variableService.findVariableById(mandatoryRequest, id)
        .map(this::toVariableResponse)
        .map(variableResponse -> CommonResponse.constructResponse(ResponseCode.SUCCESS.getCode(),
            ResponseCode.SUCCESS.getMessage(), null, variableResponse))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @ApiOperation(value = "Find variables", notes = "Get Document of Variables")
  @GetMapping(path = "/findAll")
  public DeferredResult<BaseResponse<List<VariableFindAllResponse>>> findAll(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest) {
    DeferredResult<BaseResponse<List<VariableFindAllResponse>>> deferred = new DeferredResult<>();

    this.variableService.findVariables(mandatoryRequest)
        .map(this::toVariableFindAllResponse)
        .map(variableResponse -> CommonResponse.constructResponse(ResponseCode.SUCCESS.getCode(),
            ResponseCode.SUCCESS.getMessage(), null, variableResponse))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @ApiOperation(value = "Get.Variables (Paginated)", notes = "Get Document All of Variable with Pagination By StoreId")
  @GetMapping
  public DeferredResult<BaseResponse<Page<VariableResponse>>> findVariableFilterPaginated(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @RequestParam(required = false) String param,
      @RequestParam(required = false) String inputType,
      @RequestParam Integer page,
      @RequestParam Integer size,
      @RequestParam(required = false) VariableColumn columnSort,
      @RequestParam(required = false) SortDirection sortDirection
  ) {

    DeferredResult<BaseResponse<Page<VariableResponse>>> deferred = new DeferredResult<>();

    this.variableService.findVariableFilterPaginated(
        mandatoryRequest, param, inputType, page, size, columnSort, sortDirection)
        .map(this::toPageVariableResponse)
        .map(variableResponses -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, variableResponses))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @ApiOperation(value = "Create Variable")
  @PostMapping
  public DeferredResult<BaseResponse<VariableResponse>> createVariable(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @Valid @RequestBody VariableRequest variableRequest) {

    Variable variable = this.toVariable(mandatoryRequest, variableRequest);

    DeferredResult<BaseResponse<VariableResponse>> deferred = new DeferredResult<>();
    this.variableService.createVariable(mandatoryRequest, variable)
        .map(variableCreated ->
            BeanMapper.map(variableCreated, VariableResponse.class)
        )
        .map(variableCreated -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, variableCreated))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;

  }


  @ApiOperation(value = "Update variable")
  @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
  public DeferredResult<BaseResponse<VariableResponse>> updateVariableById(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @Valid @RequestBody VariableRequest variableRequest,
      @PathVariable String id) {

    Variable variable = this.toVariable(mandatoryRequest, variableRequest);

    DeferredResult<BaseResponse<VariableResponse>> deferred = new DeferredResult<>();

    this.variableService.updateVariable(mandatoryRequest, variable, id)
        .map(variableUpdated -> BeanMapper.map(variableUpdated, VariableResponse.class))
        .map(variableUpdated -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, variableUpdated))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;

  }

  @ApiOperation(value = "Delete variable by Id")
  @DeleteMapping(path = "/{id}")
  public DeferredResult<BaseResponse> deleteVariableById(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @NotBlank(message = "Id must be not blank")
      @NotEmpty(message = "Id not be empty")
      @PathVariable String id) {

    DeferredResult<BaseResponse> deferred = new DeferredResult<>();

    this.variableService.deleteVariable(mandatoryRequest, id)
        .map(success -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, null))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @ApiOperation(value = "Test Kafka Send")
  @PostMapping(path = "/sendKafka")
  public String sendKafka(
      @RequestParam(value = "topic") String key,
      @RequestBody String message) {

    kafkaTemplate.send(key, "test", message);

    return message;
  }

  private Variable toVariable(MandatoryRequest mandatoryRequest,
      VariableRequest variableRequest) {
    Variable variable = this.variableMapper(variableRequest);
    variable.setStoreId(mandatoryRequest.getStoreId());
    variable.setUpdatedBy(mandatoryRequest.getUsername());
    variable.setCreatedBy(mandatoryRequest.getUsername());

    return variable;
  }

  private List<VariableFindAllResponse> toVariableFindAllResponse(List<Variable> variables) {
    List<VariableFindAllResponse> variableFindAllResponses = new ArrayList<>();

    for (Variable variable : variables) {
      VariableFindAllResponse variableFindAllResponse = BeanMapper.map(variable,
          VariableFindAllResponse.class);
      variableFindAllResponse.setLabel(variable.getName());
      variableFindAllResponse.setValue(variable.getParam());
      variableFindAllResponses.add(variableFindAllResponse);
    }
    return variableFindAllResponses;
  }

  private VariableResponse toVariableResponse(Variable variable) {
    return BeanMapper.map(variable, VariableResponse.class);
  }

  private Variable variableMapper(VariableRequest variableRequest) {
    return BeanMapper.map(variableRequest, Variable.class);
  }

  private Page<VariableResponse> toPageVariableResponse(
      Page<Variable> variables) {
    return variables
        .map(variable -> BeanMapper.map(variable, VariableResponse.class));
  }


  @ModelAttribute
  public MandatoryRequest getMandatoryParameter(HttpServletRequest request) {
    return (MandatoryRequest) request.getAttribute("mandatory");
  }

}
