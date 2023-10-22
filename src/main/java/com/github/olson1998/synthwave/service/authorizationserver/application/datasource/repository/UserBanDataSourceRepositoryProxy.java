package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserBanDataSourceRepositoryProxy {

    private final UserBanJpaRepository userBanJpaRepository;
}
