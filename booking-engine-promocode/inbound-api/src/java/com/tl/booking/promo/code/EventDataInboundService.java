package com.tl.booking.promo.code;

import org.springframework.stereotype.Service;

@Service
public interface EventDataInboundService {

  void createEventData(final String message) throws Exception;
}
