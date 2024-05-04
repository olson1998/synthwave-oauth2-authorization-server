package com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.*;
import com.github.olson1998.synthwave.support.joda.converter.JavaInstantConverter;
import com.github.olson1998.synthwave.support.joda.converter.MutableDateTimeConverter;
import lombok.*;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RegisteredClientModel extends RegisteredClient {

    @JsonProperty(value = "RCL", required = true)
    private RegisteredClientPropertiesModel propertiesModel;

    @JsonProperty(value = "RCLS", required = true)
    private RegisteredClientSecretModel secretModel;

    @JsonProperty(value = "SCP", required = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<ScopeModel> scopeModels;

    @JsonProperty(value = "RURI", required = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<RedirectUriModel> redirectUriModels;

    @JsonProperty(value = "LURI", required = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<RedirectUriModel> postLogoutRedirectUriModels;

    @JsonProperty(value = "AGT", required = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<AuthorizationGrantTypeEntityModel> authorizationGrantTypesSet;

    @JsonProperty(value = "CAM", required = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<ClientAuthenticationMethodEntityModel> clientAuthenticationMethodsSet;

    @JsonProperty(value = "CLST" ,required = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ClientSettingsEntityModel clientSettingsModel;

    @JsonProperty(value = "TSST", required = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private TokenSettingsEntityModel tokenSettingsModel;

    public RegisteredClientModel(RegisteredClient registeredClient) {
        this.propertiesModel = RegisteredClientPropertiesModel.builder()
                .id(Long.parseLong(registeredClient.getId()))
                .clientId(registeredClient.getClientId())
                .name(registeredClient.getClientName())
                .createdOn(new JavaInstantConverter(registeredClient.getClientIdIssuedAt()).toMutableDateTime())
                .build();
        this.secretModel = RegisteredClientSecretModel.builder()
                .value(registeredClient.getClientSecret())
                .expireOn(new JavaInstantConverter(registeredClient.getClientSecretExpiresAt()).toMutableDateTime())
                .build();
        this.scopeModels = Objects.requireNonNullElseGet(registeredClient.getScopes(), ()-> new HashSet<String>())
                .stream()
                .map(ScopeModel::new)
                .collect(Collectors.toSet());
        this.redirectUriModels = Objects.requireNonNullElseGet(registeredClient.getRedirectUris(), ()-> new HashSet<String>())
                .stream()
                .map(RedirectUriModel::new)
                .collect(Collectors.toSet());
        this.postLogoutRedirectUriModels = Objects.requireNonNullElseGet(registeredClient.getPostLogoutRedirectUris(), ()-> new HashSet<String>())
                .stream()
                .map(RedirectUriModel::new)
                .collect(Collectors.toSet());
        this.authorizationGrantTypesSet = Objects.requireNonNullElseGet(registeredClient.getAuthorizationGrantTypes(), ()-> new HashSet<AuthorizationGrantType>())
                .stream()
                .map(AuthorizationGrantTypeEntityModel::new)
                .collect(Collectors.toSet());
        this.clientAuthenticationMethodsSet = Objects.requireNonNullElseGet(registeredClient.getClientAuthenticationMethods(), ()-> new HashSet<ClientAuthenticationMethod>())
                .stream()
                .map(ClientAuthenticationMethodEntityModel::new)
                .collect(Collectors.toSet());
        this.clientSettingsModel = Optional.ofNullable(registeredClient.getClientSettings())
                .map(ClientSettingsEntityModel::new)
                .orElse(null);
        this.tokenSettingsModel = Optional.ofNullable(registeredClient.getTokenSettings())
                .map(TokenSettingsEntityModel::new)
                .orElse(null);
    }

    @Override
    public Instant getClientIdIssuedAt() {
        return Optional.ofNullable(propertiesModel.getCreatedOn())
                .map(MutableDateTimeConverter::new)
                .map(MutableDateTimeConverter::toJavaInstant)
                .orElse(null);
    }

    @Override
    public Instant getClientSecretExpiresAt() {
        return Optional.ofNullable(secretModel.getExpireOn())
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
        return Optional.ofNullable(propertiesModel)
                .map(RegisteredClientPropertiesModel::getId)
                .map(String::valueOf)
                .orElse(null);
    }

    @Override
    public String getClientId() {
        return Optional.ofNullable(propertiesModel)
                .map(RegisteredClientPropertiesModel::getClientId)
                .orElse(null);
    }

    @Override
    public String getClientName() {
        return Optional.ofNullable(propertiesModel)
                .map(RegisteredClientPropertiesModel::getName)
                .orElse(null);
    }
}
