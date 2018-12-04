import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.when;

import com.tl.booking.promo.code.dao.api.PromoCodeAdjustmentRepository;
import com.tl.booking.promo.code.entity.PromoCodeAdjustmentDropdown;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeAdjustmentColumn;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeAdjustmentStatus;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.PromoCodeAdjustmentTestVariable;
import com.tl.booking.promo.code.entity.dao.PromoCodeAdjustment;
import com.tl.booking.promo.code.libraries.exception.BusinessLogicException;
import com.tl.booking.promo.code.service.api.CacheService;
import com.tl.booking.promo.code.service.api.CampaignService;
import com.tl.booking.promo.code.service.api.log.PromoCodeAdjustmentLogService;
import com.tl.booking.promo.code.service.impl.PromoCodeAdjustmentServiceImpl;
import io.reactivex.Single;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;

public class PromoCodeAdjustmentServiceImplTest extends PromoCodeAdjustmentTestVariable {

  @InjectMocks
  PromoCodeAdjustmentServiceImpl promoCodeAdjustmentService;

  @Mock
  PromoCodeAdjustmentRepository promoCodeAdjustmentRepository;

  @Mock
  PromoCodeAdjustmentLogService promoCodeAdjustmentLogService;

  @Mock
  CacheService cacheService;

  @Mock
  CampaignService campaignService;

  @Test
  public void createPromoCodeAdjustmentTestSuccess() throws Exception {

    when(promoCodeAdjustmentRepository
        .findPromoCodeAdjustmentByStoreIdAndCodeAndIsDeleted(CommonTestVariable.STORE_ID, CODE, 0))
        .thenReturn(null);
    when(promoCodeAdjustmentRepository.save(PROMO_CODE_ADJUSTMENT))
        .thenReturn(PROMO_CODE_ADJUSTMENT);
    when(cacheService.createCache(CACHE_KEY, PROMO_CODE_ADJUSTMENT, 0)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY_FIND_ACTIVATE)).thenReturn(true);

    PromoCodeAdjustment promoCodeAdjustment = promoCodeAdjustmentService
        .createPromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST, PROMO_CODE_ADJUSTMENT)
        .blockingGet();

    verify(promoCodeAdjustmentRepository)
        .findPromoCodeAdjustmentByStoreIdAndCodeAndIsDeleted(CommonTestVariable.STORE_ID, CODE, 0);
    verify(promoCodeAdjustmentRepository).save(PROMO_CODE_ADJUSTMENT);
    verify(cacheService).createCache(CACHE_KEY, PROMO_CODE_ADJUSTMENT, 0);
    verify(cacheService).deleteCache(CACHE_KEY_FIND_ACTIVATE);

    assertEquals(PROMO_CODE_ADJUSTMENT, promoCodeAdjustment);
  }


  @Test
  public void createPromoCodeAdjustmentTestExceptionExistPromoCodeAdjustment() throws Exception {

    when(promoCodeAdjustmentRepository
        .findPromoCodeAdjustmentByStoreIdAndCodeAndIsDeleted(CommonTestVariable.STORE_ID, CODE, 0))
        .thenReturn(PROMO_CODE_ADJUSTMENT);

    try {
      promoCodeAdjustmentService
          .createPromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST, PROMO_CODE_ADJUSTMENT)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(promoCodeAdjustmentRepository)
          .findPromoCodeAdjustmentByStoreIdAndCodeAndIsDeleted(CommonTestVariable.STORE_ID, CODE,
              0);

      assertEquals(ResponseCode.DUPLICATE_DATA_BY_CODE.getCode(), be.getCode());
      assertEquals(ResponseCode.DUPLICATE_DATA_BY_CODE.getMessage(), be.getMessage());
    }
  }

  @Test
  public void createPromoCodeAdjustmentTestExceptionSystemError() throws Exception {
    when(promoCodeAdjustmentRepository
        .findPromoCodeAdjustmentByStoreIdAndCodeAndIsDeleted(CommonTestVariable.STORE_ID, CODE, 0))
        .thenReturn(null);
    when(promoCodeAdjustmentRepository.save(PROMO_CODE_ADJUSTMENT)).thenReturn(null);

    try {
      promoCodeAdjustmentService
          .createPromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST, PROMO_CODE_ADJUSTMENT)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(promoCodeAdjustmentRepository)
          .findPromoCodeAdjustmentByStoreIdAndCodeAndIsDeleted(CommonTestVariable.STORE_ID, CODE,
              0);
      verify(promoCodeAdjustmentRepository).save(PROMO_CODE_ADJUSTMENT);

      assertEquals(ResponseCode.SYSTEM_ERROR.getCode(), be.getCode());
      assertEquals(ResponseCode.SYSTEM_ERROR.getMessage(), be.getMessage());
    }

  }

  @Test
  public void createPromoCodeAdjustmentTestErrorExceptionUsageRuleEmpty() throws Exception {

    when(promoCodeAdjustmentRepository
        .findPromoCodeAdjustmentByStoreIdAndCodeAndIsDeleted(CommonTestVariable.STORE_ID, CODE, 0))
        .thenReturn(null);

    try {
      promoCodeAdjustmentService
          .createPromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST,
              PROMO_CODE_ADJUSTMENT_ZERO_USAGE_RULE).blockingGet();

    } catch (BusinessLogicException be) {
      verify(promoCodeAdjustmentRepository)
          .findPromoCodeAdjustmentByStoreIdAndCodeAndIsDeleted(CommonTestVariable.STORE_ID, CODE,
              0);

      assertEquals(ResponseCode.USAGE_RULES_MUST_BE_NOT_EMPTY.getCode(), be.getCode());
      assertEquals(ResponseCode.USAGE_RULES_MUST_BE_NOT_EMPTY.getMessage(), be.getMessage());

    }


  }

  @Test
  public void createPromoCodeAdjustmentTestErrorExceptionUsageRuleValueEmpty() throws Exception {

    when(promoCodeAdjustmentRepository
        .findPromoCodeAdjustmentByStoreIdAndCodeAndIsDeleted(CommonTestVariable.STORE_ID, CODE, 0))
        .thenReturn(null);

    try {
      promoCodeAdjustmentService
          .createPromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST,
              PROMO_CODE_ADJUSTMENT_ZERO_RULE_VALUE).blockingGet();

    } catch (BusinessLogicException be) {
      verify(promoCodeAdjustmentRepository)
          .findPromoCodeAdjustmentByStoreIdAndCodeAndIsDeleted(CommonTestVariable.STORE_ID, CODE,
              0);

      assertEquals(ResponseCode.USAGE_COUNT_CANNOT_SET_ZERO.getCode(), be.getCode());
      assertEquals(ResponseCode.USAGE_COUNT_CANNOT_SET_ZERO.getMessage(), be.getMessage());

    }


  }


  @Test
  public void updatePromoCodeAdjustmentTestSuccess() throws Exception {
    when(this.campaignService.checkExistAndActiveCampaignByAdjustmentId(CommonTestVariable.MANDATORY_REQUEST, ID, false))
        .thenReturn(Single.just(false));

    when(promoCodeAdjustmentRepository
        .findPromoCodeAdjustmentByIdAndIsDeleted(ID, 0)).thenReturn(PROMO_CODE_ADJUSTMENT);
    when(promoCodeAdjustmentRepository.save(PROMO_CODE_ADJUSTMENT))
        .thenReturn(PROMO_CODE_ADJUSTMENT);
    when(cacheService.deleteCache(CACHE_KEY)).thenReturn(true);
    when(cacheService.createCache(CACHE_KEY, PROMO_CODE_ADJUSTMENT, 0)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY)).thenReturn(true);
    when(promoCodeAdjustmentLogService.createPromoCodeAdjustmentLog(CommonTestVariable.MANDATORY_REQUEST, PROMO_CODE_ADJUSTMENT))
        .thenReturn(Single.just(PROMO_CODE_ADJUSTMENT));

    when(cacheService.deleteCache(CACHE_KEY_FIND_ACTIVATE)).thenReturn(true);

    PromoCodeAdjustment promoCodeAdjustment = promoCodeAdjustmentService
        .updatePromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST, PROMO_CODE_ADJUSTMENT, ID)
        .blockingGet();

    verify(this.campaignService).checkExistAndActiveCampaignByAdjustmentId(CommonTestVariable.MANDATORY_REQUEST, ID, false);

    verify(promoCodeAdjustmentRepository)
        .findPromoCodeAdjustmentByIdAndIsDeleted(ID, 0);
    verify(promoCodeAdjustmentRepository).save(PROMO_CODE_ADJUSTMENT);
    verify(promoCodeAdjustmentLogService)
        .createPromoCodeAdjustmentLog(CommonTestVariable.MANDATORY_REQUEST, PROMO_CODE_ADJUSTMENT);
    verify(cacheService).deleteCache(CACHE_KEY);
    verify(cacheService).createCache(CACHE_KEY, PROMO_CODE_ADJUSTMENT, 0);

    verify(cacheService).deleteCache(CACHE_KEY);
    verify(cacheService).deleteCache(CACHE_KEY_FIND_ACTIVATE);

    assertEquals(PROMO_CODE_ADJUSTMENT, promoCodeAdjustment);
  }

  @Test
  public void updatePromoCodeAdjustmentTestExceptionDataNotExist() throws Exception {
    when(this.campaignService.checkExistAndActiveCampaignByAdjustmentId(CommonTestVariable.MANDATORY_REQUEST, ID, false))
        .thenReturn(Single.just(false));

    when(promoCodeAdjustmentRepository
        .findPromoCodeAdjustmentByIdAndIsDeleted(ID, 0)).thenReturn(null);

    try {
      promoCodeAdjustmentService
          .updatePromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST, PROMO_CODE_ADJUSTMENT,
              ID).blockingGet();
    } catch (BusinessLogicException be) {
      verify(this.campaignService).checkExistAndActiveCampaignByAdjustmentId(CommonTestVariable.MANDATORY_REQUEST, ID, false);
      verify(promoCodeAdjustmentRepository)
          .findPromoCodeAdjustmentByIdAndIsDeleted(ID, 0);

      assertEquals(ResponseCode.DATA_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.DATA_NOT_EXIST.getMessage(), be.getMessage());
    }

  }


  @Test
  public void updatePromoCodeAdjustmentTestExceptionCodeSameFromOtherDocument() throws Exception {
    when(this.campaignService.checkExistAndActiveCampaignByAdjustmentId(CommonTestVariable.MANDATORY_REQUEST, ID, false))
        .thenReturn(Single.just(false));

    when(promoCodeAdjustmentRepository
            .findPromoCodeAdjustmentByIdAndIsDeleted(ID, 0)).thenReturn(PROMO_CODE_ADJUSTMENT);

    when(promoCodeAdjustmentRepository
        .findPromoCodeAdjustmentByStoreIdAndCodeAndIsDeleted(CommonTestVariable.STORE_ID,
            CODE_OTHER, 0))
        .thenReturn(PROMO_CODE_ADJUSTMENT_3);

    try {
      promoCodeAdjustmentService
          .updatePromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST, PROMO_CODE_ADJUSTMENT_2,
              ID).blockingGet();
    } catch (BusinessLogicException be) {
      verify(this.campaignService).checkExistAndActiveCampaignByAdjustmentId(CommonTestVariable.MANDATORY_REQUEST, ID, false);

      verify(promoCodeAdjustmentRepository)
          .findPromoCodeAdjustmentByIdAndIsDeleted(ID, 0);
      verify(promoCodeAdjustmentRepository)
          .findPromoCodeAdjustmentByStoreIdAndCodeAndIsDeleted(CommonTestVariable.STORE_ID,
              CODE_OTHER, 0);

      assertEquals(ResponseCode.CODE_EXIST_OTHER_RECORD.getCode(), be.getCode());
      assertEquals(ResponseCode.CODE_EXIST_OTHER_RECORD.getMessage(), be.getMessage());
    }

  }


  @Test
  public void updatePromoCodeAdjustmentTestExceptionSystemError() throws Exception {
    when(this.campaignService.checkExistAndActiveCampaignByAdjustmentId(CommonTestVariable.MANDATORY_REQUEST, ID, false))
        .thenReturn(Single.just(false));

    when(promoCodeAdjustmentRepository
        .findPromoCodeAdjustmentByIdAndIsDeleted(ID, 0)).thenReturn(PROMO_CODE_ADJUSTMENT);
    when(promoCodeAdjustmentRepository.save(PROMO_CODE_ADJUSTMENT)).thenReturn(null);

    try {
      promoCodeAdjustmentService
          .updatePromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST, PROMO_CODE_ADJUSTMENT,
              ID).blockingGet();
    } catch (BusinessLogicException be) {
      verify(this.campaignService).checkExistAndActiveCampaignByAdjustmentId(CommonTestVariable.MANDATORY_REQUEST, ID, false);

      verify(promoCodeAdjustmentRepository)
          .findPromoCodeAdjustmentByIdAndIsDeleted(ID, 0);
      verify(promoCodeAdjustmentRepository).save(PROMO_CODE_ADJUSTMENT);

      assertEquals(ResponseCode.SYSTEM_ERROR.getCode(), be.getCode());
      assertEquals(ResponseCode.SYSTEM_ERROR.getMessage(), be.getMessage());
    }


  }

  @Test
  public void deletePromoCodeAdjustmentTestSuccess() throws Exception {
    PROMO_CODE_ADJUSTMENT.setIsDeleted(IS_DELETED_FOR_DELETED);

    when(this.campaignService.checkExistAndActiveCampaignByAdjustmentId(CommonTestVariable.MANDATORY_REQUEST, ID, true))
        .thenReturn(Single.just(false));

    when(promoCodeAdjustmentRepository
        .findPromoCodeAdjustmentByIdAndIsDeleted(ID, 0)).thenReturn(PROMO_CODE_ADJUSTMENT);
    when(promoCodeAdjustmentRepository.softDeleted(PROMO_CODE_ADJUSTMENT, ID)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY_FIND_ACTIVATE)).thenReturn(true);

    Boolean promoCodeAdjustment = promoCodeAdjustmentService
        .deletePromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();

    verify(this.campaignService).checkExistAndActiveCampaignByAdjustmentId(CommonTestVariable.MANDATORY_REQUEST, ID, true);

    verify(promoCodeAdjustmentRepository)
        .findPromoCodeAdjustmentByIdAndIsDeleted(ID, 0);
    verify(promoCodeAdjustmentRepository).softDeleted(PROMO_CODE_ADJUSTMENT, ID);
    verify(cacheService).deleteCache(CACHE_KEY);
    verify(cacheService).deleteCache(CACHE_KEY_FIND_ACTIVATE);

    assertEquals(promoCodeAdjustment, true);
  }

  @Test
  public void deletePromoCodeAdjustmentTestDataNotExist() throws Exception {
    when(this.campaignService.checkExistAndActiveCampaignByAdjustmentId(CommonTestVariable.MANDATORY_REQUEST, ID, true))
        .thenReturn(Single.just(false));

    when(promoCodeAdjustmentRepository
        .findPromoCodeAdjustmentByIdAndIsDeleted(ID, 0)).thenReturn(null);

    try {
      promoCodeAdjustmentService.deletePromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(this.campaignService).checkExistAndActiveCampaignByAdjustmentId(CommonTestVariable.MANDATORY_REQUEST, ID, true);

      verify(promoCodeAdjustmentRepository)
          .findPromoCodeAdjustmentByIdAndIsDeleted(ID, 0);

      assertEquals(ResponseCode.DATA_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.DATA_NOT_EXIST.getMessage(), be.getMessage());
    }


  }


  @Test
  public void findPromoCodeAdjustmentByIdTestSuccessWithCache() throws Exception {
    when(cacheService.findCacheByKey(CACHE_KEY, PromoCodeAdjustment.class))
        .thenReturn(PROMO_CODE_ADJUSTMENT);

    PromoCodeAdjustment promoCodeAdjustment = promoCodeAdjustmentService
        .findPromoCodeAdjustmentById(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();

    verify(cacheService).findCacheByKey(CACHE_KEY, PromoCodeAdjustment.class);

    assertEquals(promoCodeAdjustment, PROMO_CODE_ADJUSTMENT);
  }

  @Test
  public void findPromoCodeAdjustmentByIdTestSuccessWithoutCache() throws Exception {
    when(cacheService.findCacheByKey(CACHE_KEY, PromoCodeAdjustment.class)).thenReturn(null);
    when(promoCodeAdjustmentRepository
        .findPromoCodeAdjustmentByIdAndIsDeleted(ID, 0)).thenReturn(PROMO_CODE_ADJUSTMENT);
    when(cacheService.createCache(CACHE_KEY, PROMO_CODE_ADJUSTMENT, 0)).thenReturn(true);

    PromoCodeAdjustment promoCodeAdjustment = promoCodeAdjustmentService
        .findPromoCodeAdjustmentById(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();

    verify(cacheService).findCacheByKey(CACHE_KEY, PromoCodeAdjustment.class);
    verify(promoCodeAdjustmentRepository).findPromoCodeAdjustmentByIdAndIsDeleted(ID, 0);
    verify(cacheService).createCache(CACHE_KEY, PROMO_CODE_ADJUSTMENT, 0);

    assertEquals(promoCodeAdjustment, PROMO_CODE_ADJUSTMENT);
  }


  @Test
  public void findPromoCodeAdjustmentByIdTestDataNotExist() throws Exception {
    when(cacheService.findCacheByKey(CACHE_KEY, PromoCodeAdjustment.class)).thenReturn(null);
    when(promoCodeAdjustmentRepository
        .findPromoCodeAdjustmentByIdAndIsDeleted(ID, 0)).thenReturn(null);

    try {
      promoCodeAdjustmentService
          .findPromoCodeAdjustmentById(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();
    } catch (BusinessLogicException be) {
      verify(cacheService).findCacheByKey(CACHE_KEY, PromoCodeAdjustment.class);
      verify(promoCodeAdjustmentRepository).findPromoCodeAdjustmentByIdAndIsDeleted(ID, 0);

      assertEquals(ResponseCode.DATA_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.DATA_NOT_EXIST.getMessage(), be.getMessage());
    }
  }


  @Test
  public void findPromoCodeAdjustmentFilterPaginatedSuccess() throws Exception {

    when(promoCodeAdjustmentRepository.findPromoCodeAdjustmentFilterPaginated(
        CommonTestVariable.STORE_ID,
        NAME, PROMO_CODE_COMBINE, MAX_DISCOUNT, null,
        PAGE, SIZE, PromoCodeAdjustmentColumn.ID.getValue(), SortDirection.ASC))
        .thenReturn(PROMO_CODE_ADJUSTMENTS_PAGE);

    Page<PromoCodeAdjustment> promoCodeAdjustments = promoCodeAdjustmentService
        .findPromoCodeAdjustmentFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
            NAME, PROMO_CODE_COMBINE, MAX_DISCOUNT, null, PAGE, SIZE, PromoCodeAdjustmentColumn.ID,
            SortDirection.ASC).blockingGet();

    verify(promoCodeAdjustmentRepository).findPromoCodeAdjustmentFilterPaginated(
        CommonTestVariable.STORE_ID,
        NAME, PROMO_CODE_COMBINE, MAX_DISCOUNT, null,
        PAGE, SIZE, PromoCodeAdjustmentColumn.ID.getValue(), SortDirection.ASC);

    assertEquals(PROMO_CODE_ADJUSTMENTS_PAGE, promoCodeAdjustments);
  }


  @Test
  public void findPromoCodeAdjustmentFilterPaginatedSuccessWithStatus() throws Exception {

    when(promoCodeAdjustmentRepository.findPromoCodeAdjustmentFilterPaginated(
        CommonTestVariable.STORE_ID,
        NAME, PROMO_CODE_COMBINE, MAX_DISCOUNT, PromoCodeAdjustmentStatus.ACTIVE.getCode(),
        PAGE, SIZE, PromoCodeAdjustmentColumn.ID.getValue(), SortDirection.ASC))
        .thenReturn(PROMO_CODE_ADJUSTMENTS_PAGE);

    Page<PromoCodeAdjustment> promoCodeAdjustments = promoCodeAdjustmentService
        .findPromoCodeAdjustmentFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
            NAME, PROMO_CODE_COMBINE, MAX_DISCOUNT, PromoCodeAdjustmentStatus.ACTIVE, PAGE, SIZE,
            PromoCodeAdjustmentColumn.ID,
            SortDirection.ASC).blockingGet();

    verify(promoCodeAdjustmentRepository).findPromoCodeAdjustmentFilterPaginated(
        CommonTestVariable.STORE_ID,
        NAME, PROMO_CODE_COMBINE, MAX_DISCOUNT, PromoCodeAdjustmentStatus.ACTIVE.getCode(),
        PAGE, SIZE, PromoCodeAdjustmentColumn.ID.getValue(), SortDirection.ASC);

    assertEquals(PROMO_CODE_ADJUSTMENTS_PAGE, promoCodeAdjustments);
  }

  @Test
  public void updateStatusPendingPromoCodeAdjustmentTestSuccess() throws Exception {
    PROMO_CODE_ADJUSTMENT.setPromoCodeAdjustmentStatus(PromoCodeAdjustmentStatus.DRAFT);

    when(promoCodeAdjustmentRepository
        .findPromoCodeAdjustmentByIdAndIsDeleted(ID, 0))
        .thenReturn(PROMO_CODE_ADJUSTMENT);

    when(promoCodeAdjustmentRepository.updateStatus(PROMO_CODE_ADJUSTMENT)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY)).thenReturn(true);
    when(cacheService.createCache(CACHE_KEY, PROMO_CODE_ADJUSTMENT, 0)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY_FIND_ACTIVATE)).thenReturn(true);
    when(promoCodeAdjustmentLogService.createPromoCodeAdjustmentLog(CommonTestVariable.MANDATORY_REQUEST, PROMO_CODE_ADJUSTMENT))
        .thenReturn(Single.just(PROMO_CODE_ADJUSTMENT));

    PromoCodeAdjustment promoCodeAdjustment = promoCodeAdjustmentService
        .updateStatusPendingPromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST, ID)
        .blockingGet();

    verify(promoCodeAdjustmentRepository).findPromoCodeAdjustmentByIdAndIsDeleted(ID, 0);

    verify(promoCodeAdjustmentRepository).updateStatus(PROMO_CODE_ADJUSTMENT);
    verify(cacheService).deleteCache(CACHE_KEY);
    verify(cacheService).createCache(CACHE_KEY, PROMO_CODE_ADJUSTMENT, 0);
    verify(cacheService).deleteCache(CACHE_KEY_FIND_ACTIVATE);
    verify(promoCodeAdjustmentLogService).createPromoCodeAdjustmentLog(CommonTestVariable.MANDATORY_REQUEST, PROMO_CODE_ADJUSTMENT);

    assertEquals(PROMO_CODE_ADJUSTMENT, promoCodeAdjustment);
  }


  @Test
  public void updateStatusPendingPromoCodeAdjustmentTestNotDataExist() throws Exception {
    PROMO_CODE_ADJUSTMENT.setPromoCodeAdjustmentStatus(PromoCodeAdjustmentStatus.DRAFT);

    when(promoCodeAdjustmentRepository
        .findPromoCodeAdjustmentByIdAndIsDeleted(ID, 0))
        .thenReturn(null);

    try {
      promoCodeAdjustmentService
          .updateStatusPendingPromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(promoCodeAdjustmentRepository).findPromoCodeAdjustmentByIdAndIsDeleted(ID, 0);

      assertEquals(ResponseCode.DATA_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.DATA_NOT_EXIST.getMessage(), be.getMessage());

    }


  }

  @Test
  public void updateStatusPendingPromoCodeAdjustmentTestStatusExistingNotDraft() throws Exception {
    PROMO_CODE_ADJUSTMENT.setPromoCodeAdjustmentStatus(PromoCodeAdjustmentStatus.PENDING);

    when(promoCodeAdjustmentRepository
        .findPromoCodeAdjustmentByIdAndIsDeleted(ID, 0))
        .thenReturn(PROMO_CODE_ADJUSTMENT);

    try {
      promoCodeAdjustmentService
          .updateStatusPendingPromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(promoCodeAdjustmentRepository).findPromoCodeAdjustmentByIdAndIsDeleted(ID, 0);

      assertEquals(ResponseCode.PROMO_CODE_ADJUSTMENT_STATUS_NOT_DRAFT.getCode(), be.getCode());
      assertEquals(ResponseCode.PROMO_CODE_ADJUSTMENT_STATUS_NOT_DRAFT.getMessage(),
          be.getMessage());

    }


  }

  @Test
  public void updateStatusActivedPromoCodeAdjustmentTestSuccess() throws Exception {
    PROMO_CODE_ADJUSTMENT.setPromoCodeAdjustmentStatus(PromoCodeAdjustmentStatus.PENDING);

    when(promoCodeAdjustmentRepository
        .findPromoCodeAdjustmentByIdAndIsDeleted(ID, 0))
        .thenReturn(PROMO_CODE_ADJUSTMENT);

    when(promoCodeAdjustmentRepository.updateStatus(PROMO_CODE_ADJUSTMENT)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY)).thenReturn(true);
    when(cacheService.createCache(CACHE_KEY, PROMO_CODE_ADJUSTMENT, 0)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY_FIND_ACTIVATE)).thenReturn(true);
    when(promoCodeAdjustmentLogService.createPromoCodeAdjustmentLog(CommonTestVariable.MANDATORY_REQUEST, PROMO_CODE_ADJUSTMENT))
        .thenReturn(Single.just(PROMO_CODE_ADJUSTMENT));

    PromoCodeAdjustment promoCodeAdjustment = promoCodeAdjustmentService
        .updateStatusActivedPromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST, ID)
        .blockingGet();

    verify(promoCodeAdjustmentRepository).findPromoCodeAdjustmentByIdAndIsDeleted(ID, 0);

    verify(promoCodeAdjustmentRepository).updateStatus(PROMO_CODE_ADJUSTMENT);
    verify(cacheService).deleteCache(CACHE_KEY);
    verify(cacheService).createCache(CACHE_KEY, PROMO_CODE_ADJUSTMENT, 0);
    verify(cacheService).deleteCache(CACHE_KEY_FIND_ACTIVATE);
    verify(promoCodeAdjustmentLogService).createPromoCodeAdjustmentLog(CommonTestVariable.MANDATORY_REQUEST, PROMO_CODE_ADJUSTMENT);

    assertEquals(PROMO_CODE_ADJUSTMENT, promoCodeAdjustment);
  }


  @Test
  public void updateStatusActivedPromoCodeAdjustmentTestSuccessWithWxistingStatusInActive()
      throws Exception {
    PROMO_CODE_ADJUSTMENT.setPromoCodeAdjustmentStatus(PromoCodeAdjustmentStatus.INACTIVE);

    when(promoCodeAdjustmentRepository
        .findPromoCodeAdjustmentByIdAndIsDeleted(ID, 0))
        .thenReturn(PROMO_CODE_ADJUSTMENT);

    when(promoCodeAdjustmentRepository.updateStatus(PROMO_CODE_ADJUSTMENT)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY)).thenReturn(true);
    when(cacheService.createCache(CACHE_KEY, PROMO_CODE_ADJUSTMENT, 0)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY_FIND_ACTIVATE)).thenReturn(true);
    when(promoCodeAdjustmentLogService.createPromoCodeAdjustmentLog(CommonTestVariable.MANDATORY_REQUEST, PROMO_CODE_ADJUSTMENT))
        .thenReturn(Single.just(PROMO_CODE_ADJUSTMENT));

    PromoCodeAdjustment promoCodeAdjustment = promoCodeAdjustmentService
        .updateStatusActivedPromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST, ID)
        .blockingGet();

    verify(promoCodeAdjustmentRepository).findPromoCodeAdjustmentByIdAndIsDeleted(ID, 0);

    verify(promoCodeAdjustmentRepository).updateStatus(PROMO_CODE_ADJUSTMENT);
    verify(cacheService).deleteCache(CACHE_KEY);
    verify(cacheService).createCache(CACHE_KEY, PROMO_CODE_ADJUSTMENT, 0);
    verify(cacheService).deleteCache(CACHE_KEY_FIND_ACTIVATE);
    verify(promoCodeAdjustmentLogService).createPromoCodeAdjustmentLog(CommonTestVariable.MANDATORY_REQUEST, PROMO_CODE_ADJUSTMENT);

    assertEquals(PROMO_CODE_ADJUSTMENT, promoCodeAdjustment);
  }


  @Test
  public void updateStatusActivedPromoCodeAdjustmentTestNotDataExist() throws Exception {
    PROMO_CODE_ADJUSTMENT.setPromoCodeAdjustmentStatus(PromoCodeAdjustmentStatus.PENDING);

    when(promoCodeAdjustmentRepository
        .findPromoCodeAdjustmentByIdAndIsDeleted(ID, 0))
        .thenReturn(null);

    try {
      promoCodeAdjustmentService
          .updateStatusActivedPromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(promoCodeAdjustmentRepository).findPromoCodeAdjustmentByIdAndIsDeleted(ID, 0);

      assertEquals(ResponseCode.DATA_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.DATA_NOT_EXIST.getMessage(), be.getMessage());

    }
  }

  @Test
  public void updateStatusActivedPromoCodeAdjustmentTestStatusExistingNotPending()
      throws Exception {
    PROMO_CODE_ADJUSTMENT.setPromoCodeAdjustmentStatus(PromoCodeAdjustmentStatus.DRAFT);

    when(promoCodeAdjustmentRepository
        .findPromoCodeAdjustmentByIdAndIsDeleted(ID, 0))
        .thenReturn(PROMO_CODE_ADJUSTMENT);

    try {
      promoCodeAdjustmentService
          .updateStatusActivedPromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(promoCodeAdjustmentRepository).findPromoCodeAdjustmentByIdAndIsDeleted(ID, 0);

      assertEquals(ResponseCode.PROMO_CODE_ADJUSTMENT_STATUS_CANNOT_UPDATE.getCode(), be.getCode());
      assertEquals(ResponseCode.PROMO_CODE_ADJUSTMENT_STATUS_CANNOT_UPDATE.getMessage(),
          be.getMessage());

    }


  }

  @Test
  public void updateStatusInActivedPromoCodeAdjustmentTestSuccess() throws Exception {
    PROMO_CODE_ADJUSTMENT.setPromoCodeAdjustmentStatus(PromoCodeAdjustmentStatus.ACTIVE);
    when(this.campaignService.checkExistAndActiveCampaignByAdjustmentId(CommonTestVariable.MANDATORY_REQUEST, ID, false))
        .thenReturn(Single.just(false));

    when(promoCodeAdjustmentRepository
        .findPromoCodeAdjustmentByIdAndIsDeleted(ID, 0))
        .thenReturn(PROMO_CODE_ADJUSTMENT);

    when(promoCodeAdjustmentRepository.updateStatus(PROMO_CODE_ADJUSTMENT)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY)).thenReturn(true);
    when(cacheService.createCache(CACHE_KEY, PROMO_CODE_ADJUSTMENT, 0)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY_FIND_ACTIVATE)).thenReturn(true);
    when(promoCodeAdjustmentLogService.createPromoCodeAdjustmentLog(CommonTestVariable.MANDATORY_REQUEST, PROMO_CODE_ADJUSTMENT))
        .thenReturn(Single.just(PROMO_CODE_ADJUSTMENT));

    PromoCodeAdjustment promoCodeAdjustment = promoCodeAdjustmentService
        .updateStatusInActivedPromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST, ID)
        .blockingGet();

    verify(this.campaignService).checkExistAndActiveCampaignByAdjustmentId(CommonTestVariable.MANDATORY_REQUEST, ID, false);

    verify(promoCodeAdjustmentRepository).findPromoCodeAdjustmentByIdAndIsDeleted(ID, 0);

    verify(promoCodeAdjustmentRepository).updateStatus(PROMO_CODE_ADJUSTMENT);
    verify(cacheService).deleteCache(CACHE_KEY);
    verify(cacheService).createCache(CACHE_KEY, PROMO_CODE_ADJUSTMENT, 0);
    verify(cacheService).deleteCache(CACHE_KEY_FIND_ACTIVATE);
    verify(promoCodeAdjustmentLogService).createPromoCodeAdjustmentLog(CommonTestVariable.MANDATORY_REQUEST, PROMO_CODE_ADJUSTMENT);

    assertEquals(PROMO_CODE_ADJUSTMENT, promoCodeAdjustment);
  }


  @Test
  public void updateStatusInActivedPromoCodeAdjustmentTestNotDataExist() throws Exception {
    PROMO_CODE_ADJUSTMENT.setPromoCodeAdjustmentStatus(PromoCodeAdjustmentStatus.ACTIVE);

    when(this.campaignService.checkExistAndActiveCampaignByAdjustmentId(CommonTestVariable.MANDATORY_REQUEST, ID, false))
        .thenReturn(Single.just(false));

    when(promoCodeAdjustmentRepository
        .findPromoCodeAdjustmentByIdAndIsDeleted(ID, 0))
        .thenReturn(null);

    try {
      promoCodeAdjustmentService
          .updateStatusInActivedPromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(this.campaignService).checkExistAndActiveCampaignByAdjustmentId(CommonTestVariable.MANDATORY_REQUEST, ID, false);
      verify(promoCodeAdjustmentRepository).findPromoCodeAdjustmentByIdAndIsDeleted(ID, 0);

      assertEquals(ResponseCode.DATA_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.DATA_NOT_EXIST.getMessage(), be.getMessage());

    }
  }


  @Test
  public void updateStatusInActivedPromoCodeAdjustmentTestStatusExistingNotActive()
      throws Exception {
    PROMO_CODE_ADJUSTMENT.setPromoCodeAdjustmentStatus(PromoCodeAdjustmentStatus.DRAFT);

    when(this.campaignService.checkExistAndActiveCampaignByAdjustmentId(CommonTestVariable.MANDATORY_REQUEST, ID, false))
        .thenReturn(Single.just(false));

    when(promoCodeAdjustmentRepository
        .findPromoCodeAdjustmentByIdAndIsDeleted(ID, 0))
        .thenReturn(PROMO_CODE_ADJUSTMENT);

    try {
      promoCodeAdjustmentService
          .updateStatusInActivedPromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(this.campaignService).checkExistAndActiveCampaignByAdjustmentId(CommonTestVariable.MANDATORY_REQUEST, ID, false);
      verify(promoCodeAdjustmentRepository).findPromoCodeAdjustmentByIdAndIsDeleted(ID, 0);

      assertEquals(ResponseCode.PROMO_CODE_ADJUSTMENT_STATUS_NOT_IN_ACTIVE.getCode(), be.getCode());
      assertEquals(ResponseCode.PROMO_CODE_ADJUSTMENT_STATUS_NOT_IN_ACTIVE.getMessage(),
          be.getMessage());

    }


  }

  @Test
  public void findPromoCodeAdjustmentsTestWithoutCache() throws Exception {

    when(this.cacheService.findCacheByKey(CACHE_KEY_FIND_ACTIVATE, List.class)).thenReturn(null);
    when(this.promoCodeAdjustmentRepository
        .findAllByStoreIdAndPromoCodeAdjustmentStatusAndIsDeleted(CommonTestVariable.STORE_ID,
            PromoCodeAdjustmentStatus.ACTIVE, 0))
        .thenReturn(PROMO_CODE_ADJUSTMENT_RESOURCE_DROPDOWN);
    when(this.cacheService
        .createCache(CACHE_KEY_FIND_ACTIVATE, PROMO_CODE_ADJUSTMENT_DROPDOWN_LIST, 0))
        .thenReturn(true);

    List<PromoCodeAdjustmentDropdown> promoCodeAdjustmentList = this.promoCodeAdjustmentService
        .findPromoCodeAdjustments(CommonTestVariable.MANDATORY_REQUEST).blockingGet();

    verify(this.cacheService).findCacheByKey(CACHE_KEY_FIND_ACTIVATE, List.class);
    verify(this.promoCodeAdjustmentRepository)
        .findAllByStoreIdAndPromoCodeAdjustmentStatusAndIsDeleted(CommonTestVariable.STORE_ID,
            PromoCodeAdjustmentStatus.ACTIVE, 0);
    verify(this.cacheService)
        .createCache(CACHE_KEY_FIND_ACTIVATE, PROMO_CODE_ADJUSTMENT_DROPDOWN_LIST, 0);

    assertEquals(promoCodeAdjustmentList, PROMO_CODE_ADJUSTMENT_DROPDOWN_LIST);
  }

  @Test
  public void findPromoCodeAdjustmentsWithoutCacheTestExceptionDataNotFound() throws Exception {

    List<PromoCodeAdjustment> promoCodeAdjustments = new ArrayList<>();

    when(this.cacheService.findCacheByKey(CACHE_KEY_FIND_ACTIVATE, List.class)).thenReturn(null);
    when(this.promoCodeAdjustmentRepository
        .findAllByStoreIdAndPromoCodeAdjustmentStatusAndIsDeleted(CommonTestVariable.STORE_ID,
            PromoCodeAdjustmentStatus.ACTIVE, 0)).thenReturn(promoCodeAdjustments);

    try {
      this.promoCodeAdjustmentService.findPromoCodeAdjustments(CommonTestVariable.MANDATORY_REQUEST)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(this.cacheService).findCacheByKey(CACHE_KEY_FIND_ACTIVATE, List.class);
      verify(this.promoCodeAdjustmentRepository)
          .findAllByStoreIdAndPromoCodeAdjustmentStatusAndIsDeleted(CommonTestVariable.STORE_ID,
              PromoCodeAdjustmentStatus.ACTIVE, 0);

      assertEquals(ResponseCode.PROMO_CODE_ADJUSTMENT_ACTIVE_IS_EMPTY.getCode(), be.getCode());
      assertEquals(ResponseCode.PROMO_CODE_ADJUSTMENT_ACTIVE_IS_EMPTY.getMessage(),
          be.getMessage());
    }

  }

  @Test
  public void findPromoCodeAdjustmentsTestWithCache() throws Exception {

    when(this.cacheService.findCacheByKey(CACHE_KEY_FIND_ACTIVATE, List.class))
        .thenReturn(PROMO_CODE_ADJUSTMENTS);

    List<PromoCodeAdjustmentDropdown> promoCodeAdjustmentList = this.promoCodeAdjustmentService
        .findPromoCodeAdjustments(CommonTestVariable.MANDATORY_REQUEST).blockingGet();

    verify(this.cacheService).findCacheByKey(CACHE_KEY_FIND_ACTIVATE, List.class);

    assertEquals(promoCodeAdjustmentList, PROMO_CODE_ADJUSTMENTS);
  }

  @Test
  public void updateStatusRejectedPromoCodeAdjustmentTestSuccess() throws Exception {
    PROMO_CODE_ADJUSTMENT.setPromoCodeAdjustmentStatus(PromoCodeAdjustmentStatus.PENDING);

    when(promoCodeAdjustmentRepository
        .findPromoCodeAdjustmentByIdAndIsDeleted(ID, 0))
        .thenReturn(PROMO_CODE_ADJUSTMENT);

    when(promoCodeAdjustmentRepository.updateStatus(PROMO_CODE_ADJUSTMENT)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY)).thenReturn(true);
    when(cacheService.createCache(CACHE_KEY, PROMO_CODE_ADJUSTMENT, 0)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY_FIND_ACTIVATE)).thenReturn(true);
    when(promoCodeAdjustmentLogService.createPromoCodeAdjustmentLog(CommonTestVariable.MANDATORY_REQUEST, PROMO_CODE_ADJUSTMENT))
        .thenReturn(Single.just(PROMO_CODE_ADJUSTMENT));

    PromoCodeAdjustment promoCodeAdjustment = promoCodeAdjustmentService
        .updateStatusRejectedPromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST, ID)
        .blockingGet();

    verify(promoCodeAdjustmentRepository).findPromoCodeAdjustmentByIdAndIsDeleted(ID, 0);

    verify(promoCodeAdjustmentRepository).updateStatus(PROMO_CODE_ADJUSTMENT);
    verify(cacheService).deleteCache(CACHE_KEY);
    verify(cacheService).createCache(CACHE_KEY, PROMO_CODE_ADJUSTMENT, 0);
    verify(cacheService).deleteCache(CACHE_KEY_FIND_ACTIVATE);
    verify(promoCodeAdjustmentLogService).createPromoCodeAdjustmentLog(CommonTestVariable.MANDATORY_REQUEST, PROMO_CODE_ADJUSTMENT);

    assertEquals(PROMO_CODE_ADJUSTMENT, promoCodeAdjustment);
  }


  @Test
  public void updateStatusRejectedPromoCodeAdjustmentTestNotDataExist() throws Exception {
    PROMO_CODE_ADJUSTMENT.setPromoCodeAdjustmentStatus(PromoCodeAdjustmentStatus.PENDING);

    when(promoCodeAdjustmentRepository
        .findPromoCodeAdjustmentByIdAndIsDeleted(ID, 0))
        .thenReturn(null);

    try {
      promoCodeAdjustmentService
          .updateStatusRejectedPromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(promoCodeAdjustmentRepository).findPromoCodeAdjustmentByIdAndIsDeleted(ID, 0);

      assertEquals(ResponseCode.DATA_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.DATA_NOT_EXIST.getMessage(), be.getMessage());

    }
  }

  @Test
  public void updateStatusRejectedPromoCodeAdjustmentTestStatusExistingNotActive()
      throws Exception {
    PROMO_CODE_ADJUSTMENT.setPromoCodeAdjustmentStatus(PromoCodeAdjustmentStatus.DRAFT);

    when(promoCodeAdjustmentRepository
        .findPromoCodeAdjustmentByIdAndIsDeleted(ID, 0))
        .thenReturn(PROMO_CODE_ADJUSTMENT);

    try {
      promoCodeAdjustmentService
          .updateStatusRejectedPromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(promoCodeAdjustmentRepository).findPromoCodeAdjustmentByIdAndIsDeleted(ID, 0);

      assertEquals(ResponseCode.PROMO_CODE_ADJUSTMENT_STATUS_NOT_PENDING.getCode(), be.getCode());
      assertEquals(ResponseCode.PROMO_CODE_ADJUSTMENT_STATUS_NOT_PENDING.getMessage(),
          be.getMessage());

    }


  }


  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(promoCodeAdjustmentRepository);
    verifyNoMoreInteractions(cacheService);
  }
}
