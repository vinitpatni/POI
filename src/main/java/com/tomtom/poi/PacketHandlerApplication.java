package com.tomtom.poi;

import com.tomtom.poi.consumer.KafkaHelper;
import com.tomtom.poi.consumer.PacketHandlerConsumer;
import com.tomtom.poi.exception.PacketHandlerConsumerException;
import com.tomtom.poi.exception.PacketHandlerConfigurationException;
import com.tomtom.poi.reporting.POIMonitoringConfiguration;
import com.tomtom.poi.validations.RuleValidator;
import com.tomtom.poi.validations.RuleValidatorHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import static com.tomtom.poi.Constants.KAFA_DOWNSTREAM_TOPIC;

public class PacketHandlerApplication {
    private static final String configFile = "poiconfig.yml";
    private static final Logger logger = LoggerFactory.getLogger(PacketHandlerApplication.class);
    public static void main(String[] args) throws URISyntaxException {

        logger.debug("PacketHandlerApplication loading");
        String downStreamKafkaTopic = System.getProperty(KAFA_DOWNSTREAM_TOPIC);
        URL uri = PacketHandlerApplication.class.getClassLoader().getResource(configFile);
        if(uri != null) {
            PackateConfigLoader.loadConfigurationFile(uri.getFile());
        }
        else {
            throw new PacketHandlerConfigurationException("config file not found");
        }

        POIMonitoringConfiguration poiMonitoringConfiguration = POIMonitoringConfiguration.registerGraphiteReporter();
        List<RuleValidator> categoryValidators = RuleValidatorHelper.setUpValidator(PackateConfigLoader.validatorConfig);
        logger.info("Total no of configured rules : {}",categoryValidators.size());
        logger.info("Following are Configured Rules :");
        for (RuleValidator categoryValidator : categoryValidators) {
            logger.info("Rule Id : {}, Rule Description : {}",categoryValidator.id(),categoryValidator.description());
        }
        //Initializing kafka Consumer passing list of category validators to run
        logger.debug("PacketHandlerApplication loading successfull");
        PacketHandlerConsumer packetHandlerConsumer = new PacketHandlerConsumer(PackateConfigLoader.kafkaProducerConfig,categoryValidators, poiMonitoringConfiguration);
        try {
            //mock messages
            packetHandlerConsumer.process(KafkaHelper.msg());
        } catch (PacketHandlerConsumerException e) {
            e.printStackTrace();
        }
    }
}
