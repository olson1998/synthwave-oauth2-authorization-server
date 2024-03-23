package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.role;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.role.RoleBindingData;
import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.role.embbedable.RoleBindingValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface RoleBindingJpaRepository extends JpaRepository<RoleBindingData, RoleBindingValue> {
}
