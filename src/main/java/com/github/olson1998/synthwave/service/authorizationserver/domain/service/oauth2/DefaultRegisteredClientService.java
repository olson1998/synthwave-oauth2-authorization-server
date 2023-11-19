package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.RedirectURIBindingDTO;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.RegisteredClientSettingsDTO;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.RegisteredClientEntityImpl;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.*;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectURIBinding;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.RegistrationClientRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.SynthWaveRegisteredClientRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RegisteredClientConfig;
import io.hypersistence.tsid.TSID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DefaultRegisteredClientService implements SynthWaveRegisteredClientRepository {

    private final RegistrationClientRepository registrationClientRepository;

    private final RedirectURIDataSourceRepository redirectUrisDataSourceRepository;

    private final RedirectURIBindingDataSourceRepository redirectURIBindingDataSourceRepository;

    private final UserPropertiesDataSourceRepository userPropertiesDataSourceRepository;

    private final RegisteredClientSourceRepository registeredClientSourceRepository;

    private final RegisteredClientSettingsDataSourceRepository registeredClientSettingsDataSourceRepository;

    private final AuthorizationGrantTypeBindDataSourceRepository authorizationGrantTypeBindDataSourceRepository;

    private final ClientAuthenticationMethodBindDataSourceRepository clientAuthenticationMethodBindDataSourceRepository;

    private final RegisteredClientMapper registeredClientMapper = new RegisteredClientMapper();

    private final AuthorizationGrantTypesBoundMapper authorizationGrantTypesBoundMapper = new AuthorizationGrantTypesBoundMapper();

    private final ClientAuthenticationMethodBoundMapper clientAuthenticationMethodBoundMapper = new ClientAuthenticationMethodBoundMapper();

    @Override
    public void save(@NonNull RegisteredClient registeredClient) {
        var username = Objects.requireNonNull(
                registeredClient.getClientName(),
                "Client id is required property of registered client"
        );
        var userMetadata = userPropertiesDataSourceRepository.getUserMetadataByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: '%s' has not been found".formatted(username)));
        var clientId = Optional.ofNullable(registeredClient.getClientId())
                .orElseGet(()-> username + "@synthwave.com");
        var userId = userMetadata.getUserId();
        var registeredClientProps = new RegisteredClientEntityImpl(userId, clientId);
        var registeredClientEntity = registeredClientSourceRepository.save(registeredClientProps);
        var registeredClientId = registeredClientEntity.getId();
        var authorizationGrantTypes = authorizationGrantTypesBoundMapper.mapToAuthorizationGrantTypeBindings(
                registeredClientId,
                registeredClient.getAuthorizationGrantTypes()
        );
        var clientSettings = registeredClient.getClientSettings();
        var registeredClientSettings = new RegisteredClientSettingsDTO(
                registeredClientId,
                clientSettings.isRequireProofKey(),
                clientSettings.isRequireAuthorizationConsent()
        );
        var clientAuthenticationMethodBounds = clientAuthenticationMethodBoundMapper.map(
                registeredClientId,
                registeredClient.getClientAuthenticationMethods()
        );
        var redirectURIIds = redirectUrisDataSourceRepository.getRedirectURIIdCollectionByRedirectURISetAndPostLogoutRedirectURISet(
                registeredClient.getRedirectUris(),
                registeredClient.getPostLogoutRedirectUris()
        );
        var redirectURIBindings = createRedirectURIBindingSet(redirectURIIds, registeredClientId);
        registeredClientSettingsDataSourceRepository.save(registeredClientSettings);
        redirectURIBindingDataSourceRepository.saveAll(redirectURIBindings);
        authorizationGrantTypeBindDataSourceRepository.saveAll(authorizationGrantTypes);
        clientAuthenticationMethodBindDataSourceRepository.saveAll(clientAuthenticationMethodBounds);
    }

    @Override
    public RegisteredClient findById(String id) {
        var longId = Long.getLong(id);
        var tsid = TSID.from(longId);
        return registeredClientSourceRepository.getRegisteredClientConfigByRegisteredClientId(tsid)
                .map(this::appendRegisteredClientConfigs)
                .map(registeredClientMapper::map)
                .orElse(null);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return Optional.ofNullable(registrationClientRepository.getRegistrationClient(clientId))
                .orElseGet(()-> registeredClientSourceRepository.getRegisteredClientConfigByClientId(clientId)
                        .map(this::appendRegisteredClientConfigs)
                        .map(registeredClientMapper::map)
                        .orElse(null));
    }

    private RegisteredClientConfig appendRegisteredClientConfigs(RegisteredClientConfig registeredClientConfig){
        var clientId = registeredClientConfig.getId();
        var redirectURI = redirectUrisDataSourceRepository.getRedirectURIByRegisteredClientId(clientId);
        var authorizationGrantTypes =
                authorizationGrantTypeBindDataSourceRepository.getAuthorizationGrantTypesByRegisteredClientId(clientId);
        var clientAuthenticationMethods =
                clientAuthenticationMethodBindDataSourceRepository.getClientAuthenticationMethodsByRegisteredClientId(clientId);
        return registeredClientConfig
                .withRedirectUris(redirectURI)
                .withAuthorizationGrantTypes(authorizationGrantTypes)
                .withClientAuthenticationMethods(clientAuthenticationMethods);
    }

    private Set<RedirectURIBinding> createRedirectURIBindingSet(Collection<TSID> redirectURIIds, TSID registeredClientId){
        return redirectURIIds.stream()
                .map(id -> new RedirectURIBindingDTO(id, registeredClientId))
                .collect(Collectors.toSet());
    }

}
