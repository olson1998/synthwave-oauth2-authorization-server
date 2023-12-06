package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.PostLoginRedirect;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.PostLogoutRedirect;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.RedirectClientBoundModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.RedirectClientBoundDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.RedirectDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectClientBound;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.RedirectRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Redirect;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Map.entry;

@Slf4j

@RequiredArgsConstructor
public class RedirectService implements RedirectRepository {

    private final RedirectDataSourceRepository redirectDataSourceRepository;

    private final RedirectClientBoundDataSourceRepository redirectClientBoundDataSourceRepository;

    @Override
    public Collection<RedirectEntity> getRedirectByRegisteredClientId(TSID registeredClientId) {
        return redirectDataSourceRepository.getRedirectByRegisteredClientId(registeredClientId);
    }

    @Override
    public Collection<RedirectEntity> saveAll(Collection<Redirect> redirectsCollection) {
        var redirectMap = createRedirectMap(redirectsCollection);
        var redirectEntities = redirectDataSourceRepository.getRedirectFromURISet(
                redirectMap.get(PostLoginRedirect.class),
                redirectMap.get(PostLogoutRedirect.class)
        );
        var notPresentRedirects = createNotPresentRedirectSet(redirectMap, redirectEntities);
        return redirectDataSourceRepository.saveAll(notPresentRedirects);
    }

    @Override
    public void saveAllBindings(TSID registeredClientId, Set<String> redirectURISet, Set<String> postLogoutRedirectURISet) {
        var redirectEntities = redirectDataSourceRepository.getRedirectFromURISet(
                redirectURISet,
                postLogoutRedirectURISet
        );
        var redirectBindings = createRedirectClientBindingSet(registeredClientId, redirectEntities);
        redirectClientBoundDataSourceRepository.saveAll(redirectBindings);
    }

    private Set<RedirectClientBound> createRedirectClientBindingSet(TSID registeredClientId, Collection<RedirectEntity> redirectEntities){
        return redirectEntities.stream()
                .map(redirectEntity -> new RedirectClientBoundModel(redirectEntity.getId(), registeredClientId))
                .collect(Collectors.toSet());
    }

    private Map<Class<? extends Redirect>, Set<String>> createRedirectMap(Collection<Redirect> redirectCollection){
        var redirectURISet = new HashSet<String>();
        var postLogoutRedirectURISet = new HashSet<String>();
        for(Redirect redirect : redirectCollection){
            var uri = redirect.getUri();
            if(redirect.isPostLogin()){
                redirectURISet.add(uri);
            }else if (redirect.isPostLogout()){
                postLogoutRedirectURISet.add(uri);
            }
        }
        return Map.ofEntries(
                entry(PostLoginRedirect.class, redirectURISet),
                entry(PostLogoutRedirect.class, postLogoutRedirectURISet)
        );
    }

    private Set<Redirect> createNotPresentRedirectSet(Map<Class<? extends Redirect>, Set<String>> redirectsToPersist, Collection<RedirectEntity> presentEntities){
        return redirectsToPersist.entrySet().stream()
                .map(redirectEntry -> mapToNotPresentRedirectEntry(redirectEntry, presentEntities))
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    private Set<Redirect> mapToNotPresentRedirectEntry(Map.Entry<Class<? extends Redirect>, Set<String>> redirectEntry,
                                                       Collection<RedirectEntity> presentEntities){
        var type = redirectEntry.getKey();
        var uriSet = redirectEntry.getValue();
        return uriSet.stream()
                .filter(uri -> isRedirectPresent(uri, type, presentEntities))
                .map(uri -> mapToRedirect(uri, type))
                .collect(Collectors.toSet());
    }

    private boolean isRedirectPresent(String uri, Class<? extends Redirect> redirectClass, Collection<RedirectEntity> presentEntities){
        return presentEntities.stream()
                .anyMatch(redirectEntity -> isEqualRedirect(redirectEntity, redirectClass, uri));
    }

    private boolean isEqualRedirect(RedirectEntity redirectEntity, Class<? extends Redirect> redirectClass, String uri){
        boolean sameType;
        if(redirectClass.equals(PostLoginRedirect.class)){
            sameType =redirectEntity.isPostLogin();
        } else if (redirectClass.equals(PostLogoutRedirect.class)) {
            sameType = redirectEntity.isPostLogout();
        }else {
            throw new UnknownError();
        }
        return sameType && redirectEntity.getUri().equals(uri);
    }

    private Redirect mapToRedirect(String uri, Class<? extends Redirect> redirectClass){
        if(redirectClass.equals(PostLoginRedirect.class)){
            return new PostLoginRedirect(uri);
        } else if (redirectClass.equals(PostLogoutRedirect.class)) {
            return new PostLogoutRedirect(uri);
        }else {
            throw new UnknownError();
        }
    }

}
