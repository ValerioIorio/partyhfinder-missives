package com.personal.app.validators;


import com.partyh.finder.common.validators.commons.AbstractValidator;
import com.personal.app.model.MissiveDTO;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class MissiveValidator extends AbstractValidator<MissiveDTO> {

    @Override
    public void validate(MissiveDTO input, HttpMethod method, LinkedHashMap<String, Boolean> object_presence) {
        LinkedHashMap<String, String> errors = new LinkedHashMap<>();
        validateMandatoryFields(errors, List.of("text"), input.getText());
        validatePresenceOfOptionalObject(object_presence, errors);
        throwExceptionIfNecessary(errors);
    }

    @Override
    public void validate(MissiveDTO input, HttpMethod method) {}
}
