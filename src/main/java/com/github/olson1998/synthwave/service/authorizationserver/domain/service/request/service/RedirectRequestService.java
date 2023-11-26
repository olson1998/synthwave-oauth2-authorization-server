package com.github.olson1998.synthwave.service.authorizationserver.domain.service.request.service;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.RedirectDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Redirect;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.repository.RedirectRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.util.Map.entry;

@Slf4j

@RequiredArgsConstructor
public class RedirectRequestService implements RedirectRepository {

    private final RedirectDataSourceRepository redirectDataSourceRepository;

    @Override
    public Collection<RedirectEntity> saveAll(@NonNull Collection<Redirect> redirectsCollection) {
        var resolveRedirects = resolveRedirectScopes(redirectsCollection);
        var redirects = resolveRedirects.get(1);
        var postLogoutRedirects = resolveRedirects.get(2);
        var presentEntities = redirectDataSourceRepository.getRedirectByRedirectAndPostLogoutURISet(
                redirects,
                postLogoutRedirects
        );
        if(!presentEntities.isEmpty()){
            log.warn("Redirects: {} already present", presentEntities);
        }
        var toPersist = resolveRedirectToPersist(redirectsCollection, presentEntities);
        return redirectDataSourceRepository.saveAll(toPersist);
    }

    private Map<Integer, Set<String>> resolveRedirectScopes(Collection<Redirect> redirectCollection){
        var redirects = new HashSet<String>();
        var postLogoutRedirects = new HashSet<String>();
        redirectCollection.forEach(redirect -> {
            var uri = redirect.getUri();
            if(redirect.isPostLogout()){
                postLogoutRedirects.add(uri);
            } else if (redirect.isPostLogin()) {
                redirects.add(uri);
            }
        });
        return Map.ofEntries(
                entry(1, redirects),
                entry(2, postLogoutRedirects)
        );
    }

    private Collection<Redirect> resolveRedirectToPersist(Collection<Redirect> redirectsCollection, Collection<RedirectEntity> presentEntities){
        return redirectsCollection.stream()
                .filter(redirect -> presentEntities.stream().anyMatch(redirectEntity -> isRedirectEntityMatching(redirectEntity, redirect)))
                .toList();
    }

    private boolean isRedirectEntityMatching(RedirectEntity redirectEntity, Redirect redirect){
        return redirectEntity.getUri().equals(redirect.getUri()) &&
                (redirectEntity.isPostLogin() && redirect.isPostLogout()) ||
                (redirectEntity.isPostLogout() && redirect.isPostLogout());
    }
}
