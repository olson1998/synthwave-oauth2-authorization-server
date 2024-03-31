package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.role;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.role.RoleBindingData;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.role.RoleBindingDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.role.RoleBinding;
import lombok.RequiredArgsConstructor;
import org.joda.time.MutableDateTime;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class RoleBindingJpaRepositoryWrapper implements RoleBindingDataSourceRepository {

    private final RoleBindingJpaRepository roleBindingJpaRepository;

    @Override
    public void saveAll(Collection<? extends RoleBinding> roleBoundsCollection) {
        var data = roleBoundsCollection.stream()
                .map(RoleBindingData::new)
                .peek(roleBindingData -> roleBindingData.setCreatedOn(MutableDateTime.now()))
                .toList();
        roleBindingJpaRepository.saveAll(data);
    }

    @Override
    public int deleteRoleById(Collection<Long> idCollection) {
        return roleBindingJpaRepository.deleteRoleById(idCollection);
    }

    @Override
    public int deleteRoleByUserIdAndRoleId(Long userId, Collection<Long> idCollection) {
        return roleBindingJpaRepository.deleteRoleByUserIdAndRoleId(userId, idCollection);
    }

}
