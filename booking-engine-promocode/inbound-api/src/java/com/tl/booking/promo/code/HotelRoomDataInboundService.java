package com.tl.booking.promo.code;

import org.springframework.stereotype.Service;

@Service
public interface HotelRoomDataInboundService {

  void createHotelRoomData(final String message) throws Exception;
}
