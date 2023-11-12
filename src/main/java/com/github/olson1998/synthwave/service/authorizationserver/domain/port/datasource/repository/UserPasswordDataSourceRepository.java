package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.PasswordEntity;

import java.util.Optional;

public interface UserPasswordDataSourceRepository {

    Optional<PasswordEntity> getUserPasswordValueByClientId(String clientId);

    void save(PasswordEntity passwordEntity);
}
