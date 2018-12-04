package com.tl.booking.promo.code.service.impl;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.entity.CalculationResult;
import com.tl.booking.promo.code.entity.CampaignPeriod;
import com.tl.booking.promo.code.entity.Cost;
import com.tl.booking.promo.code.entity.CostAmount;
import com.tl.booking.promo.code.entity.CostAmountBuilder;
import com.tl.booking.promo.code.entity.OrderDetail;
import com.tl.booking.promo.code.entity.PaymentDTO;
import com.tl.booking.promo.code.entity.PaymentMethod;
import com.tl.booking.promo.code.entity.PromoCodeDiscount;
import com.tl.booking.promo.code.entity.PromoCodeDistribution;
import com.tl.booking.promo.code.entity.PromoCodeGroupRule;
import com.tl.booking.promo.code.entity.PromoCodeObject;
import com.tl.booking.promo.code.entity.PromoCodePriceRange;
import com.tl.booking.promo.code.entity.PromoCodeRule;
import com.tl.booking.promo.code.entity.PromoCodeRuleValidation;
import com.tl.booking.promo.code.entity.PromoCodeRuleValidationBuilder;
import com.tl.booking.promo.code.entity.UsageRule;
import com.tl.booking.promo.code.entity.constant.enums.CalculateType;
import com.tl.booking.promo.code.entity.constant.enums.DataType;
import com.tl.booking.promo.code.entity.constant.enums.IgnoreValidation;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeStatus;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.UsagePeriod;
import com.tl.booking.promo.code.entity.constant.enums.ValidatedBy;
import com.tl.booking.promo.code.entity.dao.PromoCodeAdjustment;
import com.tl.booking.promo.code.entity.dao.PromoCodeUsage;
import com.tl.booking.promo.code.libraries.configuration.TimeZoneProperties;
import com.tl.booking.promo.code.libraries.exception.BusinessLogicException;
import com.tl.booking.promo.code.libraries.utility.DateFormatterHelper;
import com.tl.booking.promo.code.service.api.CalculatePromoCodeService;
import com.tl.booking.promo.code.service.api.PromoCodeObjectService;
import com.tl.booking.promo.code.service.api.PromoCodeUsageService;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;


@Service
public class CalculatePromoCodeServiceImpl implements CalculatePromoCodeService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CalculatePromoCodeServiceImpl.class);
  private static final String NOT_IN = "NOT_IN";
  private static final String IN = "IN";
  @Autowired
  private PromoCodeObjectService promoCodeObjectService;
  @Autowired
  private PromoCodeUsageService promoCodeUsageService;
  @Autowired
  private TimeZoneProperties timeZoneProperties;
  @Autowired
  private Scheduler schedulerPromoCode;

  @Override
  public Single<CalculationResult>
  calculatePromoCode
      (MandatoryRequest mandatoryRequest,
          String code,
          List<OrderDetail> orderDetails,
          Set<String> usedPromoCodes,
          Double totalPrice,
          PaymentDTO payment,
          String referenceId,
          IgnoreValidation ignoreValidation) {

    LOGGER.info(
        "calculatePromoCode Request mandatoryRequest, code, orderDetails, usedPromoCodes, totalPrice,"
            + "payment, ignoreValidation", mandatoryRequest, code, orderDetails, usedPromoCodes,
        totalPrice, payment, ignoreValidation);

    return this.promoCodeObjectService
        .findPromoCodeObjectByStoreIdAndCode(mandatoryRequest, code)
        .map(
            promoCodeObject -> {
              this.checkAllowedPaymentMethod(promoCodeObject.getPromoCodeAdjustment
                  (), payment, ignoreValidation);
              this.checkPromoCodeStatus(
                  promoCodeObject.getPromoCode().getPromoCodeStatus().getCode());
              this.checkPromoCodeDistribution(mandatoryRequest,
                  promoCodeObject.getPromoCodeAdjustment().getPromoCodeDistributions());
              this.checkPromoCodePeriod(promoCodeObject.getCampaign().getCampaignPeriods());
              this.checkPromoCodeCombine(
                  promoCodeObject.getPromoCodeAdjustment().isPromoCodeCombine(),
                  usedPromoCodes);

              return promoCodeObject;
            })
        .flatMap(
            promoCodeObject -> this
                  .isCombineAndIsValidUsageRulesAndIsValidMaxQty(mandatoryRequest, promoCodeObject,
                      usedPromoCodes, payment
                          .getCardNumber(), ignoreValidation))
        .flatMap(calculationResult -> {
          List<CostAmount> partnerCosts = new ArrayList<>();
          List<CostAmount> companyCosts = new ArrayList<>();

          for (OrderDetail orderDetail : orderDetails) {
            partnerCosts.addAll(orderDetail.getPartnerCosts());
            companyCosts.addAll(orderDetail.getCompanyCosts());
          }
          calculationResult.setTotalPrice(totalPrice);
          calculationResult.setOrderDetails(orderDetails);
          calculationResult.setUsedPromoCodes(usedPromoCodes);
          calculationResult.setReferenceId(referenceId);
          calculationResult.setCompanyCostAmount(companyCosts);
          calculationResult.setPartnerCostAmount(partnerCosts);
          return this.checkOrderDetailsWithPromoCodeGroupRules(calculationResult);
        })
        .flatMap(this::calculateDiscountAndCost)
        .subscribeOn(schedulerPromoCode);
  }

  private void checkAllowedPaymentMethod(PromoCodeAdjustment promoCodeAdjustment,
      PaymentDTO payment, IgnoreValidation ignoreValidation) {

    if (isIgnorePaymentMethodValidation(ignoreValidation)) {
      return;
    }

    if(promoCodeAdjustment.getPaymentMethods().isEmpty()){
      return;
    }

    for (PaymentMethod paymentMethod : promoCodeAdjustment.getPaymentMethods()) {
      if (paymentMethod.getPaymentId().equals(payment.getPaymentId())) {
        this.validateBinNumber(paymentMethod, payment);
        return;
      }
    }
    throw new BusinessLogicException(ResponseCode.INVALID_PAYMENT_METHOD.getCode(), ResponseCode
        .INVALID_PAYMENT_METHOD.getMessage());
  }

  private void validateBinNumber(PaymentMethod paymentMethod, PaymentDTO payment) {
    if (isExistBinNumbers(paymentMethod.getBinNumbers())
        && !paymentMethod.getBinNumbers().isEmpty()) {

      if (!isExistBinNumber(payment.getBinNumber())) {
        throw new BusinessLogicException(ResponseCode.INVALID_BIN_NUMBER.getCode(), ResponseCode
            .INVALID_BIN_NUMBER.getMessage());
      }

      Boolean statusBinNumber = false;
      for (String binNumber : paymentMethod.getBinNumbers()) {
        if (binNumber.equals(payment.getBinNumber())) {
          statusBinNumber = true;
          break;
        }
      }

      if (!statusBinNumber) {
        throw new BusinessLogicException(ResponseCode.INVALID_BIN_NUMBER.getCode(), ResponseCode
            .INVALID_BIN_NUMBER.getMessage());
      }
    }
  }

  private boolean isExistBinNumber(String binNumber) {
    return binNumber != null;
  }

  private boolean isExistBinNumbers(Set<String> binNumbers) {
    return binNumbers != null;
  }

  private Single<CalculationResult> isCombineAndIsValidUsageRulesAndIsValidMaxQty(MandatoryRequest
      mandatoryRequest,
      PromoCodeObject promoCodeObject, Set<String> usedPromoCodes, String cardNumber,
      IgnoreValidation ignoreValidation) {
    return Single.zip(
        this.isCombine(mandatoryRequest, usedPromoCodes),
        this.isMaxQtyValid(mandatoryRequest, promoCodeObject, ignoreValidation),
        this.isValidUsageRule(mandatoryRequest, promoCodeObject, cardNumber, ignoreValidation),
        (isCombine, isMaxQtyValid, isValidUsageRule) -> {
          if (!isCombine) {
            throw new BusinessLogicException(ResponseCode.USED_PROMO_CODE_CANNOT_COMBINE.getCode(),
                ResponseCode.USED_PROMO_CODE_CANNOT_COMBINE.getMessage());
          }

          if (!isMaxQtyValid) {
            throw new BusinessLogicException(ResponseCode.PROMO_CODE_NOT_AVAILABLE.getCode(),
                ResponseCode.PROMO_CODE_NOT_AVAILABLE.getMessage());
          }

          if (!isValidUsageRule) {
            throw new BusinessLogicException(ResponseCode.NOT_VALID_USAGE_RULE.getCode(),
                ResponseCode.NOT_VALID_USAGE_RULE.getMessage());
          }

          CalculationResult calculationResult = new CalculationResult();
          calculationResult.setPromoCodeObject(promoCodeObject);
          return calculationResult;
        }).subscribeOn(schedulerPromoCode);
  }

  private Single<Boolean> isMaxQtyValid(MandatoryRequest mandatoryRequest,
      PromoCodeObject promoCodeObject, IgnoreValidation ignoreValidation) {
    return this.promoCodeUsageService
        .findPromoCodeUsageGeneralByPromoCodeObjectAndUsername
            (mandatoryRequest, promoCodeObject).map(
            promoCodeUsage -> {

              if (this.isIgnoreQuantityValidation(ignoreValidation)) {
                return true;
              }

              if (isExistUsageCountInPromoCodeUsage(promoCodeUsage) && !isUsageCountLessThanMaxQty
                  (promoCodeUsage, promoCodeObject)) {
                return false;
              }
              return true;
            }).subscribeOn(schedulerPromoCode);
  }

  private Single<CalculationResult>
  calculateDiscountAndCost
      (CalculationResult calculationResult) {
    return Single.<CalculationResult>create
        (singleEmitter
            -> {
          Integer numOrderDetailWithZeroDiscount = 0;

          for (OrderDetail orderDetail : calculationResult.getOrderDetails()) {
            if (!orderDetail.getPromoCodeRuleValid()) {
              orderDetail.setDiscount(0.0);
              numOrderDetailWithZeroDiscount++;
              continue;
            }

            PromoCodeGroupRule promoCodeGroupRule = orderDetail.getPromoCodeGroupRule();
            Integer orderDetailQty = this
                .calculateOrderDetailQty(promoCodeGroupRule, orderDetail);
            this.calculateProcess(calculationResult, promoCodeGroupRule, orderDetail,
                orderDetailQty);
          }

          if (numOrderDetailWithZeroDiscount == calculationResult.getOrderDetails().size()) {
            throw new BusinessLogicException(
                ResponseCode.PROMO_CODE_NOT_VALID_NO_DISCOUNT.getCode(),
                ResponseCode.PROMO_CODE_NOT_VALID_NO_DISCOUNT.getMessage());
          }

          Double totalDiscount = calculationResult.getOrderDetails().get(0).getDiscount();
          if (calculationResult.getPromoCodeObject().getPromoCodeAdjustment().getCalculateType()
              .equals(CalculateType.ORDER_DETAIL)) {
            for (OrderDetail orderDetail : calculationResult.getOrderDetails()) {
              totalDiscount = totalDiscount + orderDetail.getDiscount();
            }
          }

          Double maxDiscount = calculationResult.getPromoCodeObject().getPromoCodeAdjustment()
              .getMaxDiscount();
          if(totalDiscount > maxDiscount){
            totalDiscount = maxDiscount;
          }

          if(totalDiscount <= 0){
            throw new BusinessLogicException(
                ResponseCode.PROMO_CODE_NOT_VALID_NO_DISCOUNT.getCode(),
                ResponseCode.PROMO_CODE_NOT_VALID_NO_DISCOUNT.getMessage());
          }

          Double finalPayment = calculationResult.getTotalPrice() - totalDiscount;

          if(finalPayment < 10000){
            throw new BusinessLogicException(ResponseCode.MINIMUM_PAYMENT_NOT_VALID.getCode(),
                ResponseCode.MINIMUM_PAYMENT_NOT_VALID.getMessage());
          }

          calculationResult.setTotalDiscount(totalDiscount);
          singleEmitter.onSuccess(calculationResult);
        }).subscribeOn(Schedulers.computation());
  }

  private void calculateProcess(
      CalculationResult calculationResult,
      PromoCodeGroupRule promoCodeGroupRule,
      OrderDetail orderDetail,
      Integer orderDetailQty
  ) {

    Double priceToCompare = 0.00;
    List<PromoCodePriceRange> promoCodePriceRange = new ArrayList<>();
    List<Cost> companyCosts = new ArrayList<>();
    List<Cost> partnerCosts = new ArrayList<>();

    if (calculationResult.getPromoCodeObject().getPromoCodeAdjustment().getCalculateType()
        .equals(CalculateType.ORDER)) {
      priceToCompare = calculationResult.getTotalPrice();
      companyCosts = calculationResult.getPromoCodeObject().getPromoCodeAdjustment()
          .getPromoCodeCost().getCompanyCost();
      partnerCosts = calculationResult.getPromoCodeObject().getPromoCodeAdjustment()
          .getPromoCodeCost().getPartnerCost();
      promoCodePriceRange = calculationResult.getPromoCodeObject().getPromoCodeAdjustment()
          .getPromoCodePriceRanges();

    } else if (calculationResult.getPromoCodeObject().getPromoCodeAdjustment().getCalculateType()
        .equals(CalculateType.ORDER_DETAIL)) {
      priceToCompare = orderDetail.getAmount();
      companyCosts = promoCodeGroupRule.getPromoCodeCost().getCompanyCost();
      partnerCosts = promoCodeGroupRule.getPromoCodeCost().getPartnerCost();
      promoCodePriceRange = promoCodeGroupRule.getPromoCodePriceRanges();
    }

    orderDetail.setDiscount(this.calculateDiscount(
        promoCodePriceRange,
        priceToCompare, orderDetailQty));
    orderDetail.setCompanyCosts(
        this.calculateCost(orderDetail.getDiscount(),
            companyCosts));
    orderDetail.setPartnerCosts(
        this.calculateCost(orderDetail.getDiscount(),
            partnerCosts));
  }

  private Single<Boolean> isCombine(MandatoryRequest mandatoryRequest, Set<String> usedPromoCodes) {
    return Single.create(singleEmitter -> {
      Boolean usedPromoCodesNotExist = true;
      if (!CollectionUtils.isEmpty(usedPromoCodes)) {
        usedPromoCodesNotExist = false;
      }
      singleEmitter.onSuccess(usedPromoCodesNotExist);
    }).flatMap(valid -> {
      if ((Boolean) valid) {
        return Single.just(true);
      }

      List<Single<PromoCodeObject>> usedPromoCombines = new ArrayList<>();
      for (String usedPromoCode : usedPromoCodes) {
        usedPromoCombines
            .add(this.promoCodeObjectService
                .findPromoCodeObjectByStoreIdAndCode(mandatoryRequest, usedPromoCode));
      }

      return Single.zip(usedPromoCombines, this::<PromoCodeObject>mergeList)
          .map(usedPromoCodeObjectsDetails -> {
            Boolean usedPromoCombine = true;
            for (PromoCodeObject usedPromoCodeObject : usedPromoCodeObjectsDetails) {
              if (!usedPromoCodeObject.getPromoCodeAdjustment().isPromoCodeCombine()) {
                usedPromoCombine = false;
                break;
              }
              usedPromoCombine = true;
            }

            return usedPromoCombine;
          }).subscribeOn(schedulerPromoCode);
    });
  }

  private Single<Boolean> isValidUsageRule(MandatoryRequest mandatoryRequest,
      PromoCodeObject promoCodeObject, String cardNumber, IgnoreValidation ignoreValidation) {

    Date todayDate = DateFormatterHelper.getTodayDate();
    List<Single<Boolean>> usageRulesSingle = new ArrayList<>();

    if(promoCodeObject.getPromoCodeAdjustment().getUsageRules().isEmpty()){
      return Single.create(singleEmitter -> singleEmitter.onSuccess(true));
    }

    for (UsageRule usageRule : promoCodeObject.getPromoCodeAdjustment().getUsageRules()) {
      usageRulesSingle.add(this.isUsageRuleValid(mandatoryRequest, todayDate, usageRule,
          promoCodeObject, cardNumber));
    }

    return Single.zip(usageRulesSingle, this::<Boolean>mergeList)
        .map(returnedValidations -> {

          if (this.isIgnoreQuantityValidation(ignoreValidation)) {
            return true;
          }

          Boolean valid = true;
          for (Boolean returnedValidation : returnedValidations) {
            valid = returnedValidation;
            if (!valid) {
              break;
            }
          }

          return valid;
        }).subscribeOn(schedulerPromoCode);
  }

  private Boolean isIgnoreQuantityValidation(IgnoreValidation ignoreValidation) {
    return ignoreValidation.equals(IgnoreValidation.QUANTITY) || ignoreValidation.equals
        (IgnoreValidation.PAYMENT_METHOD_QUANTITY);
  }

  private Boolean isIgnorePaymentMethodValidation(IgnoreValidation ignoreValidation) {
    return ignoreValidation.equals(IgnoreValidation.PAYMENT_METHOD_QUANTITY) || ignoreValidation
        .equals(IgnoreValidation.PAYMENT_METHOD);
  }

  private Single<Boolean> isUsageRuleValid(MandatoryRequest mandatoryRequest, Date todayDate,
      UsageRule
          usageRule, PromoCodeObject promoCodeObject, String cardNumber) {

    if (usageRule.getValidatedBy().equals(ValidatedBy.CARD_NUMBER)) {
      if (usageRule.getUsagePeriod().equals(UsagePeriod.DAILY)) {
        return isUsageRuleValidByStartDateAndEndDateAndUsageRuleDailyByCardNumber(
            mandatoryRequest, todayDate,
            usageRule, promoCodeObject, cardNumber);
      } else {
        return isUsageRuleValidByStartDateAndEndDateAndUsageRulePeriodByCardNumber(
            mandatoryRequest,
            usageRule, promoCodeObject, cardNumber);
      }

    } else {
      if (usageRule.getUsagePeriod().equals(UsagePeriod.DAILY)) {
        return isUsageRuleValidByStartDateAndEndDateAndUsageRuleDaily(
            mandatoryRequest, todayDate,
            usageRule, promoCodeObject);
      } else {
        return isUsageRuleValidByStartDateAndEndDateAndUsageRulePeriod(
            mandatoryRequest,
            usageRule, promoCodeObject);
      }
    }
  }


  private Single<Boolean> isUsageRuleValidByStartDateAndEndDateAndUsageRuleDaily(MandatoryRequest
      mandatoryRequest,
      Date todayDate,
      UsageRule usageRule, PromoCodeObject promoCodeObject) {

    return this.promoCodeUsageService
        .findPromoCodeUsageByPromoCodeObjectAndUsernameAndTodayDate
            (mandatoryRequest, todayDate, promoCodeObject).map(promoCodeUsage ->
            isReturnedPromoCodeUsageCountValid(promoCodeUsage, usageRule));

  }

  private Single<Boolean> isUsageRuleValidByStartDateAndEndDateAndUsageRulePeriod(MandatoryRequest
      mandatoryRequest,
      UsageRule usageRule, PromoCodeObject promoCodeObject) {
    return this.promoCodeUsageService
        .findPromoCodeUsageByPromoCodeObjectAndUsername
            (mandatoryRequest, promoCodeObject).map(promoCodeUsage ->
            isReturnedPromoCodeUsageCountValid(promoCodeUsage, usageRule));

  }

  private Single<Boolean> isUsageRuleValidByStartDateAndEndDateAndUsageRuleDailyByCardNumber(
      MandatoryRequest
          mandatoryRequest,
      Date todayDate,
      UsageRule usageRule, PromoCodeObject promoCodeObject, String cardNumber) {

    return this.promoCodeUsageService
        .findPromoCodeUsageByPromoCodeObjectAndCardNameAndTodayDate
            (mandatoryRequest, todayDate, promoCodeObject, cardNumber).map(promoCodeUsage ->
            isReturnedPromoCodeUsageCountValid(promoCodeUsage, usageRule));

  }

  private Single<Boolean> isUsageRuleValidByStartDateAndEndDateAndUsageRulePeriodByCardNumber
      (MandatoryRequest
          mandatoryRequest,
          UsageRule usageRule, PromoCodeObject promoCodeObject, String cardNumber) {
    return this.promoCodeUsageService
        .findPromoCodeUsageByPromoCodeObjectAndCardNumber
            (mandatoryRequest, promoCodeObject, cardNumber).map(promoCodeUsage ->
            isReturnedPromoCodeUsageCountValid(promoCodeUsage, usageRule));

  }

  private Boolean isReturnedPromoCodeUsageCountValid(PromoCodeUsage promoCodeUsage,
      UsageRule usageRule) {
    Boolean valid = false;
    if (isExistUsageCountInPromoCodeUsage(promoCodeUsage)) {
      if (promoCodeUsage.getUsageCount() < usageRule
          .getUsageCount()) {
        valid = true;
      }
    } else {
      valid = true;
    }

    return valid;
  }

  private Double calculateDiscount(
      List<PromoCodePriceRange> promoCodePriceRanges, Double orderDetailAmount, Integer
      orderDetailQty) {
    Double totalDiscount = 0.0;
    for (PromoCodePriceRange promoCodePriceRange : promoCodePriceRanges) {
      if (promoCodePriceRange.getMinPrice() <= orderDetailAmount &&
          promoCodePriceRange.getMaxPrice() >= orderDetailAmount) {
        PromoCodeDiscount promoCodeDiscount = promoCodePriceRange.getPromoCodeDiscount();
        totalDiscount =
            (orderDetailAmount * promoCodeDiscount.getPercent() / 100) + promoCodeDiscount
                .getValue();
        totalDiscount = totalDiscount * orderDetailQty;

        if (totalDiscount > promoCodePriceRange.getPromoCodeDiscount().getMaxValue()) {
          totalDiscount = promoCodePriceRange.getPromoCodeDiscount().getMaxValue();
        }

        if(totalDiscount > orderDetailAmount){
          totalDiscount = orderDetailAmount;
        }
      }
    }

    return totalDiscount;
  }

  private Integer calculateOrderDetailQty(PromoCodeGroupRule promoCodeGroupRule,
      OrderDetail orderDetail) {
    Integer orderDetailQty = 1;
    for (PromoCodeRule promoCodeRule : promoCodeGroupRule.getPromoCodeRules()) {
      if (promoCodeRule.getUsedForCalculate()) {
        orderDetailQty = orderDetailQty * Integer.parseInt(orderDetail.getOrderAttribute().get
            (promoCodeRule.getParam()));
      }
    }

    return orderDetailQty;
  }

  private Single<CalculationResult> checkOrderDetailsWithPromoCodeGroupRules(
      CalculationResult calculationResult) {
    List<Single<OrderDetail>> sOrderDetails = new ArrayList<>();

    for (OrderDetail orderDetail : calculationResult.getOrderDetails()) {
      sOrderDetails
          .add(this.findPromoCodeGroupRulesByOrderDetail(orderDetail,
              calculationResult.getPromoCodeObject()));
    }

    return Single.zip(sOrderDetails, this::<OrderDetail>mergeList).map(returnedOrderDetails -> {

      calculationResult.setOrderDetails(returnedOrderDetails);
      calculationResult.setTotalDiscount(0.00);

      for(OrderDetail orderDetail : returnedOrderDetails){
        if(calculationResult.getPromoCodeObject().getPromoCodeAdjustment().getValidAllOrderDetails() &&
            !orderDetail.getPromoCodeRuleValid()){
          throw new BusinessLogicException(ResponseCode.INVALID_PROMO_CODE_RULE.getCode(),
              ResponseCode.INVALID_PROMO_CODE_RULE.getMessage());
        }
      }
      return calculationResult;
    })
        .subscribeOn(Schedulers.computation());
  }

  private Single<OrderDetail> findPromoCodeGroupRulesByOrderDetail(
      OrderDetail orderDetail, PromoCodeObject promoCodeObject) {

    List<PromoCodeGroupRule> promoCodeGroupRules = promoCodeObject.getPromoCodeAdjustment()
        .getPromoCodeGroupRules();

    return Single.create(singleEmitter -> {
      orderDetail.setPromoCodeRuleValid(false);
      for (PromoCodeGroupRule promoCodeGroupRule : promoCodeGroupRules) {
        PromoCodeRuleValidation promoCodeRuleValidation = isValidPromoCodeRules(promoCodeGroupRule,
            orderDetail.getOrderAttribute(),
            promoCodeObject);
        if (promoCodeRuleValidation.getValid()) {
          orderDetail.setPromoCodeRuleValid(true);
          orderDetail.setPromoCodeGroupRule(promoCodeGroupRule);
          break;
        }
        orderDetail.setPromoCodeRuleValid(false);
      }

      singleEmitter.onSuccess(orderDetail);
    });
  }

  private <T> List<T> mergeList(Object... objects) {
    List<T> list = new ArrayList<>();

    for (Object object : objects) {
      list.add((T) object);
    }

    return list;
  }

  private void checkPromoCodeStatus(String promoCodeStatus) {
    if (!promoCodeStatus.equals(PromoCodeStatus.ACTIVE.getCode())) {
      throw new BusinessLogicException(ResponseCode.PROMO_CODE_NOT_ACTIVE.getCode(),
          ResponseCode.PROMO_CODE_NOT_ACTIVE.getMessage());
    }
  }

  private void checkPromoCodePeriod(List<CampaignPeriod> campaignPeriods) {
    Boolean isValidPeriod = false;
    for (CampaignPeriod campaignPeriod : campaignPeriods) {
      DateTime startDate = this.changeToDateTime(campaignPeriod.getStartDate());
      DateTime endDate = this.changeToDateTime(campaignPeriod.getEndDate());

      if (startDate.isBeforeNow() && endDate.isAfterNow()) {
        isValidPeriod = true;
        break;
      }
    }

    if (!isValidPeriod) {
      throw new BusinessLogicException(ResponseCode.PERIOD_NOT_VALID.getCode(),
          ResponseCode.PERIOD_NOT_VALID.getMessage());
    }
  }

  private DateTime changeToDateTime(Date date) {
    return new DateTime(date).minusHours(timeZoneProperties.getOffsetToDate());
  }

  private void checkPromoCodeCombine(Boolean isPromoCodeCombine, Set<String> usedPromoCodes) {
    if (!isPromoCodeCombine && !CollectionUtils.isEmpty(usedPromoCodes)) {
      throw new BusinessLogicException(ResponseCode.CANNOT_COMBINE.getCode(),
          ResponseCode.CANNOT_COMBINE.getMessage());
    }
  }

  private void checkPromoCodeDistribution(MandatoryRequest mandatoryRequest,
      Set<PromoCodeDistribution> promoCodeDistributions) {
    Boolean validStoreId = false;
    Boolean validChannelId = false;

    Map<String, String> allowedChannelIds;
    for (PromoCodeDistribution promoCodeDistribution : promoCodeDistributions) {
      if (promoCodeDistribution.getStoreId().equals(mandatoryRequest.getStoreId())) {
        validStoreId = true;
        allowedChannelIds = new HashMap<>();
        validChannelId = !isExistChannelIds(promoCodeDistribution.getChannelId());
        for (String allowedChannelId : promoCodeDistribution.getChannelId()) {
          allowedChannelIds.put(allowedChannelId, allowedChannelId);
        }

        if (allowedChannelIds.get(mandatoryRequest.getChannelId()) != null) {
          validChannelId = true;
        }
        break;
      }
    }

    if (!validStoreId) {
      throw new BusinessLogicException(ResponseCode.INVALID_STORE_ID.getCode(),
          ResponseCode.INVALID_STORE_ID.getMessage());
    }

    if (!validChannelId) {
      throw new BusinessLogicException(ResponseCode.INVALID_CHANNEL_ID.getCode(),
          ResponseCode.INVALID_CHANNEL_ID.getMessage());
    }
  }

  private boolean isExistChannelIds(Set<String> channelId) {
    return channelId != null;
  }

  private List<CostAmount> calculateCost(Double discount, List<Cost> costs) {
    List<CostAmount> costAmounts = new ArrayList<>();
    costs.forEach(cost -> {
      Double calculatedCostAmount = cost.getValue() + (cost.getPercent() * discount / 100);
      if (calculatedCostAmount > cost.getMaxValue()) {
        calculatedCostAmount = cost.getMaxValue();
      }

      CostAmount costAmount = new CostAmountBuilder()
          .withName(cost.getName())
          .withAmount(calculatedCostAmount)
          .build();
      costAmounts.add(costAmount);
    });

    return costAmounts;
  }

  private PromoCodeRuleValidation isValidPromoCodeRules(PromoCodeGroupRule promoCodeGroupRule,
      Map<String, String> orderAttribute, PromoCodeObject promoCodeObject) {

    List<PromoCodeRule> promoCodeRules = promoCodeGroupRule.getPromoCodeRules();

    PromoCodeRuleValidation promoCodeRuleValidation = new PromoCodeRuleValidationBuilder()
        .withValid(true)
        .withInvalidParams(new ArrayList<>())
        .build();

    if(!isExistPromoCodeRules(promoCodeRules)){
      return promoCodeRuleValidation;
    }

    for (PromoCodeRule promoCodeRule : promoCodeRules) {
      promoCodeRuleValidation.setValid(true);
      if (!isExistPromoCodeRuleParam(orderAttribute.get(promoCodeRule.getParam()))) {
        promoCodeRuleValidation.setValid(false);
        promoCodeRuleValidation.getInvalidParams().add(promoCodeRule.getParam());
      } else if (
          !this.isOperatorValid(promoCodeObject.getVariableMap().get(promoCodeRule.getParam())
              .getAllowedArithmetics(), promoCodeRule)
              || !this.isPromoCodeRuleValid(promoCodeRule, orderAttribute, String.valueOf(
              promoCodeObject.getVariableMap().get(promoCodeRule.getParam()).getDataType()))) {
        promoCodeRuleValidation.setValid(false);
        promoCodeRuleValidation.getInvalidParams().add(promoCodeRule.getParam());
        break;
      }
    }
    return promoCodeRuleValidation;
  }

  private boolean isExistPromoCodeRules(List<PromoCodeRule> promoCodeRules) {
    return promoCodeRules != null;
  }

  private Boolean isPromoCodeRuleValid(PromoCodeRule promoCodeRule,
      Map<String, String> orderAttribute, String dataType) {

    Boolean valid = false;
    if (dataType.equals(String.valueOf(DataType.STRING)) && this
        .isValidPromoCodeRuleWithDataTypeString(promoCodeRule, orderAttribute)
        ) {
      LOGGER.info("isPromoCodeRuleValid STRING, {}", true);
      valid = true;
    } else if (dataType.equals(String.valueOf(DataType.INTEGER)) && this
        .isValidPromoCodeRuleWithDataTypeInteger(promoCodeRule, orderAttribute)) {
      LOGGER.info("isPromoCodeRuleValid INTEGER, {}", true);
      valid = true;
    } else if (dataType.equals(String.valueOf(DataType.DOUBLE)) &&
        this.isValidPromoCodeRuleWithDataTypeDouble(promoCodeRule, orderAttribute)) {
      LOGGER.info("isPromoCodeRuleValid DOUBLE, {}", true);
      valid = true;
    } else if (dataType.equals(String.valueOf(DataType.DATE)) &&
        this.isValidPromoCodeRuleWithDataTypeDate(promoCodeRule, orderAttribute)) {
      LOGGER.info("isPromoCodeRuleValid DATE, {}", true);
      valid = true;
    }

    return valid;
  }

  private Boolean isValidPromoCodeRuleWithDataTypeString(PromoCodeRule promoCodeRule,
      Map<String, String> orderAttribute) {
    Boolean valid = false;
    String promoCodeRuleValue = promoCodeRule.getPromoCodeRuleValue().getValue();
    String promoCodeRuleOperator = promoCodeRule.getPromoCodeRuleValue().getOperator();
    String orderAttributeValue = orderAttribute.get(promoCodeRule.getParam());

    if ("=".equals(promoCodeRuleOperator)) {
      valid = promoCodeRuleValue.equals(orderAttributeValue);
      LOGGER.info("isValidPromoCodeRuleWithDataTypeString, = {}", valid);
    } else if ("<>".equals(promoCodeRuleOperator)) {
      valid = !promoCodeRuleValue.equals(orderAttributeValue);
      LOGGER.info("isValidPromoCodeRuleWithDataTypeString, <> {}", valid);
    } else if (IN.equalsIgnoreCase(promoCodeRuleOperator)) {
      String[] values = promoCodeRuleValue.split("\\|");
      for (String value : values) {
        if (value.equals(orderAttributeValue)) {
          valid = true;
          break;
        }
      }
      LOGGER.info("isValidPromoCodeRuleWithDataTypeString, IN {}", valid);
    } else if (NOT_IN.equalsIgnoreCase(promoCodeRuleOperator)) {
      String[] values = promoCodeRuleValue.split("\\|");
      for (String value : values) {
        valid = true;
        if (value.equals(orderAttributeValue)) {
          valid = false;
          break;
        }
      }
      LOGGER.info("isValidPromoCodeRuleWithDataTypeString, NOT_IN {}", valid);
    }

    return valid;
  }

  private Boolean isValidPromoCodeRuleWithDataTypeDate(PromoCodeRule promoCodeRule,
      Map<String, String> orderAttribute) {
    Boolean valid = false;

    Integer offset = timeZoneProperties.getOffsetToDate();

    String promoCodeRuleValue = promoCodeRule.getPromoCodeRuleValue().getValue();
    String promoCodeRuleOperator = promoCodeRule.getPromoCodeRuleValue().getOperator();
    String orderAttributeValue = orderAttribute.get(promoCodeRule.getParam());

    Date dateForValidator = DateFormatterHelper.stringToDate(promoCodeRuleValue, offset);
    Date dateToBeValidated = DateFormatterHelper.stringToDate(orderAttributeValue, offset);

    if ("=".equals(promoCodeRuleOperator)) {
      valid = dateToBeValidated.equals(dateForValidator);
      LOGGER.info("isValidPromoCodeRuleWithDataTypeDate, = {}", valid);
    } else if ("<>".equals(promoCodeRuleOperator)) {
      valid = !dateToBeValidated.equals(dateForValidator);
      LOGGER.info("isValidPromoCodeRuleWithDataTypeDate, <> {}", valid);
    } else if (">".equals(promoCodeRuleOperator)) {
      valid = dateToBeValidated.compareTo(dateForValidator) > 0;
      LOGGER.info("isValidPromoCodeRuleWithDataTypeDate, > {}", valid);
    } else if (">=".equals(promoCodeRuleOperator)) {
      valid = dateToBeValidated.compareTo(dateForValidator) >= 0;
      LOGGER.info("isValidPromoCodeRuleWithDataTypeDate, >= {}", valid);
    } else if ("<".equals(promoCodeRuleOperator)) {
      valid = dateToBeValidated.compareTo(dateForValidator) < 0;
      LOGGER.info("isValidPromoCodeRuleWithDataTypeDate, < {}", valid);
    } else if ("<=".equals(promoCodeRuleOperator)) {
      valid = dateToBeValidated.compareTo(dateForValidator) <= 0;
      LOGGER.info("isValidPromoCodeRuleWithDataTypeDate, <= {}", valid);
    } else if (IN.equalsIgnoreCase(promoCodeRuleOperator)) {
      String[] values = orderAttributeValue.split("\\|");
      Map<Object, Object> mappedValues = new HashMap<>();
      for (String value : values) {
        mappedValues.put(value, value);
      }
      valid = mappedValues.get(orderAttributeValue) != null;
      LOGGER.info("isValidPromoCodeRuleWithDataTypeDate, IN {}", valid);
    } else if (NOT_IN.equalsIgnoreCase(promoCodeRuleOperator)) {
      String[] values = orderAttributeValue.split("\\|");
      Map<Object, Object> mappedValues = new HashMap<>();
      for (String value : values) {
        mappedValues.put(value, value);
      }
      valid = mappedValues.get(orderAttributeValue) == null;
      LOGGER.info("isValidPromoCodeRuleWithDataTypeDate, NOT_IN {}", valid);
    }

    return valid;
  }

  private Boolean isValidPromoCodeRuleWithDataTypeInteger(PromoCodeRule promoCodeRule,
      Map<String, String> orderAttribute) {
    Boolean valid = false;

    String promoCodeRuleValue = promoCodeRule.getPromoCodeRuleValue().getValue();
    String promoCodeRuleOperator = promoCodeRule.getPromoCodeRuleValue().getOperator();
    String orderAttributeValue = orderAttribute.get(promoCodeRule.getParam());

    if ("=".equals(promoCodeRuleOperator)) {
      valid = promoCodeRuleValue.equals(orderAttributeValue);
      LOGGER.info("isValidPromoCodeRuleWithDataTypeInteger, = {}", true);
    } else if (IN.equalsIgnoreCase(promoCodeRule.getPromoCodeRuleValue().getOperator())) {
      String[] values = orderAttributeValue.split("\\|");
      Map<Object, Object> mappedValues = new HashMap<>();
      for (String value : values) {
        mappedValues.put(value, value);
      }
      valid = mappedValues.get(orderAttributeValue) != null;

      LOGGER.info("isValidPromoCodeRuleWithDataTypeInteger, IN {}", valid);
    } else if (NOT_IN.equals(promoCodeRuleOperator)) {
      String[] values = orderAttributeValue.split("\\|");
      Map<Object, Object> mappedValues = new HashMap<>();
      for (String value : values) {
        mappedValues.put(value, value);
      }
      valid = mappedValues.get(orderAttributeValue) == null;

      LOGGER.info("isValidPromoCodeRuleWithDataTypeInteger, NOT_IN {}", valid);
    } else if ("<>".equals(promoCodeRuleOperator)) {
      valid = !promoCodeRuleValue.equals(orderAttributeValue);
      LOGGER.info("isValidPromoCodeRuleWithDataTypeInteger, <> {}", true);
    } else if ("<".equals(promoCodeRuleOperator)) {
      valid = Integer.parseInt(promoCodeRuleValue) > Integer.parseInt(orderAttributeValue);
      LOGGER.info("isValidPromoCodeRuleWithDataTypeInteger, < {}", true);
    } else if (">".equals(promoCodeRuleOperator)) {
      valid = Integer.parseInt(promoCodeRuleValue) < Integer.parseInt(orderAttributeValue);
      LOGGER.info("isValidPromoCodeRuleWithDataTypeInteger, > {}", true);
    } else if ("<=".equals(promoCodeRuleOperator)) {
      valid = Integer.parseInt(promoCodeRuleValue) >= Integer.parseInt(orderAttributeValue);
      LOGGER.info("isValidPromoCodeRuleWithDataTypeInteger, <= {}", true);
    } else if (">=".equals(promoCodeRuleOperator)) {
      valid = Integer.parseInt(promoCodeRuleValue) <= Integer.parseInt(orderAttributeValue);
      LOGGER.info("isValidPromoCodeRuleWithDataTypeInteger, >= {}", true);
    }

    return valid;
  }

  private Boolean isValidPromoCodeRuleWithDataTypeDouble(PromoCodeRule promoCodeRule,
      Map<String, String> orderAttribute) {
    Boolean valid = false;

    String promoCodeRuleValue = promoCodeRule.getPromoCodeRuleValue().getValue();
    String promoCodeRuleOperator = promoCodeRule.getPromoCodeRuleValue().getOperator();
    String orderAttributeValue = orderAttribute.get(promoCodeRule.getParam());

    if (
        "=".equals(promoCodeRuleOperator) &&
            promoCodeRuleValue.equals(orderAttributeValue)) {
      valid = true;
      LOGGER.info("isValidPromoCodeRuleWithDataTypeDouble, = {}", true);
    } else if (
        "<>".equals(promoCodeRuleOperator) &&
            !promoCodeRuleValue.equals(orderAttributeValue)) {
      valid = true;
      LOGGER.info("isValidPromoCodeRuleWithDataTypeDouble, <> {}", true);
    } else if (
        "<".equals(promoCodeRuleOperator) &&
            (Double.parseDouble(promoCodeRuleValue)) > Double.parseDouble(orderAttributeValue)) {
      valid = true;
      LOGGER.info("isValidPromoCodeRuleWithDataTypeDouble, < {}", true);
    } else if (
        ">".equals(promoCodeRuleOperator) &&
            Double.parseDouble(promoCodeRuleValue) < Double.parseDouble(orderAttributeValue)) {
      valid = true;
      LOGGER.info("isValidPromoCodeRuleWithDataTypeDouble, > {}", true);
    } else if (
        "<=".equals(promoCodeRuleOperator) &&
            Double.parseDouble(promoCodeRuleValue) >= Double.parseDouble(orderAttributeValue)) {
      valid = true;
      LOGGER.info("isValidPromoCodeRuleWithDataTypeDouble, <= {}", true);
    } else if (
        ">=".equals(promoCodeRuleOperator) &&
            Double.parseDouble(promoCodeRuleValue) <= Double.parseDouble(orderAttributeValue)) {
      valid = true;
      LOGGER.info("isValidPromoCodeRuleWithDataTypeDouble, >= {}", true);
    }

    return valid;
  }

  private Boolean isOperatorValid(List<String> allowedArithmetics, PromoCodeRule promoCodeRule) {
    for (String allowedArithmetic : allowedArithmetics) {
      if (allowedArithmetic.equals(promoCodeRule.getPromoCodeRuleValue().getOperator())) {
        return true;
      }
    }
    return false;
  }

  private Boolean isExistPromoCodeRuleParam(Object param) {
    return param != null;
  }

  private Boolean isExistUsageCountInPromoCodeUsage(PromoCodeUsage promoCodeUsage) {
    return promoCodeUsage.getUsageCount() != null;
  }

  private Boolean isUsageCountLessThanMaxQty(PromoCodeUsage promoCodeUsage,
      PromoCodeObject promoCodeObject) {

    return promoCodeUsage == null
        || promoCodeUsage.getUsageCount() < promoCodeObject
        .getPromoCode()
        .getMaxQty();
  }
}
