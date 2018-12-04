package com.tl.booking.image.service.impl;

import com.tl.booking.image.dao.api.SystemParameterRepository;
import com.tl.booking.image.entity.constant.CacheKey;
import com.tl.booking.image.entity.constant.enums.ResponseCode;
import com.tl.booking.image.entity.dao.SystemParameter;
import com.tl.booking.image.libraries.exception.BusinessLogicException;
import com.tl.booking.image.service.api.SystemParameterService;
import java.util.Date;
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

  @Autowired
  SystemParameterRepository systemParameterRepository;

  @Override
//  @Cacheable(value = CacheKey.SYSTEM_PARAMETER, key = "#storeId + '-' + #page + '-' + #size")
  public Page<SystemParameter> findAll(String storeId, Integer page, Integer size) {
    Pageable pageable = new PageRequest(page, size);
    Page<SystemParameter> systemParametersPaginated = this.systemParameterRepository
        .findByStoreId(storeId, pageable);

    if (systemParametersPaginated.getNumberOfElements() == 0) {
      throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
          ResponseCode.DATA_NOT_EXIST.getMessage());
    }

    return systemParametersPaginated;
  }

  @Override
  public SystemParameter findSystemParameterByStoreId(String storeId, String variable) {
    SystemParameter systemParameter = this.systemParameterRepository
        .findSystemParameterByStoreIdAndVariable(storeId, variable);

    if (systemParameter == null) {
      throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
          ResponseCode.DATA_NOT_EXIST.getMessage());
    }

    return systemParameter;
  }

  private Boolean isExistSystemParameter(SystemParameter systemParameter)
  {
    return systemParameter != null;
  }

  @Override
  public void createSystemParameter(SystemParameter systemParameter) {

    SystemParameter getSystemParameter = this.systemParameterRepository
        .findSystemParameterByStoreIdAndVariable(systemParameter.getStoreId(), systemParameter.getVariable());

    if (isExistSystemParameter(getSystemParameter)) {
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
