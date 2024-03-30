package com.github.olson1998.synthwave.service.authorizationserver.domain.service.user;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.user.UserPasswordModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.user.UserPasswordDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.user.UserPassword;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.user.repository.UserPasswordRepository;
import lombok.RequiredArgsConstructor;
import org.joda.time.MutableDateTime;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class UserPasswordService implements UserPasswordRepository {

    private final PasswordEncoder passwordEncoder;

    private final UserPasswordDataSourceRepository userPasswordDataSourceRepository;

    @Override
    public void saveNewUserPassword(UserPassword userPassword) {
        UserPasswordModel userPasswordModel;
        if(userPassword instanceof UserPasswordModel model) {
            userPasswordModel = model;
        } else {
            userPasswordModel = new UserPasswordModel(userPassword);
        }
        var encryptedValue = passwordEncoder.encode(userPasswordModel.getValue());
        userPasswordModel.setValue(encryptedValue);
        userPasswordModel.setCreatedOn(MutableDateTime.now());
        userPasswordDataSourceRepository.save(userPassword);
    }

    @Override
    public void updateUserPassword(UserPassword userPassword) {
        var affectedRows = userPasswordDataSourceRepository.updateActivePasswordNotActiveByUserId(userPassword.getUserId());
        if(affectedRows != 1) {
            throw new DataIntegrityViolationException("Multiple active passwords for user: " + userPassword.getUserId());
        }
        saveNewUserPassword(userPassword);
    }

}
