package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.authority;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.authoritiy.Authority;
import org.joda.time.MutableDateTime;

import java.util.Collection;

public interface AuthorityDataSourceRepository {

    Collection<? extends Authority> getAllAuthorities();

    Collection<Long> getIdByAuthorityIdCollection(Collection<Long> authorityIdCollection);

    Collection<? extends Authority> getAuthoritiesByUserIdAndTimestamp(Long userId, MutableDateTime timestamp);

    String[] getActiveAuthoritiesNameByUserId(Long userId);

    Collection<? extends Authority> getAuthoritiesByUserId(Long userId);

    Collection<Long> getAuthoritiesIdsByExamples(Collection<? extends Authority> authorityExamplesCollection);

    Collection<? extends Authority> saveAll(Collection<? extends Authority> authoritiesCollection);
}
