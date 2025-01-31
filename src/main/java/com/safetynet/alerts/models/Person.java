package com.safetynet.alerts.models;

import com.safetynet.alerts.models.miscellaneous.PersonInfo;
import com.safetynet.alerts.models.miscellaneous.PersonName;

public class Person extends PersonInfo implements Identifier<PersonName> {
    public String city;
    public String zip;
    public String email;

    @Override
    public PersonName getIdentifier() {
        return this;
    }
};
