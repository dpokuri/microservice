import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.fields.BaseMongoFields;
import com.tl.booking.promo.code.libraries.utility.RepositoryImplHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

public class RepositoryImplHelperTest {

  private MockMvc mockMvc;

  @InjectMocks
  private RepositoryImplHelper repositoryImplHelper;

  @Test
  public void setPageableTest() throws Exception {
    Pageable setPageable = repositoryImplHelper
        .setPageable(0, 10, SortDirection.ASC, BaseMongoFields.ID);

    String pageableSort = setPageable.getSort().toString();

    assertEquals(pageableSort, "_id: ASC");
  }

  @Test
  public void setPageableTestDESC() throws Exception {
    Pageable setPageable = repositoryImplHelper
        .setPageable(0, 10, SortDirection.DESC, BaseMongoFields.ID);

    String pageableSort = setPageable.getSort().toString();

    assertEquals(pageableSort, "_id: DESC");
  }

  @Test
  public void maxSizePaginatedTestMoreThanLimit() throws Exception {
    Pageable setPageable = repositoryImplHelper
        .setPageable(0, 600, SortDirection.DESC, BaseMongoFields.ID);

    String pageableSort = setPageable.getSort().toString();

    assertEquals(pageableSort, "_id: DESC");
  }


  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
  }
}
