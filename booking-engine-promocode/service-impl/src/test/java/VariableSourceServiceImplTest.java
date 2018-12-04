import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.promo.code.dao.api.VariableSourceRepository;
import com.tl.booking.promo.code.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.VariableSourceTestVariable;
import com.tl.booking.promo.code.entity.dao.VariableSource;
import com.tl.booking.promo.code.service.api.CacheService;
import com.tl.booking.promo.code.service.impl.VariableSourceServiceImpl;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class VariableSourceServiceImplTest extends VariableSourceTestVariable {

  @InjectMocks
  VariableSourceServiceImpl variableSourceService;

  @Mock
  VariableSourceRepository variableSourceRepository;

  @Mock
  CacheService cacheService;

  @Test
  public void updateVariableSourceTestSuccess() {
    when(variableSourceRepository.findVariableSourceBySourceTypeAndValueIdAndStoreIdAndIsDeleted(
        SOURCE_TYPE, VALUE_ID, CommonTestVariable.STORE_ID, 0)).thenReturn(VARIABLE_SOURCE);

    when(variableSourceRepository
        .save(VARIABLE_SOURCE)).thenReturn(VARIABLE_SOURCE);

    when(cacheService.deleteCache(CACHE_KEY_ID)).thenReturn(true);

    when(cacheService.createCache(CACHE_KEY_ID, VARIABLE_SOURCE, 0)).thenReturn(true);

    Boolean variableSource = variableSourceService
        .updateVariableSources(CommonTestVariable.MANDATORY_REQUEST, VARIABLE_SOURCE_LIST)
        .blockingGet();

    verify(variableSourceRepository).findVariableSourceBySourceTypeAndValueIdAndStoreIdAndIsDeleted(
        SOURCE_TYPE, VALUE_ID, CommonTestVariable.STORE_ID, 0);
    verify(variableSourceRepository).save(VARIABLE_SOURCE);

    verify(cacheService).deleteCache(CACHE_KEY_ID);
    assertEquals(true, variableSource);
  }

  @Test
  public void findVariableSourceBySourceTypeKeyTestSuccessWithsourceTypeAndKeyNotExist()
      throws Exception {

    when(this.cacheService.findCacheByKey(CACHE_KEY_SOURCE_AND_KEY, List.class)).thenReturn(null);

    when(this.variableSourceRepository
        .findBySourceTypeAndValueSearchRegexAndStoreIdAndIsDeleted(SOURCE_TYPE, KEY,
            CommonTestVariable.STORE_ID, 0)).thenReturn(VARIABLE_SOURCE_LIST);

    when(this.cacheService.createCache(CACHE_KEY_SOURCE_AND_KEY, VARIABLE_SOURCE_LIST, 300))
        .thenReturn(true);

    List<VariableSource> variableSource = variableSourceService
        .findVariableSourceBySourceTypeKey(CommonTestVariable.MANDATORY_REQUEST, SOURCE_TYPE, KEY)
        .blockingGet();

    verify(this.cacheService).findCacheByKey(CACHE_KEY_SOURCE_AND_KEY, List.class);

    verify(this.variableSourceRepository)
        .findBySourceTypeAndValueSearchRegexAndStoreIdAndIsDeleted(SOURCE_TYPE, KEY,
            CommonTestVariable.STORE_ID, 0);

    verify(this.cacheService).createCache(CACHE_KEY_SOURCE_AND_KEY, VARIABLE_SOURCE_LIST, 300);

    assertEquals(VARIABLE_SOURCE_LIST, variableSource);
  }


  @Test
  public void findVariableSourceBySourceTypeKeyTestSuccessWithsourceTypeAndKeyExist()
      throws Exception {

    when(this.cacheService.findCacheByKey(CACHE_KEY_SOURCE_AND_KEY, List.class))
        .thenReturn(VARIABLE_SOURCE_LIST);

    List<VariableSource> variableSource = variableSourceService
        .findVariableSourceBySourceTypeKey(CommonTestVariable.MANDATORY_REQUEST, SOURCE_TYPE, KEY)
        .blockingGet();

    verify(this.cacheService).findCacheByKey(CACHE_KEY_SOURCE_AND_KEY, List.class);

    assertEquals(VARIABLE_SOURCE_LIST, variableSource);
  }


  @Test
  public void findVariableSourceBySourceTypeKeyTestSuccessWithKeyNullAndsourceTypeCacheNotExist()
      throws Exception {

    when(this.cacheService.findCacheByKey(CACHE_KEY_SOURCE, List.class)).thenReturn(null);

    when(this.variableSourceRepository
        .findBySourceTypeAndStoreIdAndIsDeleted(SOURCE_TYPE, CommonTestVariable.STORE_ID, 0))
        .thenReturn(VARIABLE_SOURCE_LIST);

    when(this.cacheService.createCache(CACHE_KEY_SOURCE, VARIABLE_SOURCE_LIST, 300))
        .thenReturn(true);

    List<VariableSource> variableSource = variableSourceService
        .findVariableSourceBySourceTypeKey(CommonTestVariable.MANDATORY_REQUEST, SOURCE_TYPE, null)
        .blockingGet();

    verify(this.cacheService).findCacheByKey(CACHE_KEY_SOURCE, List.class);

    verify(this.variableSourceRepository)
        .findBySourceTypeAndStoreIdAndIsDeleted(SOURCE_TYPE, CommonTestVariable.STORE_ID, 0);

    verify(this.cacheService).createCache(CACHE_KEY_SOURCE, VARIABLE_SOURCE_LIST, 300);

    assertEquals(VARIABLE_SOURCE_LIST, variableSource);
  }

  @Test
  public void findVariableSourceBySourceTypeKeyTestSuccessWithKeyNullAndsourceTypeCacheExist()
      throws Exception {

    when(this.cacheService.findCacheByKey(CACHE_KEY_SOURCE, List.class))
        .thenReturn(VARIABLE_SOURCE_LIST);

    List<VariableSource> variableSource = variableSourceService
        .findVariableSourceBySourceTypeKey(CommonTestVariable.MANDATORY_REQUEST, SOURCE_TYPE, null)
        .blockingGet();

    verify(this.cacheService).findCacheByKey(CACHE_KEY_SOURCE, List.class);

    assertEquals(VARIABLE_SOURCE_LIST, variableSource);
  }

  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(variableSourceRepository);
    verifyNoMoreInteractions(cacheService);
  }
}
