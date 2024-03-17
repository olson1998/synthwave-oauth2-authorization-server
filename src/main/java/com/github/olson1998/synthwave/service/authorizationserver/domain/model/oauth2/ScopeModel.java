package com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.Scope;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.MutableDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScopeModel implements Scope {

    @JsonProperty(value = "ID")
    private Long id;

    @JsonProperty(value = "NAME")
    private String name;

    @JsonProperty(value = "CTMP")
    private MutableDateTime createdOn;

    public ScopeModel(Scope scope) {
        this.id = scope.getId();
        this.name = scope.getName();
        this.createdOn = scope.getCreatedOn();
    }

    public ScopeModel(String name) {
        this.name = name;
    }
}
