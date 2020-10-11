package com.tomtom.poi.validations;

import com.tomtom.poi.exception.PacketHandlerConfigurationException;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.tomtom.poi.Constants.HANDLER_CLASS;

public class RuleValidatorHelper {

    public static List<RuleValidator> setUpValidator(List<Map<String, Object>> validatorConfig){
        List<RuleValidator> result = new ArrayList<>();
        for (Map<String, Object> configMap : validatorConfig) {
            String className = (String) configMap.get(HANDLER_CLASS);
            RuleValidator ruleValidator = createHandlerObject(className, configMap);
            result.add(ruleValidator);
        }

        return result;
    }

    private static RuleValidator createHandlerObject(String className, Map<String, Object> args) {
        RuleValidator gatewayHandler = null;

        try {
            Class handlerClass = Class.forName(className);
            Object handlerObject;
            if (args != null) {
                handlerObject = handlerClass.getConstructor(Map.class).newInstance(args);
            } else{
                handlerObject = handlerClass.getConstructor().newInstance();
            }

            if (handlerObject instanceof RuleValidator) {
                gatewayHandler = (RuleValidator) handlerObject;
            } else {
                throw new PacketHandlerConfigurationException("Class '" + className + "' needs to implement PacketHandler");
            }
        } catch(ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
            throw new PacketHandlerConfigurationException("Unable to instantiate Class '" + className + "");
        }
        return gatewayHandler;
    }
}
