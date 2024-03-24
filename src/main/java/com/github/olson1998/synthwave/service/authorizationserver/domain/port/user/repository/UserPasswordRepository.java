package com.github.olson1998.synthwave.service.authorizationserver.domain.port.user.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.user.UserPassword;

public interface UserPasswordRepository {

    void saveNewUserPassword(UserPassword userPassword);

    void updateUserPassword(UserPassword userPassword);

}
