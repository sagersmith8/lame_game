package com.github.jsappz.lamegame.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.*;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class JsonConverterTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void test_writeToStream() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        User user = User.builder()
                .id(1)
                .username("username")
                .build();
        JsonConverter.writeToStream(out, user);
        InputStream inputStream = new ByteArrayInputStream(out.toByteArray());
        out.close();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String line;
        int count = 0;
        while ((line = reader.readLine()) != null) {
            assertThat(MAPPER.writeValueAsString(user), is(line));
            count++;
        }
        assertThat(count, is(1));
        reader.close();
        inputStreamReader.close();
        inputStream.close();
    }

    @Test
    public void test_readFromString() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        User user = User.builder()
                .id(1)
                .username("username")
                .build();
        JsonConverter.writeToStream(out, user);
        InputStream inputStream = new ByteArrayInputStream(out.toByteArray());
        out.close();
        assertThat(JsonConverter.readFromStream(inputStream, User.class), is(user));
        inputStream.close();
    }
}
