package com.tl.booking.gateway.entity.dao;

import com.tl.booking.gateway.entity.constant.CollectionName;
import com.tl.booking.gateway.entity.constant.fields.PrivilegeFields;
import com.tl.booking.gateway.entity.dao.common.BaseMongo;

import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@GeneratePojoBuilder
@Document(collection = CollectionName.PRIVILEGE)
public class Privilege extends BaseMongo {

  @Field(value = PrivilegeFields.PRIVILEGE_ID)
  private String privilegeId;

  @Field(value = PrivilegeFields.PRIVILEGE_NAME)
  private String privilegeName;

  @Field(value = PrivilegeFields.PRIVILEGE_DESCRIPTION)
  private String privilegeDescription;

  public String getPrivilegeId() {
    return privilegeId;
  }

  public void setPrivilegeId(String privilegeId) {
    this.privilegeId = privilegeId;
  }

  public String getPrivilegeName() {
    return privilegeName;
  }

  public void setPrivilegeName(String privilegeName) {
    this.privilegeName = privilegeName;
  }

  public String getPrivilegeDescription() {
    return privilegeDescription;
  }

  public void setPrivilegeDescription(String privilegeDescription) {
    this.privilegeDescription = privilegeDescription;
  }

  @Override
  public String toString() {
    return "Privilege{" +
        "privilegeId='" + privilegeId + '\'' +
        ", privilegeName='" + privilegeName + '\'' +
        ", privilegeDescription='" + privilegeDescription + '\'' +
        '}';
  }
}

