package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository;

import org.springframework.transaction.annotation.Transactional;

public interface AuthorizationServerStartupProvisioning {

    @Transactional(rollbackFor = Exception.class)
    void provisionOnStartup();

}
