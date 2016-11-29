package org.paradise.microservice.userpreference.domain;

/**
 * Created by terrence on 28/11/2016.
 */
public enum PreferenceType {

    ACCOUNT("Account"),
    SENDING("Sending"),
    EBAY("eBay");

    private String type;

    PreferenceType(String type) {
        this.type = type;
    }

    public String value() {
        return type;
    }

}
