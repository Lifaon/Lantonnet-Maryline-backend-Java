package com.safetynet.alerts.models.miscellaneous;

import com.safetynet.alerts.Utils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class PersonInfo extends PersonName {
    private String address;
    private String phone;

    public <Child extends PersonInfo> PersonInfo (Child child) {
        Utils.copyFields(PersonInfo.class, child, this);
    }
}
