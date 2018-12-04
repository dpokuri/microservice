package com.tl.booking.image.rest.web.model;

import com.tl.booking.common.entity.ObjectHelper;

public class CommonModel
{

  @Override
  public boolean equals(Object obj) {
    return ObjectHelper.equals(this, obj);
  }

  @Override
  public int hashCode() {
    return ObjectHelper.hashCode(this);
  }
}
