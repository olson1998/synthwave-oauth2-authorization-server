package com.github.olson1998.synthwave.service.authorizationserver.domain.model.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.rest.stereotype.DeleteAuthorizationGrantTypeBindingResponse;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.rest.stereotype.DeletedRows;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteAuthorizationGrantTypeBindingResponseModel implements DeleteAuthorizationGrantTypeBindingResponse {

    @JsonProperty("AGID")
    private Long parameters;

    @JsonProperty("RCID")
    private Long registeredClientId;

    @JsonProperty("OAU2AGTB")
    private DeletedRowsModel deletedRowsModel;

    @Override
    public DeletedRows getDeletedRows() {
        return deletedRowsModel;
    }
}
