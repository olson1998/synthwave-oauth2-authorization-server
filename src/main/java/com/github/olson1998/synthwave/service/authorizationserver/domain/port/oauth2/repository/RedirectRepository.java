package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.RedirectUri;
import org.joda.time.MutableDateTime;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;

public interface RedirectRepository {

    Set<String> getPostLogoutRedirectUriByRegisteredClientId(Long registeredClientId);

    Set<String> getRedirectUriByRegisteredClientIdWithTimestamp(Long registeredClientId, MutableDateTime timestamp);

    @Transactional(rollbackFor = Exception.class)
    Collection<? extends RedirectUri> saveAllRedirectUri(Collection<? extends RedirectUri> redirectUriCollection);

    @Transactional(rollbackFor = Exception.class)
    Collection<? extends RedirectUri> saveAllPostLogoutRedirectUri(Collection<? extends RedirectUri> redirectUriCollection);

    void saveAllRedirectBounds(Collection<? extends RedirectUri> redirectUriCollection, Long registeredClientId);

    void saveAllPostLogoutRedirectBounds(Collection<? extends RedirectUri> redirectUriCollection, Long registeredClientId);

}
