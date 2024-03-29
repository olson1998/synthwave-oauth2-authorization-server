package com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.RegisteredClientSecret;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.MutableDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisteredClientSecretModel implements RegisteredClientSecret {

    @JsonProperty(value = "ID")
    private Long id;

    @JsonProperty(value = "RCID")
    private Long registeredClientId;

    @JsonProperty(value = "VAL", required = true)
    private String value;

    @JsonProperty(value = "CTMP")
    private MutableDateTime createdOn;

    @JsonProperty(value = "ETMP")
    private MutableDateTime expireOn;
}
