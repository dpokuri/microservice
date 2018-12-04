import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.promo.code.dao.api.ProductTypeRepository;
import com.tl.booking.promo.code.entity.constant.enums.ProductTypeColumn;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.ProductTypeTestVariable;
import com.tl.booking.promo.code.entity.dao.ProductType;
import com.tl.booking.promo.code.libraries.exception.BusinessLogicException;
import com.tl.booking.promo.code.service.api.CacheService;
import com.tl.booking.promo.code.service.impl.ProductTypeServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;

public class ProductTypeServiceImplTest extends ProductTypeTestVariable {

  @InjectMocks
  ProductTypeServiceImpl productTypeService;

  @Mock
  ProductTypeRepository productTypeRepository;

  @Mock
  CacheService cacheService;

  @Test
  public void createProductTypeTestSuccess() throws Exception {
    when(this.productTypeRepository
        .findProductTypeByStoreIdAndNameAndIsDeleted(CommonTestVariable.STORE_ID,
            PRODUCT_TYPE.getName(), 0)).thenReturn(null);

    when(productTypeRepository
        .save(PRODUCT_TYPE)).thenReturn(PRODUCT_TYPE);

    when(cacheService.createCache(CACHE_KEY, PRODUCT_TYPE, 0)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY_FIND)).thenReturn(true);

    ProductType productType = productTypeService
        .createProductType(CommonTestVariable.MANDATORY_REQUEST, PRODUCT_TYPE).blockingGet();

    verify(productTypeRepository)
        .findProductTypeByStoreIdAndNameAndIsDeleted(CommonTestVariable.STORE_ID,
            PRODUCT_TYPE.getName(), 0);

    verify(productTypeRepository).save(PRODUCT_TYPE);
    verify(cacheService).createCache(CACHE_KEY, PRODUCT_TYPE, 0);
    verify(cacheService).deleteCache(CACHE_KEY_FIND);

    assertEquals(PRODUCT_TYPE, productType);
  }


  @Test
  public void createProductTypeTestErrorExistProductType() throws Exception {
    when(productTypeRepository
        .findProductTypeByStoreIdAndNameAndIsDeleted(CommonTestVariable.STORE_ID, NAME, 0))
        .thenReturn(PRODUCT_TYPE);

    try {
      productTypeService.createProductType(CommonTestVariable.MANDATORY_REQUEST, PRODUCT_TYPE)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(productTypeRepository)
          .findProductTypeByStoreIdAndNameAndIsDeleted(CommonTestVariable.STORE_ID, NAME, 0);

      assertEquals(ResponseCode.DUPLICATE_DATA.getCode(), be.getCode());
      assertEquals(ResponseCode.DUPLICATE_DATA.getMessage(), be.getMessage());
    }

  }

  @Test
  public void createProductTypeTestSystemError() throws Exception {

    when(productTypeRepository
        .findProductTypeByStoreIdAndNameAndIsDeleted(CommonTestVariable.STORE_ID, NAME, 0))
        .thenReturn(null);

    when(productTypeRepository
        .save(PRODUCT_TYPE)).thenReturn(null);
    try {
      productTypeService.createProductType(CommonTestVariable.MANDATORY_REQUEST, PRODUCT_TYPE)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(productTypeRepository)
          .findProductTypeByStoreIdAndNameAndIsDeleted(CommonTestVariable.STORE_ID, NAME, 0);
      verify(productTypeRepository).save(PRODUCT_TYPE);

      assertEquals(ResponseCode.SYSTEM_ERROR.getCode(), be.getCode());
      assertEquals(ResponseCode.SYSTEM_ERROR.getMessage(), be.getMessage());
    }

  }


  @Test
  public void updateCampaignTestSuccess() throws Exception {
    when(productTypeRepository.findProductTypeByStoreIdAndIdAndIsDeleted(
        CommonTestVariable.STORE_ID, ID, 0)).thenReturn(PRODUCT_TYPE);

    when(productTypeRepository
        .save(PRODUCT_TYPE)).thenReturn(PRODUCT_TYPE);

    when(cacheService.deleteCache(CACHE_KEY)).thenReturn(true);
    when(cacheService.createCache(CACHE_KEY, PRODUCT_TYPE, 0)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY_FIND)).thenReturn(true);

    ProductType productType = productTypeService
        .updateProductType(CommonTestVariable.MANDATORY_REQUEST, PRODUCT_TYPE, ID).blockingGet();
    verify(productTypeRepository).findProductTypeByStoreIdAndIdAndIsDeleted(
        CommonTestVariable.STORE_ID, ID, 0);
    verify(productTypeRepository).save(PRODUCT_TYPE);
    verify(cacheService).deleteCache(CACHE_KEY);
    verify(cacheService).createCache(CACHE_KEY, PRODUCT_TYPE, 0);
    verify(cacheService).deleteCache(CACHE_KEY_FIND);

    assertEquals(PRODUCT_TYPE, productType);
  }


  @Test
  public void updateProductTypeTestErrordataNotExist() throws Exception {
    when(productTypeRepository.findProductTypeByStoreIdAndIdAndIsDeleted(
        CommonTestVariable.STORE_ID, ID, 0)).thenReturn(null);
    try {
      productTypeService.updateProductType(CommonTestVariable.MANDATORY_REQUEST, PRODUCT_TYPE, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(productTypeRepository).findProductTypeByStoreIdAndIdAndIsDeleted(
          CommonTestVariable.STORE_ID, ID, 0);

      assertEquals(ResponseCode.PRODUCT_TYPE_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.PRODUCT_TYPE_NOT_EXIST.getMessage(), be.getMessage());
    }

  }


  @Test
  public void updateProductTypeTestErrorNameIsExistInOtherDocument() throws Exception {
    when(productTypeRepository.findProductTypeByStoreIdAndIdAndIsDeleted(
        CommonTestVariable.STORE_ID, ID, 0)).thenReturn(PRODUCT_TYPE);
    when(productTypeRepository
        .findProductTypeByStoreIdAndNameAndIsDeleted(CommonTestVariable.STORE_ID, NAME_2, 0))
        .thenReturn(PRODUCT_TYPE_2);

    try {
      productTypeService.updateProductType(CommonTestVariable.MANDATORY_REQUEST, PRODUCT_TYPE_2, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(productTypeRepository).findProductTypeByStoreIdAndIdAndIsDeleted(
          CommonTestVariable.STORE_ID, ID, 0);
      verify(productTypeRepository)
          .findProductTypeByStoreIdAndNameAndIsDeleted(CommonTestVariable.STORE_ID, NAME_2, 0);

      assertEquals(ResponseCode.NAME_EXIST_OTHER_RECORD.getCode(), be.getCode());
      assertEquals(ResponseCode.NAME_EXIST_OTHER_RECORD.getMessage(), be.getMessage());
    }

  }


  @Test
  public void updateCampaignTestExceptionSystemError() throws Exception {
    when(productTypeRepository
        .findProductTypeByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(PRODUCT_TYPE);

    when(productTypeRepository
        .save(PRODUCT_TYPE)).thenReturn(null);

    try {
      productTypeService.updateProductType(CommonTestVariable.MANDATORY_REQUEST, PRODUCT_TYPE, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(productTypeRepository)
          .findProductTypeByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);
      verify(productTypeRepository).save(PRODUCT_TYPE);

      assertEquals(ResponseCode.SYSTEM_ERROR.getCode(), be.getCode());
      assertEquals(ResponseCode.SYSTEM_ERROR.getMessage(), be.getMessage());
    }

  }


  @Test
  public void deleteCampaignTestSuccess() throws Exception {
    PRODUCT_TYPE.setIsDeleted(1);

    when(productTypeRepository
        .findProductTypeByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(PRODUCT_TYPE);

    when(productTypeRepository.softDeleted(PRODUCT_TYPE, ID)).thenReturn(true);

    when(cacheService.deleteCache(CACHE_KEY)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY_FIND)).thenReturn(true);

    Boolean campaign = productTypeService
        .deleteProductType(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();

    verify(productTypeRepository)
        .findProductTypeByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);
    verify(productTypeRepository).softDeleted(PRODUCT_TYPE, ID);
    verify(cacheService).deleteCache(CACHE_KEY);
    verify(cacheService).deleteCache(CACHE_KEY_FIND);

    assertEquals(true, campaign);
  }

  @Test
  public void findProductTypePaginatedTestSuccess() throws Exception {
    when(productTypeRepository
        .findProductTypeFilterPaginated(CommonTestVariable.STORE_ID, NAME, PAGE,
            SIZE, ProductTypeColumn.ID.getValue(), SortDirection.ASC))
        .thenReturn(PRODUCT_TYPE_PAGE);

    Page<ProductType> productTypePage = productTypeService
        .findProductTypeFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
            NAME, PAGE, SIZE, ProductTypeColumn.ID,
            SortDirection.ASC).blockingGet();

    verify(productTypeRepository)
        .findProductTypeFilterPaginated(CommonTestVariable.STORE_ID, NAME, PAGE,
            SIZE, ProductTypeColumn.ID.getValue(), SortDirection.ASC);

    assertEquals(PRODUCT_TYPE_PAGE, productTypePage);
  }

  @Test
  public void deleteProductTypeTestDataNotExist() throws Exception {

    when(productTypeRepository
        .findProductTypeByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(null);
    try {
      productTypeService.deleteProductType(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();
    } catch (BusinessLogicException be) {
      verify(productTypeRepository)
          .findProductTypeByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);

      assertEquals(ResponseCode.PRODUCT_TYPE_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.PRODUCT_TYPE_NOT_EXIST.getMessage(), be.getMessage());
    }
  }


  @Test
  public void deleteProductTypeTestExceptionSystemError() throws Exception {

    when(productTypeRepository
        .findProductTypeByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(PRODUCT_TYPE);

    when(productTypeRepository.softDeleted(PRODUCT_TYPE, ID)).thenReturn(false);

    try {
      productTypeService.deleteProductType(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();
    } catch (BusinessLogicException be) {
      verify(productTypeRepository)
          .findProductTypeByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);
      verify(productTypeRepository).softDeleted(PRODUCT_TYPE, ID);

      assertEquals(ResponseCode.SYSTEM_ERROR.getCode(), be.getCode());
      assertEquals(ResponseCode.SYSTEM_ERROR.getMessage(), be.getMessage());
    }
  }


  @Test
  public void findProductTypeByIdTestCacheResult() throws Exception {
    when(cacheService.findCacheByKey(CACHE_KEY, ProductType.class))
        .thenReturn(PRODUCT_TYPE);

    ProductType productType = productTypeService
        .findProductTypeById(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();

    verify(cacheService).findCacheByKey(CACHE_KEY, ProductType.class);

    assertEquals(PRODUCT_TYPE, productType);
  }

  @Test
  public void findProductTypeByIdTestNonCacheResult() throws Exception {
    when(cacheService.findCacheByKey(CACHE_KEY, ProductType.class))
        .thenReturn(null);
    when(productTypeRepository
        .findProductTypeByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(PRODUCT_TYPE);
    when(cacheService.createCache(CACHE_KEY, PRODUCT_TYPE, 0)).thenReturn(true);

    ProductType productType = productTypeService
        .findProductTypeById(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();

    verify(cacheService).findCacheByKey(CACHE_KEY, ProductType.class);
    verify(productTypeRepository)
        .findProductTypeByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);
    verify(cacheService).createCache(CACHE_KEY, PRODUCT_TYPE, 0);

    assertEquals(PRODUCT_TYPE, productType);
  }

  @Test
  public void findProductTypeByIdTestExceptionCampaignNotExist() throws Exception {
    when(cacheService.findCacheByKey(CACHE_KEY, ProductType.class))
        .thenReturn(null);
    when(productTypeRepository
        .findProductTypeByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(null);

    try {
      productTypeService
          .findProductTypeById(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();
    } catch (BusinessLogicException be) {
      verify(cacheService).findCacheByKey(CACHE_KEY, ProductType.class);
      verify(productTypeRepository)
          .findProductTypeByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);

      assertEquals(ResponseCode.PRODUCT_TYPE_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.PRODUCT_TYPE_NOT_EXIST.getMessage(), be.getMessage());
    }
  }

  @Test
  public void findProductTypeTestSuccessWithCache() throws Exception {

    when(this.cacheService.findCacheByKey(CACHE_KEY_FIND, List.class))
        .thenReturn(PRODUCT_TYPE_LIST);

    List<ProductType> storeId = this.productTypeService
        .findProductTypes(CommonTestVariable.MANDATORY_REQUEST).blockingGet();

    verify(this.cacheService).findCacheByKey(CACHE_KEY_FIND, List.class);

    assertEquals(storeId, PRODUCT_TYPE_LIST);
  }

  @Test
  public void findProductTypeTestExceptionDataNotExist() throws Exception {

    List<ProductType> storeIdList = new ArrayList<>();

    when(this.cacheService.findCacheByKey(CACHE_KEY_FIND, List.class)).thenReturn(null);
    when(productTypeRepository.findProductTypeByStoreIdAndIsDeleted(CommonTestVariable.STORE_ID, 0))
        .thenReturn(storeIdList);

    try {
      this.productTypeService.findProductTypes(CommonTestVariable.MANDATORY_REQUEST).blockingGet();
    } catch (BusinessLogicException be) {
      verify(this.cacheService).findCacheByKey(CACHE_KEY_FIND, List.class);
      verify(productTypeRepository)
          .findProductTypeByStoreIdAndIsDeleted(CommonTestVariable.STORE_ID, 0);
      assertEquals(ResponseCode.PRODUCT_TYPE_LIST_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.PRODUCT_TYPE_LIST_NOT_EXIST.getMessage(), be.getMessage());
    }

  }

  @Test
  public void findProductTypeTestCache() throws Exception {

    when(this.cacheService.findCacheByKey(CACHE_KEY_FIND, List.class)).thenReturn(null);
    when(productTypeRepository.findProductTypeByStoreIdAndIsDeleted(CommonTestVariable.STORE_ID, 0))
        .thenReturn(PRODUCT_TYPE_LIST);

    List<ProductType> productTypes = this.productTypeService
        .findProductTypes(CommonTestVariable.MANDATORY_REQUEST).blockingGet();

    verify(this.cacheService).findCacheByKey(CACHE_KEY_FIND, List.class);
    verify(productTypeRepository)
        .findProductTypeByStoreIdAndIsDeleted(CommonTestVariable.STORE_ID, 0);
    verify(cacheService).createCache(CACHE_KEY_FIND, PRODUCT_TYPE_LIST, 0);

    assertEquals(PRODUCT_TYPE_LIST, productTypes);
  }


  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(productTypeRepository);
    verifyNoMoreInteractions(cacheService);
  }
}
