package com.github.olson1998.synthwave.service.authorizationserver.domain.model.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.rest.stereotype.DeleteScopeBindingResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteScopeBindingResponseModel implements DeleteScopeBindingResponse {

    @JsonProperty("RCID")
    private Long registeredClientId;

    @JsonProperty("SCID")
    private Collection<Long> parameters;

}
