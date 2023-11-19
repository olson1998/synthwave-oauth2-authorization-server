package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Password;

public interface PasswordRepository {

    void save(Password password);
}
