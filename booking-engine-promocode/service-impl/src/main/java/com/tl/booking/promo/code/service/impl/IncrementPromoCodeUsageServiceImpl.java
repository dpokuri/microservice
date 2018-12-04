package com.tl.booking.promo.code.service.impl;

import com.mongodb.DuplicateKeyException;
import com.mongodb.WriteResult;
import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.dao.api.PromoCodeUsageRepository;
import com.tl.booking.promo.code.entity.CalculationResult;
import com.tl.booking.promo.code.entity.CostAmount;
import com.tl.booking.promo.code.entity.OrderDetail;
import com.tl.booking.promo.code.entity.PromoCodeObject;
import com.tl.booking.promo.code.entity.UsageRule;
import com.tl.booking.promo.code.entity.constant.PromoCodeUsageType;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.UsagePeriod;
import com.tl.booking.promo.code.entity.constant.enums.ValidatedBy;
import com.tl.booking.promo.code.entity.dao.PromoCodeUsage;
import com.tl.booking.promo.code.entity.dao.PromoCodeUsageBuilder;
import com.tl.booking.promo.code.libraries.configuration.TimeZoneProperties;
import com.tl.booking.promo.code.libraries.exception.BusinessLogicException;
import com.tl.booking.promo.code.libraries.utility.CommonHelper;
import com.tl.booking.promo.code.libraries.utility.DateFormatterHelper;
import com.tl.booking.promo.code.libraries.utility.PeriodHelper;
import com.tl.booking.promo.code.service.api.IncrementPromoCodeUsageService;
import com.tl.booking.promo.code.service.api.PromoCodeUsageLogService;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class IncrementPromoCodeUsageServiceImpl implements IncrementPromoCodeUsageService {

  @Autowired
  private PromoCodeUsageRepository promoCodeUsageRepository;

  @Autowired
  private PromoCodeUsageLogService promoCodeUsageLogService;

  @Autowired
  private Scheduler schedulerPromoCode;

  @Autowired
  private TimeZoneProperties timeZoneProperties;

  private static final Logger LOGGER = LoggerFactory.getLogger(IncrementPromoCodeUsageServiceImpl.class);

  private static final String IPROMO_CODE_USAGE_PERIOD_RULE = "iPromoCodeUsagePeriodRule";
  private static final String IPROMO_CODE_USAGE_GENERAL_RULE = "iPromoCodeUsageGeneralRule";
  private static final String IPROMO_CODE_USAGE_DAILY_RULE = "iPromoCodeUsageDailyRule";
  private static final String IPROMO_CODE_USAGE_DAILY_RULE_CARD_NUMBER =
      "iPromoCodeUsageDailyRuleCardNumber";
  private static final String IPROMO_CODE_USAGE_PERIOD_RULE_CARD_NUMBER = "iPromoCodeUsagePeriodRuleCardNumber";
  
  @Override
  public Single<Boolean> incrementPromoCodeUsage(MandatoryRequest mandatoryRequest,
      CalculationResult calculationResult, String cardNumber, String referenceId) {
    return this
        .incrementPromoCodeUsageByCode(calculationResult.getPromoCodeObject(),
            mandatoryRequest, cardNumber, 1)
        .map(success -> {

          if(!success){
            throw new BusinessLogicException(ResponseCode.NOT_VALID_USAGE_RULE.getCode(),
                ResponseCode.NOT_VALID_USAGE_RULE.getMessage());
          }

          List<CostAmount> partnerCosts = new ArrayList<>();
          List<CostAmount> companyCosts = new ArrayList<>();

          for (OrderDetail orderDetail : calculationResult.getOrderDetails()) {
            partnerCosts.addAll(orderDetail.getPartnerCosts());
            companyCosts.addAll(orderDetail.getCompanyCosts());
          }

          this.promoCodeUsageLogService
              .createPromoCodeUsageLog(mandatoryRequest,
                  calculationResult.getPromoCodeObject().getPromoCode().getCode(),
                  calculationResult.getTotalDiscount(),
                  partnerCosts,
                  companyCosts,
                  calculationResult.getTotalPrice(),
                  referenceId,
                  1, calculationResult.getOrderDetails());

          return success;
        }).subscribeOn(schedulerPromoCode);
  }

  private Single<Boolean> incrementPromoCodeUsageByCode(PromoCodeObject promoCodeObject,
      MandatoryRequest mandatoryRequest, String cardNumber, Integer increment) {
    return this
        .incrementPromoCodeUsageByCodeProcess(promoCodeObject, mandatoryRequest, cardNumber,
            increment).subscribeOn(schedulerPromoCode);
  }

  private Single<Boolean> incrementPromoCodeUsageByCodeProcess(PromoCodeObject promoCodeObject,
      MandatoryRequest mandatoryRequest, String cardNumber, Integer increment) {

    Map<String, UsageRule> usageRulesMap = CommonHelper.generateUsageRuleMap(promoCodeObject
        .getPromoCodeAdjustment().getUsageRules());

    Single<Boolean> incrementPromoCodeUsagePeriodRule = this
        .incrementPromoCodeUsagePeriodRule(mandatoryRequest.getUsername(), promoCodeObject, increment,
            usageRulesMap);

    Single<Boolean> incrementPromoCodeUsageDailyRule = this
        .incrementPromoCodeUsageDailyRule(mandatoryRequest.getUsername(), promoCodeObject, increment,
            usageRulesMap);

    Single<Boolean> incrementPromoCodeUsageGeneralRule = this
        .incrementPromoCodeUsageGeneralRule(promoCodeObject,
            increment, usageRulesMap);

    Single<Boolean> incrementPromoCodeUsageDailyRuleCardNumber = this
        .incrementPromoCodeUsageDailyRuleCardNumber(promoCodeObject, cardNumber,
            increment, usageRulesMap);

    Single<Boolean> incrementPromoCodeUsagePeriodRuleCardNumber = this
        .incrementPromoCodeUsagePeriodRuleCardNumber(promoCodeObject, cardNumber,
            increment, usageRulesMap);

    return Single.zip(
        incrementPromoCodeUsagePeriodRule,
        incrementPromoCodeUsageGeneralRule,
        incrementPromoCodeUsageDailyRule,
        incrementPromoCodeUsageDailyRuleCardNumber,
        incrementPromoCodeUsagePeriodRuleCardNumber,
        (iPromoCodeUsagePeriodRule,
            iPromoCodeUsageGeneralRule,
            iPromoCodeUsageDailyRule,
            iPromoCodeUsageDailyRuleCardNumber,
            iPromoCodeUsagePeriodRuleCardNumber) ->
        {
          Map<String, Boolean> validIncrementUsageRule = new HashMap<>();
          validIncrementUsageRule.put(IPROMO_CODE_USAGE_PERIOD_RULE,iPromoCodeUsagePeriodRule);
          validIncrementUsageRule.put(IPROMO_CODE_USAGE_GENERAL_RULE,iPromoCodeUsageGeneralRule);
          validIncrementUsageRule.put(IPROMO_CODE_USAGE_DAILY_RULE,iPromoCodeUsageDailyRule);
          validIncrementUsageRule.put(IPROMO_CODE_USAGE_DAILY_RULE_CARD_NUMBER,iPromoCodeUsageDailyRuleCardNumber);
          validIncrementUsageRule.put(IPROMO_CODE_USAGE_PERIOD_RULE_CARD_NUMBER,iPromoCodeUsagePeriodRuleCardNumber);

          return validIncrementUsageRule;
        })
        .flatMap(validIncrementUsageRule -> {
          if(validIncrementUsageRule.get(IPROMO_CODE_USAGE_PERIOD_RULE)
              && validIncrementUsageRule.get(IPROMO_CODE_USAGE_GENERAL_RULE)
              && validIncrementUsageRule.get(IPROMO_CODE_USAGE_DAILY_RULE)
              && validIncrementUsageRule.get(IPROMO_CODE_USAGE_DAILY_RULE_CARD_NUMBER)
              && validIncrementUsageRule.get(IPROMO_CODE_USAGE_PERIOD_RULE_CARD_NUMBER)){

            return Single.<Boolean>create(singleEmitter -> singleEmitter.onSuccess(true))
                .subscribeOn(schedulerPromoCode);
          } else {

            List<Single<Boolean>> singles = new ArrayList<>();

            if(validIncrementUsageRule.get(IPROMO_CODE_USAGE_GENERAL_RULE)) {
              singles.add(this.decrementUsageGeneral(promoCodeObject));
            }

            StringBuilder periodUsernameKey = new StringBuilder();
            periodUsernameKey.append(UsagePeriod.PERIOD.getCode());
            periodUsernameKey.append(ValidatedBy.USERNAME.getName());
            if(
                validIncrementUsageRule.get(IPROMO_CODE_USAGE_PERIOD_RULE)
                && isExistUsageRuleMap(usageRulesMap.get(periodUsernameKey.toString()))
                ){
              singles.add(this.decrementUsagePeriod(mandatoryRequest.getUsername(), promoCodeObject));
            }

            StringBuilder dailyUsernameKey = new StringBuilder();
            dailyUsernameKey.append(UsagePeriod.DAILY.getCode());
            dailyUsernameKey.append(ValidatedBy.USERNAME.getName());
            if(validIncrementUsageRule.get(IPROMO_CODE_USAGE_DAILY_RULE)
                && isExistUsageRuleMap(usageRulesMap.get(dailyUsernameKey.toString()))) {
              singles.add(this.decrementUsageDaily(mandatoryRequest.getUsername(), promoCodeObject));
            }

            StringBuilder dailyCardNumberKey = new StringBuilder();
            dailyCardNumberKey.append(UsagePeriod.DAILY.getCode());
            dailyCardNumberKey.append(ValidatedBy.CARD_NUMBER.getName());
            if(validIncrementUsageRule.get(IPROMO_CODE_USAGE_DAILY_RULE_CARD_NUMBER)
                && isExistUsageRuleMap(usageRulesMap.get(dailyCardNumberKey.toString()))){
              singles.add(this.decrementUsageDailyCardNumber(cardNumber, promoCodeObject));
            }

            StringBuilder periodCardNumberKey = new StringBuilder();
            periodCardNumberKey.append(UsagePeriod.PERIOD.getCode());
            periodCardNumberKey.append(ValidatedBy.CARD_NUMBER.getName());
            if(validIncrementUsageRule.get(IPROMO_CODE_USAGE_PERIOD_RULE_CARD_NUMBER)
                && isExistUsageRuleMap(usageRulesMap.get(periodCardNumberKey.toString()))){
              singles.add(this.decrementUsagePeriodCardNumber(cardNumber, promoCodeObject));
            }

            return Single.zip(singles, this::<Boolean>mergeList)
                .map(returnedValidations -> false).subscribeOn(schedulerPromoCode);
          }
        }).subscribeOn(schedulerPromoCode);
  }

  private void decrementUsageProcess(Date startDate, Date endDate, String cardNumber, String
      username,
      String promoCodeId) {
    PromoCodeUsage promoCodeUsage = this.promoCodeUsageRepository
        .findPromoCodeUsageByPromoCodeIdAndStartDateAndEndDateAndCardNumberAndUsername(
            promoCodeId,
            startDate, endDate, cardNumber, username);
    if (isExistPromoCodeUsage(promoCodeUsage)) {
      this.promoCodeUsageRepository
          .updatePromoCodeUsageById(promoCodeUsage.getId(), -1, 0);
    }
  }

  private Single<Boolean> decrementUsageDailyCardNumber(String cardNumber,
      PromoCodeObject promoCodeObject) {

    return Single.<Boolean>create(singleEmitter -> {
      Date todayDate = DateFormatterHelper.getTodayDate();
      this.decrementUsageProcess(todayDate, todayDate, cardNumber, "*", promoCodeObject
          .getPromoCode().getId());

      singleEmitter.onSuccess(true);
    }).subscribeOn(schedulerPromoCode);
  }

  private Single<Boolean> decrementUsageDaily(String username,
      PromoCodeObject promoCodeObject) {
    return Single.<Boolean>create(singleEmitter -> {
      Date todayDate = DateFormatterHelper.getTodayDate();
      this.decrementUsageProcess(todayDate, todayDate, "*", username, promoCodeObject
          .getPromoCode().getId());

      singleEmitter.onSuccess(true);
    }).subscribeOn(schedulerPromoCode);
  }

  private Single<Boolean> decrementUsagePeriod(String username, PromoCodeObject
      promoCodeObject) {
    return Single.<Boolean>create(singleEmitter -> {
      Date startDate = PeriodHelper.getStartDate(promoCodeObject, timeZoneProperties.getOffsetToDate());
      Date endDate = PeriodHelper.getEndDate(promoCodeObject, timeZoneProperties.getOffsetToDate());
      this.decrementUsageProcess(startDate, endDate, "*", username, promoCodeObject
          .getPromoCode().getId());

      singleEmitter.onSuccess(true);
    }).subscribeOn(schedulerPromoCode);
  }

  private Single<Boolean> decrementUsagePeriodCardNumber(String cardNumber,
      PromoCodeObject promoCodeObject) {

    return Single.<Boolean>create(singleEmitter -> {
      Date startDate = PeriodHelper.getStartDate(promoCodeObject, timeZoneProperties.getOffsetToDate());
      Date endDate = PeriodHelper.getEndDate(promoCodeObject, timeZoneProperties.getOffsetToDate());
      this.decrementUsageProcess(startDate, endDate, cardNumber, "*", promoCodeObject
          .getPromoCode().getId());

      singleEmitter.onSuccess(true);
    }).subscribeOn(schedulerPromoCode);
  }

  private Single<Boolean> decrementUsageGeneral(PromoCodeObject promoCodeObject) {
    return Single.<Boolean>create(singleEmitter -> {
      Date startDate = PeriodHelper.getStartDate(promoCodeObject, timeZoneProperties.getOffsetToDate());
      Date endDate = PeriodHelper.getEndDate(promoCodeObject, timeZoneProperties.getOffsetToDate());

      this.decrementUsageProcess(startDate, endDate, "*", "*", promoCodeObject
          .getPromoCode().getId());

      singleEmitter.onSuccess(true);
    }).subscribeOn(schedulerPromoCode);
  }

  private Single<Boolean> incrementPromoCodeUsagePeriodRule(String username,
      PromoCodeObject promoCodeObject, Integer increment, Map<String, UsageRule> usageRuleMap) {

    return Single.<Boolean>create(singleEmitter -> {
      Date startDate = PeriodHelper.getStartDate(promoCodeObject, timeZoneProperties.getOffsetToDate());
      Date endDate = PeriodHelper.getEndDate(promoCodeObject, timeZoneProperties.getOffsetToDate());

      StringBuilder usageRuleCode = new StringBuilder();
      usageRuleCode.append(UsagePeriod.PERIOD.getCode());
      usageRuleCode.append(ValidatedBy.USERNAME.getName());

      singleEmitter.onSuccess(this.incrementPromoCodeUsageProcess(usageRuleMap.get(usageRuleCode
          .toString()), startDate, endDate, "*", username, promoCodeObject, increment));
    }).subscribeOn(schedulerPromoCode);

  }

  private Single<Boolean> incrementPromoCodeUsagePeriodRuleCardNumber(PromoCodeObject promoCodeObject, String cardNumber, Integer increment, Map<String, UsageRule>
      usageRuleMap) {

    return Single.<Boolean>create(singleEmitter -> {
      Date startDate = PeriodHelper.getStartDate(promoCodeObject, timeZoneProperties.getOffsetToDate());
      Date endDate = PeriodHelper.getEndDate(promoCodeObject, timeZoneProperties.getOffsetToDate());

      StringBuilder usageRuleCode = new StringBuilder();
      usageRuleCode.append(UsagePeriod.PERIOD.getCode());
      usageRuleCode.append(ValidatedBy.CARD_NUMBER.getName());

      singleEmitter.onSuccess(this.incrementPromoCodeUsageProcess(usageRuleMap.get(usageRuleCode
          .toString()), startDate, endDate, cardNumber, "*", promoCodeObject, increment));
    }).subscribeOn(schedulerPromoCode);

  }

  private Single<Boolean> incrementPromoCodeUsageDailyRule(String username,
      PromoCodeObject promoCodeObject, Integer increment, Map<String,UsageRule> usageRuleMap) {
    return Single.<Boolean>create(singleEmitter -> {
      Date todayDate = DateFormatterHelper.getTodayDate();

      StringBuilder usageRuleCode = new StringBuilder();
      usageRuleCode.append(UsagePeriod.DAILY.getCode());
      usageRuleCode.append(ValidatedBy.USERNAME.getName());

      singleEmitter.onSuccess(this.incrementPromoCodeUsageProcess(usageRuleMap.get(usageRuleCode
          .toString()), todayDate, todayDate, "*", username, promoCodeObject, increment));
    }).subscribeOn(Schedulers.io());
  }

  private Boolean incrementPromoCodeUsageProcess(
      UsageRule usageRule,
      Date startDate,
      Date endDate,
      String cardNumber,
      String username,
      PromoCodeObject promoCodeObject,
      Integer increment) {

      if (isExistUsageRuleMap(usageRule)) {
        Integer maxUsage = promoCodeObject.getPromoCode().getMaxQty();
        String usagePeriodString = PromoCodeUsageType.GENERAL;
        try {
          if(usageRule.getUsageCount() != null){
            maxUsage = usageRule.getUsageCount();
          }

          if(usageRule.getUsagePeriod() != null){
            usagePeriodString = usageRule.getUsagePeriod().toString();
          }

          PromoCodeUsage searchPromoCodeUsage = this.promoCodeUsageRepository
              .findPromoCodeUsageByPromoCodeIdAndStartDateAndEndDateAndCardNumberAndUsername(
                  promoCodeObject.getPromoCode().getId(),
                  startDate, endDate, cardNumber, username);
          if (isExistPromoCodeUsage(searchPromoCodeUsage)) {
            WriteResult writeResult = this.promoCodeUsageRepository
                .updatePromoCodeUsageById(searchPromoCodeUsage.getId(), increment,
                    maxUsage);

            return this.checkWriteResult(writeResult);
          } else {
            this.promoCodeUsageRepository.save(
                this.buildPromoCodeUsage(username, promoCodeObject, cardNumber,
                    increment, usagePeriodString));
          }
        } catch (DuplicateKeyException dpe){
          PromoCodeUsage secondAttemptPromoCodeUsage = this.promoCodeUsageRepository
              .findPromoCodeUsageByPromoCodeIdAndStartDateAndEndDateAndCardNumberAndUsername(
                  promoCodeObject.getPromoCode().getId(),
                  startDate, endDate, cardNumber, username);
          WriteResult secondAttemptWriteResult = this.promoCodeUsageRepository
              .updatePromoCodeUsageById(secondAttemptPromoCodeUsage.getId(), increment,
                  maxUsage);

          LOGGER.error("DuplicateKeyException", dpe);

          return this.checkWriteResult(secondAttemptWriteResult);
        }
      }

    return true;
  }

  private Single<Boolean> incrementPromoCodeUsageDailyRuleCardNumber(PromoCodeObject promoCodeObject, String cardNumber, Integer increment, Map<String, UsageRule>
      usageRuleMap) {
    return Single.<Boolean>create(singleEmitter -> {
      Date todayDate = DateFormatterHelper.getTodayDate();

      StringBuilder usageRuleCode = new StringBuilder();
      usageRuleCode.append(UsagePeriod.DAILY.getCode());
      usageRuleCode.append(ValidatedBy.CARD_NUMBER.getName());

      singleEmitter.onSuccess(this.incrementPromoCodeUsageProcess(usageRuleMap.get(usageRuleCode
          .toString()), todayDate, todayDate, cardNumber, "*", promoCodeObject, increment));
    }).subscribeOn(schedulerPromoCode);
  }

  private Single<Boolean> incrementPromoCodeUsageGeneralRule(PromoCodeObject promoCodeObject,
      Integer increment, Map<String, UsageRule>
      usageRuleMap) {

    return Single.<Boolean>create(singleEmitter -> {
      Date startDate = PeriodHelper.getStartDate(promoCodeObject, timeZoneProperties.getOffsetToDate());
      Date endDate = PeriodHelper.getEndDate(promoCodeObject, timeZoneProperties.getOffsetToDate());

      singleEmitter.onSuccess(this.incrementPromoCodeUsageProcess(usageRuleMap.get(PromoCodeUsageType.GENERAL), startDate, endDate, "*", "*", promoCodeObject, increment));
    }).subscribeOn(schedulerPromoCode);
  }

  private PromoCodeUsage buildPromoCodeUsage(String username, PromoCodeObject
      promoCodeObject, String cardNumber, Integer increment, String
      promoCodeUsageType) {

    PromoCodeUsage promoCodeUsage = new PromoCodeUsageBuilder()
        .withPromoCode(promoCodeObject.getPromoCode().getCode())
        .withPromoCodeId(promoCodeObject.getPromoCode().getId())
        .withUsageCount(increment)
        .withUsername("*")
        .withCardNumber("*")
        .build();

    if(isExistCardNumber(cardNumber)){
      promoCodeUsage.setCardNumber(cardNumber);
    }

    if(isExistUsername(username)){
      promoCodeUsage.setUsername(username);
    }

    if (
        promoCodeUsageType.equals(PromoCodeUsageType.PERIOD)
            || promoCodeUsageType.equals(PromoCodeUsageType.GENERAL)) {
      promoCodeUsage.setStartDate(PeriodHelper.getStartDate(promoCodeObject, timeZoneProperties.getOffsetToDate()));
      promoCodeUsage.setEndDate(PeriodHelper.getEndDate(promoCodeObject, timeZoneProperties.getOffsetToDate()));
    } else {
      Date todayDate = DateFormatterHelper.getTodayDate();
      promoCodeUsage.setStartDate(todayDate);
      promoCodeUsage.setEndDate(todayDate);
    }
    return promoCodeUsage;
  }

  private <T> List<T> mergeList(Object... objects) {
    List<T> list = new ArrayList<>();

    for (Object object : objects) {
      list.add((T) object);
    }

    return list;
  }

  private boolean isExistUsageRuleMap(UsageRule usageRule) {
    return usageRule != null;
  }

  private boolean isExistPromoCodeUsage(PromoCodeUsage searchPromoCodeUsage) {
    return searchPromoCodeUsage != null;
  }

  private boolean isExistUsername(String username) {
    return username != null;
  }

  private boolean isExistCardNumber(String cardNumber) {
    return cardNumber != null;
  }

  private boolean checkWriteResult(WriteResult writeResult) {
    return writeResult.getN() > 0;
  }
}
