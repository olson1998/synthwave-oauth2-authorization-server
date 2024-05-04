package com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private MutableDateTime activeFrom;

    @JsonProperty(value = "ETMP")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private MutableDateTime expireOn;

    public RegisteredClientPropertiesModel(RegisteredClientProperties registeredClientProperties) {
        this.id = registeredClientProperties.getId();
        this.clientId = registeredClientProperties.getClientId();
        this.name = registeredClientProperties.getName();
        this.createdOn = registeredClientProperties.getCreatedOn();
        this.activeFrom = registeredClientProperties.getActiveFrom();
        this.expireOn = registeredClientProperties.getExpireOn();
    }
}
