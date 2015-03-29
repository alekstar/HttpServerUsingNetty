package com.alekstar.HttpServerUsingNetty;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public class StatusUriProcessor implements UriProcessor {
    int requestsAmount;
    FullHttpResponse response;

    public StatusUriProcessor(int requestsAmount) {
        setRequestsAmount(requestsAmount);
    }

    public void setResponse(FullHttpResponse response) {
        this.response = response;
    }

    private int getRequestsAmount() {
        return requestsAmount;
    }

    private void setRequestsAmount(int requestsAmount) {
        this.requestsAmount = requestsAmount;
    }

    public FullHttpResponse getResponse() {
        return this.response;
    }

    public void process() {
        setResponse(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.wrappedBuffer(defineResponseString().getBytes())));
    }

    private String defineResponseString() {
        return "<!DOCTYPE HTML>" + "<html lang=\"en-US\">" + "<head>"
                + "</head>" + "<body>Overall amount of requests: "
                + getRequestsAmount() + "</body>" + "</html>";
    }
}
