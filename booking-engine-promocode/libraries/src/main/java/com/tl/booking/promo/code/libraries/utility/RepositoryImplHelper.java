package com.tl.booking.promo.code.libraries.utility;

import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.fields.BaseMongoFields;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

public class RepositoryImplHelper {

  private RepositoryImplHelper() {
    // construct class
  }

  public static Pageable setPageable(Integer page, Integer size, SortDirection direction,
      String sort) {
    Direction setDirection = setDirection(direction);

    String setSort = setSortParam(sort);

    Integer setSize = maxSizePaginated(size);

    return new PageRequest(page, setSize, new Sort(new Order(setDirection, setSort)));
  }

  public static Integer maxSizePaginated(Integer size) {
    Integer newSize = size;
    if (size > 500) {
      newSize = 500;
    }
    return newSize;
  }

  public static Direction setDirection(SortDirection sort) {
    Direction direction = Direction.ASC;
    if (sort != null && sort.getValue().equals(SortDirection.DESC.getValue())) {
      direction = Direction.DESC;
    }

    return direction;
  }

  public static String setSortParam(String sortParam) {
    String sort = BaseMongoFields.ID;
    if (sortParam != null) {
      sort = sortParam;
    }

    return sort;
  }


}
