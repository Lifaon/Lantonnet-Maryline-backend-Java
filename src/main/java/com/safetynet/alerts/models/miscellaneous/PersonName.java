package com.safetynet.alerts.models.miscellaneous;

import com.safetynet.alerts.Utils;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PersonName {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    public <Child extends PersonName> PersonName(Child child) {
        Utils.copyFields(PersonName.class, child, this);
    }

    final public <T extends PersonName> boolean sameName(PersonName personName) {
        return firstName.equals(personName.firstName) && lastName.equals(personName.lastName);
    }

    @Override
    public String toString() {
        return firstName + ' ' + lastName;
    }
}
