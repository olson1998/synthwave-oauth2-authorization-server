package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.PostLogoutRedirectUriData;
import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.RedirectUriData;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.RedirectUriDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.RedirectUri;
import lombok.RequiredArgsConstructor;
import org.joda.time.MutableDateTime;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class RedirectUriJpaRepositoryWrapper implements RedirectUriDataSourceRepository {

    private final RedirectUriJpaRepository redirectUriJpaRepository;

    private final PostLogoutRedirectUriJpaRepository postLogoutRedirectUriJpaRepository;

    @Override
    public Collection<? extends RedirectUri> getRedirectUriByExamples(Collection<? extends RedirectUri> redirectUriExamples) {
        var examples = createRedirectDataExampleList(redirectUriExamples, RedirectUriData::new);
        return redirectUriJpaRepository.selectRedirectUriByExamples(examples);
    }

    @Override
    public Collection<? extends RedirectUri> getPostLogoutRedirectUriByExamples(Collection<? extends RedirectUri> redirectUriExamples) {
        var examples = createRedirectDataExampleList(redirectUriExamples, PostLogoutRedirectUriData::new);
        return postLogoutRedirectUriJpaRepository.selectPostLogoutRedirectUriByExamples(examples);
    }

    @Override
    public Set<String> getPostLogoutRedirectUriByRegisteredClientId(Long registeredClientId) {
        return postLogoutRedirectUriJpaRepository.selectPostLogoutRedirectUriByRegisteredClientId(registeredClientId);
    }

    @Override
    public Set<String> getRedirectUriByRegisteredClientIdWithTimestamp(Long registeredClientId, MutableDateTime timestamp) {
        return redirectUriJpaRepository.selectRedirectUriByRegisteredClientId(registeredClientId, timestamp);
    }

    private <T> List<Example<T>> createRedirectDataExampleList(Collection<? extends RedirectUri> redirectUriCollection, Function<RedirectUri, T> mapper){
        return redirectUriCollection.stream()
                .map(redirectUri -> createRedirectDataExample(redirectUri, mapper))
                .toList();
    }

    private Example<RedirectUriData> createRedirectUriDataExample(RedirectUri redirectUri) {
        return createRedirectDataExample(redirectUri, RedirectUriData::new);
    }

    private <T> Example<T> createRedirectDataExample(RedirectUri example, Function<RedirectUri, T> mapper) {
        var dataExample = mapper.apply(example);
        var matcher = ExampleMatcher.matchingAll()
                .withIgnoreNullValues();
        return Example.of(dataExample, matcher);
    }
}
