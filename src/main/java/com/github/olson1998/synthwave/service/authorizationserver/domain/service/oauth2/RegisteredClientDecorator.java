package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.AuthorizationGrantTypeRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.ClientAuthenticationMethodRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RegisteredClientProperties;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.RedirectRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

@RequiredArgsConstructor(access = AccessLevel.MODULE)
class RegisteredClientDecorator {

    private final RedirectRepository redirectRepository;

    private final AuthorizationGrantTypeRepository authorizationGrantTypeRepository;

    private final ClientAuthenticationMethodRepository clientAuthenticationMethodRepository;

    private final RegisteredClientMapper registeredClientMapper = new RegisteredClientMapper();

    RegisteredClient decorateRegisteredClient(RegisteredClientProperties registeredClientProperties){
        var id = registeredClientProperties.getId();
        var redirectEntities = redirectRepository.getRedirectByRegisteredClientId(id);
        var authorizationGrantTypes =
                authorizationGrantTypeRepository.getAuthorizationGrantTypesByRegisteredClientId(id);
        var clientAuthenticationMethods =
                clientAuthenticationMethodRepository.getClientAuthenticationMethodByRegisteredClientId(id);
        var config = registeredClientProperties
                .withRedirectEntities(redirectEntities)
                .withAuthorizationGrantTypes(authorizationGrantTypes)
                .withClientAuthenticationMethods(clientAuthenticationMethods);
        return registeredClientMapper.map(config);
    }
}
