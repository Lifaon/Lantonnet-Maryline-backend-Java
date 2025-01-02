package com.safetynet.alerts.models;

import com.safetynet.alerts.Utils;

public class PersonInfo extends PersonName {
    public String address;
    public String phone;

    public PersonInfo() {};

    private PersonInfo(String address, String phone) {
        this.address = address;
        this.phone = phone;
    }

    public PersonInfo(PersonName name, String address, String phone) {
        this(address, phone);
        Utils.copyFields(PersonName.class, name, this);
    }

    public PersonInfo(String firstName, String lastName, String address, String phone) {
        this(new PersonName(lastName, firstName), address, phone);
    };
}
