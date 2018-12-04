package com.tl.booking.gateway.entity.outbound.PromoCode;

import com.tl.booking.common.entity.CommonModel;
import com.tl.booking.gateway.entity.constant.enums.Language;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@GeneratePojoBuilder
public class ResponseMessageRequest extends CommonModel implements Serializable {
  @NotNull
  private Language lang;

  @NotNull
  @NotBlank
  @NotEmpty
  @Length(min = 2, max = 200)
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
    return "ResponseMessageRequest{" +
        "lang=" + lang +
        ", content='" + content + '\'' +
        '}' + super.toString();
  }

}
