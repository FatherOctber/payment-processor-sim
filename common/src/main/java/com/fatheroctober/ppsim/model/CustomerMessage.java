package com.fatheroctober.ppsim.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.Value;

import java.io.IOException;

@Value
public class CustomerMessage {
    private static ObjectMapper objectMapper = new ObjectMapper();

    String pan;
    String exDate;
    String cvc2;


    @SneakyThrows(JsonProcessingException.class)
    public String serealizedContent() {
        return objectMapper.writeValueAsString(this);
    }

    @SneakyThrows(IOException.class)
    public static CustomerMessage create(String json) {
        return objectMapper.readValue(json, CustomerMessage.class);
    }
}
