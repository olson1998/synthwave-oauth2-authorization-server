package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserPassword;

public interface PasswordRepository {

    void save(UserPassword userPassword);

    String encode(String password);
}
