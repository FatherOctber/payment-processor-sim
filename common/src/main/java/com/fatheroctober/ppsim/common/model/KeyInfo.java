package com.fatheroctober.ppsim.common.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fatheroctober.ppsim.common.util.KeyDeserializer;
import com.fatheroctober.ppsim.common.util.KeySerializer;
import lombok.SneakyThrows;
import lombok.Value;

import java.io.IOException;
import java.security.Key;

@Value
public class KeyInfo {
    private static ObjectMapper objectMapper = initObjectMapper();

    @JsonSerialize(using = KeySerializer.class)
    @JsonDeserialize(using = KeyDeserializer.class)
    Key key;

    @SneakyThrows(JsonProcessingException.class)
    public String serializedContent() {
        return objectMapper.writeValueAsString(this);
    }

    @SneakyThrows(IOException.class)
    public static KeyInfo create(String json) {
        return objectMapper.readValue(json, KeyInfo.class);
    }

    private static ObjectMapper initObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Key.class, new KeySerializer());
        module.addDeserializer(Key.class, new KeyDeserializer());
        mapper.registerModule(module);

        return mapper;
    }
}
