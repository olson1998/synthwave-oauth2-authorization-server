package com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.RedirectUri;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.MutableDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedirectUriModel implements RedirectUri {

    @JsonProperty(value = "ID")
    private Long id;

    @JsonProperty(value = "URI", required = true)
    private String value;

    @JsonProperty(value = "CTMP")
    private MutableDateTime createdOn;

    @JsonProperty(value = "ETMP")
    private MutableDateTime expireOn;

    public RedirectUriModel(RedirectUri redirectUri) {
        this.id = redirectUri.getId();
        this.value = redirectUri.getValue();
        this.createdOn = redirectUri.getCreatedOn();
        this.expireOn = redirectUri.getExpireOn();
    }

    public RedirectUriModel(String value) {
        this.value = value;
    }
}
