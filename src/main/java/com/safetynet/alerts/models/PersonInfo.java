package com.safetynet.alerts.models;

import com.safetynet.alerts.Utils;

public class PersonInfo extends PersonName {
    public String address;
    public String phone;

    public PersonInfo() {};
    public <Child extends PersonInfo> PersonInfo (Child child) {
        Utils.copyFields(PersonInfo.class, child, this);
    }
}
