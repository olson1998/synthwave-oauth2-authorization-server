package com.github.olson1998.synthwave.service.authorizationserver.domain.model.authoritity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.authority.stereotype.UserAuthorities;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.authoritiy.Authority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthoritiesModel implements UserAuthorities {

    @JsonProperty(value = "USID", required = true)
    private Long userId;

    @JsonProperty(value = "AUTHDATA")
    private List<AuthorityModel> authorityModelList;

    @Override
    public Collection<? extends Authority> getAuthorities() {
        return authorityModelList;
    }
}
