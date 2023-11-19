package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.RedirectURIBindingData;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.RedirectURIBindingDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectURIBinding;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class RedirectURIBindingJpaRepositoryProxy implements RedirectURIBindingDataSourceRepository {

    private final RedirectURIBindingJpaRepository redirectURIBindingJpaRepository;

    @Override
    public void saveAll(@NonNull Collection<RedirectURIBinding> redirectURIBindCollection) {
        var data = redirectURIBindCollection.stream()
                .map(RedirectURIBindingData::new)
                .toList();
        redirectURIBindingJpaRepository.saveAll(data);
    }
}
