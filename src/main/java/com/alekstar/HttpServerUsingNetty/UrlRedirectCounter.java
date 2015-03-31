package com.alekstar.HttpServerUsingNetty;

import java.util.SortedMap;
import java.util.TreeMap;

public class UrlRedirectCounter {
    private SortedMap<String, RedirectionsPerUrl> redirectionsPerUrlContainer =
            new TreeMap<String, RedirectionsPerUrl>();

    public synchronized void processUrl(String url) {
        if (getRedirectionsPerUrlContainer().containsKey(url)) {
            getRedirectionsPerUrlContainer().get(url).processRedirection();
        } else {
            RedirectionsPerUrl redirectionsPerUrl = new RedirectionsPerUrl(url);
            redirectionsPerUrl.processRedirection();
            getRedirectionsPerUrlContainer().put(url, redirectionsPerUrl);
        }
    }

    private SortedMap<String, RedirectionsPerUrl> getRedirectionsPerUrlContainer() {
        return redirectionsPerUrlContainer;
    }

    public String generateHtmlTable() {
        if (getRedirectionsPerUrlContainer().size() == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(HtmlTags.generateHeader("Redirections"));
        stringBuilder.append(HtmlTags.defineTableBeginTag());
        stringBuilder.append(defineHeadOfHtmlTable());
        stringBuilder.append(defineHtmlTableRows());
        stringBuilder.append(HtmlTags.defineTableEndTag());
        return stringBuilder.toString();
    }

    private String defineHtmlTableRows() {
        StringBuilder stringBuilder = new StringBuilder();
        for (RedirectionsPerUrl current : getRedirectionsPerUrlContainer()
                .values()) {
            stringBuilder.append(HtmlTags.defineTableRow(current.getUrl(),
                    new Long(current.getRedirectionsAmount()).toString()));
        }
        return stringBuilder.toString();
    }

    private String defineHeadOfHtmlTable() {
        return HtmlTags.defineHeadOfTable(defineUrlColumnName(),
                defineRedirectionsAmountColumnName());
    }

    private String defineRedirectionsAmountColumnName() {
        return "Redirections amount";
    }

    private String defineUrlColumnName() {
        return "Url";
    }
}
