package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.user.query;

import org.joda.time.MutableDateTime;

import java.util.Collection;
import java.util.Set;

public interface ApplicationUserDetailsSearchQueryResult {

    String getUsername();

    String getPassword();

    Boolean getEnabled();

    MutableDateTime getExpireOn();

    Set<String> getAuthorities();

    void addAuthorities(Collection<String> authorities);
}
