package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.AuthoritiesBindingData;
import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.embeddable.AuthoritiesBindingValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface AuthoritiesBindingJpaRepository extends JpaRepository<AuthoritiesBindingData, AuthoritiesBindingValue> {
}
