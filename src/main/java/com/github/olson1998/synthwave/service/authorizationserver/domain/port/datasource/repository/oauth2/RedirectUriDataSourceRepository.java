package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.RedirectUri;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.UriBinding;
import org.joda.time.MutableDateTime;

import java.util.Collection;
import java.util.Set;

public interface RedirectUriDataSourceRepository {

    Collection<? extends RedirectUri> getRedirectUriByExamples(Collection<? extends RedirectUri> redirectUriExamples);

    Collection<? extends RedirectUri> getPostLogoutRedirectUriByExamples(Collection<? extends RedirectUri> redirectUriExamples);

    Set<String> getPostLogoutRedirectUriByRegisteredClientId(Long registeredClientId);

    Set<String> getRedirectUriByRegisteredClientIdWithTimestamp(Long registeredClientId, MutableDateTime timestamp);

    Collection<? extends RedirectUri> saveAll(Collection<? extends RedirectUri> redirectUriCollection);

    Collection<? extends RedirectUri> saveAllPostLogout(Collection<? extends RedirectUri> postLogoutRedirectUriCollection);

    void saveAllRedirectBounds(Collection<? extends UriBinding> redirectUriCollection);

    void saveAllPostLogoutRedirectBounds(Collection<? extends UriBinding> postLogoutRedirectUriCollection);

    int deleteRedirectUriBoundsById(Long redirectId);

    int deletePostLogoutRedirectUriBoundsById(Long redirectId);

    int deleteRedirectBoundsByIdAndRegisteredClientId(Collection<Long> idCollection, Long registeredClientId);

    int deletePostLogoutRedirectBoundsByIdAndRegisteredClientId(Collection<Long> idCollection, Long registeredClientId);

}
