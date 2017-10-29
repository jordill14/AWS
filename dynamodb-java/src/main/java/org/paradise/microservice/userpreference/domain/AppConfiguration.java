package org.paradise.microservice.userpreference.domain;

import java.util.List;

public class AppConfiguration {

    // A list of email addresses allow to access API
    private List<String> whitelist;

    // Session Configuration expiry time
    private long expiryInSeconds;

    public List<String> getWhitelist() {
        return whitelist;
    }

    public void setWhitelist(List<String> whitelist) {
        this.whitelist = whitelist;
    }

    public long getExpiryInSeconds() {
        return expiryInSeconds;
    }

    public void setExpiryInSeconds(long expiryInSeconds) {
        this.expiryInSeconds = expiryInSeconds;
    }

}
