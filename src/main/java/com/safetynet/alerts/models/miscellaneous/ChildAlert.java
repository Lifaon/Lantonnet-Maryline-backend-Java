package com.safetynet.alerts.models.miscellaneous;

import com.safetynet.alerts.models.PersonName;

import java.util.ArrayList;
import java.util.List;

public class ChildAlert {
    public List<ChildInfo> children = new ArrayList<>();
    public List<PersonName> adults = new ArrayList<>();
}
