package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.UserPhoneDataSourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserPhoneJpaRepositoryProxy implements UserPhoneDataSourceRepository {

    private final UserPhoneJpaRepository userPhoneJpaRepository;
}
