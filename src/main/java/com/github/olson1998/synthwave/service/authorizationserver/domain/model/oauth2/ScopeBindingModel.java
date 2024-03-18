package com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.ScopeBinding;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.MutableDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScopeBindingModel implements ScopeBinding {

    @JsonProperty(value = "RCID", required = true)
    private Long registeredClientId;

    @JsonProperty(value = "SCID", required = true)
    private Long scopeId;

    @JsonProperty(value = "CTMP")
    private MutableDateTime createdOn;

    public ScopeBindingModel(ScopeBinding scope) {
        this.registeredClientId = scope.getRegisteredClientId();
        this.scopeId = scope.getScopeId();
        this.createdOn = scope.getCreatedOn();
    }

    public ScopeBindingModel(Long registeredClientId, Long scopeId) {
        this.registeredClientId = registeredClientId;
        this.scopeId = scopeId;
    }
}
