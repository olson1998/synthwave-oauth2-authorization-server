package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.user;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.user.ApplicationUserData;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.user.ApplicationUserDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.user.ApplicationUser;
import com.github.olson1998.synthwave.support.hibernate.util.AffectedRows;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.joda.time.MutableDateTime;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ApplicationUserJpaRepositoryWrapper implements ApplicationUserDataSourceRepository {

    private final ApplicationUserJpaRepository applicationUserJpaRepository;

    @Override
    public List<ApplicationUser> getByExample(@NonNull ApplicationUser applicationUser, Integer limit) {
        var example = Example.of(
                mapToData(applicationUser),
                ExampleMatcher.matching().withIgnoreNullValues()
        );
        return Optional.ofNullable(limit)
                .map(limitValue -> {
                    var page = Pageable.ofSize(limit);
                    return applicationUserJpaRepository.findAll(example, page);
                }).map(page -> page.stream().toList())
                .orElseGet(()-> applicationUserJpaRepository.findAll(example))
                .stream()
                .map(ApplicationUser.class::cast)
                .toList();
    }

    @Override
    public ApplicationUser save(ApplicationUser applicationUser) {
        return applicationUserJpaRepository.save(mapToData(applicationUser));
    }

    @Override
    public AffectedRows  updateUser(ApplicationUser applicationUser) {
        var displayName = applicationUser.getDisplayName();
        var id = applicationUser.getId();
        var expireOn = applicationUser.getExpireOn();
        int affectedRows =0;
        if(displayName != null && expireOn != null && id !=null){
            affectedRows = applicationUserJpaRepository.updateApplicationUserDisplayNameExpireOnById(displayName, expireOn, id);
        } else if(displayName != null && expireOn == null && id !=null){
            affectedRows = applicationUserJpaRepository.updateApplicationUserDisplayNameById(displayName, id);
        } else if(displayName == null && expireOn != null && id !=null){
            affectedRows = applicationUserJpaRepository.updateApplicationUserExpireOnById(expireOn, id);
        }
        return new AffectedRows(affectedRows);
    }

    @Override
    public AffectedRows deleteUser(Long userId) {
        return new AffectedRows(applicationUserJpaRepository.deleteApplicationUserById(userId));
    }

    private ApplicationUserData mapToData(ApplicationUser applicationUser){
        return new ApplicationUserData(
                applicationUser.getId(),
                applicationUser.getUsername(),
                applicationUser.getDisplayName(),
                MutableDateTime.now(),
                applicationUser.getExpireOn()
        );
    }
}
