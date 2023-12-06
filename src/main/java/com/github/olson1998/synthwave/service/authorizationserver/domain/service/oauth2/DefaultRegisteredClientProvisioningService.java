package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.RegisteredClientSecretModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.RegisteredClientSettingsModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.ClientSecretModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.OAuth2ClientModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.RegisteredClientTokenSettingsModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.AuthorizationGrantTypeBindDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.ClientAuthenticationMethodBindDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.RegisteredClientRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.RegisteredClientProvisioningRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.UserDetailsRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.AbstractSynthWaveRegisteredClient;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.ClientSecret;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserMetadata;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.repository.RedirectRepository;
import com.github.olson1998.synthwave.support.joda.converter.JavaInstantConverter;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.HashSet;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class DefaultRegisteredClientProvisioningService implements RegisteredClientProvisioningRepository {

    private final RedirectRepository redirectRepository;

    private final UserDetailsRepository userDetailsRepository;

    private final RegisteredClientRepository registeredClientRepository;

    private final AuthorizationGrantTypeBindDataSourceRepository authorizationGrantTypeBindDataSourceRepository;

    private final ClientAuthenticationMethodBindDataSourceRepository clientAuthenticationMethodBindDataSourceRepository;

    private final AuthorizationGrantTypesBoundMapper authorizationGrantTypesBoundMapper = new AuthorizationGrantTypesBoundMapper();

    private final ClientAuthenticationMethodBoundMapper clientAuthenticationMethodBoundMapper = new ClientAuthenticationMethodBoundMapper();

    @Override
    public RegisteredClient provision(RegisteredClient registeredClient) {
        if(registeredClient instanceof AbstractSynthWaveRegisteredClient synthWaveRegisteredClient){
            provisionSynthWaveClient(synthWaveRegisteredClient);
            return registeredClient;
        }
        var username = Objects.requireNonNull(
                registeredClient.getClientName(),
                "Client id is required property of registered client"
        );
        var userMetadata = userDetailsRepository.getUserMetadataByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: '%s' has not been found".formatted(username)));
        var clientSecret = new ClientSecretModel(
                registeredClient.getClientSecret(),
                new JavaInstantConverter(registeredClient.getClientSecretExpiresAt()).toMutableDateTime()
        );
        doProvisionRegisteredClient(registeredClient, clientSecret, userMetadata);
        return registeredClient;
    }

    @Override
    public void provisionSynthWaveClient(AbstractSynthWaveRegisteredClient synthWaveRegisteredClient) {
        var username = synthWaveRegisteredClient.getClientName();
        var userMetadata = userDetailsRepository.getUserMetadataByUsername(username)
                .orElseGet(()-> userDetailsRepository.saveUser(synthWaveRegisteredClient));
        doProvisionRegisteredClient(synthWaveRegisteredClient, synthWaveRegisteredClient.getClientSecretObject(), userMetadata);
    }

    private void doProvisionRegisteredClient(RegisteredClient registeredClient, ClientSecret clientSecret, UserMetadata userMetadata){
        var username = userMetadata.getUsername();
        if(!registeredClientRepository.isExistingRegisteredClientForUser(username)){
            saveRegisteredClient(registeredClient, clientSecret, userMetadata);
        }else {
            log.info("Registered client for user: '{}' already exists", username);
        }
    }

    private void saveRegisteredClient(RegisteredClient registeredClient, ClientSecret clientSecret, UserMetadata userMetadata){
        var clientId = registeredClient.getClientId();
        var username = userMetadata.getUsername();
        if(clientId.equals("{?}")){
            clientId = username + "@synthwave." + userMetadata.getCompanyCode().toLowerCase() +
                    userMetadata.getDivision().toLowerCase() + ".com";
        }
        var userId = userMetadata.getUserId();
        var clientModel = new OAuth2ClientModel(userId, clientId);
        var registeredClientId = registeredClientRepository.saveRegisteredClient(clientModel);
        var registeredClientSecret = new RegisteredClientSecretModel(
                registeredClientId,
                clientSecret.getValue(),
                clientSecret.getExpiresDateTime()
        );
        var clientSettings = registeredClient.getClientSettings();
        var registeredClientSettings = new RegisteredClientSettingsModel(
                registeredClientId,
                clientSettings.isRequireProofKey(),
                clientSettings.isRequireAuthorizationConsent()
        );
        var clientTokenSettings = new RegisteredClientTokenSettingsModel(
                registeredClientId,
                registeredClient.getTokenSettings()
        );
        var authorizationGrantTypes = authorizationGrantTypesBoundMapper.mapToAuthorizationGrantTypeBindings(
                registeredClientId,
                registeredClient.getAuthorizationGrantTypes()
        );
        var clientAuthenticationMethodBounds = clientAuthenticationMethodBoundMapper.map(
                registeredClientId,
                registeredClient.getClientAuthenticationMethods()
        );
        registeredClientRepository.saveClientSecret(registeredClientId, clientSecret);
        registeredClientRepository.saveClientSettings(registeredClientSettings);
        registeredClientRepository.saveTokenSettings(clientTokenSettings);
        saveRedirectBindings(registeredClient, registeredClientId);
        authorizationGrantTypeBindDataSourceRepository.saveAll(authorizationGrantTypes);
        clientAuthenticationMethodBindDataSourceRepository.saveAll(clientAuthenticationMethodBounds);
    }

    private void saveRedirectBindings(RegisteredClient registeredClient, TSID registeredClientId){
        var requestedRedirectURI = Objects.requireNonNullElseGet(
                registeredClient.getRedirectUris(),
                ()-> new HashSet<String>()
        );
        var requestedPostLogoutRedirectURI = Objects.requireNonNullElseGet(
                registeredClient.getPostLogoutRedirectUris(),
                ()-> new HashSet<String>()
        );
        redirectRepository.saveAllBindings(
                registeredClientId,
                requestedRedirectURI,
                requestedPostLogoutRedirectURI
        );
    }


}
