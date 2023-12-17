package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype;

import com.github.olson1998.synthwave.support.web.util.URIModel;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.Set;

public abstract class AbstractSynthWaveRegisteredClient extends RegisteredClient implements SynthWaveUserProperties {

    @Override
    public abstract String getCompanyCode();

    @Override
    public abstract String getDivision();

    public abstract Password getUserPassword();

    public abstract ClientSecret getClientSecretObject();

    public abstract Set<URIModel> getRedirectUriModels();

    public abstract Set<URIModel> getPostLogoutRedirectModels();

}
