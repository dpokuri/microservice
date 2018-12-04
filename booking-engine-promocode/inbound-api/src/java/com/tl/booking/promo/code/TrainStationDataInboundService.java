package com.tl.booking.promo.code;

import org.springframework.stereotype.Service;

@Service
public interface TrainStationDataInboundService {

  void createTrainStationData(final String message) throws Exception;
}
