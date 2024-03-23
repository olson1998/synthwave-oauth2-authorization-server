package com.github.olson1998.synthwave.service.authorizationserver.domain.port.user.stereotype;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.authoritiy.Authority;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.user.ApplicationUser;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.user.UserPassword;

import java.util.Collection;

public interface ApplicationUserDetails {

    ApplicationUser getApplicationUser();

    UserPassword getPassword();

    Collection<? extends Authority> getAuthorities();
}
