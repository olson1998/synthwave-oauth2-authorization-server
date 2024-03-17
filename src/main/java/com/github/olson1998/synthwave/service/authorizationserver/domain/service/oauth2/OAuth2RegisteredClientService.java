package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.*;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.OAuth2RegisteredClientRepository;
import lombok.RequiredArgsConstructor;
import org.joda.time.MutableDateTime;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class OAuth2RegisteredClientService implements OAuth2RegisteredClientRepository {

    private final Executor executor;

    private final RegisteredClientDataSourceRepository registeredClientDataSourceRepository;

    private final ScopeDataSourceRepository scopeDataSourceRepository;

    private final RedirectUriDataSourceRepository redirectUriDataSourceRepository;

    private final PostLogoutRedirectUriDataSourceRepository postLogoutRedirectUriDataSourceRepository;

    private final AuthorizationGrantTypeDatasourceRepository authorizationGrantTypeDatasourceRepository;

    private final ClientAuthenticationMethodDataSourceRepository clientAuthenticationMethodDataSourceRepository;

    @Override
    public void save(RegisteredClient registeredClient) {

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
                ()-> postLogoutRedirectUriDataSourceRepository.getPostLogoutRedirectUriByRegisteredClientId(id),
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

}
