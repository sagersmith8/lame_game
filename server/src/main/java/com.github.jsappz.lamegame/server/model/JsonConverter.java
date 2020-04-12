package com.github.jsappz.lamegame.server.model;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class JsonConverter {
    private static JsonFactory factory = new JsonFactory();
    static {
        factory.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
    }
    private static final ObjectMapper STREAM_WRITER = new ObjectMapper(factory);
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static <T extends ApiObject> T readFromStream(InputStream stream, Class<T> clazz) throws IOException {
        return MAPPER.readValue(stream, clazz);
    }

    public static <T extends ApiObject> OutputStream writeToStream(OutputStream stream, T objectToWrite) throws IOException {
        STREAM_WRITER.writeValue(stream, objectToWrite);
        return stream;
    }
}
