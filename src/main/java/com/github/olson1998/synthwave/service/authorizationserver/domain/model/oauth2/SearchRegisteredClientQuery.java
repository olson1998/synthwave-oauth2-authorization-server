package com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.query.SearchRegisteredClient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.joda.time.MutableDateTime;

@Data
@Builder(builderClassName = "Builder")
@AllArgsConstructor
public class SearchRegisteredClientQuery implements SearchRegisteredClient {

    private final Long registeredClientId;

    private final String clientIdPattern;

    private final String namePattern;

    private final MutableDateTime timestamp;

    private final boolean filterExpired;

    private final boolean filterNonActive;

    private final int pageSize;

    private final int page;

}
