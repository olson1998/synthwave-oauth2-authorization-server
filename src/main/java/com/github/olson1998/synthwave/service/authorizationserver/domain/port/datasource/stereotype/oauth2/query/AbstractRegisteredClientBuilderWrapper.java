package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.query;

import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

public abstract class AbstractRegisteredClientBuilderWrapper extends RegisteredClient.Builder {

    public abstract Long getId();

    protected AbstractRegisteredClientBuilderWrapper(String id) {
        super(id);
    }
}
