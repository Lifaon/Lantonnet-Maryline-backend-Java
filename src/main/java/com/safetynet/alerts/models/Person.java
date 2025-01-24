package com.safetynet.alerts.models;

public class Person extends PersonInfo implements Identifier<PersonName> {
    public String city;
    public String zip;
    public String email;

    @Override
    public PersonName getIdentifier() {
        return this;
    }
};
