package com.sloydev.okresponsefaker;

import com.sloydev.jsonadapters.JsonAdapter;

public class JsonFakeResponse implements FakeResponse {

    private final JsonAdapter objectMapper;
    private final Object response;
    private final int httpcode;

    public JsonFakeResponse(JsonAdapter objectMapper, Object response, int httpcode) {
        this.objectMapper = objectMapper;
        this.response = response;
        this.httpcode = httpcode;
    }

    @Override
    public String body() {
        return objectMapper.toJson(response);
    }

    @Override
    public String mediaType() {
        return "application/json";
    }

    @Override
    public int httpCode() {
        return httpcode;
    }
}
