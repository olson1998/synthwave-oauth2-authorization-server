package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.RedirectURIBindData;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.RedirectURIBindDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RedirectURIBind;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class RedirectURIBindJpaRepositoryProxy implements RedirectURIBindDataSourceRepository {

    private final RedirectURIBindJpaRepository redirectURIBindJpaRepository;

    @Override
    public void saveAll(@NonNull Collection<RedirectURIBind> redirectURIBindCollection) {
        var data = redirectURIBindCollection.stream()
                .map(RedirectURIBindData::new)
                .toList();
        redirectURIBindJpaRepository.saveAll(data);
    }
}
