package com.tl.booking.gateway.outbound.impl.configuration;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.tl.booking.gateway.entity.configuration.CalendarApiConfiguration;
import com.tl.booking.gateway.entity.configuration.FlightRMEConfiguration;
import com.tl.booking.gateway.entity.configuration.HotelAggregateConfiguration;
import com.tl.booking.gateway.entity.configuration.HotelScrappingConfiguration;
import com.tl.booking.gateway.entity.configuration.HotelSubsidyConfiguration;
import com.tl.booking.gateway.entity.configuration.ImageConfiguration;
import com.tl.booking.gateway.entity.configuration.PromoCodeConfiguration;
import com.tl.booking.gateway.entity.configuration.PromoListConfiguration;

@Configuration
@ConditionalOnClass(OkHttpClient.class)
public class OkHttpConfiguration {

  public static final Logger LOGGER = LoggerFactory.getLogger(OkHttpConfiguration.class);

  @Bean(name = "HolidayApiHttpClient")
  public OkHttpClient okHttpClient(CalendarApiConfiguration calendarApiConfiguration) {

    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(LOGGER::info);

    httpLoggingInterceptor
        .setLevel(HttpLoggingInterceptor.Level.valueOf(calendarApiConfiguration.getLogLevel()));

    OkHttpClient.Builder builder = new OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .connectTimeout(calendarApiConfiguration.getConnectTimeout(), TimeUnit.MILLISECONDS)
        .readTimeout(calendarApiConfiguration.getReadTimeout(), TimeUnit.MILLISECONDS)
        .writeTimeout(calendarApiConfiguration.getReadTimeout(), TimeUnit.MILLISECONDS);
    builder.addInterceptor(
        chain -> chain.proceed(chain.request().newBuilder()
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build()));

    return builder.build();
  }


  @Bean(name = "PromoListApiHttpClient")
  public OkHttpClient okHttpPromoListClient(PromoListConfiguration promoListConfiguration) {

    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(LOGGER::info);

    httpLoggingInterceptor
        .setLevel(HttpLoggingInterceptor.Level.valueOf(promoListConfiguration.getLogLevel()));

    OkHttpClient.Builder builder = new OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .connectTimeout(promoListConfiguration.getConnectTimeout(), TimeUnit.MILLISECONDS)
        .readTimeout(promoListConfiguration.getReadTimeout(), TimeUnit.MILLISECONDS)
        .writeTimeout(promoListConfiguration.getReadTimeout(), TimeUnit.MILLISECONDS);
    builder.addInterceptor(
        chain -> chain.proceed(chain.request().newBuilder()
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build()));

    return builder.build();
  }

  @Bean(name = "HotelScrappingHttpClient")
  public OkHttpClient okHttpHotelScrappingHttpClient(
      HotelScrappingConfiguration hotelScrappingConfiguration) {

    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(LOGGER::info);

    httpLoggingInterceptor
        .setLevel(HttpLoggingInterceptor.Level.valueOf(hotelScrappingConfiguration.getLogLevel()));

    OkHttpClient.Builder builder = new OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .connectTimeout(hotelScrappingConfiguration.getConnectTimeout(), TimeUnit.MILLISECONDS)
        .readTimeout(hotelScrappingConfiguration.getReadTimeout(), TimeUnit.MILLISECONDS)
        .writeTimeout(hotelScrappingConfiguration.getReadTimeout(), TimeUnit.MILLISECONDS);
    builder.addInterceptor(
        chain -> chain.proceed(chain.request().newBuilder()
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build()));

    return builder.build();
  }

  @Bean(name = "HotelAggregateHttpClient")
  public OkHttpClient okHttpHotelAggregateHttpClient(
      HotelAggregateConfiguration hotelAggregateConfiguration) {

    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(LOGGER::info);

    httpLoggingInterceptor
        .setLevel(HttpLoggingInterceptor.Level.valueOf(hotelAggregateConfiguration.getLogLevel()));

    OkHttpClient.Builder builder = new OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .connectTimeout(hotelAggregateConfiguration.getConnectTimeout(), TimeUnit.MILLISECONDS)
        .readTimeout(hotelAggregateConfiguration.getReadTimeout(), TimeUnit.MILLISECONDS)
        .writeTimeout(hotelAggregateConfiguration.getReadTimeout(), TimeUnit.MILLISECONDS);
    builder.addInterceptor(
        chain -> chain.proceed(chain.request().newBuilder()
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build()));

    return builder.build();
  }

  @Bean(name = "HotelSubsidyHttpClient")
  public OkHttpClient okHttpHotelSubsidyHttpClient(
      HotelSubsidyConfiguration hotelSubsidyConfiguration) {

    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(LOGGER::info);

    httpLoggingInterceptor
        .setLevel(HttpLoggingInterceptor.Level.valueOf(hotelSubsidyConfiguration.getLogLevel()));

    OkHttpClient.Builder builder = new OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .connectTimeout(hotelSubsidyConfiguration.getConnectTimeout(), TimeUnit.MILLISECONDS)
        .readTimeout(hotelSubsidyConfiguration.getReadTimeout(), TimeUnit.MILLISECONDS)
        .writeTimeout(hotelSubsidyConfiguration.getReadTimeout(), TimeUnit.MILLISECONDS);
    builder.addInterceptor(
        chain -> chain.proceed(chain.request().newBuilder()
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build()));

    return builder.build();
  }

  @Bean(name = "ImageHttpClient")
  public OkHttpClient okHttpImageHttpClient(
      ImageConfiguration imageConfiguration) {

    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(LOGGER::info);

    httpLoggingInterceptor
        .setLevel(HttpLoggingInterceptor.Level.valueOf(imageConfiguration.getLogLevel()));

    OkHttpClient.Builder builder = new OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .connectTimeout(imageConfiguration.getConnectTimeout(), TimeUnit.MILLISECONDS)
        .readTimeout(imageConfiguration.getReadTimeout(), TimeUnit.MILLISECONDS)
        .writeTimeout(imageConfiguration.getReadTimeout(), TimeUnit.MILLISECONDS);
    builder.addInterceptor(
        chain -> chain.proceed(chain.request().newBuilder()
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build()));

    return builder.build();
  }

  @Bean(name = "PromoCodeApiHttpClient")
  public OkHttpClient okHttpPromoCodeApiHttpClient(
      PromoCodeConfiguration promoCodeConfiguration) {

    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(LOGGER::info);

    httpLoggingInterceptor
        .setLevel(HttpLoggingInterceptor.Level.valueOf(promoCodeConfiguration.getLogLevel()));

    OkHttpClient.Builder builder = new OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .connectTimeout(promoCodeConfiguration.getConnectTimeout(), TimeUnit.MILLISECONDS)
        .readTimeout(promoCodeConfiguration.getReadTimeout(), TimeUnit.MILLISECONDS)
        .writeTimeout(promoCodeConfiguration.getReadTimeout(), TimeUnit.MILLISECONDS);
    builder.addInterceptor(
        chain -> chain.proceed(chain.request().newBuilder()
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build()));

    return builder.build();
  }

//  @Bean(name = "FlightRMEApiHttpClient")
//  public OkHttpClient okHttpFlightRMEApiHttpClient(
//      FlightRMEConfiguration flightRMEConfiguration) {
//
//    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(LOGGER::info);
//
//    httpLoggingInterceptor
//        .setLevel(HttpLoggingInterceptor.Level.valueOf(flightRMEConfiguration.getLogLevel()));
//
//    OkHttpClient.Builder builder = new OkHttpClient.Builder()
//        .addInterceptor(httpLoggingInterceptor)
//        .connectTimeout(flightRMEConfiguration.getConnectTimeout(), TimeUnit.MILLISECONDS)
//        .readTimeout(flightRMEConfiguration.getReadTimeout(), TimeUnit.MILLISECONDS)
//        .writeTimeout(flightRMEConfiguration.getReadTimeout(), TimeUnit.MILLISECONDS);
//    builder.addInterceptor(
//        chain -> chain.proceed(chain.request().newBuilder()
//            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//            .build()));
//
//    return builder.build();
//  }

}
