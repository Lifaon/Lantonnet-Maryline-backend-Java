package com.safetynet.alerts;

import java.util.ArrayList;

public class Person extends PersonInfo {
    public String city;
    public String zip;
    public String email;
    public Person() {};
    public Person(String firstName, String lastName, String address, String phoneNumber, String city, String zip, String email) {
        super(firstName, lastName, address, phoneNumber);
        this.city = city;
        this.zip = zip;
        this.email = email;
    };
};
