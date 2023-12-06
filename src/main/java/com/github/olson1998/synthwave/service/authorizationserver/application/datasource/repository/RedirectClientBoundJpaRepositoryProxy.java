package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.RedirectClientBoundData;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.RedirectClientBoundDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectClientBound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class RedirectClientBoundJpaRepositoryProxy implements RedirectClientBoundDataSourceRepository {

    private final RedirectClientBoundJpaRepository redirectClientBoundJpaRepository;

    @Override
    public void saveAll(Collection<RedirectClientBound> redirectClientBoundCollection) {
        var data = redirectClientBoundCollection.stream()
                .map(RedirectClientBoundData::new)
                .toList();
        redirectClientBoundJpaRepository.saveAll(data);
    }
}
