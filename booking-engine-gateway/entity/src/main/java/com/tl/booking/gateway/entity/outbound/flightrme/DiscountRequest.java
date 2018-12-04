package com.tl.booking.gateway.entity.outbound.flightrme;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class DiscountRequest extends CommonModel implements Serializable {

  @NotNull
  @Valid
  private List<PriceRangeRequest> priceRanges;

  public List<PriceRangeRequest> getPriceRanges() {
    return priceRanges;
  }

  public void setPriceRanges(
      List<PriceRangeRequest> priceRanges) {
    this.priceRanges = priceRanges;
  }

  @Override
  public String toString() {
    return "DiscountRequest{" +
        "priceRanges=" + priceRanges +
        '}' + super.toString();
  }
}
