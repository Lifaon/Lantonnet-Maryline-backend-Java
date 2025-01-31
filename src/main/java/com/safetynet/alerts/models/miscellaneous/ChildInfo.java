package com.safetynet.alerts.models.miscellaneous;

import com.safetynet.alerts.Utils;

public class ChildInfo extends PersonName {
    public int age;

    public ChildInfo(PersonName name, int age) {
        Utils.copyFields(PersonName.class, name, this);
        this.age = age;
    }
}
