package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.role;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.role.RoleData;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.role.RoleDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.role.Role;
import lombok.RequiredArgsConstructor;
import org.joda.time.MutableDateTime;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class RoleJpaRepositoryWrapper implements RoleDataSourceRepository {

    private final RoleJpaRepository roleJpaRepository;

    @Override
    public String[] getActiveRoleNamesByUserId(Long userId) {
        return roleJpaRepository.selectActiveRoleNamesByUserId(userId, MutableDateTime.now());
    }

    @Override
    public Collection<Long> getRoleIdByExample(Collection<? extends Role> roleExamples) {
        var dataExamples = roleExamples.stream()
                .map(RoleData::new)
                .map(roleData -> Example.of(roleData,  ExampleMatcher.matching().withIgnoreNullValues()))
                .toList();
        return roleJpaRepository.selectRoleIdByExamples(dataExamples);
    }

    @Override
    public Collection<? extends Role> saveAll(Collection<? extends Role> roleCollection) {
        var data = roleCollection.stream()
                .map(RoleData::new)
                .toList();
        return roleJpaRepository.saveAll(data);
    }
}
