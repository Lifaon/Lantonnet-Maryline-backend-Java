package com.safetynet.alerts.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface Identifier <T> {
    @JsonIgnore
    T getIdentifier();
}
