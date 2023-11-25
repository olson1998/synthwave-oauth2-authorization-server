package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.*;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.PostLoginRedirect;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.PostLogoutRedirect;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.RedirectBoundModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.RegisteredClientEntityModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.*;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectBound;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.provisioning.RegisteredClientProvisioningRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Redirect;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
public class DefaultRegisteredClientProvisioningService implements RegisteredClientProvisioningRepository {

    private final RedirectDataSourceRepository redirectUrisDataSourceRepository;

    private final RedirectBoundDataSourceRepository redirectBoundDataSourceRepository;

    private final UserDataSourceRepository userDataSourceRepository;

    private final RegisteredClientDataSourceRepository registeredClientDataSourceRepository;

    private final RegisteredClientSettingsDataSourceRepository registeredClientSettingsDataSourceRepository;

    private final AuthorizationGrantTypeBindDataSourceRepository authorizationGrantTypeBindDataSourceRepository;

    private final ClientAuthenticationMethodBindDataSourceRepository clientAuthenticationMethodBindDataSourceRepository;

    private final AuthorizationGrantTypesBoundMapper authorizationGrantTypesBoundMapper = new AuthorizationGrantTypesBoundMapper();

    private final ClientAuthenticationMethodBoundMapper clientAuthenticationMethodBoundMapper = new ClientAuthenticationMethodBoundMapper();

    @Override
    public void provision(RegisteredClient registeredClient) {
        var username = Objects.requireNonNull(
                registeredClient.getClientName(),
                "Client id is required property of registered client"
        );
        var userMetadata = userDataSourceRepository.getUserMetadataByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: '%s' has not been found".formatted(username)));
        log.debug("Provisioning of user: {}", userMetadata);
        var clientId = registeredClient.getClientId();
        if(clientId.equals("{?}")){
            clientId = username + "@synthwave." + userMetadata.getCompanyCode().toLowerCase() +
                    userMetadata.getDivision().toLowerCase() + ".com";
        }
        var userId = userMetadata.getUserId();
        var registeredClientProps = new RegisteredClientEntityModel(userId, clientId);
        var registeredClientEntity = registeredClientDataSourceRepository.save(registeredClientProps);
        var registeredClientId = registeredClientEntity.getId();
        var authorizationGrantTypes = authorizationGrantTypesBoundMapper.mapToAuthorizationGrantTypeBindings(
                registeredClientId,
                registeredClient.getAuthorizationGrantTypes()
        );
        var clientSettings = registeredClient.getClientSettings();
        var registeredClientSettings = new RegisteredClientSettingsModel(
                registeredClientId,
                clientSettings.isRequireProofKey(),
                clientSettings.isRequireAuthorizationConsent()
        );
        var clientAuthenticationMethodBounds = clientAuthenticationMethodBoundMapper.map(
                registeredClientId,
                registeredClient.getClientAuthenticationMethods()
        );
        var presentRedirectURI = redirectUrisDataSourceRepository.getRedirectURICollectionByRedirectURISetAndPostLogoutRedirectURISet(
                registeredClient.getRedirectUris(),
                registeredClient.getPostLogoutRedirectUris()
        );
        var toPersistURI = resolveNotPresentRedirectURI(
                presentRedirectURI,
                registeredClient.getRedirectUris(),
                registeredClient.getPostLogoutRedirectUris()
        );
        var persistedURI = redirectUrisDataSourceRepository.saveAll(toPersistURI);
        var redirectURIIds = resolveRedirectURIId(
                Stream.concat(presentRedirectURI.stream(), persistedURI.stream()).toList()
        );
        var redirectURIBindings = createRedirectURIBindingSet(redirectURIIds, registeredClientId);
        registeredClientSettingsDataSourceRepository.save(registeredClientSettings);
        redirectBoundDataSourceRepository.saveAll(redirectURIBindings);
        authorizationGrantTypeBindDataSourceRepository.saveAll(authorizationGrantTypes);
        clientAuthenticationMethodBindDataSourceRepository.saveAll(clientAuthenticationMethodBounds);
    }

    private Set<RedirectBound> createRedirectURIBindingSet(Collection<TSID> redirectURIIds, TSID registeredClientId){
        return redirectURIIds.stream()
                .map(id -> new RedirectBoundModel(id, registeredClientId))
                .collect(Collectors.toSet());
    }

    private Set<TSID> resolveRedirectURIId(Collection<RedirectEntity> redirectEntityCollection){
        return redirectEntityCollection.stream()
                .map(RedirectEntity::getId)
                .collect(Collectors.toSet());
    }

    private Set<Redirect> resolveNotPresentRedirectURI(Collection<RedirectEntity> redirectURIEntities, Set<String> redirectURI, Set<String> postLogoutURI){
        var toPersist = new HashSet<Redirect>();
        redirectURI.stream()
                .map(Optional::ofNullable)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(uri -> redirectURIEntities.stream().noneMatch(entity -> entity.getUri().equals(uri) && entity.isPostLogin()))
                .forEach(uriValue ->{
                    var data = new PostLoginRedirect(uriValue);
                    toPersist.add(data);
                });
        postLogoutURI.stream()
                .map(Optional::ofNullable)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(uri -> redirectURIEntities.stream().noneMatch(entity -> entity.getUri().equals(uri) && entity.isPostLogout()))
                .forEach(uriValue ->{
                    var data = new PostLogoutRedirect(uriValue);
                    toPersist.add(data);
                });
        return toPersist;
    }

}
