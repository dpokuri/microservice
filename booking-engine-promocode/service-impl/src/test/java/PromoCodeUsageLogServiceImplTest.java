import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.promo.code.dao.api.PromoCodeUsageLogRepository;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeUsageLogColumn;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.PromoCodeUsageLogTestVariable;
import com.tl.booking.promo.code.entity.dao.PromoCodeUsageLog;
import com.tl.booking.promo.code.libraries.exception.BusinessLogicException;
import com.tl.booking.promo.code.service.impl.PromoCodeUsageLogServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;

public class PromoCodeUsageLogServiceImplTest extends PromoCodeUsageLogTestVariable {

  @InjectMocks
  PromoCodeUsageLogServiceImpl promoCodeUsageLogService;

  @Mock
  PromoCodeUsageLogRepository promoCodeUsageLogRepository;

  @Test
  public void createPromoCodeUsageLogTestSuccess() throws Exception {

    promoCodeUsageLogService
        .createPromoCodeUsageLog(CommonTestVariable.MANDATORY_REQUEST, CODE, DISCOUNT_AMOUNT,
            PARTNER_COST_AMOUNTS, COMPANY_COST_AMOUNTS, TOTAL_PRICE, REFERENCE_ID, USED_QTY, null);

    verify(promoCodeUsageLogRepository).save(any(PromoCodeUsageLog.class));

  }

  @Test
  public void findPromoCodeUsageLogFilterPaginated() {
    when(this.promoCodeUsageLogRepository
        .findPromoCodeUsageLogFilterPaginated(MANDATORY_REQUEST, CODE, START_DATE,
            END_DATE, PAGE, SIZE, PromoCodeUsageLogColumn.ID.getValue(), SortDirection.ASC))
        .thenReturn(PROMO_CODE_USAGE_LOG_LIST_PAGE);

    Page<PromoCodeUsageLog> promoCodeUsageLogs = promoCodeUsageLogService
        .findPromoCodeUsageLogFilterPaginated(MANDATORY_REQUEST,
            CODE, START_DATE, END_DATE, PAGE, SIZE, PromoCodeUsageLogColumn.ID, SortDirection.ASC)
        .blockingGet();

    verify(promoCodeUsageLogRepository)
        .findPromoCodeUsageLogFilterPaginated(MANDATORY_REQUEST,
            CODE, START_DATE, END_DATE, PAGE, SIZE, PromoCodeUsageLogColumn.ID.getValue(),
            SortDirection.ASC);

    assertEquals(PROMO_CODE_USAGE_LOG_LIST_PAGE, promoCodeUsageLogs);
  }

  @Test
  public void findPromoCodeUsageLogFilterPaginatedEmpty() {
    when(this.promoCodeUsageLogRepository
        .findPromoCodeUsageLogFilterPaginated(MANDATORY_REQUEST, CODE, START_DATE,
            END_DATE, PAGE, SIZE, PromoCodeUsageLogColumn.ID.getValue(), SortDirection.ASC))
        .thenReturn(PROMO_CODE_USAGE_LOG_PAGE_NULL);

    try {
      promoCodeUsageLogService
          .findPromoCodeUsageLogFilterPaginated(MANDATORY_REQUEST,
              CODE, START_DATE, END_DATE, PAGE, SIZE, PromoCodeUsageLogColumn.ID, SortDirection.ASC)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(promoCodeUsageLogRepository)
          .findPromoCodeUsageLogFilterPaginated(MANDATORY_REQUEST,
              CODE, START_DATE, END_DATE, PAGE, SIZE, PromoCodeUsageLogColumn.ID.getValue(),
              SortDirection.ASC);

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
    verifyNoMoreInteractions(promoCodeUsageLogRepository);
  }
}
