package com.safetynet.alerts.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

@NoArgsConstructor
@Setter
@Getter
public class Firestation implements Identifier<String> {
    @NotBlank
    private String address;
    @NotBlank
    private String station;

    @Override
    public String getIdentifier() {
        return address;
    }
}
