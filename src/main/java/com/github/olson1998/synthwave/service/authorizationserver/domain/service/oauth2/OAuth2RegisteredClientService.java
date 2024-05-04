package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.*;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.ClientSettingsDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.RegisteredClientDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.RegisteredClientSecretDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.TokenSettingsDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.RegisteredClientProperties;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.MutableDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class OAuth2RegisteredClientService implements OAuth2RegisteredClientRepository {

    private final Executor executor;

    private final PasswordEncoder passwordEncoder;

    private final RedirectRepository redirectRepository;

    private final ScopeRepository scopeRepository;

    private final AuthorizationGrantTypeRepository authorizationGrantTypeRepository;

    private final ClientAuthenticationMethodRepository clientAuthenticationMethodRepository;

    private final RegisteredClientDataSourceRepository registeredClientDataSourceRepository;

    private final RegisteredClientSecretDataSourceRepository registeredClientSecretDataSourceRepository;

    private final ClientSettingsDataSourceRepository clientSettingsDataSourceRepository;

    private final TokenSettingsDataSourceRepository tokenSettingsDataSourceRepository;

    @Override
    public Page<? extends RegisteredClientProperties> searchRegisteredClient(Long id,
                                                                             String clientIdPatter,
                                                                             String clientNamePattern,
                                                                             MutableDateTime timestamp,
                                                                             boolean filterExpired,
                                                                             boolean filterNonActive,
                                                                             int pageSize,
                                                                             int page) {
        SearchRegisteredClientQuery query = SearchRegisteredClientQuery.builder()
                .registeredClientId(id)
                .clientIdPattern(clientIdPatter)
                .namePattern(clientNamePattern)
                .timestamp(Optional.ofNullable(timestamp).orElseGet(MutableDateTime::now))
                .filterExpired(filterExpired)
                .filterNonActive(filterNonActive)
                .pageSize(pageSize)
                .page(page)
                .build();
        log.debug("Registered Client: {}", query);
        Page<? extends RegisteredClientProperties> dataPage = registeredClientDataSourceRepository.searchRegisteredClient(query);
        List<RegisteredClientPropertiesModel> modelList = dataPage.getContent()
                .stream()
                .map(RegisteredClientPropertiesModel::new)
                .collect(Collectors.toCollection(ArrayList::new));
        return new PageImpl<>(
                modelList,
                dataPage.getPageable(),
                dataPage.getTotalElements()
        );
    }

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
        var grantTypes = authorizationGrantTypeRepository.getAuthorizationGrantTypeByExamples(authorizationGrantTypeEntityModels);
        authorizationGrantTypeRepository.saveBounds(registeredClientId, grantTypes);
    }

    private void saveClientAuthenticationMethodsBounds(Long registeredClientId, Collection<ClientAuthenticationMethodEntityModel> methods) {
        var boundsCollection = clientAuthenticationMethodRepository.getClientAuthenticationMethodsByExamples(methods);
        clientAuthenticationMethodRepository.saveBounds(registeredClientId, boundsCollection);
    }

    private Flux<Void> appendAdditionalPropertiesAsync(RegisteredClient.Builder registeredClientBuilder, long id, MutableDateTime timestamp) {
        var appendGrantTypesMono = appendRegisteredClientValuesAsync(
                registeredClientBuilder,
                ()-> authorizationGrantTypeRepository.getAuthorizationGrantTypesByRegisteredClientId(id),
                ((builder, authorizationGrantTypes) -> builder.authorizationGrantTypes(values -> values.addAll(authorizationGrantTypes)))
        );
        var appendClientAuthMethodsMono = appendRegisteredClientValuesAsync(
                registeredClientBuilder,
                ()-> clientAuthenticationMethodRepository.getClientAuthenticationMethodSetByRegisteredClientId(id),
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
