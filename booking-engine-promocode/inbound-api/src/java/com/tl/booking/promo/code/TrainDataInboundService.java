package com.tl.booking.promo.code;

import org.springframework.stereotype.Service;

@Service
public interface TrainDataInboundService {

  void createTrainData(final String message) throws Exception;
}
