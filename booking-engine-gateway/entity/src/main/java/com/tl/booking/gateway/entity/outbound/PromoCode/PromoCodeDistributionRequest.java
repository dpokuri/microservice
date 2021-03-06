package com.tl.booking.gateway.entity.outbound.PromoCode;

import com.tl.booking.common.entity.CommonModel;
import com.tl.booking.gateway.entity.configuration.NotEmptySetFields;

import java.io.Serializable;
import java.util.Set;
import javax.validation.constraints.NotNull;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@GeneratePojoBuilder
public class PromoCodeDistributionRequest extends CommonModel implements Serializable {

  @NotNull
  @NotEmpty
  @NotBlank
  private String storeId;

  @NotNull
  @NotEmpty
  @NotEmptySetFields
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
    return "PromoCodeDistributionRequest{" +
        "storeId='" + storeId + '\'' +
        ", channelId=" + channelId +
        '}' + super.toString();
  }
}
