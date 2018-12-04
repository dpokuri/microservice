package com.tl.booking.promo.code.rest.web.controller;

import com.tl.booking.common.libraries.BeanMapper;
import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.common.rest.web.model.response.BaseResponse;
import com.tl.booking.common.rest.web.model.response.CommonResponse;
import com.tl.booking.promo.code.entity.constant.ApiPath;
import com.tl.booking.promo.code.entity.constant.enums.ChannelIdColumn;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.dao.ChannelId;
import com.tl.booking.promo.code.rest.web.model.request.ChannelIdRequest;
import com.tl.booking.promo.code.rest.web.model.response.ChannelIdResponse;
import com.tl.booking.promo.code.service.api.ChannelIdService;
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
@RequestMapping(ApiPath.CHANNEL_ID_PATH)
@Api(value = "API ChannelId")
public class ChannelIdController {

  @Autowired
  private ChannelIdService channelIdService;


  @ApiOperation(value = "Find ChannelId by Id", notes = "Get Document of ChannelId By Id")
  @GetMapping(path = "/{id}")
  public DeferredResult<BaseResponse<ChannelIdResponse>> findChannelIdById(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @PathVariable String id) {
    DeferredResult<BaseResponse<ChannelIdResponse>> deferred = new DeferredResult<>();

    this.channelIdService.findChannelIdById(mandatoryRequest, id)
        .map(this::toVariableResponse)
        .map(variableResponse -> CommonResponse.constructResponse(ResponseCode.SUCCESS.getCode(),
            ResponseCode.SUCCESS.getMessage(), null, variableResponse))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }


  @ApiOperation(value = "Get.ChannelId (Paginated)", notes = "Get Document All of ChannelId with Pagination By ChannelId")
  @GetMapping
  public DeferredResult<BaseResponse<Page<ChannelIdResponse>>> findChannelIdFilterPaginated(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @RequestParam(required = false) String name,
      @RequestParam Integer page,
      @RequestParam Integer size,
      @RequestParam(required = false) ChannelIdColumn columnSort,
      @RequestParam(required = false) SortDirection sortDirection
  ) {

    DeferredResult<BaseResponse<Page<ChannelIdResponse>>> deferred = new DeferredResult<>();

    this.channelIdService.findChannelIdFilterPaginated(
        mandatoryRequest, name, page, size, columnSort, sortDirection)
        .map(this::toPageChannelIdResponse)
        .map(channelIds -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, channelIds))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @ApiOperation(value = "Create ChannelId")
  @PostMapping
  public DeferredResult<BaseResponse<ChannelIdResponse>> createChannelId(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @Valid @RequestBody ChannelIdRequest channelIdRequest) {

    ChannelId channelId = this.toChannelId(mandatoryRequest, channelIdRequest);

    DeferredResult<BaseResponse<ChannelIdResponse>> deferred = new DeferredResult<>();
    this.channelIdService.createChannelId(mandatoryRequest, channelId)
        .map(channelIdCreated -> BeanMapper.map(channelIdCreated, ChannelIdResponse.class))
        .map(channelIdCreated -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, channelIdCreated))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;

  }


  @ApiOperation(value = "Update channelId")
  @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
  public DeferredResult<BaseResponse<ChannelIdResponse>> updateChannelIdById(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @Valid @RequestBody ChannelIdRequest channelIdRequest,
      @PathVariable String id) {

    ChannelId channelId = this.toChannelId(mandatoryRequest, channelIdRequest);

    DeferredResult<BaseResponse<ChannelIdResponse>> deferred = new DeferredResult<>();

    this.channelIdService.updateChannelId(mandatoryRequest, channelId, id)
        .map(channelIdUpdated -> BeanMapper.map(channelIdUpdated, ChannelIdResponse.class))
        .map(channelIdUpdated -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, channelIdUpdated))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;

  }

  @ApiOperation(value = "Delete channelId by Id")
  @DeleteMapping(path = "/{id}")
  public DeferredResult<BaseResponse> deleteChannelIdById(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @NotBlank(message = "Id must be not blank")
      @NotEmpty(message = "Id not be empty")
      @PathVariable String id) {

    DeferredResult<BaseResponse> deferred = new DeferredResult<>();

    this.channelIdService.deleteChannelId(mandatoryRequest, id)
        .map(success -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, null))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @ApiOperation(value = "Find All ChannelId")
  @GetMapping(path = "/findAll")
  public DeferredResult<BaseResponse<List<ChannelIdResponse>>> findChannelIds(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest) {

    DeferredResult<BaseResponse<List<ChannelIdResponse>>> deferred = new DeferredResult<>();

    this.channelIdService.findChannelIds(
        mandatoryRequest)
        .map(this::toListChannelIdResponse)
        .map(channelIds -> CommonResponse
            .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, channelIds))
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }


  private ChannelId toChannelId(MandatoryRequest mandatoryRequest,
      ChannelIdRequest channelIdRequest) {

    ChannelId channelId = this.channelIdMapper(channelIdRequest);
    channelId.setStoreId(mandatoryRequest.getStoreId());
    channelId.setChannelId(mandatoryRequest.getChannelId());
    channelId.setUpdatedBy(mandatoryRequest.getUsername());
    channelId.setCreatedBy(mandatoryRequest.getUsername());

    return channelId;
  }

  private ChannelIdResponse toVariableResponse(ChannelId channelId) {
    return BeanMapper.map(channelId, ChannelIdResponse.class);
  }

  private ChannelId channelIdMapper(ChannelIdRequest channelIdRequest) {
    return BeanMapper.map(channelIdRequest, ChannelId.class);
  }

  private Page<ChannelIdResponse> toPageChannelIdResponse(
      Page<ChannelId> channelIds) {
    return channelIds
        .map(channelId -> BeanMapper.map(channelId, ChannelIdResponse.class));
  }

  private List<ChannelIdResponse> toListChannelIdResponse(
      List<ChannelId> storeIds) {
    return BeanMapper.mapAsList(storeIds, ChannelIdResponse.class);
  }

  @ModelAttribute
  public MandatoryRequest getMandatoryParameter(HttpServletRequest request) {
    return (MandatoryRequest) request.getAttribute("mandatory");
  }

}
