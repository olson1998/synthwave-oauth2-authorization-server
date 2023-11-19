package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.properties.RegistrationClientProperties;
import io.hypersistence.tsid.TSID;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.Optional;
import java.util.Set;

import static org.springframework.security.oauth2.core.AuthorizationGrantType.CLIENT_CREDENTIALS;
import static org.springframework.security.oauth2.core.ClientAuthenticationMethod.CLIENT_SECRET_BASIC;

public class RegistrationClientService implements RegistrationClientRepository {

    private static final Set<String> REGISTRATION_CLIENT_SCOPES = Set.of(
            "client.create",
            "client.read"
    );

    private final RegisteredClient registrationClient;

    public RegistrationClientService(PasswordEncoder passwordEncoder,
                                     RegistrationClientProperties registrationClientProperties) {
        var encodedSecret = passwordEncoder.encode(registrationClientProperties.getClientSecret());
        this.registrationClient = RegisteredClient.withId(TSID.fast().toString())
                .clientId(registrationClientProperties.getClientId())
                .clientSecret(encodedSecret)
                .clientAuthenticationMethod(CLIENT_SECRET_BASIC)
                .authorizationGrantTypes(authorizationGrantTypes -> authorizationGrantTypes.add(CLIENT_CREDENTIALS))
                .scopes(scopes -> scopes.addAll(REGISTRATION_CLIENT_SCOPES))
                .build();
    }

    @Override
    public RegisteredClient getRegistrationClient(String clientId) {
        return Optional.of(registrationClient)
                .filter(registeredClient -> registeredClient.getClientId().equals(clientId))
                .map(RegisteredClient::from)
                .map(registrationClientBuilder -> registrationClientBuilder.id(TSID.fast().toString()))
                .map(RegisteredClient.Builder::build)
                .orElse(null);
    }
}
