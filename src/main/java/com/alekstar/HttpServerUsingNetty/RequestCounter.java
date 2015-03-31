package com.alekstar.HttpServerUsingNetty;

import java.net.SocketAddress;
import java.util.SortedMap;
import java.util.TreeMap;

public class RequestCounter {
    private int requestsAmount = 0;
    private SortedMap<String, RequestsPerEachIpCounter> requestsPerEachIpCounters =
            new TreeMap<String, RequestsPerEachIpCounter>();

    public int getOverallRequestAmount() {
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
        stringBuilder.append("<h3>Requests per each IP</h3>");
        stringBuilder.append(defineHtmlTableBeginTag());
        stringBuilder.append(defineHeadOfHtmlTable());
        stringBuilder.append(defineHtmlTableRows());
        stringBuilder.append(defineHtmlTableEndTag());

        return stringBuilder.toString();
    }

    private String defineHtmlTableRows() {
        StringBuilder stringBuilder = new StringBuilder();
        for (RequestsPerEachIpCounter current : getRequestsPerEachIpCounters()
                .values()) {
            stringBuilder.append("<tr>");
            stringBuilder.append("<td>");
            stringBuilder.append(current.getIp());
            stringBuilder.append("</td>");
            stringBuilder.append("<td>");
            stringBuilder.append(current.getRequestsAmount());
            stringBuilder.append("</td>");
            stringBuilder.append("<td>");
            stringBuilder.append(current.getTimestampOfLastRequest());
            stringBuilder.append("</td>");
            stringBuilder.append("</tr>");
        }
        return stringBuilder.toString();
    }

    private String defineHtmlTableEndTag() {
        return "</table>";
    }

    private String defineHtmlTableBeginTag() {
        return "<table border = \"1\">";
    }

    private String defineHeadOfHtmlTable() {
        return "<tr><th>IP</th><th>Requests amount</th><th>Last request time</th><tr>";
    }
}
