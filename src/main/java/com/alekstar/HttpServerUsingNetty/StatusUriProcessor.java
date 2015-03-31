package com.alekstar.HttpServerUsingNetty;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public class StatusUriProcessor implements UriProcessor {
    RequestCounter requestCounter;
    FullHttpResponse response;
    UrlRedirectCounter redirectionsCounter;

    public StatusUriProcessor(RequestCounter requestCounter,
            UrlRedirectCounter redirectionsCounter) {
        setRequestCounter(requestCounter);
        setRedirectionsCounter(redirectionsCounter);
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

    private UrlRedirectCounter getRedirectionsCounter() {
        return redirectionsCounter;
    }

    private void setRedirectionsCounter(UrlRedirectCounter redirectionsCounter) {
        if (redirectionsCounter == null) {
            throw new IllegalArgumentException(
                    "Argument redirectionsCounter is null");
        }
        this.redirectionsCounter = redirectionsCounter;
    }

    private void setResponse(FullHttpResponse response) {
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
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(HtmlTags.defineDocumentTypeTag());
        stringBuilder.append(HtmlTags.defineHtmlBeginTag());
        stringBuilder.append(HtmlTags.defineHeadBeginTag());
        stringBuilder.append(HtmlTags.defineDefaultMetaTag());
        stringBuilder.append(HtmlTags.defineTitle(defineTitle()));
        stringBuilder.append(HtmlTags.defineHeadEndTag());
        stringBuilder.append(HtmlTags.defineBodyBeginTag());
        stringBuilder.append("Overall amount of requests: ");
        stringBuilder.append(getRequestCounter().getOverallRequestAmount());
        stringBuilder.append(HtmlTags.defineBreakLineTag());
        stringBuilder.append("Requests from unique IPs: ");
        stringBuilder.append(getRequestCounter().getUniqueIpRequestsAmount());
        stringBuilder.append(HtmlTags.defineBreakLineTag());
        stringBuilder.append(HtmlTags.defineBreakLineTag());
        stringBuilder.append(getRequestCounter().generateHtmlTable());
        stringBuilder.append(HtmlTags.defineBreakLineTag());
        stringBuilder.append(HtmlTags.defineBreakLineTag());
        stringBuilder.append(getRedirectionsCounter().generateHtmlTable());
        stringBuilder.append(HtmlTags.defineBodyEndTag());
        stringBuilder.append(HtmlTags.defineHtmlEndTag());
        return stringBuilder.toString();
    }

    private String defineTitle() {
        return "Statistics";
    }
}
