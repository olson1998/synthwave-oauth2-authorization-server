package com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.config;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.*;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.*;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.*;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.provisioning.RegisteredClientProvisioningRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.repository.UserPropertiesRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2.*;
import com.github.olson1998.synthwave.service.authorizationserver.domain.service.request.service.UserPropertiesService;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class OAuth2RepositoriesConfig {

    @Bean
    public RegisteredClientProvisioningRepository registeredClientProvisioningRepository(@NonNull RedirectURIDataSourceRepository redirectURIDataSourceRepository,
                                                                                         @NonNull RedirectURIBindingDataSourceRepository redirectURIBindingDataSourceRepository,
                                                                                         @NonNull UserDataSourceRepository userDataSourceRepository,
                                                                                         @NonNull RegisteredClientDataSourceRepository registeredClientDataSourceRepository,
                                                                                         @NonNull RegisteredClientSettingsDataSourceRepository registeredClientSettingsDataSourceRepository,
                                                                                         @NonNull AuthorizationGrantTypeBindDataSourceRepository authorizationGrantTypeBindDataSourceRepository,
                                                                                         @NonNull ClientAuthenticationMethodBindDataSourceRepository clientAuthenticationMethodBindDataSourceRepository){
        return new DefaultRegisteredClientProvisioningService(
                redirectURIDataSourceRepository,
                redirectURIBindingDataSourceRepository,
                userDataSourceRepository,
                registeredClientDataSourceRepository,
                registeredClientSettingsDataSourceRepository,
                authorizationGrantTypeBindDataSourceRepository,
                clientAuthenticationMethodBindDataSourceRepository
        );
    }

    @Bean
    public SynthWaveRegisteredClientRepository synthWaveRegisteredClientRepository(@NonNull RegisteredClientProvisioningRepository registeredClientProvisioningRepository,
                                                                                   @NonNull RedirectURIDataSourceRepository redirectURIDataSourceRepository,
                                                                                   @NonNull RegisteredClientDataSourceRepository registeredClientDataSourceRepository,
                                                                                   @NonNull AuthorizationGrantTypeBindDataSourceRepository authorizationGrantTypeBindDataSourceRepository,
                                                                                   @NonNull ClientAuthenticationMethodBindDataSourceRepository clientAuthenticationMethodBindDataSourceRepository){
        return new DefaultRegisteredClientService(
                registeredClientProvisioningRepository,
                redirectURIDataSourceRepository,
                registeredClientDataSourceRepository,
                authorizationGrantTypeBindDataSourceRepository,
                clientAuthenticationMethodBindDataSourceRepository
        );

    }

    @Bean
    public UserDetailsRepository synthWaveUserDetailsRepository(@NonNull UserJpaRepositoryProxy synthWaveUserDataSourceRepositoryProxy){
        return new DefaultUserDetailsService(synthWaveUserDataSourceRepositoryProxy);
    }

    @Bean
    public OAuth2AuthorizationRepository oAuth2AuthorizationRepository(){
        return new DefaultOAuth2AuthorizationService();
    }

    @Bean
    public UserPropertiesRepository userPropertiesRepository(@NonNull PasswordEncoder passwordEncoder,
                                                             @NonNull UserDataSourceRepository userDataSourceRepository,
                                                             @NonNull UserPasswordDataSourceRepository userPasswordDataSourceRepository,
                                                             @NonNull AffiliationDataSourceRepository affiliationDataSourceRepository){
        return new UserPropertiesService(
                passwordEncoder,
                userDataSourceRepository,
                userPasswordDataSourceRepository,
                affiliationDataSourceRepository
        );
    }
}
