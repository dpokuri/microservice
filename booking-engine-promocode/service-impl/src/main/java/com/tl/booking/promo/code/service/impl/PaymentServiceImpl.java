package com.tl.booking.promo.code.service.impl;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.dao.api.PaymentRepository;
import com.tl.booking.promo.code.entity.constant.CacheKey;
import com.tl.booking.promo.code.entity.constant.enums.PaymentColumn;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.dao.Payment;
import com.tl.booking.promo.code.libraries.exception.BusinessLogicException;
import com.tl.booking.promo.code.libraries.utility.DateFormatterHelper;
import com.tl.booking.promo.code.service.api.CacheService;
import com.tl.booking.promo.code.service.api.PaymentService;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@Service
public class PaymentServiceImpl implements PaymentService {

  private static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImpl.class);

  @Autowired
  CacheService cacheService;

  @Autowired
  PaymentRepository paymentRepository;

  @Override
  public Single<Payment> createPayment(MandatoryRequest mandatoryRequest, Payment payment) {
    LOGGER.info("createPayment request {}, {}", mandatoryRequest, payment);
    return Single.<Payment>create(
        singleEmitter -> {
          Payment checkPayment = this.paymentRepository
              .findPaymentByStoreIdAndNameAndIsDeleted(mandatoryRequest.getStoreId(),
                  payment.getName(), 0);

          if (isExistPayment(checkPayment)) {
            throw new BusinessLogicException(ResponseCode.DUPLICATE_DATA.getCode(),
                ResponseCode.DUPLICATE_DATA.getMessage());
          }

          Payment createdPayment = this.paymentRepository.save(payment);

          if (!isExistPayment(createdPayment)) {
            throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                ResponseCode.SYSTEM_ERROR.getMessage());
          }

          this.deleteCachePaymentFindAll(mandatoryRequest.getStoreId());
          String cacheKey = this.generateCacheKey(mandatoryRequest.getStoreId(), payment.getId());
          this.cacheService.createCache(cacheKey, payment, 0);

          LOGGER.info("createPayment response {}", createdPayment);
          singleEmitter.onSuccess(createdPayment);
        }
    ).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<List<Payment>> findPayments(MandatoryRequest mandatoryRequest) {
    LOGGER.info("findPayment Request mandatoryRequest {} ", mandatoryRequest);

    return Single.<List<Payment>>create(singleEmitter -> {

      String cacheKey = this.generateCacheKeyFindAll(mandatoryRequest.getStoreId());
      List<Payment> payments = this.cacheService.findCacheByKey(cacheKey, List.class);

      if (!isExistListPayment(payments)) {

        payments = this.paymentRepository
            .findByIsDeletedAndStoreId(0, mandatoryRequest.getStoreId());

        this.cacheService.createCache(cacheKey, payments, 0);
      }

      LOGGER.info("findPayment Response payment {} ", payments);

      singleEmitter.onSuccess(payments);

    }).subscribeOn(Schedulers.io());
  }

  private boolean isExistListPayment(List<Payment> payments) {
    return payments != null;
  }

  @Override
  public Single<Page<Payment>> findPaymentFilterPaginated(MandatoryRequest mandatoryRequest,
      String name,
      String paymentId, Integer page, Integer size, PaymentColumn columnSort,
      SortDirection sortDirection) {

    LOGGER.info(
        "findPaymentFilterPaginated Request mandatoryRequest, name, page, size, columnSort, sortDirection {} ",
        mandatoryRequest, name, paymentId, page, size, columnSort, sortDirection);

    return Single.<Page<Payment>>create(
        singleEmitter -> {

          String sort = setColumnSort(columnSort);

          Page<Payment> paymentPage = this.paymentRepository
              .findPaymentFilterPaginated(mandatoryRequest.getStoreId(), name, paymentId, page,
                  size, sort, sortDirection);

          LOGGER.info("findPaymentFilterPaginated Response paymentPage {} ", paymentPage);

          singleEmitter.onSuccess(paymentPage);
        }
    ).subscribeOn(Schedulers.io());
  }

  private String setColumnSort(PaymentColumn paymentColumn) {
    String result = null;
    if (isNotNullColumnSort(paymentColumn)) {
      result = paymentColumn.getValue();
    }

    return result;
  }

  private Boolean isNotNullColumnSort(PaymentColumn paymentColumn) {
    Boolean result = false;
    if (paymentColumn != null) {
      result = true;
    }

    return result;
  }

  @Override
  public Single<Payment> updatePayment(MandatoryRequest mandatoryRequest, Payment paymentParam,
      String id) {
    LOGGER.info("updatePayment Request MandatoryRequest mandatoryRequest, paymentParam, id {} ",
        mandatoryRequest, paymentParam, id);

    return Single.<Payment>create(singleEmitter -> {
      Payment payment = this.paymentRepository
          .findPaymentByStoreIdAndIdAndIsDeleted(mandatoryRequest.getStoreId(), id, 0);

      if (!isExistPayment(payment)) {
        throw new BusinessLogicException(ResponseCode.PAYMENT_NOT_EXIST.getCode(),
            ResponseCode.PAYMENT_NOT_EXIST.getMessage());
      }

      this.checkStoreIdAndNameForOtherDocument(payment, paymentParam);

      this.combinePaymentAndPaymentParam(payment, paymentParam);

      Payment updatedPayment = this.paymentRepository.save(payment);

      if (!isExistPayment(updatedPayment)) {
        throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
            ResponseCode.SYSTEM_ERROR.getMessage());
      }

      this.deleteCachePaymentFindAll(mandatoryRequest.getStoreId());
      String cacheKey = this
          .generateCacheKey(mandatoryRequest.getStoreId(), updatedPayment.getId());
      this.cacheService.createCache(cacheKey, updatedPayment, 0);

      LOGGER.info("updatePayment Response Payment {} ", updatedPayment);

      singleEmitter.onSuccess(updatedPayment);

    }).subscribeOn(Schedulers.io());
  }

  private void combinePaymentAndPaymentParam(Payment payment,
      Payment paymentParam) {
    payment.setVersion(payment.getVersion());
    payment.setStoreId(payment.getStoreId());
    payment.setUsername(payment.getUsername());
    payment.setCreatedDate(payment.getCreatedDate());
    payment.setCreatedBy(payment.getCreatedBy());
    payment.setUpdatedBy(payment.getUsername());

    payment.setName(paymentParam.getName());
    payment.setPaymentId(paymentParam.getPaymentId());
    payment.setUseBinNumber(paymentParam.getUseBinNumber());
  }

  private void checkStoreIdAndNameForOtherDocument(Payment payment,
      Payment paymentParam) {

    if (isExistPayment(payment) && !payment.getName()
        .equals(paymentParam.getName())) {

      Boolean checkDuplicateStoreIdAndName = this.checkExistOtherStoreIdAndName(paymentParam);
      if (checkDuplicateStoreIdAndName) {
        throw new BusinessLogicException(ResponseCode.NAME_EXIST_OTHER_RECORD.getCode(),
            ResponseCode.NAME_EXIST_OTHER_RECORD.getMessage());
      }
    }
  }

  private Boolean checkExistOtherStoreIdAndName(Payment paymentParam) {
    Boolean result = false;
    Payment payment = this.paymentRepository
        .findPaymentByStoreIdAndNameAndIsDeleted(paymentParam.getStoreId(),
            paymentParam.getName(), 0);
    if (isExistPayment(payment)) {
      result = true;
    }

    return result;
  }

  @Override
  public Single<Boolean> deletePayment(MandatoryRequest mandatoryRequest, String id) {
    LOGGER.info("deletePayment Request mandatoryRequest, id {} ", mandatoryRequest, id);

    return Single.<Boolean>create(singleEmitter -> {
      Payment payment = this.paymentRepository
          .findPaymentByStoreIdAndIdAndIsDeleted(mandatoryRequest.getStoreId(), id, 0);

      if (!isExistPayment(payment)) {
        throw new BusinessLogicException(ResponseCode.PAYMENT_NOT_EXIST.getCode(),
            ResponseCode.PAYMENT_NOT_EXIST.getMessage());
      }

      payment.setIsDeleted(1);
      payment.setUpdatedBy(mandatoryRequest.getUsername());
      payment.setUpdatedDate(DateFormatterHelper.getTodayDateTime());
      Boolean deleted = this.paymentRepository.softDeleted(payment, id);

      if (!deleted) {
        throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
            ResponseCode.SYSTEM_ERROR.getMessage());
      }

      this.deleteCachePaymentFindAll(mandatoryRequest.getStoreId());
      String cacheKey = this.generateCacheKey(mandatoryRequest.getStoreId(), id);
      this.cacheService.deleteCache(cacheKey);

      LOGGER.info("deletePayment Response Boolean {} ", deleted);

      singleEmitter.onSuccess(true);

    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<Payment> findPaymentById(MandatoryRequest mandatoryRequest, String id) {
    LOGGER.info("findPaymentById Request mandatoryRequest, id {} ", mandatoryRequest, id);

    return Single.<Payment>create(
        singleEmitter -> {
          String cacheKey = this.generateCacheKey(mandatoryRequest.getStoreId(), id);
          Payment payment = this.cacheService.findCacheByKey(cacheKey, Payment.class);

          if (!isExistPayment(payment)) {
            payment = this.paymentRepository
                .findPaymentByStoreIdAndIdAndIsDeleted(mandatoryRequest.getStoreId(), id, 0);

            if (!isExistPayment(payment)) {
              throw new BusinessLogicException(ResponseCode.PAYMENT_NOT_EXIST.getCode(),
                  ResponseCode.PAYMENT_NOT_EXIST.getMessage());
            }

            this.cacheService.createCache(cacheKey, payment, 0);
          }

          LOGGER.info("findPaymentById Response payment {} ", payment);

          singleEmitter.onSuccess(payment);
        }
    ).subscribeOn(Schedulers.io());
  }

  private boolean isExistPayment(Payment checkPayment) {
    Boolean result = false;
    if (checkPayment != null) {
      result = true;
    }

    return result;
  }

  private String generateCacheKey(String storeId, String id) {
    return CacheKey.PAYMENT + "-" + storeId + "-" + id;
  }

  private String generateCacheKeyFindAll(String storeId) {
    return CacheKey.PAYMENT + "-" + storeId;
  }

  private void deleteCachePaymentFindAll(String storeId) {
    String cacheKeyFindAll = this.generateCacheKeyFindAll(storeId);
    this.cacheService.deleteCache(cacheKeyFindAll);
  }

}
