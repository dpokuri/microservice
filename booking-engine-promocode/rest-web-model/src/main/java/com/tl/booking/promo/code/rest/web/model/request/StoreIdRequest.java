package com.tl.booking.promo.code.rest.web.model.request;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public class StoreIdRequest extends CommonModel implements Serializable {

  @NotBlank
  @NotEmpty
  @NotNull
  private String label;

  @NotBlank
  @NotEmpty
  @NotNull
  private String value;

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "StoreIdRequest{" +
        "label='" + label + '\'' +
        ", value='" + value + '\'' +
        '}';
  }
}
