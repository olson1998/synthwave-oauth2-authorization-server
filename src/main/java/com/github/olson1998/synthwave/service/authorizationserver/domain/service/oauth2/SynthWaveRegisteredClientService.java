package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.PostLoginRedirectURI;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.PostLogoutRedirectURI;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.RegisteredClientProps;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.RedirectUrisDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.RegisteredClientPropertiesSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.UserPropertiesSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectURI;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.SynthWaveRegisteredClientRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.SynthWaveRegisteredClientProperties;
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
public class SynthWaveRegisteredClientService implements SynthWaveRegisteredClientRepository {

    private final RedirectUrisDataSourceRepository redirectUrisDataSourceRepository;

    private final UserPropertiesSourceRepository userPropertiesSourceRepository;

    private final RegisteredClientPropertiesSourceRepository registeredClientPropertiesSourceRepository;

    private final SynthWaveRegisteredClientPropertiesMapper synthWaveRegisteredClientPropertiesMapper =
            new SynthWaveRegisteredClientPropertiesMapper();

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
        var userProps = userPropertiesSourceRepository.getUserPropertiesByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: '%s' has not been found".formatted(username)));
        var registeredClientProps = new RegisteredClientProps(TSID.from(id), userProps.getId(), clientId);
        var concatRedirectUri = concatRedirectUris(redirectUris, postLogoutRedirectUris);
        registeredClientPropertiesSourceRepository.save(registeredClientProps);
        redirectUrisDataSourceRepository.saveAll(redirectUrisDataSourceRepository.getAllNotPresentRedirectUris(concatRedirectUri));
    }

    @Override
    public RegisteredClient findById(String id) {
        var longId = Long.getLong(id);
        var tsid = TSID.from(longId);
        return registeredClientPropertiesSourceRepository.getSynthWaveRegisteredClientPropsByRegisteredClientId(tsid)
                .map(this::mapRedirectUris)
                .map(synthWaveRegisteredClientPropertiesMapper::map)
                .orElse(null);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return registeredClientPropertiesSourceRepository.getSynthWaveRegisteredClientPropsByClientId(clientId)
                .map(this::mapRedirectUris)
                .map(synthWaveRegisteredClientPropertiesMapper::map)
                .orElse(null);
    }

    private SynthWaveRegisteredClientProperties mapRedirectUris(SynthWaveRegisteredClientProperties synthWaveRegisteredClientProperties) {
        var redirectUris = redirectUrisDataSourceRepository.getAllRedirectUris();
        synthWaveRegisteredClientProperties.appendUnresolvedUris(redirectUris);
        return synthWaveRegisteredClientProperties;
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
