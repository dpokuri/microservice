import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.when;

import com.tl.booking.promo.code.dao.api.CampaignRepository;
import com.tl.booking.promo.code.dao.api.PromoCodeAdjustmentRepository;
import com.tl.booking.promo.code.entity.CampaignDropdown;
import com.tl.booking.promo.code.entity.constant.enums.CampaignColumn;
import com.tl.booking.promo.code.entity.constant.enums.CampaignStatus;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeAdjustmentStatus;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.unit.test.CampaignTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.promo.code.entity.dao.Campaign;
import com.tl.booking.promo.code.libraries.exception.BusinessLogicException;
import com.tl.booking.promo.code.service.api.CacheService;
import com.tl.booking.promo.code.service.api.PromoCodeService;
import com.tl.booking.promo.code.service.api.log.CampaignLogService;
import com.tl.booking.promo.code.service.impl.CampaignServiceImpl;
import io.reactivex.Single;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;

public class CampaignServiceImplTest extends CampaignTestVariable {

  @InjectMocks
  CampaignServiceImpl campaignService;

  @Mock
  CampaignRepository campaignRepository;

  @Mock
  PromoCodeAdjustmentRepository promoCodeAdjustmentRepository;

  @Mock
  CacheService cacheService;

  @Mock
  CampaignLogService campaignLogService;

  @Mock
  PromoCodeService promoCodeService;

  @Test
  public void createCampaignTestSuccess() throws Exception {

    when(campaignRepository
        .findCampaignByStoreIdAndNameAndIsDeleted(CommonTestVariable.STORE_ID, NAME, 0))
        .thenReturn(null);

    when(promoCodeAdjustmentRepository
        .findPromoCodeAdjustmentByIdAndPromoCodeAdjustmentStatusAndIsDeleted(
            ADJUSTMENT_ID, PromoCodeAdjustmentStatus.ACTIVE, 0))
        .thenReturn(PROMO_CODE_ADJUSTMENT_ACTIVE);

    when(promoCodeAdjustmentRepository
        .findPromoCodeAdjustmentByIdAndPromoCodeAdjustmentStatusAndIsDeleted(
            ADJUSTMENT_ID, PromoCodeAdjustmentStatus.ACTIVE, 0)).thenReturn(PROMO_CODE_ADJUSTMENT);

    when(campaignRepository
        .save(CAMPAIGN_REQUEST_CREATE_2)).thenReturn(CAMPAIGN_REQUEST_CREATE_2);

    when(cacheService.createCache(CACHE_KEY, CAMPAIGN_REQUEST_CREATE_2, 0)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY_FIND_ACTIVE)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY_FIND_ADJUSTMENT_ID)).thenReturn(true);


    Campaign campaign = campaignService
        .createCampaign(CommonTestVariable.MANDATORY_REQUEST, CAMPAIGN_REQUEST_CREATE_2)
        .blockingGet();

    verify(campaignRepository)
        .findCampaignByStoreIdAndNameAndIsDeleted(CommonTestVariable.STORE_ID, NAME, 0);
    verify(promoCodeAdjustmentRepository)
        .findPromoCodeAdjustmentByIdAndPromoCodeAdjustmentStatusAndIsDeleted(ADJUSTMENT_ID,
            PromoCodeAdjustmentStatus.ACTIVE, 0);
    verify(campaignRepository).save(CAMPAIGN_REQUEST_CREATE_2);
    verify(cacheService).createCache(CACHE_KEY, CAMPAIGN_REQUEST_CREATE_2, 0);
    verify(cacheService).deleteCache(CACHE_KEY_FIND_ACTIVE);
    verify(cacheService).deleteCache(CACHE_KEY_FIND_ADJUSTMENT_ID);

    assertEquals(CAMPAIGN_RESPONSE, campaign);
  }


  @Test
  public void createCampaignTestErrorExistCampaign() throws Exception {
    when(campaignRepository
        .findCampaignByStoreIdAndNameAndIsDeleted(CommonTestVariable.STORE_ID, NAME, 0))
        .thenReturn(CAMPAIGN_RESPONSE);

    try {
      campaignService.createCampaign(CommonTestVariable.MANDATORY_REQUEST, CAMPAIGN_REQUEST)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(campaignRepository)
          .findCampaignByStoreIdAndNameAndIsDeleted(CommonTestVariable.STORE_ID, NAME, 0);

      assertEquals(ResponseCode.DUPLICATE_DATA.getCode(), be.getCode());
      assertEquals(ResponseCode.DUPLICATE_DATA.getMessage(), be.getMessage());
    }

  }

  @Test
  public void createCampaignTestErrorAdjustmentNotActive() throws Exception {

    when(campaignRepository
        .findCampaignByStoreIdAndNameAndIsDeleted(CommonTestVariable.STORE_ID, NAME, 0))
        .thenReturn(null);

    when(promoCodeAdjustmentRepository
        .findPromoCodeAdjustmentByIdAndPromoCodeAdjustmentStatusAndIsDeleted(
            ADJUSTMENT_ID, PromoCodeAdjustmentStatus.ACTIVE, 0)).thenReturn(null);

    try {
      campaignService.createCampaign(CommonTestVariable.MANDATORY_REQUEST, CAMPAIGN_REQUEST)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(campaignRepository)
          .findCampaignByStoreIdAndNameAndIsDeleted(CommonTestVariable.STORE_ID, NAME, 0);
      verify(promoCodeAdjustmentRepository)
          .findPromoCodeAdjustmentByIdAndPromoCodeAdjustmentStatusAndIsDeleted(ADJUSTMENT_ID,
              PromoCodeAdjustmentStatus.ACTIVE, 0);

      assertEquals(ResponseCode.ADJUSTMENT_STATUS_ACTIVE_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.ADJUSTMENT_STATUS_ACTIVE_NOT_EXIST.getMessage(), be.getMessage());
    }

  }

  @Test
  public void createCampaignTestSystemError() throws Exception {

    when(campaignRepository
        .findCampaignByStoreIdAndNameAndIsDeleted(CommonTestVariable.STORE_ID, NAME, 0))
        .thenReturn(null);

    when(promoCodeAdjustmentRepository
        .findPromoCodeAdjustmentByIdAndPromoCodeAdjustmentStatusAndIsDeleted(
            ADJUSTMENT_ID, PromoCodeAdjustmentStatus.ACTIVE, 0)).thenReturn(PROMO_CODE_ADJUSTMENT);
    when(campaignRepository
        .save(CAMPAIGN_REQUEST)).thenReturn(null);
    try {
      campaignService.createCampaign(CommonTestVariable.MANDATORY_REQUEST, CAMPAIGN_REQUEST)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(campaignRepository)
          .findCampaignByStoreIdAndNameAndIsDeleted(CommonTestVariable.STORE_ID, NAME, 0);
      verify(promoCodeAdjustmentRepository)
          .findPromoCodeAdjustmentByIdAndPromoCodeAdjustmentStatusAndIsDeleted(ADJUSTMENT_ID,
              PromoCodeAdjustmentStatus.ACTIVE, 0);
      verify(campaignRepository).save(CAMPAIGN_REQUEST);

      assertEquals(ResponseCode.SYSTEM_ERROR.getCode(), be.getCode());
      assertEquals(ResponseCode.SYSTEM_ERROR.getMessage(), be.getMessage());
    }

  }

  @Test
  public void updateCampaignTestSuccess() throws Exception {
    PROMO_CODE_ADJUSTMENT.setPromoCodeAdjustmentStatus(PromoCodeAdjustmentStatus.ACTIVE);

    when(this.promoCodeService.findPromoCodeByCampaignId(CommonTestVariable.MANDATORY_REQUEST, ID)).thenReturn(PROMO_CODE_LIST);

    when(promoCodeAdjustmentRepository
        .findPromoCodeAdjustmentByIdAndPromoCodeAdjustmentStatusAndIsDeleted(
            ADJUSTMENT_ID, PromoCodeAdjustmentStatus.ACTIVE, 0)).thenReturn(PROMO_CODE_ADJUSTMENT);

    when(campaignRepository
        .findCampaignByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID,
            CAMPAIGN_REQUEST.getId(), 0)).thenReturn(CAMPAIGN_REQUEST);

    when(campaignRepository
        .save(CAMPAIGN_REQUEST)).thenReturn(CAMPAIGN_RESPONSE);

    when(campaignLogService.createCampaignLog(CommonTestVariable.MANDATORY_REQUEST, CAMPAIGN))
        .thenReturn(
            Single.just(CAMPAIGN));

    when(cacheService.deleteCache(CACHE_KEY)).thenReturn(true);
    when(cacheService.createCache(CACHE_KEY, CAMPAIGN_RESPONSE, 0)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY_FIND_ACTIVE)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY_FIND_ADJUSTMENT_ID)).thenReturn(true);

    when(this.campaignLogService.createCampaignLog(CommonTestVariable.MANDATORY_REQUEST, CAMPAIGN_RESPONSE)).thenReturn(Single.just(CAMPAIGN_RESPONSE));

    Campaign campaign = campaignService
        .updateCampaign(CommonTestVariable.MANDATORY_REQUEST, CAMPAIGN_REQUEST, ID).blockingGet();

    verify(this.promoCodeService).findPromoCodeByCampaignId(CommonTestVariable.MANDATORY_REQUEST, ID);

    verify(promoCodeAdjustmentRepository)
        .findPromoCodeAdjustmentByIdAndPromoCodeAdjustmentStatusAndIsDeleted(ADJUSTMENT_ID,
            PromoCodeAdjustmentStatus.ACTIVE, 0);
    verify(campaignRepository).findCampaignByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID,
        CAMPAIGN_REQUEST.getId(), 0);
    verify(campaignRepository).save(CAMPAIGN_REQUEST);

    verify(campaignLogService).createCampaignLog(CommonTestVariable.MANDATORY_REQUEST, CAMPAIGN);

    verify(cacheService).deleteCache(CACHE_KEY);
    verify(cacheService).createCache(CACHE_KEY, CAMPAIGN_RESPONSE, 0);
    verify(cacheService).deleteCache(CACHE_KEY_FIND_ACTIVE);
    verify(cacheService).deleteCache(CACHE_KEY_FIND_ADJUSTMENT_ID);

    verify(this.campaignLogService).createCampaignLog(CommonTestVariable.MANDATORY_REQUEST, CAMPAIGN_RESPONSE);

    assertEquals(CAMPAIGN_RESPONSE, campaign);
  }


  @Test
  public void updateCampaignTestErrorAdjustmentNotActive() throws Exception {
    when(this.promoCodeService.findPromoCodeByCampaignId(CommonTestVariable.MANDATORY_REQUEST, ID)).thenReturn(PROMO_CODE_LIST);

    when(promoCodeAdjustmentRepository
        .findPromoCodeAdjustmentByIdAndPromoCodeAdjustmentStatusAndIsDeleted(
            ADJUSTMENT_ID, PromoCodeAdjustmentStatus.ACTIVE, 0)).thenReturn(null);

    try {
      campaignService.updateCampaign(CommonTestVariable.MANDATORY_REQUEST, CAMPAIGN_REQUEST, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(this.promoCodeService).findPromoCodeByCampaignId(CommonTestVariable.MANDATORY_REQUEST, ID);

      verify(promoCodeAdjustmentRepository)
          .findPromoCodeAdjustmentByIdAndPromoCodeAdjustmentStatusAndIsDeleted(ADJUSTMENT_ID,
              PromoCodeAdjustmentStatus.ACTIVE, 0);

      assertEquals(ResponseCode.ADJUSTMENT_STATUS_ACTIVE_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.ADJUSTMENT_STATUS_ACTIVE_NOT_EXIST.getMessage(), be.getMessage());
    }

  }

  @Test
  public void updateCampaignTestErrordataNotExist() throws Exception {
    when(this.promoCodeService.findPromoCodeByCampaignId(CommonTestVariable.MANDATORY_REQUEST, ID)).thenReturn(PROMO_CODE_LIST);

    when(promoCodeAdjustmentRepository
        .findPromoCodeAdjustmentByIdAndPromoCodeAdjustmentStatusAndIsDeleted(
            ADJUSTMENT_ID, PromoCodeAdjustmentStatus.ACTIVE, 0)).thenReturn(PROMO_CODE_ADJUSTMENT);
    when(campaignRepository
        .findCampaignByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID,
            CAMPAIGN_REQUEST.getId(), 0)).thenReturn(null);

    try {
      campaignService.updateCampaign(CommonTestVariable.MANDATORY_REQUEST, CAMPAIGN_REQUEST, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(this.promoCodeService).findPromoCodeByCampaignId(CommonTestVariable.MANDATORY_REQUEST, ID);

      verify(promoCodeAdjustmentRepository)
          .findPromoCodeAdjustmentByIdAndPromoCodeAdjustmentStatusAndIsDeleted(ADJUSTMENT_ID,
              PromoCodeAdjustmentStatus.ACTIVE, 0);
      verify(campaignRepository).findCampaignByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID,
          CAMPAIGN_REQUEST.getId(), 0);

      assertEquals(ResponseCode.DATA_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.DATA_NOT_EXIST.getMessage(), be.getMessage());
    }

  }


  @Test
  public void updateCampaignTestErrorNameIsExistInOtherDocument() throws Exception {
    when(this.promoCodeService.findPromoCodeByCampaignId(CommonTestVariable.MANDATORY_REQUEST, ID)).thenReturn(PROMO_CODE_LIST);
    when(promoCodeAdjustmentRepository
        .findPromoCodeAdjustmentByIdAndPromoCodeAdjustmentStatusAndIsDeleted(
            ADJUSTMENT_ID, PromoCodeAdjustmentStatus.ACTIVE, 0)).thenReturn(PROMO_CODE_ADJUSTMENT);
    when(campaignRepository
        .findCampaignByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID,
            CAMPAIGN_REQUEST_UPDATED_NAME.getId(), 0)).thenReturn(CAMPAIGN_REQUEST_UPDATED_NAME);

    when(campaignRepository
        .findCampaignByIdAndNameAndIsDeleted(CAMPAIGN_REQUEST.getStoreId(),
            CAMPAIGN_REQUEST.getName(), 0)).thenReturn(CAMPAIGN_REQUEST);

    try {
      campaignService.updateCampaign(CommonTestVariable.MANDATORY_REQUEST, CAMPAIGN_REQUEST, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(this.promoCodeService).findPromoCodeByCampaignId(CommonTestVariable.MANDATORY_REQUEST, ID);

      verify(promoCodeAdjustmentRepository)
          .findPromoCodeAdjustmentByIdAndPromoCodeAdjustmentStatusAndIsDeleted(ADJUSTMENT_ID,
              PromoCodeAdjustmentStatus.ACTIVE, 0);
      verify(campaignRepository).findCampaignByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID,
          CAMPAIGN_REQUEST_UPDATED_NAME.getId(), 0);
      verify(campaignRepository)
          .findCampaignByIdAndNameAndIsDeleted(CAMPAIGN_REQUEST.getStoreId(),
              CAMPAIGN_REQUEST.getName(), 0);

      assertEquals(ResponseCode.NAME_EXIST_OTHER_RECORD.getCode(), be.getCode());
      assertEquals(ResponseCode.NAME_EXIST_OTHER_RECORD.getMessage(), be.getMessage());
    }

  }


  @Test
  public void updateCampaignTestExceptionSystemError() throws Exception {
    when(this.promoCodeService.findPromoCodeByCampaignId(CommonTestVariable.MANDATORY_REQUEST, ID)).thenReturn(PROMO_CODE_LIST);
    when(promoCodeAdjustmentRepository
        .findPromoCodeAdjustmentByIdAndPromoCodeAdjustmentStatusAndIsDeleted(
            ADJUSTMENT_ID, PromoCodeAdjustmentStatus.ACTIVE, 0)).thenReturn(PROMO_CODE_ADJUSTMENT);
    when(campaignRepository
        .findCampaignByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID,
            CAMPAIGN_REQUEST.getId(), 0)).thenReturn(CAMPAIGN_REQUEST);

    when(campaignRepository
        .save(CAMPAIGN_REQUEST)).thenReturn(null);

    try {
      campaignService.updateCampaign(CommonTestVariable.MANDATORY_REQUEST, CAMPAIGN_REQUEST, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(this.promoCodeService).findPromoCodeByCampaignId(CommonTestVariable.MANDATORY_REQUEST, ID);
      verify(promoCodeAdjustmentRepository)
          .findPromoCodeAdjustmentByIdAndPromoCodeAdjustmentStatusAndIsDeleted(ADJUSTMENT_ID,
              PromoCodeAdjustmentStatus.ACTIVE, 0);
      verify(campaignRepository).findCampaignByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID,
          CAMPAIGN_REQUEST.getId(), 0);
      verify(campaignRepository).save(CAMPAIGN_REQUEST);

      assertEquals(ResponseCode.SYSTEM_ERROR.getCode(), be.getCode());
      assertEquals(ResponseCode.SYSTEM_ERROR.getMessage(), be.getMessage());
    }

  }


  @Test
  public void deleteCampaignTestSuccess() throws Exception {
    CAMPAIGN_DELETE.setIsDeleted(1);

    when(this.promoCodeService.findPromoCodeByCampaignId(CommonTestVariable.MANDATORY_REQUEST, ID)).thenReturn(PROMO_CODE_LIST_NULL);

    when(campaignRepository
        .findCampaignByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID,
            CAMPAIGN_DELETE.getId(), 0)).thenReturn(CAMPAIGN_DELETE);

    when(campaignRepository.softDeleted(CAMPAIGN_DELETE, CAMPAIGN_DELETE.getId())).thenReturn(true);

    when(cacheService.deleteCache(CACHE_KEY)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY_FIND_ACTIVE)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY_FIND_ADJUSTMENT_ID)).thenReturn(true);

    Boolean campaign = campaignService.deleteCampaign(CommonTestVariable.MANDATORY_REQUEST, ID)
        .blockingGet();
    verify(this.promoCodeService).findPromoCodeByCampaignId(CommonTestVariable.MANDATORY_REQUEST, ID);
    verify(campaignRepository).findCampaignByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID,
        CAMPAIGN_DELETE.getId(), 0);
    verify(campaignRepository).softDeleted(CAMPAIGN_DELETE, CAMPAIGN_DELETE.getId());
    verify(cacheService).deleteCache(CACHE_KEY);
    verify(cacheService).deleteCache(CACHE_KEY_FIND_ACTIVE);
    verify(cacheService).deleteCache(CACHE_KEY_FIND_ADJUSTMENT_ID);

    assertEquals(true, campaign);
  }

  @Test
  public void findCampaignPaginatedTestSuccess() throws Exception {
    when(campaignRepository
        .findCampaignFilterPaginated(CommonTestVariable.STORE_ID, NAME,
            CampaignStatus.DRAFT.getCode(), PAGE,
            SIZE, CampaignColumn.ID.getValue(), SortDirection.ASC)).thenReturn(CAMPAIGN_PAGE);

    Page<Campaign> campaignPage = campaignService
        .findCampaignFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
            NAME, CampaignStatus.DRAFT, PAGE, SIZE, CampaignColumn.ID,
            SortDirection.ASC).blockingGet();

    verify(campaignRepository)
        .findCampaignFilterPaginated(CommonTestVariable.STORE_ID, NAME,
            CampaignStatus.DRAFT.getCode(), PAGE,
            SIZE, CampaignColumn.ID.getValue(), SortDirection.ASC);

    assertEquals(CAMPAIGN_PAGE, campaignPage);
  }

  @Test
  public void deleteCampaignTestDataNotExist() throws Exception {

    when(campaignRepository
        .findCampaignByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID,
            CAMPAIGN_DELETE.getId(), 0)).thenReturn(null);

    try {
      campaignService.deleteCampaign(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();
    } catch (BusinessLogicException be) {
      verify(campaignRepository).findCampaignByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID,
          CAMPAIGN_DELETE.getId(), 0);

      assertEquals(ResponseCode.CAMPAIGN_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.CAMPAIGN_NOT_EXIST.getMessage(), be.getMessage());
    }
  }

  @Test
  public void findCampaignByIdTestCacheResult() throws Exception {
    when(cacheService.findCacheByKey(CACHE_KEY, Campaign.class))
        .thenReturn(CAMPAIGN_FIND);

    Campaign campaign = campaignService
        .findCampaignById(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();

    verify(cacheService).findCacheByKey(CACHE_KEY, Campaign.class);

    assertEquals(CAMPAIGN_FIND, campaign);
  }

  @Test
  public void findCampaignByIdTestNonCacheResult() throws Exception {
    when(cacheService.findCacheByKey(CACHE_KEY, Campaign.class))
        .thenReturn(null);
    when(campaignRepository
        .findCampaignByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(CAMPAIGN_REQUEST);
    when(cacheService.createCache(CACHE_KEY, CAMPAIGN_REQUEST, 0)).thenReturn(true);

    Campaign campaign = campaignService
        .findCampaignById(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();

    verify(cacheService).findCacheByKey(CACHE_KEY, Campaign.class);
    verify(campaignRepository)
        .findCampaignByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);
    verify(cacheService).createCache(CACHE_KEY, CAMPAIGN_REQUEST, 0);

    assertEquals(CAMPAIGN_REQUEST, campaign);
  }

  @Test
  public void findCampaignByIdTestExceptionCampaignNotExist() throws Exception {
    when(cacheService.findCacheByKey(CACHE_KEY, Campaign.class))
        .thenReturn(null);
    when(campaignRepository
        .findCampaignByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(null);

    try {
      campaignService.findCampaignById(CommonTestVariable.MANDATORY_REQUEST, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(cacheService).findCacheByKey(CACHE_KEY, Campaign.class);
      verify(campaignRepository)
          .findCampaignByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);

      assertEquals(ResponseCode.NO_CAMPAIGN_EXISTED.getCode(), be.getCode());
      assertEquals(ResponseCode.NO_CAMPAIGN_EXISTED.getMessage(), be.getMessage());
    }
  }

  @Test
  public void updateStatusPendingPromoCodeAdjustmentTestSuccess() throws Exception {
    CAMPAIGN_REQUEST.setCampaignStatus(CampaignStatus.DRAFT);

    when(campaignRepository
        .findCampaignByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(CAMPAIGN_REQUEST);

    when(promoCodeAdjustmentRepository
        .findPromoCodeAdjustmentByIdAndPromoCodeAdjustmentStatusAndIsDeleted(
            ADJUSTMENT_ID, PromoCodeAdjustmentStatus.ACTIVE, 0)).thenReturn(PROMO_CODE_ADJUSTMENT);

    when(campaignRepository.updateStatus(CAMPAIGN_REQUEST)).thenReturn(true);
    when(cacheService.createCache(CACHE_KEY, CAMPAIGN_REQUEST, 0)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY_FIND_ACTIVE)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY_FIND_ADJUSTMENT_ID)).thenReturn(true);
    when(campaignLogService.createCampaignLog(CommonTestVariable.MANDATORY_REQUEST, CAMPAIGN_REQUEST))
        .thenReturn(Single.just(CAMPAIGN_REQUEST));

    Campaign campaign = campaignService
        .updateStatusPendingCampaign(CommonTestVariable.MANDATORY_REQUEST, ID)
        .blockingGet();

    verify(campaignRepository)
        .findCampaignByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);
    verify(promoCodeAdjustmentRepository)
        .findPromoCodeAdjustmentByIdAndPromoCodeAdjustmentStatusAndIsDeleted(
            ADJUSTMENT_ID, PromoCodeAdjustmentStatus.ACTIVE, 0);

    verify(campaignRepository).updateStatus(CAMPAIGN_REQUEST);
    verify(cacheService).createCache(CACHE_KEY, CAMPAIGN_REQUEST, 0);
    verify(cacheService).deleteCache(CACHE_KEY_FIND_ACTIVE);
    verify(cacheService).deleteCache(CACHE_KEY_FIND_ADJUSTMENT_ID);
    verify(campaignLogService).createCampaignLog(CommonTestVariable.MANDATORY_REQUEST, CAMPAIGN_REQUEST);

    assertEquals(CAMPAIGN_REQUEST, campaign);
  }

  @Test
  public void updateStatusPendingCampaignTestNotDataExist() throws Exception {
    when(campaignRepository
        .findCampaignByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(null);

    try {
      campaignService
          .updateStatusPendingCampaign(CommonTestVariable.MANDATORY_REQUEST, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(campaignRepository)
          .findCampaignByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);

      assertEquals(ResponseCode.DATA_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.DATA_NOT_EXIST.getMessage(), be.getMessage());

    }


  }

  @Test
  public void updateStatusPendingCampaignTestStatusExistingNotDraft() throws Exception {
    CAMPAIGN_REQUEST.setCampaignStatus(CampaignStatus.ACTIVE);

    when(campaignRepository
        .findCampaignByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(CAMPAIGN_REQUEST);

    try {
      campaignService
          .updateStatusPendingCampaign(CommonTestVariable.MANDATORY_REQUEST, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(campaignRepository)
          .findCampaignByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);

      assertEquals(ResponseCode.CAMPAIGN_STATUS_NOT_DRAFT.getCode(), be.getCode());
      assertEquals(ResponseCode.CAMPAIGN_STATUS_NOT_DRAFT.getMessage(), be.getMessage());

    }
  }

  @Test
  public void updateStatusPendingCampaignTestAdjustmentNotActive() throws Exception {
    CAMPAIGN_REQUEST.setCampaignStatus(CampaignStatus.DRAFT);

    when(campaignRepository
        .findCampaignByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(CAMPAIGN_REQUEST);
    when(promoCodeAdjustmentRepository
        .findPromoCodeAdjustmentByIdAndPromoCodeAdjustmentStatusAndIsDeleted(
            ADJUSTMENT_ID, PromoCodeAdjustmentStatus.ACTIVE, 0)).thenReturn(null);

    try {
      campaignService
          .updateStatusPendingCampaign(CommonTestVariable.MANDATORY_REQUEST, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(campaignRepository)
          .findCampaignByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);
      verify(promoCodeAdjustmentRepository)
          .findPromoCodeAdjustmentByIdAndPromoCodeAdjustmentStatusAndIsDeleted(
              ADJUSTMENT_ID, PromoCodeAdjustmentStatus.ACTIVE, 0);

      assertEquals(ResponseCode.ADJUSTMENT_STATUS_ACTIVE_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.ADJUSTMENT_STATUS_ACTIVE_NOT_EXIST.getMessage(), be.getMessage());

    }
  }

  @Test
  public void updateStatusActivePromoCodeAdjustmentTestSuccess() throws Exception {
    CAMPAIGN_REQUEST.setCampaignStatus(CampaignStatus.PENDING);

    when(campaignRepository
        .findCampaignByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(CAMPAIGN_REQUEST);

    when(promoCodeAdjustmentRepository
        .findPromoCodeAdjustmentByIdAndPromoCodeAdjustmentStatusAndIsDeleted(
            ADJUSTMENT_ID, PromoCodeAdjustmentStatus.ACTIVE, 0)).thenReturn(PROMO_CODE_ADJUSTMENT);

    when(campaignRepository.updateStatus(CAMPAIGN_REQUEST)).thenReturn(true);
    when(cacheService.createCache(CACHE_KEY, CAMPAIGN_REQUEST, 0)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY_FIND_ACTIVE)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY_FIND_ADJUSTMENT_ID)).thenReturn(true);
    when(campaignLogService.createCampaignLog(CommonTestVariable.MANDATORY_REQUEST, CAMPAIGN_REQUEST))
        .thenReturn(Single.just(CAMPAIGN_REQUEST));

    Campaign campaign = campaignService
        .updateStatusActiveCampaign(CommonTestVariable.MANDATORY_REQUEST, ID)
        .blockingGet();

    verify(campaignRepository)
        .findCampaignByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);
    verify(promoCodeAdjustmentRepository)
        .findPromoCodeAdjustmentByIdAndPromoCodeAdjustmentStatusAndIsDeleted(
            ADJUSTMENT_ID, PromoCodeAdjustmentStatus.ACTIVE, 0);

    verify(campaignRepository).updateStatus(CAMPAIGN_REQUEST);
    verify(cacheService).createCache(CACHE_KEY, CAMPAIGN_REQUEST, 0);
    verify(cacheService).deleteCache(CACHE_KEY_FIND_ACTIVE);
    verify(cacheService).deleteCache(CACHE_KEY_FIND_ADJUSTMENT_ID);
    verify(campaignLogService).createCampaignLog(CommonTestVariable.MANDATORY_REQUEST, CAMPAIGN_REQUEST);

    assertEquals(CAMPAIGN_REQUEST, campaign);
  }

  @Test
  public void updateStatusPublishedCampaignTestNotDataExist() throws Exception {
    when(campaignRepository
        .findCampaignByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(null);

    try {
      campaignService
          .updateStatusActiveCampaign(CommonTestVariable.MANDATORY_REQUEST, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(campaignRepository)
          .findCampaignByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);

      assertEquals(ResponseCode.DATA_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.DATA_NOT_EXIST.getMessage(), be.getMessage());

    }


  }

  @Test
  public void updateStatusPublishedCampaignTestStatusExistingNotApproved() throws Exception {
    CAMPAIGN_REQUEST.setCampaignStatus(CampaignStatus.DRAFT);

    when(campaignRepository
        .findCampaignByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(CAMPAIGN_REQUEST);

    try {
      campaignService
          .updateStatusActiveCampaign(CommonTestVariable.MANDATORY_REQUEST, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(campaignRepository)
          .findCampaignByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);

      assertEquals(ResponseCode.CAMPAIGN_STATUS_NOT_ACTIVE.getCode(), be.getCode());
      assertEquals(ResponseCode.CAMPAIGN_STATUS_NOT_ACTIVE.getMessage(), be.getMessage());

    }
  }

  @Test
  public void updateStatusActiveCampaignTestAdjustmentNotActive() throws Exception {
    CAMPAIGN_REQUEST.setCampaignStatus(CampaignStatus.PENDING);

    when(campaignRepository
        .findCampaignByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(CAMPAIGN_REQUEST);
    when(promoCodeAdjustmentRepository
        .findPromoCodeAdjustmentByIdAndPromoCodeAdjustmentStatusAndIsDeleted(
            ADJUSTMENT_ID, PromoCodeAdjustmentStatus.ACTIVE, 0)).thenReturn(null);

    try {
      campaignService
          .updateStatusActiveCampaign(CommonTestVariable.MANDATORY_REQUEST, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(campaignRepository)
          .findCampaignByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);
      verify(promoCodeAdjustmentRepository)
          .findPromoCodeAdjustmentByIdAndPromoCodeAdjustmentStatusAndIsDeleted(
              ADJUSTMENT_ID, PromoCodeAdjustmentStatus.ACTIVE, 0);

      assertEquals(ResponseCode.ADJUSTMENT_STATUS_ACTIVE_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.ADJUSTMENT_STATUS_ACTIVE_NOT_EXIST.getMessage(), be.getMessage());

    }
  }

  @Test
  public void updateStatusClosedPromoCodeAdjustmentTestSuccess() throws Exception {
    CAMPAIGN_REQUEST.setCampaignStatus(CampaignStatus.ACTIVE);

    when(campaignRepository
        .findCampaignByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(CAMPAIGN_REQUEST);

    when(campaignRepository.updateStatus(CAMPAIGN_REQUEST)).thenReturn(true);
    when(cacheService.createCache(CACHE_KEY, CAMPAIGN_REQUEST, 0)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY_FIND_ACTIVE)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY_FIND_ADJUSTMENT_ID)).thenReturn(true);
    when(campaignLogService.createCampaignLog(CommonTestVariable.MANDATORY_REQUEST, CAMPAIGN_REQUEST))
        .thenReturn(Single.just(CAMPAIGN_REQUEST));

    Campaign campaign = campaignService
        .updateStatusInactiveCampaign(CommonTestVariable.MANDATORY_REQUEST, ID)
        .blockingGet();

    verify(campaignRepository)
        .findCampaignByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);

    verify(campaignRepository).updateStatus(CAMPAIGN_REQUEST);
    verify(cacheService).createCache(CACHE_KEY, CAMPAIGN_REQUEST, 0);
    verify(cacheService).deleteCache(CACHE_KEY_FIND_ACTIVE);
    verify(cacheService).deleteCache(CACHE_KEY_FIND_ADJUSTMENT_ID);
    verify(campaignLogService).createCampaignLog(CommonTestVariable.MANDATORY_REQUEST, CAMPAIGN_REQUEST);

    assertEquals(CAMPAIGN_REQUEST, campaign);
  }

  @Test
  public void updateStatusClosedCampaignTestNotDataExist() throws Exception {
    when(campaignRepository
        .findCampaignByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(null);

    try {
      campaignService
          .updateStatusInactiveCampaign(CommonTestVariable.MANDATORY_REQUEST, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(campaignRepository)
          .findCampaignByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);

      assertEquals(ResponseCode.DATA_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.DATA_NOT_EXIST.getMessage(), be.getMessage());

    }


  }

  @Test
  public void updateStatusClosedCampaignTestStatusExistingNotPublished() throws Exception {
    CAMPAIGN_REQUEST.setCampaignStatus(CampaignStatus.DRAFT);

    when(campaignRepository
        .findCampaignByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(CAMPAIGN_REQUEST);

    try {
      campaignService
          .updateStatusInactiveCampaign(CommonTestVariable.MANDATORY_REQUEST, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(campaignRepository)
          .findCampaignByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);

      assertEquals(ResponseCode.CAMPAIGN_STATUS_NOT_ACTIVE.getCode(), be.getCode());
      assertEquals(ResponseCode.CAMPAIGN_STATUS_NOT_ACTIVE.getMessage(), be.getMessage());

    }
  }

  @Test
  public void findCampaignsTestSuccessWithoutCache() throws Exception {

    when(this.cacheService.findCacheByKey(CACHE_KEY_FIND_ACTIVE, List.class)).thenReturn(null);
    when(campaignRepository.findByStoreIdAndCampaignStatusAndIsDeleted(
        CommonTestVariable.MANDATORY_REQUEST.getStoreId(),
        CampaignStatus.ACTIVE.getCode(), 0))
        .thenReturn(CAMPAIGN_LIST);
    when(this.cacheService.createCache(CACHE_KEY_FIND_ACTIVE, CAMPAIGN_DROPDOWN_LIST, 0))
        .thenReturn(true);

    List<CampaignDropdown> campaign = this.campaignService
        .findCampaigns(CommonTestVariable.MANDATORY_REQUEST).blockingGet();

    verify(this.cacheService).findCacheByKey(CACHE_KEY_FIND_ACTIVE, List.class);
    verify(campaignRepository).findByStoreIdAndCampaignStatusAndIsDeleted(
        CommonTestVariable.MANDATORY_REQUEST.getStoreId(),
        CampaignStatus.ACTIVE.getCode(), 0);
    verify(this.cacheService).createCache(CACHE_KEY_FIND_ACTIVE, CAMPAIGN_DROPDOWN_LIST, 0);

    assertEquals(campaign, CAMPAIGN_DROPDOWN_LIST);
  }

  @Test
  public void findCampaignsTestSuccessWithCache() throws Exception {

    when(this.cacheService.findCacheByKey(CACHE_KEY_FIND_ACTIVE, List.class))
        .thenReturn(CAMPAIGN_LIST);

    List<CampaignDropdown> campaign = this.campaignService
        .findCampaigns(CommonTestVariable.MANDATORY_REQUEST).blockingGet();

    verify(this.cacheService).findCacheByKey(CACHE_KEY_FIND_ACTIVE, List.class);

    assertEquals(campaign, CAMPAIGN_LIST);
  }

  @Test
  public void findCampaignsTestExceptionDataNotExist() throws Exception {

    List<Campaign> campaignList = new ArrayList<>();

    when(this.cacheService.findCacheByKey(CACHE_KEY_FIND_ACTIVE, List.class)).thenReturn(null);
    when(campaignRepository.findByStoreIdAndCampaignStatusAndIsDeleted(
        CommonTestVariable.MANDATORY_REQUEST.getStoreId(),
        CampaignStatus.ACTIVE.getCode(), 0))
        .thenReturn(campaignList);

    try {
      this.campaignService.findCampaigns(CommonTestVariable.MANDATORY_REQUEST).blockingGet();
    } catch (BusinessLogicException be) {
      verify(this.cacheService).findCacheByKey(CACHE_KEY_FIND_ACTIVE, List.class);
      verify(campaignRepository).findByStoreIdAndCampaignStatusAndIsDeleted(
          CommonTestVariable.MANDATORY_REQUEST.getStoreId(),
          CampaignStatus.ACTIVE.getCode(), 0);
      assertEquals(ResponseCode.CAMPAIGN_ACTIVE_IS_EMPTY.getCode(), be.getCode());
      assertEquals(ResponseCode.CAMPAIGN_ACTIVE_IS_EMPTY.getMessage(), be.getMessage());
    }

  }


  @Test
  public void updateStatusRejectedCampaignTestSuccess() throws Exception {
    CAMPAIGN_REQUEST.setCampaignStatus(CampaignStatus.PENDING);

    when(campaignRepository
        .findCampaignByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(CAMPAIGN_REQUEST);

    when(campaignRepository.updateStatus(CAMPAIGN_REQUEST)).thenReturn(true);
    when(cacheService.createCache(CACHE_KEY, CAMPAIGN_REQUEST, 0)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY_FIND_ACTIVE)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY_FIND_ADJUSTMENT_ID)).thenReturn(true);
    when(campaignLogService.createCampaignLog(CommonTestVariable.MANDATORY_REQUEST, CAMPAIGN_REQUEST))
        .thenReturn(Single.just(CAMPAIGN_REQUEST));

    Campaign campaign = campaignService
        .updateStatusRejectedCampaign(CommonTestVariable.MANDATORY_REQUEST, ID)
        .blockingGet();

    verify(campaignRepository)
        .findCampaignByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);

    verify(campaignRepository).updateStatus(CAMPAIGN_REQUEST);
    verify(cacheService).createCache(CACHE_KEY, CAMPAIGN_REQUEST, 0);
    verify(cacheService).deleteCache(CACHE_KEY_FIND_ACTIVE);
    verify(cacheService).deleteCache(CACHE_KEY_FIND_ADJUSTMENT_ID);
    verify(campaignLogService).createCampaignLog(CommonTestVariable.MANDATORY_REQUEST, CAMPAIGN_REQUEST);

    assertEquals(CAMPAIGN_REQUEST, campaign);
  }

  @Test
  public void updateStatusRejectedCampaignTestNotDataExist() throws Exception {
    when(campaignRepository
        .findCampaignByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(null);

    try {
      campaignService
          .updateStatusRejectedCampaign(CommonTestVariable.MANDATORY_REQUEST, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(campaignRepository)
          .findCampaignByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);

      assertEquals(ResponseCode.DATA_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.DATA_NOT_EXIST.getMessage(), be.getMessage());

    }
  }


  @Test
  public void updateStatusRejectedCampaignTestStatusExistingNotPending() throws Exception {
    CAMPAIGN_REQUEST.setCampaignStatus(CampaignStatus.DRAFT);

    when(campaignRepository
        .findCampaignByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(CAMPAIGN_REQUEST);

    try {
      campaignService
          .updateStatusRejectedCampaign(CommonTestVariable.MANDATORY_REQUEST, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(campaignRepository)
          .findCampaignByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);

      assertEquals(ResponseCode.CAMPAIGN_STATUS_NOT_PENDING.getCode(), be.getCode());
      assertEquals(ResponseCode.CAMPAIGN_STATUS_NOT_PENDING.getMessage(), be.getMessage());

    }
  }


  @Test
  public void checkExistAndActiveCampaignByAdjustmentIdTest() throws Exception {
    when(this.cacheService.findCacheByKey(CACHE_KEY_FIND_ADJUSTMENT_ID, List.class))
        .thenReturn(null);
    when(this.campaignRepository
        .findAllByStoreIdAndPromoCodeAdjustmentIdAndIsDeleted(CommonTestVariable.STORE_ID, ADJUSTMENT_ID, 0))
        .thenReturn(CAMPAIGN_LIST);
    when(this.cacheService.createCache(CACHE_KEY_FIND_ADJUSTMENT_ID, CAMPAIGN_LIST, 0)).thenReturn(true);

    when(this.promoCodeService
        .checkExistAndActivePromoCodeByCampaigns(CommonTestVariable.MANDATORY_REQUEST, CAMPAIGN_LIST, false))
        .thenReturn(Single.just(false));

    Boolean result = this.campaignService.checkExistAndActiveCampaignByAdjustmentId(CommonTestVariable.MANDATORY_REQUEST,
        ADJUSTMENT_ID, false).blockingGet();

    verify(this.cacheService).findCacheByKey(CACHE_KEY_FIND_ADJUSTMENT_ID, List.class);
    verify(this.campaignRepository)
        .findAllByStoreIdAndPromoCodeAdjustmentIdAndIsDeleted(CommonTestVariable.STORE_ID, ADJUSTMENT_ID, 0);

    verify(this.cacheService).createCache(CACHE_KEY_FIND_ADJUSTMENT_ID, CAMPAIGN_LIST, 0);

    verify(this.promoCodeService)
        .checkExistAndActivePromoCodeByCampaigns(CommonTestVariable.MANDATORY_REQUEST, CAMPAIGN_LIST, false);

    assertEquals(result, false);
  }

  @Test
  public void checkExistAndActiveCampaignByAdjustmentIdWithCacheFindCampaignTest() throws Exception {
    when(this.cacheService.findCacheByKey(CACHE_KEY_FIND_ADJUSTMENT_ID, List.class))
        .thenReturn(CAMPAIGN_LIST);

    when(this.promoCodeService
        .checkExistAndActivePromoCodeByCampaigns(CommonTestVariable.MANDATORY_REQUEST, CAMPAIGN_LIST, false))
        .thenReturn(Single.just(false));

    Boolean result = this.campaignService.checkExistAndActiveCampaignByAdjustmentId(CommonTestVariable.MANDATORY_REQUEST,
        ADJUSTMENT_ID, false).blockingGet();

    verify(this.cacheService).findCacheByKey(CACHE_KEY_FIND_ADJUSTMENT_ID, List.class);

    verify(this.promoCodeService)
        .checkExistAndActivePromoCodeByCampaigns(CommonTestVariable.MANDATORY_REQUEST, CAMPAIGN_LIST, false);

    assertEquals(result, false);
  }

  @Test
  public void checkExistAndActiveCampaignByAdjustmentIdToDeletedTrueTestExceptionCampaignExist() throws Exception {
    when(this.cacheService.findCacheByKey(CACHE_KEY_FIND_ADJUSTMENT_ID, List.class))
        .thenReturn(CAMPAIGN_LIST);

    when(this.promoCodeService
        .checkExistAndActivePromoCodeByCampaigns(CommonTestVariable.MANDATORY_REQUEST, CAMPAIGN_LIST, true))
        .thenReturn(Single.just(false));

    try{
      this.campaignService.checkExistAndActiveCampaignByAdjustmentId(CommonTestVariable.MANDATORY_REQUEST,
          ADJUSTMENT_ID, true).blockingGet();
    }
    catch (BusinessLogicException be) {
      verify(this.cacheService).findCacheByKey(CACHE_KEY_FIND_ADJUSTMENT_ID, List.class);

      verify(this.promoCodeService)
          .checkExistAndActivePromoCodeByCampaigns(CommonTestVariable.MANDATORY_REQUEST, CAMPAIGN_LIST, true);

      assertEquals(ResponseCode.CANNOT_DELETE_CAUSE_CAMPAIGN_IS_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.CANNOT_DELETE_CAUSE_CAMPAIGN_IS_EXIST.getMessage(), be.getMessage());

    }


  }

  @Test
  public void checkExistAndActiveCampaignByAdjustmentIdToUpdateTestExceptionCampaignExist() throws Exception {
    when(this.cacheService.findCacheByKey(CACHE_KEY_FIND_ADJUSTMENT_ID, List.class))
        .thenReturn(CAMPAIGN_LIST_ACTIVE);

    when(this.promoCodeService
        .checkExistAndActivePromoCodeByCampaigns(CommonTestVariable.MANDATORY_REQUEST, CAMPAIGN_LIST_ACTIVE, false))
        .thenReturn(Single.just(false));

    try{
      this.campaignService.checkExistAndActiveCampaignByAdjustmentId(CommonTestVariable.MANDATORY_REQUEST,
          ADJUSTMENT_ID, false).blockingGet();
    }
    catch (BusinessLogicException be)
    {
      verify(this.cacheService).findCacheByKey(CACHE_KEY_FIND_ADJUSTMENT_ID, List.class);

      verify(this.promoCodeService)
          .checkExistAndActivePromoCodeByCampaigns(CommonTestVariable.MANDATORY_REQUEST, CAMPAIGN_LIST_ACTIVE, false);

      assertEquals(ResponseCode.CANNOT_UPDATE_CAUSE_CAMPAIGN_STATUS_ACTIVE.getCode(), be.getCode());
      assertEquals(ResponseCode.CANNOT_UPDATE_CAUSE_CAMPAIGN_STATUS_ACTIVE.getMessage(), be.getMessage());
    }


  }



  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(campaignRepository);
    verifyNoMoreInteractions(promoCodeAdjustmentRepository);
    verifyNoMoreInteractions(cacheService);
  }
}
