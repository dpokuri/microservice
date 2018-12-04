package com.tl.booking.gateway.entity.outbound.Hotel;

import com.tl.booking.common.entity.CommonModel;
import com.tl.booking.gateway.entity.constant.fields.HotelFields;

import java.util.HashMap;
import java.util.Map;
import javax.validation.constraints.NotNull;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class FindHotelByAddressParam extends CommonModel {

  @NotNull
  private String countryId;

  @NotNull
  private String provinceId = "0";

  @NotNull
  private String cityId = "0";

  @NotNull
  private String areaId = "0";

  public String getCountryId() {
    return countryId;
  }

  public void setCountryId(String countryId) {
    this.countryId = countryId;
  }

  public String getProvinceId() {
    return provinceId;
  }

  public void setProvinceId(String provinceId) {
    this.provinceId = provinceId;
  }

  public String getCityId() {
    return cityId;
  }

  public void setCityId(String cityId) {
    this.cityId = cityId;
  }

  public String getAreaId() {
    return areaId;
  }

  public void setAreaId(String areaId) {
    this.areaId = areaId;
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  public Map<String, String> getFindHotelByAddressAsMap() {
    Map<String, String> params = new HashMap<>();
    params.put(HotelFields.COUNTRY_ID, getCountryId());
    params.put(HotelFields.PROVINCE_ID, getProvinceId());
    params.put(HotelFields.CITY_ID, getCityId());
    params.put(HotelFields.AREA_ID, getAreaId());

    return params;
  }
}
