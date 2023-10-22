package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype;

import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

public abstract class SynthWaveRegisteredClient extends RegisteredClient {

    public abstract String getCompanyCode();

    public abstract String getDivision();
}
