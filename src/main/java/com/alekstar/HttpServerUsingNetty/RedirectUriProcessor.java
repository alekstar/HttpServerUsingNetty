package com.alekstar.HttpServerUsingNetty;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public class RedirectUriProcessor implements UriProcessor {
    private FullHttpResponse response;
    private String uri;
    private UrlRedirectCounter redirectionsCounter;

    public RedirectUriProcessor(String uri,
            UrlRedirectCounter redirectionsCounter) {
        setUri(uri);
        setRedirectionsCounter(redirectionsCounter);
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

    private UrlRedirectCounter getRedirectionsCounter() {
        return redirectionsCounter;
    }

    private void setRedirectionsCounter(UrlRedirectCounter redirectionsCounter) {
        if (redirectionsCounter == null) {
            throw new IllegalArgumentException(
                    "Argument redirectionsCounter is null.");
        }
        this.redirectionsCounter = redirectionsCounter;
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
        String redirectUrl = defineRedirectUrl(getUri());
        setResponse(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.MOVED_PERMANENTLY,
                Unpooled.wrappedBuffer(defineResponseString(redirectUrl)
                        .getBytes())));
        getRedirectionsCounter().processUrl(redirectUrl);
    }

    private String defineResponseString(String redirectUrl) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(HtmlTags.defineDocumentTypeTag());
        stringBuilder.append(HtmlTags.defineHtmlBeginTag());
        stringBuilder.append(HtmlTags.defineHeadBeginTag());
        stringBuilder.append(HtmlTags.defineRedirectionMetaTag(redirectUrl));
        stringBuilder.append(HtmlTags.defineHeadEndTag());
        stringBuilder.append(HtmlTags.defineBodyBeginTag());
        stringBuilder.append(HtmlTags.defineBodyBeginTag());
        stringBuilder.append(HtmlTags.defineHtmlBeginTag());
        return stringBuilder.toString();
    }
}
