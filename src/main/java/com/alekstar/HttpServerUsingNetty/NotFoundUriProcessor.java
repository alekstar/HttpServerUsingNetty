package com.alekstar.HttpServerUsingNetty;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public class NotFoundUriProcessor implements UriProcessor {
    private FullHttpResponse response;

    public FullHttpResponse getResponse() {
        return this.response;
    }

    private void setResponse(FullHttpResponse response) {
        this.response = response;
    }

    public void process() {
        setResponse(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.NOT_FOUND,
                Unpooled.wrappedBuffer(defineResponseString().getBytes())));
    }

    private String defineResponseString() {
        return "404 - Page not found";
    }

}
