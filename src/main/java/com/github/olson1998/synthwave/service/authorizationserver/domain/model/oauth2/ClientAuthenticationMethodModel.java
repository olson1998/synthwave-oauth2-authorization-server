package com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.ClientAuthenticationMethodEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.MutableDateTime;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientAuthenticationMethodModel implements ClientAuthenticationMethodEntity {

    @JsonProperty(value = "ID")
    private Long id;

    @JsonProperty(value = "VAL")
    private ClientAuthenticationMethod method;

    @JsonProperty(value = "CTMP")
    private MutableDateTime createdOn;
}
