package com.safetynet.alerts.models;

import com.safetynet.alerts.Utils;

public class PersonName {
    public String firstName;
    public String lastName;

    public PersonName() {}

    public PersonName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public <Child extends PersonName> PersonName(Child child) {
        Utils.copyFields(PersonName.class, child, this);
    }
}
