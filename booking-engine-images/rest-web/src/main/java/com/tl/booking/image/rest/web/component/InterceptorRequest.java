package com.tl.booking.image.rest.web.component;


import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.common.rest.web.model.request.MandatoryRequestBuilder;
import com.tl.booking.image.entity.constant.fields.BaseMongoFields;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class InterceptorRequest extends HandlerInterceptorAdapter {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId(request.getHeader(BaseMongoFields.STORE_ID))
        .withChannelId(request.getHeader(BaseMongoFields.CHANNEL_ID))
        .withUsername(request.getHeader(BaseMongoFields.USERNAME))
        .withRequestId(request.getHeader(BaseMongoFields.REQUEST_ID))
        .withServiceId(request.getHeader(BaseMongoFields.SERVICE_ID)).build();

    MDC.put("mandatoryRequest", mandatoryRequest);
    MDC.put("storeId", request.getHeader(BaseMongoFields.STORE_ID));
    MDC.put("channelId", request.getHeader(BaseMongoFields.CHANNEL_ID));
    MDC.put("username", request.getHeader(BaseMongoFields.USERNAME));
    MDC.put("requestId", request.getHeader(BaseMongoFields.REQUEST_ID));
    MDC.put("serviceId", request.getHeader(BaseMongoFields.SERVICE_ID));


    request.setAttribute("mandatory", mandatoryRequest);

    return true;
  }
}
