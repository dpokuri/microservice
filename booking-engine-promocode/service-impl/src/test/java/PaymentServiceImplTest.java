import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.promo.code.dao.api.PaymentRepository;
import com.tl.booking.promo.code.entity.constant.enums.PaymentColumn;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.PaymentTestVariable;
import com.tl.booking.promo.code.entity.dao.Payment;
import com.tl.booking.promo.code.libraries.exception.BusinessLogicException;
import com.tl.booking.promo.code.service.api.CacheService;
import com.tl.booking.promo.code.service.impl.PaymentServiceImpl;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;

public class PaymentServiceImplTest extends PaymentTestVariable {

  @InjectMocks
  PaymentServiceImpl paymentService;

  @Mock
  PaymentRepository paymentRepository;

  @Mock
  CacheService cacheService;

  @Test
  public void createPaymentTestSuccess() throws Exception {
    when(this.paymentRepository
        .findPaymentByStoreIdAndNameAndIsDeleted(CommonTestVariable.STORE_ID,
            PAYMENT.getName(), 0)).thenReturn(null);

    when(paymentRepository
        .save(PAYMENT)).thenReturn(PAYMENT);

    when(cacheService.deleteCache(CACHE_KEY_FIND_ALL)).thenReturn(true);

    when(cacheService.createCache(CACHE_KEY, PAYMENT, 0)).thenReturn(true);

    Payment payment = paymentService
        .createPayment(CommonTestVariable.MANDATORY_REQUEST, PAYMENT).blockingGet();

    verify(paymentRepository)
        .findPaymentByStoreIdAndNameAndIsDeleted(CommonTestVariable.STORE_ID,
            PAYMENT.getName(), 0);

    verify(paymentRepository).save(PAYMENT);

    verify(cacheService).deleteCache(CACHE_KEY_FIND_ALL);

    verify(cacheService).createCache(CACHE_KEY, PAYMENT, 0);

    assertEquals(PAYMENT, payment);
  }

  @Test
  public void createPaymentTestErrorExistPayment() throws Exception {
    when(paymentRepository
        .findPaymentByStoreIdAndNameAndIsDeleted(CommonTestVariable.STORE_ID, NAME, 0))
        .thenReturn(PAYMENT);

    try {
      paymentService.createPayment(CommonTestVariable.MANDATORY_REQUEST, PAYMENT)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(paymentRepository)
          .findPaymentByStoreIdAndNameAndIsDeleted(CommonTestVariable.STORE_ID, NAME, 0);

      assertEquals(ResponseCode.DUPLICATE_DATA.getCode(), be.getCode());
      assertEquals(ResponseCode.DUPLICATE_DATA.getMessage(), be.getMessage());
    }

  }

  @Test
  public void createPaymentTestSystemError() throws Exception {

    when(paymentRepository
        .findPaymentByStoreIdAndNameAndIsDeleted(CommonTestVariable.STORE_ID, NAME, 0))
        .thenReturn(null);

    when(paymentRepository
        .save(PAYMENT)).thenReturn(null);
    try {
      paymentService.createPayment(CommonTestVariable.MANDATORY_REQUEST, PAYMENT)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(paymentRepository)
          .findPaymentByStoreIdAndNameAndIsDeleted(CommonTestVariable.STORE_ID, NAME, 0);
      verify(paymentRepository).save(PAYMENT);

      assertEquals(ResponseCode.SYSTEM_ERROR.getCode(), be.getCode());
      assertEquals(ResponseCode.SYSTEM_ERROR.getMessage(), be.getMessage());
    }

  }

  @Test
  public void updatePaymentTestSuccess() throws Exception {
    when(paymentRepository.findPaymentByStoreIdAndIdAndIsDeleted(
        CommonTestVariable.STORE_ID, ID, 0)).thenReturn(PAYMENT);

    when(paymentRepository
        .save(PAYMENT)).thenReturn(PAYMENT);

    when(cacheService.deleteCache(CACHE_KEY_FIND_ALL)).thenReturn(true);

    when(cacheService.createCache(CACHE_KEY, PAYMENT, 0)).thenReturn(true);

    Payment payment = paymentService
        .updatePayment(CommonTestVariable.MANDATORY_REQUEST, PAYMENT, ID).blockingGet();
    verify(paymentRepository).findPaymentByStoreIdAndIdAndIsDeleted(
        CommonTestVariable.STORE_ID, ID, 0);
    verify(paymentRepository).save(PAYMENT);

    verify(cacheService).deleteCache(CACHE_KEY_FIND_ALL);

    verify(cacheService).createCache(CACHE_KEY, PAYMENT, 0);

    assertEquals(PAYMENT, payment);
  }

  @Test
  public void updatePaymentTestErrorNameIsExistInOtherDocument() throws Exception {
    when(paymentRepository.findPaymentByStoreIdAndIdAndIsDeleted(
        CommonTestVariable.STORE_ID, ID, 0)).thenReturn(PAYMENT);
    when(paymentRepository
        .findPaymentByStoreIdAndNameAndIsDeleted(CommonTestVariable.STORE_ID, NAME_2, 0))
        .thenReturn(PAYMENT_2);

    try {
      paymentService.updatePayment(CommonTestVariable.MANDATORY_REQUEST, PAYMENT_2, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(paymentRepository).findPaymentByStoreIdAndIdAndIsDeleted(
          CommonTestVariable.STORE_ID, ID, 0);
      verify(paymentRepository)
          .findPaymentByStoreIdAndNameAndIsDeleted(CommonTestVariable.STORE_ID, NAME_2, 0);

      assertEquals(ResponseCode.NAME_EXIST_OTHER_RECORD.getCode(), be.getCode());
      assertEquals(ResponseCode.NAME_EXIST_OTHER_RECORD.getMessage(), be.getMessage());
    }

  }

  @Test
  public void updatePaymentTestExceptionSystemError() throws Exception {
    when(paymentRepository
        .findPaymentByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(PAYMENT);

    when(paymentRepository
        .save(PAYMENT)).thenReturn(null);

    try {
      paymentService.updatePayment(CommonTestVariable.MANDATORY_REQUEST, PAYMENT, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(paymentRepository)
          .findPaymentByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);
      verify(paymentRepository).save(PAYMENT);

      assertEquals(ResponseCode.SYSTEM_ERROR.getCode(), be.getCode());
      assertEquals(ResponseCode.SYSTEM_ERROR.getMessage(), be.getMessage());
    }

  }

  @Test
  public void updatePaymentTestNotExist() throws Exception {

    when(paymentRepository
        .findPaymentByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(null);

    try {
      paymentService
          .updatePayment(CommonTestVariable.MANDATORY_REQUEST, PAYMENT, ID).blockingGet();
    } catch (BusinessLogicException be) {
      verify(paymentRepository)
          .findPaymentByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);

      assertEquals(ResponseCode.PAYMENT_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.PAYMENT_NOT_EXIST.getMessage(), be.getMessage());
    }
  }

  @Test
  public void deletePaymentTestSuccess() throws Exception {
    PAYMENT.setIsDeleted(1);

    when(paymentRepository
        .findPaymentByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(PAYMENT);

    when(paymentRepository.softDeleted(PAYMENT, ID)).thenReturn(true);

    when(cacheService.deleteCache(CACHE_KEY_FIND_ALL)).thenReturn(true);

    when(cacheService.deleteCache(CACHE_KEY)).thenReturn(true);

    Boolean payment = paymentService
        .deletePayment(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();

    verify(paymentRepository)
        .findPaymentByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);
    verify(paymentRepository).softDeleted(PAYMENT, ID);
    verify(cacheService).deleteCache(CACHE_KEY_FIND_ALL);
    verify(cacheService).deleteCache(CACHE_KEY);

    assertEquals(true, payment);
  }

  @Test
  public void deletePaymentTestExceptionSystemError() throws Exception {

    when(paymentRepository
        .findPaymentByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(PAYMENT);

    when(paymentRepository.softDeleted(PAYMENT, ID)).thenReturn(false);

    try {
      paymentService.deletePayment(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();
    } catch (BusinessLogicException be) {
      verify(paymentRepository)
          .findPaymentByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);
      verify(paymentRepository).softDeleted(PAYMENT, ID);

      assertEquals(ResponseCode.SYSTEM_ERROR.getCode(), be.getCode());
      assertEquals(ResponseCode.SYSTEM_ERROR.getMessage(), be.getMessage());
    }
  }

  @Test
  public void deletePaymentTestNotExist() throws Exception {

    when(paymentRepository
        .findPaymentByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(null);

    try {
      paymentService
          .deletePayment(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();
    } catch (BusinessLogicException be) {
      verify(paymentRepository)
          .findPaymentByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);

      assertEquals(ResponseCode.PAYMENT_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.PAYMENT_NOT_EXIST.getMessage(), be.getMessage());
    }
  }

  @Test
  public void findPaymentByIdTestCacheResult() throws Exception {
    when(cacheService.findCacheByKey(CACHE_KEY, Payment.class))
        .thenReturn(PAYMENT);

    Payment payment = paymentService
        .findPaymentById(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();

    verify(cacheService).findCacheByKey(CACHE_KEY, Payment.class);

    assertEquals(PAYMENT, payment);
  }

  @Test
  public void findPaymentByIdTestNonCacheResult() throws Exception {
    when(cacheService.findCacheByKey(CACHE_KEY, Payment.class))
        .thenReturn(null);
    when(paymentRepository
        .findPaymentByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(PAYMENT);
    when(cacheService.createCache(CACHE_KEY, PAYMENT, 0)).thenReturn(true);

    Payment payment = paymentService
        .findPaymentById(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();

    verify(cacheService).findCacheByKey(CACHE_KEY, Payment.class);
    verify(paymentRepository)
        .findPaymentByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);
    verify(cacheService).createCache(CACHE_KEY, PAYMENT, 0);

    assertEquals(PAYMENT, payment);
  }

  @Test
  public void findAllPaymentTestNonCacheResult() throws Exception {
    when(cacheService.findCacheByKey(CACHE_KEY_FIND_ALL, List.class))
        .thenReturn(null);
    when(paymentRepository.findByIsDeletedAndStoreId(0, CommonTestVariable.STORE_ID))
        .thenReturn(PAYMENT_LIST_IS_DELETED);
    when(cacheService.createCache(CACHE_KEY_FIND_ALL, PAYMENT_LIST_IS_DELETED, 0)).thenReturn(true);

    List<Payment> payment = paymentService
        .findPayments(CommonTestVariable.MANDATORY_REQUEST).blockingGet();

    verify(cacheService).findCacheByKey(CACHE_KEY_FIND_ALL, List.class);
    verify(paymentRepository).findByIsDeletedAndStoreId(0, CommonTestVariable.STORE_ID);
    verify(cacheService).createCache(CACHE_KEY_FIND_ALL, PAYMENT_LIST_IS_DELETED, 0);

    assertEquals(PAYMENT_LIST_IS_DELETED, payment);
  }

  @Test
  public void findPaymentByIdTestExceptionPaymentNotExist() throws Exception {
    when(cacheService.findCacheByKey(CACHE_KEY, Payment.class))
        .thenReturn(null);
    when(paymentRepository
        .findPaymentByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(null);

    try {
      paymentService
          .findPaymentById(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();
    } catch (BusinessLogicException be) {
      verify(cacheService).findCacheByKey(CACHE_KEY, Payment.class);
      verify(paymentRepository)
          .findPaymentByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);

      assertEquals(ResponseCode.PAYMENT_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.PAYMENT_NOT_EXIST.getMessage(), be.getMessage());
    }
  }

  @Test
  public void findPaymentPaginatedTestSuccess() throws Exception {
    when(paymentRepository
        .findPaymentFilterPaginated(CommonTestVariable.STORE_ID, NAME, PAYMENT_ID, PAGE,
            SIZE, PaymentColumn.ID.getValue(), SortDirection.ASC))
        .thenReturn(PAYMENT_PAGE);

    Page<Payment> paymentPage = paymentService
        .findPaymentFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
            NAME, PAYMENT_ID, PAGE, SIZE, PaymentColumn.ID,
            SortDirection.ASC).blockingGet();

    verify(paymentRepository)
        .findPaymentFilterPaginated(CommonTestVariable.STORE_ID, NAME, PAYMENT_ID, PAGE,
            SIZE, PaymentColumn.ID.getValue(), SortDirection.ASC);

    assertEquals(PAYMENT_PAGE, paymentPage);
  }

  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(paymentRepository);
    verifyNoMoreInteractions(cacheService);
  }
}
