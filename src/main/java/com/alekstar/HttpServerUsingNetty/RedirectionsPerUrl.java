package com.alekstar.HttpServerUsingNetty;

public class RedirectionsPerUrl {
    private String url;
    private long redirectionsAmount;

    public RedirectionsPerUrl(String url) {
        setUrl(url);
        this.redirectionsAmount = 0;
    }

    public long getRedirectionsAmount() {
        return this.redirectionsAmount;
    }

    public void setUrl(String url) {
        if (url == null) {
            throw new IllegalArgumentException("Argument url is null");
        }
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    private void incrementRedirectionsAmount() {
        this.redirectionsAmount++;
    }

    public void processRedirection() {
        incrementRedirectionsAmount();
    }
}
