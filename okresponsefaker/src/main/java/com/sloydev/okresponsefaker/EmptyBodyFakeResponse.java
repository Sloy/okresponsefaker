package com.sloydev.okresponsefaker;

public class EmptyBodyFakeResponse implements FakeResponse {

    private final int httpCode;

    public EmptyBodyFakeResponse(int httpCode) {
        this.httpCode = httpCode;
    }

    @Override
    public String body() {
        return "";
    }

    @Override
    public String mediaType() {
        return "text/plain";
    }

    @Override
    public int httpCode() {
        return httpCode;
    }
}
