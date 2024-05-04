package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RegisteredClientExtendedProperties;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface OAuth2RegisteredClientRepository extends RegisteredClientRepository {

    Collection<? extends RegisteredClientExtendedProperties> searchRegisteredClientBases(Long id,
                                                                                         String clientIdPatter,
                                                                                         String clientNamePattern,
                                                                                         Collection<ClientAuthenticationMethod> clientAuthenticationMethods,
                                                                                         Collection<AuthorizationGrantType> authorizationGrantTypes);

    @Override
    @Transactional(rollbackFor = Exception.class)
    void save(RegisteredClient registeredClient);

    @Transactional(rollbackFor = Exception.class)
    void delete(Long registeredClientId);
}
