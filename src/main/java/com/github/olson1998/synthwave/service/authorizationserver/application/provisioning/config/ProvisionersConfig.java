package com.github.olson1998.synthwave.service.authorizationserver.application.provisioning.config;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.AuthorizationGrantTypeDatasourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.ClientAuthenticationMethodDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.RegisteredClientSecretDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.ScopeDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.RedirectUri;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.OAuth2RegisteredClientRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.RedirectRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.provisioning.repository.Provisioner;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.provisioning.repository.ProvisionerSource;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.user.repository.ApplicationUserRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.user.stereotype.ApplicationUserDetails;
import com.github.olson1998.synthwave.service.authorizationserver.domain.service.provisioning.OAuth2ProvisioningService;
import com.github.olson1998.synthwave.service.authorizationserver.domain.service.provisioning.UserProvisioningService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.Collection;

import static com.github.olson1998.synthwave.service.authorizationserver.application.provisioning.config.ProvisionersSourceConfig.*;

@Configuration
public class ProvisionersConfig {

    @Bean
    public Provisioner userProvisioner(@Qualifier(APPLICATION_USER_DETAILS_PROVISIONER_SOURCE_BEAN) ProvisionerSource<Collection<ApplicationUserDetails>> applicationUserDetailsProvisionerSource,
                                           ApplicationUserRepository applicationUserRepository) {
        return new UserProvisioningService(applicationUserDetailsProvisionerSource, applicationUserRepository);
    }

    @Bean
    public Provisioner authorizationServerEntitiesProvisioner(@Qualifier(REDIRECT_URI_PROVISIONER_SOURCE_BEAN) ProvisionerSource<Collection<RedirectUri>> redirectUriProvisionerSource,
                                                              @Qualifier(POST_LOGOUT_REDIRECT_PROVISIONER_SOURCE_BEAN) ProvisionerSource<Collection<RedirectUri>> postLogoutRedirectUriProvisionerSource,
                                                              @Qualifier(REGISTERED_CLIENT_PROVISIONER_SOURCE_BEAN) ProvisionerSource<Collection<RegisteredClient>> registeredClientProvisionerSource,
                                                              OAuth2RegisteredClientRepository oAuth2RegisteredClientRepository,
                                                              RedirectRepository redirectRepository,
                                                              RegisteredClientSecretDataSourceRepository registeredClientSecretDataSourceRepository,
                                                              ScopeDataSourceRepository scopeDataSourceRepository,
                                                              AuthorizationGrantTypeDatasourceRepository authorizationGrantTypeDatasourceRepository,
                                                              ClientAuthenticationMethodDataSourceRepository clientAuthenticationMethodDataSourceRepository) {
        return new OAuth2ProvisioningService(
                redirectUriProvisionerSource,
                postLogoutRedirectUriProvisionerSource,
                registeredClientProvisionerSource,
                oAuth2RegisteredClientRepository,
                redirectRepository,
                registeredClientSecretDataSourceRepository,
                scopeDataSourceRepository,
                authorizationGrantTypeDatasourceRepository,
                clientAuthenticationMethodDataSourceRepository
        );
    }

}
