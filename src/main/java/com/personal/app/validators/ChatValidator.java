package com.personal.app.validators;

import com.partyh.finder.common.validators.commons.AbstractValidator;
import com.personal.app.model.ChatDTO;
import jdk.jfr.Category;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.collections.map.LinkedMap;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ChatValidator extends AbstractValidator<ChatDTO> {
    @Override
    public void validate(ChatDTO input, HttpMethod method, LinkedHashMap<String, Boolean> object_presence) {
        LinkedHashMap<String, String> errors = new LinkedHashMap<>();
        errors.putAll(validateMandatoryFields(errors, List.of("name", "usersStringIds"), input.getName(), input.getUserStringIds()));
        errors.putAll(validatePresenceOfOptionalObject(object_presence, errors));
        errors.putAll(validateStringIds(input.getUserStringIds(), errors));
        throwExceptionIfNecessary(errors);
    }

    @Override
    public void validate(ChatDTO input, HttpMethod method) {
        LinkedHashMap<String, String> errors = new LinkedHashMap<>();
        errors.putAll(validateStringIds(input.getUserStringIds(), errors));
        errors.putAll(validateMandatoryFields(errors, List.of("name", "usersStringIds"), input.getName(), input.getUserStringIds()));
        throwExceptionIfNecessary(errors);
    }

    //method that validates if a string is in a format number,number,number
    public LinkedHashMap validateStringIds(String stringIds, LinkedHashMap<String, String> errors){
        if (stringIds!=null){
            String[] ids = stringIds.split(",");
            for (String id : ids) {
                if (!id.matches("[0-9]+")) {
                    errors.put("usersStringIds", "The usersStringIds field must be a string of numbers separated by commas");
                }
            }
        }
        return errors;
    }
}
