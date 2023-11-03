package com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.stereotype.UserSchema;
import io.hypersistence.tsid.TSID;

import java.util.Map;

public interface UserRequestRepository {

    Map<String, String> saveUser(UserSchema userSchema);

    Void activateUser(String activationToken);

    Void  deactivateUser(TSID userId);
}
