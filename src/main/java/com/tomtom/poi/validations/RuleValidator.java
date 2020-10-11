package com.tomtom.poi.validations;


import com.tomtom.poi.payload.DataPayload;


/**
 * Implementation of this interface can write specific rules and then run
 * validation on the datapayload which we consumer from Car
 */
public interface RuleValidator {
    boolean validate(DataPayload dataPayload);

    String description();

    String id();
}


