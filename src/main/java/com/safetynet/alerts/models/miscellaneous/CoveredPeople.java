package com.safetynet.alerts.models.miscellaneous;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class CoveredPeople {
    private int children = 0;
    private int adults = 0;
    private List<PersonInfo> people = new ArrayList<>();
    public void addChild() {
        children++;
    }
    public void addAdult() {
        adults++;
    }
}
