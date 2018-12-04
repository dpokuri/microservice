package com.tl.booking.gateway.entity.outbound.flightrme;

import com.tl.booking.common.entity.CommonModel;
import com.tl.booking.gateway.entity.outbound.BaseMongoResponse;

import java.io.Serializable;
import java.util.List;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class DiscountResponse extends BaseMongoResponse {

  private List<PriceRangeResponse> priceRanges;

  public List<PriceRangeResponse> getPriceRanges() {
    return priceRanges;
  }

  public void setPriceRanges(
      List<PriceRangeResponse> priceRanges) {
    this.priceRanges = priceRanges;
  }

  @Override
  public String toString() {
    return "DiscountResponse{" +
        "priceRanges=" + priceRanges +
        '}' + super.toString();
  }
}
