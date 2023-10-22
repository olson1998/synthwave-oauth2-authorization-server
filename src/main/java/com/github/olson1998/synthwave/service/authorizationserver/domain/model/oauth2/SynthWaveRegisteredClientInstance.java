package com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.SynthWaveRegisteredClient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Instant;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
@RequiredArgsConstructor
public class SynthWaveRegisteredClientInstance extends SynthWaveRegisteredClient {

    private final String companyCode;

    private final String division;

    private final RegisteredClient registeredClient;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SynthWaveRegisteredClientInstance that = (SynthWaveRegisteredClientInstance) o;
        return companyCode.equals(that.companyCode) && division.equals(that.division) && registeredClient.equals(that.registeredClient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), companyCode, division, registeredClient);
    }
}
