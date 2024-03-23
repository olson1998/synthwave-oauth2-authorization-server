package com.github.olson1998.synthwave.service.authorizationserver.domain.port.authority.stereotype;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.authoritiy.Authority;

import java.util.Collection;

public interface UserAuthorities {

    Long getUserId();

    Collection<? extends Authority> getAuthorities();

}
