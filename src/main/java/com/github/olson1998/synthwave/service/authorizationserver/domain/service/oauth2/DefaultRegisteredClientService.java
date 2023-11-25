package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.AuthorizationGrantTypeBindDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.ClientAuthenticationMethodBindDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.RedirectDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.RegisteredClientDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.provisioning.RegisteredClientProvisioningRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.SynthWaveRegisteredClientRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RegisteredClientConfig;
import io.hypersistence.tsid.TSID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

@Slf4j
@RequiredArgsConstructor
public class DefaultRegisteredClientService implements SynthWaveRegisteredClientRepository {

    private final RegisteredClientProvisioningRepository registeredClientProvisioningRepository;

    private final RedirectDataSourceRepository redirectUrisDataSourceRepository;

    private final RegisteredClientDataSourceRepository registeredClientDataSourceRepository;

    private final AuthorizationGrantTypeBindDataSourceRepository authorizationGrantTypeBindDataSourceRepository;

    private final ClientAuthenticationMethodBindDataSourceRepository clientAuthenticationMethodBindDataSourceRepository;

    private final RegisteredClientMapper registeredClientMapper = new RegisteredClientMapper();

    @Override
    public void save(@NonNull RegisteredClient registeredClient) {
        registeredClientProvisioningRepository.provision(registeredClient);
    }

    @Override
    public RegisteredClient findById(String id) {
        log.debug("Searching client: '%s'".formatted(id));
        var longId = Long.getLong(id);
        var tsid = TSID.from(longId);
        return registeredClientDataSourceRepository.getRegisteredClientConfigByRegisteredClientId(tsid)
                .map(this::appendRegisteredClientConfigs)
                .map(registeredClientMapper::map)
                .orElse(null);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        log.debug("Searching client: '%s'".formatted(clientId));
        return registeredClientDataSourceRepository.getRegisteredClientConfigByClientId(clientId)
                .map(this::appendRegisteredClientConfigs)
                .map(registeredClientMapper::map)
                .orElse(null);
    }

    private RegisteredClientConfig appendRegisteredClientConfigs(RegisteredClientConfig registeredClientConfig){
        var id = registeredClientConfig.getId();
        var redirectURI =
                redirectUrisDataSourceRepository.getRedirectURIByRegisteredClientId(id);
        var authorizationGrantTypes =
                authorizationGrantTypeBindDataSourceRepository.getAuthorizationGrantTypesByRegisteredClientId(id);
        var clientAuthenticationMethods =
                clientAuthenticationMethodBindDataSourceRepository.getClientAuthenticationMethodsByRegisteredClientId(id);
        return registeredClientConfig
                .withRedirectUris(redirectURI)
                .withAuthorizationGrantTypes(authorizationGrantTypes)
                .withClientAuthenticationMethods(clientAuthenticationMethods);
    }

}
