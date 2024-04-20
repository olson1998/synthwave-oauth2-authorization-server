package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.RedirectUriBindingModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.RedirectUriModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.RedirectUriDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.RedirectUri;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.UriBinding;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.RedirectRepository;
import lombok.RequiredArgsConstructor;
import org.joda.time.MutableDateTime;

import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

@RequiredArgsConstructor
public class RedirectService implements RedirectRepository {

    private final RedirectUriDataSourceRepository redirectUriDataSourceRepository;

    @Override
    public Collection<? extends RedirectUri> getRedirectUriByExample(Collection<? extends RedirectUri> redirectUriExamples) {
        return redirectUriDataSourceRepository.getRedirectUriByExamples(redirectUriExamples);
    }

    @Override
    public Collection<? extends RedirectUri> getPostLogoutRedirectUriByExample(Collection<? extends RedirectUri> redirectUriExamples) {
        return redirectUriDataSourceRepository.getPostLogoutRedirectUriByExamples(redirectUriExamples);
    }

    @Override
    public Collection<String> getPostLogoutRedirectUriByRegisteredClientId(Long registeredClientId) {
        return redirectUriDataSourceRepository.getPostLogoutRedirectUriByRegisteredClientId(registeredClientId);
    }

    @Override
    public Collection<String> getRedirectUriByRegisteredClientIdWithTimestamp(Long registeredClientId, MutableDateTime timestamp) {
        return redirectUriDataSourceRepository.getRedirectUriByRegisteredClientIdWithTimestamp(registeredClientId, timestamp);
    }

    @Override
    public Collection<? extends RedirectUri> saveAllRedirectUri(Collection<? extends RedirectUri> redirectUriCollection) {
        return redirectUriDataSourceRepository.saveAll(redirectUriCollection).stream()
                .map(RedirectUriModel::new)
                .toList();
    }

    @Override
    public Collection<? extends RedirectUri> saveAllPostLogoutRedirectUri(Collection<? extends RedirectUri> redirectUriCollection) {
       return redirectUriDataSourceRepository.saveAllPostLogout(redirectUriCollection).stream()
               .map(RedirectUriModel::new)
               .toList();
    }

    @Override
    public void deleteRedirectUri(String query) {

    }

    @Override
    public void deletePostLogoutRedirectUri(String query) {

    }

    @Override
    public Collection<? extends RedirectUri> saveAllRedirect(Collection<? extends RedirectUri> redirectUriCollection) {
        var redirectCollection = redirectUriCollection.stream()
                .map(redirectUri -> {
                    RedirectUriModel model;
                    if(redirectUri instanceof RedirectUriModel redirectUriModel) {
                        model = redirectUriModel;
                    } else {
                        model = new RedirectUriModel(redirectUri);
                    }
                    model.setCreatedOn(MutableDateTime.now());
                    return model;
                }).toList();
        return redirectUriDataSourceRepository.saveAll(redirectCollection);
    }

    @Override
    public Collection<? extends RedirectUri> saveAllPostLogoutRedirect(Collection<? extends RedirectUri> redirectUriCollection) {
        var redirectCollection = redirectUriCollection.stream()
                .map(redirectUri -> {
                    RedirectUriModel model;
                    if(redirectUri instanceof RedirectUriModel redirectUriModel) {
                        model = redirectUriModel;
                    } else {
                        model = new RedirectUriModel(redirectUri);
                    }
                    model.setCreatedOn(MutableDateTime.now());
                    return model;
                }).toList();
        return redirectUriDataSourceRepository.saveAllPostLogout(redirectCollection);
    }

    @Override
    public void saveAllRedirectBounds(Collection<? extends RedirectUri> redirectUriCollection, Long registeredClientId) {
        saveRedirectUris(
                registeredClientId,
                redirectUriCollection,
                redirectUriDataSourceRepository::getRedirectUriByExamples,
                redirectUriDataSourceRepository::saveAllRedirectBounds
        );
    }

    @Override
    public void saveAllPostLogoutRedirectBounds(Collection<? extends RedirectUri> redirectUriCollection, Long registeredClientId) {
        saveRedirectUris(
                registeredClientId,
                redirectUriCollection,
                redirectUriDataSourceRepository::getPostLogoutRedirectUriByExamples,
                redirectUriDataSourceRepository::saveAllPostLogoutRedirectBounds
        );
    }

    private RedirectUri eraseIrrelevantData(RedirectUri redirectUri) {
        RedirectUriModel redirectUriModel;
        if(redirectUri instanceof RedirectUriModel model) {
            redirectUriModel = model;
        } else {
            redirectUriModel = new RedirectUriModel(redirectUri);
        }
        redirectUriModel.setCreatedOn(null);
        redirectUriModel.setExpireOn(null);
        return redirectUriModel;
    }

    private void saveRedirectUris(Long registeredClientId,
                                  Collection<? extends RedirectUri> redirectUriCollection,
                                  Function<Collection<? extends RedirectUri>, Collection<? extends RedirectUri>> existingDataSupplier,
                                  Consumer<Collection<? extends UriBinding>> provisioningConsumer) {
        var examples = redirectUriCollection.stream()
                .map(this::eraseIrrelevantData)
                .toList();
        var existingRedirect = existingDataSupplier.apply(examples);
        if(areAllMatchingExampleRedirectUri(existingRedirect, redirectUriCollection)) {
            var bounds = existingRedirect.stream()
                    .map(RedirectUri::getId)
                    .map(id -> new RedirectUriBindingModel(registeredClientId, id))
                    .toList();
            provisioningConsumer.accept(bounds);
        } else {
            throw new IllegalArgumentException("");
        }
    }

    private boolean areAllMatchingExampleRedirectUri(Collection<? extends RedirectUri> dataCollection, Collection<? extends RedirectUri> examples) {
        if(dataCollection.size() != examples.size()) {
            return false;
        } else {
            return examples.stream()
                    .allMatch(example -> dataCollection.stream().anyMatch(data -> isMatchingExampleRedirectUri(data, example)));
        }
    }

    private boolean isMatchingExampleRedirectUri(RedirectUri redirectUri, RedirectUri example) {
        if(example.getId() != null && example.getValue() != null) {
            return redirectUri.getId().equals(example.getId()) &&
                    redirectUri.getValue().equals(example.getValue());
        } else if (example.getId() != null) {
            return redirectUri.getId().equals(example.getId());
        } else if (example.getValue() != null) {
            return redirectUri.getValue().equals(example.getValue());
        } else {
            return false;
        }
    }

}
