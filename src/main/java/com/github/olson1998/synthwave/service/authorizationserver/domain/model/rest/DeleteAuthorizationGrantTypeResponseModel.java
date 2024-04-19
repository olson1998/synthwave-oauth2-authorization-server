package com.github.olson1998.synthwave.service.authorizationserver.domain.model.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.rest.stereotype.DeleteAuthorizationGrantTypeResponse;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.rest.stereotype.DeletedRows;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteAuthorizationGrantTypeResponseModel implements DeleteAuthorizationGrantTypeResponse {

    @JsonProperty("AGID")
    private Long parameters;

    @JsonProperty("OAU2AGTB")
    private DeletedRowsModel deletedBoundsModel;

    @Override
    public DeletedRows getDeletedBounds() {
        return deletedBoundsModel;
    }

    @Override
    public DeletedRows getDeletedRows() {
        return null;
    }
}
