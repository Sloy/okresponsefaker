package com.sloydev.okresponsefaker;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;

public class FakeResponseInterceptor implements Interceptor {

    private FakeResponse nextFakeResponse;
    private boolean triggerOnce = true;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        if (nextFakeResponse == null) {
            return originalResponse;
        }

        Response fakedResponse = originalResponse.newBuilder() //
          .code(nextFakeResponse.httpCode()) //
          .body(ResponseBody.create(MediaType.parse(nextFakeResponse.mediaType()), nextFakeResponse.body())) //
          .build();

        if (triggerOnce) {
            nextFakeResponse = null;
        }

        return fakedResponse;
    }

    public void setNextFakeResponse(FakeResponse nextFakeResponse) {
        this.nextFakeResponse = nextFakeResponse;
    }

    public void clearNextFakeResponse() {
        this.nextFakeResponse = null;
    }

    public void setTriggerOnce(boolean triggerOnce) {
        this.triggerOnce = triggerOnce;
    }

    public boolean isTriggerOnce() {
        return triggerOnce;
    }
}
