package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.authority;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.authoritiy.Authority;

import java.util.Collection;

public interface AuthorityDataSourceRepository {

    Collection<? extends Authority> getAuthoritiesByIds(Collection<Long> authoritiesIdsCollection);

    Collection<Long> getAuthoritiesIdsByExamples(Collection<? extends Authority> authorityExamplesCollection);

    Collection<? extends Authority> saveAll(Collection<? extends Authority> authoritiesCollection);
}
