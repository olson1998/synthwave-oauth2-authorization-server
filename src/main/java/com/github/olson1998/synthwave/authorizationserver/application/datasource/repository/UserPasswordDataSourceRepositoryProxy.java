package com.github.olson1998.synthwave.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.authorizationserver.domain.port.oauth2.datasource.repository.UserPasswordDataSourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserPasswordDataSourceRepositoryProxy implements UserPasswordDataSourceRepository {

    private final UserPasswordJpaRepository userPasswordJpaRepository;
}
