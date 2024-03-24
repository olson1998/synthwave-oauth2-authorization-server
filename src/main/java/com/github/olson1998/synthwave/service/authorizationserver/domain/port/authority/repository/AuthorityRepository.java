package com.github.olson1998.synthwave.service.authorizationserver.domain.port.authority.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.authority.stereotype.UserAuthorities;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.authoritiy.Authority;
import org.joda.time.MutableDateTime;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface AuthorityRepository {

    Collection<? extends Authority> getAuthoritiesByUserIdAndTimestamp(Long userId, MutableDateTime timestamp);

    String[] getActiveAuthoritiesNamesByUserId(Long userId);

    @Transactional(rollbackFor = Exception.class)
    Collection<? extends Authority> saveAll(Collection<Authority> authorityCollection);

    @Transactional(rollbackFor = Exception.class)
    void saveUserAuthorities(Long userId, Collection<? extends Authority> authorityCollection);

}
