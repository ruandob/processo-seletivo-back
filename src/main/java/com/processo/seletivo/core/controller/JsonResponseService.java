package com.processo.seletivo.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class JsonResponseService {

    @Autowired
    private MessageSource messageSource;

    private static ThreadLocal<Map<JsonResponseType, List<String>>> messageHolder = new ThreadLocal<>();

    private HttpStatus httpStatus = HttpStatus.OK;


    public void addWarning(String messageKey, Object... parameters) {
        this.addMessage(JsonResponseType.WARNING, messageKey, parameters);
        this.setHttpStatus(HttpStatus.BAD_REQUEST);
    }

    public void addError(String messageKey, Object... parameters) {
        addMessage(JsonResponseType.ERROR, messageKey, parameters);
        this.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public void addSuccess(String messageKey, Object... parameters) {
        addMessage(JsonResponseType.SUCCESS, messageKey, parameters);
        this.setHttpStatus(HttpStatus.OK);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    private void addMessage(JsonResponseType type, String messageKey, Object... parameters){

        List<String> messages = getMessageHolder().get(type);

        messages = messages != null ? messages : new ArrayList<>();
        messages.add(getMensagemI18n(messageKey, parameters));

        getMessageHolder().put(type,  messages);
    }

    public void clearMessages(){
        this.messageHolder.set(null);
        this.setHttpStatus(HttpStatus.OK);
    }

    public Map<JsonResponseType, List<String>> getMessageList() {
        return messageHolder.get();
    }

    private Map<JsonResponseType, List<String>> getMessageHolder() {
        if (messageHolder.get() == null) {
            messageHolder.set(new LinkedHashMap<>());
        }
        return messageHolder.get();
    }

    private String getMensagemI18n(String messageKey, Object... parameters) {
        return messageSource.getMessage(messageKey, parameters, LocaleContextHolder.getLocale());
    }



}
