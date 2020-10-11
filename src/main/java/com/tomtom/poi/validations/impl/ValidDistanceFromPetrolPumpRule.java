package com.tomtom.poi.validations.impl;

import com.tomtom.poi.PacketHandlerApplication;
import com.tomtom.poi.payload.DataPayload;
import com.tomtom.poi.validations.RuleValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class ValidDistanceFromPetrolPumpRule implements RuleValidator {

    private final Map<String, Object> args;
    private static final Logger logger = LoggerFactory.getLogger(PacketHandlerApplication.class);
    public ValidDistanceFromPetrolPumpRule(Map<String, Object> args) {
        this.args = args;
    }

    @Override
    public boolean validate(DataPayload dataPayload) {
        logger.debug("Invoking Rule : {}", id());
        return true;
    }

    @Override
    public String description() {
        return (String)args.get("description");
    }

    @Override
    public String id() {
        return (String)args.get("ruleId");
    }

}
