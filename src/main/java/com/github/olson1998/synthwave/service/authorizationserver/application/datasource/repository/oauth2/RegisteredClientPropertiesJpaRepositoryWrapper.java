package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.RegisteredClientData;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.RegisteredClientDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.RegisteredClientProperties;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.query.AbstractRegisteredClientBuilderWrapper;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.query.SearchRegisteredClient;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.joda.time.MutableDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RegisteredClientPropertiesJpaRepositoryWrapper implements RegisteredClientDataSourceRepository {

    private final RegisteredClientPropertiesJpaRepository registeredClientPropertiesJpaRepository;

    @Override
    public Page<? extends RegisteredClientProperties> searchRegisteredClient(SearchRegisteredClient searchRegisteredClient) {
        Specification<RegisteredClientData> registeredClientDataSpec =
                ((root, query, criteriaBuilder) -> createRegisteredClientPredicate(root, query, criteriaBuilder, searchRegisteredClient));
        Pageable pageable = PageRequest.of(
                searchRegisteredClient.getPage(),
                searchRegisteredClient.getPageSize(),
                Sort.by("id")
        );
        return registeredClientPropertiesJpaRepository.findAll(registeredClientDataSpec, pageable);
    }

    @Override
    public Optional<? extends AbstractRegisteredClientBuilderWrapper> findRegisteredClientByClientIdWithTimestamp(String clientId, MutableDateTime timestamp) {
        return registeredClientPropertiesJpaRepository.selectPropertiesByClientId(clientId, timestamp);
    }

    @Override
    public Optional<RegisteredClient.Builder> findRegisteredClientByIdWithTimestamp(Long id, MutableDateTime timestamp) {
        return registeredClientPropertiesJpaRepository.selectPropertiesByRegisteredClientId(id, timestamp)
                .map(RegisteredClient.Builder.class::cast);
    }

    @Override
    public RegisteredClientProperties save(RegisteredClientProperties registeredClientProperties) {
        var data = new RegisteredClientData(registeredClientProperties);
        return registeredClientPropertiesJpaRepository.save(data);
    }

    private Predicate createRegisteredClientPredicate(Root<RegisteredClientData> root,
                                                      CriteriaQuery<?> query,
                                                      CriteriaBuilder criteriaBuilder,
                                                      SearchRegisteredClient searchRegisteredClient) {
        Predicate registeredClientPredicate = criteriaBuilder.isNotNull(root.get("id"));
        if(searchRegisteredClient.getRegisteredClientId() != null) {
            Predicate isEqId = criteriaBuilder.equal(root.get("id"), searchRegisteredClient.getRegisteredClientId());
            registeredClientPredicate = criteriaBuilder.and(registeredClientPredicate, isEqId);
        }
        if(searchRegisteredClient.getClientIdPattern() != null) {
            Predicate isLikeClientIdPattern = criteriaBuilder.like(root.get("clientId"), searchRegisteredClient.getClientIdPattern());
            registeredClientPredicate = criteriaBuilder.and(registeredClientPredicate, isLikeClientIdPattern);
        }
        if(searchRegisteredClient.getNamePattern() != null) {
            Predicate isLikeClientNamePattern = criteriaBuilder.like(root.get("name"), searchRegisteredClient.getNamePattern());
            registeredClientPredicate = criteriaBuilder.and(registeredClientPredicate, isLikeClientNamePattern);
        }
        MutableDateTime timestamp = searchRegisteredClient.getTimestamp();
        if(searchRegisteredClient.isFilterExpired()) {
            Predicate hasNoExpirationTime = criteriaBuilder.isNull(root.get("expireOn"));
            Predicate hasExpireTime = criteriaBuilder.isNotNull(root.get("expireOn"));
            Predicate isExpirationBeforeTimestamp = criteriaBuilder.greaterThan(root.get("expireOn"), timestamp);
            Predicate isBeforeExpireTime = criteriaBuilder.and(
                    hasExpireTime,
                    isExpirationBeforeTimestamp
            );
            Predicate isNotExpired = criteriaBuilder.or(
                    hasNoExpirationTime,
                    isBeforeExpireTime
            );
            registeredClientPredicate = criteriaBuilder.and(registeredClientPredicate, isNotExpired);
        }
        if(searchRegisteredClient.isFilterNonActive()) {
            Predicate hasNoActivationTime = criteriaBuilder.isNull((root.get("activeFrom")));
            Predicate hasActivationTime = criteriaBuilder.isNotNull(root.get("activeFrom"));
            Predicate isActivationBeforeTimestamp = criteriaBuilder.lessThan(root.get("activeFrom"), timestamp);
            Predicate isAfterActivationTime = criteriaBuilder.and(
                    hasActivationTime,
                    isActivationBeforeTimestamp
            );
            Predicate isActive = criteriaBuilder.or(
                    hasNoActivationTime,
                    isAfterActivationTime
            );
            registeredClientPredicate = criteriaBuilder.and(registeredClientPredicate, isActive);
        }
        return registeredClientPredicate;
    }

}
