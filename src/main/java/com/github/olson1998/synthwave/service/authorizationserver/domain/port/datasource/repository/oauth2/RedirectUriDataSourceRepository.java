package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.RedirectUri;
import org.joda.time.MutableDateTime;

import java.util.Collection;
import java.util.Set;

public interface RedirectUriDataSourceRepository {

    Collection<? extends RedirectUri> getRedirectUriByExamples(Collection<? extends RedirectUri> redirectUriExamples);

    Collection<? extends RedirectUri> getPostLogoutRedirectUriByExamples(Collection<? extends RedirectUri> redirectUriExamples);

    Set<String> getPostLogoutRedirectUriByRegisteredClientId(Long registeredClientId);

    Set<String> getRedirectUriByRegisteredClientIdWithTimestamp(Long registeredClientId, MutableDateTime timestamp);
}
