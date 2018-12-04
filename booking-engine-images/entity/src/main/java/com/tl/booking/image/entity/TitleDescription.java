package com.tl.booking.image.entity;

import com.tl.booking.common.entity.CommonModel;
import com.tl.booking.image.entity.constant.enums.Language;
import java.io.Serializable;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class TitleDescription extends CommonModel implements Serializable {
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
    return "TitleDescription{" +
        "lang=" + lang +
        ", content='" + content + '\'' +
        '}' + super.toString();
  }
}
