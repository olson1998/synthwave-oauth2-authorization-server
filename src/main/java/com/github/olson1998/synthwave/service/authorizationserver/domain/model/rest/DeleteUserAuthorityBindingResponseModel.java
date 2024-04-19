package com.github.olson1998.synthwave.service.authorizationserver.domain.model.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.rest.stereotype.DeleteUserAuthorityBindingResponse;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.rest.stereotype.DeletedRows;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteUserAuthorityBindingResponseModel implements DeleteUserAuthorityBindingResponse {

    @JsonProperty("USID")
    private Long userId;

    @JsonProperty("AUTHBIND")
    private Collection<Long> parameters;

    @Override
    public DeletedRows getDeletedRows() {
        return null;
    }
}
