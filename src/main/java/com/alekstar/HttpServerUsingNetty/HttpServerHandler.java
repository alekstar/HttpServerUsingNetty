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

    @Override
    public void channelReadComplete(ChannelHandlerContext context) {
        context.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext context, Object message) {
        if (message instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) message;

            if (HttpHeaders.is100ContinueExpected(request)) {
                context.write(new DefaultFullHttpResponse(HTTP_1_1, CONTINUE));
            }
            boolean keepAlive = HttpHeaders.isKeepAlive(request);
            FullHttpResponse response = defineResponse(request.getUri());
            response.headers().set(CONTENT_TYPE, "text/html");
            response.headers().set(CONTENT_LENGTH,
                    response.content().readableBytes());

            if (!keepAlive) {
                context.write(response)
                        .addListener(ChannelFutureListener.CLOSE);
            } else {
                response.headers().set(CONNECTION, Values.KEEP_ALIVE);
                context.write(response);
            }
        }
    }

    private UriProcessor defineUriProcessor(String uri) {
        if (isHelloUri(uri)) {
            return new HelloUriProcessor();
        } else if (isRedirectUri(uri)) {
            return new RedirectUriProcessor(uri);
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

    private FullHttpResponse defineResponse(String uri) {
        UriProcessor processor = defineUriProcessor(uri);
        processor.process();
        return processor.getResponse();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
        cause.printStackTrace();
        context.close();
    }
}
