package com.tl.booking.gateway.service.impl;

import com.mongodb.WriteResult;
import com.tl.booking.gateway.dao.api.SystemParameterRepository;
import com.tl.booking.gateway.libraries.exception.BusinessLogicException;
import com.tl.booking.gateway.service.api.SystemParameterService;
import com.tl.booking.gateway.entity.constant.CacheKey;
import com.tl.booking.gateway.entity.constant.enums.ResponseCode;
import com.tl.booking.gateway.entity.dao.SystemParameter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SystemParameterServiceImpl implements SystemParameterService {

  private static final String CACHE_KEY_BY_ID = "#id";

  @Autowired
  SystemParameterRepository systemParameterRepository;

  @Override
  @Cacheable(value = CacheKey.SYSTEM_PARAMETER, key = "#storeId + '-' + #page + '-' + #size")
  public Page<SystemParameter> findAllSystemParametersByStoreId(String storeId, Integer page,
      Integer size) {
    Pageable pageable = new PageRequest(page, size);
    Page<SystemParameter> systemParametersPaginated = this.systemParameterRepository
        .findSystemParametersByStoreIdAndIsDeleted(storeId, 0, pageable);

    if (systemParametersPaginated.getTotalElements() == 0) {
      throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
          ResponseCode.DATA_NOT_EXIST.getMessage());
    }

    return systemParametersPaginated;
  }

  @Override
  @Cacheable(value = CacheKey.SYSTEM_PARAMETER, key = CACHE_KEY_BY_ID)
  public SystemParameter findSystemParameterById(String id) {
    SystemParameter systemParameter = this.systemParameterRepository
        .findSystemParameterByIdAndIsDeleted(id, 0);

    if (!isExistSystemParameter(systemParameter)) {
      throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
          ResponseCode.DATA_NOT_EXIST.getMessage());
    }

    return systemParameter;
  }

  @Override
  public SystemParameter findSystemParameterByStoreIdAndVariable(String storeId, String variable) {
    SystemParameter systemParameter = this.systemParameterRepository
        .findSystemParameterByStoreIdAndVariableAndIsDeleted(storeId, variable, 0);

    if (!isExistSystemParameter(systemParameter)) {
      throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
          ResponseCode.DATA_NOT_EXIST.getMessage());
    }

    return systemParameter;
  }

  private boolean isExistSystemParameter(SystemParameter systemParameter) {
    Boolean exist = true;

    if (systemParameter == null) {
      exist = false;
    }

    return exist;
  }

  @Override
  public SystemParameter createSystemParameter(SystemParameter systemParameter) {
    SystemParameter existSystemParameter = this.systemParameterRepository
        .findSystemParameterByStoreIdAndVariableAndIsDeleted(systemParameter.getStoreId(),
            systemParameter.getVariable(), 0);

    if (isExistSystemParameter(existSystemParameter)) {
      throw new BusinessLogicException(ResponseCode.DUPLICATE_DATA.getCode(),
          ResponseCode.DUPLICATE_DATA.getMessage());
    }

    return this.systemParameterRepository.save(systemParameter);
  }

  @Override
  @CacheEvict(value = CacheKey.SYSTEM_PARAMETER, key = SystemParameterServiceImpl.CACHE_KEY_BY_ID)
  public SystemParameter updateSystemParameter(SystemParameter newSystemParameter, String id) {
    SystemParameter existSystemParameter = this.systemParameterRepository
        .findSystemParameterByIdAndIsDeleted(id, 0);

    if (!isExistSystemParameter(existSystemParameter)) {
      throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
          ResponseCode.DATA_NOT_EXIST.getMessage());
    }

    SystemParameter duplicateSystemParameter = this
        .systemParameterRepository
        .findSystemParameterByStoreIdAndVariableAndIsDeleted(newSystemParameter.getStoreId(),
            newSystemParameter.getVariable(), 0);

    if (isExistSystemParameter(duplicateSystemParameter)) {
      throw new BusinessLogicException(ResponseCode.DUPLICATE_DATA.getCode(),
          ResponseCode.DUPLICATE_DATA.getMessage());
    }

    existSystemParameter.setValue(newSystemParameter.getValue());
    existSystemParameter.setDescription(newSystemParameter.getDescription());

    return this.systemParameterRepository.save(existSystemParameter);
  }

  @Override
  @CacheEvict(value = CacheKey.SYSTEM_PARAMETER, key = SystemParameterServiceImpl.CACHE_KEY_BY_ID)
  public Boolean deleteSystemParameterById(String id) {
    SystemParameter systemParameter = this.systemParameterRepository
        .findSystemParameterByIdAndIsDeleted(id, 0);

    if (!isExistSystemParameter(systemParameter)) {
      throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
          ResponseCode.DATA_NOT_EXIST.getMessage());
    }

    systemParameter.setIsDeleted(1);

    WriteResult writeResult = this.systemParameterRepository
        .updateSystemParameterIsDeleteById(id, 1);

    Boolean success = true;

    if(writeResult.getN() == 0) {
      success = false;
    }

    return success;
  }
}
