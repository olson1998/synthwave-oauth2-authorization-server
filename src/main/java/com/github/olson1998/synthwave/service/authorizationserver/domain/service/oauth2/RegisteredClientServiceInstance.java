package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.PostLoginRedirectURI;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.PostLogoutRedirectURI;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.RegisteredClientProps;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.RedirectURIsDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.RegisteredClientPropertiesSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.UserPropertiesDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectURI;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.RegisteredClientMapper;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.RegisteredClientRepository;
import io.hypersistence.tsid.TSID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class RegisteredClientServiceInstance implements RegisteredClientRepository {

    private final RegisteredClientMapper registeredClientMapper;

    private final RedirectURIsDataSourceRepository redirectUrisDataSourceRepository;

    private final UserPropertiesDataSourceRepository userPropertiesDataSourceRepository;

    private final RegisteredClientPropertiesSourceRepository registeredClientPropertiesSourceRepository;

    @Override
    public void save(@NonNull RegisteredClient registeredClient) {
        var clientId = Objects.requireNonNull(
                registeredClient.getClientId(),
                "Client id is required property of registered client"
        );
        var username = Objects.requireNonNull(
                registeredClient.getClientName(),
                "Client id is required property of registered client"
        );
        var redirectUris = registeredClient.getRedirectUris();
        var postLogoutRedirectUris = registeredClient.getPostLogoutRedirectUris();
        var id = Long.parseLong(registeredClient.getId());
        var userProps = userPropertiesDataSourceRepository.getUserPropertiesByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: '%s' has not been found".formatted(username)));
        var registeredClientProps = new RegisteredClientProps(TSID.from(id), userProps.getId(), clientId );
        var concatRedirectUri = concatRedirectUris(redirectUris, postLogoutRedirectUris);
        registeredClientPropertiesSourceRepository.save(registeredClientProps);
        redirectUrisDataSourceRepository.saveAll(redirectUrisDataSourceRepository.getAllNotPresentRedirectUris(concatRedirectUri));
    }

    @Override
    public RegisteredClient findById(String id) {
        var longId = Long.getLong(id);
        var tsid = TSID.from(longId);
        return registeredClientPropertiesSourceRepository.getRegisteredClientConfigByRegisteredClientId(tsid)
                .map(config -> config.withRedirectUris(redirectUrisDataSourceRepository.getAllRedirectUris()))
                .map(registeredClientMapper::map)
                .orElse(null);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return registeredClientPropertiesSourceRepository.getRegisteredClientConfigByClientId(clientId)
                .map(config -> config.withRedirectUris(redirectUrisDataSourceRepository.getAllRedirectUris()))
                .map(registeredClientMapper::map)
                .orElse(null);
    }

    private Set<RedirectURI> concatRedirectUris(Set<String> redirectUris, Set<String> postLogoutRedirectUris){
        var redirectUrisStream = redirectUris.stream()
                .map(PostLoginRedirectURI::new)
                .map(RedirectURI.class::cast);
        var postLogoutRedirectUrisStream = postLogoutRedirectUris.stream()
                .map(PostLogoutRedirectURI::new)
                .map(RedirectURI.class::cast);
        return Stream.concat(redirectUrisStream, postLogoutRedirectUrisStream)
                .collect(Collectors.toSet());
    }

}
