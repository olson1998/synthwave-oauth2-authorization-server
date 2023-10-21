package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.data.repository.UserDataSourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDataSourceRepositoryProxy implements UserDataSourceRepository {

    private final UserJpaRepository userJpaRepository;
}
