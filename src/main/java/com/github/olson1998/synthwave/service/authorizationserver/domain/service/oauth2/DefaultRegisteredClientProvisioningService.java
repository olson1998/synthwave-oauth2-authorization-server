package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.RedirectURIBindingDTO;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.RegisteredClientSettingsDTO;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.RegisteredClientEntityImpl;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.*;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectURIBinding;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.RegisteredClientProvisioningRepository;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class DefaultRegisteredClientProvisioningService implements RegisteredClientProvisioningRepository {

    private final RedirectURIDataSourceRepository redirectUrisDataSourceRepository;

    private final RedirectURIBindingDataSourceRepository redirectURIBindingDataSourceRepository;

    private final UserDataSourceRepository userDataSourceRepository;

    private final RegisteredClientDataSourceRepository registeredClientDataSourceRepository;

    private final RegisteredClientSettingsDataSourceRepository registeredClientSettingsDataSourceRepository;

    private final AuthorizationGrantTypeBindDataSourceRepository authorizationGrantTypeBindDataSourceRepository;

    private final ClientAuthenticationMethodBindDataSourceRepository clientAuthenticationMethodBindDataSourceRepository;

    private final AuthorizationGrantTypesBoundMapper authorizationGrantTypesBoundMapper = new AuthorizationGrantTypesBoundMapper();

    private final ClientAuthenticationMethodBoundMapper clientAuthenticationMethodBoundMapper = new ClientAuthenticationMethodBoundMapper();

    @Override
    public void provision(RegisteredClient registeredClient) {
        var username = Objects.requireNonNull(
                registeredClient.getClientName(),
                "Client id is required property of registered client"
        );
        var userMetadata = userDataSourceRepository.getUserMetadataByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: '%s' has not been found".formatted(username)));
        log.debug("Provisioning of user: {}", userMetadata);
        var clientId = Optional.ofNullable(registeredClient.getClientId())
                .orElseGet(()-> username + "@synthwave.com");
        var userId = userMetadata.getUserId();
        var registeredClientProps = new RegisteredClientEntityImpl(userId, clientId);
        var registeredClientEntity = registeredClientDataSourceRepository.save(registeredClientProps);
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

    private Set<RedirectURIBinding> createRedirectURIBindingSet(Collection<TSID> redirectURIIds, TSID registeredClientId){
        return redirectURIIds.stream()
                .map(id -> new RedirectURIBindingDTO(id, registeredClientId))
                .collect(Collectors.toSet());
    }
}
