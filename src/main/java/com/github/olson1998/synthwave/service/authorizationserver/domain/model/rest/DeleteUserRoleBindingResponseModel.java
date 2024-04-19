package com.github.olson1998.synthwave.service.authorizationserver.domain.model.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.rest.stereotype.DeleteUserRoleBindingResponse;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.rest.stereotype.DeletedRows;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteUserRoleBindingResponseModel implements DeleteUserRoleBindingResponse {

    @JsonProperty("USID")
    private Long userId;

    @JsonProperty("RLID")
    private Collection<Long> parameters;

    @Override
    public DeletedRows getDeletedRows() {
        return null;
    }
}
