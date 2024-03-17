package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.*;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.OAuth2RegisteredClientRepository;
import lombok.RequiredArgsConstructor;
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
        var longId = Long.parseLong(id);
        var properties = registeredClientDataSourceRepository.findRegisteredClientById(longId);
        return null;
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return null;
    }

    private void appendOneToManyEntities(RegisteredClient.Builder registeredClientBuilder, long id) {
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
                ()-> redirectUriDataSourceRepository.getRedirectUriByRegisteredClientId(id),
                ((builder, uriModelSet) -> {
                    var uriSet =
                })
        );
        var appendPostLogoutRedirectUriMono = appendRegisteredClientValuesAsync(
                registeredClientBuilder,
                ()-> postLogoutRedirectUriDataSourceRepository.getPostLogoutRedirectUriByRegisteredClientId(id),
                ((builder, uriModelSet) -> {
                    var uriSet =
                })
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
        Mono.just(appendMonoList)
                .flatMapMany(Flux::fromIterable)
                .collectList()
                .block();
    }

    private <T> Mono<Void> appendRegisteredClientValuesAsync(RegisteredClient.Builder builder, Supplier<T> valueSupplier, BiConsumer<RegisteredClient.Builder, T> registeredClientValueConsumer) {
        return Mono.fromFuture(()-> CompletableFuture.runAsync(()-> {
            var values = valueSupplier.get();
            registeredClientValueConsumer.accept(builder, values);
        }));
    }

}
