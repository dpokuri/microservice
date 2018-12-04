import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.promo.code.dao.api.CardTypeRepository;
import com.tl.booking.promo.code.entity.constant.enums.CardTypeColumn;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.unit.test.CardTypeTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.promo.code.entity.dao.CardType;
import com.tl.booking.promo.code.libraries.exception.BusinessLogicException;
import com.tl.booking.promo.code.service.api.CacheService;
import com.tl.booking.promo.code.service.impl.CardTypeServiceImpl;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;

public class CardTypeServiceImplTest extends CardTypeTestVariable {

  @InjectMocks
  CardTypeServiceImpl cardTypeService;

  @Mock
  CardTypeRepository cardTypeRepository;

  @Mock
  CacheService cacheService;

  @Test
  public void createCardTypeTestSuccess() throws Exception {
    when(this.cardTypeRepository
        .findCardTypeByStoreIdAndNameAndBankIdAndIsDeleted(CommonTestVariable.STORE_ID,
            CARD_TYPE.getName(), CARD_TYPE.getBankId(), 0)).thenReturn(null);

    when(cardTypeRepository
        .save(CARD_TYPE)).thenReturn(CARD_TYPE);

    when(cacheService.deleteCache(CACHE_KEY_FIND_ALL)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY)).thenReturn(true);

    when(cacheService.createCache(CACHE_KEY, CARD_TYPE, 0)).thenReturn(true);

    CardType cardType = cardTypeService
        .createCardType(CommonTestVariable.MANDATORY_REQUEST, CARD_TYPE).blockingGet();

    verify(cardTypeRepository)
        .findCardTypeByStoreIdAndNameAndBankIdAndIsDeleted(CommonTestVariable.STORE_ID,
            CARD_TYPE.getName(), CARD_TYPE.getBankId(), 0);

    verify(cardTypeRepository).save(CARD_TYPE);

    verify(cacheService).deleteCache(CACHE_KEY_FIND_ALL);
    verify(cacheService).deleteCache(CACHE_KEY);

    verify(cacheService).createCache(CACHE_KEY, CARD_TYPE, 0);

    assertEquals(CARD_TYPE, cardType);
  }

  @Test
  public void createCardTypeTestErrorExistCardType() throws Exception {
    when(cardTypeRepository
        .findCardTypeByStoreIdAndNameAndBankIdAndIsDeleted(CommonTestVariable.STORE_ID, NAME, BANK_ID, 0))
        .thenReturn(CARD_TYPE);

    try {
      cardTypeService.createCardType(CommonTestVariable.MANDATORY_REQUEST, CARD_TYPE)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(cardTypeRepository)
          .findCardTypeByStoreIdAndNameAndBankIdAndIsDeleted(CommonTestVariable.STORE_ID, NAME, BANK_ID, 0);

      assertEquals(ResponseCode.DUPLICATE_DATA.getCode(), be.getCode());
      assertEquals(ResponseCode.DUPLICATE_DATA.getMessage(), be.getMessage());
    }

  }

  @Test
  public void createCardTypeTestSystemError() throws Exception {

    when(cardTypeRepository
        .findCardTypeByStoreIdAndNameAndBankIdAndIsDeleted(CommonTestVariable.STORE_ID, NAME, BANK_ID, 0))
        .thenReturn(null);

    when(cardTypeRepository
        .save(CARD_TYPE)).thenReturn(null);
    try {
      cardTypeService.createCardType(CommonTestVariable.MANDATORY_REQUEST, CARD_TYPE)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(cardTypeRepository)
          .findCardTypeByStoreIdAndNameAndBankIdAndIsDeleted(CommonTestVariable.STORE_ID, NAME, BANK_ID, 0);
      verify(cardTypeRepository).save(CARD_TYPE);

      assertEquals(ResponseCode.SYSTEM_ERROR.getCode(), be.getCode());
      assertEquals(ResponseCode.SYSTEM_ERROR.getMessage(), be.getMessage());
    }

  }

  @Test
  public void updateCardTypeTestSuccess() throws Exception {
    when(cardTypeRepository.findCardTypeByStoreIdAndIdAndIsDeleted(
        CommonTestVariable.STORE_ID, ID, 0)).thenReturn(CARD_TYPE);

    when(cardTypeRepository
        .save(CARD_TYPE)).thenReturn(CARD_TYPE);

    when(cacheService.deleteCache(CACHE_KEY_FIND_ALL)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY)).thenReturn(true);

    when(cacheService.createCache(CACHE_KEY, CARD_TYPE, 0)).thenReturn(true);

    CardType cardType = cardTypeService
        .updateCardType(CommonTestVariable.MANDATORY_REQUEST, CARD_TYPE, ID).blockingGet();
    verify(cardTypeRepository).findCardTypeByStoreIdAndIdAndIsDeleted(
        CommonTestVariable.STORE_ID, ID, 0);
    verify(cardTypeRepository).save(CARD_TYPE);

    verify(cacheService).deleteCache(CACHE_KEY_FIND_ALL);
    verify(cacheService).deleteCache(CACHE_KEY);
    verify(cacheService).createCache(CACHE_KEY, CARD_TYPE, 0);

    assertEquals(CARD_TYPE, cardType);
  }

  @Test
  public void updateCardTypeTestErrordataNotExist() throws Exception {
    when(cardTypeRepository.findCardTypeByStoreIdAndIdAndIsDeleted(
        CommonTestVariable.STORE_ID, ID, 0)).thenReturn(null);
    try {
      cardTypeService.updateCardType(CommonTestVariable.MANDATORY_REQUEST, CARD_TYPE, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(cardTypeRepository).findCardTypeByStoreIdAndIdAndIsDeleted(
          CommonTestVariable.STORE_ID, ID, 0);

      assertEquals(ResponseCode.CARD_TYPE_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.CARD_TYPE_NOT_EXIST.getMessage(), be.getMessage());
    }

  }

  @Test
  public void updateCardTypeTestErrorNameIsExistInOtherDocument() throws Exception {
//    when(cardTypeRepository.findCardTypeByStoreIdAndIdAndIsDeleted(
//        CommonTestVariable.STORE_ID, ID, 0)).thenReturn(CARD_TYPE);
//    when(cardTypeRepository
//        .findCardTypeByStoreIdAndNameAndIsDeleted(CommonTestVariable.STORE_ID, NAME_2, 0))
//        .thenReturn(CARD_TYPE_2);
//
//    try {
//      cardTypeService.updateCardType(CommonTestVariable.MANDATORY_REQUEST, CARD_TYPE_2, ID)
//          .blockingGet();
//    } catch (BusinessLogicException be) {
//      verify(cardTypeRepository).findCardTypeByStoreIdAndIdAndIsDeleted(
//          CommonTestVariable.STORE_ID, ID, 0);
//      verify(cardTypeRepository)
//          .findCardTypeByStoreIdAndNameAndIsDeleted(CommonTestVariable.STORE_ID, NAME_2, 0);
//
//      assertEquals(ResponseCode.NAME_EXIST_OTHER_RECORD.getCode(), be.getCode());
//      assertEquals(ResponseCode.NAME_EXIST_OTHER_RECORD.getMessage(), be.getMessage());
//    }

  }

  @Test
  public void updateCardTypeTestExceptionSystemError() throws Exception {
    when(cardTypeRepository
        .findCardTypeByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(CARD_TYPE);

    when(cardTypeRepository
        .save(CARD_TYPE)).thenReturn(null);

    try {
      cardTypeService.updateCardType(CommonTestVariable.MANDATORY_REQUEST, CARD_TYPE, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(cardTypeRepository)
          .findCardTypeByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);
      verify(cardTypeRepository).save(CARD_TYPE);

      assertEquals(ResponseCode.SYSTEM_ERROR.getCode(), be.getCode());
      assertEquals(ResponseCode.SYSTEM_ERROR.getMessage(), be.getMessage());
    }

  }

  @Test
  public void deleteCardTypeTestSuccess() throws Exception {
    CARD_TYPE.setIsDeleted(1);

    when(cardTypeRepository
        .findCardTypeByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(CARD_TYPE);

    when(cardTypeRepository.softDeleted(CARD_TYPE, ID)).thenReturn(true);

    when(cacheService.deleteCache(CACHE_KEY_FIND_ALL)).thenReturn(true);

    when(cacheService.deleteCache(CACHE_KEY)).thenReturn(true);

    Boolean cardType = cardTypeService
        .deleteCardType(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();

    verify(cardTypeRepository)
        .findCardTypeByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);
    verify(cardTypeRepository).softDeleted(CARD_TYPE, ID);

    verify(cacheService).deleteCache(CACHE_KEY_FIND_ALL);

    verify(cacheService).deleteCache(CACHE_KEY);

    assertEquals(true, cardType);
  }

  @Test
  public void deleteCardTypeTestDataNotExist() throws Exception {

    when(cardTypeRepository
        .findCardTypeByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(null);
    try {
      cardTypeService.deleteCardType(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();
    } catch (BusinessLogicException be) {
      verify(cardTypeRepository)
          .findCardTypeByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);

      assertEquals(ResponseCode.CARD_TYPE_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.CARD_TYPE_NOT_EXIST.getMessage(), be.getMessage());
    }
  }

  @Test
  public void deleteCardTypeTestExceptionSystemError() throws Exception {

    when(cardTypeRepository
        .findCardTypeByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(CARD_TYPE);

    when(cardTypeRepository.softDeleted(CARD_TYPE, ID)).thenReturn(false);

    try {
      cardTypeService.deleteCardType(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();
    } catch (BusinessLogicException be) {
      verify(cardTypeRepository)
          .findCardTypeByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);
      verify(cardTypeRepository).softDeleted(CARD_TYPE, ID);

      assertEquals(ResponseCode.SYSTEM_ERROR.getCode(), be.getCode());
      assertEquals(ResponseCode.SYSTEM_ERROR.getMessage(), be.getMessage());
    }
  }

  @Test
  public void findAllCardTypeTestNonCacheResult() throws Exception {
    when(cacheService.findCacheByKey(CACHE_KEY_FIND_ALL, List.class))
        .thenReturn(null);
    when(cardTypeRepository.findCardTypes(0, CommonTestVariable.STORE_ID, BANK_ID))
        .thenReturn(CARD_TYPE_LIST_IS_DELETED);
    when(cacheService.createCache(CACHE_KEY_FIND_ALL, CARD_TYPE_LIST_IS_DELETED, 0))
        .thenReturn(true);

    List<CardType> cardType = cardTypeService
        .findCardTypes(CommonTestVariable.MANDATORY_REQUEST, BANK_ID).blockingGet();

    verify(cacheService).findCacheByKey(CACHE_KEY_FIND_ALL, List.class);
    verify(cardTypeRepository).findCardTypes(0, CommonTestVariable.STORE_ID, BANK_ID);
    verify(cacheService).createCache(CACHE_KEY_FIND_ALL, CARD_TYPE_LIST_IS_DELETED, 0);

    assertEquals(CARD_TYPE_LIST_IS_DELETED, cardType);
  }

  @Test
  public void findCardTypeByIdTestCacheResult() throws Exception {
    when(cacheService.findCacheByKey(CACHE_KEY, CardType.class))
        .thenReturn(CARD_TYPE);

    CardType cardType = cardTypeService
        .findCardTypeById(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();

    verify(cacheService).findCacheByKey(CACHE_KEY, CardType.class);

    assertEquals(CARD_TYPE, cardType);
  }

  @Test
  public void findCardTypeByIdTestNonCacheResult() throws Exception {
    when(cacheService.findCacheByKey(CACHE_KEY, CardType.class))
        .thenReturn(null);
    when(cardTypeRepository
        .findCardTypeByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(CARD_TYPE);
    when(cacheService.createCache(CACHE_KEY, CARD_TYPE, 0)).thenReturn(true);

    CardType cardType = cardTypeService
        .findCardTypeById(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();

    verify(cacheService).findCacheByKey(CACHE_KEY, CardType.class);
    verify(cardTypeRepository)
        .findCardTypeByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);
    verify(cacheService).createCache(CACHE_KEY, CARD_TYPE, 0);

    assertEquals(CARD_TYPE, cardType);
  }

  @Test
  public void findCardTypeByIdTestExceptionCardTypeNotExist() throws Exception {
    when(cacheService.findCacheByKey(CACHE_KEY, CardType.class))
        .thenReturn(null);
    when(cardTypeRepository
        .findCardTypeByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(null);

    try {
      cardTypeService
          .findCardTypeById(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();
    } catch (BusinessLogicException be) {
      verify(cacheService).findCacheByKey(CACHE_KEY, CardType.class);
      verify(cardTypeRepository)
          .findCardTypeByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);

      assertEquals(ResponseCode.CARD_TYPE_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.CARD_TYPE_NOT_EXIST.getMessage(), be.getMessage());
    }
  }

  @Test
  public void findCardTypePaginatedTestSuccess() throws Exception {
    when(cardTypeRepository
        .findCardTypeFilterPaginated(CommonTestVariable.STORE_ID, NAME, PAGE,
            SIZE, CardTypeColumn.ID.getValue(), SortDirection.ASC))
        .thenReturn(CARD_TYPE_PAGE);

    Page<CardType> cardTypePage = cardTypeService
        .findCardTypeFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
            NAME, PAGE, SIZE, CardTypeColumn.ID,
            SortDirection.ASC).blockingGet();

    verify(cardTypeRepository)
        .findCardTypeFilterPaginated(CommonTestVariable.STORE_ID, NAME, PAGE,
            SIZE, CardTypeColumn.ID.getValue(), SortDirection.ASC);

    assertEquals(CARD_TYPE_PAGE, cardTypePage);
  }

  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(cardTypeRepository);
    verifyNoMoreInteractions(cacheService);
  }
}
