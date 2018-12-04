package com.tl.booking.promo.code.libraries.configuration.scheduler;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("promo.code.scheduler.promopage")
public class PromoCodeSchedulerConfiguration extends SchedulerConfiguration{
}
