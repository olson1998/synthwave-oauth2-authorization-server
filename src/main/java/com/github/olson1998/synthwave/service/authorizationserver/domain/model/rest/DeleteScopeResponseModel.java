package com.github.olson1998.synthwave.service.authorizationserver.domain.model.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.rest.stereotype.DeleteScopeResponse;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.rest.stereotype.DeletedRows;
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

    @JsonProperty("OAU2SCPD")
    private DeletedRowsModel deletedRowsModel;

    @JsonProperty("OAU2SCPB")
    private DeletedRowsModel deletedBoundsModel;

    @Override
    public DeletedRows getDeletedRows() {
        return deletedRowsModel;
    }

    @Override
    public DeletedRows getDeletedBounds() {
        return deletedBoundsModel;
    }
}
