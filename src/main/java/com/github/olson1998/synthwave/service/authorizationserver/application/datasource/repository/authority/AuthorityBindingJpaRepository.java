package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.authority;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.authority.AuthorityBindingData;
import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.authority.embbedable.AuthorityBindingValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface AuthorityBindingJpaRepository extends JpaRepository<AuthorityBindingData, AuthorityBindingValue> {


}
