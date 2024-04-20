package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.RedirectUri;
import org.joda.time.MutableDateTime;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;

public interface RedirectRepository {

    Collection<? extends RedirectUri> getRedirectUriByExample(Collection<? extends RedirectUri> redirectUriExamples);

    Collection<? extends RedirectUri> getPostLogoutRedirectUriByExample(Collection<? extends RedirectUri> redirectUriExamples);

    Collection<String> getPostLogoutRedirectUriByRegisteredClientId(Long registeredClientId);

    Collection<String> getRedirectUriByRegisteredClientIdWithTimestamp(Long registeredClientId, MutableDateTime timestamp);

    @Transactional(rollbackFor = Exception.class)
    Collection<? extends RedirectUri> saveAllRedirectUri(Collection<? extends RedirectUri> redirectUriCollection);

    @Transactional(rollbackFor = Exception.class)
    Collection<? extends RedirectUri> saveAllPostLogoutRedirectUri(Collection<? extends RedirectUri> redirectUriCollection);

    @Transactional(rollbackFor = Exception.class)
    void deleteRedirectUri(String query);

    @Transactional(rollbackFor = Exception.class)
    void deletePostLogoutRedirectUri(String query);

    @Transactional(rollbackFor = Exception.class)
    Collection<? extends RedirectUri> saveAllRedirect(Collection<? extends RedirectUri> redirectUriCollection);

    @Transactional(rollbackFor = Exception.class)
    Collection<? extends RedirectUri> saveAllPostLogoutRedirect(Collection<? extends RedirectUri> redirectUriCollection);

    @Transactional(rollbackFor = Exception.class)
    void saveAllRedirectBounds(Collection<? extends RedirectUri> redirectUriCollection, Long registeredClientId);

    @Transactional(rollbackFor = Exception.class)
    void saveAllPostLogoutRedirectBounds(Collection<? extends RedirectUri> redirectUriCollection, Long registeredClientId);

}
