package com.sloydev.okresponsefaker;

public interface FakeResponse {

    String body();

    String mediaType();

    int httpCode();
}
