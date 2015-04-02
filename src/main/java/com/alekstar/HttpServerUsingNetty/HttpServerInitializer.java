package com.alekstar.HttpServerUsingNetty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {
    RequestCounter requestCounter = new RequestCounter();
    UrlRedirectCounter redirectionsCounter = new UrlRedirectCounter();

    public HttpServerInitializer() {

    }

    private RequestCounter getRequestCounter() {
        return requestCounter;
    }

    private UrlRedirectCounter getRedirectionsCounter() {
        return redirectionsCounter;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline channelPipeline = socketChannel.pipeline();
        channelPipeline.addLast("codec", new HttpServerCodec());
        channelPipeline.addLast("main handler", new HttpServerHandler(
                getRequestCounter(), getRedirectionsCounter()));
    }
}
