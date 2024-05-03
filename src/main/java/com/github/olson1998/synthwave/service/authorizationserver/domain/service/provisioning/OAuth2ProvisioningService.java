package com.github.olson1998.synthwave.service.authorizationserver.domain.service.provisioning;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.AuthorizationGrantTypeEntityModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.ClientAuthenticationMethodEntityModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.ScopeModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.AuthorizationGrantTypeDatasourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.ClientAuthenticationMethodDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.RegisteredClientSecretDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.ScopeDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.RedirectUri;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.OAuth2RegisteredClientRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.RedirectRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.provisioning.repository.Provisioner;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.provisioning.repository.ProvisionerSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.MutableDateTime;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public class OAuth2ProvisioningService implements Provisioner {

    private static final Set<String> REQUIRED_SCOPES = Set.of(
            OidcScopes.OPENID,
            OidcScopes.PROFILE,
            OidcScopes.PHONE,
            OidcScopes.ADDRESS,
            OidcScopes.EMAIL
    );

    private static final Set<AuthorizationGrantType> REQUIRED_AUTHORIZATION_GRANT_TYPES = Set.of(
            AuthorizationGrantType.CLIENT_CREDENTIALS,
            AuthorizationGrantType.JWT_BEARER,
            AuthorizationGrantType.AUTHORIZATION_CODE,
            AuthorizationGrantType.DEVICE_CODE,
            AuthorizationGrantType.REFRESH_TOKEN
    );

    private static final Set<ClientAuthenticationMethod> REQUIRED_CLIENT_AUTHENTICATION_METHODS = Set.of(
            ClientAuthenticationMethod.CLIENT_SECRET_BASIC,
            ClientAuthenticationMethod.NONE,
            ClientAuthenticationMethod.CLIENT_SECRET_JWT,
            ClientAuthenticationMethod.PRIVATE_KEY_JWT,
            ClientAuthenticationMethod.CLIENT_SECRET_POST
    );

    private final ProvisionerSource<Collection<RedirectUri>> redirectUriProvisionerSource;

    private final ProvisionerSource<Collection<RedirectUri>> postLogoutRedirectUriProvisionerSource;

    private final ProvisionerSource<Collection<RegisteredClient>> registeredClientProvisionerSource;

    private final OAuth2RegisteredClientRepository oAuth2RegisteredClientRepository;

    private final RedirectRepository redirectRepository;

    private final RegisteredClientSecretDataSourceRepository registeredClientSecretDataSourceRepository;

    private final ScopeDataSourceRepository scopeDataSourceRepository;

    private final AuthorizationGrantTypeDatasourceRepository authorizationGrantTypeDatasourceRepository;

    private final ClientAuthenticationMethodDataSourceRepository clientAuthenticationMethodDataSourceRepository;

    @Override
    public void provision() {
        provisionRequiredScopes();
        provisionRequiredAuthorizationGrantTypes();
        provisionRequiredClientAuthenticationMethods();
        provisionRedirectUri();
        provisionPostLogoutRedirectUri();
        provisionRegisteredClients();
    }

    private void provisionRequiredScopes() {
        var existingScopes = scopeDataSourceRepository.getScopeByNames(REQUIRED_SCOPES);
        log.debug("Existing scopes: {}", existingScopes);
        var provisionScopes = REQUIRED_SCOPES.stream()
                .filter(scopeName -> existingScopes.stream().noneMatch(scope -> scope.getName().equals(scopeName)))
                .map(scope -> new ScopeModel(null, scope, MutableDateTime.now()))
                .toList();
        if(!provisionScopes.isEmpty()) {
            var provisionedScopes = scopeDataSourceRepository.saveAll(provisionScopes);
            log.debug("Provisioned scopes: {}", provisionedScopes);
        }
    }

    private void provisionRequiredAuthorizationGrantTypes() {
        var existingTypes =
                authorizationGrantTypeDatasourceRepository.getAuthorizationGrantTypeByType(REQUIRED_AUTHORIZATION_GRANT_TYPES);
        log.debug("Existing authorization grant types: {}", existingTypes);
        var provisionTypes = REQUIRED_AUTHORIZATION_GRANT_TYPES.stream()
                .filter(authorizationGrantType -> existingTypes.stream().noneMatch(authorizationGrantTypeEntity -> authorizationGrantTypeEntity.getGrantType().getValue().equals(authorizationGrantType.getValue())))
                .map(authorizationGrantType -> new AuthorizationGrantTypeEntityModel(null, authorizationGrantType, null))
                .toList();
        if(!provisionTypes.isEmpty()) {
            var provisionedTypes = authorizationGrantTypeDatasourceRepository.saveAll(provisionTypes);
            log.debug("Provisioned types: {}", provisionedTypes);
        }
    }

    private void provisionRequiredClientAuthenticationMethods() {
        var existingMethods =
                clientAuthenticationMethodDataSourceRepository.getClientAuthenticationMethodsByMethod(REQUIRED_CLIENT_AUTHENTICATION_METHODS);
        log.debug("Existing client authentication methods: {}", existingMethods);
        var provisionMethods = REQUIRED_CLIENT_AUTHENTICATION_METHODS.stream()
                .filter(clientAuthenticationMethod -> existingMethods.stream().noneMatch(entity -> entity.getMethod().getValue().equals(clientAuthenticationMethod.getValue())))
                .map(clientAuthenticationMethod -> new ClientAuthenticationMethodEntityModel(null, clientAuthenticationMethod, null))
                .toList();
        if(!provisionMethods.isEmpty()) {
            var provisionedMethods = clientAuthenticationMethodDataSourceRepository.saveAll(provisionMethods);
            log.debug("Provisioned client authentication methods: {}", provisionedMethods);
        }
    }

    private void provisionRedirectUri() {
        var redirectUriCollection = redirectUriProvisionerSource.getEntity();
        if(!redirectUriCollection.isEmpty()) {
            var existingRedirectUri = Optional.ofNullable(redirectRepository.getRedirectUriByExample(redirectUriCollection))
                    .orElseGet(Collections::emptyList);
            log.debug("Existing redirect uri: {}", existingRedirectUri);
            var provisionRedirect = redirectUriCollection.stream()
                    .filter(redirectUri -> existingRedirectUri.stream().noneMatch(uri -> redirectUri.getValue().equals(uri.getValue())))
                    .toList();
            if(!provisionRedirect.isEmpty()) {
                var provisionedRedirect = redirectRepository.saveAllRedirect(provisionRedirect);
                log.debug("Provisioned redirect uri: {}", provisionedRedirect);
            }
        }
    }

    private void provisionPostLogoutRedirectUri() {
        var redirectUriCollection = postLogoutRedirectUriProvisionerSource.getEntity();
        if(!redirectUriCollection.isEmpty()) {
            var existingRedirectUri = Optional.ofNullable(redirectRepository.getPostLogoutRedirectUriByExample(redirectUriCollection)).
                    orElseGet(Collections::emptyList);
            log.debug("Existing post logout redirect uri: {}", existingRedirectUri);
            var provisionRedirect = redirectUriCollection.stream()
                    .filter(redirectUri -> existingRedirectUri.stream().noneMatch(uri -> redirectUri.getValue().equals(uri.getValue())))
                    .toList();
            if(!provisionRedirect.isEmpty()) {
                var provisionedRedirect = redirectRepository.saveAllPostLogoutRedirect(provisionRedirect);
                log.debug("Provisioned post logout redirect uri: {}", provisionedRedirect);
            }
        }
    }

    private void provisionRegisteredClients() {
        var registeredClients = Optional.ofNullable(registeredClientProvisionerSource.getEntity()).orElseGet(Collections::emptyList);
        registeredClients.forEach(registeredClient -> {
            if(!registeredClientSecretDataSourceRepository.existsRegisteredClientId(Long.parseLong(registeredClient.getId()))) {
                oAuth2RegisteredClientRepository.save(registeredClient);
            }
        });
    }

}
