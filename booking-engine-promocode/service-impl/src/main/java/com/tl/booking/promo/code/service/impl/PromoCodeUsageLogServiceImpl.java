package com.tl.booking.promo.code.service.impl;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.dao.api.PromoCodeUsageLogRepository;
import com.tl.booking.promo.code.entity.CostAmount;
import com.tl.booking.promo.code.entity.OrderDetail;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeUsageLogColumn;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.dao.PromoCodeUsageLog;
import com.tl.booking.promo.code.entity.dao.PromoCodeUsageLogBuilder;
import com.tl.booking.promo.code.libraries.exception.BusinessLogicException;
import com.tl.booking.promo.code.service.api.PromoCodeUsageLogService;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@Service
public class PromoCodeUsageLogServiceImpl implements PromoCodeUsageLogService {

  @Autowired
  private PromoCodeUsageLogRepository promoCodeUsageLogRepository;

  @Override
  public void createPromoCodeUsageLog(MandatoryRequest mandatoryRequest, String code,
      Double discountAmount,
      List<CostAmount> partnerCostAmount, List<CostAmount> companyCostAmount, Double
      totalPrice, String referenceId, Integer
      usedQty, List<OrderDetail> orderDetails) {

    PromoCodeUsageLog promoCodeUsageLog = new PromoCodeUsageLogBuilder()
        .withCode(code)
        .withDate(new Date())
        .withTotalPrice(totalPrice)
        .withReferenceId(referenceId)
        .withDiscountAmount(discountAmount)
        .withPartnerCostAmount(partnerCostAmount)
        .withCompanyCostAmount(companyCostAmount)
        .withUsedQty(usedQty)
        .withStoreId(mandatoryRequest.getStoreId())
        .withChannelId(mandatoryRequest.getChannelId())
        .withUsername(mandatoryRequest.getUsername())
        .build();

    if(isExistOrderDetails(orderDetails)){
      promoCodeUsageLog.setOrderDetails(orderDetails);
    }

    this.promoCodeUsageLogRepository.save(promoCodeUsageLog);
  }

  private boolean isExistOrderDetails(List<OrderDetail> orderDetails) {
    return orderDetails != null;
  }

  @Override
  public Single<Page<PromoCodeUsageLog>> findPromoCodeUsageLogFilterPaginated(
      MandatoryRequest mandatoryRequest, String code, String startDate,
      String endDate, Integer page, Integer size,
      PromoCodeUsageLogColumn columnSort, SortDirection sortDirection) {
    return Single.<Page<PromoCodeUsageLog>>create(
        singleEmitter -> {

          String sort = this.setColumnSort(columnSort);

          Page<PromoCodeUsageLog> promoCodeUsageLogs = this.promoCodeUsageLogRepository
              .findPromoCodeUsageLogFilterPaginated(mandatoryRequest, code, startDate, endDate,
                  page, size,
                  sort, sortDirection);

          if (promoCodeUsageLogs.getNumberOfElements() == 0) {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                ResponseCode.DATA_NOT_EXIST.getMessage());
          }

          singleEmitter.onSuccess(promoCodeUsageLogs);
        }
    ).subscribeOn(Schedulers.io());
  }

  private String setColumnSort(PromoCodeUsageLogColumn promoCodeUsageLogColumn) {
    String result = null;
    if (isNotNullColumnSort(promoCodeUsageLogColumn)) {
      result = promoCodeUsageLogColumn.getValue();
    }

    return result;
  }

  private Boolean isNotNullColumnSort(PromoCodeUsageLogColumn promoCodeUsageLogColumn) {
    Boolean result = false;
    if (promoCodeUsageLogColumn != null) {
      result = true;
    }

    return result;
  }
}
