package com.github.olson1998.synthwave.service.authorizationserver.domain.model.authoritity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.authoritiy.AuthorityBinding;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityBindingModel implements AuthorityBinding {

    @JsonProperty(value = "USID", required = true)
    private Long userId;

    @JsonProperty(value = "AUID", required = true)
    private Long authorityId;
}
