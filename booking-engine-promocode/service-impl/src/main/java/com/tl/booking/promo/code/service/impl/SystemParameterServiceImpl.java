package com.tl.booking.promo.code.service.impl;

import com.tl.booking.promo.code.dao.api.SystemParameterRepository;
import com.tl.booking.promo.code.entity.constant.CacheKey;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.dao.SystemParameter;
import com.tl.booking.promo.code.libraries.exception.BusinessLogicException;
import com.tl.booking.promo.code.service.api.SystemParameterService;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SystemParameterServiceImpl implements SystemParameterService {

  private static final String CACHE_KEY_BY_STORE_ID_AND_VARIABLE = "#storeId + '-' + #variable";
  private static final String CACHE_KEY_BY_OBJECT = "#newSystemParameter.storeId + '-' + #newSystemParameter.variable";
  private static final Logger LOGGER = LoggerFactory.getLogger(SystemParameterServiceImpl.class);

  @Autowired
  SystemParameterRepository systemParameterRepository;

  @Override
  public Page<SystemParameter> findAll(String storeId, Integer page, Integer size) {
    Pageable pageable = new PageRequest(page, size);
    Page<SystemParameter> systemParametersPaginated = this.systemParameterRepository
        .findByStoreId(storeId, pageable);

    if (systemParametersPaginated.getNumberOfElements() == 0) {
      throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
          ResponseCode.DATA_NOT_EXIST.getMessage());
    }

    LOGGER.info("Result of System Parameter {} ", storeId);

    return systemParametersPaginated;
  }

  @Override
  public SystemParameter findSystemParameterByStoreId(String storeId, String variable) {
    SystemParameter systemParameter = this.systemParameterRepository
        .findSystemParameterByStoreIdAndVariableAndIsDeleted(storeId, variable, 0);

    LOGGER
        .info("findSystemParameterByStoreId Response StoreId and Variable {} ", storeId, variable);

    if (isNullSystemParameter(systemParameter)) {
      throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
          ResponseCode.DATA_NOT_EXIST.getMessage());
    }

    return systemParameter;
  }

  private boolean isNullSystemParameter(SystemParameter systemParameter) {
    return systemParameter == null;
  }

  @Override
  public void createSystemParameter(SystemParameter systemParameter) {
    SystemParameter exist = this.systemParameterRepository
        .findSystemParameterByStoreIdAndVariableAndIsDeleted(systemParameter.getStoreId(),
            systemParameter.getVariable(), 0);

    if (!isNullSystemParameter(exist)) {
      throw new BusinessLogicException(ResponseCode.DUPLICATE_DATA.getCode(),
          ResponseCode.DUPLICATE_DATA.getMessage());
    }

    this.systemParameterRepository.save(systemParameter);
  }

  @Override
  @CacheEvict(value = CacheKey.SYSTEM_PARAMETER, key = SystemParameterServiceImpl.CACHE_KEY_BY_OBJECT)
  public void updateSystemParameter(SystemParameter newSystemParameter) {
    SystemParameter systemParameter = this
        .findSystemParameterByStoreId(newSystemParameter.getStoreId(),
            newSystemParameter.getVariable());

    systemParameter.setValue(newSystemParameter.getValue());
    systemParameter.setDescription(newSystemParameter.getDescription());
    systemParameter.setUpdatedBy(newSystemParameter.getUpdatedBy());
    systemParameter.setUpdatedDate(new Date());

    this.systemParameterRepository.save(systemParameter);
  }

  @Override
  @CacheEvict(value = CacheKey.SYSTEM_PARAMETER, key = SystemParameterServiceImpl.CACHE_KEY_BY_STORE_ID_AND_VARIABLE)
  public void deleteByStoreIdAndVariable(String storeId, String variable) {
    this.systemParameterRepository.deleteSystemParameterByStoreIdAndVariable(storeId, variable);
  }
}
