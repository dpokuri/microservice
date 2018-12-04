package com.tl.booking.promo.code.service.impl;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.dao.api.ProductTypeRepository;
import com.tl.booking.promo.code.entity.constant.CacheKey;
import com.tl.booking.promo.code.entity.constant.enums.ProductTypeColumn;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.dao.ProductType;
import com.tl.booking.promo.code.libraries.exception.BusinessLogicException;
import com.tl.booking.promo.code.libraries.utility.DateFormatterHelper;
import com.tl.booking.promo.code.service.api.CacheService;
import com.tl.booking.promo.code.service.api.ProductTypeService;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ProductTypeServiceImpl implements ProductTypeService {

  private static final Logger LOGGER = LoggerFactory.getLogger(VariableServiceImpl.class);

  @Autowired
  CacheService cacheService;

  @Autowired
  ProductTypeRepository productTypeRepository;

  @Override
  public Single<ProductType> createProductType(MandatoryRequest mandatoryRequest,
      ProductType productType) {

    LOGGER.info("createProductType Request mandatoryRequest, productType {} ", mandatoryRequest,
        productType);

    return Single.<ProductType>create(singleEmitter -> {
      ProductType checkProductType = this.productTypeRepository
          .findProductTypeByStoreIdAndNameAndIsDeleted(mandatoryRequest.getStoreId(),
              productType.getName(), 0);

      if (isExistProductType(checkProductType)) {
        throw new BusinessLogicException(ResponseCode.DUPLICATE_DATA.getCode(),
            ResponseCode.DUPLICATE_DATA.getMessage());
      }

      ProductType createdproductType = this.productTypeRepository.save(productType);

      if (!isExistProductType(createdproductType)) {
        throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
            ResponseCode.SYSTEM_ERROR.getMessage());
      }

      String cacheKey = this.generateCacheKey(mandatoryRequest.getStoreId(), productType.getId());
      this.cacheService.createCache(cacheKey, productType, 0);

      this.deleteCacheFind(mandatoryRequest.getStoreId());

      LOGGER.info("createProductType Response ProductType {} ", createdproductType);

      singleEmitter.onSuccess(createdproductType);

    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<ProductType> updateProductType(MandatoryRequest mandatoryRequest,
      ProductType productTypeParam, String id) {

    LOGGER.info(
        "updateProductType Request MandatoryRequest mandatoryRequest, productTypeParam, id {} ",
        mandatoryRequest, productTypeParam, id);

    return Single.<ProductType>create(singleEmitter -> {
      ProductType productType = this.productTypeRepository
          .findProductTypeByStoreIdAndIdAndIsDeleted(mandatoryRequest.getStoreId(), id, 0);

      if (!isExistProductType(productType)) {
        throw new BusinessLogicException(ResponseCode.PRODUCT_TYPE_NOT_EXIST.getCode(),
            ResponseCode.PRODUCT_TYPE_NOT_EXIST.getMessage());
      }

      this.checkStoreIdAndNameForOtherDocument(productType, productTypeParam);

      this.combineProductTypeAndProductTypeParam(productType, productTypeParam);

      ProductType updatedProductType = this.productTypeRepository.save(productType);

      if (!isExistProductType(updatedProductType)) {
        throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
            ResponseCode.SYSTEM_ERROR.getMessage());
      }

      String cacheKey = this
          .generateCacheKey(mandatoryRequest.getStoreId(), updatedProductType.getId());
      this.cacheService.deleteCache(cacheKey);
      this.cacheService.createCache(cacheKey, updatedProductType, 0);

      this.deleteCacheFind(mandatoryRequest.getStoreId());

      LOGGER.info("updateProductType Response ProductType {} ", updatedProductType);

      singleEmitter.onSuccess(updatedProductType);

    }).subscribeOn(Schedulers.io());

  }

  @Override
  public Single<Boolean> deleteProductType(MandatoryRequest mandatoryRequest, String id) {

    LOGGER.info("deleteProductType Request mandatoryRequest, id {} ", mandatoryRequest, id);

    return Single.<Boolean>create(singleEmitter -> {
      ProductType productType = this.productTypeRepository
          .findProductTypeByStoreIdAndIdAndIsDeleted(mandatoryRequest.getStoreId(), id, 0);

      if (!isExistProductType(productType)) {
        throw new BusinessLogicException(ResponseCode.PRODUCT_TYPE_NOT_EXIST.getCode(),
            ResponseCode.PRODUCT_TYPE_NOT_EXIST.getMessage());
      }

      productType.setIsDeleted(1);
      productType.setUpdatedBy(mandatoryRequest.getUsername());
      productType.setUpdatedDate(DateFormatterHelper.getTodayDateTime());
      Boolean deleted = this.productTypeRepository.softDeleted(productType, id);

      if (!deleted) {
        throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
            ResponseCode.SYSTEM_ERROR.getMessage());
      }

      String cacheKey = this.generateCacheKey(mandatoryRequest.getStoreId(), id);
      this.cacheService.deleteCache(cacheKey);

      this.deleteCacheFind(mandatoryRequest.getStoreId());

      LOGGER.info("deleteProductType Response Boolean {} ", deleted);

      singleEmitter.onSuccess(true);

    }).subscribeOn(Schedulers.io());
  }


  @Override
  public Single<ProductType> findProductTypeById(MandatoryRequest mandatoryRequest, String id) {

    LOGGER.info("findProductTypeById Request mandatoryRequest, id {} ", mandatoryRequest, id);

    return Single.<ProductType>create(
        singleEmitter -> {
          String cacheKey = this.generateCacheKey(mandatoryRequest.getStoreId(), id);
          ProductType productType = this.cacheService.findCacheByKey(cacheKey, ProductType.class);

          if (!isExistProductType(productType)) {
            productType = this.productTypeRepository
                .findProductTypeByStoreIdAndIdAndIsDeleted(mandatoryRequest.getStoreId(), id, 0);

            if (!isExistProductType(productType)) {
              throw new BusinessLogicException(ResponseCode.PRODUCT_TYPE_NOT_EXIST.getCode(),
                  ResponseCode.PRODUCT_TYPE_NOT_EXIST.getMessage());
            }

            this.cacheService.createCache(cacheKey, productType, 0);
          }

          LOGGER.info("findProductTypeById Response productType {} ", productType);

          singleEmitter.onSuccess(productType);
        }
    ).subscribeOn(Schedulers.io());
  }


  @Override
  public Single<Page<ProductType>> findProductTypeFilterPaginated(MandatoryRequest mandatoryRequest,
      String name, Integer page, Integer size, ProductTypeColumn columnSort,
      SortDirection sortDirection) {

    LOGGER.info(
        "findProductTypeFilterPaginated Request mandatoryRequest, name, page, size, columnSort, sortDirection {} ",
        mandatoryRequest, name, page, size, columnSort, sortDirection);

    return Single.<Page<ProductType>>create(
        singleEmitter -> {

          String sort = setColumnSort(columnSort);

          Page<ProductType> productTypePage = this.productTypeRepository
              .findProductTypeFilterPaginated(mandatoryRequest.getStoreId(), name, page,
                  size, sort, sortDirection);

          LOGGER
              .info("findProductTypeFilterPaginated Response productTypePage {} ", productTypePage);

          singleEmitter.onSuccess(productTypePage);
        }
    ).subscribeOn(Schedulers.io());
  }


  @Override
  public Single<List<ProductType>> findProductTypes(MandatoryRequest mandatoryRequest) {
    LOGGER.info("findProductTypes Request mandatoryRequest {} ", mandatoryRequest);

    return Single.<List<ProductType>>create(singleEmitter -> {

      String cacheKey = this.generateCacheKeyFind(mandatoryRequest.getStoreId());
      List<ProductType> channelIds = this.cacheService.findCacheByKey(cacheKey, List.class);

      if (!isExistProductTypeList(channelIds)) {
        channelIds = this.productTypeRepository
            .findProductTypeByStoreIdAndIsDeleted(mandatoryRequest.getStoreId(), 0);

        if (channelIds.isEmpty()) {
          throw new BusinessLogicException(ResponseCode.PRODUCT_TYPE_LIST_NOT_EXIST.getCode(),
              ResponseCode.PRODUCT_TYPE_LIST_NOT_EXIST.getMessage());
        }

        this.cacheService.createCache(cacheKey, channelIds, 0);
      }

      singleEmitter.onSuccess(channelIds);

      LOGGER.info("findProductTypes Response channelIds {} ", channelIds);

    }).subscribeOn(Schedulers.io());
  }

  private boolean isExistProductTypeList(List<ProductType> channelIds) {
    return channelIds != null;
  }


  private String setColumnSort(ProductTypeColumn productTypeColumn) {
    String result = null;
    if (isNotNullColumnSort(productTypeColumn)) {
      result = productTypeColumn.getValue();
    }

    return result;
  }

  private Boolean isNotNullColumnSort(ProductTypeColumn productTypeColumn) {
    Boolean result = false;
    if (productTypeColumn != null) {
      result = true;
    }

    return result;
  }


  private void checkStoreIdAndNameForOtherDocument(ProductType productType,
      ProductType productTypeParam) {

    if (isExistProductType(productType) && !productType.getName()
        .equals(productTypeParam.getName())) {

      Boolean checkDuplicateStoreIdAndname = this.checkExistOtherStoreIdAndName(productTypeParam);
      if (checkDuplicateStoreIdAndname) {
        throw new BusinessLogicException(ResponseCode.NAME_EXIST_OTHER_RECORD.getCode(),
            ResponseCode.NAME_EXIST_OTHER_RECORD.getMessage());
      }
    }
  }


  private void combineProductTypeAndProductTypeParam(ProductType productType,
      ProductType productTypeParam) {
    productType.setVersion(productType.getVersion());
    productType.setStoreId(productType.getStoreId());
    productType.setUsername(productType.getUsername());
    productType.setCreatedDate(productType.getCreatedDate());
    productType.setCreatedBy(productType.getCreatedBy());
    productType.setUpdatedBy(productType.getUsername());

    productType.setName(productTypeParam.getName());
    productType.setDescription(productTypeParam.getDescription());
  }

  private Boolean checkExistOtherStoreIdAndName(ProductType productTypeParam) {
    Boolean result = false;
    ProductType productType = this.productTypeRepository
        .findProductTypeByStoreIdAndNameAndIsDeleted(productTypeParam.getStoreId(),
            productTypeParam.getName(), 0);
    if (isExistProductType(productType)) {
      result = true;
    }

    return result;
  }


  private Boolean isExistProductType(ProductType checkProductType) {
    Boolean result = false;
    if (checkProductType != null) {
      result = true;
    }

    return result;
  }

  private String generateCacheKey(String storeId, String id) {
    return CacheKey.PRODUCT_TYPE + "-" + storeId + "-" + id;
  }

  private void deleteCacheFind(String storeId) {
    String cacheKey = this.generateCacheKeyFind(storeId);
    this.cacheService.deleteCache(cacheKey);
  }


  private String generateCacheKeyFind(String storeId) {
    return CacheKey.PRODUCT_TYPE + "-" + storeId;
  }

}
