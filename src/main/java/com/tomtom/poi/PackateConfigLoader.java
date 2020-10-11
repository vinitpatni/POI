package com.tomtom.poi;

import com.tomtom.poi.exception.PacketHandlerConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

import static com.tomtom.poi.Constants.*;

public class PackateConfigLoader {

    private PackateConfigLoader() { }

    public static List<Map<String, Object>> validatorConfig;
    public static Map<String, Object>  kafkaProducerConfig;
    private static final Logger logger = LoggerFactory.getLogger(PackateConfigLoader.class);

    public static void loadConfigurationFile(String configFile) {
        try {
            Yaml yaml = new Yaml();
            Map<String, Map<String, Object>> configMap = yaml.load(new FileReader(configFile));
            Map<String, Object> configMapOfPacketHandlerService = (Map<String, Object>) configMap.get(CONFNAME_PACKAGE_HANDLER_SERVICE);
            validatorConfig = (List<Map<String, Object>>) configMapOfPacketHandlerService.get(CONFNAME_VALIDATOR);
            kafkaProducerConfig = ((List<Map<String, Object>>) configMapOfPacketHandlerService.get(CONFNAME_KAFKA_PRODUCER_CONFIG)).get(0);
        } catch(FileNotFoundException ex) {
          throw new PacketHandlerConfigurationException("config file not found");
        }
    }





}

