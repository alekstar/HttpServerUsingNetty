package com.alekstar.HttpServerUsingNetty;

import io.netty.handler.codec.http.FullHttpResponse;

public interface UriProcessor {
    FullHttpResponse getResponse();

    void process();
}
