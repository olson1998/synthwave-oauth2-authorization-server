package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.RegisteredClientSettingsDTO;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.RegisteredClientEntityImpl;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.*;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.RegistrationClientRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.SynthWaveRegisteredClientRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RegisteredClientProperties;
import io.hypersistence.tsid.TSID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class SynthWaveRegisteredClientService implements SynthWaveRegisteredClientRepository {

    private final RegistrationClientRepository registrationClientRepository;

    private final RedirectURIDataSourceRepository redirectUrisDataSourceRepository;

    private final RedirectURIBindingDataSourceRepository redirectURIBindingDataSourceRepository;

    private final UserPropertiesDataSourceRepository userPropertiesDataSourceRepository;

    private final RegisteredClientPropertiesSourceRepository registeredClientPropertiesSourceRepository;

    private final RegisteredClientSettingsDataSourceRepository registeredClientSettingsDataSourceRepository;

    private final AuthorizationGrantTypeBindDataSourceRepository authorizationGrantTypeBindDataSourceRepository;

    private final ClientAuthenticationMethodBindDataSourceRepository clientAuthenticationMethodBindDataSourceRepository;

    private final RegisteredClientMapper registeredClientMapper = new RegisteredClientMapper();

    private final RedirectURIBoundMapper redirectURIBoundMapper = new RedirectURIBoundMapper();

    private final AuthorizationGrantTypesBoundMapper authorizationGrantTypesBoundMapper = new AuthorizationGrantTypesBoundMapper();

    private final ClientAuthenticationMethodBoundMapper clientAuthenticationMethodBoundMapper = new ClientAuthenticationMethodBoundMapper();

    @Override
    public void save(@NonNull RegisteredClient registeredClient) {
        var username = Objects.requireNonNull(
                registeredClient.getClientName(),
                "Client id is required property of registered client"
        );
        var userProps = userPropertiesDataSourceRepository.getExtendedUserPropertiesByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: '%s' has not been found".formatted(username)));
        var userId = userProps.getId();
        var redirectUrisValues = registeredClient.getRedirectUris();
        var postLogoutRedirectUrisValues = registeredClient.getPostLogoutRedirectUris();
        var redirectUrisIds =
                redirectUrisDataSourceRepository.getRedirectURIByURISet(redirectUrisValues);
        var postLogoutRedirectUrisIds =
                redirectUrisDataSourceRepository.getPostLogoutRedirectURIIdByURISet(postLogoutRedirectUrisValues);
        var registeredClientProps = new RegisteredClientEntityImpl(userId);
        var registeredClientEntity = registeredClientPropertiesSourceRepository.save(registeredClientProps);
        var registeredClientId = registeredClientEntity.getId();
        var redirectURIBounds = redirectURIBoundMapper.mapBounds(
                registeredClientId,
                userProps.getCompanyCode(),
                userProps.getDivision(),
                redirectUrisIds,
                postLogoutRedirectUrisIds
        );
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
        registeredClientSettingsDataSourceRepository.save(registeredClientSettings);
        authorizationGrantTypeBindDataSourceRepository.saveAll(authorizationGrantTypes);
        clientAuthenticationMethodBindDataSourceRepository.saveAll(clientAuthenticationMethodBounds);
        redirectURIBindingDataSourceRepository.saveAll(redirectURIBounds);
    }

    @Override
    public RegisteredClient findById(String id) {
        var longId = Long.getLong(id);
        var tsid = TSID.from(longId);
        return registeredClientPropertiesSourceRepository.getRegisteredClientConfigByRegisteredClientId(tsid)
                .map(this::appendRegisteredClientConfigs)
                .map(registeredClientMapper::map)
                .orElse(null);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return Optional.ofNullable(registrationClientRepository.getRegistrationClient(clientId))
                .orElseGet(()-> registeredClientPropertiesSourceRepository.getRegisteredClientConfigByClientId(clientId)
                        .map(this::appendRegisteredClientConfigs)
                        .map(registeredClientMapper::map)
                        .orElse(null));
    }

    private RegisteredClientProperties appendRegisteredClientConfigs(RegisteredClientProperties registeredClientProperties){
        var clientId = registeredClientProperties.getClientId();
        var divi = registeredClientProperties.getDivision();
        var code = registeredClientProperties.getCompanyCode();
        var redirectURI =
                redirectUrisDataSourceRepository.getRedirectURIByRegisteredClientIdCompanyCodeAndDivision(clientId, code, divi);
        var authorizationGrantTypes =
                authorizationGrantTypeBindDataSourceRepository.getAuthorizationGrantTypesByRegisteredClientId(clientId);
        var clientAuthenticationMethods =
                clientAuthenticationMethodBindDataSourceRepository.getClientAuthenticationMethodsByRegisteredClientId(clientId);
        return registeredClientProperties
                .withRedirectUris(redirectURI)
                .withAuthorizationGrantTypes(authorizationGrantTypes)
                .withClientAuthenticationMethods(clientAuthenticationMethods);
    }

}
