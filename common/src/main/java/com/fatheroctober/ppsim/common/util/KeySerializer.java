package com.fatheroctober.ppsim.common.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.security.Key;

public class KeySerializer extends StdSerializer<Key> {

    public KeySerializer() {
        this(null);
    }

    public KeySerializer(Class<Key> t) {
        super(t);
    }

    @Override
    public void serialize(Key value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        jgen.writeBinaryField("byteData", value.getEncoded());
        jgen.writeStringField("algo", value.getAlgorithm());
        jgen.writeStringField("format", value.getFormat());
        jgen.writeEndObject();
    }
}