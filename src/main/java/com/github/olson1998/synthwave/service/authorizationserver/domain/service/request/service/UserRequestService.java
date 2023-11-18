package com.github.olson1998.synthwave.service.authorizationserver.domain.service.request.service;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.request.UserActivationToken;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.repository.UserPropertiesRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.repository.UserRequestRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.stereotype.UserSchema;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
public class UserRequestService implements UserRequestRepository {

    private static final String ACTIVATION_TOKEN_FIELD = "activation_token";

    private final UserPropertiesRepository userPropertiesRepository;

    @Override
    public Map<String, String> saveUser(UserSchema userSchema) {
        var user = userPropertiesRepository.saveUserSchema(userSchema);
        var activationToken = new UserActivationToken(user);
        return Collections.singletonMap(ACTIVATION_TOKEN_FIELD, activationToken.toString());
    }

    @Override
    public Void activateUser(String activationToken) {
        var token = UserActivationToken.fromString(activationToken);
        var userId = token.getUserId();
        var user = userPropertiesRepository.getUserById(userId);
        var verifyToken = new UserActivationToken(user);
        if(verifyToken.toString().equals(activationToken)){
            userPropertiesRepository.activateUser(userId);
        }
        return null;
    }

    @Override
    public Void deactivateUser(TSID userId) {
        userPropertiesRepository.deactivateUser(userId);
        return null;
    }

}
