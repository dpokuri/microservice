package com.tl.booking.promo.code.service.impl;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.dao.api.VariableSourceRepository;
import com.tl.booking.promo.code.entity.constant.CacheKey;
import com.tl.booking.promo.code.entity.dao.VariableSource;
import com.tl.booking.promo.code.service.api.CacheService;
import com.tl.booking.promo.code.service.api.VariableSourceService;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class VariableSourceServiceImpl implements VariableSourceService {

  private static final Logger LOGGER = LoggerFactory.getLogger(VariableSourceServiceImpl.class);

  @Autowired
  CacheService cacheService;

  @Autowired
  VariableSourceRepository variableSourceRepository;

  @Override
  public Single<Boolean> updateVariableSources(MandatoryRequest mandatoryRequest,
      List<VariableSource> variableSources) {
    return Single.<Boolean>create(singleEmitter -> {
      LOGGER.info("updateVariableSources request {} and {}", mandatoryRequest, variableSources);

      for (VariableSource variableSource : variableSources) {
        VariableSource detailVariableSource = this.variableSourceRepository
            .findVariableSourceBySourceTypeAndValueIdAndStoreIdAndIsDeleted(variableSource
                .getSourceType(), variableSource
                .getValueId(), mandatoryRequest.getStoreId(), 0);

        if (isExistVariableSource(detailVariableSource)) {
          variableSource.setId(detailVariableSource.getId());
        }
        VariableSource createdVariableSource = this.variableSourceRepository.save(variableSource);

        String cacheKey = this.generateCacheKeyByStoreIdAndSourceType(mandatoryRequest.getStoreId(),
            createdVariableSource.getId());
        this.cacheService.deleteCache(cacheKey);

        LOGGER.info("updateVariableSources response {}", createdVariableSource);
      }
      singleEmitter.onSuccess(true);
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<List<VariableSource>> findVariableSourceBySourceTypeKey(
      MandatoryRequest mandatoryRequest,
      String sourceType, String key) {
    return Single.<List<VariableSource>>create(singleEmitter -> {
      List<VariableSource> variableSources;
      if (isKeyExistAndNotBlank(key)) {
        variableSources = this.generateVariableSourcesDataBySourceTypeAndKey(mandatoryRequest,
            sourceType, key);
      } else {
        variableSources = this.generateVariableSourcesDataBySourceType(mandatoryRequest,
            sourceType);
      }
      singleEmitter.onSuccess(variableSources);
    });
  }

  private List<VariableSource> generateVariableSourcesDataBySourceType(
      MandatoryRequest mandatoryRequest,
      String sourceType) {

    String cacheKey = this
        .generateCacheKeyByStoreIdAndSourceType(mandatoryRequest.getStoreId(), sourceType);
    List<VariableSource> variableSources = this.cacheService.findCacheByKey(cacheKey, List.class);
    if (!isExistVariableSources(variableSources)) {
      variableSources = this.variableSourceRepository
          .findBySourceTypeAndStoreIdAndIsDeleted(sourceType, mandatoryRequest.getStoreId(), 0);
      this.cacheService.createCache(cacheKey, variableSources, 300);
    }

    return variableSources;
  }

  private List<VariableSource> generateVariableSourcesDataBySourceTypeAndKey(
      MandatoryRequest mandatoryRequest,
      String sourceType, String key) {

    String cacheKey = this
        .generateCacheKeyByStoreIdAndsourceTypeAndKey(mandatoryRequest.getStoreId(), sourceType,
            key);
    List<VariableSource> variableSources = this.cacheService.findCacheByKey(cacheKey, List.class);
    if (!isExistVariableSources(variableSources)) {
      variableSources = this.variableSourceRepository
          .findBySourceTypeAndValueSearchRegexAndStoreIdAndIsDeleted(sourceType, key,
              mandatoryRequest.getStoreId
                  (), 0);
      this.cacheService.createCache(cacheKey, variableSources, 300);
    }

    return variableSources;
  }

  private boolean isKeyExistAndNotBlank(String key) {
    return key != null;
  }

  private boolean isExistVariableSources(List<VariableSource> variableSources) {
    return variableSources != null;
  }

  private String generateCacheKeyByStoreIdAndSourceType(String storeId, String sourceType) {
    return CacheKey.VARIABLE_SOURCE + "-" + storeId + "-" + sourceType;
  }

  private String generateCacheKeyByStoreIdAndsourceTypeAndKey(String storeId, String sourceType,
      String key) {
    return CacheKey.VARIABLE_SOURCE + "-" + storeId + "-" + sourceType + "-" + key;
  }

  private boolean isExistVariableSource(VariableSource checkVariableSource) {
    return checkVariableSource != null;
  }
}
