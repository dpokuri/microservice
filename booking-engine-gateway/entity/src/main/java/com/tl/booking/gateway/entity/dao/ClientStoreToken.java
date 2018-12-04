package com.tl.booking.gateway.entity.dao;

import com.tl.booking.gateway.entity.constant.CollectionName;
import com.tl.booking.gateway.entity.constant.fields.ClientTokenFields;
import com.tl.booking.gateway.entity.dao.common.BaseMongo;

import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@GeneratePojoBuilder
@Document(collection = CollectionName.CLIENT_STORE_TOKEN)
public class ClientStoreToken extends BaseMongo {

  @Field(value = ClientTokenFields.CLIENT_TOKEN)
  private String clientToken;

  public String getClientToken() {
    return clientToken;
  }

  public void setClientToken(String clientToken) {
    this.clientToken = clientToken;
  }

  @Override
  public String toString() {
    return "ClientStoreToken{" +
        "clientToken='" + clientToken + '\'' +
        '}' + super.toString();
  }

}

