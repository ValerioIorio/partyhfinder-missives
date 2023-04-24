package com.personal.app.validators;


import com.partyh.finder.common.exception.impl.PFValidationException;
import com.partyh.finder.common.validators.commons.AbstractValidator;
import com.personal.app.model.MissiveDTO;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MissiveValidator extends AbstractValidator<MissiveDTO> {

    @Override
    public void validate(MissiveDTO input, HttpMethod method, LinkedHashMap<String, Boolean> object_presence) {
        LinkedHashMap<String, String> errors = new LinkedHashMap<>();
        validateExternalObjectPresence(input.getSenderId(), "user", errors);
        validateExternalObjectPresence(input.getChatId(), "chat", errors);
        validateMandatoryFields(errors,List.of("text") , input.getText());
        throwExceptionIfNecessary(errors);
    }

    @Override
    public void validate(MissiveDTO input, HttpMethod method) {
        LinkedHashMap<String, String> errors = new LinkedHashMap<>();
        validateExternalObjectPresence(input.getSenderId(), "user", errors);
        validateExternalObjectPresence(input.getChatId(), "chat", errors);
        validateMandatoryFields(errors,List.of("text", "chatId", "senderId") , input.getText(), input.getChatId().toString(), input.getSenderId().toString());
        throwExceptionIfNecessary(errors);
    }
    private LinkedHashMap<String,String> validateExternalObjectPresence(Long id, String checkType, LinkedHashMap<String, String> errors) {
        String url = "";
        if(checkType.equals("user")) {
            url = USER_CHECK_URL + id;
        } else if(checkType.equals("chat")) {
            url = CHAT_CHECK_URL + id;
        }
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if(!response.isSuccessful()) {
                errors.put(checkType, checkType + " does not exist");
            }
        } catch (IOException e) {
            errors.put(checkType, "Error while checking the " + checkType + " in the external service");
        }
        return errors;
    }

    @Value("${user.check.url}")
    private String USER_CHECK_URL;

    @Value("${chat.check.url}")
    private String CHAT_CHECK_URL;

    private final OkHttpClient client = new OkHttpClient();
}
