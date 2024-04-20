package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.*;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.*;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.ClientAuthenticationMethodEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.Scope;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.OAuth2RegisteredClientRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.RedirectRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.ScopeRepository;
import lombok.RequiredArgsConstructor;
import org.joda.time.MutableDateTime;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class OAuth2RegisteredClientService implements OAuth2RegisteredClientRepository {

    private final Executor executor;

    private final PasswordEncoder passwordEncoder;

    private final RedirectRepository redirectRepository;

    private final ScopeRepository scopeRepository;

    private final RegisteredClientDataSourceRepository registeredClientDataSourceRepository;

    private final RegisteredClientSecretDataSourceRepository registeredClientSecretDataSourceRepository;

    private final ClientSettingsDataSourceRepository clientSettingsDataSourceRepository;

    private final TokenSettingsDataSourceRepository tokenSettingsDataSourceRepository;

    private final AuthorizationGrantTypeDatasourceRepository authorizationGrantTypeDatasourceRepository;

    private final ClientAuthenticationMethodDataSourceRepository clientAuthenticationMethodDataSourceRepository;

    @Override
    public void save(RegisteredClient registeredClient) {
        if(registeredClient instanceof RegisteredClientModel registeredClientModel) {
            saveRegisteredClientModel(registeredClientModel);
        } else {
            var model = new RegisteredClientModel(registeredClient);
            saveRegisteredClientModel(model);
        }
    }

    @Override
    public void delete(Long registeredClientId) {

    }

    @Override
    public RegisteredClient findById(String id) {
        var timestamp = MutableDateTime.now();
        var longId = Long.parseLong(id);
        var registeredClientBuilder = registeredClientDataSourceRepository.findRegisteredClientByIdWithTimestamp(longId, timestamp)
                .orElseThrow();
        appendAdditionalPropertiesAsync(registeredClientBuilder, longId, timestamp)
                .collectList()
                .block();
        return registeredClientBuilder.build();
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        var timestamp = MutableDateTime.now();
        return registeredClientDataSourceRepository.findRegisteredClientByClientIdWithTimestamp(clientId, timestamp)
                .map(abstractRegisteredClientBuilderWrapper -> {
                    var id = abstractRegisteredClientBuilderWrapper.getId();
                    appendAdditionalPropertiesAsync(abstractRegisteredClientBuilderWrapper, id, timestamp)
                            .collectList()
                            .block();
                    return abstractRegisteredClientBuilderWrapper.build();
                }).orElse(null);
    }

    private void saveRegisteredClientModel(RegisteredClientModel registeredClientModel) {
        var properties = registeredClientModel.getPropertiesModel();
        var registeredClientId = registeredClientDataSourceRepository.save(properties).getId();
        saveRegisteredClientSecret(registeredClientId, registeredClientModel.getSecretModel());
        saveClientSettings(registeredClientId, registeredClientModel.getClientSettingsModel());
        saveTokenSettings(registeredClientId, registeredClientModel.getTokenSettingsModel());
        saveClientAuthenticationMethodsBounds(registeredClientId, registeredClientModel.getClientAuthenticationMethodsSet());
        scopeRepository.saveAllBounds(registeredClientModel.getScopeModels(), registeredClientId);
        saveAuthorizationGrantTypeBounds(registeredClientId, registeredClientModel.getAuthorizationGrantTypesSet());
        redirectRepository.saveAllRedirectBounds(registeredClientModel.getRedirectUriModels(), registeredClientId);
        redirectRepository.saveAllPostLogoutRedirectBounds(registeredClientModel.getPostLogoutRedirectUriModels(), registeredClientId);
    }

    private void saveRegisteredClientSecret(Long registeredClientId, RegisteredClientSecretModel secret){
        secret.setRegisteredClientId(registeredClientId);
        secret.setValue(passwordEncoder.encode(secret.getValue()));
        registeredClientSecretDataSourceRepository.save(secret);
    }

    private void saveClientSettings(Long registeredClientId, ClientSettingsEntityModel clientSettings) {
        clientSettings.setRegisteredClientId(registeredClientId);
        clientSettingsDataSourceRepository.save(clientSettings);
    }

    private void saveTokenSettings(Long registeredClientId, TokenSettingsEntityModel tokenSettings) {
        tokenSettings.setRegisteredClientId(registeredClientId);
        tokenSettingsDataSourceRepository.save(tokenSettings);
    }

    private void saveAuthorizationGrantTypeBounds(Long registeredClientId, Collection<AuthorizationGrantTypeEntityModel> authorizationGrantTypeEntityModels) {
        var boundsCollection = authorizationGrantTypeDatasourceRepository.getAuthorizationGrantTypeByExamples(authorizationGrantTypeEntityModels)
                .stream()
                .map(authorizationGrantType -> new AuthorizationGrantTypeBindingModel(registeredClientId, authorizationGrantType.getId()))
                .toList();
        authorizationGrantTypeDatasourceRepository.saveBounds(boundsCollection);
    }

    private void saveClientAuthenticationMethodsBounds(Long registeredClientId, Collection<ClientAuthenticationMethodEntityModel> methods) {
        var boundsCollection = clientAuthenticationMethodDataSourceRepository.getClientAuthenticationMethodsByExamples(methods).stream()
                .map(clientAuthenticationMethod -> new ClientAuthenticationMethodBindingModel(registeredClientId, clientAuthenticationMethod.getId()))
                .toList();
        clientAuthenticationMethodDataSourceRepository.saveAllBounds(boundsCollection);
    }

    private Flux<Void> appendAdditionalPropertiesAsync(RegisteredClient.Builder registeredClientBuilder, long id, MutableDateTime timestamp) {
        var appendGrantTypesMono = appendRegisteredClientValuesAsync(
                registeredClientBuilder,
                ()-> authorizationGrantTypeDatasourceRepository.getAuthorizationGrantTypeSetByRegisteredClientId(id),
                ((builder, authorizationGrantTypes) -> builder.authorizationGrantTypes(values -> values.addAll(authorizationGrantTypes)))
        );
        var appendClientAuthMethodsMono = appendRegisteredClientValuesAsync(
                registeredClientBuilder,
                ()-> clientAuthenticationMethodDataSourceRepository.getClientAuthenticationMethodSetByRegisteredClientId(id),
                ((builder, clientAuthenticationMethods) -> builder.clientAuthenticationMethods(values -> values.addAll(clientAuthenticationMethods)))
        );
        var appendRedirectUriMono = appendRegisteredClientValuesAsync(
                registeredClientBuilder,
                ()-> redirectRepository.getRedirectUriByRegisteredClientIdWithTimestamp(id, timestamp),
                ((builder, uriSet) -> builder.redirectUris(values -> values.addAll(uriSet)))
        );
        var appendPostLogoutRedirectUriMono = appendRegisteredClientValuesAsync(
                registeredClientBuilder,
                ()-> redirectRepository.getPostLogoutRedirectUriByRegisteredClientId(id),
                ((builder, uriSet) -> builder.postLogoutRedirectUris(values -> values.addAll(uriSet)))
        );
        var appendScopesMono = appendRegisteredClientValuesAsync(
                registeredClientBuilder,
                ()-> scopeRepository.getScopeNameByRegisteredClientId(id),
                ((builder, strings) -> builder.scopes(values -> values.addAll(strings)))
        );
        var appendMonoList = List.of(
                appendGrantTypesMono,
                appendClientAuthMethodsMono,
                appendRedirectUriMono,
                appendPostLogoutRedirectUriMono,
                appendScopesMono
        );
        return Flux.merge(appendMonoList);
    }

    private <T> Mono<Void> appendRegisteredClientValuesAsync(RegisteredClient.Builder builder, Supplier<T> valueSupplier, BiConsumer<RegisteredClient.Builder, T> registeredClientValueConsumer) {
        return Mono.fromFuture(()-> CompletableFuture.runAsync(()-> {
            var values = valueSupplier.get();
            registeredClientValueConsumer.accept(builder, values);
        }, executor));
    }


}
