package com.safetynet.alerts;

public class PersonInfo extends PersonName {
    public String address;
    public String phone;
    public PersonInfo() {};
    public PersonInfo(String firstName, String lastName, String address, String phone) {
        super(firstName, lastName);
        this.address = address;
        this.phone = phone;
    };
}
