package com.github.olson1998.synthwave.authorizationserver.domain.port.oauth2.stereotype;

import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

public interface SynthWaveClient {

    RegisteredClient buildRegisteredClient();
}
