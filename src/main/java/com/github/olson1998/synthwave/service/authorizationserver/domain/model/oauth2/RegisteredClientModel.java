package com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.*;
import com.github.olson1998.synthwave.support.joda.converter.MutableDateTimeConverter;
import lombok.*;
import org.joda.time.MutableDateTime;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RegisteredClientModel extends RegisteredClient {

    @JsonProperty(value = "ID", required = true)
    private Long id;

    @JsonProperty(value = "CID", required = true)
    private String clientId;

    @JsonProperty(value = "NAME", required = true)
    private String clientName;

    @JsonProperty(value = "RCS", required = true)
    private RegisteredClientSecret clientSecret;

    @JsonProperty(value = "CTMP")
    private MutableDateTime createdOn;

    @JsonProperty(value = "ETMP")
    private MutableDateTime expireOn;

    @JsonProperty(value = "ATMP")
    private MutableDateTime activeFrom;

    @JsonProperty(value = "SCP", required = true)
    private Set<Scope> scopes;

    @JsonProperty(value = "RU", required = true)
    private Set<RedirectUri> redirectUriSet;

    @JsonProperty(value = "LRU", required = true)
    private Set<RedirectUri> postLogoutRedirectUriSet;

    @JsonProperty(value = "AGT", required = true)
    private Set<AuthorizationGrantTypeEntity> authorizationGrantTypesSet;

    @JsonProperty(value = "CAM", required = true)
    private Set<ClientAuthenticationMethodEntity> clientAuthenticationMethodsSet;

    @JsonProperty(value = "CSS" ,required = true)
    private ClientSettingsEntity clientSettingsModel;

    @JsonProperty(value = "TSS", required = true)
    private TokenSettingsEntity tokenSettingsModel;

    @Override
    public Instant getClientIdIssuedAt() {
        return new MutableDateTimeConverter(createdOn).toJavaInstant();
    }

    @Override
    public Instant getClientSecretExpiresAt() {
        return new MutableDateTimeConverter(clientSecret.getExpireOn()).toJavaInstant();
    }

    @Override
    public Set<String> getRedirectUris() {
        return redirectUriSet.stream()
                .map(RedirectUri::getValue)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Set<String> getPostLogoutRedirectUris() {
        return postLogoutRedirectUriSet.stream()
                .map(RedirectUri::getValue)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Set<ClientAuthenticationMethod> getClientAuthenticationMethods() {
        return super.getClientAuthenticationMethods();
    }

    @Override
    public Set<AuthorizationGrantType> getAuthorizationGrantTypes() {
        return super.getAuthorizationGrantTypes();
    }

    @Override
    public ClientSettings getClientSettings() {
        return clientSettingsModel.toSettings();
    }

    @Override
    public TokenSettings getTokenSettings() {
        return tokenSettingsModel.toSettings();
    }
}
