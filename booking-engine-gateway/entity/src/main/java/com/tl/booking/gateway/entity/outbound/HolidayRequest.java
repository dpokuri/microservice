package com.tl.booking.gateway.entity.outbound;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.Pattern;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.NotBlank;

@GeneratePojoBuilder
public class HolidayRequest extends CommonModel implements Serializable {

  @NotBlank
  @Pattern(regexp = "(([12][0-9]|3[01]|0?[1-9])-(0?[1-9]|1[012])-(?:19|20)\\\\d\\\\d)")
  private String date;

  @NotBlank
  private List<HolidayDescription> descriptions;

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public List<HolidayDescription> getDescriptions() {
    return descriptions;
  }

  public void setDescriptions(List<HolidayDescription> descriptions) {
    this.descriptions = descriptions;
  }

  @Override
  public String toString() {
    return "HolidayRequest{" +
        "date='" + date + '\'' +
        ", descriptions=" + descriptions +
        '}' + super.toString();
  }
}
