package com.processo.seletivo.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class ResponseAbstractController {

    @Autowired
    protected JsonResponseService jsonResponseService;

    @Autowired
    protected JsonResponseFactory jsonResponseFactory;

    public ResponseEntity<?> jsonResponse(){
        return jsonResponse(null);
    }

    public ResponseEntity<?> jsonResponse(Object content){
        try {
            JsonResponse response = jsonResponseFactory.create(content, getResponseService().getMessageList());

            getResponseService().clearMessages();

            return new ResponseEntity<>(response, getResponseService().getHttpStatus());

        } finally {
            getResponseService().clearMessages();
        }
    }

    protected JsonResponseService getResponseService() {
        return jsonResponseService;
    }


}
