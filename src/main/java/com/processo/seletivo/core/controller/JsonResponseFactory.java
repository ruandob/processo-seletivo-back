package com.processo.seletivo.core.controller;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class JsonResponseFactory {

    public JsonResponse create(Map<JsonResponseType, List<String>> messages) {
        return create(null, messages);
    }

    public JsonResponse create(Object content, Map<JsonResponseType, List<String>> messages) {
        JsonResponse jsonResponse = new JsonResponse();
        jsonResponse.setContent(content);
        jsonResponse.setMessages(messages);
        return jsonResponse;
    }

}
