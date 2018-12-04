import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.mongodb.WriteResult;
import com.tl.booking.gateway.dao.api.SystemParameterRepository;
import com.tl.booking.gateway.libraries.exception.BusinessLogicException;
import com.tl.booking.gateway.service.impl.SystemParameterServiceImpl;
import com.tl.booking.gateway.entity.constant.enums.ResponseCode;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.SystemParameterTestVariable;
import com.tl.booking.gateway.entity.dao.SystemParameter;

import java.util.ArrayList;
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

public class SystemParameterServiceImplTest extends SystemParameterTestVariable {

  @InjectMocks
  SystemParameterServiceImpl systemParameterServiceImpl;

  @Mock
  SystemParameterRepository systemParameterRepository;

  @Test
  public void findAllSystemParametersByStoreIdTest() throws Exception {
    Pageable pageable = new PageRequest(this.PAGE,
        this.SIZE);
    List<SystemParameter> systemParameterList = new ArrayList<>();
    systemParameterList.add(this.SYSTEM_PARAMETER);

    Page<SystemParameter> systemParameterPage = new PageImpl<SystemParameter>(systemParameterList);

    when(this.systemParameterRepository
        .findSystemParametersByStoreIdAndIsDeleted(CommonTestVariable.STORE_ID, 0, pageable))
        .thenReturn(systemParameterPage);

    Page<SystemParameter> systemParameters = this.systemParameterServiceImpl
        .findAllSystemParametersByStoreId(CommonTestVariable.STORE_ID, this.PAGE,
            this.SIZE);

    verify(this.systemParameterRepository)
        .findSystemParametersByStoreIdAndIsDeleted(CommonTestVariable.STORE_ID, 0,
            this.PAGEABLE);

    assertEquals(this.VARIABLE,
        systemParameters.getContent().get(0).getVariable());
    assertEquals(this.VALUE,
        systemParameters.getContent().get(0).getValue());
    assertEquals(this.DESCRIPTION,
        systemParameters.getContent().get(0).getDescription());
  }

  @Test
  public void findAllSystemParametersByStoreIdTestExceptionDataNotExist() throws Exception {
    Pageable pageable = new PageRequest(this.PAGE,
        this.SIZE);
    List<SystemParameter> systemParameterList = new ArrayList<>();

    Page<SystemParameter> systemParameterPage = new PageImpl<SystemParameter>(systemParameterList);

    when(this.systemParameterRepository
        .findSystemParametersByStoreIdAndIsDeleted(CommonTestVariable.STORE_ID, 0, pageable))
        .thenReturn(systemParameterPage);

    try {
      this.systemParameterServiceImpl
          .findAllSystemParametersByStoreId(CommonTestVariable.STORE_ID, this.PAGE,
              this.SIZE);
    } catch (BusinessLogicException be) {
      verify(this.systemParameterRepository)
          .findSystemParametersByStoreIdAndIsDeleted(CommonTestVariable.STORE_ID, 0,
              this.PAGEABLE);

      assertEquals(ResponseCode.DATA_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.DATA_NOT_EXIST.getMessage(), be.getMessage());
    }
  }

  @Test
  public void findSystemParameterByStoreIdAndVariableTest() throws Exception {
    when(this.systemParameterRepository
        .findSystemParameterByStoreIdAndVariableAndIsDeleted(CommonTestVariable.STORE_ID,
            this.VARIABLE, 0))
        .thenReturn(this.SYSTEM_PARAMETER);

    SystemParameter systemParameter = this.systemParameterServiceImpl
        .findSystemParameterByStoreIdAndVariable(CommonTestVariable.STORE_ID,
            this.VARIABLE);

    verify(this.systemParameterRepository)
        .findSystemParameterByStoreIdAndVariableAndIsDeleted(CommonTestVariable.STORE_ID,
            this.VARIABLE, 0);

    assertEquals(this.VARIABLE, systemParameter.getVariable());
    assertEquals(this.VALUE, systemParameter.getValue());
    assertEquals(this.DESCRIPTION, systemParameter.getDescription());
  }

  @Test
  public void findSystemParameterByStoreIdAndVariableTestExceptionDataNotExist()
      throws Exception {
    when(this.systemParameterRepository
        .findSystemParameterByStoreIdAndVariableAndIsDeleted(CommonTestVariable.STORE_ID,
            this.VARIABLE, 0))
        .thenReturn(null);
    try {
      this.systemParameterServiceImpl
          .findSystemParameterByStoreIdAndVariable(CommonTestVariable.STORE_ID,
              this.VARIABLE);
    } catch (BusinessLogicException be) {
      assertEquals(ResponseCode.DATA_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.DATA_NOT_EXIST.getMessage(), be.getMessage());

      verify(this.systemParameterRepository)
          .findSystemParameterByStoreIdAndVariableAndIsDeleted(CommonTestVariable.STORE_ID,
              this.VARIABLE, 0);
    }
  }

  @Test
  public void findSystemParameterByIdTest() throws Exception {
    when(this.systemParameterRepository
        .findSystemParameterByIdAndIsDeleted(this.ID, 0))
        .thenReturn(this.SYSTEM_PARAMETER);

    SystemParameter systemParameter = this.systemParameterServiceImpl
        .findSystemParameterById(this.ID);

    verify(this.systemParameterRepository)
        .findSystemParameterByIdAndIsDeleted(this.ID, 0);

    assertEquals(this.VARIABLE, systemParameter.getVariable());
    assertEquals(this.VALUE, systemParameter.getValue());
    assertEquals(this.DESCRIPTION, systemParameter.getDescription());
  }

  @Test
  public void findSystemParameterByIdExceptionDataNotExist()
      throws Exception {
    when(this.systemParameterRepository
        .findSystemParameterByIdAndIsDeleted(this.ID, 0))
        .thenReturn(null);
    try {
      this.systemParameterServiceImpl.findSystemParameterById(this.ID);
    } catch (BusinessLogicException be) {
      assertEquals(ResponseCode.DATA_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.DATA_NOT_EXIST.getMessage(), be.getMessage());

      verify(this.systemParameterRepository)
          .findSystemParameterByIdAndIsDeleted(this.ID, 0);
    }
  }

  @Test
  public void createSystemParameterTest() throws Exception {
    when(this.systemParameterRepository
        .findSystemParameterByStoreIdAndVariableAndIsDeleted(CommonTestVariable.STORE_ID,
            this.VARIABLE, 0)).thenReturn(null);
    when(this.systemParameterRepository.save(this.SYSTEM_PARAMETER))
        .thenReturn(this.SYSTEM_PARAMETER);

    SystemParameter systemParameter = this.systemParameterServiceImpl
        .createSystemParameter(this.SYSTEM_PARAMETER);

    verify(this.systemParameterRepository)
        .findSystemParameterByStoreIdAndVariableAndIsDeleted(CommonTestVariable.STORE_ID,
            this.VARIABLE, 0);
    verify(this.systemParameterRepository).save(this.SYSTEM_PARAMETER);

    assertEquals(this.SYSTEM_PARAMETER, systemParameter);
  }

  @Test
  public void createSystemParameterTestExceptionDuplicateData() throws Exception {
    when(this.systemParameterRepository
        .findSystemParameterByStoreIdAndVariableAndIsDeleted(CommonTestVariable.STORE_ID,
            this.VARIABLE, 0))
        .thenReturn(this.SYSTEM_PARAMETER);

    try {
      this.systemParameterServiceImpl
          .createSystemParameter(this.SYSTEM_PARAMETER);
    } catch (BusinessLogicException be) {
      assertEquals(ResponseCode.DUPLICATE_DATA.getCode(), be.getCode());
      assertEquals(ResponseCode.DUPLICATE_DATA.getMessage(), be.getMessage());

      verify(this.systemParameterRepository)
          .findSystemParameterByStoreIdAndVariableAndIsDeleted(CommonTestVariable.STORE_ID,
              this.VARIABLE, 0);
    }
  }

  @Test
  public void updateSystemParameterTest() throws Exception {
    when(this.systemParameterRepository.findSystemParameterByIdAndIsDeleted(this.ID, 0))
        .thenReturn(this.SYSTEM_PARAMETER);
    when(this.systemParameterRepository
        .findSystemParameterByStoreIdAndVariableAndIsDeleted(CommonTestVariable.STORE_ID,
            this.VARIABLE, 0)).thenReturn(null);
    when(this.systemParameterRepository.save(this.SYSTEM_PARAMETER))
        .thenReturn(this.SYSTEM_PARAMETER);

    SystemParameter systemParameter = this.systemParameterServiceImpl
        .updateSystemParameter(this.SYSTEM_PARAMETER, this.ID);

    verify(this.systemParameterRepository).findSystemParameterByIdAndIsDeleted(this.ID, 0);
    verify(this.systemParameterRepository)
        .findSystemParameterByStoreIdAndVariableAndIsDeleted(CommonTestVariable.STORE_ID,
            this.VARIABLE, 0);
    verify(this.systemParameterRepository).save(this.SYSTEM_PARAMETER);

    assertEquals(this.SYSTEM_PARAMETER, systemParameter);
  }

  @Test
  public void updateSystemParameterTestExceptionDataNotExist() throws Exception {
    when(this.systemParameterRepository.findSystemParameterByIdAndIsDeleted(this.ID, 0))
        .thenReturn(null);

    try {
      this.systemParameterServiceImpl
          .updateSystemParameter(this.SYSTEM_PARAMETER, this.ID);
    } catch (BusinessLogicException be) {
      assertEquals(ResponseCode.DATA_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.DATA_NOT_EXIST.getMessage(), be.getMessage());

      verify(this.systemParameterRepository).findSystemParameterByIdAndIsDeleted(this.ID, 0);
    }
  }

  @Test
  public void updateSystemParameterTestExceptionDuplicateData() throws Exception {
    when(this.systemParameterRepository.findSystemParameterByIdAndIsDeleted(this.ID, 0))
        .thenReturn(this.SYSTEM_PARAMETER);
    when(this.systemParameterRepository
        .findSystemParameterByStoreIdAndVariableAndIsDeleted(CommonTestVariable.STORE_ID,
            this.VARIABLE, 0))
        .thenReturn(this.SYSTEM_PARAMETER);

    try {
      this.systemParameterServiceImpl
          .updateSystemParameter(this.SYSTEM_PARAMETER, this.ID);
    } catch (BusinessLogicException be) {
      assertEquals(ResponseCode.DUPLICATE_DATA.getCode(), be.getCode());
      assertEquals(ResponseCode.DUPLICATE_DATA.getMessage(), be.getMessage());

      verify(this.systemParameterRepository).findSystemParameterByIdAndIsDeleted(this.ID, 0);
      verify(this.systemParameterRepository)
          .findSystemParameterByStoreIdAndVariableAndIsDeleted(CommonTestVariable.STORE_ID,
              this.VARIABLE, 0);
    }
  }

  @Test
  public void deleteSystemParameterByIdTest() throws Exception {
    when(this.systemParameterRepository.findSystemParameterByIdAndIsDeleted(this.ID, 0))
        .thenReturn(this.SYSTEM_PARAMETER);
    when(this.systemParameterRepository.updateSystemParameterIsDeleteById(this.ID, 1))
        .thenReturn(new WriteResult(1, true, this.SYSTEM_PARAMETER));

    Boolean deleted = this.systemParameterServiceImpl
        .deleteSystemParameterById(SystemParameterTestVariable.ID);

    verify(this.systemParameterRepository).findSystemParameterByIdAndIsDeleted(this.ID, 0);
    verify(this.systemParameterRepository).updateSystemParameterIsDeleteById(this.ID, 1);

    assertEquals(true, deleted);
  }

  @Test
  public void deleteSystemParameterByIdTestFailedToUpdate() throws Exception {
    when(this.systemParameterRepository.findSystemParameterByIdAndIsDeleted(this.ID, 0))
        .thenReturn(this.SYSTEM_PARAMETER);
    when(this.systemParameterRepository.updateSystemParameterIsDeleteById(this.ID, 1))
        .thenReturn(new WriteResult(0, false, null));

    Boolean deleted = this.systemParameterServiceImpl
        .deleteSystemParameterById(SystemParameterTestVariable.ID);

    verify(this.systemParameterRepository).findSystemParameterByIdAndIsDeleted(this.ID, 0);
    verify(this.systemParameterRepository).updateSystemParameterIsDeleteById(this.ID, 1);

    assertEquals(false, deleted);
  }

  @Test
  public void deleteSystemParameterByIdTestExceptionDataNotExist() throws Exception {
    when(this.systemParameterRepository.findSystemParameterByIdAndIsDeleted(this.ID, 0))
        .thenReturn(null);
    try {
      this.systemParameterServiceImpl
          .deleteSystemParameterById(SystemParameterTestVariable.ID);
    } catch (BusinessLogicException be) {
      verify(this.systemParameterRepository).findSystemParameterByIdAndIsDeleted(this.ID, 0);

      assertEquals(ResponseCode.DATA_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.DATA_NOT_EXIST.getMessage(), be.getMessage());
    }
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
