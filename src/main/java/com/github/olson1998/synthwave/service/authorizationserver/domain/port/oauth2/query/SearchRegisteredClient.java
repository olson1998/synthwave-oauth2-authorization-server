package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.query;

import org.joda.time.MutableDateTime;

public interface SearchRegisteredClient  {

    Long getRegisteredClientId();

    String getClientIdPattern();

    String getNamePattern();

    MutableDateTime getTimestamp();

    boolean isFilterExpired();

    boolean isFilterNonActive();

    int getPageSize();

    int getPage();
}
