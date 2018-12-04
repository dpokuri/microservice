package com.tl.booking.image.libraries.utility;

import com.tl.booking.image.entity.constant.enums.SortDirection;
import com.tl.booking.image.entity.constant.fields.BaseMongoFields;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

public class RepositoryImplHelper {

  private RepositoryImplHelper() {}

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

  public static String setSortParam(String sortParam)
  {
    String sort = BaseMongoFields.ID;
    if(sortParam != null)
    {
      sort = sortParam;
    }

    return sort;
  }

}
