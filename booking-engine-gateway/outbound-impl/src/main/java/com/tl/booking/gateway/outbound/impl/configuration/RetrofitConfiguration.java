package com.tl.booking.gateway.outbound.impl.configuration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tl.booking.gateway.entity.configuration.CalendarApiConfiguration;
import com.tl.booking.gateway.entity.configuration.FlightRMEConfiguration;
import com.tl.booking.gateway.entity.configuration.HotelAggregateConfiguration;
import com.tl.booking.gateway.entity.configuration.HotelScrappingConfiguration;
import com.tl.booking.gateway.entity.configuration.HotelSubsidyConfiguration;
import com.tl.booking.gateway.entity.configuration.ImageConfiguration;
import com.tl.booking.gateway.entity.configuration.PromoCodeConfiguration;
import com.tl.booking.gateway.entity.configuration.PromoListConfiguration;

import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
@ConditionalOnClass(Retrofit.class)
public class RetrofitConfiguration {

  @Bean(name = "retrofitHoliday")
  public Retrofit retrofitHoliday(CalendarApiConfiguration gatewayApiConfiguration, @Qualifier
      (value = "HolidayApiHttpClient") OkHttpClient okHttpClient) {
    Retrofit.Builder builder = new Retrofit.Builder();
    if (okHttpClient != null) {
      builder.client(okHttpClient);
    }
    builder.baseUrl(gatewayApiConfiguration.getHost());
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);
    builder.addConverterFactory(JacksonConverterFactory.create(objectMapper));
    return builder.build();
  }

  @Bean(name = "retrofitServiceHoliday")
  public CalendarEndPointService retrofitService(@Qualifier("retrofitHoliday") Retrofit
      retrofit) {
    return retrofit.create(CalendarEndPointService.class);
  }


  @Bean(name = "retrofitPromoList")
  public Retrofit retrofitPromoList(PromoListConfiguration promoListConfiguration, @Qualifier
      (value = "PromoListApiHttpClient") OkHttpClient okHttpPromoListClient) {
    Retrofit.Builder builder = new Retrofit.Builder();
    if (okHttpPromoListClient != null) {
      builder.client(okHttpPromoListClient);
    }
    builder.baseUrl(promoListConfiguration.getHost());
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);
    builder.addConverterFactory(JacksonConverterFactory.create(objectMapper));
    return builder.build();
  }

  @Bean(name = "retrofitPromoListService")
  public PromoListEndPointService retrofitPromoListService(@Qualifier(value = "retrofitPromoList")
      Retrofit
      retrofitPromoList) {
    return retrofitPromoList.create(PromoListEndPointService.class);
  }

  @Bean(name = "retrofitHotelScrapping")
  public Retrofit retrofitHotelScrapping(HotelScrappingConfiguration hotelScrappingConfiguration,
      @Qualifier(value = "HotelScrappingHttpClient") OkHttpClient okHttpHotelScrappingClient) {
    Retrofit.Builder builder = new Retrofit.Builder();
    if (okHttpHotelScrappingClient != null) {
      builder.client(okHttpHotelScrappingClient);
    }
    builder.baseUrl(hotelScrappingConfiguration.getHost());
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);
    builder.addConverterFactory(JacksonConverterFactory.create(objectMapper));
    return builder.build();
  }

  @Bean(name = "retrofitHotelScrappingEndPointService")
  public HotelScrappingEndPointService retrofitHotelScrappingService(
      @Qualifier(value = "retrofitHotelScrapping")
          Retrofit
          retrofitHotelScrapping) {
    return retrofitHotelScrapping.create(HotelScrappingEndPointService.class);
  }

  @Bean(name = "retrofitHotelAggregate")
  public Retrofit retrofitHotelPromoAggregate(
      HotelAggregateConfiguration hotelAggregateConfiguration,
      @Qualifier(value = "HotelAggregateHttpClient") OkHttpClient okHttpHotelAggregateClient) {
    Retrofit.Builder builder = new Retrofit.Builder();
    if (okHttpHotelAggregateClient != null) {
      builder.client(okHttpHotelAggregateClient);
    }
    builder.baseUrl(hotelAggregateConfiguration.getHost());
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);
    builder.addConverterFactory(JacksonConverterFactory.create(objectMapper));
    return builder.build();
  }

  @Bean(name = "retrofitHotelPromoAggregateEndPointService")
  public HotelAggregateEndPointService retrofitHotelPromoAggregateService(
      @Qualifier(value = "retrofitHotelAggregate")
          Retrofit
          retrofitHotelAggregate) {
    return retrofitHotelAggregate.create(HotelAggregateEndPointService.class);
  }

  @Bean(name = "retrofitHotelSubsidy")
  public Retrofit retrofitHotelSubsidy(
      HotelSubsidyConfiguration hotelSubsidyConfiguration,
      @Qualifier(value = "HotelSubsidyHttpClient") OkHttpClient okHttpHotelSubsidyClient) {
    Retrofit.Builder builder = new Retrofit.Builder();
    if (okHttpHotelSubsidyClient != null) {
      builder.client(okHttpHotelSubsidyClient);
    }
    builder.baseUrl(hotelSubsidyConfiguration.getHost());
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);
    builder.addConverterFactory(JacksonConverterFactory.create(objectMapper));
    return builder.build();
  }

  @Bean(name = "retrofitHotelSubsidyEndPointService")
  public HotelSubsidyEndPointService retrofitHotelSubsidyService(
      @Qualifier(value = "retrofitHotelSubsidy") Retrofit retrofitHotelSubsidy) {
    return retrofitHotelSubsidy.create(HotelSubsidyEndPointService.class);
  }

  @Bean(name = "retrofitImage")
  public Retrofit retrofitImage(
      ImageConfiguration imageConfiguration,
      @Qualifier(value = "ImageHttpClient") OkHttpClient okHttpImageClient) {
    Retrofit.Builder builder = new Retrofit.Builder();
    if (okHttpImageClient != null) {
      builder.client(okHttpImageClient);
    }
    builder.baseUrl(imageConfiguration.getHost());
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);
    builder.addConverterFactory(JacksonConverterFactory.create(objectMapper));
    return builder.build();
  }

  @Bean(name = "retrofitImageEndPointService")
  public ImageEndPointService retrofitImageService(
      @Qualifier(value = "retrofitImage") Retrofit retrofitImage) {
    return retrofitImage.create(ImageEndPointService.class);
  }


  @Bean(name = "retrofitPromoCode")
  public Retrofit retrofitPromoCode(PromoCodeConfiguration promoCodeConfiguration, @Qualifier
      (value = "PromoCodeApiHttpClient") OkHttpClient okHttpPromoCodeClient) {
    Retrofit.Builder builder = new Retrofit.Builder();
    if (okHttpPromoCodeClient != null) {
      builder.client(okHttpPromoCodeClient);
    }
    builder.baseUrl(promoCodeConfiguration.getHost());
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);
    builder.addConverterFactory(JacksonConverterFactory.create(objectMapper));
    return builder.build();
  }

  @Bean(name = "retrofitPromoCodeService")
  public PromoCodeEndPointService retrofitPromoCodeService(@Qualifier(value = "retrofitPromoCode")
      Retrofit
      retrofitPromoCode) {
    return retrofitPromoCode.create(PromoCodeEndPointService.class);
  }

//  @Bean(name = "retrofitFlightRME")
//  public Retrofit retrofitFlightRME(FlightRMEConfiguration flightRMEConfiguration, @Qualifier
//      (value = "FlightRMEHttpClient") OkHttpClient okHttpPromoCodeClient) {
//    Retrofit.Builder builder = new Retrofit.Builder();
//    if (okHttpPromoCodeClient != null) {
//      builder.client(okHttpPromoCodeClient);
//    }
//    builder.baseUrl(flightRMEConfiguration.getHost());
//    ObjectMapper objectMapper = new ObjectMapper();
//    objectMapper.configure(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);
//    builder.addConverterFactory(JacksonConverterFactory.create(objectMapper));
//    return builder.build();
//  }
//
//  @Bean(name = "retrofitFlightRMEService")
//  public FlightRMEEndPointService retrofitFlightRMEService(@Qualifier(value = "retrofitFlightRME")
//      Retrofit
//      retrofitFlightRME) {
//    return retrofitFlightRME.create(FlightRMEEndPointService.class);
//  }


}
