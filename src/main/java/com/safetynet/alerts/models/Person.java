package com.safetynet.alerts.models;

import com.safetynet.alerts.models.miscellaneous.PersonInfo;
import com.safetynet.alerts.models.miscellaneous.PersonName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class Person extends PersonInfo implements Identifier<PersonName> {
    private String city;
    private String zip;
    private String email;

    @Override
    public PersonName getIdentifier() {
        return this;
    }
};
