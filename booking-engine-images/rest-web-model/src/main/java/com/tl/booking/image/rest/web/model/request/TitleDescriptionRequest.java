package com.tl.booking.image.rest.web.model.request;

import com.tl.booking.common.entity.CommonModel;
import com.tl.booking.image.entity.constant.enums.Language;
import java.io.Serializable;
import javax.validation.Valid;

public class TitleDescriptionRequest extends CommonModel implements Serializable {

  private Language lang;

  @Valid
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
    return "TitleDescriptionRequest{" +
        "lang=" + lang +
        ", content='" + content + '\'' +
        '}' + super.toString();
  }

}
