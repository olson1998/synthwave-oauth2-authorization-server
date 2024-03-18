package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.user;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.user.UserPassword;

public interface UserPasswordDataSourceRepository {

    void save(UserPassword userPassword);
}
