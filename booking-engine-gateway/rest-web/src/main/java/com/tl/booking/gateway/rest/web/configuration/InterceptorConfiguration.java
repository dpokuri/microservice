package com.tl.booking.gateway.rest.web.configuration;

import com.tl.booking.gateway.rest.web.component.InterceptorRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

  @Autowired
  InterceptorRequest interceptorRequest;

  public void configurePathMatch(PathMatchConfigurer pathMatchConfigurer) {

  }

  public void configureContentNegotiation(
      ContentNegotiationConfigurer contentNegotiationConfigurer) {

  }

  public void configureAsyncSupport(AsyncSupportConfigurer asyncSupportConfigurer) {

  }

  public void configureDefaultServletHandling(
      DefaultServletHandlerConfigurer defaultServletHandlerConfigurer) {

  }

  public void addFormatters(FormatterRegistry formatterRegistry) {

  }

  public void addResourceHandlers(ResourceHandlerRegistry resourceHandlerRegistry) {

  }

  public void addCorsMappings(CorsRegistry corsRegistry) {

  }

  public void addViewControllers(ViewControllerRegistry viewControllerRegistry) {

  }

  public void configureViewResolvers(ViewResolverRegistry viewResolverRegistry) {

  }

  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> list) {

  }

  public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> list) {

  }

  public void configureMessageConverters(List<HttpMessageConverter<?>> list) {

  }

  public void extendMessageConverters(List<HttpMessageConverter<?>> list) {

  }

  public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> list) {

  }

  public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> list) {

  }

  public Validator getValidator() {
    return null;
  }

  public MessageCodesResolver getMessageCodesResolver() {
    return null;
  }

  public void addInterceptors(InterceptorRegistry interceptorRegistry) {
    interceptorRegistry.addInterceptor(interceptorRequest);
  }
}
