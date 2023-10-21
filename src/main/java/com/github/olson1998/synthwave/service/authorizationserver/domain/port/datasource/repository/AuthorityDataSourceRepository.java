package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.Authority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

public interface AuthorityDataSourceRepository {

    Collection<SimpleGrantedAuthority> getSimpleGrantedAuthoritiesAndAllChildAuthoritiesByParentValue(String authority);

    void saveAll(Collection<Authority> authorityCollection);
}
