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

    private final RedirectDataSourceRepository redirectDataSourceRepository;

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
        var redirectURISet = registeredClient.getRedirectUris();
        var postLogoutRedirectURISet = registeredClient.getPostLogoutRedirectUris();
        var redirectEntities = redirectDataSourceRepository.getRedirectByRedirectAndPostLogoutURISetAndAffiliation(
                redirectURISet,
                postLogoutRedirectURISet,
                userMetadata.getCompanyCode(),
                userMetadata.getDivision()
        );
        registeredClientSettingsDataSourceRepository.save(registeredClientSettings);
        authorizationGrantTypeBindDataSourceRepository.saveAll(authorizationGrantTypes);
        clientAuthenticationMethodBindDataSourceRepository.saveAll(clientAuthenticationMethodBounds);
    }

}
