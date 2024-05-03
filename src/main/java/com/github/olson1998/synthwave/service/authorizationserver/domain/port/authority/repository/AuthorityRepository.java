package com.github.olson1998.synthwave.service.authorizationserver.domain.port.authority.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.authority.stereotype.UserAuthorities;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.authoritiy.Authority;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.rest.stereotype.DeleteAuthorityResponse;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.rest.stereotype.DeleteUserAuthorityBindingResponse;
import org.joda.time.MutableDateTime;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface AuthorityRepository {

    Collection<? extends Authority> getAllAuthorities();

    Collection<? extends Authority> getAuthoritiesByUserIdAndTimestamp(Long userId, MutableDateTime timestamp);

    String[] getActiveAuthoritiesNamesByUserId(Long userId);

    @Transactional(rollbackFor = Exception.class)
    Collection<? extends Authority> saveAll(Collection<Authority> authorityCollection);

    @Transactional(rollbackFor = Exception.class)
    void saveUserAuthorities(UserAuthorities userAuthorities);

    @Transactional(rollbackFor = Exception.class)
    DeleteAuthorityResponse deleteAuthorities(Collection<Long> idCollection);

    @Transactional(rollbackFor = Exception.class)
    DeleteUserAuthorityBindingResponse deleteUserAuthoritiesBounds(Long userId, Collection<Long> idCollection);

}
