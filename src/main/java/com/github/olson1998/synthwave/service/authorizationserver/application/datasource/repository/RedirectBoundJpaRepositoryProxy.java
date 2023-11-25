package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.RedirectBoundData;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.RedirectBoundDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectBound;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class RedirectBoundJpaRepositoryProxy implements RedirectBoundDataSourceRepository {

    private final RedirectBoundJpaRepository redirectBoundJpaRepository;

    @Override
    public void saveAll(@NonNull Collection<RedirectBound> redirectURIBindCollection) {
        var data = redirectURIBindCollection.stream()
                .map(RedirectBoundData::new)
                .toList();
        redirectBoundJpaRepository.saveAll(data);
    }
}
