package com.tl.booking.promo.code.rest.web.component;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.common.rest.web.model.request.MandatoryRequestBuilder;
import com.tl.booking.promo.code.entity.constant.enums.Language;
import com.tl.booking.promo.code.entity.constant.fields.BaseMongoFields;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class InterceptorRequest extends HandlerInterceptorAdapter {

  private static final Logger LOGGER = LoggerFactory.getLogger(InterceptorRequest.class);

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId(request.getHeader(BaseMongoFields.STORE_ID))
        .withChannelId(request.getHeader(BaseMongoFields.CHANNEL_ID))
        .withUsername(request.getHeader(BaseMongoFields.USERNAME))
        .withRequestId(request.getHeader(BaseMongoFields.REQUEST_ID))
        .withServiceId(request.getHeader(BaseMongoFields.SERVICE_ID)).build();

    LOGGER.info("Request POST {}", request.getParameter("promoCodeGroupRules"));

    MDC.put("mandatoryRequest", mandatoryRequest);
    MDC.put("storeId", request.getHeader(BaseMongoFields.STORE_ID));
    MDC.put("channelId", request.getHeader(BaseMongoFields.CHANNEL_ID));
    MDC.put("username", request.getHeader(BaseMongoFields.USERNAME));
    MDC.put("requestId", request.getHeader(BaseMongoFields.REQUEST_ID));
    MDC.put("serviceId", request.getHeader(BaseMongoFields.SERVICE_ID));

    if (request.getHeader(BaseMongoFields.LANG) != null
        && !"".equals(request.getHeader(BaseMongoFields.LANG))) {
      MDC.put("lang", request.getHeader(BaseMongoFields.LANG));
    } else {
      MDC.put("lang", Language.ID.getCode());
    }

    request.setAttribute("mandatory", mandatoryRequest);

    return true;
  }
}
