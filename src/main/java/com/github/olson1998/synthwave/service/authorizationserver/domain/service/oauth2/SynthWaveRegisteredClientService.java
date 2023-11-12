package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.AuthorizationGrantTypeBindDTO;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.ClientAuthenticationMethodBindDTO;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.PostLoginRedirectURI;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.PostLogoutRedirectURI;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.SynthWaveRegisteredClient;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.*;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.AuthorizationGrantTypeBinding;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.ClientAuthenticationMethodBinding;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RedirectURI;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.RegisteredClientMapper;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.RegistrationClientRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.SynthWaveRegisteredClientRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RegisteredClientConfig;
import io.hypersistence.tsid.TSID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class SynthWaveRegisteredClientService implements SynthWaveRegisteredClientRepository {

    private final RegisteredClientMapper registeredClientMapper;

    private final RegistrationClientRepository registrationClientRepository;

    private final RedirectURIDataSourceRepository redirectUrisDataSourceRepository;

    private final UserPropertiesDataSourceRepository userPropertiesDataSourceRepository;

    private final RegisteredClientPropertiesSourceRepository registeredClientPropertiesSourceRepository;

    private final AuthorizationGrantTypeBindDataSourceRepository authorizationGrantTypeBindDataSourceRepository;

    private final ClientAuthenticationMethodBindDataSourceRepository clientAuthenticationMethodBindDataSourceRepository;

    @Override
    public void save(@NonNull RegisteredClient registeredClient) {
        var username = Objects.requireNonNull(
                registeredClient.getClientName(),
                "Client id is required property of registered client"
        );
        var userProps = userPropertiesDataSourceRepository.getUserPropertiesByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: '%s' has not been found".formatted(username)));
        var userId = userProps.getId();
        String clientId;
        var optionalClientId = registeredClientPropertiesSourceRepository.getClientIdByUserId(userId);
        if(optionalClientId.isPresent()){
            throw new IllegalStateException("User has already registered client: '%s'".formatted(optionalClientId.get()));
        }else {
            clientId = userProps.getUsername() + "@synthwave.com";
        }
        var redirectUris = registeredClient.getRedirectUris();
        var postLogoutRedirectUris = registeredClient.getPostLogoutRedirectUris();
        var registeredClientProps = new SynthWaveRegisteredClient(userId, clientId);
        var concatRedirectUri = concatRedirectUris(redirectUris, postLogoutRedirectUris);
        var registeredClientEntity = registeredClientPropertiesSourceRepository.save(registeredClientProps);
        var registeredClientId = registeredClientEntity.getId();
        var clientAuthenticationMethods =
                mapToClientAuthenticationMethodBind(registeredClientId, registeredClient.getClientAuthenticationMethods());
        var authorizationGrantTypes =
                mapToAuthorizationGrantTypeBindings(registeredClientId, registeredClient.getAuthorizationGrantTypes());
        clientAuthenticationMethodBindDataSourceRepository.saveAll(clientAuthenticationMethods);
        authorizationGrantTypeBindDataSourceRepository.saveAll(authorizationGrantTypes);

    }

    @Override
    public RegisteredClient findById(String id) {
        var longId = Long.getLong(id);
        var tsid = TSID.from(longId);
        return registeredClientPropertiesSourceRepository.getRegisteredClientConfigByRegisteredClientId(tsid)
                .map(this::appendRegisteredClientConfigs)
                .map(registeredClientMapper::map)
                .orElse(null);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return Optional.ofNullable(registrationClientRepository.getRegistrationClient(clientId))
                .orElseGet(()-> registeredClientPropertiesSourceRepository.getRegisteredClientConfigByClientId(clientId)
                        .map(this::appendRegisteredClientConfigs)
                        .map(registeredClientMapper::map)
                        .orElse(null));
    }

    private RegisteredClientConfig appendRegisteredClientConfigs(RegisteredClientConfig registeredClientConfig){
        var clientId = registeredClientConfig.getRegisteredClientId();
        var divi = registeredClientConfig.getDivision();
        var code = registeredClientConfig.getCompanyCode();
        var redirectURI =
                redirectUrisDataSourceRepository.getRedirectURIByRegisteredClientIdCompanyCodeAndDivision(clientId, code, divi);
        var authorizationGrantTypes =
                authorizationGrantTypeBindDataSourceRepository.getAuthorizationGrantTypesByRegisteredClientId(clientId);
        var clientAuthenticationMethods =
                clientAuthenticationMethodBindDataSourceRepository.getClientAuthenticationMethodsByRegisteredClientId(clientId);
        return registeredClientConfig
                .withRedirectUris(redirectURI)
                .withAuthorizationGrantTypes(authorizationGrantTypes)
                .withClientAuthenticationMethods(clientAuthenticationMethods);
    }

    private Set<RedirectURI> concatRedirectUris(Set<String> redirectUris, Set<String> postLogoutRedirectUris){
        var redirectUrisStream = redirectUris.stream()
                .map(PostLoginRedirectURI::new)
                .map(RedirectURI.class::cast);
        var postLogoutRedirectUrisStream = postLogoutRedirectUris.stream()
                .map(PostLogoutRedirectURI::new)
                .map(RedirectURI.class::cast);
        return Stream.concat(redirectUrisStream, postLogoutRedirectUrisStream)
                .collect(Collectors.toSet());
    }

    private List<AuthorizationGrantTypeBinding> mapToAuthorizationGrantTypeBindings(TSID registeredClientId, Collection<AuthorizationGrantType> authorizationGrantTypes){
        return authorizationGrantTypes.stream()
                .map(authorizationGrantType -> new AuthorizationGrantTypeBindDTO(registeredClientId, authorizationGrantType))
                .map(AuthorizationGrantTypeBinding.class::cast)
                .toList();
    }

    private List<ClientAuthenticationMethodBinding> mapToClientAuthenticationMethodBind(TSID registeredClientId, Collection<ClientAuthenticationMethod> clientAuthenticationMethods){
        return clientAuthenticationMethods.stream()
                .map(clientAuthenticationMethod -> new ClientAuthenticationMethodBindDTO(registeredClientId, clientAuthenticationMethod))
                .map(ClientAuthenticationMethodBinding.class::cast)
                .toList();
    }

}
