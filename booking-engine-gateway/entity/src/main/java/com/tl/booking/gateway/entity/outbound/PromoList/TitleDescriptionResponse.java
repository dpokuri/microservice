package com.tl.booking.gateway.entity.outbound.PromoList;


import com.tl.booking.common.entity.CommonModel;
import com.tl.booking.gateway.entity.constant.enums.Language;

import java.io.Serializable;
import net.karneim.pojobuilder.GeneratePojoBuilder;


@GeneratePojoBuilder
public class TitleDescriptionResponse extends CommonModel implements Serializable {
  private Language lang;
  private String content;

  public Language getLang() {
    return lang;
  }

  public void setLang(Language lang) {
    this.lang = lang;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Override
  public String toString() {
    return "TitleDescriptionResponse{" +
        "lang=" + lang +
        ", content='" + content + '\'' +
        '}' + super.toString();
  }
}