package com.github.olson1998.synthwave.service.authorizationserver.domain.model.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.authoritity.AuthorityModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.role.RoleModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.authoritiy.Authority;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.role.Role;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.user.ApplicationUser;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.user.UserPassword;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.user.stereotype.ApplicationUserDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationUserDetailsModel implements ApplicationUserDetails {

    @JsonProperty(value = "USER", required = true)
    private ApplicationUserModel applicationUserModel;

    @JsonProperty(value = "PASS")
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private UserPasswordModel userPasswordModel;

    @JsonProperty(value = "AUTH")
    private List<AuthorityModel> authorityModelList;

    @JsonProperty(value = "ROLE")
    private List<RoleModel> roleModelsList;

    @Override
    public ApplicationUser getApplicationUser() {
        return applicationUserModel;
    }

    @Override
    public UserPassword getPassword() {
        return userPasswordModel;
    }

    @Override
    public Collection<? extends Authority> getAuthorities() {
        return authorityModelList;
    }

    @Override
    public Collection<? extends Role> getRoles() {
        return roleModelsList;
    }
}
