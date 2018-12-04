import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.promo.code.dao.api.CampaignRepository;
import com.tl.booking.promo.code.dao.api.PromoCodeAdjustmentRepository;
import com.tl.booking.promo.code.dao.api.PromoCodeRepository;
import com.tl.booking.promo.code.entity.constant.enums.CampaignStatus;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeAdjustmentStatus;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeColumn;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeStatus;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.PromoCodeTestVariable;
import com.tl.booking.promo.code.entity.dao.PromoCode;
import com.tl.booking.promo.code.libraries.exception.BusinessLogicException;
import com.tl.booking.promo.code.service.api.CacheService;
import com.tl.booking.promo.code.service.api.PromoCodeUsageService;
import com.tl.booking.promo.code.service.api.log.PromoCodeLogService;
import com.tl.booking.promo.code.service.impl.PromoCodeServiceImpl;
import io.reactivex.Single;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;

public class PromoCodeServiceImplTest extends PromoCodeTestVariable {

  @InjectMocks
  PromoCodeServiceImpl promoCodeService;

  @Mock
  PromoCodeRepository promoCodeRepository;

  @Mock
  CampaignRepository campaignRepository;

  @Mock
  PromoCodeAdjustmentRepository promoCodeAdjustmentRepository;

  @Mock
  PromoCodeLogService promoCodeLogService;

  @Mock
  CacheService cacheService;

  @Mock
  PromoCodeUsageService promoCodeUsageService;

  @Test
  public void createPromoCodeTestSuccessWithCampaignApproved() throws Exception {
    CAMPAIGN.setCampaignStatus(CampaignStatus.ACTIVE);
    PROMO_CODE_ADJUSTMENT.setPromoCodeAdjustmentStatus(PromoCodeAdjustmentStatus.ACTIVE);

    when(campaignRepository.findOne(CAMPAIGN_ID)).thenReturn(CAMPAIGN);
    when(promoCodeAdjustmentRepository.findPromoCodeAdjustmentById(ADJUSTMENT_ID))
        .thenReturn(PROMO_CODE_ADJUSTMENT);
    when(promoCodeRepository
        .findPromoCodeByStoreIdAndCodeAndIsDeleted(CommonTestVariable.STORE_ID, CODE, 0))
        .thenReturn(null);
    when(promoCodeRepository.save(PROMO_CODE)).thenReturn(PROMO_CODE);
    when(cacheService.createCache(CACHE_KEY_BY_STORE_ID_AND_CODE, PROMO_CODE, 0)).thenReturn(true);
    when(this.cacheService.deleteCache(CACHE_KEY_BY_CAMPAIGN_ID)).thenReturn(true);



    PromoCode promoCode = promoCodeService
        .createPromoCode(CommonTestVariable.MANDATORY_REQUEST, PROMO_CODE).blockingGet();

    verify(campaignRepository).findOne(CAMPAIGN_ID);
    verify(promoCodeAdjustmentRepository).findPromoCodeAdjustmentById(ADJUSTMENT_ID);
    verify(promoCodeRepository)
        .findPromoCodeByStoreIdAndCodeAndIsDeleted(CommonTestVariable.STORE_ID, CODE, 0);
    verify(promoCodeRepository).save(PROMO_CODE);
    verify(cacheService).createCache(CACHE_KEY_BY_STORE_ID_AND_CODE, PROMO_CODE, 0);
    verify(this.cacheService).deleteCache(CACHE_KEY_BY_CAMPAIGN_ID);

    assertEquals(PROMO_CODE, promoCode);
  }

  @Test
  public void createPromoCodeTestSuccessWithCampaignPublished() throws Exception {
    CAMPAIGN.setCampaignStatus(CampaignStatus.ACTIVE);
    PROMO_CODE_ADJUSTMENT.setPromoCodeAdjustmentStatus(PromoCodeAdjustmentStatus.ACTIVE);

    when(campaignRepository.findOne(CAMPAIGN_ID)).thenReturn(CAMPAIGN);
    when(promoCodeAdjustmentRepository.findPromoCodeAdjustmentById(ADJUSTMENT_ID))
        .thenReturn(PROMO_CODE_ADJUSTMENT);
    when(promoCodeRepository
        .findPromoCodeByStoreIdAndCodeAndIsDeleted(CommonTestVariable.STORE_ID, CODE, 0))
        .thenReturn(null);
    when(promoCodeRepository.save(PROMO_CODE)).thenReturn(PROMO_CODE);
    when(cacheService.createCache(CACHE_KEY_BY_STORE_ID_AND_CODE, PROMO_CODE, 0)).thenReturn(true);
    when(this.cacheService.deleteCache(CACHE_KEY_BY_CAMPAIGN_ID)).thenReturn(true);

    PromoCode promoCode = promoCodeService
        .createPromoCode(CommonTestVariable.MANDATORY_REQUEST, PROMO_CODE).blockingGet();

    verify(campaignRepository).findOne(CAMPAIGN_ID);
    verify(promoCodeAdjustmentRepository).findPromoCodeAdjustmentById(ADJUSTMENT_ID);
    verify(promoCodeRepository)
        .findPromoCodeByStoreIdAndCodeAndIsDeleted(CommonTestVariable.STORE_ID, CODE, 0);
    verify(promoCodeRepository).save(PROMO_CODE);
    verify(cacheService).createCache(CACHE_KEY_BY_STORE_ID_AND_CODE, PROMO_CODE, 0);
    verify(this.cacheService).deleteCache(CACHE_KEY_BY_CAMPAIGN_ID);

    assertEquals(PROMO_CODE, promoCode);
  }

  @Test
  public void createPromoCodeTestExceptionCampaignNotExist() throws Exception {
    when(campaignRepository.findOne(CAMPAIGN_ID)).thenReturn(null);

    try {
      promoCodeService.createPromoCode(CommonTestVariable.MANDATORY_REQUEST, PROMO_CODE)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(campaignRepository).findOne(CAMPAIGN_ID);

      assertEquals(ResponseCode.CAMPAIGN_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.CAMPAIGN_NOT_EXIST.getMessage(), be.getMessage());
    }
  }

  @Test
  public void createPromoCodeTestExceptionAdjustmentNotExist() throws Exception {
    CAMPAIGN.setCampaignStatus(CampaignStatus.ACTIVE);
    when(campaignRepository.findOne(CAMPAIGN_ID)).thenReturn(CAMPAIGN);
    when(promoCodeAdjustmentRepository.findPromoCodeAdjustmentById(ADJUSTMENT_ID))
        .thenReturn(null);

    try {
      promoCodeService.createPromoCode(CommonTestVariable.MANDATORY_REQUEST, PROMO_CODE)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(campaignRepository).findOne(CAMPAIGN_ID);
      verify(promoCodeAdjustmentRepository).findPromoCodeAdjustmentById(ADJUSTMENT_ID);

      assertEquals(ResponseCode.ADJUSTMENT_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.ADJUSTMENT_NOT_EXIST.getMessage(), be.getMessage());
    }
  }

  @Test
  public void createPromoCodeTestExceptionAdjustmentNotActive() throws Exception {
    CAMPAIGN.setCampaignStatus(CampaignStatus.ACTIVE);
    PROMO_CODE_ADJUSTMENT.setPromoCodeAdjustmentStatus(PromoCodeAdjustmentStatus.INACTIVE);
    when(campaignRepository.findOne(CAMPAIGN_ID)).thenReturn(CAMPAIGN);
    when(promoCodeAdjustmentRepository.findPromoCodeAdjustmentById(ADJUSTMENT_ID))
        .thenReturn(PROMO_CODE_ADJUSTMENT);

    try {
      promoCodeService.createPromoCode(CommonTestVariable.MANDATORY_REQUEST, PROMO_CODE)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(campaignRepository).findOne(CAMPAIGN_ID);
      verify(promoCodeAdjustmentRepository).findPromoCodeAdjustmentById(ADJUSTMENT_ID);

      assertEquals(ResponseCode.ADJUSTMENT_NOT_ACTIVE.getCode(), be.getCode());
      assertEquals(ResponseCode.ADJUSTMENT_NOT_ACTIVE.getMessage(), be.getMessage());
    }
  }

  @Test
  public void createPromoCodeTestExceptionPromoCodeExist() throws Exception {
    CAMPAIGN.setCampaignStatus(CampaignStatus.ACTIVE);
    PROMO_CODE_ADJUSTMENT.setPromoCodeAdjustmentStatus(PromoCodeAdjustmentStatus.ACTIVE);

    when(campaignRepository.findOne(CAMPAIGN_ID)).thenReturn(CAMPAIGN);
    when(promoCodeAdjustmentRepository.findPromoCodeAdjustmentById(ADJUSTMENT_ID))
        .thenReturn(PROMO_CODE_ADJUSTMENT);
    when(promoCodeRepository
        .findPromoCodeByStoreIdAndCodeAndIsDeleted(CommonTestVariable.STORE_ID, CODE, 0))
        .thenReturn(PROMO_CODE_EXIST);
    when(campaignRepository.findOne(CAMPAIGN_ID_EXIST))
        .thenReturn(CAMPAIGN);

    try {
      promoCodeService.createPromoCode(CommonTestVariable.MANDATORY_REQUEST, PROMO_CODE)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(campaignRepository).findOne(CAMPAIGN_ID);
      verify(promoCodeAdjustmentRepository).findPromoCodeAdjustmentById(ADJUSTMENT_ID);
      verify(promoCodeRepository)
          .findPromoCodeByStoreIdAndCodeAndIsDeleted(CommonTestVariable.STORE_ID, CODE, 0);
      verify(campaignRepository).findOne(CAMPAIGN_ID_EXIST);

      assertEquals(ResponseCode.PROMO_CODE_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.PROMO_CODE_EXIST.getMessage(), be.getMessage());
    }
  }

  @Test
  public void createPromoCodeTestExceptionSystemError() throws Exception {
    CAMPAIGN.setCampaignStatus(CampaignStatus.ACTIVE);
    PROMO_CODE_ADJUSTMENT.setPromoCodeAdjustmentStatus(PromoCodeAdjustmentStatus.ACTIVE);

    when(campaignRepository.findOne(CAMPAIGN_ID)).thenReturn(CAMPAIGN);
    when(promoCodeAdjustmentRepository.findPromoCodeAdjustmentById(ADJUSTMENT_ID))
        .thenReturn(PROMO_CODE_ADJUSTMENT);
    when(promoCodeRepository
        .findPromoCodeByStoreIdAndCodeAndIsDeleted(CommonTestVariable.STORE_ID, CODE, 0))
        .thenReturn(null);
    when(promoCodeRepository.save(PROMO_CODE)).thenReturn(null);
    when(cacheService.createCache(CACHE_KEY, PROMO_CODE, 0)).thenReturn(true);

    try {
      promoCodeService.createPromoCode(CommonTestVariable.MANDATORY_REQUEST, PROMO_CODE)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(campaignRepository).findOne(CAMPAIGN_ID);
      verify(promoCodeAdjustmentRepository).findPromoCodeAdjustmentById(ADJUSTMENT_ID);
      verify(promoCodeRepository)
          .findPromoCodeByStoreIdAndCodeAndIsDeleted(CommonTestVariable.STORE_ID, CODE, 0);
      verify(promoCodeRepository).save(PROMO_CODE);

      assertEquals(ResponseCode.SYSTEM_ERROR.getCode(), be.getCode());
      assertEquals(ResponseCode.SYSTEM_ERROR.getMessage(), be.getMessage());
    }
  }

  @Test
  public void updatePromoCodeTestSuccess() throws Exception {
    CAMPAIGN.setCampaignStatus(CampaignStatus.ACTIVE);
    PROMO_CODE_ADJUSTMENT.setPromoCodeAdjustmentStatus(PromoCodeAdjustmentStatus.ACTIVE);

    when(campaignRepository.findOne(CAMPAIGN_ID)).thenReturn(CAMPAIGN);
    when(promoCodeAdjustmentRepository.findPromoCodeAdjustmentById(ADJUSTMENT_ID))
        .thenReturn(PROMO_CODE_ADJUSTMENT);
    when(promoCodeRepository
        .findPromoCodeByIdAndIsDeleted(ID, 0))
        .thenReturn(PROMO_CODE);
    when(promoCodeRepository.save(PROMO_CODE)).thenReturn(PROMO_CODE);
    when(cacheService.deleteCache(CACHE_KEY_BY_STORE_ID_AND_CODE)).thenReturn(true);
    when(cacheService.createCache(CACHE_KEY_BY_STORE_ID_AND_CODE, PROMO_CODE, 0)).thenReturn(true);
    when(this.cacheService.deleteCache(CACHE_KEY_BY_CAMPAIGN_ID)).thenReturn(true);

    when(promoCodeLogService.createPromoCodeLog(CommonTestVariable.MANDATORY_REQUEST, PROMO_CODE))
        .thenReturn(
            Single.just(PROMO_CODE));

    PromoCode promoCode = promoCodeService
        .updatePromoCode(CommonTestVariable.MANDATORY_REQUEST, PROMO_CODE, ID)
        .blockingGet();

    verify(campaignRepository).findOne(CAMPAIGN_ID);
    verify(promoCodeAdjustmentRepository).findPromoCodeAdjustmentById(ADJUSTMENT_ID);
    verify(promoCodeRepository).findPromoCodeByIdAndIsDeleted(ID, 0);
    verify(promoCodeRepository).save(PROMO_CODE);
    verify(cacheService).deleteCache(CACHE_KEY_BY_STORE_ID_AND_CODE);
    verify(cacheService).deleteCache(CACHE_KEY);
    verify(cacheService).createCache(CACHE_KEY_BY_STORE_ID_AND_CODE, PROMO_CODE, 0);
    verify(this.cacheService).deleteCache(CACHE_KEY_BY_CAMPAIGN_ID);

    verify(promoCodeLogService)
        .createPromoCodeLog(CommonTestVariable.MANDATORY_REQUEST, PROMO_CODE);

    assertEquals(PROMO_CODE, promoCode);
  }

  @Test
  public void createPromoCodeTestInvalidCharacter() throws Exception {
    try {
      promoCodeService.createPromoCode(CommonTestVariable.MANDATORY_REQUEST, PROMO_CODE_SPACE)
          .blockingGet();
    } catch (BusinessLogicException be) {

      assertEquals(ResponseCode.PROMO_CODE_INVALID_CHARACTER.getCode(), be.getCode());
      assertEquals(ResponseCode.PROMO_CODE_INVALID_CHARACTER.getMessage(), be.getMessage());
    }
  }

  @Test
  public void updatePromoCodeTestExceptionPromoCodeNotExist() throws Exception {
    CAMPAIGN.setCampaignStatus(CampaignStatus.ACTIVE);
    PROMO_CODE_ADJUSTMENT.setPromoCodeAdjustmentStatus(PromoCodeAdjustmentStatus.ACTIVE);

    when(campaignRepository.findOne(CAMPAIGN_ID)).thenReturn(CAMPAIGN);
    when(promoCodeAdjustmentRepository.findPromoCodeAdjustmentById(ADJUSTMENT_ID))
        .thenReturn(PROMO_CODE_ADJUSTMENT);
    when(promoCodeRepository
        .findPromoCodeByIdAndIsDeleted(ID, 0))
        .thenReturn(null);
    try {
      promoCodeService
          .updatePromoCode(CommonTestVariable.MANDATORY_REQUEST, PROMO_CODE, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {

      verify(campaignRepository).findOne(CAMPAIGN_ID);
      verify(promoCodeAdjustmentRepository).findPromoCodeAdjustmentById(ADJUSTMENT_ID);
      verify(promoCodeRepository).findPromoCodeByIdAndIsDeleted(ID, 0);

      assertEquals(ResponseCode.PROMO_CODE_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.PROMO_CODE_NOT_EXIST.getMessage(), be.getMessage());
    }
  }

  @Test
  public void UpdatePromoCodeTestInvalidCharacter() throws Exception {
    try {
      promoCodeService.updatePromoCode(CommonTestVariable.MANDATORY_REQUEST, PROMO_CODE_SPACE, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {

      assertEquals(ResponseCode.PROMO_CODE_INVALID_CHARACTER.getCode(), be.getCode());
      assertEquals(ResponseCode.PROMO_CODE_INVALID_CHARACTER.getMessage(), be.getMessage());
    }
  }

  @Test
  public void updatePromoCodeTestFailedSaveExceptionSystemError() throws Exception {
    CAMPAIGN.setCampaignStatus(CampaignStatus.ACTIVE);
    PROMO_CODE_ADJUSTMENT.setPromoCodeAdjustmentStatus(PromoCodeAdjustmentStatus.ACTIVE);

    when(campaignRepository.findOne(CAMPAIGN_ID)).thenReturn(CAMPAIGN);
    when(promoCodeAdjustmentRepository.findPromoCodeAdjustmentById(ADJUSTMENT_ID))
        .thenReturn(PROMO_CODE_ADJUSTMENT);
    when(promoCodeRepository.findPromoCodeByIdAndIsDeleted(ID, 0))
        .thenReturn(PROMO_CODE);
    when(promoCodeRepository.save(PROMO_CODE)).thenReturn(null);

    try {
      promoCodeService
          .updatePromoCode(CommonTestVariable.MANDATORY_REQUEST, PROMO_CODE, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {

      verify(campaignRepository).findOne(CAMPAIGN_ID);
      verify(promoCodeAdjustmentRepository).findPromoCodeAdjustmentById(ADJUSTMENT_ID);
      verify(promoCodeRepository).findPromoCodeByIdAndIsDeleted(ID, 0);
      verify(promoCodeRepository).save(PROMO_CODE);

      assertEquals(ResponseCode.SYSTEM_ERROR.getCode(), be.getCode());
      assertEquals(ResponseCode.SYSTEM_ERROR.getMessage(), be.getMessage());
    }
  }

  @Test
  public void deletePromoCodeTestSuccess() throws Exception {
    when(promoCodeRepository.findPromoCodeByIdAndIsDeleted(ID, 0))
        .thenReturn(PROMO_CODE);
    when(promoCodeRepository.softDeleted(PROMO_CODE, ID)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY_BY_STORE_ID_AND_CODE)).thenReturn(true);
    when(this.cacheService.deleteCache(CACHE_KEY_BY_CAMPAIGN_ID)).thenReturn(true);

    Boolean deleted = promoCodeService
        .deletePromoCode(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();

    verify(promoCodeRepository).findPromoCodeByIdAndIsDeleted(ID, 0);
    verify(promoCodeRepository).softDeleted(PROMO_CODE, ID);
    verify(cacheService).deleteCache(CACHE_KEY_BY_STORE_ID_AND_CODE);
    verify(cacheService).deleteCache(CACHE_KEY);
    verify(this.cacheService).deleteCache(CACHE_KEY_BY_CAMPAIGN_ID);

    assertEquals(true, deleted);
  }

  @Test
  public void deletePromoCodeTestExceptionPromoCodeNotExist() throws Exception {
    when(promoCodeRepository.findPromoCodeByIdAndIsDeleted(ID, 0))
        .thenReturn(null);

    try {
      promoCodeService.deletePromoCode(CommonTestVariable.MANDATORY_REQUEST, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(promoCodeRepository).findPromoCodeByIdAndIsDeleted(ID, 0);

      assertEquals(ResponseCode.PROMO_CODE_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.PROMO_CODE_NOT_EXIST.getMessage(), be.getMessage());
    }
  }

  @Test
  public void deletePromoCodeTestFailedSaveExceptionSystemError() throws Exception {
    when(promoCodeRepository.findPromoCodeByIdAndIsDeleted(ID, 0))
        .thenReturn(PROMO_CODE);
    when(promoCodeRepository.softDeleted(PROMO_CODE, ID)).thenReturn(false);

    try {
      promoCodeService.deletePromoCode(CommonTestVariable.MANDATORY_REQUEST, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(promoCodeRepository).findPromoCodeByIdAndIsDeleted(ID, 0);
      verify(promoCodeRepository).softDeleted(PROMO_CODE, ID);

      assertEquals(ResponseCode.SYSTEM_ERROR.getCode(), be.getCode());
      assertEquals(ResponseCode.SYSTEM_ERROR.getMessage(), be.getMessage());
    }
  }

  @Test
  public void findPromoCodeByIdTestCacheResult() throws Exception {
    when(cacheService.findCacheByKey(CACHE_KEY, PromoCode.class))
        .thenReturn(PROMO_CODE);

    PromoCode promoCode = promoCodeService
        .findPromoCodeById(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();

    verify(cacheService).findCacheByKey(CACHE_KEY, PromoCode.class);

    assertEquals(PROMO_CODE, promoCode);
  }

  @Test
  public void findPromoCodeByIdTestNonCacheResult() throws Exception {
    when(cacheService.findCacheByKey(CACHE_KEY, PromoCode.class))
        .thenReturn(null);
    when(promoCodeRepository.findPromoCodeByIdAndIsDeleted(ID, 0))
        .thenReturn(PROMO_CODE);
    when(cacheService.createCache(CACHE_KEY, PROMO_CODE, 0)).thenReturn(true);

    PromoCode promoCode = promoCodeService
        .findPromoCodeById(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();

    verify(cacheService).findCacheByKey(CACHE_KEY, PromoCode.class);
    verify(promoCodeRepository).findPromoCodeByIdAndIsDeleted(ID, 0);
    verify(cacheService).createCache(CACHE_KEY, PROMO_CODE, 0);

    assertEquals(PROMO_CODE, promoCode);
  }

  @Test
  public void findPromoCodeByIdTestExceptionPromoCodeNotExist() throws Exception {
    when(cacheService.findCacheByKey(CACHE_KEY, PromoCode.class))
        .thenReturn(null);
    when(promoCodeRepository.findPromoCodeByIdAndIsDeleted(ID, 0))
        .thenReturn(null);

    try {
      promoCodeService.findPromoCodeById(CommonTestVariable.MANDATORY_REQUEST, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(cacheService).findCacheByKey(CACHE_KEY, PromoCode.class);
      verify(promoCodeRepository).findPromoCodeByIdAndIsDeleted(ID, 0);

      assertEquals(ResponseCode.PROMO_CODE_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.PROMO_CODE_NOT_EXIST.getMessage(), be.getMessage());
    }
  }

  @Test
  public void findPromoCodeFilterPaginatedTest() throws Exception {
    when(promoCodeRepository
        .findPromoCodeFilterPaginated(CommonTestVariable.STORE_ID, CODE, CAMPAIGN_ID,
            PAGE, SIZE, PromoCodeColumn.CODE.getValue(), SortDirection.ASC))
        .thenReturn(PROMO_CODE_PAGE);

    Page<PromoCode> promoCodes = promoCodeService
        .findPromoCodeFilterPaginated(CommonTestVariable.MANDATORY_REQUEST, CODE,
            CAMPAIGN_ID, PAGE, SIZE, PromoCodeColumn.CODE, SortDirection.ASC)
        .blockingGet();

    verify(promoCodeRepository)
        .findPromoCodeFilterPaginated(CommonTestVariable.STORE_ID, CODE, CAMPAIGN_ID,
            PAGE, SIZE, PromoCodeColumn.CODE.getValue(), SortDirection.ASC);

    assertEquals(PROMO_CODE_PAGE, promoCodes);
  }


  @Test
  public void updateStatusActivedPromoCodeTestSuccess() throws Exception {
    CAMPAIGN.setCampaignStatus(CampaignStatus.ACTIVE);
    PROMO_CODE_ADJUSTMENT.setPromoCodeAdjustmentStatus(PromoCodeAdjustmentStatus.ACTIVE);
    PROMO_CODE.setPromoCodeStatus(PromoCodeStatus.INACTIVE);

    when(promoCodeRepository
        .findPromoCodeByIdAndIsDeleted(ID, 0))
        .thenReturn(PROMO_CODE);
    when(campaignRepository.findOne(CAMPAIGN_ID)).thenReturn(CAMPAIGN);
    when(promoCodeAdjustmentRepository.findPromoCodeAdjustmentById(ADJUSTMENT_ID))
        .thenReturn(PROMO_CODE_ADJUSTMENT);
    when(promoCodeRepository.updateStatus(PROMO_CODE)).thenReturn(true);
    when(cacheService.createCache(CACHE_KEY_BY_STORE_ID_AND_CODE, PROMO_CODE, 0)).thenReturn(true);
    when(this.cacheService.deleteCache(CACHE_KEY_BY_CAMPAIGN_ID)).thenReturn(true);
    when(promoCodeLogService.createPromoCodeLog(CommonTestVariable.MANDATORY_REQUEST, PROMO_CODE))
        .thenReturn(Single.just(PROMO_CODE));

    PromoCode promoCode = promoCodeService
        .updateStatusActivedPromoCode(CommonTestVariable.MANDATORY_REQUEST, ID)
        .blockingGet();

    verify(promoCodeRepository).findPromoCodeByIdAndIsDeleted(ID, 0);
    verify(campaignRepository).findOne(CAMPAIGN_ID);
    verify(promoCodeAdjustmentRepository).findPromoCodeAdjustmentById(ADJUSTMENT_ID);
    verify(promoCodeRepository).updateStatus(PROMO_CODE);
    verify(cacheService).deleteCache(CACHE_KEY_BY_STORE_ID_AND_CODE);
    verify(cacheService).deleteCache(CACHE_KEY);
    verify(cacheService).createCache(CACHE_KEY_BY_STORE_ID_AND_CODE, PROMO_CODE, 0);
    verify(this.cacheService).deleteCache(CACHE_KEY_BY_CAMPAIGN_ID);
    verify(promoCodeLogService).createPromoCodeLog(CommonTestVariable.MANDATORY_REQUEST, PROMO_CODE);

    assertEquals(PROMO_CODE, promoCode);
  }

  @Test
  public void updateStatusActivedPromoCodeTestDataNotExist() throws Exception {
    when(promoCodeRepository
        .findPromoCodeByIdAndIsDeleted(ID, 0))
        .thenReturn(null);

    try {
      promoCodeService.updateStatusActivedPromoCode(CommonTestVariable.MANDATORY_REQUEST, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(promoCodeRepository).findPromoCodeByIdAndIsDeleted(ID, 0);

      assertEquals(ResponseCode.PROMO_CODE_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.PROMO_CODE_NOT_EXIST.getMessage(), be.getMessage());
    }
  }

  @Test
  public void updateStatusActivedPromoCodeTestCampaignNotExist() throws Exception {
    PROMO_CODE.setPromoCodeStatus(PromoCodeStatus.INACTIVE);
    when(promoCodeRepository
        .findPromoCodeByIdAndIsDeleted(ID, 0))
        .thenReturn(PROMO_CODE);
    when(campaignRepository.findOne(CAMPAIGN_ID)).thenReturn(null);

    try {
      promoCodeService.updateStatusActivedPromoCode(CommonTestVariable.MANDATORY_REQUEST, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(promoCodeRepository).findPromoCodeByIdAndIsDeleted(ID, 0);
      verify(campaignRepository).findOne(CAMPAIGN_ID);

      assertEquals(ResponseCode.CAMPAIGN_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.CAMPAIGN_NOT_EXIST.getMessage(), be.getMessage());
    }
  }

  @Test
  public void updateStatusActivePromoCodeTestStatusExistingNotInActive() throws Exception {
    PROMO_CODE.setPromoCodeStatus(PromoCodeStatus.ACTIVE);

    when(promoCodeRepository
        .findPromoCodeByIdAndIsDeleted(ID, 0))
        .thenReturn(PROMO_CODE);

    try {
      promoCodeService.updateStatusActivedPromoCode(CommonTestVariable.MANDATORY_REQUEST, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(promoCodeRepository).findPromoCodeByIdAndIsDeleted(ID, 0);

      assertEquals(ResponseCode.PROMO_CODE_STATUS_NOT_IN_ACTIVE.getCode(), be.getCode());
      assertEquals(ResponseCode.PROMO_CODE_STATUS_NOT_IN_ACTIVE.getMessage(), be.getMessage());

    }


  }


  @Test
  public void updateStatusInActivedPromoCodeTestSuccess() throws Exception {
    PROMO_CODE.setPromoCodeStatus(PromoCodeStatus.ACTIVE);

    when(promoCodeRepository
        .findPromoCodeByIdAndIsDeleted(ID, 0))
        .thenReturn(PROMO_CODE);
    when(promoCodeRepository.updateStatus(PROMO_CODE)).thenReturn(true);
    when(cacheService.createCache(CACHE_KEY_BY_STORE_ID_AND_CODE, PROMO_CODE, 0)).thenReturn(true);
    when(this.cacheService.deleteCache(CACHE_KEY_BY_CAMPAIGN_ID)).thenReturn(true);
    when(promoCodeLogService.createPromoCodeLog(CommonTestVariable.MANDATORY_REQUEST, PROMO_CODE))
        .thenReturn(Single.just(PROMO_CODE));

    PromoCode promoCode = promoCodeService
        .updateStatusInActivedPromoCode(CommonTestVariable.MANDATORY_REQUEST, ID)
        .blockingGet();

    verify(promoCodeRepository).findPromoCodeByIdAndIsDeleted(ID, 0);

    verify(promoCodeRepository).updateStatus(PROMO_CODE);

    verify(cacheService).deleteCache(CACHE_KEY_BY_STORE_ID_AND_CODE);
    verify(cacheService).deleteCache(CACHE_KEY);
    verify(cacheService).createCache(CACHE_KEY_BY_STORE_ID_AND_CODE, PROMO_CODE, 0);
    verify(cacheService).deleteCache(CACHE_KEY_BY_CAMPAIGN_ID);
    verify(promoCodeLogService).createPromoCodeLog(CommonTestVariable.MANDATORY_REQUEST, PROMO_CODE);

    assertEquals(PROMO_CODE, promoCode);
  }

  @Test
  public void updateStatusInActivedPromoCodeTestDataNotExist() throws Exception {
    when(promoCodeRepository
        .findPromoCodeByIdAndIsDeleted(ID, 0))
        .thenReturn(null);

    try {
      promoCodeService.updateStatusInActivedPromoCode(CommonTestVariable.MANDATORY_REQUEST, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(promoCodeRepository).findPromoCodeByIdAndIsDeleted(ID, 0);

      assertEquals(ResponseCode.PROMO_CODE_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.PROMO_CODE_NOT_EXIST.getMessage(), be.getMessage());
    }
  }

  @Test
  public void updateStatusInActivedPromoCodeTestExceptionError() throws Exception {
    PROMO_CODE.setPromoCodeStatus(PromoCodeStatus.ACTIVE);
    when(promoCodeRepository
        .findPromoCodeByIdAndIsDeleted(ID, 0))
        .thenReturn(PROMO_CODE);

    when(promoCodeRepository.updateStatus(PROMO_CODE)).thenReturn(false);

    try {
      promoCodeService.updateStatusInActivedPromoCode(CommonTestVariable.MANDATORY_REQUEST, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(promoCodeRepository).findPromoCodeByIdAndIsDeleted(ID, 0);

      verify(promoCodeRepository).updateStatus(PROMO_CODE);

      assertEquals(ResponseCode.SYSTEM_ERROR.getCode(), be.getCode());
      assertEquals(ResponseCode.SYSTEM_ERROR.getMessage(), be.getMessage());
    }
  }

  @Test
  public void updateStatusInActivePromoCodeTestStatusExistingNotActive() throws Exception {
    PROMO_CODE.setPromoCodeStatus(PromoCodeStatus.INACTIVE);

    when(promoCodeRepository
        .findPromoCodeByIdAndIsDeleted(ID, 0))
        .thenReturn(PROMO_CODE);

    try {
      promoCodeService.updateStatusInActivedPromoCode(CommonTestVariable.MANDATORY_REQUEST, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(promoCodeRepository).findPromoCodeByIdAndIsDeleted(ID, 0);

      assertEquals(ResponseCode.PROMO_CODE_STATUS_NOT_ACTIVE.getCode(), be.getCode());
      assertEquals(ResponseCode.PROMO_CODE_STATUS_NOT_ACTIVE.getMessage(), be.getMessage());

    }


  }

  @Test
  public void findPromoCodeByCodeTestSuccessWithCache() throws Exception {
    PROMO_CODE.setCode(CODE);

    when(cacheService.findCacheByKey(CACHE_KEY_BY_STORE_ID_AND_CODE, PromoCode.class))
        .thenReturn(PROMO_CODE);

    PromoCode promoCode = promoCodeService
        .findPromoCodeByCode(CommonTestVariable.MANDATORY_REQUEST, CODE).blockingGet();

    verify(cacheService).findCacheByKey(CACHE_KEY_BY_STORE_ID_AND_CODE, PromoCode.class);

    assertEquals(PROMO_CODE, promoCode);
  }

  @Test
  public void findPromoCodeByCodeTestSuccessNoCache() throws Exception {
    PROMO_CODE.setCode(CODE);

    when(cacheService.findCacheByKey(CACHE_KEY_BY_STORE_ID_AND_CODE, PromoCode.class))
        .thenReturn(null);

    when(promoCodeRepository
        .findPromoCodeByStoreIdAndCodeAndIsDeleted(CommonTestVariable.STORE_ID, CODE, 0))
        .thenReturn(PROMO_CODE);

    when(cacheService.createCache(CACHE_KEY_BY_STORE_ID_AND_CODE, PROMO_CODE, 0)).thenReturn(true);

    PromoCode promoCode = promoCodeService
        .findPromoCodeByCode(CommonTestVariable.MANDATORY_REQUEST, CODE).blockingGet();

    verify(cacheService).findCacheByKey(CACHE_KEY_BY_STORE_ID_AND_CODE, PromoCode.class);
    verify(promoCodeRepository)
        .findPromoCodeByStoreIdAndCodeAndIsDeleted(CommonTestVariable.STORE_ID, CODE, 0);
    verify(cacheService).createCache(CACHE_KEY_BY_STORE_ID_AND_CODE, PROMO_CODE, 0);

    assertEquals(PROMO_CODE, promoCode);
  }

  @Test
  public void findPromoCodeByCodeTestNoData() throws Exception {
    PROMO_CODE.setCode(CODE);

    when(cacheService.findCacheByKey(CACHE_KEY_BY_STORE_ID_AND_CODE, PromoCode.class))
        .thenReturn(null);

    when(promoCodeRepository
        .findPromoCodeByStoreIdAndCodeAndIsDeleted(CommonTestVariable.STORE_ID, CODE, 0))
        .thenReturn(null);

    try {
      promoCodeService
          .findPromoCodeByCode(CommonTestVariable.MANDATORY_REQUEST, CODE).blockingGet();
    } catch (BusinessLogicException be) {

      verify(cacheService).findCacheByKey(CACHE_KEY_BY_STORE_ID_AND_CODE, PromoCode.class);
      verify(promoCodeRepository)
          .findPromoCodeByStoreIdAndCodeAndIsDeleted(CommonTestVariable.STORE_ID, CODE, 0);

      assertEquals(ResponseCode.PROMO_CODE_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.PROMO_CODE_NOT_EXIST.getMessage(), be.getMessage());
    }
  }

  @Test
  public void findPromoCodeByCodeAndStatusTestSuccessWithCache() throws Exception {
    PROMO_CODE.setCode(CODE);

    when(cacheService.findCacheByKey(CACHE_KEY_BY_STORE_ID_AND_CODE, PromoCode.class))
        .thenReturn(PROMO_CODE);

    PromoCode promoCode = promoCodeService
        .findPromoCodeByCodeAndStatus(CommonTestVariable.MANDATORY_REQUEST, CODE,
            PromoCodeStatus.ACTIVE).blockingGet();

    verify(cacheService).findCacheByKey(CACHE_KEY_BY_STORE_ID_AND_CODE, PromoCode.class);

    assertEquals(PROMO_CODE, promoCode);
  }

  @Test
  public void findPromoCodeByCodeAndStatusTestSuccessNoCache() throws Exception {
    PROMO_CODE.setCode(CODE);

    when(cacheService.findCacheByKey(CACHE_KEY_BY_STORE_ID_AND_CODE, PromoCode.class))
        .thenReturn(null);

    when(promoCodeRepository.findPromoCodeByStoreIdAndCodeAndIsDeletedAndPromoCodeStatus(
        CommonTestVariable.STORE_ID, CODE, 0, PromoCodeStatus.ACTIVE))
        .thenReturn(PROMO_CODE);

    when(cacheService.createCache(CACHE_KEY_BY_STORE_ID_AND_CODE, PROMO_CODE, 0)).thenReturn(true);

    PromoCode promoCode = promoCodeService
        .findPromoCodeByCodeAndStatus(CommonTestVariable.MANDATORY_REQUEST, CODE,
            PromoCodeStatus.ACTIVE).blockingGet();

    verify(cacheService).findCacheByKey(CACHE_KEY_BY_STORE_ID_AND_CODE, PromoCode.class);
    verify(promoCodeRepository)
        .findPromoCodeByStoreIdAndCodeAndIsDeletedAndPromoCodeStatus(CommonTestVariable.STORE_ID,
            CODE, 0, PromoCodeStatus.ACTIVE);
    verify(cacheService).createCache(CACHE_KEY_BY_STORE_ID_AND_CODE, PROMO_CODE, 0);

    assertEquals(PROMO_CODE, promoCode);
  }

  @Test
  public void findPromoCodeByCodeAndStatusTestNoData() throws Exception {
    PROMO_CODE.setCode(CODE);

    when(cacheService.findCacheByKey(CACHE_KEY_BY_STORE_ID_AND_CODE, PromoCode.class))
        .thenReturn(null);

    when(promoCodeRepository
        .findPromoCodeByStoreIdAndCodeAndIsDeletedAndPromoCodeStatus(
            CommonTestVariable.STORE_ID, CODE, 0, PromoCodeStatus.ACTIVE))
        .thenReturn(null);

    try {
      promoCodeService
          .findPromoCodeByCodeAndStatus(CommonTestVariable.MANDATORY_REQUEST, CODE,
              PromoCodeStatus.ACTIVE).blockingGet();
    } catch (BusinessLogicException be) {

      verify(cacheService).findCacheByKey(CACHE_KEY_BY_STORE_ID_AND_CODE, PromoCode.class);
      verify(promoCodeRepository)
          .findPromoCodeByStoreIdAndCodeAndIsDeletedAndPromoCodeStatus(CommonTestVariable.STORE_ID,
              CODE, 0, PromoCodeStatus.ACTIVE);

      assertEquals(ResponseCode.PROMO_CODE_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.PROMO_CODE_NOT_EXIST.getMessage(), be.getMessage());
    }
  }

  @Test
  public void checkExistAndActivePromoCodeByCampaignsTest() throws Exception {

    when(this.cacheService.findCacheByKey(CACHE_KEY_BY_CAMPAIGN_ID, List.class))
    .thenReturn(null);

    when(this.promoCodeRepository.findAllByStoreIdAndCampaignIdAndIsDeleted(CommonTestVariable.STORE_ID, CAMPAIGN_ID, 0))
    .thenReturn(PROMO_CODE_LIST);

    when(this.cacheService.createCache(CACHE_KEY_BY_CAMPAIGN_ID, PROMO_CODE_LIST, 0)).thenReturn(true);

    Boolean status = this.promoCodeService
        .checkExistAndActivePromoCodeByCampaigns(CommonTestVariable.MANDATORY_REQUEST, CAMPAIGN_LIST, false)
        .blockingGet();

    verify(this.cacheService).findCacheByKey(CACHE_KEY_BY_CAMPAIGN_ID, List.class);
    verify(this.promoCodeRepository).findAllByStoreIdAndCampaignIdAndIsDeleted(CommonTestVariable.STORE_ID, CAMPAIGN_ID, 0);
    verify(this.cacheService).createCache(CACHE_KEY_BY_CAMPAIGN_ID, PROMO_CODE_LIST, 0);


    assertEquals(false, status);
  }

  @Test
  public void checkExistAndActivePromoCodeByCampaignsTestExceptionCannotDelete() throws Exception {

    when(this.cacheService.findCacheByKey(CACHE_KEY_BY_CAMPAIGN_ID, List.class))
        .thenReturn(PROMO_CODE_LIST);

    try{
      this.promoCodeService
          .checkExistAndActivePromoCodeByCampaigns(CommonTestVariable.MANDATORY_REQUEST, CAMPAIGN_LIST, true)
          .blockingGet();
    }
    catch (BusinessLogicException be)
    {
      verify(this.cacheService).findCacheByKey(CACHE_KEY_BY_CAMPAIGN_ID, List.class);

      assertEquals(ResponseCode.CANNOT_DELETE_CAUSE_PROMO_CODE_IS_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.CANNOT_DELETE_CAUSE_PROMO_CODE_IS_EXIST.getMessage(), be.getMessage());
    }

  }

  @Test
  public void checkExistAndActivePromoCodeByCampaignsTestExceptionCannotUpdate() throws Exception {

    when(this.cacheService.findCacheByKey(CACHE_KEY_BY_CAMPAIGN_ID, List.class))
        .thenReturn(PROMO_CODE_LIST_ACTIVE);

    try{
      this.promoCodeService
          .checkExistAndActivePromoCodeByCampaigns(CommonTestVariable.MANDATORY_REQUEST, CAMPAIGN_LIST, false)
          .blockingGet();
    }
    catch (BusinessLogicException be)
    {
      verify(this.cacheService).findCacheByKey(CACHE_KEY_BY_CAMPAIGN_ID, List.class);

      assertEquals(ResponseCode.CANNOT_UPDATE_CAUSE_PROMO_CODE_STATUS_ACTIVE.getCode(), be.getCode());
      assertEquals(ResponseCode.CANNOT_UPDATE_CAUSE_PROMO_CODE_STATUS_ACTIVE.getMessage(), be.getMessage());
    }

  }




  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(promoCodeRepository);
    verifyNoMoreInteractions(campaignRepository);
    verifyNoMoreInteractions(promoCodeAdjustmentRepository);
    verifyNoMoreInteractions(cacheService);
  }
}
