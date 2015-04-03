package com.alekstar.HttpServerUsingNetty;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpHeaders.Values;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.*;

public class HttpServerHandler extends ChannelInboundHandlerAdapter {
    private RequestCounter requestCounter;
    private UrlRedirectCounter redirectionsCounter;

    public HttpServerHandler(RequestCounter requestCounter,
            UrlRedirectCounter redirectionsCounter) {
        if (requestCounter == null) {
            throw new IllegalArgumentException(
                    "Argument requestCounter is null.");
        }

        if (redirectionsCounter == null) {
            throw new IllegalArgumentException(
                    "Argument urlRedirectCounter is null.");
        }
        this.requestCounter = requestCounter;
        this.redirectionsCounter = redirectionsCounter;
    }

    private RequestCounter getRequestCounter() {
        return requestCounter;
    }

    private UrlRedirectCounter getRedirectionsCounter() {
        return redirectionsCounter;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext context) {
        context.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext context, Object message) {
        if (!(message instanceof HttpRequest)) {
            return;
        }

        HttpRequest request = (HttpRequest) message;
        getRequestCounter().processSocketAddress(
                context.channel().remoteAddress());
        proccessContinueRequest(context, request);
        boolean keepAlive = haveHeadersKeepAliveRequest(request);
        FullHttpResponse response = defineResponse(request.getUri());

        if (!keepAlive) {
            context.write(response).addListener(ChannelFutureListener.CLOSE);
        } else {
            response.headers().set(CONNECTION, Values.KEEP_ALIVE);
            context.write(response);
        }
    }

    private boolean haveHeadersKeepAliveRequest(HttpRequest request) {
        return HttpHeaders.isKeepAlive(request);
    }

    private void proccessContinueRequest(ChannelHandlerContext context,
            HttpRequest request) {
        if (HttpHeaders.is100ContinueExpected(request)) {
            context.write(new DefaultFullHttpResponse(HTTP_1_1, CONTINUE));
        }
    }

    private UriProcessor defineUriProcessor(String uri) {
        if (isHelloUri(uri)) {
            return new HelloUriProcessor();
        } else if (isRedirectUri(uri)) {
            return new RedirectUriProcessor(uri, getRedirectionsCounter());
        } else if (isStatusUri(uri)) {
            return new StatusUriProcessor(getRequestCounter(),
                    getRedirectionsCounter());
        } else {
            return new NotFoundUriProcessor();
        }
    }

    private boolean isHelloUri(String uri) {
        return uri.equals("/hello");
    }

    private boolean isRedirectUri(String uri) {
        if (uri.length() < "/redirect?url=".length()) {
            return false;
        }
        return uri.substring(0, "/redirect?url=".length()).equals(
                "/redirect?url=");
    }

    private boolean isStatusUri(String uri) {
        return uri.equals("/status");
    }

    private FullHttpResponse defineResponse(String uri) {
        UriProcessor processor = defineUriProcessor(uri);
        processor.process();
        FullHttpResponse response = processor.getResponse();
        response.headers().set(CONTENT_TYPE, "text/html");
        response.headers().set(CONTENT_LENGTH,
                response.content().readableBytes());
        return response;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
        cause.printStackTrace();
        context.close();
    }
}
