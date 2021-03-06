package com.tl.booking.promo.code.entity.dao;

import com.tl.booking.common.entity.CommonModel;
import com.tl.booking.promo.code.entity.constant.enums.Language;
import java.io.Serializable;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class ResponseMessageResponse extends CommonModel implements Serializable {

  Language lang;
  String content;

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
    return "ResponseMessage{" +
        "lang=" + lang +
        ", content='" + content + '\'' +
        '}';
  }
}
