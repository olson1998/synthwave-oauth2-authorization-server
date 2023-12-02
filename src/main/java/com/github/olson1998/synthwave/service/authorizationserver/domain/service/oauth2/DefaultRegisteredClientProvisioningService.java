package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.AffiliationEntityModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.RegisteredClientSecretModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.RegisteredClientSettingsModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.UserMetadaModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.PasswordModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.RegisteredClientEntityModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.RegisteredClientTokenSettingsModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.UserPropertiesModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.*;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.AffiliationRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.PasswordRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.provisioning.RegisteredClientProvisioningRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.AbstractSynthWaveRegisteredClient;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RegisteredClientSecret;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserMetadata;
import com.github.olson1998.synthwave.support.joda.converter.JavaInstantConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class DefaultRegisteredClientProvisioningService implements RegisteredClientProvisioningRepository {

    private final PasswordRepository passwordRepository;

    private final AffiliationRepository affiliationRepository;

    private final UserDataSourceRepository userDataSourceRepository;

    private final RegisteredClientDataSourceRepository registeredClientDataSourceRepository;

    private final RegisteredClientSecretDataSourceRepository registeredClientSecretDataSourceRepository;

    private final RegisteredClientSettingsDataSourceRepository registeredClientSettingsDataSourceRepository;

    private final RegisteredClientTokenSettingsDataSourceRepository registeredClientTokenSettingsDataSourceRepository;

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
        var userMetadata = userDataSourceRepository.getUserMetadataByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: '%s' has not been found".formatted(username)));
        var clientSecret = new RegisteredClientSecretModel(
                null,
                registeredClient.getClientSecret(),
                new JavaInstantConverter(registeredClient.getClientSecretExpiresAt()).toMutableDateTime()
        );
        saveRegisteredClient(registeredClient,clientSecret, userMetadata);
        return registeredClient;
    }

    @Override
    public void provisionSynthWaveClient(AbstractSynthWaveRegisteredClient synthWaveRegisteredClient) {
        var username = synthWaveRegisteredClient.getClientName();
        var userMetadata = userDataSourceRepository.getUserMetadataByUsername(username)
                .orElseGet(()-> saveNewUser(synthWaveRegisteredClient));
        saveRegisteredClient(synthWaveRegisteredClient, synthWaveRegisteredClient.getRegisteredClientSecret(), userMetadata);
    }

    private void saveRegisteredClient(RegisteredClient registeredClient,
                                      RegisteredClientSecret registeredClientSecret,
                                      UserMetadata userMetadata){
        var clientId = registeredClient.getClientId();
        var username = userMetadata.getUsername();
        if(clientId.equals("{?}")){
            clientId = username + "@synthwave." + userMetadata.getCompanyCode().toLowerCase() +
                    userMetadata.getDivision().toLowerCase() + ".com";
        }
        var userId = userMetadata.getUserId();
        var registeredClientProps = new RegisteredClientEntityModel(userId, clientId);
        var registeredClientEntity = registeredClientDataSourceRepository.save(registeredClientProps);
        var registeredClientId = registeredClientEntity.getId();
        var authorizationGrantTypes = authorizationGrantTypesBoundMapper.mapToAuthorizationGrantTypeBindings(
                registeredClientId,
                registeredClient.getAuthorizationGrantTypes()
        );
        var clientSecret = new RegisteredClientSecretModel(
                registeredClientId,
                registeredClientSecret.getValue(),
                registeredClientSecret.getExpiresDateTime()
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
        var clientAuthenticationMethodBounds = clientAuthenticationMethodBoundMapper.map(
                registeredClientId,
                registeredClient.getClientAuthenticationMethods()
        );
        registeredClientSecretDataSourceRepository.save(clientSecret);
        registeredClientSettingsDataSourceRepository.save(registeredClientSettings);
        registeredClientTokenSettingsDataSourceRepository.save(clientTokenSettings);
        authorizationGrantTypeBindDataSourceRepository.saveAll(authorizationGrantTypes);
        clientAuthenticationMethodBindDataSourceRepository.saveAll(clientAuthenticationMethodBounds);
    }

    private UserMetadata saveNewUser(AbstractSynthWaveRegisteredClient synthWaveRegisteredClient){
        var username = synthWaveRegisteredClient.getClientName();
        var password = synthWaveRegisteredClient.getUserPassword();
        var companyCode = synthWaveRegisteredClient.getCompanyCode();
        var division = synthWaveRegisteredClient.getDivision();
        var userProps = new UserPropertiesModel(
                username,
                true,
                null
        );
        var userEntity = userDataSourceRepository.save(userProps);
        var userId = userEntity.getId();
        var affiliation = new AffiliationEntityModel(userId, companyCode, division);
        affiliationRepository.save(affiliation);
        var passwordObj = new PasswordModel(password.getValue(), userId, password.getExpirePeriod());
        passwordRepository.save(passwordObj);
        return new UserMetadaModel(userId, username, companyCode, division);
    }

}
