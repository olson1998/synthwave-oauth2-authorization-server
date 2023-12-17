package com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.AbstractSynthWaveRegisteredClient;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.ClientSecret;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Password;
import com.github.olson1998.synthwave.support.web.util.URIModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.joda.time.MutableDateTime;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.net.URI;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@ToString(exclude = {"clientSecretObject", "userPassword"})
@EqualsAndHashCode(callSuper = true, exclude = {"clientSecretObject","userPassword"})
@RequiredArgsConstructor
public class SynthWaveRegisteredClient extends AbstractSynthWaveRegisteredClient {

    public static final String REGISTERED_CLIENT_COMPANY_CODE_JSON_PROPERTY ="company_code";

    public static final String REGISTERED_CLIENT_DIVISION_JSON_PROPERTY ="division";

    public static final String REGISTERED_CLIENT_USER_PASSWORD_JSON_PROPERTY ="password";

    @Getter
    private final String companyCode;

    @Getter
    private final String division;

    @Getter
    private final Password userPassword;

    private final RegisteredClient registeredClient;

    @Getter
    private final ClientSecret clientSecretObject;

    @Getter
    private final Set<URIModel> redirectUriModels;

    @Getter
    private final Set<URIModel> postLogoutRedirectModels;

    @Override
    public String getId() {
        return registeredClient.getId();
    }

    @Override
    public String getClientId() {
        return registeredClient.getClientId();
    }

    @Override
    public Instant getClientIdIssuedAt() {
        return registeredClient.getClientIdIssuedAt();
    }

    @Override
    public String getClientSecret() {
        return registeredClient.getClientSecret();
    }

    @Override
    public Instant getClientSecretExpiresAt() {
        return registeredClient.getClientSecretExpiresAt();
    }

    @Override
    public String getClientName() {
        return registeredClient.getClientName();
    }

    @Override
    public Set<ClientAuthenticationMethod> getClientAuthenticationMethods() {
        return registeredClient.getClientAuthenticationMethods();
    }

    @Override
    public Set<AuthorizationGrantType> getAuthorizationGrantTypes() {
        return registeredClient.getAuthorizationGrantTypes();
    }

    @Override
    public Set<String> getRedirectUris() {
        return registeredClient.getRedirectUris();
    }

    @Override
    public Set<String> getPostLogoutRedirectUris() {
        return registeredClient.getPostLogoutRedirectUris();
    }

    @Override
    public Set<String> getScopes() {
        return registeredClient.getScopes();
    }

    @Override
    public ClientSettings getClientSettings() {
        return registeredClient.getClientSettings();
    }

    @Override
    public TokenSettings getTokenSettings() {
        return registeredClient.getTokenSettings();
    }

    @Override
    public String getUsername() {
        return getClientName();
    }

    @Override
    public Boolean getEnabled() {
        return null;
    }

    @Override
    public MutableDateTime getExpireDateTime() {
        return null;
    }

    private void fillVariables(URIModel uriModel){
        var path = uriModel.getPath();
        path.customize(pathVariables -> {
            if(pathVariables.containsVariable("${clientId}")){
                pathVariables.setValue("${clientId}", registeredClient.getClientId());
            }
        });
    }
}
