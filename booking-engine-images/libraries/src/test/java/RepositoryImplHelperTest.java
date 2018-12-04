import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.image.entity.constant.enums.SortDirection;
import com.tl.booking.image.entity.constant.fields.BaseMongoFields;
import com.tl.booking.image.libraries.utility.RepositoryImplHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.test.web.servlet.MockMvc;

public class RepositoryImplHelperTest {
  private MockMvc mockMvc;

  @InjectMocks
  private RepositoryImplHelper repositoryImplHelper;

  @Test
  public void setPageableTest() throws Exception {
    Pageable setPageable = repositoryImplHelper.setPageable(0, 10, SortDirection.ASC, BaseMongoFields.ID);
    Pageable setPageableCompare = repositoryImplHelper.setPageable(0, 10, SortDirection.ASC, BaseMongoFields.ID);

    assertEquals(setPageable,setPageableCompare);
  }

  @Test
  public void setPageableTestDesc() throws Exception {
    Pageable setPageable = repositoryImplHelper.setPageable(0, 10, SortDirection.DESC, BaseMongoFields.ID);
    Pageable setPageableCompare = repositoryImplHelper.setPageable(0, 10, SortDirection.DESC, BaseMongoFields.ID);

    assertEquals(setPageable,setPageableCompare);
  }


  public static Pageable setPageable(Integer page, Integer size, SortDirection direction, String sort)
  {
    Direction setDirection = setDirection(direction);

    String setSort = setSortParam(sort);

    Integer setSize = maxSizePaginated(size);

    return new PageRequest(page, setSize, new Sort(new Order(setDirection, setSort)));
  }

  private static Integer maxSizePaginated(Integer value)
  {
    Integer size = 500;

    if(value < 500)
    {
      size = value;
    }

    return size;
  }

  private static Direction setDirection(SortDirection sort)
  {
    Direction direction = Direction.ASC;
    if(sort != null && sort.getValue() == SortDirection.DESC.getValue())
    {
      direction = Direction.DESC;
    }

    return direction;
  }

  private static String setSortParam(String sortParam)
  {
    String sort = BaseMongoFields.ID;
    if(sortParam != null)
    {
      sort = sortParam;
    }

    return sort;
  }

  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
  }
}
