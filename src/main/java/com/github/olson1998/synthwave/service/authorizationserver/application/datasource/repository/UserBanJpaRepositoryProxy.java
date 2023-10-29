package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserBanJpaRepositoryProxy {

    private final UserBanJpaRepository userBanJpaRepository;
}
