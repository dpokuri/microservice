import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.promo.code.dao.api.VariableRepository;
import com.tl.booking.promo.code.entity.constant.enums.InputType;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.enums.VariableColumn;
import com.tl.booking.promo.code.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.VariableServiceTestVariable;
import com.tl.booking.promo.code.entity.dao.Campaign;
import com.tl.booking.promo.code.entity.dao.Variable;
import com.tl.booking.promo.code.libraries.exception.BusinessLogicException;
import com.tl.booking.promo.code.service.api.CacheService;
import com.tl.booking.promo.code.service.impl.VariableServiceImpl;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;

public class VariableServiceImplTest extends VariableServiceTestVariable {

  @InjectMocks
  VariableServiceImpl variableService;

  @Mock
  VariableRepository variableRepository;

  @Mock
  CacheService cacheService;

  @Test
  public void createVariableTestSuccess() throws Exception {

    when(variableRepository
        .findVariableByStoreIdAndParamAndIsDeleted(CommonTestVariable.STORE_ID, PARAM, 0))
        .thenReturn(null);

    when(variableRepository
        .save(VARIABLE)).thenReturn(VARIABLE);

    when(cacheService.createCache(CACHE_KEY, VARIABLE, 0)).thenReturn(true);

    when(cacheService.deleteCache(CACHE_KEY_MAPPED)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY_ALL)).thenReturn(true);

    Variable variable = variableService
        .createVariable(CommonTestVariable.MANDATORY_REQUEST, VARIABLE).blockingGet();

    verify(variableRepository)
        .findVariableByStoreIdAndParamAndIsDeleted(CommonTestVariable.STORE_ID, PARAM, 0);
    verify(variableRepository).save(VARIABLE);
    verify(cacheService).createCache(CACHE_KEY, VARIABLE, 0);
    verify(cacheService).deleteCache(CACHE_KEY_MAPPED);
    verify(cacheService).deleteCache(CACHE_KEY_ALL);

    assertEquals(VARIABLE, variable);
  }


  @Test
  public void createVariableTestErrorExistCampaign() throws Exception {
    when(variableRepository
        .findVariableByStoreIdAndParamAndIsDeleted(CommonTestVariable.STORE_ID, PARAM, 0))
        .thenReturn(VARIABLE);

    try {
      variableService.createVariable(CommonTestVariable.MANDATORY_REQUEST, VARIABLE).blockingGet();
    } catch (BusinessLogicException be) {
      verify(variableRepository)
          .findVariableByStoreIdAndParamAndIsDeleted(CommonTestVariable.STORE_ID, PARAM, 0);

      assertEquals(ResponseCode.DUPLICATE_DATA.getCode(), be.getCode());
      assertEquals(ResponseCode.DUPLICATE_DATA.getMessage(), be.getMessage());
    }

  }

  @Test
  public void createVariableTestSystemError() throws Exception {
    when(variableRepository
        .findVariableByStoreIdAndParamAndIsDeleted(CommonTestVariable.STORE_ID, PARAM, 0))
        .thenReturn(null);

    when(variableRepository
        .save(VARIABLE)).thenReturn(null);

    try {
      variableService.createVariable(CommonTestVariable.MANDATORY_REQUEST, VARIABLE).blockingGet();
    } catch (BusinessLogicException be) {
      verify(variableRepository)
          .findVariableByStoreIdAndParamAndIsDeleted(CommonTestVariable.STORE_ID, PARAM, 0);
      verify(variableRepository).save(VARIABLE);

      assertEquals(ResponseCode.SYSTEM_ERROR.getCode(), be.getCode());
      assertEquals(ResponseCode.SYSTEM_ERROR.getMessage(), be.getMessage());
    }
  }

  @Test
  public void updateVariableTestErrorDataNotExist() throws Exception {
    when(variableRepository
        .findVariableByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(null);

    try {
      variableService.updateVariable(CommonTestVariable.MANDATORY_REQUEST, VARIABLE, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(variableRepository)
          .findVariableByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);

      assertEquals(ResponseCode.VARIABLE_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.VARIABLE_NOT_EXIST.getMessage(), be.getMessage());
    }

  }


  @Test
  public void updateVariableTestErrorParamIsExistInOtherDocument() throws Exception {
    when(variableRepository
        .findVariableByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(VARIABLE_UPDATE_MOCK);

    when(variableRepository
        .findVariableByStoreIdAndParamAndIsDeleted(CommonTestVariable.STORE_ID, PARAM, 0))
        .thenReturn(VARIABLE_UPDATE_PARAM_OTHER);

    try {
      variableService
          .updateVariable(CommonTestVariable.MANDATORY_REQUEST, VARIABLE_UPDATE_PARAM, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(variableRepository)
          .findVariableByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);
      verify(variableRepository)
          .findVariableByStoreIdAndParamAndIsDeleted(CommonTestVariable.STORE_ID, PARAM, 0);

      assertEquals(ResponseCode.PARAM_EXIST_OTHER_RECORD.getCode(), be.getCode());
      assertEquals(ResponseCode.PARAM_EXIST_OTHER_RECORD.getMessage(), be.getMessage());
    }

  }


  @Test
  public void updateVariableTestExceptionSystemError() throws Exception {
    when(variableRepository
        .findVariableByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(VARIABLE);

    when(variableRepository
        .save(VARIABLE)).thenReturn(null);

    try {
      variableService.updateVariable(CommonTestVariable.MANDATORY_REQUEST, VARIABLE, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(variableRepository)
          .findVariableByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);
      verify(variableRepository).save(VARIABLE);

      assertEquals(ResponseCode.SYSTEM_ERROR.getCode(), be.getCode());
      assertEquals(ResponseCode.SYSTEM_ERROR.getMessage(), be.getMessage());
    }

  }

  @Test
  public void updateVariableTest() throws Exception {
    when(variableRepository
        .findVariableByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(VARIABLE);
    when(variableRepository
        .save(VARIABLE)).thenReturn(VARIABLE);
    when(cacheService.deleteCache(CACHE_KEY)).thenReturn(true);

    Variable variable = variableService
        .updateVariable(CommonTestVariable.MANDATORY_REQUEST, VARIABLE, ID)
        .blockingGet();

    verify(variableRepository)
        .findVariableByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);
    verify(variableRepository).save(VARIABLE);
    verify(cacheService).deleteCache(CACHE_KEY);
    verify(cacheService).createCache(CACHE_KEY, variable, 0);

    verify(cacheService).deleteCache(CACHE_KEY_ALL);
    verify(cacheService).deleteCache(CACHE_KEY_MAPPED);

    assertEquals(VARIABLE, variable);

  }


  @Test
  public void deleteVariableTestSuccess() throws Exception {
    VARIABLE.setIsDeleted(1);

    when(variableRepository
        .findVariableByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(VARIABLE);

    when(variableRepository.softDeleted(VARIABLE, ID)).thenReturn(true);

    when(cacheService.deleteCache(CACHE_KEY)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY_MAPPED)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY_ALL)).thenReturn(true);

    Boolean deleted = variableService.deleteVariable(CommonTestVariable.MANDATORY_REQUEST, ID)
        .blockingGet();
    verify(variableRepository)
        .findVariableByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);
    verify(variableRepository).softDeleted(VARIABLE, ID);
    verify(cacheService).deleteCache(CACHE_KEY);
    verify(cacheService).deleteCache(CACHE_KEY_MAPPED);
    verify(cacheService).deleteCache(CACHE_KEY_ALL);

    assertEquals(true, deleted);
  }


  @Test
  public void deleteVariableTestDataNotExist() throws Exception {

    when(variableRepository
        .findVariableByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(null);

    try {
      variableService.deleteVariable(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();
    } catch (BusinessLogicException be) {
      verify(variableRepository)
          .findVariableByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);

      assertEquals(ResponseCode.VARIABLE_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.VARIABLE_NOT_EXIST.getMessage(), be.getMessage());
    }
  }


  @Test
  public void deleteVariableTestExceptionSystemError() throws Exception {
    when(variableRepository
        .findVariableByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(VARIABLE);

    when(variableRepository.softDeleted(VARIABLE, ID)).thenReturn(false);

    try {
      variableService.deleteVariable(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();
    } catch (BusinessLogicException be) {
      verify(variableRepository)
          .findVariableByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);

      verify(variableRepository).softDeleted(VARIABLE, ID);

      assertEquals(ResponseCode.SYSTEM_ERROR.getCode(), be.getCode());
      assertEquals(ResponseCode.SYSTEM_ERROR.getMessage(), be.getMessage());
    }
  }


  @Test
  public void findVariablePaginatedTestSuccess() throws Exception {
    when(variableRepository
        .findVariableFilterPaginated(CommonTestVariable.STORE_ID,
            PARAM, InputType.DROPDOWN.getName(), PAGE, SIZE, VariableColumn.INPUT_TYPE.getValue(),
            SortDirection.ASC)).thenReturn(VARIABLE_PAGE);

    Page<Variable> variablePage = variableService
        .findVariableFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
            PARAM, InputType.DROPDOWN.getName(), PAGE, SIZE, VariableColumn.INPUT_TYPE,
            SortDirection.ASC).blockingGet();

    verify(variableRepository)
        .findVariableFilterPaginated(CommonTestVariable.STORE_ID,
            PARAM, InputType.DROPDOWN.getName(), PAGE, SIZE, VariableColumn.INPUT_TYPE.getValue(),
            SortDirection.ASC);

    assertEquals(VARIABLE_PAGE, variablePage);
  }

  @Test
  public void findVariablesWithCacheTest() throws Exception {
    when(cacheService.findCacheByKey(CACHE_KEY_ALL, List.class))
        .thenReturn(VARIABLE_LIST);

    List<Variable> variable = variableService
        .findVariables(CommonTestVariable.MANDATORY_REQUEST).blockingGet();

    verify(cacheService)
        .findCacheByKey(CACHE_KEY_ALL, List.class);
    verify(cacheService).createCache(CACHE_KEY_ALL, variable, 0);

    assertEquals(VARIABLE_LIST, variable);
  }

  @Test
  public void findVariablesWithoutCacheTest() throws Exception {
    when(cacheService.findCacheByKey(CACHE_KEY_ALL, List.class))
        .thenReturn(null);
    when(variableRepository.findAllByIsDeleted(0))
        .thenReturn(VARIABLE_LIST);

    List<Variable> variable = variableService
        .findVariables(CommonTestVariable.MANDATORY_REQUEST).blockingGet();

    verify(cacheService)
        .findCacheByKey(CACHE_KEY_ALL, List.class);
    verify(variableRepository).findAllByIsDeleted(0);
    verify(cacheService).createCache(CACHE_KEY_ALL, variable, 0);

    assertEquals(VARIABLE_LIST, variable);
  }


  @Test
  public void findVariablePaginatedTestSuccessWithoutParameter() throws Exception {
    when(variableRepository
        .findVariableFilterPaginated(CommonTestVariable.STORE_ID,
            null, null, PAGE, SIZE, null,
            null)).thenReturn(VARIABLE_PAGE);

    Page<Variable> variablePage = variableService
        .findVariableFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
            null, null, PAGE, SIZE, null,
            null).blockingGet();

    verify(variableRepository)
        .findVariableFilterPaginated(CommonTestVariable.STORE_ID,
            null, null, PAGE, SIZE, null,
            null);

    assertEquals(VARIABLE_PAGE, variablePage);
  }

  @Test
  public void findVariableByIdTestCacheResult() throws Exception {
    when(cacheService.findCacheByKey(CACHE_KEY, Variable.class))
        .thenReturn(VARIABLE);

    Variable variable = variableService
        .findVariableById(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();

    verify(cacheService).findCacheByKey(CACHE_KEY, Variable.class);

    assertEquals(VARIABLE, variable);
  }

  @Test
  public void findVariableByIdTestSuccescWithoutCacheResult() throws Exception {
    when(cacheService.findCacheByKey(CACHE_KEY, Campaign.class))
        .thenReturn(null);
    when(variableRepository
        .findVariableByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(VARIABLE);
    when(cacheService.createCache(CACHE_KEY, VARIABLE, 0)).thenReturn(true);

    Variable variable = variableService
        .findVariableById(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();

    verify(cacheService).findCacheByKey(CACHE_KEY, Variable.class);
    verify(variableRepository)
        .findVariableByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);
    verify(cacheService).createCache(CACHE_KEY, VARIABLE, 0);

    assertEquals(VARIABLE, variable);
  }

  @Test
  public void findVariableByIdTestExceptionCampaignNotExist() throws Exception {
    when(cacheService.findCacheByKey(CACHE_KEY, Variable.class))
        .thenReturn(null);
    when(variableRepository
        .findVariableByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(null);

    try {
      variableService.findVariableById(CommonTestVariable.MANDATORY_REQUEST, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(cacheService).findCacheByKey(CACHE_KEY, Variable.class);
      verify(variableRepository)
          .findVariableByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);

      assertEquals(ResponseCode.VARIABLE_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.VARIABLE_NOT_EXIST.getMessage(), be.getMessage());
    }
  }

  @Test
  public void findVariablesMappedCacheTest() throws Exception {
    when(variableRepository.findAllByIsDeleted(0))
        .thenReturn(VARIABLE_MAP);

    when(cacheService.findCacheByKey(CACHE_KEY_MAPPED, Map.class))
        .thenReturn(VARIABLE_MAPPED);

    Map<String, Variable> variableMap = variableService
        .findVariablesMapped(CommonTestVariable.MANDATORY_REQUEST)
        .blockingGet();

    verify(cacheService).findCacheByKey(CACHE_KEY_MAPPED, Map.class);

    assertEquals(VARIABLE_MAPS, variableMap);
  }

  @Test
  public void findVariablesMappedTest() throws Exception {
    when(variableRepository.findAllByIsDeleted(0))
        .thenReturn(VARIABLE_MAP);

    when(cacheService.findCacheByKey(CACHE_KEY_MAPPED, Map.class))
        .thenReturn(null);

    when(cacheService.createCache(CACHE_KEY_MAPPED, VARIABLE_MAPPED, 60)).thenReturn(true);

    Map<String, Variable> variableMap = variableService
        .findVariablesMapped(CommonTestVariable.MANDATORY_REQUEST)
        .blockingGet();

    verify(variableRepository).findAllByIsDeleted(0);
    verify(cacheService).findCacheByKey(CACHE_KEY_MAPPED, Map.class);
    verify(cacheService).createCache(CACHE_KEY_MAPPED, VARIABLE_MAPPED, 60);

    assertEquals(VARIABLE_MAPS, variableMap);
  }


  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(variableRepository);
    verifyNoMoreInteractions(cacheService);
  }
}
