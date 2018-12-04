package com.tl.booking.promo.code.rest.web.controller;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.common.rest.web.model.response.BaseResponse;
import com.tl.booking.common.rest.web.model.response.CommonResponse;
import com.tl.booking.promo.code.entity.constant.ApiPath;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.dao.VariableSource;
import com.tl.booking.promo.code.rest.web.model.response.VariableSourceResponse;
import com.tl.booking.promo.code.service.api.VariableSourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.annotations.ApiIgnore;


@RestController
@RequestMapping(ApiPath.VARIABLE_SOURCE_PATH)
@Api(value = "API Variable Source")
public class VariableSourceController {

  @Autowired
  private VariableSourceService variableSourceService;

  @ApiOperation(value = "Find variables", notes = "Get Document of Variables")
  @GetMapping(path = "/{sourceType}")
  public DeferredResult<BaseResponse<List<VariableSourceResponse>>> findAll(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @PathVariable String sourceType,
      @RequestParam(value = "key", required = false) String key) {
    DeferredResult<BaseResponse<List<VariableSourceResponse>>> deferred = new DeferredResult<>();

    this.variableSourceService.findVariableSourceBySourceTypeKey(mandatoryRequest, sourceType, key)
        .map(this::toVariableSourceResponses)
        .map(variableSourceResponses -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(),
                ResponseCode.SUCCESS.getMessage(), null, variableSourceResponses))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  private List<VariableSourceResponse> toVariableSourceResponses(List<VariableSource>
      variableSources) {
    List<VariableSourceResponse> variableSourceResponses = new ArrayList<>();
    for (VariableSource variableSource : variableSources) {
      VariableSourceResponse variableSourceResponse = new VariableSourceResponse();
      variableSourceResponse.setValue(variableSource.getValueId());
      variableSourceResponse.setLabel(variableSource.getValueName());
      variableSourceResponses.add(variableSourceResponse);
    }

    return variableSourceResponses;
  }

  @ModelAttribute
  public MandatoryRequest getMandatoryParameter(HttpServletRequest request) {
    return (MandatoryRequest) request.getAttribute("mandatory");
  }

}
