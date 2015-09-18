package com.sloydev.okresponsefaker;

public class ResponseFaker {

    private static FakeResponseInterceptor interceptorInstance = new FakeResponseInterceptor();

    public static FakeResponseInterceptor interceptor() {
        return interceptorInstance;
    }

    public static void setNextFakeResponse(FakeResponse nextFakeResponse) {
        interceptor().setNextFakeResponse(nextFakeResponse);
    }

    public static void clearNextFakeResponse() {
        interceptor().clearNextFakeResponse();
    }

    public static void setTriggerOnce(boolean triggerOnce) {
        interceptor().setTriggerOnce(triggerOnce);
    }

    public static boolean isTriggerOnce() {
        return interceptor().isTriggerOnce();
    }

    private ResponseFaker() {
        /* no-op */
    }
}
