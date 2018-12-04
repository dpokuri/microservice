import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.image.dao.api.SystemParameterRepository;
import com.tl.booking.image.entity.constant.enums.ResponseCode;
import com.tl.booking.image.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.image.entity.constant.unit.test.SystemParameterTestVariable;
import com.tl.booking.image.entity.dao.SystemParameter;
import com.tl.booking.image.libraries.exception.BusinessLogicException;
import com.tl.booking.image.service.impl.SystemParameterServiceImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class SystemParameterServiceImplTest {

  @InjectMocks
  private SystemParameterServiceImpl systemParameterServiceImpl;

  @Mock
  private SystemParameterRepository systemParameterRepository;

  @Test
  public void findAllTest() throws Exception {
    Pageable pageable = new PageRequest(SystemParameterTestVariable.PAGE,
        SystemParameterTestVariable.SIZE);
    List<SystemParameter> systemParameterList = new ArrayList<>();
    systemParameterList.add(SystemParameterTestVariable.SYSTEM_PARAMETER);

    Page<SystemParameter> systemParameterPage = new PageImpl<SystemParameter>(systemParameterList);

    when(this.systemParameterRepository.findByStoreId(CommonTestVariable.STORE_ID, pageable))
        .thenReturn(systemParameterPage);

    Page<SystemParameter> systemParameters = this.systemParameterServiceImpl
        .findAll(CommonTestVariable.STORE_ID, SystemParameterTestVariable.PAGE,
            SystemParameterTestVariable.SIZE);

    verify(this.systemParameterRepository)
        .findByStoreId(CommonTestVariable.STORE_ID,
            SystemParameterTestVariable.PAGEABLE);

    assertEquals(SystemParameterTestVariable.VARIABLE,
        systemParameters.getContent().get(0).getVariable());
    assertEquals(SystemParameterTestVariable.VALUE,
        systemParameters.getContent().get(0).getValue());
    assertEquals(SystemParameterTestVariable.DESCRIPTION,
        systemParameters.getContent().get(0).getDescription());
  }

  @Test
  public void findAllTestNullPageable() throws Exception {
    Pageable pageable = new PageRequest(SystemParameterTestVariable.PAGE,
        SystemParameterTestVariable.SIZE);
    List<SystemParameter> systemParameterList = new ArrayList<>();
    systemParameterList.add(SystemParameterTestVariable.SYSTEM_PARAMETER);

    List<SystemParameter> PROMO_PAGE_LIST_NULL =  Arrays.asList();

    Page<SystemParameter> systemParameterPage = new PageImpl<>(PROMO_PAGE_LIST_NULL);

    when(this.systemParameterRepository.findByStoreId(CommonTestVariable.STORE_ID, pageable))
        .thenReturn(systemParameterPage);

    try{
      this.systemParameterServiceImpl.findAll(CommonTestVariable.STORE_ID, SystemParameterTestVariable.PAGE,
          SystemParameterTestVariable.SIZE);
    }
    catch (BusinessLogicException be){
      verify(this.systemParameterRepository)
          .findByStoreId(CommonTestVariable.STORE_ID,
              SystemParameterTestVariable.PAGEABLE);
      assertEquals(ResponseCode.DATA_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.DATA_NOT_EXIST.getMessage(),
          be.getMessage());
    }


  }

  @Test
  public void findSystemParameterByStoreIdAndVariableTest() throws Exception {
    when(this.systemParameterRepository
        .findSystemParameterByStoreIdAndVariable(CommonTestVariable.STORE_ID,
            SystemParameterTestVariable.VARIABLE))
        .thenReturn(SystemParameterTestVariable.SYSTEM_PARAMETER);

    SystemParameter systemParameter = this.systemParameterServiceImpl
        .findSystemParameterByStoreId(CommonTestVariable.STORE_ID,
            SystemParameterTestVariable.VARIABLE);

    verify(this.systemParameterRepository)
        .findSystemParameterByStoreIdAndVariable(CommonTestVariable.STORE_ID,
            SystemParameterTestVariable.VARIABLE);

    assertEquals(SystemParameterTestVariable.VARIABLE, systemParameter.getVariable());
    assertEquals(SystemParameterTestVariable.VALUE, systemParameter.getValue());
    assertEquals(SystemParameterTestVariable.DESCRIPTION, systemParameter.getDescription());
  }

  @Test
  public void findSystemParameterByStoreIdAndVariableTestExceptionDataNotExist()
      throws Exception {
    when(this.systemParameterRepository
        .findSystemParameterByStoreIdAndVariable(CommonTestVariable.STORE_ID,
            SystemParameterTestVariable.VARIABLE))
        .thenReturn(null);
    try {
      this.systemParameterServiceImpl
          .findSystemParameterByStoreId(CommonTestVariable.STORE_ID,
              SystemParameterTestVariable.VARIABLE);
    } catch (BusinessLogicException be) {
      assertEquals(ResponseCode.DATA_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.DATA_NOT_EXIST.getMessage(), be.getMessage());

      verify(this.systemParameterRepository)
          .findSystemParameterByStoreIdAndVariable(CommonTestVariable.STORE_ID,
              SystemParameterTestVariable.VARIABLE);
    }
  }

  @Test
  public void createSystemParameterTest() throws Exception {
    when(this.systemParameterRepository
        .findSystemParameterByStoreIdAndVariable(CommonTestVariable.STORE_ID,
            SystemParameterTestVariable.VARIABLE))
        .thenReturn(null);

    this.systemParameterServiceImpl
        .createSystemParameter(SystemParameterTestVariable.SYSTEM_PARAMETER);

    verify(this.systemParameterRepository)
        .findSystemParameterByStoreIdAndVariable(CommonTestVariable.STORE_ID,
            SystemParameterTestVariable.VARIABLE);
    verify(this.systemParameterRepository).save(SystemParameterTestVariable.SYSTEM_PARAMETER);
  }

  @Test
  public void createSystemParameterTestExceptionDuplicateData() throws Exception {
    when(this.systemParameterRepository
        .findSystemParameterByStoreIdAndVariable(CommonTestVariable.STORE_ID,
            SystemParameterTestVariable.VARIABLE))
        .thenReturn(SystemParameterTestVariable.SYSTEM_PARAMETER);

    try {
      this.systemParameterServiceImpl
          .createSystemParameter(SystemParameterTestVariable.SYSTEM_PARAMETER);
    } catch (BusinessLogicException be) {
      assertEquals(ResponseCode.DUPLICATE_DATA.getCode(), be.getCode());
      assertEquals(ResponseCode.DUPLICATE_DATA.getMessage(), be.getMessage());

      verify(this.systemParameterRepository)
          .findSystemParameterByStoreIdAndVariable(CommonTestVariable.STORE_ID,
              SystemParameterTestVariable.VARIABLE);
    }
  }

  @Test
  public void updateSystemParameterTest() throws Exception {
    when(this.systemParameterRepository
        .findSystemParameterByStoreIdAndVariable(CommonTestVariable.STORE_ID,
            SystemParameterTestVariable.VARIABLE))
        .thenReturn(SystemParameterTestVariable.SYSTEM_PARAMETER);

    this.systemParameterServiceImpl
        .updateSystemParameter(SystemParameterTestVariable.SYSTEM_PARAMETER);

    verify(this.systemParameterRepository)
        .findSystemParameterByStoreIdAndVariable(CommonTestVariable.STORE_ID,
            SystemParameterTestVariable.VARIABLE);
    verify(this.systemParameterRepository).save(SystemParameterTestVariable.SYSTEM_PARAMETER);
  }

  @Test
  public void updateSystemParameterTestException_DataNotExist() throws Exception {
    when(this.systemParameterRepository
        .findSystemParameterByStoreIdAndVariable(CommonTestVariable.STORE_ID,
            SystemParameterTestVariable.VARIABLE)).thenReturn(null);

    try {
      this.systemParameterServiceImpl
          .updateSystemParameter(SystemParameterTestVariable.SYSTEM_PARAMETER);
    } catch (BusinessLogicException be) {
      assertEquals(ResponseCode.DATA_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.DATA_NOT_EXIST.getMessage(), be.getMessage());

      verify(this.systemParameterRepository)
          .findSystemParameterByStoreIdAndVariable(CommonTestVariable.STORE_ID,
              SystemParameterTestVariable.VARIABLE);
    }
  }

  @Test
  public void deleteByStoreIdAndVariable() throws Exception {
    this.systemParameterServiceImpl
        .deleteByStoreIdAndVariable(CommonTestVariable.STORE_ID,
            SystemParameterTestVariable.VARIABLE);

    verify(this.systemParameterRepository)
        .deleteSystemParameterByStoreIdAndVariable(CommonTestVariable.STORE_ID,
            SystemParameterTestVariable.VARIABLE);
  }

  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.systemParameterRepository);
  }
}
