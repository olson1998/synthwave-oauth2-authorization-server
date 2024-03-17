package com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.RegisteredClientProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.MutableDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisteredClientPropertiesModel implements RegisteredClientProperties {

    @JsonProperty(value = "ID", required = true)
    private Long id;

    @JsonProperty(value = "CID", required = true)
    private String clientId;

    @JsonProperty(value = "NAME", required = true)
    private String name;

    @JsonProperty(value = "CTMP")
    private MutableDateTime createdOn;

    @JsonProperty(value = "ATMP")
    private MutableDateTime activeFrom;

    @JsonProperty(value = "ETMP")
    private MutableDateTime expireOn;
}
