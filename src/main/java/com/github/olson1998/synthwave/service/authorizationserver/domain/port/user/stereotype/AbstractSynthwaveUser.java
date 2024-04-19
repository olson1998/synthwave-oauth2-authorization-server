package com.github.olson1998.synthwave.service.authorizationserver.domain.port.user.stereotype;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public abstract class AbstractSynthwaveUser extends User {

    public AbstractSynthwaveUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public AbstractSynthwaveUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public abstract Long getUserId();

    public abstract String getCompanyCode();

    public abstract String getDivision();

}
