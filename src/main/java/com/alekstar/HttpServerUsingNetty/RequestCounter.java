package com.alekstar.HttpServerUsingNetty;

import java.net.SocketAddress;
import java.util.SortedMap;
import java.util.TreeMap;

public class RequestCounter {
    private long requestsAmount = 0;
    private SortedMap<String, RequestsPerEachIpCounter> requestsPerEachIpCounters =
            new TreeMap<String, RequestsPerEachIpCounter>();

    public long getOverallRequestAmount() {
        return this.requestsAmount;
    }

    private void incrementRequestAmount() {
        this.requestsAmount++;
    }

    private void processIp(String ip) {
        if (getRequestsPerEachIpCounters().containsKey(ip)) {
            getRequestsPerEachIpCounters().get(ip).processIncomingRequest();
        } else {
            RequestsPerEachIpCounter requestsPerEachIpCounter =
                    new RequestsPerEachIpCounter(ip);
            requestsPerEachIpCounter.processIncomingRequest();
            getRequestsPerEachIpCounters().put(ip, requestsPerEachIpCounter);
        }
        incrementRequestAmount();
    }

    public synchronized void processSocketAddress(SocketAddress address) {
        processIp(defineIpFromAddressString(address.toString()));
    }

    public int getUniqueIpRequestsAmount() {
        return getRequestsPerEachIpCounters().size();
    }

    private SortedMap<String, RequestsPerEachIpCounter> getRequestsPerEachIpCounters() {
        return this.requestsPerEachIpCounters;
    }

    private String defineIpFromAddressString(String remoteAddressString) {
        if (!isValidIpAddress(remoteAddressString)) {
            throw new IllegalArgumentException(
                    "Argument remoteAddressString is not valid IP address.");
        }
        return remoteAddressString.substring(
                defineBeginIndexOfIpAddressAtString(remoteAddressString),
                defineEndIndexOfIpAddressAtString(remoteAddressString));
    }

    private int defineEndIndexOfIpAddressAtString(String remoteAddressString) {
        return remoteAddressString.indexOf(defineEndOfIpAddressCharacter());
    }

    private int defineBeginIndexOfIpAddressAtString(String remoteAddressString) {
        return remoteAddressString.indexOf(defineBeginOfIpAddressCharacter()) + 1;
    }

    private char defineEndOfIpAddressCharacter() {
        return ':';
    }

    private char defineBeginOfIpAddressCharacter() {
        return '/';
    }

    private boolean isValidIpAddress(String remoteAddressString) {
        return isBeginOfIpAddressCharacterExists(remoteAddressString)
                && isEndOfIpAddressCharacterExists(remoteAddressString);
    }

    private boolean isEndOfIpAddressCharacterExists(String remoteAddressString) {
        return defineEndIndexOfIpAddressAtString(remoteAddressString) != -1;
    }

    private boolean isBeginOfIpAddressCharacterExists(String remoteAddressString) {
        return defineBeginIndexOfIpAddressAtString(remoteAddressString) != -1;
    }

    public String generateHtmlTable() {
        if (getUniqueIpRequestsAmount() == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(HtmlTags.generateHeader(defineTableName()));
        stringBuilder.append(HtmlTags.defineTableBeginTag());
        stringBuilder.append(defineHeadOfHtmlTable());
        stringBuilder.append(defineHtmlTableRows());
        stringBuilder.append(HtmlTags.defineTableEndTag());
        return stringBuilder.toString();
    }

    private String defineTableName() {
        return "Requests for each IP";
    }

    private String defineHtmlTableRows() {
        StringBuilder stringBuilder = new StringBuilder();
        for (RequestsPerEachIpCounter current : getRequestsPerEachIpCounters()
                .values()) {
            stringBuilder.append(HtmlTags.defineTableRow(current.getIp(),
                    new Long(current.getRequestsAmount()).toString(), current
                            .getTimestampOfLastRequest().toString()));
        }
        return stringBuilder.toString();
    }

    private String defineHeadOfHtmlTable() {
        return HtmlTags.defineHeadOfTable(defineIpColumnName(),
                defineRequestsAmountColumnName(),
                defineLastRequestTimeColumnName());
    }

    private String defineLastRequestTimeColumnName() {
        return "Last request time";
    }

    private String defineRequestsAmountColumnName() {
        return "Requests amount";
    }

    private String defineIpColumnName() {
        return "IP";
    }
}
