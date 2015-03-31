package com.alekstar.HttpServerUsingNetty;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public class StatusUriProcessor implements UriProcessor {
    RequestCounter requestCounter;
    FullHttpResponse response;

    public StatusUriProcessor(RequestCounter requestCounter) {
        setRequestCounter(requestCounter);
    }

    private RequestCounter getRequestCounter() {
        return requestCounter;
    }

    private void setRequestCounter(RequestCounter requestCounter) {
        if (requestCounter == null) {
            throw new IllegalArgumentException(
                    "Argument requestCounter is null.");
        }
        this.requestCounter = requestCounter;
    }

    public void setResponse(FullHttpResponse response) {
        this.response = response;
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
        return "<!DOCTYPE HTML>" + "<meta charset=\"utf-8\">" + "<head>"
                + "</head>" + "<body>Overall amount of requests: "
                + getRequestCounter().getOverallRequestAmount() + "<br>"
                + "Request from unique IPs: "
                + getRequestCounter().getUniqueIpRequestsAmount() + "<br>"
                + "<br>" + getRequestCounter().generateHtmlTable() + "</body>"
                + "</html>";
    }
}
