package com.alekstar.HttpServerUsingNetty;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public class RedirectUriProcessor implements UriProcessor {
    private FullHttpResponse response;
    private String uri;

    public RedirectUriProcessor(String uri) {
        setUri(uri);
    }

    private String getUri() {
        return uri;
    }

    private void setUri(String uri) {
        if (uri == null) {
            throw new IllegalArgumentException("Argument uri is null.");
        }
        this.uri = uri;
    }

    private void setResponse(FullHttpResponse response) {
        this.response = response;
    }

    public FullHttpResponse getResponse() {
        return this.response;
    }

    private int defineIndexOfEndOfRedirectionUrl(
            String stringAfterRedirectKeyWord) {
        int indexOfNextAmpersand = stringAfterRedirectKeyWord.indexOf('&');
        if (indexOfNextAmpersand == -1) {
            return stringAfterRedirectKeyWord.length();
        } else {
            return indexOfNextAmpersand;
        }
    }

    private String defineRedirectUrl(String uri) {
        String stringAfterRedirectKeyWord =
                uri.substring("/redirect?url=".length());
        return stringAfterRedirectKeyWord.substring(0,
                defineIndexOfEndOfRedirectionUrl(stringAfterRedirectKeyWord));
    }

    public void process() {
        setResponse(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.MOVED_PERMANENTLY,
                Unpooled.wrappedBuffer(defineResponseString(
                        defineRedirectUrl(getUri())).getBytes())));
    }

    private String defineResponseString(String redirectUrl) {
        return "<!DOCTYPE HTML>" + "<html lang=\"en-US\">" + "<head>"
                + "<meta http-equiv=\"refresh\" content=\"0; url="
                + redirectUrl + "\" />" + "</head>" + "<body></body>"
                + "</html>";
    }
}
