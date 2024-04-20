package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.PostLogoutRedirectUriBindingData;
import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.PostLogoutRedirectUriData;
import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.RedirectUriBindingData;
import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.RedirectUriData;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.RedirectUriDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.RedirectUri;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.UriBinding;
import com.github.olson1998.synthwave.support.jpa.spec.JpaSpecificationUtil;
import lombok.RequiredArgsConstructor;
import org.joda.time.MutableDateTime;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RedirectUriJpaRepositoryWrapper implements RedirectUriDataSourceRepository {

    private final RedirectUriJpaRepository redirectUriJpaRepository;

    private final PostLogoutRedirectUriJpaRepository postLogoutRedirectUriJpaRepository;

    private final RedirectUriBindingJpaRepository redirectUriBindingJpaRepository;

    private final PostLogoutRedirectUriBindingJpaRepository postLogoutRedirectUriBindingJpaRepository;

    @Override
    public Collection<? extends RedirectUri> getRedirectUriByExamples(Collection<? extends RedirectUri> redirectUriExamples) {
        var redirectUriDataExampleChain = createRedirectUriDataExampleChain(redirectUriExamples);
        return redirectUriJpaRepository.findAll(redirectUriDataExampleChain);
    }

    @Override
    public Collection<? extends RedirectUri> getPostLogoutRedirectUriByExamples(Collection<? extends RedirectUri> redirectUriExamples) {
        var redirectUriDataExampleChain = createPostLogoutRedirectUriDataExampleChain(redirectUriExamples);
        return postLogoutRedirectUriJpaRepository.findAll(redirectUriDataExampleChain);
    }

    @Override
    public Collection<String> getPostLogoutRedirectUriByRegisteredClientId(Long registeredClientId) {
        return postLogoutRedirectUriJpaRepository.selectPostLogoutRedirectUriByRegisteredClientId(registeredClientId);
    }

    @Override
    public Collection<String> getRedirectUriByRegisteredClientIdWithTimestamp(Long registeredClientId, MutableDateTime timestamp) {
        return redirectUriJpaRepository.selectRedirectUriByRegisteredClientId(registeredClientId, timestamp);
    }

    @Override
    public Collection<? extends RedirectUri> saveAll(Collection<? extends RedirectUri> redirectUriCollection) {
        var data = redirectUriCollection.stream()
                .map(RedirectUriData::new)
                .toList();
        return redirectUriJpaRepository.saveAll(data);
    }

    @Override
    public Collection<? extends RedirectUri> saveAllPostLogout(Collection<? extends RedirectUri> postLogoutRedirectUriCollection) {
        var data = postLogoutRedirectUriCollection.stream()
                .map(PostLogoutRedirectUriData::new)
                .toList();
        return postLogoutRedirectUriJpaRepository.saveAll(data);
    }

    @Override
    public void saveAllRedirectBounds(Collection<? extends UriBinding> redirectUriCollection) {
        var data = redirectUriCollection.stream()
                .map(RedirectUriBindingData::new)
                .toList();
        redirectUriBindingJpaRepository.saveAll(data);
    }

    @Override
    public void saveAllPostLogoutRedirectBounds(Collection<? extends UriBinding> postLogoutRedirectUriCollection) {
        var data = postLogoutRedirectUriCollection.stream()
                .map(PostLogoutRedirectUriBindingData::new)
                .toList();
        postLogoutRedirectUriBindingJpaRepository.saveAll(data);
    }

    @Override
    public int deleteRedirectUriBoundsById(Long redirectId) {
        return 0;
    }

    @Override
    public int deletePostLogoutRedirectUriBoundsById(Long redirectId) {
        return 0;
    }

    @Override
    public int deleteRedirectBoundsByIdAndRegisteredClientId(Collection<Long> idCollection, Long registeredClientId) {
        return redirectUriBindingJpaRepository.deleteRedirectByIdAndRegisteredClientId(idCollection, registeredClientId);
    }

    @Override
    public int deletePostLogoutRedirectBoundsByIdAndRegisteredClientId(Collection<Long> idCollection, Long registeredClientId) {
        return postLogoutRedirectUriBindingJpaRepository.deletePostLogoutRedirectUriByIdAndRegisteredClientId(idCollection, registeredClientId);
    }

    private Specification<RedirectUriData> createRedirectUriDataExampleChain(Collection<? extends RedirectUri> redirectUriExampleCollection) {
        return redirectUriExampleCollection.stream()
                .map(RedirectUriData::new)
                .collect(Collectors.collectingAndThen(Collectors.toList(), JpaSpecificationUtil::createDataExamplesSpecChain));
    }

    private Specification<PostLogoutRedirectUriData> createPostLogoutRedirectUriDataExampleChain(Collection<? extends RedirectUri> redirectUriExampleCollection) {
        return redirectUriExampleCollection.stream()
                .map(PostLogoutRedirectUriData::new)
                .collect(Collectors.collectingAndThen(Collectors.toList(), JpaSpecificationUtil::createDataExamplesSpecChain));
    }

}
