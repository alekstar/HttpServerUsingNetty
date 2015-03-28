package com.alekstar.HttpServerUsingNetty;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public class HelloUriProcessor implements UriProcessor {
    private FullHttpResponse response;

    public HelloUriProcessor() {

    }

    public FullHttpResponse getResponse() {
        return response;
    }

    private void setResponse(FullHttpResponse response) {
        this.response = response;
    }

    public void process() {
        try {
            waitFor10Seconds();
        } catch (InterruptedException e) {
            System.err.println("Waiting for 10 seconds interrupted.");
        }
        setResponse(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.wrappedBuffer(defineResponseString().getBytes())));
    }

    private String defineResponseString() {
        return "Hello World";
    }

    private void waitFor10Seconds() throws InterruptedException {
        Thread.sleep(10000);
    }

}
