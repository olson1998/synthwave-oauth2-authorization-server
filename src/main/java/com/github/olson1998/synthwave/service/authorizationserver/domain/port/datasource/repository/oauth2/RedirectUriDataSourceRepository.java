package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2;

import org.joda.time.MutableDateTime;

import java.util.Set;

public interface RedirectUriDataSourceRepository {

    Set<String> getRedirectUriByRegisteredClientIdWithTimestamp(Long registeredClientId, MutableDateTime timestamp);
}
