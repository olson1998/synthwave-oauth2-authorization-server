package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.PasswordEntity;

public interface UserPasswordDataSourceRepository {

    void save(PasswordEntity passwordEntity);
}
