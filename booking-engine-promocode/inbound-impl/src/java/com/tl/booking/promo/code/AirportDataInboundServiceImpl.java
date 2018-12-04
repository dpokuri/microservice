package com.tl.booking.promo.code;

import com.tl.booking.common.libraries.JSONHelper;
import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.entity.constant.KafkaTopic;
import com.tl.booking.promo.code.entity.constant.VariableSourceTable;
import com.tl.booking.promo.code.entity.dao.VariableSource;
import com.tl.booking.promo.code.entity.inbound.KafkaInboundData;
import com.tl.booking.promo.code.libraries.configuration.KafkaProperties;
import com.tl.booking.promo.code.service.api.VariableSourceService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class AirportDataInboundServiceImpl implements AirportDataInboundService {

  private static final Logger LOGGER = LoggerFactory.getLogger(AirportDataInboundServiceImpl.class);

  @Autowired
  KafkaProperties kafkaProperties;

  @Autowired
  VariableSourceService variableSourceService;

  private final String AIRPORT_CODE = "airportCode";
  private final String AIRPORT_NAME = "airportName";

  @Override
  @KafkaListener(topics = KafkaTopic.AIRPORT)
  public void createAirportData(String message) throws Exception {
    MandatoryRequest mandatoryRequest = new MandatoryRequest();
    mandatoryRequest.setUsername("system");
    mandatoryRequest.setServiceId("com.tl.booking.promo.code");
    mandatoryRequest.setRequestId(kafkaProperties.getGroupId());
    mandatoryRequest.setChannelId("DESKTOP");
    mandatoryRequest.setStoreId("TIKETCOM");

    LOGGER.info("createAirportData request message {}, mandatoryRequest {}", message,
        mandatoryRequest);

    List<VariableSource> variableSources = this.mapDataToAirport(message, mandatoryRequest);

    this.variableSourceService.updateVariableSources(mandatoryRequest, variableSources).subscribe();

    LOGGER.info("createAirportData response variableSources {}", variableSources);
  }

  private List<VariableSource> mapDataToAirport(String message, MandatoryRequest
      mandatoryRequest) throws IOException {
    List<VariableSource> variableSources = new ArrayList<>();
    for (Map<String, String> data : JSONHelper
        .convertJsonInStringToObject(message, KafkaInboundData.class).getData
            ()) {
      if(
          "".equals(data.get(AIRPORT_CODE))
          || "".equals(data.get(AIRPORT_NAME))) {
        VariableSource variableSource = new VariableSource();
        variableSource.setSourceType(VariableSourceTable.AIRPORT);
        variableSource.setValueId(data.get(AIRPORT_CODE));
        variableSource.setValueName(data.get(AIRPORT_NAME));
        variableSource.setValueSearch(data.get(AIRPORT_NAME).toLowerCase());
        variableSource.setStoreId(mandatoryRequest.getStoreId());
        variableSources.add(variableSource);
      }
    }

    return variableSources;
  }
}
