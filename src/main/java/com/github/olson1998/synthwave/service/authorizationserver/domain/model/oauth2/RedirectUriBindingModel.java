package com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.UriBinding;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.MutableDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedirectUriBindingModel implements UriBinding {

    @JsonProperty(value = "RCID", required = true)
    private Long registeredClientId;

    @JsonProperty(value = "ID", required = true)
    private Long uriId;

    @JsonProperty(value = "CTMP")
    private MutableDateTime createdOn;

    public RedirectUriBindingModel(Long registeredClientId, Long uriId) {
        this.registeredClientId = registeredClientId;
        this.uriId = uriId;
    }
}
