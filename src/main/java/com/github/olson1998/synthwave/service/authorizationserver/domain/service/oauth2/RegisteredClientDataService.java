package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.RegisteredClientSecretModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.ClientSecretModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.RegisteredClientDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.RegisteredClientSecretDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.RegisteredClientSettingsDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.RegisteredClientTokenSettingsDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RegisteredClientSettings;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RegisteredClientTokenSettings;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.RegisteredClientRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.ClientSecret;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.OAuth2Client;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class RegisteredClientDataService implements RegisteredClientRepository {

    private final PasswordEncoder passwordEncoder;

    private final RegisteredClientDataSourceRepository registeredClientDataSourceRepository;

    private final RegisteredClientSecretDataSourceRepository registeredClientSecretDataSourceRepository;

    private final RegisteredClientSettingsDataSourceRepository registeredClientSettingsDataSourceRepository;

    private final RegisteredClientTokenSettingsDataSourceRepository registeredClientTokenSettingsDataSourceRepository;

    @Override
    public boolean isExistingRegisteredClientForUser(String username) {
        return registeredClientDataSourceRepository.existsRegisteredClientForUsername(username);
    }

    @Override
    public TSID saveRegisteredClient(OAuth2Client OAuth2Client) {
        var entity = registeredClientDataSourceRepository.save(OAuth2Client);
        return entity.getId();
    }

    @Override
    public void saveClientSecret(TSID registeredClientId, ClientSecret clientSecret) {
        var encodedSecret = encodeClientSecret(clientSecret);
        registeredClientSecretDataSourceRepository.save(new RegisteredClientSecretModel(registeredClientId, encodedSecret));
    }

    @Override
    public void saveClientSettings(RegisteredClientSettings registeredClientSettings) {
        registeredClientSettingsDataSourceRepository.save(registeredClientSettings);
    }

    @Override
    public void saveTokenSettings(RegisteredClientTokenSettings registeredClientTokenSettings) {
        registeredClientTokenSettingsDataSourceRepository.save(registeredClientTokenSettings);
    }

    private ClientSecret encodeClientSecret(ClientSecret clientSecret) {
        var encodedSecret = passwordEncoder.encode(clientSecret.getValue());
        return new ClientSecretModel(encodedSecret, clientSecret.getExpiresDateTime());
    }

}
