package com.tl.booking.promo.code.service.impl;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.dao.api.VariableRepository;
import com.tl.booking.promo.code.entity.constant.CacheKey;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.enums.VariableColumn;
import com.tl.booking.promo.code.entity.dao.Variable;
import com.tl.booking.promo.code.libraries.exception.BusinessLogicException;
import com.tl.booking.promo.code.libraries.utility.DateFormatterHelper;
import com.tl.booking.promo.code.service.api.CacheService;
import com.tl.booking.promo.code.service.api.VariableService;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@Service
public class VariableServiceImpl implements VariableService {

  private static final Logger LOGGER = LoggerFactory.getLogger(VariableServiceImpl.class);

  @Autowired
  CacheService cacheService;

  @Autowired
  VariableRepository variableRepository;

  @Override
  public Single<Variable> createVariable(MandatoryRequest mandatoryRequest, Variable variable) {
    return Single.<Variable>create(singleEmitter -> {
      LOGGER.info("createVariable request {} and {}", mandatoryRequest, variable);
      Variable checkVariable = this.variableRepository
          .findVariableByStoreIdAndParamAndIsDeleted(mandatoryRequest.getStoreId(),
              variable.getParam(), 0);

      if (isExistVariable(checkVariable)) {
        throw new BusinessLogicException(ResponseCode.DUPLICATE_DATA.getCode(),
            ResponseCode.DUPLICATE_DATA.getMessage());
      }

      Variable createdVariable = this.variableRepository.save(variable);

      if (!isExistVariable(createdVariable)) {
        throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
            ResponseCode.SYSTEM_ERROR.getMessage());
      }

      String cacheKey = this.generateCacheKey(mandatoryRequest.getStoreId(), variable.getId());
      this.cacheService.createCache(cacheKey, variable, 0);

      this.deleteCacheAll(mandatoryRequest);

      LOGGER.info("createVariable response {}", createdVariable);

      singleEmitter.onSuccess(createdVariable);

    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<Variable> updateVariable(MandatoryRequest mandatoryRequest,
      Variable variableParam, String id) {

    LOGGER.info("updateVariable request {}, {}, {}", mandatoryRequest, variableParam,
        id);

    return Single.<Variable>create(singleEmitter -> {
      Variable variable = this.variableRepository
          .findVariableByStoreIdAndIdAndIsDeleted(mandatoryRequest.getStoreId(), id, 0);

      if (!isExistVariable(variable)) {
        throw new BusinessLogicException(ResponseCode.VARIABLE_NOT_EXIST.getCode(),
            ResponseCode.VARIABLE_NOT_EXIST.getMessage());
      }

      this.checkStoreIdAndParamForOtherDocument(variable, variableParam);

      this.combineVariableAndVariableParam(variable, variableParam);

      Variable updatedVariable = this.variableRepository.save(variable);

      if (!isExistVariable(updatedVariable)) {
        throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
            ResponseCode.SYSTEM_ERROR.getMessage());
      }

      String cacheKey = this
          .generateCacheKey(mandatoryRequest.getStoreId(), updatedVariable.getId());
      this.cacheService.deleteCache(cacheKey);
      this.cacheService.createCache(cacheKey, updatedVariable, 0);

      this.deleteCacheAll(mandatoryRequest);

      LOGGER.info("updateVariable response {}, {}, {}", mandatoryRequest, variableParam, id);
      singleEmitter.onSuccess(updatedVariable);

    }).subscribeOn(Schedulers.io());

  }

  @Override
  public Single<Boolean> deleteVariable(MandatoryRequest mandatoryRequest, String id) {

    LOGGER.info("deleteVariable request {}, {}", mandatoryRequest, id);
    return Single.<Boolean>create(singleEmitter -> {
      Variable variable = this.variableRepository
          .findVariableByStoreIdAndIdAndIsDeleted(mandatoryRequest.getStoreId(), id, 0);

      if (!isExistVariable(variable)) {
        throw new BusinessLogicException(ResponseCode.VARIABLE_NOT_EXIST.getCode(),
            ResponseCode.VARIABLE_NOT_EXIST.getMessage());
      }

      variable.setIsDeleted(1);
      variable.setUpdatedBy(mandatoryRequest.getUsername());
      variable.setUpdatedDate(DateFormatterHelper.getTodayDateTime());
      Boolean deleted = this.variableRepository.softDeleted(variable, id);

      if (!deleted) {
        throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
            ResponseCode.SYSTEM_ERROR.getMessage());
      }

      String cacheKey = this.generateCacheKey(mandatoryRequest.getStoreId(), id);
      this.cacheService.deleteCache(cacheKey);

      this.deleteCacheAll(mandatoryRequest);

      singleEmitter.onSuccess(true);

    }).subscribeOn(Schedulers.io());
  }

  private void deleteCacheAll(MandatoryRequest mandatoryRequest) {
    String cacheKey = this.generateCacheKeyFindAll(mandatoryRequest.getStoreId());
    this.cacheService.deleteCache(cacheKey);

    String cacheKeyMapped = this.generateCacheKeyMapped(mandatoryRequest.getStoreId());
    this.cacheService.deleteCache(cacheKeyMapped);
  }

  @Override
  public Single<Variable> findVariableById(MandatoryRequest mandatoryRequest, String id) {
    LOGGER.info("findVariableById request {}, {}", mandatoryRequest, id);
    return Single.<Variable>create(
        singleEmitter -> {
          String cacheKey = this.generateCacheKey(mandatoryRequest.getStoreId(), id);
          Variable variable = this.cacheService.findCacheByKey(cacheKey, Variable.class);

          if (!isExistVariable(variable)) {
            variable = this.variableRepository
                .findVariableByStoreIdAndIdAndIsDeleted(mandatoryRequest.getStoreId(), id, 0);

            if (!isExistVariable(variable)) {
              throw new BusinessLogicException(ResponseCode.VARIABLE_NOT_EXIST.getCode(),
                  ResponseCode.VARIABLE_NOT_EXIST.getMessage());
            }

            this.cacheService.createCache(cacheKey, variable, 0);
          }

          LOGGER.info("findVariableById response {}", variable);
          singleEmitter.onSuccess(variable);
        }
    ).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<Map<String, Variable>> findVariablesMapped(MandatoryRequest mandatoryRequest) {
    LOGGER.info("findVariablesMapped request {}", mandatoryRequest);
    return Single.create(singleEmitter -> {
      String cacheKey = this.generateCacheKeyMapped(mandatoryRequest.getStoreId());
      Map<String, Variable> variableMap = this.cacheService.findCacheByKey(cacheKey, Map.class);
      if (!isExistVariablesMap(variableMap)) {
        List<Variable> variables = this.variableRepository.findAllByIsDeleted(0);
        variableMap = new HashMap<>();
        for (Variable variable : variables) {
          variableMap.put(variable.getParam(), variable);
        }

        this.cacheService.createCache(cacheKey, variableMap, 60);
      }
      LOGGER.info("findVariablesMapped response {}", variableMap);

      singleEmitter.onSuccess(variableMap);
    });
  }

  private boolean isExistVariablesMap(Map<String, Variable> variableMap) {
    return variableMap != null;
  }

  @Override
  public Single<List<Variable>> findVariables(MandatoryRequest mandatoryRequest) {
    LOGGER.info("findVariables request {}", mandatoryRequest);
    return Single.create(singleEmitter -> {
      String cacheKey = this.generateCacheKeyFindAll(mandatoryRequest.getStoreId());
      List<Variable> variables = this.cacheService.findCacheByKey(cacheKey, List.class);
      if (!isExistVariables(variables)) {
        variables = this.variableRepository.findAllByIsDeleted(0);
      }
      LOGGER.info("findVariables response {}", variables);

      this.cacheService.createCache(cacheKey, variables, 0);
      singleEmitter.onSuccess(variables);
    });
  }

  @Override
  public Single<Page<Variable>> findVariableFilterPaginated(MandatoryRequest mandatoryRequest,
      String param, String inputType, Integer page, Integer size, VariableColumn columnSort,
      SortDirection sortDirection) {
    LOGGER.info("findVariableFilterPaginated request {}, {}, {}, {}, {}, {}",
        mandatoryRequest, param, inputType, page, size, columnSort, sortDirection);

    return Single.<Page<Variable>>create(
        singleEmitter -> {

          String sort = setColumnSort(columnSort);

          Page<Variable> variables = this.variableRepository
              .findVariableFilterPaginated(mandatoryRequest.getStoreId(), param, inputType, page,
                  size, sort, sortDirection);

          LOGGER.info("findVariableFilterPaginated response {}", variables);
          singleEmitter.onSuccess(variables);
        }
    ).subscribeOn(Schedulers.io());
  }

  private Boolean isExistVariable(Variable variable) {
    Boolean isExistVariable = false;
    if (variable != null) {
      isExistVariable = true;
    }

    return isExistVariable;
  }

  private boolean isExistVariables(List<Variable> variables) {
    return variables != null;
  }

  private String generateCacheKey(String storeId, String id) {
    return CacheKey.VARIABLE + "-" + storeId + "-" + id;
  }

  private String generateCacheKeyFindAll(String storeId) {
    return CacheKey.VARIABLE + "-" + storeId;
  }

  private String generateCacheKeyMapped(String storeId) {
    return CacheKey.VARIABLE_MAPPED + "-" + storeId;
  }

  private String setColumnSort(VariableColumn variableColumn) {
    String result = null;
    if (isNotNullColumnSort(variableColumn)) {
      result = variableColumn.getValue();
    }

    return result;
  }

  private Boolean isNotNullColumnSort(VariableColumn variableColumn) {
    Boolean result = false;
    if (variableColumn != null) {
      result = true;
    }

    return result;
  }


  private void checkStoreIdAndParamForOtherDocument(Variable variable, Variable variableParam) {
    if (isExistVariable(variable) && !variable.getParam()
        .equals(variableParam.getParam())) {

      Boolean checkDuplicateStoreIdAndCode = this.checkExistOtherStoreIdAndParam(variableParam);
      if (checkDuplicateStoreIdAndCode) {
        throw new BusinessLogicException(ResponseCode.PARAM_EXIST_OTHER_RECORD.getCode(),
            ResponseCode.PARAM_EXIST_OTHER_RECORD.getMessage());
      }
    }
  }

  private Boolean checkExistOtherStoreIdAndParam(Variable variableParam) {
    Boolean result = false;
    Variable variable = this.variableRepository
        .findVariableByStoreIdAndParamAndIsDeleted(variableParam.getStoreId(),
            variableParam.getParam(), 0);
    if (isExistVariable(variable)) {
      result = true;
    }

    return result;
  }

  private void combineVariableAndVariableParam(Variable variable, Variable variableParam) {
    variable.setVersion(variable.getVersion());
    variable.setStoreId(variable.getStoreId());
    variable.setUsername(variable.getUsername());
    variable.setCreatedDate(variable.getCreatedDate());
    variable.setCreatedBy(variable.getCreatedBy());
    variable.setUpdatedBy(variableParam.getUsername());

    variable.setAllowedArithmetics(variableParam.getAllowedArithmetics());
    variable.setDataType(variableParam.getDataType());
    variable.setInputSource(variableParam.getInputSource());
    variable.setInputType(variableParam.getInputType());
    variable.setInputData(variableParam.getInputData());
    variable.setParam(variableParam.getParam());
    variable.setProductTypes(variableParam.getProductTypes());
    variable.setDescription(variableParam.getDescription());
    variable.setName(variableParam.getName());

  }

}
