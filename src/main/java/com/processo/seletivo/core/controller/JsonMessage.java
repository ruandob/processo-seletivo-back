package com.processo.seletivo.core.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonMessage {

    private JsonResponseType type;
    private String messageKey;
    private String message;

    public JsonMessage(JsonResponseType type, String messageKey) {
        this.type = type;
        this.messageKey = messageKey;
    }

}
