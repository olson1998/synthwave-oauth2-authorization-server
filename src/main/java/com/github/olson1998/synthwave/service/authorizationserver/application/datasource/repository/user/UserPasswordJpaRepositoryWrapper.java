package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.user;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.user.UserPasswordData;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.user.UserPasswordDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.user.UserPassword;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserPasswordJpaRepositoryWrapper implements UserPasswordDataSourceRepository {

    private final UserPasswordJpaRepository userPasswordJpaRepository;

    @Override
    public void save(UserPassword userPassword) {
        var data = new UserPasswordData(userPassword);
        userPasswordJpaRepository.save(data);
    }

    @Override
    public int updateActivePasswordNotActiveByUserId(Long userId) {
        return userPasswordJpaRepository.updatePasswordActiveByUserId(userId);
    }

}
