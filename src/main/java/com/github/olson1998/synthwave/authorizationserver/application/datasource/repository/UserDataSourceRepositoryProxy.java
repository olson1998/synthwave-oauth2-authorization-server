package com.github.olson1998.synthwave.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.authorizationserver.domain.port.oauth2.datasource.repository.UserDataSourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDataSourceRepositoryProxy implements UserDataSourceRepository {

    private final UserJpaRepository userJpaRepository;
}
