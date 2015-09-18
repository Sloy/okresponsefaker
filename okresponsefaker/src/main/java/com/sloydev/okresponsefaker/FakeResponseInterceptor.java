package com.sloydev.okresponsefaker;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
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
