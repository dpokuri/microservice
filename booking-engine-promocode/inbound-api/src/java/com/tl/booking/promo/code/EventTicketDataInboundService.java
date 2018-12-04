package com.tl.booking.promo.code;

import org.springframework.stereotype.Service;

@Service
public interface EventTicketDataInboundService {

  void createEventTicketData(final String message) throws Exception;
}
