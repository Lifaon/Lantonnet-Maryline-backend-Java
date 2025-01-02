package com.safetynet.alerts.models.miscellaneous;

import com.safetynet.alerts.models.PersonName;
import com.safetynet.alerts.Utils;

public class ChildInfo extends PersonName {
    public int age;

    public ChildInfo() {}

    public ChildInfo(PersonName name, int age) {
        Utils.copyFields(PersonName.class, name, this);
        this.age = age;
    }

    public ChildInfo(String firstName, String lastName, int age) {
        super(firstName, lastName);
        this.age = age;
    }
}
