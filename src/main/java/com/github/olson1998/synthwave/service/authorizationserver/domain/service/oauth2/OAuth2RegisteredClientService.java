package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.ClientSettingsEntityModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.RegisteredClientModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.RegisteredClientSecretModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.TokenSettingsEntityModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.*;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.RedirectUri;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.RedirectUriBinding;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.OAuth2RegisteredClientRepository;
import lombok.RequiredArgsConstructor;
import org.joda.time.MutableDateTime;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class OAuth2RegisteredClientService implements OAuth2RegisteredClientRepository {

    private final Executor executor;

    private final RegisteredClientDataSourceRepository registeredClientDataSourceRepository;

    private final RegisteredClientSecretDataSourceRepository registeredClientSecretDataSourceRepository;

    private final ClientSettingsDataSourceRepository clientSettingsDataSourceRepository;

    private final TokenSettingsDataSourceRepository tokenSettingsDataSourceRepository;

    private final ScopeDataSourceRepository scopeDataSourceRepository;

    private final RedirectUriDataSourceRepository redirectUriDataSourceRepository;

    private final AuthorizationGrantTypeDatasourceRepository authorizationGrantTypeDatasourceRepository;

    private final ClientAuthenticationMethodDataSourceRepository clientAuthenticationMethodDataSourceRepository;

    @Override
    public void save(RegisteredClient registeredClient) {
        if(registeredClient instanceof RegisteredClientModel registeredClientModel) {
            saveRegisteredClientModel(registeredClientModel);
        } else {
            saveRegisteredClient(registeredClient);
        }
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
        var registeredClientBuilder = registeredClientDataSourceRepository.findRegisteredClientByClientIdWithTimestamp(clientId, timestamp)
                .orElseThrow();
        var id = registeredClientBuilder.getId();
        appendAdditionalPropertiesAsync(registeredClientBuilder, id, timestamp)
                .collectList()
                .block();
        return registeredClientBuilder.build();
    }

    private void saveRegisteredClientModel(RegisteredClientModel registeredClientModel) {
        var properties = registeredClientModel.getPropertiesModel();
        var registeredClientId = registeredClientDataSourceRepository.save(properties).getId();
        saveRegisteredClientSecret(registeredClientId, registeredClientModel.getSecretModel());
        saveClientSettings(registeredClientId, registeredClientModel.getClientSettingsModel());
        saveTokenSettings(registeredClientId, registeredClientModel.getTokenSettingsModel());
        var existingScopes = scopeDataSourceRepository.getScopesByExamples(registeredClientModel.getScopeModels());
        var redirectUris = registeredClientModel.getRedirectUriModels();
        var existingRedirectUris = redirectUriDataSourceRepository.getRedirectUriByExamples(registeredClientModel.getRedirectUriModels());
        if(areAllMatchingExampleRedirectUri(existingRedirectUris, redirectUris)) {

        } else {

        }
        var postLogoutRedirectUris = registeredClientModel.getPostLogoutRedirectUriModels();
        var existingPostLogoutRedirectUris = redirectUriDataSourceRepository.getRedirectUriByExamples(postLogoutRedirectUris);
        if(areAllMatchingExampleRedirectUri(existingPostLogoutRedirectUris, postLogoutRedirectUris)) {

        } else {

        }
    }

    private void saveRegisteredClientSecret(Long registeredClientId, RegisteredClientSecretModel secret){
        secret.setRegisteredClientId(registeredClientId);
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

    private void saveScopes(Long registeredClientId, Collection<RedirectUri> redirectUri){

    }

    private void saveRedirectUris(Collection<RedirectUri> redirectUris,
                                  Function<Collection<RedirectUri>, Collection<RedirectUri>> existingDataSupplier,
                                  Consumer<Collection<? extends RedirectUriBinding>> provisioningConsumer) {
        var existingRedirect = existingDataSupplier.apply(redirectUris);
        if(areAllMatchingExampleRedirectUri(existingRedirect, redirectUris)) {
            var redirectIds = existingRedirect.stream()
                    .map(RedirectUri::getId)
                    .map()
                    .toList();
            provisioningConsumer.accept();
        } else {

        }
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
                ()-> redirectUriDataSourceRepository.getRedirectUriByRegisteredClientIdWithTimestamp(id, timestamp),
                ((builder, uriSet) -> builder.redirectUris(values -> values.addAll(uriSet)))
        );
        var appendPostLogoutRedirectUriMono = appendRegisteredClientValuesAsync(
                registeredClientBuilder,
                ()-> redirectUriDataSourceRepository.getPostLogoutRedirectUriByRegisteredClientId(id),
                ((builder, uriSet) -> builder.postLogoutRedirectUris(values -> values.addAll(uriSet)))
        );
        var appendScopesMono = appendRegisteredClientValuesAsync(
                registeredClientBuilder,
                ()-> scopeDataSourceRepository.getScopesByRegisteredClientId(id),
                ((builder, strings) -> builder.scopes(values -> values.addAll(strings)))
        );
        var appendMonoList = List.of(
                appendGrantTypesMono,
                appendClientAuthMethodsMono,
                appendRedirectUriMono,
                appendPostLogoutRedirectUriMono,
                appendScopesMono
        );
        return Flux.fromIterable(appendMonoList)
                .flatMap(voidMono -> voidMono);
    }

    private <T> Mono<Void> appendRegisteredClientValuesAsync(RegisteredClient.Builder builder, Supplier<T> valueSupplier, BiConsumer<RegisteredClient.Builder, T> registeredClientValueConsumer) {
        return Mono.fromFuture(()-> CompletableFuture.runAsync(()-> {
            var values = valueSupplier.get();
            registeredClientValueConsumer.accept(builder, values);
        }, executor));
    }

    private boolean areAllMatchingExampleRedirectUri(Collection<? extends RedirectUri> data, Collection<? extends RedirectUri> examples) {
        if(data.size() != examples.size()) {
            return false;
        } else {
            return examples.stream()
                    .allMatch(example -> data.stream().anyMatch(redirectUri -> isMatchingExampleRedirectUri(redirectUri, example)));
        }
    }

    private boolean isMatchingExampleRedirectUri(RedirectUri redirectUri, RedirectUri example) {
        if(example.getId() != null && example.getValue() != null) {
            return redirectUri.getId().equals(example.getId()) &&
                    redirectUri.getValue().equals(example.getValue());
        } else if (example.getId() != null) {
            return redirectUri.getId().equals(example.getId());
        } else if (example.getValue() != null) {
            return redirectUri.getValue().equals(example.getValue());
        } else {
            return false;
        }
    }

}
