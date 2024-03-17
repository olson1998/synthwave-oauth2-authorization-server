package com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.*;
import com.github.olson1998.synthwave.support.joda.converter.MutableDateTimeConverter;
import lombok.*;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RegisteredClientModel extends RegisteredClient {

    @JsonProperty(value = "RCL", required = true)
    private RegisteredClientPropertiesModel propertiesModel;

    @JsonProperty(value = "RCS", required = true)
    private RegisteredClientSecretModel secretModel;

    @JsonProperty(value = "SCP", required = true)
    private Set<ScopeModel> scopeModels;

    @JsonProperty(value = "RU", required = true)
    private Set<RedirectUriModel> redirectUriModels;

    @JsonProperty(value = "LRU", required = true)
    private Set<RedirectUriModel> postLogoutRedirectUriModels;

    @JsonProperty(value = "AGT", required = true)
    private Set<AuthorizationGrantTypeEntityModel> authorizationGrantTypesSet;

    @JsonProperty(value = "CAM", required = true)
    private Set<ClientAuthenticationMethodEntityModel> clientAuthenticationMethodsSet;

    @JsonProperty(value = "CSS" ,required = true)
    private ClientSettingsEntityModel clientSettingsModel;

    @JsonProperty(value = "TSS", required = true)
    private TokenSettingsEntityModel tokenSettingsModel;

    @Override
    public Instant getClientIdIssuedAt() {
        return Optional.ofNullable(properties.getCreatedOn())
                .map(MutableDateTimeConverter::new)
                .map(MutableDateTimeConverter::toJavaInstant)
                .orElse(null);
    }

    @Override
    public Instant getClientSecretExpiresAt() {
        return Optional.ofNullable(secret.getExpireOn())
                .map(MutableDateTimeConverter::new)
                .map(MutableDateTimeConverter::toJavaInstant)
                .orElse(null);
    }

    @Override
    public Set<String> getRedirectUris() {
        return redirectUriModels.stream()
                .map(RedirectUri::getValue)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Set<String> getPostLogoutRedirectUris() {
        return postLogoutRedirectUriModels.stream()
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

    @Override
    public String getId() {
        return Optional.ofNullable(properties.getId())
                .map(String::valueOf)
                .orElse(null);
    }

    @Override
    public String getClientId() {
        return properties.getClientId();
    }

    @Override
    public String getClientName() {
        return properties.getName();
    }
}
