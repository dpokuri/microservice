package com.tl.booking.promo.code.entity;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import java.util.Set;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class PromoCodeDistribution extends CommonModel implements Serializable {

  private String storeId;
  private Set<String> channelId;

  public String getStoreId() {
    return storeId;
  }

  public void setStoreId(String storeId) {
    this.storeId = storeId;
  }

  public Set<String> getChannelId() {
    return channelId;
  }

  public void setChannelId(Set<String> channelId) {
    this.channelId = channelId;
  }

  @Override
  public String toString() {
    return "PromoCodeDistribution{" +
        "storeId='" + storeId + '\'' +
        ", channelId=" + channelId +
        '}';
  }
}
