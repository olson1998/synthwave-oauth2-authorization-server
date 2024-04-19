package com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.AuthorizationGrantTypeDatasourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.ClientAuthenticationMethodDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.RegisteredClientSecretDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.ScopeDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.RedirectUri;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.AuthorizationServerStartupProvisioning;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.EntityProvider;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.OAuth2RegisteredClientRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.RedirectRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2.AuthorizationServerStartupProvisioningService;
import com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2.FileEntityProvider;
import com.github.olson1998.synthwave.support.springbootstarter.jackson.config.ObjectMapperInit;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.io.File;
import java.util.Collection;

@Configuration
@AutoConfigureAfter(ObjectMapperInit.class)
public class OAuth2ProvisioningConfig {

    public static final String REDIRECT_URI_ENTITY_PROVIDER_BEAN = "redirectUriEntityProvider";

    public static final String POST_LOGOUT_REDIRECT_URI_ENTITY_PROVIDER_BEAN = "postLogoutRedirectUriEntityProvider";

    public static final String REGISTERED_CLIENT_ENTITY_PROVIDER_BEAN = "registeredClientEntityProvider";

    @Bean(REDIRECT_URI_ENTITY_PROVIDER_BEAN)
    public EntityProvider<Collection<RedirectUri>> redirectUriEntityProvider(@Value("#{T(org.springframework.util.ResourceUtils).getFile(environment['synthwave.service.authorizationserver.oauth2.provisioning.redirect-uri.file'])}") File configFile,
                                                                             ObjectMapper objectMapper) {
        return new FileEntityProvider<>(objectMapper, configFile, new TypeReference<>() {
        });
    }

    @Bean(POST_LOGOUT_REDIRECT_URI_ENTITY_PROVIDER_BEAN)
    public EntityProvider<Collection<RedirectUri>> postLogoutRedirectUriEntityProvider(@Value("#{T(org.springframework.util.ResourceUtils).getFile(environment['synthwave.service.authorizationserver.oauth2.provisioning.post-logout-redirect-uri.file'])}") File configFile,
                                                                             ObjectMapper objectMapper) {
        return new FileEntityProvider<>(objectMapper, configFile, new TypeReference<>() {
        });
    }

    @Bean(REGISTERED_CLIENT_ENTITY_PROVIDER_BEAN)
    public EntityProvider<Collection<RegisteredClient>> registeredClientEntityProvider(@Value("#{T(org.springframework.util.ResourceUtils).getFile(environment['synthwave.service.authorizationserver.oauth2.provisioning.registered-client.file'])}") File configFile,
                                                                                       ObjectMapper objectMapper) {
        return new FileEntityProvider<>(objectMapper, configFile, new TypeReference<>() {
        });
    }

    @Bean
    public AuthorizationServerStartupProvisioning authorizationServerStartupProvisioning(@Qualifier(REDIRECT_URI_ENTITY_PROVIDER_BEAN) EntityProvider<Collection<RedirectUri>> redirectUriEntityProvider,
                                                                                         @Qualifier(POST_LOGOUT_REDIRECT_URI_ENTITY_PROVIDER_BEAN) EntityProvider<Collection<RedirectUri>> postLogoutRedirectUriEntityProvider,
                                                                                         EntityProvider<Collection<RegisteredClient>> registeredClientEntityProvider,
                                                                                         OAuth2RegisteredClientRepository oAuth2RegisteredClientRepository,
                                                                                         RedirectRepository redirectRepository,
                                                                                         RegisteredClientSecretDataSourceRepository registeredClientSecretDataSourceRepository,
                                                                                         ScopeDataSourceRepository scopeDataSourceRepository,
                                                                                         AuthorizationGrantTypeDatasourceRepository authorizationGrantTypeDatasourceRepository,
                                                                                         ClientAuthenticationMethodDataSourceRepository clientAuthenticationMethodDataSourceRepository) {
        return new AuthorizationServerStartupProvisioningService(
                redirectUriEntityProvider,
                postLogoutRedirectUriEntityProvider,
                registeredClientEntityProvider,
                oAuth2RegisteredClientRepository,
                redirectRepository,
                registeredClientSecretDataSourceRepository,
                scopeDataSourceRepository,
                authorizationGrantTypeDatasourceRepository,
                clientAuthenticationMethodDataSourceRepository
        );
    }


}
