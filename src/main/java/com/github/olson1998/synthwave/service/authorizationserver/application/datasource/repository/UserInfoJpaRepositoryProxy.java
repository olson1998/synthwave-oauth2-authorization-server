package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.UserInfoDataSourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserInfoJpaRepositoryProxy implements UserInfoDataSourceRepository {

    private final UserInfoJpaRepository userInfoJpaRepository;
}
