package com.github.olson1998.synthwave.service.authorizationserver.domain.model.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.rest.stereotype.DeleteScopeResponse;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.rest.stereotype.DeletedBinding;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteScopeResponseModel implements DeleteScopeResponse {

    @JsonProperty("RCID")
    private Collection<Long> parameters;

    @JsonProperty("OAU2SCPB")
    private DeletedBindingModel deletedBoundsModel;

    @Override
    public DeletedBinding getDeletedBounds() {
        return deletedBoundsModel;
    }
}
