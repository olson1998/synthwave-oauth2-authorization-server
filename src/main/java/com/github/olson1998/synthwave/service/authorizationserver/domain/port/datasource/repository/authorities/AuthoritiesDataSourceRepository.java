package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.authorities;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.authoritiy.GrantedAuthority;

import java.util.Collection;

public interface AuthoritiesDataSourceRepository {

    Collection<? extends GrantedAuthority> getAllByExamples(Collection<? extends GrantedAuthority> examples);

    void saveAll(Collection<? extends GrantedAuthority> grantedAuthoritiesCollection);
}
