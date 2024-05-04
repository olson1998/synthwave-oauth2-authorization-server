package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.RegisteredClientProperties;
import org.joda.time.MutableDateTime;
import org.springframework.data.domain.Page;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.transaction.annotation.Transactional;

public interface OAuth2RegisteredClientRepository extends RegisteredClientRepository {

    Page<? extends RegisteredClientProperties> searchRegisteredClient(Long id,
                                                                      String clientIdPatter,
                                                                      String clientNamePattern,
                                                                      MutableDateTime timestamp,
                                                                      boolean filterExpired,
                                                                      boolean filterNonActive,
                                                                      int pageSize,
                                                                      int page);

    @Override
    @Transactional(rollbackFor = Exception.class)
    void save(RegisteredClient registeredClient);

    @Transactional(rollbackFor = Exception.class)
    void delete(Long registeredClientId);
}
