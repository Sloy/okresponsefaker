# OkResponseFaker
OkHttp response faker. Provide "fire and forget" or persistent fake responses to OkHttp through interceptors.

# Description
OkResponseFaker uses a special OkHttp Interceptor that allows you to provide a custom response for the next or more requests executed, allowing you to easily debug feedback to special cases in your application.
You can fake response status and body using the *FakeResponse* interface. Use simple static data, or build a complex response using JSON objects. Your choice.

# Gradle
Just import the library from jcenter:
```groovy
dependencies {
    compile 'com.sloydev:okresponsefaker:1.0.0'
    compile 'com.squareup.okhttp:okhttp:2.5.0' // requires OkHttp 2.2 or better
}
```

You can add the optional JSON module if you want to build `JsonFakeResponse` using [JsonAdapters](https://github.com/Sloy/JsonAdapters):
```groovy
dependencies {
    compile 'com.sloydev:okresponsefaker:1.0.0'
    compile 'com.sloydev:okresponsefaker-json:1.0.0'
    compile 'com.squareup.okhttp:okhttp:2.5.0' // requires OkHttp 2.2 or better
    compile 'com.sloydev:jsonadapters-core:0.1.0' // included in okresponsefaker-json, but whatever
    // ...
}
```

# Setup
You just need to add a **FakeResponseInterceptor** to the end of interceptors chain when creating your OkHttp client.
There are two easy of using OkResponseFaker: singleton or custom instance. Check the diferences in the usage section.
```java
public void OkHttpClient createClient(){
  OkHttpClient client = new OkHttpClient();
  client.interceptors().add(...);
  client.interceptors().add(ResponseFaker.interceptor()); // singleton way
  // or
  client.interceptors().add(fakeResponseInterceptor); // instance way
  return client;
}
```

# Usage
The simplest way of using the library is through ResponseFaker class and its static methods. It contains a singleton instance of FakeResponseInteractor and wraps its method calls so you always use the same interactor everywhere. 

But you can also create your own instance of FakeResponseInteractor and call its methods directly. This is useful if you have a dependency injection setup and you want to manage your objects lifecycle yourself. All the examples below apply to both ways, since they use the same methods.

### Fake a response
Provide any instance of FakeResponse. You can use your own or a built-in one.
```java
public void fakeMyResponse(){
  ResponseFaker.setNextFakeResponse(new FakeResponse(){
    @Override
    public String body() {
        return "this is a fake response body";
    }
    @Override
    public int httpCode() {
        return 404;
    }
    @Override
    public String mediaType() {
        return "text/plain";
    }
  );
}
```

Right now the only built-in response type is **EmptyBodyFakeResponse**
```java
public void fakeNotFoundResponse(){
  ResponseFaker.setNextFakeResponse(new EmptyBodyFakeResponse(404));
}
```

### Trigger more than once
By defaut fake responses are triggered in a "fire and forget" manner, meaning that they will be used once only. To avoid this and keep using the fake response until you manually cancel them use the method **setTriggerOnce(boolean)**
```java
ResponseFaker.setTriggerOnce(false);
```
This flag doesn't change with a new fake responses, you **must** set it to *true* to revert back to "fire and forget" mode, or stop using the fake response with **clearNextFakeResponse()**:
```java
ResponseFaker.clearNextFakeResponse();
```

### Json Responses
If you use the optional Json module you can very easily fake the contents of a response using objects:
```java
public void fakeEmailInUseResponse(){
  ResponseFaker.setNextFakeResponse(new JsonFakeResponse(myJsonAdapter,
                new EmailInUseError(),
                HTTPCODE_INVALID_REQUEST);
}
```

This is super useful if you already have classes representing your server's errors.

# Best served with
By its own this library isn't that great. But you can really take advantage of its power when using it with some debugging tools like [Stetho's dumpapp](http://facebook.github.io/stetho/) or a Debug Drawer with debug actions as seen in [Jake Wharton's u2020](https://github.com/JakeWharton/u2020/).

# Demo
There isnt't a demo project for this. I think the snippets above make it clear enough. If you think it isn't and have a simple project demo idea, open an issue or pull request.

# Contributing
This little library was made for internal usage, and then published to public usage. It's very small and simple, I've just added what we needed. If you consider that it could have more or nicer features, go ahead an *pull request*! I will gladly accept contributions and new features.
