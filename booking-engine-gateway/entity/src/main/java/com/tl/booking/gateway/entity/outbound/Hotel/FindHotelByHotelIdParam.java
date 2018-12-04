package com.tl.booking.gateway.entity.outbound.Hotel;

import com.tl.booking.common.entity.CommonModel;
import java.util.List;
import javax.validation.constraints.NotNull;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class FindHotelByHotelIdParam extends CommonModel {

  @NotNull
  private List<Integer> hotelId;

  public List<Integer> getHotelId() {
    return hotelId;
  }

  public void setHotelId(List<Integer> hotelId) {
    this.hotelId = hotelId;
  }

  @Override
  public String toString() {
    return "FindHotelByHotelIdParam{" +
        "hotelId=" + hotelId +
        '}';
  }
}
