package com.safetynet.alerts.models.miscellaneous;

import com.safetynet.alerts.Utils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Setter
@Getter
public class ChildInfo extends PersonName {
    private int age;

    public ChildInfo(PersonName name, int age) {
        Utils.copyFields(PersonName.class, name, this);
        this.age = age;
    }
}
