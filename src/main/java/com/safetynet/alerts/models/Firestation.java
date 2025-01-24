package com.safetynet.alerts.models;

public class Firestation implements Identifier<String> {
    public String address;
    public String station;

    @Override
    public String getIdentifier() {
        return address;
    }
}
