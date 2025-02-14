package com.safetynet.alerts.models.miscellaneous;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class ChildAlert {
    private List<ChildInfo> children = new ArrayList<>();
    private List<PersonName> adults = new ArrayList<>();
}
