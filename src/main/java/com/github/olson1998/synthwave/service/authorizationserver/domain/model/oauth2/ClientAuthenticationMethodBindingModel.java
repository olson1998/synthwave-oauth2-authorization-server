package com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.ClientAuthenticationMethodBinding;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientAuthenticationMethodBindingModel implements ClientAuthenticationMethodBinding {

    @JsonProperty("RCID")
    private Long registeredClientId;

    @JsonProperty("CMID")
    private Long clientAuthenticationMethodId;

}
