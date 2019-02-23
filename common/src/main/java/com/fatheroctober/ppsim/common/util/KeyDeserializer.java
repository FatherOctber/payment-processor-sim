package com.fatheroctober.ppsim.common.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import sun.misc.BASE64Decoder;

import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.Key;

public class KeyDeserializer extends StdDeserializer<Key> {

    public KeyDeserializer() {
        this(null);
    }

    public KeyDeserializer(Class<Key> t) {
        super(t);
    }

    @Override
    public Key deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonToken currentToken = null;
        String byteKeyStr = null;
        String algo = null;
        while ((currentToken = jsonParser.nextValue()) != null) {
            switch (currentToken) {
                case VALUE_STRING:
                    switch (jsonParser.getCurrentName()) {
                        case "byteData":
                            byteKeyStr = jsonParser.getText();
                            break;
                        case "algo":
                            algo = jsonParser.getText();
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
        return new SecretKeySpec(new BASE64Decoder().decodeBuffer(byteKeyStr), algo);
    }
}