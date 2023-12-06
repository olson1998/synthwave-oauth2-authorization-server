package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.RegisteredClientSecretModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.RegisteredClientSettingsModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.ClientSecretModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.OAuth2ClientModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.RegisteredClientTokenSettingsModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.RegisteredClientDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.RegisteredClientSecretDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.RegisteredClientSettingsDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.RegisteredClientTokenSettingsDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RegisteredClientSettings;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RegisteredClientTokenSettings;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.AuthorizationGrantTypeRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.ClientAuthenticationMethodRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.RegisteredClientRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.UserDetailsRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.*;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.RedirectRepository;
import com.github.olson1998.synthwave.support.joda.converter.JavaInstantConverter;
import io.hypersistence.tsid.TSID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class RegisteredClientService implements RegisteredClientRepository {

    private final PasswordEncoder passwordEncoder;

    private final UserDetailsRepository userDetailsRepository;

    private final RedirectRepository redirectRepository;

    private final ClientAuthenticationMethodRepository clientAuthenticationMethodRepository;

    private final AuthorizationGrantTypeRepository authorizationGrantTypeRepository;

    private final RegisteredClientDataSourceRepository registeredClientDataSourceRepository;

    private final RegisteredClientSecretDataSourceRepository registeredClientSecretDataSourceRepository;

    private final RegisteredClientSettingsDataSourceRepository registeredClientSettingsDataSourceRepository;

    private final RegisteredClientTokenSettingsDataSourceRepository registeredClientTokenSettingsDataSourceRepository;

    private final RegisteredClientDecorator registeredClientDecorator;

    public RegisteredClientService(PasswordEncoder passwordEncoder,
                                   UserDetailsRepository userDetailsRepository,
                                   RedirectRepository redirectRepository,
                                   ClientAuthenticationMethodRepository clientAuthenticationMethodRepository,
                                   AuthorizationGrantTypeRepository authorizationGrantTypeRepository,
                                   RegisteredClientDataSourceRepository registeredClientDataSourceRepository,
                                   RegisteredClientSecretDataSourceRepository registeredClientSecretDataSourceRepository,
                                   RegisteredClientSettingsDataSourceRepository registeredClientSettingsDataSourceRepository,
                                   RegisteredClientTokenSettingsDataSourceRepository registeredClientTokenSettingsDataSourceRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsRepository = userDetailsRepository;
        this.redirectRepository = redirectRepository;
        this.clientAuthenticationMethodRepository = clientAuthenticationMethodRepository;
        this.authorizationGrantTypeRepository = authorizationGrantTypeRepository;
        this.registeredClientDataSourceRepository = registeredClientDataSourceRepository;
        this.registeredClientSecretDataSourceRepository = registeredClientSecretDataSourceRepository;
        this.registeredClientSettingsDataSourceRepository = registeredClientSettingsDataSourceRepository;
        this.registeredClientTokenSettingsDataSourceRepository = registeredClientTokenSettingsDataSourceRepository;
        this.registeredClientDecorator = new RegisteredClientDecorator(
                redirectRepository,
                authorizationGrantTypeRepository,
                clientAuthenticationMethodRepository
        );
    }

    @Override
    public Optional<RegisteredClient> findRegisteredClientById(TSID id) {
        return registeredClientDataSourceRepository.getRegisteredClientConfigByRegisteredClientId(id)
                .map(registeredClientDecorator::decorateRegisteredClient);
    }

    @Override
    public Optional<RegisteredClient> findRegisteredClientByClientId(String clientId) {
        return registeredClientDataSourceRepository.getRegisteredClientConfigByClientId(clientId)
                .map(registeredClientDecorator::decorateRegisteredClient);
    }

    @Override
    public RegisteredClient saveRegisteredClient(RegisteredClient registeredClient) {
        if(registeredClient instanceof AbstractSynthWaveRegisteredClient synthWaveRegisteredClient){
            saveSynthWaveRegisteredClient(synthWaveRegisteredClient);
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
        saveRegisteredClient(registeredClient, clientSecret, userMetadata);
        return registeredClient;
    }

    @Override
    public AbstractSynthWaveRegisteredClient saveSynthWaveRegisteredClient(AbstractSynthWaveRegisteredClient synthWaveRegisteredClient) {
        var username = synthWaveRegisteredClient.getClientName();
        var userMetadata = userDetailsRepository.getUserMetadataByUsername(username)
                .orElseGet(()-> userDetailsRepository.saveUser(synthWaveRegisteredClient));
        saveRegisteredClient(synthWaveRegisteredClient, synthWaveRegisteredClient.getClientSecretObject(), userMetadata);
        return synthWaveRegisteredClient;
    }

    private void saveClientSecret(RegisteredClientSecret registeredClientSecret) {
        registeredClientSecretDataSourceRepository.save(registeredClientSecret);
    }

    private void saveClientSettings(RegisteredClientSettings registeredClientSettings) {
        registeredClientSettingsDataSourceRepository.save(registeredClientSettings);
    }

    private void saveTokenSettings(RegisteredClientTokenSettings registeredClientTokenSettings) {
        registeredClientTokenSettingsDataSourceRepository.save(registeredClientTokenSettings);
    }

    private void saveRegisteredClient(RegisteredClient registeredClient, ClientSecret clientSecret, UserMetadata userMetadata){
        var clientId = registeredClient.getClientId();
        var username = userMetadata.getUsername();
        if(registeredClientDataSourceRepository.existsRegisteredClientForUsername(username)){
            log.info("Registered client for user: '{}' already exists", username);
            return;
        }
        if(clientId.equals("{?}")){
            clientId = username + "@synthwave." + userMetadata.getCompanyCode().toLowerCase() +
                    userMetadata.getDivision().toLowerCase() + ".com";
        }
        var userId = userMetadata.getUserId();
        var clientModel = new OAuth2ClientModel(userId, clientId);
        var registeredClientId = registeredClientDataSourceRepository.save(clientModel)
                .getId();
        var registeredClientSecret = new RegisteredClientSecretModel(
                registeredClientId,
                encodeClientSecret(clientSecret)
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
        saveClientSecret(registeredClientSecret);
        saveClientSettings(registeredClientSettings);
        saveTokenSettings(clientTokenSettings);
        saveRedirectBindings(registeredClient, registeredClientId);
        clientAuthenticationMethodRepository.saveBindings(registeredClientId, registeredClient.getClientAuthenticationMethods());
        authorizationGrantTypeRepository.saveBindings(registeredClientId, registeredClient.getAuthorizationGrantTypes());;
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

    private ClientSecret encodeClientSecret(ClientSecret clientSecret) {
        var encodedSecret = passwordEncoder.encode(clientSecret.getValue());
        return new ClientSecretModel(encodedSecret, clientSecret.getExpiresDateTime());
    }

}
