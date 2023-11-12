package com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.config;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.*;
import com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.props.RegistrationClientImplProperties;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.*;
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
    public RegisteredClientMapper registeredClientMapper(){
        return new RegisteredClientMapperImpl();
    }

    @Bean
    public RegistrationClientRepository registrationClientRepository(@NonNull PasswordEncoder passwordEncoder,
                                                                     @NonNull RegistrationClientImplProperties registrationClientImplProperties){
        return new RegistrationClientService(passwordEncoder, registrationClientImplProperties);
    }

    @Bean
    public SynthWaveRegisteredClientRepository synthWaveRegisteredClientRepository(@NonNull RegisteredClientMapper registeredClientMapper,
                                                                                   @NonNull RegistrationClientRepository registrationClientRepository,
                                                                                   @NonNull UserPropertiesJpaRepositoryProxy userPropertiesSourceRepository,
                                                                                   @NonNull RedirectURIJpaRepositoryProxy redirectURIJpaRepositoryProxy,
                                                                                   @NonNull RegisteredClientJpaRepositoryProxy synthWaveRegisteredClientJpaRepositoryProxy,
                                                                                   @NonNull AuthorizationGrantTypeBindJpaRepositoryProxy authorizationGrantTypeBindJpaRepositoryProxy,
                                                                                   @NonNull ClientAuthenticationMethodJpaRepositoryProxy clientAuthenticationMethodJpaRepositoryProxy){
        return new SynthWaveRegisteredClientService(
                registeredClientMapper,
                registrationClientRepository,
                redirectURIJpaRepositoryProxy,
                userPropertiesSourceRepository,
                synthWaveRegisteredClientJpaRepositoryProxy,
                authorizationGrantTypeBindJpaRepositoryProxy,
                clientAuthenticationMethodJpaRepositoryProxy
        );

    }

    @Bean
    public UserDetailsRepository synthWaveUserDetailsRepository(@NonNull UserPropertiesJpaRepositoryProxy synthWaveUserDataSourceRepositoryProxy){
        return new UserDetailsService(synthWaveUserDataSourceRepositoryProxy);
    }

    @Bean
    public OAuth2AuthorizationRepository oAuth2AuthorizationRepository(){
        return new SimpleOAuth2AuthorizationService();
    }

    @Bean
    public UserPropertiesRepository userPropertiesRepository(@NonNull PasswordEncoder passwordEncoder,
                                                             @NonNull UserPropertiesJpaRepositoryProxy userPropertiesJpaRepositoryProxy,
                                                             @NonNull UserPasswordJpaRepositoryProxy userPasswordJpaRepositoryProxy,
                                                             @NonNull AffiliationJpaRepositoryProxy affiliationJpaRepositoryProxy){
        return new UserPropertiesService(
                passwordEncoder,
                userPropertiesJpaRepositoryProxy,
                userPasswordJpaRepositoryProxy,
                affiliationJpaRepositoryProxy
        );
    }
}
