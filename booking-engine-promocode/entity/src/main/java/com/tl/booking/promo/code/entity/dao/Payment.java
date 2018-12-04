package com.tl.booking.promo.code.entity.dao;

import com.tl.booking.promo.code.entity.constant.CollectionName;
import com.tl.booking.promo.code.entity.constant.fields.PaymentFields;
import com.tl.booking.promo.code.entity.dao.common.BaseMongo;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@GeneratePojoBuilder
@Document(collection = CollectionName.PAYMENT)
public class Payment extends BaseMongo {

  @Field(value = PaymentFields.PAYMENT_ID)
  private String paymentId;
  @Field(value = PaymentFields.NAME)
  private String name;
  @Field(value = PaymentFields.USE_BIN_NUMBER)
  private Boolean useBinNumber;

  @Override
  public String toString() {
    return "Payment{" +
        "paymentId='" + paymentId + '\'' +
        ", name='" + name + '\'' +
        ", useBinNumber=" + useBinNumber +
        '}';
  }

  public String getPaymentId() {
    return paymentId;
  }

  public void setPaymentId(String paymentId) {
    this.paymentId = paymentId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean getUseBinNumber() {
    return useBinNumber;
  }

  public void setUseBinNumber(Boolean useBinNumber) {
    this.useBinNumber = useBinNumber;
  }
}
