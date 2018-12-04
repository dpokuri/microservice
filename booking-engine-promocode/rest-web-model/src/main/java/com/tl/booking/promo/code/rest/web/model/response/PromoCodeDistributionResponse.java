package com.tl.booking.promo.code.rest.web.model.response;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import java.util.Set;

public class PromoCodeDistributionResponse extends CommonModel implements Serializable {

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
    return "PromoCodeDistributionResponse{" +
        "storeId='" + storeId + '\'' +
        ", channelId=" + channelId +
        '}' + super.toString();
  }
}
