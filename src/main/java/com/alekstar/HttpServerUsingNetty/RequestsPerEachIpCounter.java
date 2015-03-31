package com.alekstar.HttpServerUsingNetty;

import java.util.Date;

public class RequestsPerEachIpCounter {
    String ip;
    long requestsAmount;
    Date timestampOfLastRequest;

    public RequestsPerEachIpCounter(String ip) {
        setIp(ip);
        this.requestsAmount = 0;
    }

    public String getIp() {
        return ip;
    }

    private void setIp(String ip) {
        if (ip == null) {
            throw new IllegalArgumentException("Argument ip is null.");
        }
        this.ip = ip;
    }

    public Date getTimestampOfLastRequest() {
        if (requestsAmount == 0) {
            return null;
        }
        return (Date) timestampOfLastRequest.clone();
    }

    private void setTimestampOfLastRequest(Date timestampOfLastRequest) {
        if (timestampOfLastRequest == null) {
            throw new IllegalArgumentException(
                    "Argument timestampOfLastRequest is null.");
        }
        this.timestampOfLastRequest = timestampOfLastRequest;
    }

    public long getRequestsAmount() {
        return requestsAmount;
    }

    private void incrementRequestsAmount() {
        this.requestsAmount++;
    }

    public void processIncomingRequest() {
        setTimestampOfLastRequest(new Date());
        incrementRequestsAmount();
    }
}
