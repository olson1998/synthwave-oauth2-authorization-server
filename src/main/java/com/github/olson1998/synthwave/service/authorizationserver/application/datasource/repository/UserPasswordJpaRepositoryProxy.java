package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.UserPasswordData;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.UserPasswordDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.PasswordEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserPasswordJpaRepositoryProxy implements UserPasswordDataSourceRepository {

    private final UserPasswordJpaRepository userPasswordJpaRepository;

    @Override
    public void save(PasswordEntity passwordEntity) {
        var data = new UserPasswordData(passwordEntity);
        userPasswordJpaRepository.save(data);
    }
}
