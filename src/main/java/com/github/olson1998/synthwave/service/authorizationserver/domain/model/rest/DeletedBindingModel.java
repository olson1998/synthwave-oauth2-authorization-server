package com.github.olson1998.synthwave.service.authorizationserver.domain.model.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.rest.stereotype.DeletedBinding;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeletedBindingModel implements DeletedBinding {

    @JsonProperty("COUNT")
    private int quantity;

}
