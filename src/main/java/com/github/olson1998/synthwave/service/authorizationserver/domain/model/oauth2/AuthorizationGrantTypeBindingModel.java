package com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.AuthorizationGrantTypeBinding;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizationGrantTypeBindingModel implements AuthorizationGrantTypeBinding {

    @JsonProperty("RCID")
    private Long registeredClientId;

    @JsonProperty("AGID")
    private Long authorizationGrantTypeId;

}
