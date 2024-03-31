package com.github.olson1998.synthwave.service.authorizationserver.domain.model.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.rest.stereotype.DeleteRoleResponse;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.rest.stereotype.DeletedBinding;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteRoleResponseModel implements DeleteRoleResponse {

    @JsonProperty("RLID")
    private Collection<Long> parameters;

    @JsonProperty("ROLEBIND")
    private DeletedBinding deletedBinding;

}
