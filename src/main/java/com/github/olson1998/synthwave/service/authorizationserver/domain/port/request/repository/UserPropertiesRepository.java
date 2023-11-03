package com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.UserEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.stereotype.UserSchema;
import io.hypersistence.tsid.TSID;
import org.springframework.transaction.annotation.Transactional;

public interface UserPropertiesRepository {

    boolean existsUserWithUsername(String username);

    UserEntity getUserById(TSID id);

    @Transactional
    UserEntity saveUserSchema(UserSchema userSchema);

    @Transactional
    void activateUser(TSID userId);

    @Transactional
    void deactivateUser(TSID userId);

}
