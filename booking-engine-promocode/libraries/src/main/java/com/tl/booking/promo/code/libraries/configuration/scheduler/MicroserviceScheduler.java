package com.tl.booking.promo.code.libraries.configuration.scheduler;

import com.tl.booking.promo.code.libraries.configuration.factory.NamingThreadFactory;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MicroserviceScheduler {

  @Bean
  public Scheduler schedulerPromoCode(PromoCodeSchedulerConfiguration
      promoCodeSchedulerConfiguration) {
    return get(promoCodeSchedulerConfiguration, "promocode");
  }

  private Scheduler get(SchedulerConfiguration configuration, String prefix) {
    Integer minIdle =
        configuration.getMinIdleThread() != null ? configuration.getMinIdleThread() : 0;
    Integer maxThread =
        configuration.getMaxThread() != null ? configuration.getMaxThread() : Integer.MAX_VALUE;
    Integer ttl = configuration.getThreadTtl() != null ? configuration.getThreadTtl() : 0;
    Integer maxQueue =
        configuration.getMaxQueue() != null ? configuration.getMaxQueue() : Integer.MAX_VALUE;

    NamingThreadFactory threadFactory = new NamingThreadFactory(prefix);

    return Schedulers.from(new ThreadPoolExecutor(minIdle, maxThread, ttl, TimeUnit.SECONDS,
        new LinkedBlockingQueue<>(maxQueue), threadFactory));
  }

}
