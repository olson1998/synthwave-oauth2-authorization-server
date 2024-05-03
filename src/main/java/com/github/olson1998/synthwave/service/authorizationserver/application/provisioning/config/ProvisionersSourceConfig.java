package com.github.olson1998.synthwave.service.authorizationserver.application.provisioning.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.RedirectUri;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.provisioning.repository.ProvisionerSource;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.user.stereotype.ApplicationUserDetails;
import com.github.olson1998.synthwave.service.authorizationserver.domain.service.provisioning.ConfigFileProvisionerSource;
import com.github.olson1998.synthwave.support.springbootstarter.jackson.config.ObjectMapperInit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.io.File;
import java.util.Collection;

@Configuration
@AutoConfigureAfter(ObjectMapperInit.class)
public class ProvisionersSourceConfig {

    public static final String APPLICATION_USER_DETAILS_PROVISIONER_SOURCE_BEAN = "applicationUserDetailsProvisionerSource";

    public static final String REDIRECT_URI_PROVISIONER_SOURCE_BEAN = "redirectUriProvisionerSource";

    public static final String POST_LOGOUT_REDIRECT_PROVISIONER_SOURCE_BEAN = "postLogoutRedirectUriProvisionerSource";

    public static final String REGISTERED_CLIENT_PROVISIONER_SOURCE_BEAN = "registeredClientProvisionerSource";

    @Bean(REDIRECT_URI_PROVISIONER_SOURCE_BEAN)
    public ProvisionerSource<Collection<RedirectUri>> redirectUriProvisionerSource(@Value("#{T(org.springframework.util.ResourceUtils).getFile(environment['synthwave.service.authorizationserver.oauth2.provisioning.redirect-uri.file'])}") File configFile,
                                                                                ObjectMapper objectMapper) {
        return new ConfigFileProvisionerSource<>(objectMapper, configFile, new TypeReference<>() {
        });
    }

    @Bean(POST_LOGOUT_REDIRECT_PROVISIONER_SOURCE_BEAN)
    public ProvisionerSource<Collection<RedirectUri>> postLogoutRedirectUriProvisionerSource(@Value("#{T(org.springframework.util.ResourceUtils).getFile(environment['synthwave.service.authorizationserver.oauth2.provisioning.post-logout-redirect-uri.file'])}") File configFile,
                                                                                          ObjectMapper objectMapper) {
        return new ConfigFileProvisionerSource<>(objectMapper, configFile, new TypeReference<>() {
        });
    }

    @Bean(REGISTERED_CLIENT_PROVISIONER_SOURCE_BEAN)
    public ProvisionerSource<Collection<RegisteredClient>> registeredClientProvisionerSource(@Value("#{T(org.springframework.util.ResourceUtils).getFile(environment['synthwave.service.authorizationserver.oauth2.provisioning.registered-client.file'])}") File configFile,
                                                                                             ObjectMapper objectMapper) {
        return new ConfigFileProvisionerSource<>(objectMapper, configFile, new TypeReference<>() {
        });
    }

    @Bean(APPLICATION_USER_DETAILS_PROVISIONER_SOURCE_BEAN)
    public ProvisionerSource<Collection<ApplicationUserDetails>> applicationUserDetailsProvisionerSource(@Value("#{T(org.springframework.util.ResourceUtils).getFile(environment['synthwave.service.authorizationserver.user.provisioning.details.file'])}") File configFile,
                                                                                                         ObjectMapper objectMapper) {
        return new ConfigFileProvisionerSource<>(objectMapper, configFile, new TypeReference<Collection<ApplicationUserDetails>>() {
        });
    }


}
